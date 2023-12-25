package encoderAndDecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author booty
 * @date 2021/6/16 15:13
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup handler = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,handler)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MyServerChannelInitializer());
        ChannelFuture sync = server.bind(9000).sync();

    }
}


class MyServerChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MyDecoder())
                .addLast(new MyEncoder())
                .addLast(new ServerHandler());
    }
}

class   ServerHandler extends SimpleChannelInboundHandler<Long>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("收到消息："+aLong);
        channelHandlerContext.channel().writeAndFlush(5678L);
    }
}