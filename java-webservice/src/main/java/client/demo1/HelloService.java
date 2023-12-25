package client.demo1;

/**
 *
 * 对外发布服务的接口
 * @author booty
 * @date 2021/6/30 10:42
 */
@javax.jws.WebService
public interface HelloService {

    String sayHello(String name);
}
