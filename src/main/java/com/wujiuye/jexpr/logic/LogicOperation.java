package com.wujiuye.jexpr.logic;

import com.wujiuye.jexpr.Expression;
import com.wujiuye.jexpr.Operation;

import java.util.Stack;

/**
 * 逻辑运算符
 *
 * @author wujiuye
 */
public abstract class LogicOperation extends Operation {

    public LogicOperation(String symbol) {
        super(symbol);
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        // 弹出左边表达式
        Expression left = stack.pop();
        // 解析右侧表达式
        int i = findNextExpression(tokens, pos + 1, stack);
        Expression right = stack.pop();

        // 生成执行and判断逻辑的新表达式，放入栈中
        this.leftOperand = left;
        this.rightOperand = right;
        stack.push(this);
        return i;
    }

}
