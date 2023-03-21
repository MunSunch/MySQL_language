package com.digdes.school;

import java.util.Objects;

public final class Token {
    private final Type type;
    private final Object value;
    private final int position;

    public Token(Type type, Object value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public Type getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }
    public int getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (position != token.position) return false;
        if (type != token.type) return false;
        return Objects.equals(value, token.value);
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value=" + value +
                ", position=" + position +
                '}';
    }
}
