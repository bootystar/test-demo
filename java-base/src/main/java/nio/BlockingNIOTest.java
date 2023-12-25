package nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 使用nio完成网络通信的三个核心
 * 通道、缓冲期、选择器
 * <p>
 * 选择器
 * 选择器收到客户端的请求后，会将该请求注册到选择器中，
 * 然后接收客户端传输的数据，只有客户端将所有数据完全准备就绪后，选择器才会将任务分配到线程上进行执行
 * 而一般io会一边接收数据一边执行，执行一般快于接收数据，此时会阻塞，会拖慢运行速度
 * <p>
 * 只有网络通信类Channel能使用选择器（实现了SelectableChannel接口）
 * java.nio.channels.Channel       接口
 * SelectableChannel           接口
 * SocketChannel           实现类
 * ServerSocketChannel     实现类
 * DatagramChannel         实现类
 * <p>
 * Pipe.SinkChannel        实现类
 * Pipe.SourceChannel      实现类
 *
 * @author booty
 * @date 2021/6/2 11:09
 */
public class BlockingNIOTest {

    /**
     * 客户端（阻塞式)
     */
    @Test
    public void client() throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9001);
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("客户端发送的数据".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.shutdownOutput();

        buffer.flip();
        socketChannel.read(buffer);
        buffer.flip();
        System.out.print(new String(buffer.array()));


        socketChannel.close();
    }


    /**
     * 服务端（阻塞式）
     */
    @Test
    public void server() throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9001);

        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.bind(inetSocketAddress);

        SocketChannel accept = channel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (accept.read(buffer) != -1) {
            buffer.flip();
            System.out.print(new String(buffer.array()));
            buffer.clear();
        }

        buffer.clear();
        buffer.put("消息已收到".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        accept.write(buffer);

        accept.close();
        channel.close();
    }

    /**
     * 服务端，非阻塞式
     */
    @Test
    public void server2() throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(9001);

        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.bind(inetSocketAddress);

        channel.configureBlocking(false);


        Selector selector=Selector.open();

        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                if(next.isAcceptable()){
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (accept.read(buffer) != -1) {
                        buffer.flip();
                        System.out.print(new String(buffer.array()));
                        buffer.clear();
                    }

                    buffer.clear();
                    buffer.put("消息已收到".getBytes(StandardCharsets.UTF_8));
                    buffer.flip();
                    accept.write(buffer);

                    accept.close();
                    channel.close();
                }else if(next.isReadable()){
                    SocketChannel channel1 = (SocketChannel) next.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (channel1.read(buffer) != -1) {
                        buffer.flip();
                        System.out.print(new String(buffer.array()));
                        buffer.clear();
                    }
                }
                iterator.remove();
            }
        }


    }


}
