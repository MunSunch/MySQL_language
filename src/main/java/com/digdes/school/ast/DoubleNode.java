package com.digdes.school.ast;

import com.digdes.school.Token;

public class DoubleNode extends DataNode {
    public DoubleNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleNode column = (DoubleNode) o;

        return getToken().equals(column.getToken());
    }
}
