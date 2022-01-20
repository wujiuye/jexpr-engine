package com.wujiuye.jexpr;

/**
 * @author wujiuye
 */
public class ValueHelper {

    public static Value<?> parseValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("The provided string must not be null");
        }
        value = value.trim(); // 去空格
        if ("true".equals(value) || "false".equals(value)) {
            return new Value<>(Boolean.getBoolean(value), Boolean.class);
        } else if (value.startsWith("'")) {
            value = value.replace("'", "");
            return new Value<>(value, String.class);
        } else if (value.contains(".")) {
            return new Value<>(Double.parseDouble(value), Double.class);
        } else {
            return new Value<>(Long.parseLong(value), Long.class);
        }
    }

    public static ArrayValue<?> parseArrayValue(String string) {
        // 逗号分割
        String[] arrayStr = string.split(",");
        Object[] values = new Object[arrayStr.length];
        Class<Object> type = null;
        for (int i = 0; i < arrayStr.length; i++) {
            Value<?> value = parseValue(arrayStr[i]);
            values[i] = value.getValue();
            type = (Class) value.getType();
        }
        return new ArrayValue<>(values, type);
    }

    public static boolean equalsValue(Value<?> value1, Object value2) {
        if (value1.getType() == value2.getClass()) {
            return value1.getValue().equals(value2);
        }
        if (value1.getType() == Long.class && value2.getClass() == Integer.class) {
            return value1.getValue().equals(Long.parseLong(value2.toString()));
        }
        if (value1.getType() == Double.class && value2.getClass() == Float.class) {
            return value1.getValue().equals(Double.parseDouble(value2.toString()));
        }
        return false;
    }

    public static int compareTo(Object value2, Value<?> value1) {
        Object cnvObj = value2;
        if (value1.getType() == Long.class) {
            cnvObj = Long.valueOf(value2.toString());
        } else if (value1.getType() == Double.class) {
            cnvObj = Double.valueOf(value2.toString());
        }
        Comparable comparable2 = (Comparable) cnvObj;
        Comparable comparable1 = (Comparable) value1.getValue();
        return comparable2.compareTo(comparable1);
    }

}
