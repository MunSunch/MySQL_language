package com.digdes.school.ast;

import com.digdes.school.Token;

public class IntegerNode extends DataNode {
    public IntegerNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntegerNode column = (IntegerNode) o;

        return getToken().equals(column.getToken());
    }
}
