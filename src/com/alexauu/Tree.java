package com.alexauu;

import java.util.ArrayList;
import java.util.List;

class Tree {

    private String attribute;  //节点的属性值
    private String arrived_attribute; //到达的属性值
    private List<Tree> children;

    Tree() {
        this.children  = new ArrayList<>();
    }

    Tree(String attribute, String arrived_attribute) {
        this.attribute = attribute;
        this.arrived_attribute = arrived_attribute;
        this.children  = new ArrayList<>();
    }

    void addNode(Tree node){
        children.add(node);
    }

    String getAttribute() {
        return attribute;
    }

    void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    String getArrived_attribute() {
        return arrived_attribute;
    }

    void setArrived_attribute(String arrived_attribute) {
        this.arrived_attribute = arrived_attribute;
    }

    List<Tree> getChildren() {
        return children;
    }

}
