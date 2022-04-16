package cn.element.manage.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class TimerAspect {

    /**
     * 定义一个公共切点
     * 切点表达式直接指向接口,降低类耦合度
     */
    @Pointcut("execution(* cn.element.manage.test.aspect.Eatable.*(..))")
    public void pointcut() {
        
    }
    
//    @Before("pointcut()")
    public void before(JoinPoint jp) {
        System.out.println("前置处理器计时开始...");
        Object[] args = jp.getArgs();
        System.out.println("打印切点的参数args = " + Arrays.toString(args));
    }

    /**
     * 切点方法无论是否出现异常,都会执行最终通知方法
     * 最终通知(后置处理器)永远是最终执行的
     * 可以理解为在finally语句块中,符合Java语言机制
     */
//    @After("pointcut()")
    public void after(JoinPoint jp) {
        System.out.println("后置处理器计时开始...");
    }

    /**
     * 返回通知,在切点方法返回值后执行增强的功能
     * 切点方法出现了异常就不执行了
     * returning 属性指定拿到返回值,并且赋值给通知中的同名参数
     */
//    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        System.out.printf("返回通知执行... 切点返回值为: %s\n", result);
    }

    /**
     * 切点方法出现了异常才会调用异常通知
     * 可以理解为异常通知在catch语句块里面
     * throwing 属性指定拿到抛出的异常,并且赋值给通知中的同名参数
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterThrowing(Exception e) {
        System.out.printf("切入点发生了异常: %s,异常通知执行... \n", e.getMessage());
    }

    /**
     * 环绕通知,切点方法之前和之后都进行功能的增强
     * 参数列表必须得带上一个特殊的形参
     * ProceedingJoinPoint 代表切点
     * 通过ProceedingJoinPoint手动指定切点执行的位置
     * 环绕通知的返回值必须要是Object类型,在环绕通知中必须要将切点方法继续向上返回
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知前置计时器开始...");    
        long start = System.currentTimeMillis();
        
        // 控制切点方法在这里执行
        Object res = pjp.proceed();

        long end = System.currentTimeMillis();
        System.out.println("环绕通知后置计时器开始...");
        
        long time = end - start;
        System.out.println("程序运行用时: " + time);
        return res;
    }
    

}
