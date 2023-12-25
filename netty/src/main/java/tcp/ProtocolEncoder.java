package tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义编码器
 * @author booty
 * @date 2021/6/17 15:10
 */
public class ProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol, ByteBuf byteBuf) throws Exception {
        System.out.println("自定义decoder被调用");
        //写出int类型长度
        byteBuf.writeInt(messageProtocol.getLength());
        //写出byte[]数据
        byteBuf.writeBytes(messageProtocol.getContent());

    }
}
