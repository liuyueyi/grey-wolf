package com.hust.hui.wolf.core.tool;

import org.junit.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * java 正则匹配测试
 * Created by yihui on 16/9/29.
 */
public class RegexTest {

    @Test
    public void testRegex() {
        String regex = "\\$\\{(.+)\\}";
        String cache = "prefix_${itemId}_123";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cache);
        while (matcher.find()) {
            String str = matcher.group().replace("${", "").replace("}", "");
            System.out.println(str);
        }


        String ans = cache.replaceAll("\\$\\{(.+)\\}", "");
        System.out.println(ans);
    }

    @Test
    public void testSpel() {
        String key = "#name";
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();

        context.setVariable("name","小灰灰");
        String ans = parser.parseExpression(key).getValue(context, String.class);
        System.out.println(ans);
    }

}
