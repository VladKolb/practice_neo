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
public class FilterService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    public void colorFilter(String color) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if (currentRevision.getColor().equals(color)) {
                    filteredRevisions.add(childRevisionId);
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            System.out.println("For object " + id + " filtered revision's id are: " + String.join(", ", filteredRevisionsId));
        }
    }

    public void startDateFilter(LocalDate startDate, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getStartDate().isBefore(startDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getStartDate().isAfter(startDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            System.out.println("For object " + id + " filtered revision's id are: " + String.join(", ", filteredRevisionsId));
        }
    }

    public void endDateFilter(LocalDate endDate, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getEndDate().isBefore(endDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getEndDate().isAfter(endDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            System.out.println("For object " + id + " filtered revision's id are: " + String.join(", ", filteredRevisionsId));
        }
    }

    public void amountFilter(int amount, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getAmount() < amount){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getAmount() > amount){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            System.out.println("For object " + id + " filtered revision's id are: " + String.join(", ", filteredRevisionsId));
        }
    }

    public void endDateAndStartDayFilter(LocalDate startDate, LocalDate endDate) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(currentRevision.getStartDate().isAfter(startDate) && currentRevision.getEndDate().isBefore(endDate)){
                    filteredRevisions.add(childRevisionId);
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            System.out.println("For object " + id + " filtered revision's id are: " + String.join(", ", filteredRevisionsId));
        }
    }

    // Same methods, but with return

    public HashMap<String, Set<String>> returnColorFilter(String color) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if (currentRevision.getColor().equals(color)) {
                    filteredRevisions.add(childRevisionId);
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        return filteredSet;
    }

    public HashMap<String, Set<String>> returnStartDateFilter(LocalDate startDate, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getStartDate().isBefore(startDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getStartDate().isAfter(startDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        return filteredSet;
    }

    public HashMap<String, Set<String>> returnEndDateFilter(LocalDate endDate, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getEndDate().isBefore(endDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getEndDate().isAfter(endDate)){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        return filteredSet;
    }

    public HashMap<String, Set<String>> returnAmountFilter(int amount, String sign) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(sign.equals("<")){
                    if(currentRevision.getAmount() < amount){
                        filteredRevisions.add(childRevisionId);
                    }
                } else if (sign.equals(">")) {
                    if(currentRevision.getAmount() > amount){
                        filteredRevisions.add(childRevisionId);
                    }
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        return filteredSet;
    }

    public HashMap<String, Set<String>> returnEndDateAndStartDayFilter(LocalDate startDate, LocalDate endDate) {
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(objectRepository.findRoot());
        HashMap<String, Set<String>> filteredSet = new HashMap<String, Set<String>>();

        while (!stack.isEmpty()) {
            Object currentObject = stack.pop();

            Set<String> filteredRevisions = new HashSet<>();

            for (String childRevisionId : currentObject.getChilds()) {
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);
                if(currentRevision.getStartDate().isAfter(startDate) && currentRevision.getEndDate().isBefore(endDate)){
                    filteredRevisions.add(childRevisionId);
                }

                for (String childObjectId : currentRevision.getChilds()) {
                    stack.add(objectRepository.findOneById(childObjectId));
                }
            }

            filteredSet.put(currentObject.getId(), filteredRevisions);
        }

        return filteredSet;
    }

}
