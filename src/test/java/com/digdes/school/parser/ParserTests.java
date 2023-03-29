package com.digdes.school.parser;

import com.digdes.school.Lexer;
import com.digdes.school.Parser;
import com.digdes.school.Token;
import com.digdes.school.Type;
import com.digdes.school.ast.*;
import org.junit.jupiter.api.*;

import java.util.Stack;

public class ParserTests {
    private Parser parser;
    private static Lexer lexer;

    @BeforeAll
    public static void setLexer() {
        lexer = new Lexer();
    }

    @AfterEach
    public void clearLexerTokens() {
        lexer.reset();
    }

    @DisplayName("SELECT *")
    @Test
    public void firstSelectTest() throws Exception {
        Stack<Node> expected = new Stack<>();
        SelectNode select = new SelectNode();
        select.addNode(new ColumnNode(new Token(Type.STAR, "*", 7)));
        expected.add(select);

        lexer.analys("SELECT *");
        parser = new Parser(lexer.getTokens());
        var actual = parser.parse();

        Assertions.assertEquals(expected, actual);
    }


    @DisplayName("SELECT name, age")
    @Test
    public void secondSelectTest() throws Exception{
        Stack<Node> expected = new Stack<>();
        SelectNode select = new SelectNode();
        select.addNode(new ColumnNode(new Token(Type.COLUMN, "name", 7)));
        select.addNode(new ColumnNode(new Token(Type.COLUMN, "age", 13)));
        expected.push(select);

        lexer.analys("SELECT name, age");
        parser = new Parser(lexer.getTokens());
        var actual = parser.parse();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("INSERT VALUES name = 'Munir' , age=24")
    @Test
    public void firstInsertTest() throws Exception{
        Stack<Node> expected = new Stack<>();
        InsertNode insert = new InsertNode();
        BinaryOperatorNode binary1 = new BinaryOperatorNode(
                new ColumnNode(new Token(Type.COLUMN, "name", 14)),
                new Token(Type.OPERATOR, "=", 19),
                new StringNode(new Token(Type.STRING, "Munir", 21))
        );
        BinaryOperatorNode binary2 = new BinaryOperatorNode(
                new ColumnNode(new Token(Type.COLUMN, "age", 31)),
                new Token(Type.OPERATOR, "=", 34),
                new IntegerNode(new Token(Type.INTEGER, 24, 35))
        );
        insert.addNode(binary1);
        insert.addNode(binary2);
        expected.push(insert);

        lexer.analys("INSERT VALUES name = 'Munir' , age=24");
        parser = new Parser(lexer.getTokens());
        var actual = parser.parse();

        Assertions.assertEquals(expected, actual);
    }


    @DisplayName("UPDATE VALUES name = 'abc',age=11")
    @Test
    public void firstUpdateTest() throws Exception {
        Stack<Node> expected = new Stack<>();
        UpdateNode update = new UpdateNode();
        BinaryOperatorNode binary1 = new BinaryOperatorNode(
                new ColumnNode(new Token(Type.COLUMN, "name", 14)),
                new Token(Type.OPERATOR, "=", 19),
                new StringNode(new Token(Type.STRING, "abc", 21))
        );
        BinaryOperatorNode binary2 = new BinaryOperatorNode(
                new ColumnNode(new Token(Type.COLUMN, "age", 27)),
                new Token(Type.OPERATOR, "=", 30),
                new IntegerNode(new Token(Type.INTEGER, 11, 31))
        );
        update.addNode(binary1);
        update.addNode(binary2);
        expected.push(update);

        lexer.analys("UPDATE VALUES name = 'abc',age=11");
        parser = new Parser(lexer.getTokens());
        var actual = parser.parse();

        Assertions.assertEquals(expected, actual);
    }
}










