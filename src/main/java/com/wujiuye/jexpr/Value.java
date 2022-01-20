package com.wujiuye.jexpr;

import java.util.Map;

/**
 * 变量值
 *
 * @author wujiuye
 */
public class Value<T> implements Expression {
    public T value;
    public Class<T> type;

    public Value(T value, Class<T> type) {
        this.value = value;
        this.type = type;
    }

    public T getValue() {
        return this.value;
    }

    public Class<T> getType() {
        return this.type;
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        return true;
    }

}
