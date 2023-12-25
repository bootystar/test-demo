package demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author booty
 * @date 2021/6/10 09:45
 */
public class DemoClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪时触发的方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道就绪，正在向服务器发送消息");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，this is client", CharsetUtil.UTF_8));
    }

    /**
     * 读取服务端发送的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务端回复的信息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端的地址是："+ctx.channel().remoteAddress());
    }
}
