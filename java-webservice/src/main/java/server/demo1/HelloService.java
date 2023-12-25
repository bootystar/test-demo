package server.demo1;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * 对外发布服务的接口
 *
 * （必须）
 * @webservice  对外暴露的服务接口
 *
 * （非必须）
 * @webmethod   对外暴露的方法
 * @webresult(name="")   指定wsdl说明书的响应的标签名
 * @webparam(name="")    指定wsdl说明书的参数标签名
 *
 * @author booty
 * @date 2021/6/30 10:42
 */
@WebService
public interface HelloService {

    @WebMethod
    @WebResult(name="sayHelloResult")
    String  sayHello(@WebParam(name="name") String name);
}
