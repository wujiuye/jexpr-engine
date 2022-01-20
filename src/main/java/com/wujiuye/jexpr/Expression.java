package com.wujiuye.jexpr;

import java.util.Map;

/**
 * 表达式
 *
 * @author wujiuye
 */
public interface Expression {

    /**
     * @param bindings 变量与值的绑定，即用于替换表达式中变量的值
     * @return
     */
    boolean interpret(final Map<String, ?> bindings);

}
