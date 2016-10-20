package com.hust.hui.wolf.base.aspect;

import com.hust.hui.wolf.base.annotation.CacheEnabled;
import com.hust.hui.wolf.base.midware.api.ICache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by yihui on 16/9/25.
 */
@Aspect
@Component
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Autowired
    private ICache redisCache;

//    直接对注解进行切面
    @Around("@annotation(com.hust.hui.wolf.base.annotation.CacheEnabled)")
    public Object execute(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Method method = getMethod(point);
            CacheEnabled cacheEnabled = method.getAnnotation(CacheEnabled.class);

            String fieldKey = parseKey(cacheEnabled.fieldKey(), method, point.getArgs());
            String key = cacheEnabled.prefix() + fieldKey + cacheEnabled.suffix();
            // 如果缓存命中, 则直接返回
            Object cacheVal = redisCache.getObject(key, method.getReturnType());
            if (cacheVal != null) {
                return cacheVal;
            }

            // 缓存没有命中,则执行后,将结果塞入缓存
            Object result = point.proceed();
            redisCache.putObject(key, result, cacheEnabled.expire());
            return result;
        } catch (Exception e) {
            logger.error("exception! e: {}", e);
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            logger.info("leave cache function! cost: {}", end - start);
        }
    }

    /**
     * 获取被拦截方法对象
     * <p/>
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     * 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp) {
//        不直接用这个方法是因为获取的是顶层接口或者父类的方法对象
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        Method method = signature.getMethod();

        //获取参数的类型
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;

    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

/*
    AtomicInteger atomicInteger = new AtomicInteger(1);

    @Before("execution(* com.hust.hui.wolf.core..*(..))")
    public void before() {
        redisCache.putObject("atomic", atomicInteger, 1000);
        logger.info("start--------");
    }
*/

}
