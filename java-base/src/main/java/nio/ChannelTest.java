package nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Channel通道
 * 用于源节点和目标节点的连接，在javaNIO中负责数据的传输
 * Channel本身不负责储存数据  ，因此需要配合缓冲区使用
 *
 * 层级结构
 * java.nio.channels.Channel接口
 * FileChannel             从文件读写读写数据
 * SocketChannel           通过TCP协议读取网络中的Channel
 * ServerSocketChannel     坚挺TCP连接，对于每一个连接都要创建一个SocketChannel
 * DatagramChannel         通过UDP协议读取网络中的Channel
 *
 * 获取通道
 * 1.java针对通道的实现类提供了getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 网络IO:
 * Socket
 * ServerSocket
 * DatagramSocket
 * 2.针对各个通道提供了静态方法openChannel()方法
 * 3.Files工具类的newByteChannel()方法
 *
 * @author booty
 * @date 2021/6/1 11:33
 */
public class ChannelTest {


    /*
    StandardOpenOption是一个枚举类，代表着文件连接时的标准选项。通常可以多个选项一起使用。
        READ                    以读的方式连接文件。
        WRITE                   以写的方式连接文件。
        APPEND                  以追加的方式连接文件，不会覆盖文件原本内容，在后面追加
        TRUNCATE_EXISTING       如果文件存在并且以WRITE的方式连接时就会把文件内容清空，文件设置为0字节大小，如果文件只以READ连接 时，该选项会被忽略
        CREATE                  创建一个文件，如果文件已存在，就打开文件连接。与CREATE_NEW同时存在时该选项会被忽略
        CREATE_NEW              创建一个文件，如果文件已存在，如果已经存在会抛异常
        DELETE_ON_CLOSE         通道关闭时删除文件
        SPARSE                  创建稀疏文件，与CREATE_NEW选项配合使用
        SYNC                    要求每次写入要把内容和元数据刷到存储设备上
        DSYNC                   要求每次写入那内容刷到存储设备上
     */



    /**
     * 使用流->通道->buffer完成文件的复制
     * (实际使用务必try、catch+finally，在finally内关闭流或通道)
     */
    @Test
    public void test1() throws Exception {
        //创建流
        FileInputStream fis = new FileInputStream(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email.html");
        FileOutputStream fos = new FileOutputStream(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email2.html");

        //获取通道
        FileChannel ic = fis.getChannel();
        FileChannel oc = fos.getChannel();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //循环读取输入通道中的数据并写入到缓冲区中
        while (ic.read(buffer) != -1) {
            //将缓冲区切换为读模式
            buffer.flip();
            //读取缓冲区中的数据并写入输出通道中
            oc.write(buffer);
            //清空缓冲区的数据
            buffer.clear();
        }
        fis.close();
        fos.close();
    }


    /**
     * 使用直接缓冲区->MappedByteBuffer完成文件的复制
     * (实际使用务必try、catch+finally，在finally内关闭流或通道)
     */
    @Test
    public void test2() throws Exception {
        //获取文件路径
        Path ip = Paths.get(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email.html");
        Path op = Paths.get(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email3.html");

        //创建读通道和写通道 StandardOpenOption枚举，CREATE代表不论有没有都创建，CREATE_NEW代表仅当没有文件时才会创建，已有文件时报错
        FileChannel ic = FileChannel.open(ip, StandardOpenOption.READ);
        FileChannel oc = FileChannel.open(op, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //获取直接缓冲区的内存映射文件，指定大小为读通道的大小
        MappedByteBuffer im = ic.map(FileChannel.MapMode.READ_ONLY, 0, ic.size());
        MappedByteBuffer om = oc.map(FileChannel.MapMode.READ_WRITE, 0, ic.size());

        //用于储存数据的数组，大小为内存映射文件的大小
        byte[] array = new byte[im.limit()];

        //读取输通道的内存映射文件中的数据
        im.get(array);

        //将数据写入到输出通道的内存映射文件
        om.put(array);

        //关闭通道
        ic.close();
        oc.close();
    }


    /**
     * 使用通道之间的数据传输完成文件复制（也是使用的直接缓冲区）
     * (实际使用务必try、catch+finally，在finally内关闭流或通道)
     */
    @Test
    public void test3() throws Exception {
        //获取文件路径
        Path ip = Paths.get(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email.html");
        Path op = Paths.get(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email4.html");
        Path op2 = Paths.get(this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email5.html");

        //创建读通道和写通道 StandardOpenOption枚举，CREATE代表不论有没有都创建，CREATE_NEW代表仅当没有文件时才会创建，已有文件时报错
        FileChannel ic = FileChannel.open(ip, StandardOpenOption.READ);
        FileChannel oc = FileChannel.open(op, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);
        FileChannel oc2 = FileChannel.open(op2, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //将数据写出到其他通道
        ic.transferTo(0, ic.size(), oc);

        //从其他通道读取数据
        oc2.transferFrom(ic, 0, ic.size());

        //关闭通道
        ic.close();
        oc.close();
        oc2.close();
    }

    /**
     * 分散(Scatter)和聚集(Gather)
     *
     * 分散读取(Scattering Reads)   将通道中的数据分散到多个缓冲区中
     * 聚集写入(Gathering Writes)   将多个缓冲区的数据聚集到通道中
     */
    @Test
    public void test4() throws Exception {

        String path = this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email.html";
        String path2 = this.getClass().getClassLoader().getResource("").getPath() + File.separator + "email6.html";
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        RandomAccessFile of = new RandomAccessFile(path2, "rw");
        FileChannel channel = raf.getChannel();
        FileChannel channel2 = of.getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(10);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers={buffer1,buffer2};


//        //分散读取(若此时缓冲区容量不如文件大，应使用while循环)
//        channel.read(buffers);
//        Arrays.stream(buffers).map(ByteBuffer::flip).forEach(e->{
//            System.out.println(new String(((ByteBuffer) e).array()));
//            System.out.println("===================================");
//            e.clear();
//        });
//
//        //聚集写入
//        channel2.write(buffers);


        //分散读取+聚集写入
        while (channel.read(buffers)!=-1){
            Arrays.stream(buffers)
                    .forEach(e -> {
                        e.flip();
                        System.out.println(new String(e.array()));
                        System.out.println("===================================");
                        e.clear(); });
        }

        channel.close();
        channel2.close();
    }




}
