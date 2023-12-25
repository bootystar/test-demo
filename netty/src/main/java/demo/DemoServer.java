package demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author booty
 * @date 2021/6/9 17:13
 */

public class DemoServer {
    public static void main(String[] args) throws Exception {
        //创建bossGroup和WorkGroup,boss只处理连接请求，worker处理业务逻辑（循环处理）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            //创建服务器实例对象
            ServerBootstrap server = new ServerBootstrap();
            //设置服务器的工作线程组
            server.group(bossGroup, workerGroup);
            //设置管道实现
            server.channel(NioServerSocketChannel.class);
            //设置队列线程的连接个数
            server.option(ChannelOption.SO_BACKLOG, 128);
            //设置保持活动连接
            server.childOption(ChannelOption.SO_KEEPALIVE, true);
            //设置通道对象
            server.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //获取频道的管道
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    //添加自定义处理器
                    pipeline.addLast(new DemoServerHandler());
                }
            });
            System.out.println("服务端准备完毕");

            //绑定端口并开始同步处理
            ChannelFuture channelFuture = server.bind(9000).sync();

            //添加监听器，监听感兴趣的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("监听到启动成功");
                    }else {
                        System.out.println("监听到启动失败");
                    }
                }
            });
        } finally {
            //关闭服务器工作组
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }

    }
}
