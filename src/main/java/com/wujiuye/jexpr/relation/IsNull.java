package com.wujiuye.jexpr.relation;

import com.wujiuye.jexpr.Expression;
import com.wujiuye.jexpr.Variable;

import java.util.Map;
import java.util.Stack;

/**
 * is null
 *
 * @author wujiuye
 */
public class IsNull extends RelationOperation {

    public IsNull() {
        super("IS NULL");
    }

    @Override
    public IsNull copy() {
        return new IsNull();
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        // pos 是等号的位置，取pos左边的表达式
        if (pos - 1 >= 0) {
            String var = tokens[pos - 1];
            // 左边是变量名
            this.leftOperand = new Variable(var);
            // 将表达式放入栈顶
            stack.push(this);
            return pos;
        }
        throw new IllegalArgumentException("Cannot assign value to variable");
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        Variable v = (Variable) this.leftOperand;
        Object obj = bindings.get(v.getName());
        return obj == null;
    }

    @Override
    public boolean needSpace() {
        return true;
    }

}
