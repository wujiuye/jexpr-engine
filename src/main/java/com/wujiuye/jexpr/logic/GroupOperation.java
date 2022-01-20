package com.wujiuye.jexpr.logic;

import com.wujiuye.jexpr.Expression;
import com.wujiuye.jexpr.Operation;

import java.util.Map;
import java.util.Stack;

/**
 * @author wujiuye
 */
public class GroupOperation extends Operation {

    public GroupOperation() {
        super(null);
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        Stack<Expression> subStack = new Stack<>();
        Expression expression;
        int i = pos;
        do {
            // 弹出右侧表达式
            i = findNextExpression(tokens, i + 1, subStack);
            expression = subStack.peek();
        } while (expression != null && !(expression instanceof GroupCloseOperation));
        subStack.pop(); // 去掉GroupCloseOperation
        leftOperand = null; // 无需左边表达式
        rightOperand = subStack.pop();
        if (subStack.size() != 0) {
            throw new IllegalArgumentException("expression in () parse fail.");
        }
        stack.push(this);
        return i;
    }

    @Override
    public Operation copy() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        return rightOperand.interpret(bindings);
    }

}
