package demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义处理，需要继承netty规定好的某个处理器适配器HandlerAdapter（规范）
 *
 * @author booty
 * @date 2021/6/9 17:23
 */
public class DemoServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 收到数据时触发的方法
     *
     * @param ctx 上下文对象，含有管道pipeline，通道channel，地址
     * @param msg 客户端发送的数据，默认object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将msg转化为ByteBuf(netty提供，非nio包)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址为：" + ctx.channel().remoteAddress());


    }

    /**
     * 数据接收完毕后触发的方法
     *
     * @param ctx 上下文对象
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("服务器正在回复客户端");
//        //write+flush 将数据以utf-8的编码写入到缓存，并刷新
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,this is server",CharsetUtil.UTF_8));

        //1.模拟耗时长的任务，解决方案1。用户自定义普通任务,任务会添加到eventLoop中的taskQueue中依次执行
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(10 * 1000);
                System.out.println("服务器正在回复客户端");
                //write+flush 将数据以utf-8的编码写入到缓存，并刷新
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,this is server", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(15 * 1000);
                System.out.println("服务器正在回复客户端");
                //write+flush 将数据以utf-8的编码写入到缓存，并刷新
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,this is server", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //2.模拟耗时长的任务，解决方案2：用户自定义定时任务，任务会添加到enentLoop中的scheduleTaskQueue中依次执行（与taskQueue相互独立）
        ctx.channel().eventLoop().schedule(()->{
            try {
                Thread.sleep(15 * 1000);
                System.out.println("服务器正在回复客户端");
                //write+flush 将数据以utf-8的编码写入到缓存，并刷新
                ctx.writeAndFlush(Unpooled.copiedBuffer("hello,this is server", CharsetUtil.UTF_8));
            } catch (Exception e) {
                e.printStackTrace();
            }
        },5, TimeUnit.SECONDS);

        //3.模拟耗时长的任务，非当前reactor线程调用channel的各种方法（将任务放入一个队列中，然后依次执行）



    }

    /**
     * 发送异常后的处理
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //一般发生异常后关闭通道
        ctx.close();
    }
}
