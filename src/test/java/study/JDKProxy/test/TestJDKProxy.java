package study.JDKProxy.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import study.JDKProxy.HelloService;
import study.JDKProxy.HelloServiceImpl;
import study.JDKProxy.HelloServiceProxy;

public class TestJDKProxy {
	@Test
	public void test() {
		HelloServiceProxy helloHandler = new HelloServiceProxy();
		HelloService proxy = (HelloService) helloHandler.bind(new HelloServiceImpl());
		proxy.sayHello("Tom");
	}
	
	@Test
	public void testCgLibProxy() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Object service = Class.forName(HelloServiceImpl.class.getName()).newInstance();
		//获取服务方法hello
		Method method = service.getClass().getMethod("sayHello", String.class);
		method.invoke(service, "June");
	}
}




