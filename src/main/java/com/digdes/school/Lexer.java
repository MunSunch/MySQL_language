package com.digdes.school;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final List<Token> tokens;
    private final List<Type> ignoreTypes;
    private int position;

    public Lexer() {
        tokens = new ArrayList<>();
        ignoreTypes = new ArrayList<>();
        addIgnoreType(Type.SPACE);
    }

    public void analys(String code) throws Exception{
        var TypeType = Type.values();
        Matcher matcher;
        OUTER:
        while(position < code.length()) {
            String subString = code.substring(position);
            for (Type curType: Type.values()) {
                matcher = Pattern.compile("^" + curType.getRegex())
                        .matcher(subString);
                if(matcher.find()) {
                    int positionNextToken = position + matcher.end();
                    if(ignoreTypes.stream().noneMatch(x -> x == curType)) {
                        tokens.add(new Token(curType,
                                code.substring(position, positionNextToken),
                                position));
                    }
                    position = positionNextToken;
                    continue OUTER;
                }
            }
            throw new Exception("Incorrect expression! Position: " + position);
        }
    }

    public void addIgnoreType(Type ...ignoreType) {
        this.ignoreTypes.addAll(List.of(ignoreType));
    }

    public void reset() {
        position = 0;
        tokens.clear();
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
