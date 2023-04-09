package com.digdes.school;

import com.digdes.school.ast.*;
import java.util.*;

public class Parser {
    private List<Token> tokens;
    private int position;
    private Map<Type, Integer> priorityOperations;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.priorityOperations = new HashMap<>();
        priorityOperations.put(Type.OPERATOR, 1);
        priorityOperations.put(Type.LOGICAL_OPERATOR, 2);
    }

    public Stack<Node> parse() throws Exception{
        Stack<Node> statementNodes = new Stack<>();
        Token currentToken;
        if(position < tokens.size()) {
            currentToken = require(Type.KEYWORD);
            switch ((String)currentToken.getValue()) {
                case "SELECT":
                    SelectNode select = parseSelect();
                    statementNodes.push(select);
                    break;
                case "INSERT":
                    InsertNode insert = parseInsert();
                    statementNodes.push(insert);
                    return statementNodes;
                case "DELETE":
                    DeleteNode delete = parseDelete();
                    statementNodes.push(delete);
                    break;
                case "UPDATE":
                    UpdateNode update = parseUpdate();
                    statementNodes.push(update);
                    break;
                default:
                    throw new Exception("Syntax Error!");
            }
            if(position == tokens.size()) {
                return statementNodes;
            }
            currentToken = match(Type.KEYWORD);
            if("WHERE".equals(currentToken.getValue())) {
                position--;
                WhereNode where = parseWhere();
                statementNodes.push(where);
            } else {
                throw new Exception("Syntax Error!");
            }
        }
        return statementNodes;
    }

    private DeleteNode parseDelete() {
        return new DeleteNode();
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

    private UpdateNode parseUpdate() throws Exception {
        UpdateNode updateNode = new UpdateNode();
        var currentToken = require(Type.KEYWORD);
        if(!"VALUES".equals(currentToken.getValue())) {
            throw new Exception("Syntax Error!");
        }
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
        var currentToken = require(Type.KEYWORD);
        if(!"VALUES".equals(currentToken.getValue())) {
            throw new Exception("Syntax Error!");
        }
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

    private BinaryOperatorNode parseAssignOperation() throws Exception {
        var binary = parseBinaryOperation();
        if(!"=".equals(binary.getOperator().getValue())){
            throw new Exception("Syntax Error!");
        }
        return binary;
    }

    private BinaryOperatorNode parseBinaryOperation() throws Exception{
        var left = require(Type.COLUMN);
        var operator = require(Type.OPERATOR);
        var right = require(Type.STRING, Type.DOUBLE, Type.LONG, Type.BOOLEAN);
        BinaryOperatorNode binaryOperatorNode = new BinaryOperatorNode();
        binaryOperatorNode.setLeft(new ColumnNode(left));
        binaryOperatorNode.setOperator(operator);
        switch (right.getType()) {
            case LONG -> binaryOperatorNode.setRight(new LongNode(right));
            case DOUBLE -> binaryOperatorNode.setRight(new DoubleNode(right));
            case STRING -> binaryOperatorNode.setRight(new StringNode(right));
            default -> binaryOperatorNode.setRight(new BooleanNode(right));
        }
        return binaryOperatorNode;
    }

    private WhereNode parseWhere() throws Exception {
        Stack<BinaryOperatorNode> temp = new Stack<>();
        Token currentToken = require(Type.KEYWORD);
        if(!"WHERE".equals(currentToken.getValue())) {
            throw new Exception("Syntax Error!");
        }
        while(position < tokens.size()) {
            currentToken = require(Type.COLUMN, Type.LOGICAL_OPERATOR);
            BinaryOperatorNode binaryOperatorNode;
            if(currentToken.getType() == Type.COLUMN) {
                position--;
                binaryOperatorNode = parseBinaryOperation();
            } else if(currentToken.getType() == Type.LOGICAL_OPERATOR) {
                binaryOperatorNode = new BinaryOperatorNode();
                binaryOperatorNode.setOperator(currentToken);
            } else {
                throw new Exception("Syntax error!");
            }

            if(temp.isEmpty()) {
                temp.push(binaryOperatorNode);
                continue;
            }
            if(priorityOperations.get(temp.peek().getOperator().getType())
                    > priorityOperations.get(binaryOperatorNode.getOperator().getType()) )
            {
                BinaryOperatorNode logicalBinaryOperator = temp.pop();
                logicalBinaryOperator.setLeft(temp.pop());
                logicalBinaryOperator.setRight(binaryOperatorNode);
                temp.push(logicalBinaryOperator);
            } else {
                temp.push(binaryOperatorNode);
            }
        }
        return new WhereNode(temp.pop());
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