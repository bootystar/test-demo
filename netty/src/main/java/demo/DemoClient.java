package demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author booty
 * @date 2021/6/10 09:38
 */
public class DemoClient {
    public static void main(String[] args) throws Exception {

        //客户端只需要一个事件循环工作组
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {


            //客户端和server使用不同
            Bootstrap bootstrap = new Bootstrap();

            //设置相关属性
            bootstrap.group(clientGroup)//设置工作组
                    .channel(NioSocketChannel.class)//设置管道实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加处理规则
                            pipeline.addLast(new DemoClientHandler());
                        }
                    });
            System.out.println("客户端准备完毕");

            //启动客户端，异步连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
            //关闭通道进行监听，
            channelFuture.channel().closeFuture().sync();
        } finally {
//            clientGroup.shutdownGracefully();
        }
    }
}
