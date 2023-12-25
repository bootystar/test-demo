package groupChat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;

/**
 * @author booty
 * @date 2021/6/11 09:33
 */
public class ChatServer implements Runnable{

    public static void main(String[] args) {
        new Thread(new ChatServer(9100)).start();
    }

    private int port;

    public ChatServer(int port) {
        this.port = port;
    }


    @Override
    public void run() {
        try {
            //创建工作组
            NioEventLoopGroup acceptGroup = new NioEventLoopGroup();
            NioEventLoopGroup handleGroup = new NioEventLoopGroup();
            //创建服务器引导
            ServerBootstrap server = new ServerBootstrap();
            //添加工作组、通道实现、处理器
            server.group(acceptGroup,handleGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //获取管道
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //添加编解码器和自定义处理器
                            pipeline.addLast("decoder",new StringDecoder())
                                    .addLast("encoder",new StringEncoder())
                                    .addLast("handler",new ServerHandler());

                        }
                    });
            //绑定端口开始服务
            ChannelFuture future = server.bind(port);
            future.sync()
                    .addListener(e->{
                        if (e.isSuccess()){
                            System.out.println("服务器启动成功");
                        }
                    });
//            acceptGroup.shutdownGracefully();
//            handleGroup.shutdownGracefully();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
//
class ServerHandler extends SimpleChannelInboundHandler<String>{

    /**
     * 用于管理所有通道的类，断开连接时自动添加删除连接的通道
     * 也可以自己写一个list，断开方法触发后手动删除
     */
    private static ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        super.handlerAdded(ctx);
    }

    /**
     * 读取数据时触发
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("msg received");
        //向所有非当前通道的通道发送消息,因为指定了编解码器为String,所以可以直接写
        Channel channel = channelHandlerContext.channel();
        channels.stream().filter(e->e!=channel).forEach(e-> e.writeAndFlush(channel.remoteAddress()+"-said:"+s));
    }

    /**
     * 数据读取完毕后触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 注册通道时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"-is added now");
        super.channelRegistered(ctx);
    }

    /**
     * 注销通道时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"-is removed now");
        super.channelUnregistered(ctx);
    }

    /**
     * 激活通道时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"-is online now");
        super.channelActive(ctx);
    }

    /**
     * 关闭通道时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"-is offline now");
        super.channelInactive(ctx);
    }



    /**
     * 发生异常时触发
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        super.exceptionCaught(ctx, cause);
    }
}
