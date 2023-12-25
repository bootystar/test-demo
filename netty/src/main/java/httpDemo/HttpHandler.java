package httpDemo;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * http自定义处理器
 * Handler声明泛型为<FullHttpRequest>之后，只有msg为FullHttpRequest的消息才能进来
 *
 * ChannelInboundHandler、ChannelOutboundHandler
 * 对于服务器而言，从客户端发送的请求称为入站，从服务器返回的响应（请求）称为出站
 * 对于客户端而言，从服务返回的响应（请求）称为入站，从客户端发送的请求称为出战
 * ChannelInboundHandler        处理入站事件
 * ChannelOutboundHandler       处理出站事件
 * SimpleChannelInboundHandler是ChannelInboundHandler的子类
 *
 *
 * @author booty
 * @date 2021/6/9 17:12
 */
public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

//    private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

    /**
     * 读取到数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("class:" + msg.getClass().getName());

        //获取uri
        URI uri=new URI(msg.uri());
        if (uri.getPath().equals("/favicon.ico")){
            System.out.println("请求网页图标，不做响应");
        }else {
            ByteBuf content=Unpooled.copiedBuffer("服务器已收到请求", CharsetUtil.UTF_8);
        }


        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                // 生成response，这里使用的FullHttpResponse，同FullHttpRequest类似，通过这个我们就不用将response拆分成多个channel返回给请求端了
                Unpooled.wrappedBuffer("httpDemo".getBytes()));

        //设置响应头
        HttpHeaders heads = response.headers();
        //设置响应类型和字符集
        heads.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN + "; charset=UTF-8");
        //添加header描述length。这一步是很重要的一步，如果没有这一步，你会发现用postman发出请求之后就一直在刷新，因为http请求方不知道返回的数据到底有多长
        heads.add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        //指定连接为长连接，即刷新时不会产生新的通道和管道
        heads.add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);


        //发送响应
        ctx.write(response);
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        super.channelReadComplete(ctx);
        // channel读取完成之后需要输出缓冲流。如果没有这一步，你会发现postman同样会一直在刷新
        ctx.flush();
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
}