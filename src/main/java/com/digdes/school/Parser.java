package com.digdes.school;

import com.digdes.school.ast.*;
import java.util.*;

public class Parser {
    private List<Token> tokens;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public Stack<Node> parse() throws Exception{
        Stack<Node> statementNodes = new Stack<>();
        if(position < tokens.size()) {
            var currentToken = require(Type.KEYWORD);
            switch ((String)currentToken.getValue()) {
                case "SELECT":
                    SelectNode select = parseSelect();
                    statementNodes.push(select);
                    break;
                case "INSERT":
                    InsertNode insert = parseInsert();
                    statementNodes.push(insert);
                    break;
                case "DELETE":

                default:
                    UpdateNode update = parseUpdate();
                    statementNodes.push(update);
            }
            currentToken = match(Type.KEYWORD);
            if("WHERE".equals(currentToken.getValue())) {
                WhereNode where = parseWhere();
                statementNodes.push(where);
            }
        }
        return statementNodes;
    }

    private WhereNode parseWhere() throws Exception {
        while(true) {
            BinaryOperatorNode left = parseBinaryOperation();
            var cur = match(Type.LOGICAL_OPERATOR);
            if (cur == null) {
                break;
            }
            BinaryOperatorNode right = parseBinaryOperation();
            BinaryOperatorNode binary = new BinaryOperatorNode(left, cur, right);

        }
    }

    private BinaryOperatorNode parseBinaryOperation() throws Exception{
        var left = require(Type.COLUMN);
        var operator = require(Type.OPERATOR);
        var right = require(Type.STRING, Type.DOUBLE, Type.INTEGER, Type.BOOLEAN);
        BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode();
        binaryOperatorNode.setLeft(new ColumnNode(left));
        binaryOperatorNode.setOperator(operator);
        switch (right.getType()) {
            case INTEGER -> binaryOperatorNode.setRight(new IntegerNode(right));
            case DOUBLE -> binaryOperatorNode.setRight(new DoubleNode(right));
            case STRING -> binaryOperatorNode.setRight(new StringNode(right));
            default -> binaryOperatorNode.setRight(new BooleanNode(right));
        }
        return binaryOperatorNode;
    }

    private UpdateNode parseUpdate() throws Exception {
        UpdateNode updateNode = new UpdateNode();
        require(Type.KEYWORD);
        while(true) {
            BinaryOperatorNode binaryOperatorNode = parseAssignOperation();
            updateNode.addNode(binaryOperatorNode);
            if(match(Type.COMMA) == null) {
                break;
            }
        }
        position--;
        return updateNode;
    }

    private InsertNode parseInsert() throws Exception{
        InsertNode insertNode = new InsertNode();
        require(Type.KEYWORD);
        while(true)
        {
            BinaryOperatorNode binaryOperatorNode = parseAssignOperation();
            insertNode.addNode(binaryOperatorNode);
            if(match(Type.COMMA) == null) {
                break;
            }
        }
        position--;
        return insertNode;
    }

    private SelectNode parseSelect() throws Exception {
        SelectNode selectNode = new SelectNode();
        var currentToken = require(Type.COLUMN, Type.STAR);
        if(currentToken.getType() == Type.STAR) {
            selectNode.addNode(new ColumnNode(currentToken));
            return selectNode;
        }

        while(true) {
            selectNode.addNode(new ColumnNode(currentToken));
            currentToken = match(Type.COMMA, Type.KEYWORD);
            if(currentToken==null || currentToken.getType() == Type.KEYWORD) {
                break;
            }
            currentToken = require(Type.COLUMN);
        }
        position--;
        return selectNode;
    }

    private BinaryOperatorNode parseAssignOperation() throws Exception {
        var binary = parseBinaryOperation();
        if(!"=".equals(binary.getOperator().getValue())){
            throw new Exception(String.format("На позиции %d ожидается '='"));
        }
        return binary;
    }

    private Token match(Type ...expectedType) {
        if(position < tokens.size()) {
            var currentToken = tokens.get(position);
            if(Arrays.stream(expectedType)
                    .anyMatch(x -> x.name().equals(currentToken.getType().name())))
            {
                position += 1;
                return currentToken;
            }
        }
        return null;
    }

    private Token require(Type ...expectedType) throws Exception{
        var token = match(expectedType);
        if(token == null) {
            throw new Exception(String.format("На позиции %d ожидается %s", position, expectedType[0].name()));
        }
        return token;
    }
}
