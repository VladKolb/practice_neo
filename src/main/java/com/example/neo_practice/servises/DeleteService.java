package com.example.neo_practice.servises;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DeleteService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    public void deleteRevision(String revisionId){
        if(revisionId == null){
            throw new IllegalArgumentException("Revision id must not be null");
        }

        Revision revision = revisionRepository.findOneById(revisionId);
        if(revision != null){
            Set<String> parentId = revision.getParentObjects();
            for(String parent : parentId){
                Object parentObject = objectRepository.findOneById(parent);
                if(parentObject.getChilds().size() == 1){
                    System.out.println("Can't delete the last revision");
                    return;
                }

                parentObject.getChilds().remove(revisionId);
                revisionRepository.deleteParentRelation(parent, revisionId);

                Set<String> childsID = revision.getChilds();
                for(String childId : childsID){
                    Object child = objectRepository.findOneById(childId);
                    child.setParentRevisions(new HashSet<>());
                    child.setRoot(true);
                    objectRepository.save(child);
                }
                revisionRepository.deleteExtendedAndRevision(revisionId);
                objectRepository.save(parentObject);
                revisionRepository.delete(revision);
                System.out.println("Revision delete");
            }
        }
        else {
            System.out.println("Revision not found");
        }
    }

    public void deleteObject(String objectId){
        if (objectId == null){
            throw new IllegalArgumentException("Object id must not be null");
        }

        Object object = objectRepository.findOneById(objectId);
        if(object != null){
            if(object.isRoot() && object.getChilds().size() == 1){
                Set<String> childRevisionsId = object.getChilds();
                for(String childRevisioinId : childRevisionsId){
                    revisionRepository.deleteParentRelation(objectId, childRevisioinId);
                    for(String childObjectId : revisionRepository.findOneById(childRevisioinId).getChilds()){
                        Object child = objectRepository.findOneById(childObjectId);
                        child.setParentRevisions(new HashSet<>());
                        child.setRoot(true);
                        objectRepository.save(child);
                    }
                    revisionRepository.deleteExtendedAndRevision(childRevisioinId);
                    revisionRepository.delete(revisionRepository.findOneById(childRevisioinId));
                    objectRepository.deleteObjectAndRelation(objectId);
                    objectRepository.delete(objectRepository.findOneById(objectId));
                    System.out.println("Object deleted");
                }

            }
            else{
                System.out.println("Can't delete object with more than one revision");
            }
        }
        else{
            System.out.println("Object not found");
        }
    }

    public void deleteObjectFromRevision(String revisionId, String objectId){
        if(revisionId == null || objectId == null){
            System.out.println("Revision id and object id must not be null");
        }

        Object currentObject = objectRepository.findOneById(objectId);
        Revision currentRevision = revisionRepository.findOneById(revisionId);
        if(currentRevision != null && currentObject != null){
            if(!currentRevision.getChilds().contains(objectId)){
                System.out.println("Object isn't child for this revision");
                return;
            }

            objectRepository.deleteParentRelation(objectId);
            if(currentObject.getParentRevisions().size() == 1){
                currentObject.setParentRevisions(new HashSet<>());
                currentObject.setRoot(true);
            }
            else{
                Set<String> newParentSet = new HashSet<>();
                for(String parentId : currentObject.getParentRevisions()){
                    if(parentId.equals(revisionId)){
                        continue;
                    }
                    newParentSet.add(parentId);
                }
                currentObject.setParentRevisions(newParentSet);
            }

            Set<String> newObjectsChilds = new HashSet<>();
            for(String childObjectId : currentRevision.getChilds()){
                if(childObjectId.equals(objectId)){
                    continue;
                }

                newObjectsChilds.add(childObjectId);
            }

            currentRevision.setChilds(newObjectsChilds);
            revisionRepository.save(currentRevision);
            objectRepository.save(currentObject);
            System.out.println("Object deleted from revision");
        }
        else{
            System.out.println("Object or revision not found");
        }
    }

}
