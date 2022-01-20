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
public class GroupOpenOperation extends LogicOperation {

    public GroupOpenOperation() {
        super("(");
    }

    @Override
    public Operation copy() {
        return new GroupOpenOperation();
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        GroupOperation groupOperation = new GroupOperation();
        int i = groupOperation.parse(tokens, pos, stack);
        this.rightOperand = groupOperation;
        return i;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        throw new UnsupportedOperationException();
    }

}
