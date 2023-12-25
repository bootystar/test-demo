package server.demo2;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 服务接口
 *
 * @Path        该服务的调用路径
 * @Produces    该服务支持的返回类型（*代表全部）
 *
 * @POST @GET @PUT @DELETE  请求类型
 * @Consumes        支持的请求参数类型
 *
 *
 * @author booty
 * @date 2021/6/30 15:16
 *
 */
@Path("/userService")
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public interface UserService {

    @POST
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    void save(User user);

    @GET
    @Path("/user/{id}")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    User get(@PathParam("id")int id);

    @PUT
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    void update(User user);

    @DELETE
    @Path("/user")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    void delete(User user);

}
