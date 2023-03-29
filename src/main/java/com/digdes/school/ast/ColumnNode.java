package com.digdes.school.ast;

import com.digdes.school.Token;

public class ColumnNode extends DataNode{
    public ColumnNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnNode column = (ColumnNode) o;

        return getToken().equals(column.getToken());
    }
}
