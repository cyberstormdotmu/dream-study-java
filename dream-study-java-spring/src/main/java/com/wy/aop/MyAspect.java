package com.wy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.EnableTransactionManagementConfiguration.CglibAutoProxyConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration.EnableTransactionManagementConfiguration.JdkDynamicAutoProxyConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring Aop
 * 
 * {@link EnableAspectJAutoProxy}:该注解指定代理使用的是JDK动态代理还是CGLIB,默认false,使用JDK动态代理
 * {@link #JdkDynamicAopProxy}:当使用JDK动态代理时的代理处理类,非public
 * {@link #CglibAopProxy}:当使用CGLIB动态代理时的代理处理类,非public
 * {@link TransactionAutoConfiguration}:事务自动配置类,会根据配置决定是使用JDK动态代理,还是CGLIB动态代理
 * {@link JdkDynamicAutoProxyConfiguration}:当spring.aop.proxy-target-class为false,使用JDK动态代理,默认代理
 * {@link CglibAutoProxyConfiguration}:当spring.aop.proxy-target-class为true时,使用CGLIB动态代理
 * 
 * AOP原理:
 * 
 * <pre>
 * {@link AbstractAutoProxyCreator}:BeanPostProcessor 实现,用AOP代理包装每个符合条件的bean,在调用bean本身之前委托给指定的拦截器
 * ->{@link AbstractAutoProxyCreator#postProcessAfterInitialization}:对初始化之后的bean进行AOP代理
 * -->{@link AbstractAutoProxyCreator#wrapIfNecessary}:判断Class是否需要代理,若需要,返回代理类以及相关切面
 * --->{@link BeanNameAutoProxyCreator#getAdvicesAndAdvisorsForBean}:根据beanName判断是否需要代理
 * ---->{@link AbstractAutoProxyCreator#createProxy}:将通用拦截,特殊拦截以及相关参数放入 ProxyFactory 中
 * ----->{@link ProxyFactory#getProxy()}:根据Class类型判断使用JDK动态代理或CGLIB代理,返回最终的代理对象
 * ------>{@link #JdkDynamicAopProxy#getProxy}:使用JDK动态代理生成代理类,非public
 * ------->{@link AopProxyUtils#completeProxiedInterfaces}:判断剔除不需要的代理类,返回所有需要代理的接口
 * ------>{@link #CglibAopProxy#getProxy}:使用CGLIB动态代理生成代理类,非public
 * ------->{@link #CglibAopProxy.DynamicAdvisedInterceptor#intercept}:AOP最终的代理对象的代理方法
 * 		this.advised.getInterceptorsAndDynamicInterceptionAdvice(method,targetClass):该方法返回所有的拦截器
 * </pre>
 *
 * @author 飞花梦影
 * @date 2021-10-21 13:35:36
 * @git {@link https://github.com/dreamFlyingFlower }
 */
@Aspect
public class MyAspect {

	/**
	 * {@link Pointcut}:声明一个切入点,用来配置需要被拦截的类,方法
	 * 
	 * <pre>
	 * execution:固定语法,表示执行.参数分为6段:
	 * 第一段表示返回值类型,通配符*表示可返回任意类型.若返回指定类型,需要写全路径名,如返回String,需要写java.lang.String.
	 * 		也可以直接写void,表示无返回值.或者!void,表示只要不是void的就可以
	 * 第二段表示需要拦截的包名
	 * 第三段的2给点,表示对包名下的子包同样拦截,不写表示只对当前包下的类,方法进行拦截
	 * 第四段表示需要拦截的类,*表示包下所有类
	 * 第五段表示需要拦截的类中的方法名,*表示所有方法.若需要指定参数,也需要写类全路径名,多给用逗号隔开
	 * 最后的括号里表示的是参数个数,..表示参数可有可无,可以有多个
	 * 
	 * 若需要排除某给方法,不进行拦截,可以使用&&和!
	 * 
	 * 拦截注解:@Pointcut("@annotation(com.wy.annotation.Logger)")
	 * </pre>
	 */
	@Pointcut("execution(* com.wy..*.*(..)) && !execution(* com.wy..TestCrl.Test(..))")
	private void aspect() {
	}

	/**
	 * {@link Before}:定义一个前置通知,在调用方法之前调用.需要设置一个拦截的切入点,即被PointCut修改的方法名
	 * 
	 * args:指定被拦截的方法参数个数以及形参名,即只会拦截参数名为username的方法
	 */
	@Before("aspect() && args(username)")
	public void beforeAspect(String username) {

	}

	/**
	 * {@link Around}:定义一个环绕通知,进入方法之后,开始执行方法之前调用一次.方法执行完,在After执行完之后再调用一次
	 * 
	 * @param joinPoint 包含了执行方法的相关信息,方法名,参数等
	 * @throws Throwable 会被AfterThrowing接收
	 */
	@Around("aspect()")
	public Object aroundAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		// 如果定义了环绕拦截,该方法必须执行.该方法实际上就是执行真正的方法,且最好有返回值
		Object object = joinPoint.proceed();
		return object;
	}

	/**
	 * {@link AfterReturning}:定义一个后置通知,即方法正常执行完之后调用.当方法抛出异常时,后置通知将不会被执行
	 * 在这里不能使用ProceedingJoinPoint,只能使用JoinPoint,否则报异常
	 * returning:接收返回结果的参数,即afterAspect的形参,类型可自定义
	 */
	@AfterReturning(pointcut = "aspect()", returning = "result")
	public void afterAspect(Object result) {

	}

	/**
	 * {@link After}:定义一个最终通知,在AfterReturning后执行,相当于finally中的代码
	 * 
	 * 当方法抛出异常时,该方法在AfterThrowing修饰的方法之后执行
	 */
	@After("aspect()")
	public void after() {

	}

	/**
	 * {@link AfterThrowing}:定义一个异常通知.throwable表示接收异常的参数名,即exception的形参
	 * 
	 * 在这里不能使用ProceedingJoinPoint,只能使用JoinPoint,否则报异常
	 */
	@AfterThrowing(pointcut = "aspect()", throwing = "throwable")
	public void exception(JoinPoint joinPoint, Throwable throwable) {

	}
}