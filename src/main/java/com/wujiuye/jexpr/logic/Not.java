package com.wujiuye.jexpr.logic;

import com.wujiuye.jexpr.Expression;

import java.util.Map;
import java.util.Stack;

/**
 * Not
 *
 * @author wujiuye
 */
public class Not extends LogicOperation {
    public Not() {
        super("NOT");
    }

    public Not copy() {
        return new Not();
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        // NOT 是没有左边表达式的
        // 先解析右侧表达式
        int i = findNextExpression(tokens, pos + 1, stack);
        // 弹出右侧表达式
        this.rightOperand = stack.pop();
        // 生成新的表达式
        stack.push(this);
        return i;
    }

    @Override
    public boolean interpret(final Map<String, ?> bindings) {
        return !this.rightOperand.interpret(bindings);
    }
}
