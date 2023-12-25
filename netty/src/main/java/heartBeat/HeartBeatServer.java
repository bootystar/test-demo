package heartBeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author booty
 * @date 2021/6/15 09:36
 */
public class HeartBeatServer {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup handleGroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boosGroup,handleGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))//添加日志处理
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        /*
                         添加空闲处理器，
                         参数1：readerIdleTime表示没有读取的时间，
                         writerIdleTime表示没有写的时间，
                         allIdleTime表示即没有读也没有写，
                         当时间达到设定值时，就会发送心跳包检测
                         当IdleStateEvent触发后，就会传递给管道的下一个handler，并触发handler的userEventTriggered，因此核心处理逻辑需要写入下一个handler的userEventTriggered方法
                         */
                        pipeline.addLast(new IdleStateHandler(10,10,5, TimeUnit.SECONDS))
                                .addLast(new HeartBeatServerHandler());
                    }
                });
        ChannelFuture sync = server.bind(9100).sync();


    }
}
class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        super.userEventTriggered(ctx, evt);
        //判断是否为心跳检测读写空闲事件，并做响应处理
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event=(IdleStateEvent) evt;
            String info=null;
            switch (event.state()){
                case READER_IDLE:
                    info ="READER_IDLE" ;
                    break;
                case WRITER_IDLE:
                    info="WRITER_IDLE";
                    break;
                case ALL_IDLE:
                    info="ALL_IDLE";
//                    //检测到空闲可关闭该连接
//                    ctx.channel().close();
                    break;
            }
            System.out.println( ctx.channel().remoteAddress()+"===>"+info);
        }
    }
}