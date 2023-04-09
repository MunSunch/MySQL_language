package com.digdes.school.ast;

public class WhereNode extends Node {
    private BinaryOperatorNode root;

    public WhereNode(BinaryOperatorNode root) {
        this.root = root;
    }
    public WhereNode() {}

    public BinaryOperatorNode getRoot() {
        return root;
    }

    public void setRoot(BinaryOperatorNode root) {
        this.root = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhereNode whereNode = (WhereNode) o;
        var status = root.equals(whereNode.getRoot());
        return status;
    }
}
