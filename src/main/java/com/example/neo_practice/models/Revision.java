package com.example.neo_practice.models;


import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.Set;

@Node("Revision")
public class Revision {
    @Id
    private String id;

    private Set<String> parentObjects;

    private Set<String> childs;

    private LocalDate startDate;

    private LocalDate endDate;

    private int amount;

    private String color;

    @Relationship(type = "HAVE", direction = Relationship.Direction.OUTGOING)
    private Set<Object> childObjects;

    public Revision(Set<String> parentObjects,Set<String> childs, Set<Object> childObjects, LocalDate startDate, LocalDate endDate, int amount, String color) {
        this.id = java.util.UUID.randomUUID().toString();
        this.parentObjects = parentObjects;
        this.childObjects = childObjects;
        this.childs = childs;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.color = color;
    }

    public Revision(){
        this.id = java.util.UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public Set<String> getParentObjects() {
        return parentObjects;
    }

    public void setParentObjects(Set<String> parentObjects) {
        this.parentObjects = parentObjects;
    }

    public Set<String> getChilds() {
        return childs;
    }

    public void setChilds(Set<String> childs) {
        this.childs = childs;
    }

    public Set<Object> getChildObjects() {
        return childObjects;
    }

    public void setChildObjects(Set<Object> childObjects) {
        this.childObjects = childObjects;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void addChild(Object object) {
        this.childObjects.add(object);
    }

    public void addChildID(String objectId) {
        this.childs.add(objectId);
    }
}
