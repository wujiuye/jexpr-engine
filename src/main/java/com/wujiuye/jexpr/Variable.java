package com.wujiuye.jexpr;

import java.util.Map;

/**
 * 变量名
 *
 * @author wujiuye
 */
public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        return true;
    }
}
