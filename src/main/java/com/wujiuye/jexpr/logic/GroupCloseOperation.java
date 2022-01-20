package com.wujiuye.jexpr.logic;

import com.wujiuye.jexpr.Expression;
import com.wujiuye.jexpr.Operation;

import java.util.Map;
import java.util.Stack;

/**
 * 处理()，优先级
 *
 * @author wujiuye
 */
public class GroupCloseOperation extends LogicOperation {

    public GroupCloseOperation() {
        super(")");
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        stack.push(this);
        return pos;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Operation copy() {
        return new GroupCloseOperation();
    }

}
