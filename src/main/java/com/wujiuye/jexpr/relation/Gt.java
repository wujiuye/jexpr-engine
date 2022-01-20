package com.wujiuye.jexpr.relation;

import com.wujiuye.jexpr.Value;
import com.wujiuye.jexpr.ValueHelper;
import com.wujiuye.jexpr.Variable;

import java.util.Map;

/**
 * >
 *
 * @author wujiuye
 */
public class Gt extends RelationOperation {
    public Gt() {
        super(">");
    }

    @Override
    public Gt copy() {
        return new Gt();
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
        Value<?> value = (Value<?>) this.rightOperand;
        return ValueHelper.compareTo(obj, value) > 0;
    }

}
