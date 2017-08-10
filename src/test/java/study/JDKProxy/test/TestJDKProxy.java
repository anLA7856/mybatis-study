package study.JDKProxy.test;

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
}
