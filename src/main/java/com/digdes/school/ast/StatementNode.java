package com.digdes.school.ast;

import java.util.ArrayList;
import java.util.List;

public class StatementNode extends Node {
    private List<Node> nodes;

    public StatementNode() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatementNode that = (StatementNode) o;

        return nodes != null ? nodes.equals(that.nodes) : that.nodes == null;
    }
}
