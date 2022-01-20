package com.wujiuye.jexpr.relation;

import com.wujiuye.jexpr.Value;
import com.wujiuye.jexpr.ValueHelper;
import com.wujiuye.jexpr.Variable;

import java.util.Map;

/**
 * !=
 *
 * @author wujiuye
 */
public class NotEquals extends RelationOperation {
    public NotEquals() {
        super("!=");
    }

    @Override
    public NotEquals copy() {
        return new NotEquals();
    }

    @Override
    public boolean interpret(Map<String, ?> bindings) {
        // 变量名匹配
        Variable v = (Variable) this.leftOperand;
        Object obj = bindings.get(v.getName());
        if (obj == null) {
            return true;
        }
        // 值匹配
        Value<?> value = (Value<?>) this.rightOperand;
        return !ValueHelper.equalsValue(value, obj);
    }

}
