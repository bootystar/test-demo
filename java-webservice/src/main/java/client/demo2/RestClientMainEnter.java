package client.demo2;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;


import javax.ws.rs.core.MediaType;
import java.util.Arrays;

/**
 * rest风格服务器
 *
 * @author booty
 * @date 2021/6/30 15:30
 */
public class RestClientMainEnter {
    public static void main(String[] args) {

        User user = new User().setAge(20).setId(2).setName("2号")
//                .setCar(new Car().setName("拖拉机").setPrice(100.0))
                ;

        WebClient
                .create("http://localhost:9001/userService/user")
                .type(MediaType.APPLICATION_JSON)
                .post(user);
        WebClient
                .create("http://localhost:9001/userService/user")
                .type(MediaType.APPLICATION_JSON)
                .put(user);

        User user2 = WebClient
                .create("http://localhost:9001/userService/user/1")
                .type(MediaType.APPLICATION_JSON)
                .get(User.class);
        System.out.println(user2);
    }
}
