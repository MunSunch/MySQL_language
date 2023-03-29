package com.digdes.school.translator;

import com.digdes.school.Token;
import com.digdes.school.Translator;
import com.digdes.school.Type;
import com.digdes.school.ast.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class TranslatorTests {
    private Translator translator;
    private static List<Map<String, Object>> table;

    @BeforeAll
    public static void setTable() {
        table = new ArrayList<>();
        Map<String, Object> row1 = new HashMap<>();
        row1.put("name", "Munir");
        row1.put("age", 24);
        Map<String, Object> row2 = new HashMap<>();
        row2.put("name", "Andrey");
        row2.put("age", 26);
        table.add(row1);
        table.add(row2);
    }

//    @DisplayName("SELECT *")
//    @Test
//    public void firstSelectTest() {
//        Stack<StatementNode> stack = new Stack<>();
//        StatementNode select = new SelectNode();
//        select.addNode(new ColumnNode(new Token(Type.STAR, "*", 7)));
//        stack.push(select);
//        var expected = this.table;
//
//        var actual = new Translator(stack, table).translate();
//
//        Assertions.assertEquals(expected, actual);
//    }

//    @DisplayName("SELECT name")
//    @Test
//    public void secondSelectTest() {
//        Stack<StatementNode> stack = new Stack<>();
//        StatementNode select = new SelectNode();
//        select.addNode(new ColumnNode(new Token(Type.COLUMN, "name", 7)));
//        stack.push(select);
//        List<Map<String, Object>> expected = new ArrayList<>();
//        for(var row: table) {
//            Map<String, Object> newRow = new HashMap<>();
//            for (var item: row.entrySet()) {
//                if("name".equals(item.getKey())) {
//                    newRow.put("name", item.getValue());
//                }
//            }
//            expected.add(newRow);
//        }
//
//        var actual = new Translator(stack, table).translate();
//
//        Assertions.assertEquals(expected, actual);
//    }

//    @DisplayName("SELECT name, age")
//    @Test
//    public void thirdSelectTest() {
//        Stack<StatementNode> stack = new Stack<>();
//        StatementNode select = new SelectNode();
//        select.addNode(new ColumnNode(new Token(Type.COLUMN, "name", 7)));
//        select.addNode(new ColumnNode(new Token(Type.COLUMN, "age", 13)));
//        stack.push(select);
//        List<Map<String, Object>> expected = new ArrayList<>();
//        for(var row: table) {
//            Map<String, Object> newRow = new HashMap<>();
//            for (var item: row.entrySet()) {
//                if("name".equals(item.getKey())) {
//                    newRow.put("name", item.getValue());
//                }
//                if("age".equals(item.getKey())) {
//                    newRow.put("age", item.getValue());
//                }
//            }
//            expected.add(newRow);
//        }
//
//        var actual = new Translator(stack, table).translate();
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Disabled
    @DisplayName("DELETE")
    @Test
    public void firstDeleteTest() {
        Stack<StatementNode> stack = new Stack<>();
        DeleteNode delete = new DeleteNode();

    }

    @Disabled
    @DisplayName("DELETE WHERE name='Munir'")
    @Test
    public void secondDeleteTest() {

    }

//    @DisplayName("INSERT VALUES name = 'Tolya' , age=17")
//    @Test
//    public void firstInsertTest() {
//        Stack<StatementNode> stack = new Stack<>();
//        InsertNode insert = new InsertNode();
//        BinaryOperatorNode binary = new BinaryOperatorNode();
//        binary.setLeft(new ColumnNode(new Token(Type.COLUMN, "name", 14)));
//        binary.setOperator(new Token(Type.OPERATOR, "=", 8));
//        binary.setRight(new StringNode(new Token(Type.STRING, "Tolya", 10)));
//        insert.addNode(binary);
//        binary = new BinaryOperatorNode();
//        binary.setLeft(new ColumnNode(new Token(Type.COLUMN, "age", 20)));
//        binary.setOperator(new Token(Type.OPERATOR, "=", 24));
//        binary.setRight(new StringNode(new Token(Type.INTEGER, 17, 25)));
//        insert.addNode(binary);
//        stack.push(insert);
//
//        List<Map<String, Object>> expected = new ArrayList<>(1);
//        Map<String, Object> newRow = new HashMap<>();
//        newRow.put("name", "Tolya");
//        newRow.put("age", 17);
//        expected.add(newRow);
//
//
//        var actual = new Translator(stack, table).translate();
//
//        Assertions.assertEquals(expected, actual);
//    }
}
