package com.digdes.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) throws Exception
    {
        List<Map<String, Object>> table = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("name", "Munir");
        row1.put("age", 24);
        Map<String, Object> row2 = new HashMap<>();
        row2.put("name", "Andrey");
        row2.put("age", 26);
        table.add(row1);
        table.add(row2);

        String command = "UPDATE VALUES name='Kolya', age=12";
        var result = execute(table, command);
        result.stream().forEach(x -> System.out.println(x));
    }

    private static List<Map<String, Object>> execute(List<Map<String, Object>> table,
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