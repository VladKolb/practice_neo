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
public class AvgService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private FilterService filterService;

    // Методы для поиска min и max
    public void findObjectWithMinQuantityRevision(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);
        Revision finalyMin = findMinQuantityRevision(root);

        while(!stack.isEmpty()){
            Object currentObject = stack.pop();
            Revision minRevision = findMinQuantityRevision(currentObject);


            if(minRevision != null) {
                if(finalyMin.getAmount() < minRevision.getAmount()){
                    finalyMin = minRevision;
                }
                Set<String> childRevisionsId = currentObject.getChilds();
                for(String childRevisiionId : childRevisionsId){
                    Revision childRevision = revisionRepository.findOneById(childRevisiionId);
                    for(String childObjectId : childRevision.getChilds()){
                        stack.push(objectRepository.findOneById(childObjectId));
                    }
                }
            }
        }
        System.out.println("Object: " +  finalyMin.getParentObjects() + ", with max revision: " + finalyMin.getId() + ". Amount = " + finalyMin.getAmount());
    }

    public Revision findMinQuantityRevision(Object object){
        if(object.getChilds() == null || object.getChilds().isEmpty()){
            return null;
        }

        Iterator<String> iterator = object.getChilds().iterator();
        Revision minRevision = revisionRepository.findOneById(iterator.next());

        while(iterator.hasNext()){
            Revision currentRevision = revisionRepository.findOneById(iterator.next());
            if(currentRevision.getAmount() < minRevision.getAmount()){
                minRevision = currentRevision;
            }
        }

        return minRevision;
    }

    //Recursive
    public Object findObjectWithMinRevisions(Object root) {
        if (root == null || root.getChilds().isEmpty()) {
            return root;
        }

        Object minRevisionObject = root;
        for (String childRevisionId : root.getChilds()) {
            for (String childObjectId : revisionRepository.findOneById(childRevisionId).getChilds()) {
                Object candidate = findObjectWithMinRevisions(objectRepository.findOneById(childObjectId));
                if (candidate != null && candidate.getChilds().size() < minRevisionObject.getChilds().size()) {
                    minRevisionObject = candidate;
                }
            }
        }
        return minRevisionObject;
    }

    public void findObjectWithMaxQuantityRevision(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);
        Revision finalyMax = findMaxQuantityRevision(root);

        while(!stack.isEmpty()){
            Object currentObject = stack.pop();
            Revision maxRevision = findMaxQuantityRevision(currentObject);


            if(maxRevision != null) {
                if(finalyMax.getAmount() < maxRevision.getAmount()){
                    finalyMax = maxRevision;
                }
                Set<String> childRevisionsId = currentObject.getChilds();
                for(String childRevisiionId : childRevisionsId){
                    Revision childRevision = revisionRepository.findOneById(childRevisiionId);
                    for(String childObjectId : childRevision.getChilds()){
                        stack.push(objectRepository.findOneById(childObjectId));
                    }
                }
            }
        }
        System.out.println("Object: " +  finalyMax.getParentObjects() + ", with max revision: " + finalyMax.getId() + ". Amount = " + finalyMax.getAmount());
    }

    public Revision findMaxQuantityRevision(Object object){
        if(object.getChilds() == null || object.getChilds().isEmpty()){
            return null;
        }

        Iterator<String> iterator = object.getChilds().iterator();
        Revision maxRevision = revisionRepository.findOneById(iterator.next());

        while(iterator.hasNext()){
            Revision currentRevision = revisionRepository.findOneById(iterator.next());
            if(currentRevision.getAmount() > maxRevision.getAmount()){
                maxRevision = currentRevision;
            }
        }

        return maxRevision;
    }

    //Recursive
    public Object findObjectWithMaxRevisions(Object root) {
        if (root == null || root.getChilds().isEmpty()) {
            return root;
        }

        Object maxRevisionObject = root;
        for (String childRevisionId : root.getChilds()) {
            for (String childObjectId : revisionRepository.findOneById(childRevisionId).getChilds()) {
                Object candidate = findObjectWithMinRevisions(objectRepository.findOneById(childObjectId));
                if (candidate != null && candidate.getChilds().size() > maxRevisionObject.getChilds().size()) {
                    maxRevisionObject = candidate;
                }
            }
        }
        return maxRevisionObject;
    }


    // Методы для нахождения среднего значения
    // 1. По всему дереву
    public void findFullAverageAmount(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);
        double currentTotal = 0;
        int size = 0;

        while(!stack.isEmpty()){
            Object currentObject = stack.pop();

            for(String childRevisionId:currentObject.getChilds()){
                Revision childRevison = revisionRepository.findOneById(childRevisionId);
                currentTotal += childRevison.getAmount();

                for(String childObjectId:childRevison.getChilds()){
                    stack.push(objectRepository.findOneById(childObjectId));
                }
            }

            size += currentObject.getChilds().size();
        }

        System.out.println(currentTotal/size);
    }

    //2. По всему дереву, начиная с какого-то объекта
    public void findAverageAmountInAllRevisionsInObject(Object object){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(object);

        double currentTotal = 0;
        int size = 0;

        while (!stack.isEmpty()){
            Object currentObject = stack.pop();

            for(String childRevisionId:currentObject.getChilds()){
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);

                currentTotal += currentRevision.getAmount();

                for(String childObjectId:currentRevision.getChilds()){
                    stack.push(objectRepository.findOneById(childObjectId));
                }
            }

            size += currentObject.getChilds().size();
        }


        System.out.println(currentTotal/size);
    }


    //3. В пределах одного объекта
    public void findAverageAmountInObject(Object object){
        double currentTotal = 0;

        for(String childRevisionId:object.getChilds()){
            currentTotal += revisionRepository.findOneById(childRevisionId).getAmount();
        }

        System.out.println(currentTotal/object.getChilds().size());
    }


    // Методы для нахождения sum
    public void findFullSumAmount(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);
        int currentTotal = 0;

        while(!stack.isEmpty()){
            Object currentObject = stack.pop();

            for(String childRevisionId:currentObject.getChilds()){
                Revision childRevison = revisionRepository.findOneById(childRevisionId);
                currentTotal += childRevison.getAmount();

                for(String childObjectId:childRevison.getChilds()){
                    stack.push(objectRepository.findOneById(childObjectId));
                }
            }
        }

        System.out.println(currentTotal);
    }

    public void findSumAmountInAllRevisionsInObject(Object object){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(object);

        int currentTotal = 0;


        while (!stack.isEmpty()){
            Object currentObject = stack.pop();

            for(String childRevisionId:currentObject.getChilds()){
                Revision currentRevision = revisionRepository.findOneById(childRevisionId);

                currentTotal += currentRevision.getAmount();

                for(String childObjectId:currentRevision.getChilds()){
                    stack.push(objectRepository.findOneById(childObjectId));
                }
            }
        }

        System.out.println(currentTotal);
    }

    public void findSumAmountInObject(Object object){
        int currentTotal = 0;

        for(String childRevisionId:object.getChilds()){
            currentTotal += revisionRepository.findOneById(childRevisionId).getAmount();
        }

        System.out.println(currentTotal);
    }


    //Методы для нахождения sum при заданных параметрах (WHERE)
    public void findSumAmountWithStartDataParametr(LocalDate startDate, String sign){
        int totalSum = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnStartDateFilter(startDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }
        }

        System.out.println("Sum of revisions with startDate " + sign + startDate + " = " + totalSum);
    }

    public void findSumAmountWithEndDataParametr(LocalDate endDate, String sign){
        int totalSum = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateFilter(endDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }
        }

        System.out.println("Sum of revisions with endDate " + sign + endDate + " = " + totalSum);
    }

    public void findSumAmountWithAmountParametr(int amount, String sign){
        int totalSum = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnAmountFilter(amount, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }
        }

        System.out.println("Sum of revisions with amount " + sign + amount + " = " + totalSum);
    }

    public void findSumAmountWithColorParametr(String color){

        int totalSum = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnColorFilter(color);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }
        }

        System.out.println("Sum of revisions with color " + color + " = " + totalSum);

    }

    public void findSumAmountWithStartAndEndDateParametr(LocalDate startDate, LocalDate endDate){

        int totalSum = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateAndStartDayFilter(startDate, endDate);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }
        }

        System.out.println("Sum of revisions with startDate " + startDate + " and endDate" + endDate + " = " + totalSum);

    }

    //Нахождение среднего значения при заданных параметрах (WHERE)

    public void findAvgAmountWithStartDataParametr(LocalDate startDate, String sign){
        double totalSum = 0;
        int size = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnStartDateFilter(startDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }

            size += filteredRevisionsId.size();
        }

        double average = totalSum/size;

        System.out.println("Avg of revisions with startDate " + sign + startDate + " = " + average);
    }

    public void findAvgAmountWithEndDataParametr(LocalDate endDate, String sign){
        double totalSum = 0;
        int size = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateFilter(endDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }

            size += filteredRevisionsId.size();
        }

        double average = totalSum/size;

        System.out.println("Avg of revisions with endDate " + sign + endDate + " = " + average);
    }

    public void findAvgAmountWithStartAndEndDataParametr(LocalDate startDate, LocalDate endDate){
        double totalSum = 0;
        int size = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateAndStartDayFilter(startDate, endDate);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }

            size += filteredRevisionsId.size();
        }

        double average = totalSum/size;

        System.out.println("Avg of revisions with startDate " + startDate + " and endDate " + endDate + " = " + average);
    }

    public void findAvgAmountWithAmountParametr(int amount, String sign){
        double totalSum = 0;
        int size = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnAmountFilter(amount, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }

            size += filteredRevisionsId.size();
        }

        double average = totalSum/size;

        System.out.println("Avg of revisions with amount " + sign + amount + " = " + average);
    }

    public void findAvgAmountWithColorParametr(String color){
        double totalSum = 0;
        int size = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnColorFilter(color);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            for(String revisionId:filteredRevisionsId){
                totalSum += revisionRepository.findOneById(revisionId).getAmount();
            }

            size += filteredRevisionsId.size();
        }

        double average = totalSum/size;

        System.out.println("Avg of revisions with color " + color + " = " + average);
    }

    //Методы count с параметрами (WHERE)

    public void countObjectAndRevision(Object root){
        Deque<Object> stack = new ArrayDeque<>();
        stack.push(root);
        int totalObjects = 0;
        int totalRevisions = 0;

        while(!stack.isEmpty()){
            Object currentObject = stack.pop();
            totalObjects++;

            for(String childRevisionId: currentObject.getChilds()){
                totalRevisions++;
                for(String childObjectId:revisionRepository.findOneById(childRevisionId).getChilds()){
                    stack.push(objectRepository.findOneById(childObjectId));
                }
            }
        }

        System.out.println("Quantity of objects: " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }

    public void countObjectAndRevisionWithStartDateParametr(LocalDate startDate, String sign){
        int totalObjects = 0;
        int totalRevisions = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnStartDateFilter(startDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            totalObjects++;
            for(String revisionId:filteredRevisionsId){
                totalRevisions++;
            }

        }

        System.out.println("Quantity of objects with startDate " + sign + startDate + ": " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }

    public void countObjectAndRevisionWithEndDateParametr(LocalDate endDate, String sign){
        int totalObjects = 0;
        int totalRevisions = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateFilter(endDate, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            totalObjects++;
            for(String revisionId:filteredRevisionsId){
                totalRevisions++;
            }

        }

        System.out.println("Quantity of objects with endDate " + sign + endDate + ": " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }

    public void countObjectAndRevisionWithStartAndEndDateParametr(LocalDate startDate, LocalDate endDate){
        int totalObjects = 0;
        int totalRevisions = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnEndDateAndStartDayFilter(startDate, endDate);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            totalObjects++;
            for(String revisionId:filteredRevisionsId){
                totalRevisions++;
            }

        }

        System.out.println("Quantity of objects with startDate " + startDate + " and endDate" + endDate + ": " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }

    public void countObjectAndRevisionWithAmountParametr(int amount, String sign){
        int totalObjects = 0;
        int totalRevisions = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnAmountFilter(amount, sign);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            totalObjects++;
            for(String revisionId:filteredRevisionsId){
                totalRevisions++;
            }

        }

        System.out.println("Quantity of objects with amount " + sign + amount + ": " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }

    public void countObjectAndRevisionWithColorParametr(String color){
        int totalObjects = 0;
        int totalRevisions = 0;

        HashMap<String, Set<String>> filteredSet = filterService.returnColorFilter(color);

        for (Map.Entry<String, Set<String>> entry : filteredSet.entrySet()) {
            String id = entry.getKey();
            Set<String> filteredRevisionsId = entry.getValue();
            if(filteredRevisionsId.isEmpty()){
                continue;
            }
            totalObjects++;
            for(String revisionId:filteredRevisionsId){
                totalRevisions++;
            }

        }

        System.out.println("Quantity of objects with color " + color + ": " + totalObjects + "\nQuantity of revisions: " + totalRevisions);
    }




}
