package com.wujiuye.jexpr.relation;

import com.wujiuye.jexpr.*;

import java.util.Map;
import java.util.Stack;

/**
 * IN
 *
 * @author wujiuye
 */
public class IN extends RelationOperation {
    public IN() {
        super("IN");
    }

    @Override
    public IN copy() {
        return new IN();
    }

    @Override
    public int parse(String[] tokens, int pos, Stack<Expression> stack) {
        // pos 是等号的位置，取pos两边的表达式
        if (pos - 1 >= 0 && tokens.length >= pos + 1) {
            String var = tokens[pos - 1];
            // 左边是变量名
            this.leftOperand = new Variable(var);
            // 右边是值
            String valuesStr = tokens[pos + 1].trim();
            if (!valuesStr.startsWith("(") || !valuesStr.endsWith(")")) {
                throw new IllegalArgumentException("Cannot assign value to variable. need '()' by str:" + valuesStr);
            }
            valuesStr = valuesStr.substring(1, valuesStr.length() - 1);
            this.rightOperand = ValueHelper.parseArrayValue(valuesStr);
            // 将表达式放入栈顶
            stack.push(this);
            return pos + 1;
        }
        throw new IllegalArgumentException("Cannot assign value to variable");
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        // 变量名匹配
        Variable v = (Variable) this.leftOperand;
        Object obj = bindings.get(v.getName());
        if (obj == null) {
            return false;
        }
        // 值匹配
        ArrayValue<?> values = (ArrayValue<?>) this.rightOperand;
        for (Object value : values.getValue()) {
            if (ValueHelper.equalsValue(new Value(value, values.getType()), obj)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean needBrackets() {
        return true;
    }

    @Override
    public boolean needSpace() {
        return true;
    }

}
