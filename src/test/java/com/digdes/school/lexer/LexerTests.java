package com.digdes.school.lexer;

import com.digdes.school.Lexer;
import com.digdes.school.Token;
import com.digdes.school.Type;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class LexerTests {
    private Lexer lexer;

    {
        lexer = new Lexer();
    }

    @BeforeEach
    public void resetLexer() {
        lexer.reset();
    }

    @DisplayName("SELECT + *")
    @Test
    public void firstTestSelect() throws Exception{
        String command = "SELECT *";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "SELECT", 0));
        expected.add(new Token(Type.STAR, "*", 7));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("SELECT + COLUMNS")
    @Test
    public void secondTestSelect() throws Exception{
        String command = "SELECT name, surname, age";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "SELECT", 0));
        expected.add(new Token(Type.COLUMN, "name", 7));
        expected.add(new Token(Type.COMMA, ",", 11));
        expected.add(new Token(Type.COLUMN, "surname", 13));
        expected.add(new Token(Type.COMMA, ",", 20));
        expected.add(new Token(Type.COLUMN, "age", 22));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }


    @DisplayName("SELECT + * + CONDITION")
    @Test
    public void thirdTestSelect() throws Exception{
        String command = "SELECT * WHERE name = 'Munir'";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "SELECT", 0));
        expected.add(new Token(Type.STAR, "*", 7));
        expected.add(new Token(Type.KEYWORD, "WHERE", 9));
        expected.add(new Token(Type.COLUMN, "name", 15));
        expected.add(new Token(Type.OPERATOR, "=", 20));
        expected.add(new Token(Type.STRING, "'Munir'", 22));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("SELECT + COLUMNS + CONDITION + AND + CONDITION + OR + CONDITION")
    @Test
    public void fourthTestSelect() throws Exception{
        String command = "SELECT name, age WHERE name = 'Munir' AND age>12 OR isChild=false";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "SELECT", 0));
        expected.add(new Token(Type.COLUMN, "name", 7));
        expected.add(new Token(Type.COMMA, ",", 11));
        expected.add(new Token(Type.COLUMN, "age", 13));
        expected.add(new Token(Type.KEYWORD, "WHERE", 17));
        expected.add(new Token(Type.COLUMN, "name", 23));
        expected.add(new Token(Type.OPERATOR, "=", 28));
        expected.add(new Token(Type.STRING, "'Munir'", 30));
        expected.add(new Token(Type.LOGICAL_OPERATOR, "AND", 38));
        expected.add(new Token(Type.COLUMN, "age", 42));
        expected.add(new Token(Type.OPERATOR, ">", 45));
        expected.add(new Token(Type.INTEGER, "12", 46));
        expected.add(new Token(Type.LOGICAL_OPERATOR, "OR", 49));
        expected.add(new Token(Type.COLUMN, "isChild", 52));
        expected.add(new Token(Type.OPERATOR, "=", 59));
        expected.add(new Token(Type.BOOLEAN, "false", 60));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("INSERT")
    @Test
    public void firstTestInsert() throws Exception{
        String command = "INSERT VALUES lastName = 'Федоров' , id=3, age=40, active=true";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "INSERT", 0));
        expected.add(new Token(Type.KEYWORD, "VALUES", 7));
        expected.add(new Token(Type.COLUMN, "lastName", 14));
        expected.add(new Token(Type.OPERATOR, "=", 23));
        expected.add(new Token(Type.STRING, "'Федоров'", 25));
        expected.add(new Token(Type.COMMA, ",", 35));
        expected.add(new Token(Type.COLUMN, "id", 37));
        expected.add(new Token(Type.OPERATOR, "=", 39));
        expected.add(new Token(Type.INTEGER, "3", 40));
        expected.add(new Token(Type.COMMA, ",", 41));
        expected.add(new Token(Type.COLUMN, "age", 43));
        expected.add(new Token(Type.OPERATOR, "=", 46));
        expected.add(new Token(Type.INTEGER, "40", 47));
        expected.add(new Token(Type.COMMA, ",", 49));
        expected.add(new Token(Type.COLUMN, "active", 51));
        expected.add(new Token(Type.OPERATOR, "=", 57));
        expected.add(new Token(Type.BOOLEAN, "true", 58));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("UPDATE + WHERE")
    @Test
    public void firstTestUpdate() throws Exception{
        String command = "UPDATE VALUES active=false, cost=10.1 WHERE id=3";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "UPDATE", 0));
        expected.add(new Token(Type.KEYWORD, "VALUES", 7));
        expected.add(new Token(Type.COLUMN, "active", 14));
        expected.add(new Token(Type.OPERATOR, "=", 20));
        expected.add(new Token(Type.BOOLEAN, "false", 21));
        expected.add(new Token(Type.COMMA, ",", 26));
        expected.add(new Token(Type.COLUMN, "cost", 28));
        expected.add(new Token(Type.OPERATOR, "=", 32));
        expected.add(new Token(Type.DOUBLE, "10.1", 33));
        expected.add(new Token(Type.KEYWORD, "WHERE", 38));
        expected.add(new Token(Type.COLUMN, "id", 44));
        expected.add(new Token(Type.OPERATOR, "=", 46));
        expected.add(new Token(Type.INTEGER, "3", 47));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("UPDATE + WHERE")
    @Test
    public void secondTestUpdate() throws Exception{
        String command = "UPDATE VALUES active=true WHERE active=false";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "UPDATE", 0));
        expected.add(new Token(Type.KEYWORD, "VALUES", 7));
        expected.add(new Token(Type.COLUMN, "active", 14));
        expected.add(new Token(Type.OPERATOR, "=", 20));
        expected.add(new Token(Type.BOOLEAN, "true", 21));
        expected.add(new Token(Type.KEYWORD, "WHERE", 26));
        expected.add(new Token(Type.COLUMN, "active", 32));
        expected.add(new Token(Type.OPERATOR, "=", 38));
        expected.add(new Token(Type.BOOLEAN, "false", 39));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }

    @DisplayName("DELETE + WHERE")
    @Test
    public void firstTestDelete() throws Exception{
        String command = "DELETE WHERE id=3";
        List<Token> expected = new ArrayList<>();
        expected.add(new Token(Type.KEYWORD, "DELETE", 0));
        expected.add(new Token(Type.KEYWORD, "WHERE", 7));
        expected.add(new Token(Type.COLUMN, "id", 13));
        expected.add(new Token(Type.OPERATOR, "=", 15));
        expected.add(new Token(Type.INTEGER, "3", 16));

        lexer.analys(command);
        var actual = lexer.getTokens();

        Assertions.assertEquals(expected, actual);
    }
}