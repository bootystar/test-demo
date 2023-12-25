package encoderAndDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encoder编码器
 *
 * Encoder最重要的实现类是MessageToByteEncoder<T>，
 * 这个类的作用就是将消息实体T从对象转换成byte，写入到ByteBuf，
 * 然后再丢给剩下的ChannelOutboundHandler传给客户端
 *
 * 链接：https://www.jianshu.com/p/fd815bd437cd
 * @author booty
 * @date 2021/6/16 15:17
 */
public class MyEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        System.out.println("encoder解析到的消息："+aLong);
        byteBuf.writeLong(aLong);
    }
}
