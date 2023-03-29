package com.digdes.school.ast;

import com.digdes.school.Token;

import java.util.Objects;

public class DataNode extends Node {
    private Token token;

    public DataNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataNode dataNode = (DataNode) o;

        return Objects.equals(token, dataNode.token);
    }
}
