package com.digdes.school.ast;

import com.digdes.school.Token;

public class BinaryOperatorNode extends Node {
    Node left;
    Token operator;
    Node right;

    public BinaryOperatorNode(Node left, Token operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public BinaryOperatorNode() {
    }

    public void setLeft(Node left) {
        this.left = left;
    }
    public void setOperator(Token operator) {
        this.operator = operator;
    }
    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryOperatorNode that = (BinaryOperatorNode) o;

        return left.equals(that.left)
            && right.equals(that.right)
            && operator.equals(that.operator);
    }
}
