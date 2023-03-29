package com.digdes.school.ast;

import com.digdes.school.Token;

public class BooleanNode extends DataNode {
    public BooleanNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanNode column = (BooleanNode) o;

        return getToken().equals(column.getToken());
    }
}
