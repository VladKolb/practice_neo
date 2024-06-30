package com.example.neo_practice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;
@Node("Object")
public class Object {
    @Id
    private String id;

    private boolean isRoot;

    private Set<String> parentRevisions;

    private Set<String> childs;

    @Relationship(type = "HAVE", direction = Relationship.Direction.OUTGOING)
    private Set<Revision> childRevisions;

    public Object(){
        this.id = java.util.UUID.randomUUID().toString();
    }

    public Object(boolean isRoot, Set<String> parentRevisions, Set<Revision> childRevisions, Set<String> childs){
        this.id = java.util.UUID.randomUUID().toString();
        this.childRevisions = childRevisions;
        this.parentRevisions = parentRevisions;
        this.childs = childs;
        this.isRoot = isRoot;
    }

    public String getId() {
        return id;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public Set<String> getParentRevisions() {
        return parentRevisions;
    }

    public void setParentRevisions(Set<String> parentRevisions) {
        this.parentRevisions = parentRevisions;
    }

    public Set<Revision> getChildRevisions() {
        return childRevisions;
    }

    public void setChildRevisions(Set<Revision> childRevisions) {
        this.childRevisions = childRevisions;
    }

    public Set<String> getChilds() {
        return childs;
    }

    public void setChilds(Set<String> childs) {
        this.childs = childs;
    }

    public void addParent(String parentID) {
        this.parentRevisions.add(parentID);
    }
}
