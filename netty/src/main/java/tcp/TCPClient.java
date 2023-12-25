package tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author booty
 * @date 2021/6/17 15:01
 */
public class TCPClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new ProtocolDecoder())
                                .addLast(new ProtocolEncoder())
                                .addLast(new SimpleChannelInboundHandler<MessageProtocol>() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        //启动时发送5个包，查看服务端的识别情况
                                        for (int i = 0; i < 5; i++) {
                                            String msg="你好不好，我很好";
                                            //获取数据
                                            byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
                                            //封装协议对象
                                            MessageProtocol messageProtocol=new MessageProtocol();
                                            messageProtocol.setLength(bytes.length);
                                            messageProtocol.setContent(bytes);
                                            //写出数据
                                            ctx.writeAndFlush(messageProtocol);
                                        }
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
                                        System.out.println("收到消息："+new String(messageProtocol.getContent(),StandardCharsets.UTF_8));
                                    }
                                });
                    }
                });
        client.connect(new InetSocketAddress("127.0.0.1",9000)).sync();

    }
}
