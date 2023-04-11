package com.digdes.school;

import com.digdes.school.ast.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Translator {
    Stack<Node> stack;
    List<Map<String, Object>> table;

    public Translator(Stack<Node> stack, List<Map<String, Object>> table) {
        this.stack = stack;
        this.table = table;
    }

    public Translator(List<Map<String, Object>> table) {
        this.table = table;
    }

    public List<Map<String, Object>> translate(Stack<Node> stack) throws Exception {
        this.stack = stack;

        List<Map<String, Object>> result = new ArrayList<>();
        List<Integer> indexes = null;
        while(!stack.isEmpty()) {
            Node node = stack.pop();
            if(node instanceof WhereNode) {
                indexes = translateWhere((WhereNode) node);
                continue;
            }
            if(node instanceof InsertNode) {
                translateInsert((InsertNode) node, result);
            } else if(node instanceof SelectNode) {
                translateSelect((SelectNode) node, result, indexes);
            } else if(node instanceof DeleteNode) {
                translateDelete(result, indexes);
            } else if(node instanceof UpdateNode) {
                translateUpdate((UpdateNode) node, result, indexes);
            }
        }
        return result;
    }

    private List<Integer> translateWhere(WhereNode where) throws Exception {
        BinaryOperatorNode root = where.getRoot();
        if(root.getOperator().getType() != Type.LOGICAL_OPERATOR) {
            return translateCondition(root);
        }
        return translateLogical(root);
    }

    private List<Integer> translateLogical(BinaryOperatorNode root) throws Exception {
        BinaryOperatorNode leftOperand = (BinaryOperatorNode)root.getLeft();

        if("AND".equals(root.getOperator().getValue())) {
            var resultLeftCondition = translateCondition((BinaryOperatorNode) root.getLeft());
            var resultRightCondition = translateCondition((BinaryOperatorNode) root.getRight());
            resultLeftCondition.retainAll(resultRightCondition);
            return resultLeftCondition;
        } else {
            var resultLeftCondition = translateCondition((BinaryOperatorNode) root.getLeft());
            var resultRightCondition = translateCondition((BinaryOperatorNode) root.getRight());
            resultLeftCondition.addAll(resultRightCondition);
            return new ArrayList<>(new HashSet<>(resultLeftCondition));
        }
    }

    private List<Integer> translateCondition(BinaryOperatorNode root) throws Exception {
        String column = (String) ((ColumnNode)root.getLeft()).getToken().getValue();
        String operator = (String) root.getOperator().getValue();

        if(root.getRight() instanceof LongNode) {
            long value = Long.parseLong((String) ((LongNode) root.getRight()).getToken().getValue());
            return doArithmeticOperationLong(column, operator, value);
        } else if(root.getRight() instanceof StringNode) {
            String value = (String) ((StringNode) root.getRight()).getToken().getValue();
            value = value.substring(1, value.length() - 1);
            return doStringOperation(column, operator, value);
        } else if(root.getRight() instanceof DoubleNode) {
            double value = (double) ((DoubleNode) root.getRight()).getToken().getValue();
            return doArithmeticOperationDouble(column, operator, value);
        } else {
            boolean value = Boolean.parseBoolean((String)((BooleanNode) root.getRight()).getToken().getValue());
            return doBooleanOperation(column, operator, value);
        }
    }

    private List<Integer> doArithmeticOperationLong(String column, String operator, Long value) throws Exception {
        Stream<Map<String, Object>> stream = table.stream();
        switch (operator) {
            case "=":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) == value);
                break;
            case "!=":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) != value);
                break;
            case ">":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) > value);
                break;
            case "<":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) < value);
                break;
            case ">=":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) >= value);
                break;
            case "<=":
                stream = stream.filter((Map<String, Object> x) -> (long)x.get(column) <= value);
                break;
            default:
                throw new Exception("Данная арифметическая операция не поддерживается!");
        }
        return stream.map(x -> table.indexOf(x))
                .collect(Collectors.toList());
    }

    private List<Integer> doArithmeticOperationDouble(String column, String operator, Double value) throws Exception {
//        Stream<Map<String, Object>> stream = table.stream();
//        switch (operator) {
//            case "=":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) == value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//            case "!=":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) != value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//            case ">":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) > value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//            case "<":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) < value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//            case ">=":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) >= value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//            case "<=":
//                return stream.filter((Map<String, Object> x) -> {
//                            return ((double)(x.get(column)) <= value);
//                        }).map(x -> table.indexOf(x))
//                        .collect(Collectors.toList());
//        }
//        throw new Exception("Данная арифметическая операция не поддерживается с числом!");
        return null;
    }

    private List<Integer> doBooleanOperation(String column, String operator, boolean value) throws Exception {
        Stream<Map<String, Object>> stream = table.stream();
        switch (operator) {
            case "=":
                stream = stream.filter((Map<String, Object> x) -> {
                    return ((boolean)x.get(column)) == value;
                });
                break;
            case "!=":
                stream = stream.filter((Map<String, Object> x) -> {
                    return ! ((boolean)x.get(column)) == value;
                });
                break;
            default:
                throw new Exception("Данная логическая операция не поддерживается с булевым значением!");
        }
        return stream.map(table::indexOf)
                .collect(Collectors.toList());
    }

    private List<Integer> doStringOperation(String column, String operator, String value) throws Exception {
        Stream<Map<String, Object>> stream = table.stream();
        switch (operator.toLowerCase()) {
            case "=":
                stream = stream.filter((Map<String, Object> x) -> {
                            return value.equals(x.get(column));
                });
                break;
            case "!=":
                stream = stream.filter((Map<String, Object> x) -> {
                        return !value.equals(x.get(column));
                });
                break;
            case "like":
                stream = stream.filter((Map<String, Object> x) -> {
                   return doRegular((String)x.get(column), value);
                });
                break;
            case "ilike":
                stream = stream.filter((Map<String, Object> x) -> {
                    return doRegular(((String)x.get(column)).toUpperCase(), value.toUpperCase());
                });
                break;
            default:
                throw new Exception("Данная операция не поддерживается со строками!");
        }
        return stream.map(x -> table.indexOf(x))
                .collect(Collectors.toList());
    }

    private boolean doRegular(String s, String value) {
        value = value.replace("%", "\\S");
        return s.matches(value);
    }

    private void translateUpdate(Node Node, List<Map<String, Object>> result, List<Integer> indexes) {
        UpdateNode update = (UpdateNode) Node;
        var nodes = update.getNodes();
        if(indexes == null) {
            for (int i = 0; i < table.size(); i++) {
                var row = table.get(i);
                translateUpdateCondition(nodes, row);
                result.add(new HashMap<>(row));
            }
        } else {
            for(int index: indexes) {
                var row = table.get(index);
                translateUpdateCondition(nodes, row);
                result.add(new HashMap<>(row));
            }
        }
    }

    private void translateUpdateCondition(List<Node> nodes, Map<String, Object> row) {
        for (Node node: nodes) {
            String column = (String) ((ColumnNode)((BinaryOperatorNode)node).getLeft()).getToken().getValue();
            Node rightNode = ((BinaryOperatorNode)node).getRight();
            if(rightNode instanceof StringNode) {
                String value = (String)((StringNode)rightNode).getToken().getValue();
                value = value.substring(1, value.length()-1);
                row.replace(column, value);
            } else if(rightNode instanceof LongNode) {
                long value = Long.parseLong((String) ((LongNode)rightNode).getToken().getValue());
                row.replace(column, value);
            } else if(rightNode instanceof DoubleNode) {
                double value = Double.parseDouble((String) ((DoubleNode)rightNode).getToken().getValue());
                row.replace(column, value);
            } else if(rightNode instanceof BooleanNode) {
                boolean value = Boolean.parseBoolean((String) ((BooleanNode)rightNode).getToken().getValue());
                row.replace(column, value);
            }
        }
    }


    private void translateDelete(List<Map<String, Object>> result, List<Integer> indexes) {
        if(indexes == null) {
            for (int i = 0; i < table.size(); i++) {
                Map<String, Object> copyRow = new HashMap<>(table.get(i));
                result.add(copyRow);
            }
            table.clear();
        } else {
            for (int i = 0; i < indexes.size(); i++) {
                var deleteRow = table.get(indexes.get(i));
                result.add(deleteRow);
            }
            for (int i = 0; i < result.size(); i++) {
                table.remove(result.get(i));
            }
        }
    }

    private void translateSelect(SelectNode select, List<Map<String, Object>> result, List<Integer> indexes) {
        if(select.getNodes().size() == 1) {
            ColumnNode columnNode = (ColumnNode) select.getNodes().get(0);
            if("*".equals(columnNode.getToken().getValue())){
                if(indexes == null) {
                    for (var row : table) {
                        result.add(new HashMap<>(row));
                    }
                } else {
                    for (int i = 0; i < indexes.size(); i++) {
                        result.add(new HashMap<>(table.get(indexes.get(i))));
                    }
                }
                return;
            }
        }

        List<String> columns = new ArrayList<>();
        for (var column : select.getNodes()) {
            ColumnNode columnNode = (ColumnNode) column;
            String value = (String) columnNode.getToken().getValue();
            columns.add(value);
        }
        if(indexes == null) {
            for (var row : table) {
                Map<String, Object> item = new HashMap<>();
                for (var column : columns) {
                    item.put(column, row.get(column));
                }
                result.add(item);
            }
        } else {
            for (int i = 0; i < indexes.size(); i++) {
                Map<String, Object> item = new HashMap<>();
                for (var column : columns) {
                    item.put(column, table.get(indexes.get(i)).get(column));
                }
                result.add(item);
            }
        }
    }

    private void translateInsert(Node Node, List<Map<String, Object>> result) {
        InsertNode insert = (InsertNode) Node;
        var nodes = insert.getNodes();
        Map<String, Object> row = new HashMap<>();
        for(Node node: nodes) {
            BinaryOperatorNode binaryOperator = (BinaryOperatorNode) node;
            var column = (ColumnNode)binaryOperator.getLeft();
            Node literal = binaryOperator.getRight();
            if(literal instanceof StringNode) {
                String value = (String) ((StringNode) literal).getToken().getValue();
                value = value.substring(1, value.length()-1);
                row.put((String) column.getToken().getValue(), value);
            } else if(literal instanceof LongNode) {
                var value = (LongNode) literal;
                row.put((String) column.getToken().getValue(), Long.parseLong((String)value.getToken().getValue()));
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
