package httpDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Netty简单小demo
 *
 * @author booty
 * @date 2021/6/9 16:37
 */
public class HttpServer {
    public static void main(String[] args) throws Exception {
        //创建服务器实例对象
        ServerBootstrap server = new ServerBootstrap();
        //绑定 工作组、频道实现类、子处理器（childHandler处理器表示处理通过当前通道的业务逻辑，handler表示处理当前通道）
        server.group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取channel通道对应的管道，管道内维护了handler清单的双向链表，入站从头到尾执行，出站从尾到头
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加解码器、编码器、消息聚合器、自定义消息处理器到管道处理链的尾部
                        pipeline.addLast("decoder", new HttpRequestDecoder())
                                .addLast("encoder", new HttpResponseEncoder())
                                .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                .addLast("handler", new HttpHandler());
                        /*
                        HttpRequestDecoder，用于解码request
                        HttpResponseEncoder，用于编码response
                        aggregator，消息聚合器（重要）。为什么能有FullHttpRequest这个东西，就是因为有HttpObjectAggregator，
                                    如果没有他，就不会有那个消息是FullHttpRequest的那段Channel，同样也不会有FullHttpResponse。
                                    如果我们将z'h，
                                    HttpObjectAggregator(512 * 1024)的参数含义是
                                    消息合并的数据大小，如此代表聚合的消息内容长度不超过512kb。
                        HttpHandler   添加自定义的处理接口对象

                         */
                    }
                });
        ChannelFuture channelFuture = server.bind(9000).sync();
        //添加监听器
        channelFuture.addListeners(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("http服务器创建成功");
                }else{
                    System.out.println("http服务器创建失败");
                }
            }
        });
        //获取服务通道的配置信息类，该类中有多个服务器信息用于get
        ChannelConfig config = channelFuture.channel().config();

    }

}
