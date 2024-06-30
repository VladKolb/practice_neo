package com.example.neo_practice.servises;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TreeService {

    private static final int MAX_NODES = 50;

    private final Random random = new Random();

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    private int currentNodeCount = 0;

    public void generateTree() {
        Object root = new Object(true, null, new HashSet<>(), new HashSet<>());
        List<Revision> firstRevsions = generateRevisions(root);
        revisionRepository.saveAll(firstRevsions);
        objectRepository.save(root);
        currentNodeCount++;
        Deque<Revision> stack = new ArrayDeque<>(firstRevsions);
        while (!stack.isEmpty() && currentNodeCount < MAX_NODES){
            Revision currentRevision = stack.pop();
            Set<Object> childObjects = new HashSet<>();
            Set<String> childObjectsId = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                if (currentNodeCount >= MAX_NODES){
                    break;
                }
                Object childObject = new Object(false, new HashSet<>(), new HashSet<>(), new HashSet<>());
                childObject.getParentRevisions().add(currentRevision.getId());
                childObjects.add(childObject);
                childObjectsId.add(childObject.getId());
                Set<Revision> childRevisions = new HashSet<>(generateRevisions(childObject));
                stack.addAll(childRevisions);
                revisionRepository.saveAll(childRevisions);
                objectRepository.save(childObject);
                currentNodeCount++;
            }
            currentRevision.setChilds(childObjectsId);
            currentRevision.setChildObjects(childObjects);
            revisionRepository.save(currentRevision);
        }

    }

    private List<Revision> generateRevisions(Object parentObject){
        List<Revision> revisions = new ArrayList<>();
        Set<String> revisionsId = new HashSet<>();

        for (int i = 0; i < 5; i++){
            LocalDate startDate = LocalDate.of(2020, 6, 10).plusDays(random.nextInt(1460));
            Revision revision = new Revision(new HashSet<>(), new HashSet<>(), new HashSet<>(), startDate, startDate.plusMonths(6), random.nextInt(1000), getRandomColor());
            revision.getParentObjects().add(parentObject.getId());
            revisions.add(revision);
            revisionsId.add(revision.getId());
        }
        parentObject.setChilds(revisionsId);
        parentObject.setChildRevisions(new HashSet<>(revisions));
        return revisions;
    }

    private String getRandomColor(){
        String[] colors = {"Red", "Green", "Blue", "Yellow", "Black", "White"};
        return colors[random.nextInt(colors.length)];
    }

    public void createObject(Revision parentRevision){
        Object object = new Object(false, new HashSet<>(), new HashSet<>(), new HashSet<>());

        generateRevisions(object);

        parentRevision.addChild(object);
        parentRevision.addChildID(object.getId());

        object.addParent(parentRevision.getId());

        objectRepository.save(object);
        revisionRepository.save(parentRevision);
    }
}
