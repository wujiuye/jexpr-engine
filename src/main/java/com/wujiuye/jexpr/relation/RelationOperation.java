package com.wujiuye.jexpr.relation;

import com.wujiuye.jexpr.Expression;
import com.wujiuye.jexpr.Operation;
import com.wujiuye.jexpr.ValueHelper;
import com.wujiuye.jexpr.Variable;

import java.util.Stack;

/**
 * 关系运算符
 *
 * @author wujiuye
 */
public abstract class RelationOperation extends Operation {

    public RelationOperation(String symbol) {
        super(symbol);
    }

    @Override
    public int parse(final String[] tokens, int pos, Stack<Expression> stack) {
        // pos 是等号的位置，取pos两边的表达式
        if (pos - 1 >= 0 && tokens.length >= pos + 1) {
            String var = tokens[pos - 1];
            // 左边是变量名
            this.leftOperand = new Variable(var);
            // 右边是值
            this.rightOperand = ValueHelper.parseValue(tokens[pos + 1]);
            // 将表达式放入栈顶
            stack.push(this);
            return pos + 1;
        }
        throw new IllegalArgumentException("Cannot assign value to variable");
    }

    /**
     * 值是否需要括号括起来
     *
     * @return
     */
    public boolean needBrackets() {
        return false;
    }

    /**
     * 表达式两边是否需要空格
     *
     * @return
     */
    public boolean needSpace() {
        return false;
    }

}
