package server.demo1;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * 服务端主启动类
 * 启动后可以通过网页访问wsdl说明书
 * wsdl说明书地址是服务地址 + ？+wsdl
 * @author booty
 * @date 2021/6/30 10:46
 */
public class ServiceMainEnter {
    public static void main(String[] args) {
        //发布服务的工厂
        JaxWsServerFactoryBean factory=new JaxWsServerFactoryBean();
        //发布地址
        factory.setAddress("http://localhost:9000/ws/hello");
        //设置服务类（实现的接口需要添加@WebService注解）
        factory.setServiceBean(new HelloServiceImpl());

        //添加日志拦截(需要先配置日志)
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        //发布
        Server server = factory.create();

        server.start();

        System.out.println("发布成功");
    }
}
