package study.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.BindException;

/**
 * 一个代理类，提供真实对象的绑定和代理方法，代理
 * 类的要求是实现invocationhandler接口的代理方法
 * 当一个对象被绑定后，执行器方法就会进去到代理方法里面
 * @author anla7856
 *
 */
public class HelloServiceProxy implements InvocationHandler{

	/**
	 * 真实服务对象
	 */
	private Object target;
	/**
	 * 绑定委托对象并返回一个代理类
	 * @param target
	 * @return
	 */
	public Object bind(Object target) {
		this.target = target;
		//取得代理对象,jdk代理需要提供接口。
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}
	
	/**
	 * @param proxy 代理对象唉嗯
	 * @param method 被调用方法
	 * @param args 方法的参数
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("这是jdk动态代理");
		Object result = null;
		//反射方法调用前
		result = method.invoke(target, args);
		//反射方法调用后
		return result;
	}

}








