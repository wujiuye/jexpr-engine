package com.wujiuye.jexpr;

import com.wujiuye.jexpr.logic.And;
import com.wujiuye.jexpr.logic.Not;
import com.wujiuye.jexpr.logic.Or;
import com.wujiuye.jexpr.relation.*;

import java.util.*;

/**
 * 表达式解析器
 *
 * @author wujiuye
 */
public class ExpressionParser {

    private static final Operations operations = Operations.INSTANCE;

    static {
        // 关系运算符
        operations.registerOperation(new Equals());
        operations.registerOperation(new NotEquals());
        operations.registerOperation(new IN());
        operations.registerOperation(new IsNull());
        operations.registerOperation(new IsNotNull());

        // 逻辑运算符
        operations.registerOperation(new And());
        operations.registerOperation(new Not());
        operations.registerOperation(new Or());
        operations.registerOperation(new Gt());
        operations.registerOperation(new Gte());
        operations.registerOperation(new Lt());
        operations.registerOperation(new Lte());
    }

    public static Expression fromString(String expr) {
        Stack<Expression> stack = new Stack<>();
        // 按空格分割
        String[] tokens = formatExpression(expr);
        // 遍历表达式
        for (int i = 0; i < tokens.length - 1; i++) {
            Operation op = operations.getOperation(tokens[i]);
            if (op != null) {
                op = op.copy();
                // seek [i] to [pos]
                i = op.parse(tokens, i, stack);
            }
        }
        return stack.pop();
    }

    private static String[] formatExpression(String expression) {
        List<String> expnArrays = new ArrayList<>();
        Set<String> logic = Operations.INSTANCE.allLogicOperation();
        Set<String> relation = Operations.INSTANCE.allRelationOperation();
        StringBuilder varOrValue = new StringBuilder();
        int stringFlag = 0;
        boolean relationNeedBrackets = false;
        out:
        for (int i = 0; i < expression.length(); ) {
            if (stringFlag == 2) {
                stringFlag = 0;
                appendToArray(expnArrays, varOrValue);
                varOrValue = new StringBuilder();
            }
            if (stringFlag == 0 && i > 0) {
                // 关系表达式匹配
                for (String expn : relation) {
                    if (i + expn.length() + 1 > expression.length()) {
                        continue;
                    }
                    String texpn;
                    String subStr;
                    if (((RelationOperation) Operations.INSTANCE.getOperation(expn)).needSpace()) { // IN和IS NULL关系表达式需要左右两个留空格
                        texpn = " " + expn + " ";
                        subStr = expression.substring(i - 1, i + expn.length() + 1);
                    } else {
                        texpn = expn;
                        subStr = expression.substring(i, i + expn.length());
                    }
                    if (subStr.equalsIgnoreCase(texpn)) { // 不区分大小写
                        i = i + expn.length();
                        appendToArray(expnArrays, varOrValue);
                        stringFlag = 0;
                        varOrValue = new StringBuilder();
                        expnArrays.add(expn);
                        relationNeedBrackets = ((RelationOperation) Operations.INSTANCE.getOperation(expn)).needBrackets();
                        continue out;
                    }
                }
                // 逻辑表达式匹配
                for (String expn : logic) {
                    String texpn = " " + expn + " "; // 逻辑表达式要求左右两边至少有一个空格
                    if (i + expn.length() + 1 > expression.length()) {
                        continue;
                    }
                    String subStr = expression.substring(i - 1, i + expn.length() + 1);
                    if (subStr.equalsIgnoreCase(texpn)) { // 不区分大小写
                        i = i + expn.length() + 1;
                        appendToArray(expnArrays, varOrValue);
                        stringFlag = 0;
                        varOrValue = new StringBuilder();
                        expnArrays.add(expn);
                        relationNeedBrackets = false;
                        continue out;
                    }
                }
            }
            // 如果当前是解析关系表达式的右边表达式，在没有遇到')'时，都当作字符串处理
            if (relationNeedBrackets && expression.charAt(i) != ')') {
                varOrValue.append(expression.charAt(i));
            }
            // 遇到"'"，则接下来的都当作字符串值解析
            else if (expression.charAt(i) == '\'') {
                varOrValue.append(expression.charAt(i));
                stringFlag++;
            }
            // '('，且非关系表达式，则为逻辑优先级所需
            else if (expression.charAt(i) == '(' && !relationNeedBrackets) {
                appendToArray(expnArrays, varOrValue);
                stringFlag = 0;
                varOrValue = new StringBuilder();
                expnArrays.add("(");
            }
            // ')'，且非关系表达式，则为逻辑优先级所需
            else if (expression.charAt(i) == ')' && !relationNeedBrackets) {
                appendToArray(expnArrays, varOrValue);
                stringFlag = 0;
                varOrValue = new StringBuilder();
                expnArrays.add(")");
            }
            // 字符
            else {
                varOrValue.append(expression.charAt(i));
            }
            i++;
        }
        appendToArray(expnArrays, varOrValue);
        return expnArrays.toArray(new String[0]);
    }

    private static void appendToArray(List<String> expnArrays, StringBuilder varOrValue) {
        String str = varOrValue.toString();
        str = str.trim();
        if (str.length() == 0) {
            return;
        }
        expnArrays.add(str);
    }

}
