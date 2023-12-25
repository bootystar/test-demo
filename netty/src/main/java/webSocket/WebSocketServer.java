package webSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.time.LocalDateTime;

/**
 * @author booty
 * @date 2021/6/15 10:11
 */
public class WebSocketServer {
    public static void main(String[] args) throws  Exception{
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup handleGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,handleGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //因websocket基于http协议，使用http协议的编码器和解码器
                        pipeline.addLast(new HttpServerCodec());
                        //以块的方式写，添加ChunkedWriteHandler
                        pipeline.addLast(new ChunkedWriteHandler());
                        //http协议在传输中是分段的，添加处理器将多个段聚合
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        //websocket数据传输以桢（frame）形式传递,将http协议升级为ws协议，指定连接路径，保持长连接
                        pipeline.addLast(new WebSocketServerProtocolHandler("/test"));
                        //添加自定义处理器
                        pipeline.addLast(new WebSocketServerHandler());
                    }
                });
        ChannelFuture sync = server.bind(9000).sync();
    }

}
class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 指定TextWebSocketFrame 表名以桢的形式接收传递
     * @param channelHandlerContext
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息:"+textWebSocketFrame.text());
        channelHandlerContext.writeAndFlush(new TextWebSocketFrame("服务器收到，发送时间："+LocalDateTime.now()+"===消息内容："+textWebSocketFrame.text()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText()+"已连接");
//        System.out.println(ctx.channel().id().asShortText());
        /*
        longText    完整长id，id唯一
        shortText   短id，可能重复
         */
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id().asLongText()+"断开连接");
    }
}