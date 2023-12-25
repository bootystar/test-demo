package buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * netty的byteBuf
 * 底层维护了redderIndex和writerIndex
 *
 * @author booty
 * @date 2021/6/11 09:09
 */
public class BufTest {

    @Test
    public void test1(){
        //创建一个无内容的buf，该buf内有一个byte数组，大小为指定大小
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        //获取（netty的buf不需要使用flip）
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }


    @Test
    public void test2(){
        //创建一个有内容的buf
        ByteBuf buf = Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8);
        if (buf.hasArray()){
            byte[] bytes=buf.array();
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }


    }
}
