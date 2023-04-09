package com.digdes.school.ast;

import com.digdes.school.Token;

public class LongNode extends DataNode {
    public LongNode(Token token) {
        super(token);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongNode column = (LongNode) o;

        return getToken().equals(column.getToken());
    }
}
