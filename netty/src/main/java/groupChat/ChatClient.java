package groupChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author booty
 * @date 2021/6/11 10:54
 */
public class ChatClient implements Runnable{

    public static void main(String[] args) {
        new Thread(new ChatClient(9100,"127.0.0.1")).start();
    }

    private int port;
    private String host;

    public ChatClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            NioEventLoopGroup workGroup = new NioEventLoopGroup();
            Bootstrap client = new Bootstrap();
            client.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder())
                                    .addLast("encoder",new StringEncoder())
                                    .addLast("handler",new ClientHandler());
                        }
                    });
            ChannelFuture future = client.connect(host, port).sync();
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()){
                    System.out.println("客户端就绪");
                }
            });
            Scanner scanner=new Scanner(System.in);
            while (scanner.hasNextLine()){
                future.channel().writeAndFlush(scanner.nextLine());
            }
        }catch ( Exception e){
            e.printStackTrace();
        }

    }
}
class ClientHandler extends SimpleChannelInboundHandler<String>{


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}