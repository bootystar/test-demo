package encoderAndDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.Data;

import java.util.List;

/**
 * Decoder解码器
 *
 * 和Encoder一样，decoder就是在服务端收到数据的时候，
 * 将字节流转换为实体对象Message。
 * 但是和Encoder的处理逻辑不一样，数据传到服务端有可能不是一次请求就能完成的，
 * 中间可能需要经过几次数据传输，并且每一次传输传多少数据也是不确定的
 *
 * 其中有两个方法：decode和decodeLast，这里两个方法的不同之处为在于他们的调用时机不同，
 * decodeLast只有在Channel的生命周期结束之前会调用一次，默认是调用decode方法
 *
 * 链接：https://www.jianshu.com/p/fd815bd437cd
 * @author booty
 * @date 2021/6/16 15:17
 */
public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decoder解码器被调用");
        //若剩余数据大于8个字节，则将其传递给下一个handler
        if(byteBuf.readableBytes()>=8){
            list.add(byteBuf.readLong());
        }
    }
}

/**
 * ReplayingDecoder
 * ReplayingDecoder 是 byte-to-message 解码的一种特殊的抽象基类，
 * 读取缓冲区的数据之前需要检查缓冲区是否有足够的字节，使用ReplayingDecoder就无需自己检查；
 * 若ByteBuf中有足够的字节，则会正常读取；若没有足够的字节则会停止解码。
 * RelayingDecoder在使用的时候需要搞清楚的两个方法是checkpoint(S s)和state()，
 * 其中checkpoint的参数S，代表的是ReplayingDecoder所处的状态，一般是枚举类型。
 * RelayingDecoder是一个有状态的Handler，状态表示的是它目前读取到了哪一步，
 * checkpoint(S s)是设置当前的状态，state()是获取当前的状态。
 *
 *
 * 继承ReplayingDecoder，泛型LiveState，用来表示当前读取的状态
 * 描述LiveState，有读取长度和读取内容两个状态
 * 初始化的时候设置为读取长度的状态
 * 读取的时候通过state()方法来确定当前处于什么状态
 * 如果读取出来的长度大于0，则设置为读取内容状态，下一次读取的时候则从这个位置开始
 * 读取完成，往结果里面放解析好的数据
 *
 * 不是所有的标准 ByteBuf 操作都被支持，如果调用一个不支持的操作会抛出 UnreplayableOperationException
 * ReplayingDecoder 略慢于 ByteToMessageDecoder
 * 所以，如果不引入过多的复杂性 使用 ByteToMessageDecoder 。否则,使用ReplayingDecoder。
 *
 */
class LiveDecoder extends ReplayingDecoder<LiveDecoder.LiveState> { //1

    public enum LiveState {
        LENGTH,
        CONTENT
    }

    private LiveMessage message = new LiveMessage();

    public LiveDecoder() {
        super(LiveState.LENGTH);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        switch (state()) {
            case LENGTH:
                int length = byteBuf.readInt();
                if (length > 0) {
                    checkpoint(LiveState.CONTENT);
                } else {
                    list.add(message);
                }
                break;
            case CONTENT:
                byte[] bytes = new byte[message.getLength()];
                byteBuf.readBytes(bytes);
                String content = new String(bytes);
                message.setContent(content);
                list.add(message);
                break;
            default:
                throw new IllegalStateException("invalid state:" + state());
        }
    }
}
@Data
class LiveMessage{
    private int length;
    private String content;
}