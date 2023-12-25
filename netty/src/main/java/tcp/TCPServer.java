package tcp;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author booty
 * @date 2021/6/17 15:00
 */
public class TCPServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup handler = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,handler)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加编解码器
                        pipeline.addLast(new ProtocolDecoder())
                                .addLast(new ProtocolEncoder())
                                .addLast(new SimpleChannelInboundHandler<MessageProtocol>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
                                        //读取到消息
                                        byte[] content = messageProtocol.getContent();
                                        System.out.println("收到的消息是："+new String(content, StandardCharsets.UTF_8));
                                    }
                                });
                    }
                });
        ChannelFuture sync = server.bind(9000).sync();

    }
}
