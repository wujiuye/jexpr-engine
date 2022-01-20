package com.wujiuye.jexpr;

import com.wujiuye.jexpr.logic.GroupCloseOperation;
import com.wujiuye.jexpr.logic.GroupOpenOperation;
import com.wujiuye.jexpr.logic.LogicOperation;
import com.wujiuye.jexpr.relation.RelationOperation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 操作枚举
 *
 * @author wujiuye
 */
public enum Operations {

    INSTANCE;

    private final Map<String, Operation> operations = new HashMap<>();
    private final GroupOpenOperation groupOpenOperation = new GroupOpenOperation();
    private final GroupCloseOperation groupCloseOperation = new GroupCloseOperation();

    public void registerOperation(Operation op) {
        if (!operations.containsKey(op.getSymbol())) {
            operations.put(op.getSymbol(), op);
        }
    }

    public Operation getOperation(String symbol) {
        Operation operation = this.operations.get(symbol);
        if (operation != null) {
            return operation;
        }
        // 默认支持的
        if ("(".equals(symbol)) {
            return groupOpenOperation;
        } else if (")".equals(symbol)) {
            return groupCloseOperation;
        }
        return null;
    }

    public Set<String> allLogicOperation() {
        Set<String> set = new HashSet<>();
        for (Operation operation : operations.values()) {
            if (operation instanceof LogicOperation) {
                set.add(operation.getSymbol());
            }
        }
        return set;
    }

    public Set<String> allRelationOperation() {
        Set<String> set = new HashSet<>();
        for (Operation operation : operations.values()) {
            if (operation instanceof RelationOperation) {
                set.add(operation.getSymbol());
            }
        }
        return set;
    }

}
