package tcp;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageAggregator;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * @author booty
 * @date 2021/6/17 15:00
 */
public class TCPServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup handler = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        server.group(boss,handler)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加编解码器
                        pipeline
//                                .addLast(new ByteToMessageDecoder() {
//                            @Override
//                            protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//                                int i = in.readableBytes();
//                                byte[] bytes = new byte[i];
//                                in.readBytes(bytes);
//                                System.out.println();
//                                String s = new String(bytes, StandardCharsets.UTF_8);
//                                System.out.println("收到消息："+s);
//
//                                StringBuilder sb =new StringBuilder();
//                                for (byte b : bytes) {
//                                    String hexString = Integer.toHexString(b).toUpperCase(Locale.ROOT);
//                                    sb.append(hexString);
//                                }
//                                System.out.println("收到消息："+sb.toString());
//
//
//
////                                String hexes = "0123456789ABCDEF";
////                                byte[] dataArray = new byte[in.readableBytes()];
////                                in.readBytes(dataArray);
////                                StringBuilder sb = new StringBuilder(2 * dataArray.length);
////
////                                for (byte data : dataArray) {
////                                    char c = hexes.charAt((data & 0xF0) >> 4);
////                                    char c1 = hexes.charAt((data & 0x0F));
////                                    sb.append(c).append(c1);
////                                }
////                                out.add(sb.toString());
//                            }
//                        })


                                .addLast(new ProtocolDecoder())
                                .addLast(new ProtocolEncoder())
                                .addLast(new SimpleChannelInboundHandler<MessageProtocol>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
                                        //读取到消息
                                        byte[] content = messageProtocol.getContent();
                                        System.out.println("收到的消息是："+new String(content, StandardCharsets.UTF_8));
                                    }
                                })
                        ;
                    }
                });
        ChannelFuture sync = server.bind(9000).sync();

    }
}
