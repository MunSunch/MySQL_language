package com.digdes.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {
    private List<Map<String, Object>> table;

    public JavaSchoolStarter(List<Map<String, Object>> table) {
        this.table = new ArrayList<>(table);
    }

    public JavaSchoolStarter() {}

    public List<Map<String,Object>> execute(String request) throws Exception {
//        Lexer lexer = new Lexer();
//        lexer.analys(request);
//        Parser parser = new Parser(lexer.getTokens());
//        StatementNode root = parser.getRoot();
//        Translator translator = new Translator(root, table);
        return new ArrayList<>();
    }
}
