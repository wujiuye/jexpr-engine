package com.wujiuye.jexpr.logic;

import java.util.Map;

/**
 * Or
 *
 * @author wujiuye
 */
public class Or extends LogicOperation {
    public Or() {
        super("OR");
    }

    public Or copy() {
        return new Or();
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        return leftOperand.interpret(bindings) || rightOperand.interpret(bindings);
    }

}
