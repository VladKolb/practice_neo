package com.example.neo_practice.servises;

import com.example.neo_practice.models.Object;
import com.example.neo_practice.models.Revision;
import com.example.neo_practice.repositories.ObjectRepository;
import com.example.neo_practice.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private ObjectRepository objectRepository;

    public void addObjectToRevision(String objectId, String revisionId){
        if(revisionId == null || objectId == null){
            throw new IllegalArgumentException("Revision id and object id must be not null");
        }

        Object currentObject = objectRepository.findOneById(objectId);
        Revision currentRevision = revisionRepository.findOneById(revisionId);

        if(currentRevision != null && currentObject != null){
            for(String parentObject :  currentRevision.getParentObjects()){
                if(parentObject.equals(objectId) || objectRepository.findOneById(objectId).isRoot()){
                    System.out.println("Error: cycle");
                }
            }
            objectRepository.createRelationship(objectId, revisionId);
            currentRevision.getChilds().add(objectId);
            currentObject.getParentRevisions().add(revisionId);
            revisionRepository.save(currentRevision);
            objectRepository.save(currentObject);
        }
        else{
            System.out.println("Object or revision not found");
        }
    }

    public void addRevisionToObject(String objectId, String revisionId){
        if(revisionId == null || objectId == null){
            throw new IllegalArgumentException("Revision id and object id must be not null");
        }

        Object currentObject = objectRepository.findOneById(objectId);
        Revision currentRevision = revisionRepository.findOneById(revisionId);

        if(currentRevision != null && currentObject != null){
            for(String parentRevision : currentObject.getParentRevisions()){
                if (parentRevision.equals(revisionId)) {
                    System.out.println("Error: cycle");
                    return;
                }
            }
            revisionRepository.createRelationship(objectId, revisionId);
            currentObject.getChilds().add(revisionId);
            currentRevision.getParentObjects().add(objectId);
            revisionRepository.save(currentRevision);
            objectRepository.save(currentObject);
        }
        else{
            System.out.println("Object or revision not found");
        }
    }



}
