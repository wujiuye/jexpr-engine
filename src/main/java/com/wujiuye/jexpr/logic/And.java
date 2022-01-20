package com.wujiuye.jexpr.logic;

import java.util.Map;

/**
 * And
 *
 * @author wujiuye
 */
public class And extends LogicOperation {
    public And() {
        super("AND");
    }

    public And copy() {
        return new And();
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        return leftOperand.interpret(bindings) && rightOperand.interpret(bindings);
    }
}
