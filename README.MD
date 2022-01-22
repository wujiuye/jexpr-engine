# jexpr-engine
用Java实现的一个轻量的条件表达式引擎。

原理无非就是利用递归与回溯算法解析表达式，再加上二叉树数据结构实现运算，以及栈数据结构实现优先级。

表达式引擎支持的运算符：
* 关系运算符：=，!=，>，>=,<,<=，in,is null,is not null
* 逻辑运算符：and ,or，not（逻辑与或非）

支持组合与优先级：
* not in可通过逻辑运算符not+关系运算符in实现
* is not null也可通过逻辑运算符not+关系运算符is null实现 
* 支持()，优先级从左到右，()里面的优先，()与()之间，也是从左到右，符合正常数学层面理解的优先级

表达式书写要求：
* 字符串必要使用"'"开始，"'"结束
* in后面必须带括号
* and、or、not、in、is null、is not null左右两边至少要有一个空格
* and、or、not、in、is null、is not null不区分大小写

关系运算符简单使用：
```text
a='jexpn'
a!='jexpn'
a>0
a>=0
a<1
a<=1
a in (1,2,3)
a in (1.0,2.0,3.0)
a in ('java','expr','engine')
a is null
a is not null
```
关系运算符与逻辑运算符混合使用：
```text
a>0 and a<10
a<10 or a>100
not a=100
a>0 and not a=10
a>0 and a<100 and c=1 or d=0
```

加入优先级的复杂使用：
```text
a='123' AND b!='123' OR (c='aa' AND d='ff')
(a>0 and a<10) or (b>10 or b<10) or not (c<0 and c>-100)
(a>0 and a<12) or (b>10 or b<10) or not (c<0 and c>-100)
(a>0 and a<10) or b=10 or not (c<0 and c>-100)
(a>0 and a<10) or (b>10 or b<10) or not (c<0 and c>-10)
```

使用方式：
```java
/**
 * @author wujiuye
 */
public class ExpressionParserTest {

    public static void main(String[] args) {
        // 解析表达式
        Expression expr = ExpressionParser.fromString("(a>0 and a<10) or (b>10 or b<10) or not (c<0 and c>-100)");
        // 设置变量值
        Map<String,Object> bindings = new HashMap<>();
        bindings.put("a", 11);
        bindings.put("b", 10);
        bindings.put("c", -10);
        // 匹配
        boolean triggered = expr.interpret(bindings);
        System.out.println("匹配结果:" + triggered);
    }

}

```
