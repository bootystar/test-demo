package server.demo1;

/**
 * @author booty
 * @date 2021/6/30 10:44
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello,"+name;
    }
}
