package tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 自定义解码器
 * @author booty
 * @date 2021/6/17 15:10
 */
public class ProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("自定义decoder调用");
        //读取数据长度
        int i = byteBuf.readInt();
        //创建指定长度数组
        byte[] bytes=new byte[i];
        //写入数据
        byteBuf.readBytes(bytes);
        //封装为自定义对象
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLength(i);
        messageProtocol.setContent(bytes);
        //将对象添加到集合中
        list.add(messageProtocol);
    }
}
