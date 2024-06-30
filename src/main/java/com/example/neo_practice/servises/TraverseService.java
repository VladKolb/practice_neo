package com.example.neo_practice.servises;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

@Service
public class TraverseService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    public void traverseTree(Object root){
        long startTimeItertive = System.currentTimeMillis();
        iterariveTraversal(root);
        long endTimeIterative = System.currentTimeMillis();
        long durationIterarive = endTimeIterative - startTimeItertive;
        System.out.println("Traversal time: " + durationIterarive + "ms");
    }

    private void iterariveTraversal(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);

        while(!stack.isEmpty()){
            Object current = stack.pop();
            Set<Revision> revisions = new HashSet<>();
            for (String revisionId : current.getChilds()){
                revisions.add(revisionRepository.findOneById(revisionId));
            }
            if(revisions != null){
                for(Revision revision : revisions){
                    Set<Object> objects = new HashSet<>();
                    for (String objectId : revision.getChilds()){
                        objects.add(objectRepository.findOneById(objectId));
                    }
                    if(objects != null){
                        for(Object child : objects){
                            stack.push(child);
                        }
                    }
                }
            }
        }
    }
}
