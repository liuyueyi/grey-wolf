package com.hust.hui.wolf.common.util.spel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * spel 解析工具类
 *
 * Created by yihui on 2017/1/6.
 */
public class SpelTool {

    private static final Logger logger = LoggerFactory.getLogger(SpelTool.class);

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    public static String parseKey(String key, Method method, Object[] args) {
        try {
            //使用SPEL进行key的解析
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = getContext(method, args);
            return parser.parseExpression(key).getValue(context, String.class);
        } catch (Exception e) {
            logger.error("parse appKey error! e: {}, args: {}", e, args);
            return null;
        }
    }


    public static Map<String, String> parseKeys(List<String> keys, Method method, Object[] args) {
        try {
            //使用SPEL进行key的解析
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = getContext(method, args);

            Map<String, String> map = new HashMap<>(keys.size());
            for (String key : keys) {
                map.put(key, parser.parseExpression(key).getValue(context, String.class));
            }

            return map;
        } catch (Exception e) {
            logger.error("parse appKey error! e: {}, args: {}", e, args);
            return null;
        }
    }


    private static StandardEvaluationContext getContext(Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return context;
    }
}
