package encoderAndDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author booty
 * @date 2021/6/16 15:13
 */
public class MyClient {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .handler(new MyClientChannelInitializer());
        ChannelFuture sync = client.connect(new InetSocketAddress("127.0.0.1", 9000)).sync();

    }
}
class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new MyEncoder())
                .addLast(new MyDecoder())
                .addLast(new MyClientHandler());
    }
}
class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().writeAndFlush(1234L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long aLong) throws Exception {
        System.out.println("收到LONG消息："+aLong);
    }
}
