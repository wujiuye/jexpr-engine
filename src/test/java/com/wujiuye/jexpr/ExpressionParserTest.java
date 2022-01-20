package com.wujiuye.jexpr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wujiuye
 */
public class ExpressionParserTest {

    public static void main(String[] args) {
        Expression ex1 = ExpressionParser.fromString("var1='A' and var2=0");
        Expression ex2 = ExpressionParser.fromString("var1='B'");
        Expression ex3 = ExpressionParser.fromString("var1='A' and var2!=0");
        Expression ex4 = ExpressionParser.fromString("var1='A' and not var2=0");
        Expression ex5 = ExpressionParser.fromString("var1='A'");
        Expression ex6 = ExpressionParser.fromString("var1 in ('A','B','C')");
        Expression ex7 = ExpressionParser.fromString("var1 in ('A','B','C') and not var1 in ('A','B','C')");
        Expression ex8 = ExpressionParser.fromString("var2 in (0,1,2) and not var2 in (1,3,4)");

        Map<String, Object> bindings = new HashMap<>();
        bindings.put("var1", "A");
        bindings.put("var2", 0);

        boolean triggered = ex1.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex2.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex3.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex4.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex5.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex6.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex7.interpret(bindings);
        System.out.println("match result:" + triggered);
        triggered = ex8.interpret(bindings);
        System.out.println("match result:" + triggered);


        bindings = new HashMap<>();
        bindings.put("a", "123");
        bindings.put("b", "123");
        bindings.put("c", "aa");
        bindings.put("d", "ff");

        // 逻辑运算符符号两边要留至少一个空格

        Expression ex9 = ExpressionParser.fromString("a='123' AND b!='123' OR (c='aa' AND d='ff')");
        triggered = ex9.interpret(bindings);
        System.out.println("match result:" + triggered);

        bindings = new HashMap<>();
        bindings.put("a", 11);
        bindings.put("b", 10);
        bindings.put("c", -10);
        Expression ex10 = ExpressionParser.fromString("(a>0 and a<10) or (b>10 or b<10) or not (c<0 and c>-100)");
        triggered = ex10.interpret(bindings);
        System.out.println("match result:" + triggered);
        Expression ex11 = ExpressionParser.fromString("(a>0 and a<12) or (b>10 or b<10) or not (c<0 and c>-100)");
        triggered = ex11.interpret(bindings);
        System.out.println("match result:" + triggered);
        Expression ex12 = ExpressionParser.fromString("(a>0 and a<10) or b=10 or not (c<0 and c>-100)");
        triggered = ex12.interpret(bindings);
        System.out.println("match result:" + triggered);
        Expression ex13 = ExpressionParser.fromString("(a>0 and a<10) or (b>10 or b<10) or not (c<0 and c>-10)");
        triggered = ex13.interpret(bindings);
        System.out.println("match result:" + triggered);
    }

}
