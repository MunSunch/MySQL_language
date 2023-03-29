package com.digdes.school;

import com.digdes.school.ast.*;

import java.util.*;

public class Translator {
    Stack<Node> stack;
    List<Map<String, Object>> table;

    public Translator(Stack<Node> stack, List<Map<String, Object>> table) {
        this.stack = stack;
        this.table = table;
    }

    public List<Map<String, Object>> translate() {
        List<Map<String, Object>> result = new ArrayList<>();
        while(!stack.isEmpty()) {
            Node Node = (StatementNode) stack.pop();
            if(Node instanceof WhereNode) {
                translateWhere(Node, result);
            }
            if(Node instanceof InsertNode) {
                translateInsert(Node, result);
            } else if(Node instanceof SelectNode) {
//                translateSelect(Node, result);
            } else if(Node instanceof DeleteNode) {
                translateDelete();
            } else if(Node instanceof UpdateNode) {
                translateUpdate(Node, result);
            }
        }
        return result;
    }

    private void translateUpdate(Node Node, List<Map<String, Object>> result) {
        UpdateNode update = (UpdateNode) Node;
        var nodes = update.getNodes();
        while(!nodes.isEmpty()) {

        }
    }


    private void translateDelete() {

    }

    private void translateWhere(Node Node, List<Map<String, Object>> result) {

    }

//    private void translateSelect(Node Node, List<Map<String, Object>> result) {
//        if(Node.nodes.size() == 1) {
//            ColumnNode columnNode = (ColumnNode) Node.nodes.get(0);
//            if("*".equals(columnNode.token.getValue())){
//                for(var row: table) {
//                    result.add(new HashMap<>(row));
//                }
//                return;
//            }
//        }
//        List<String> columns = new ArrayList<>();
//        for (var column : Node.nodes) {
//            ColumnNode columnNode = (ColumnNode) column;
//            String value = (String) columnNode.getToken().getValue();
//            columns.add(value);
//        }
//        for(var row: table) {
//            Map<String, Object> item = new HashMap<>();
//            for(var column: columns) {
//                item.put(column, row.get(column));
//            }
//            result.add(item);
//        }
//    }

    private void translateInsert(Node Node, List<Map<String, Object>> result) {
        InsertNode insert = (InsertNode) Node;
        var nodes = insert.getNodes();
        Map<String, Object> row = new HashMap<>();
        for(Node node: nodes) {
            BinaryOperatorNode binaryOperator = (BinaryOperatorNode) node;
            var column = (ColumnNode)binaryOperator.getLeft();
            Node literal = binaryOperator.getRight();
            if(literal instanceof StringNode) {
                var value = (StringNode) literal;
                row.put((String) column.getToken().getValue(), value.getToken().getValue());
            } else if(literal instanceof IntegerNode) {
                var value = (IntegerNode) literal;
                row.put((String) column.getToken().getValue(), Integer.parseInt((String)value.getToken().getValue()));
            } else if(literal instanceof DoubleNode) {
                var value = (DoubleNode) literal;
                row.put((String) column.getToken().getValue(), Double.parseDouble((String) value.getToken().getValue()));
            } else {
                var value = (BooleanNode) literal;
                row.put((String) column.getToken().getValue(), Boolean.parseBoolean((String) value.getToken().getValue()));
            }
        }
        table.add(row);
        result.add(row);
    }
}
