## 基于 redis & 注解的缓存实现

> 对于某些高频访问方法的返回结果, 缓存到redis中, 每次进行访问的时候,优先从缓存中获取, 若没有, 则执行方法并将结果塞入缓存
将缓存的读取和写入逻辑与业务代码隔离
> 当前实现得比较简单, 在方法上添加注解 @CacheEnabeld, 配置缓存key,失效时间即可

### I. 需要改进的点

- 不能实现自定义的选择塞入\查询缓存的方式 （如某个方法, 要求参数满足某些特性的时候, 才表示要从缓存中获取）
- 目前仅实现针对方法级别的结果进行缓存
- 现在直接采用redis进行缓存的操作, 后续需要与具体的缓存实例解耦

### II. 为什么不用spring的 `EnableCaching` ?

> 自己实现熟悉下这套流程, 然后膜拜一下spring框架中实现的精妙

### III. 设计思路

#### 1. 定义注解

`CacheEnabled` 有四个属性, 其中缓存key的生成方法: `key = prefix + filedKey + suffix`

其中 `fieldKey` 由参数值来替换, 采用 `SpEL`

#### 2. 注解切面
> 对于切面实现缓存的逻辑, 主要分三点, 如何切?  如何生成 key?  逻辑怎么玩?

1. ` @Around("@annotation(com.hust.hui.wolf.base.annotation.CacheEnabled)")`

    实现对包含`CacheEnabel`的注解的方法进行拦截

2. 生成key时, 需要额外解析 `spel`

```java
String key = "prefix_#name";
//使用SPEL进行key的解析
ExpressionParser parser = new SpelExpressionParser();
//SPEL上下文
StandardEvaluationContext context = new StandardEvaluationContext();

context.setVariable("name","小灰灰");
String ans = parser.parseExpression(key).getValue(context, String.class);
System.out.println(ans);
```

另外一个需要注意的是,获取切面获取method,不能简单的使用`((MethodSignature) point.getSignature()).getMethod();`

因为上面的方法获取的方法可能为父类的方法, 而不是当前执行类的方法, 因此可能出现各种奇怪的问题, 因此在切面中采用了反射的方法获取当前执行的方法

```java
public Method getMethod(ProceedingJoinPoint pjp) {
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
```


3. 实现逻辑

> 实现逻辑比较简单, 首先是获取key, 查缓存, 未命中, 则执行方法, 将返回结果塞入缓存; 命中, 则直接返回


