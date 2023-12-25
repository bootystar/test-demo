package client.demo1;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * 客户端主启动类
 * @author booty
 * @date 2021/6/30 11:06
 */
public class ClientMainEnter {
    public static void main(String[] args) {

        //代理链接的工厂
        JaxWsProxyFactoryBean factory=new JaxWsProxyFactoryBean();
        //服务端地址
        factory.setAddress("http://localhost:9000/ws/hello");
        //设置接口类型
        factory.setServiceClass(HelloService.class);
        //对接口生成代理对象
        HelloService helloService = factory.create(HelloService.class);

        //代理对象类型（class com.sun.proxy.$Proxy35，jdk动态代理）
        System.out.println(helloService.getClass());

        //远程访问服务端方法
        String reply = helloService.sayHello("zhangsan");
        System.out.println(reply);
    }
}
