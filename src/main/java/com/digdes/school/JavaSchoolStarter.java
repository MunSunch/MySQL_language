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

    public static List<Map<String, Object>> execute(List<Map<String, Object>> table,
                                                     String command) throws Exception
    {
        Lexer lexer = new Lexer();
        lexer.analys(command);
        Parser parser = new Parser(lexer.getTokens());
        var stack = parser.parse();
        Translator translator = new Translator(stack, table);
        return translator.translate();
    }
}
