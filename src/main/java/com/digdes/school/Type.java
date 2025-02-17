package com.digdes.school;

public enum Type {
    COMMA(","),
    SPACE(" "),
    KEYWORD("^SELECT|^UPDATE|^INSERT|^DELETE|^WHERE|^VALUES"),
    STAR("\\*"),
    OPERATOR("^=|^!=|^<=|^>=|^<|^>|^LIKE|^ILIKE"),
    LOGICAL_OPERATOR("^AND|^OR"),
    DOUBLE("\\d+\\.\\d+"),
    LONG("\\d+"),
    BOOLEAN("^true|^false"),
    STRING("'[a-zA-Zа-яА-Я0-9%]+'"),
    COLUMN("\\w+");

    private String regex;

    Type(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
