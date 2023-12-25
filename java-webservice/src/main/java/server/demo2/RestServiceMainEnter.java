package server.demo2;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * rest风格服务器
 * @author booty
 * @date 2021/6/30 15:30
 */
public class RestServiceMainEnter {
    public static void main(String[] args) {
        //步骤与之前类似，不过工厂更换
        JAXRSServerFactoryBean factory=new JAXRSServerFactoryBean();
        factory.setAddress("http://localhost:9001/");
        factory.setServiceBean(new UserServiceImpl());


        //添加日志拦截(需要先配置日志)
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());

        factory.create();
        System.out.println("rest启动成功");
    }
}
