package study.JDKProxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HelloServiceCgLib implements MethodInterceptor{

	/**
	 * 创建代理对象唉
	 */
	private Object target;
	
	public Object getInstance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		//回调方法
		enhancer.setCallback(this);
		//创建代理对象
		return enhancer.create();
		
	}
	/**
	 * 回调方法
	 */
	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		System.out.println("通过cglib来实现动态代理");
		Object returnObj = arg3.invokeSuper(arg0, arg2);
		return returnObj;
	}

}
