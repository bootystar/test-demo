package nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Buffer缓冲区
 * 每个基本数据类型对应了相应数据类型的缓冲区，缓冲区的基本使用方式基本完全一致
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 *
 * 缓冲区的4个核心属性：
 * capacity 缓冲区的最大容量，声明后不能改变
 * limit    界限，缓冲区中可以操作的数据大小，limit后的数据不能进行读写
 * position 表示缓冲区中正在操作数据的位置
 * mark     标记位置，程序员手动标记的位置
 *
 * position<=limit<=capacity
 *
 *
 * 缓冲区的使用方式基本一致，此处使用ByteBuffer作为测试
 * 通过allocate()获取指定大小的缓冲区
 *
 * 通过put()存入数据
 * 通过get()获取数据
 *
 * limit、position与缓冲区的读写模式：
 * Buffer有分读模式和写模式，其实质是由limit值和position值决定的。这种模式没有特定的死规定。
 *
 * 写模式：
 *      往缓冲区Buffer里面填充数据的模式。Buffer刚刚创建好时就是处于这个模式。
 * 读模式：
 *      从缓冲区数组中读取缓冲区数组数据的模式。
 *
 * 假设我们没有把缓冲区从写模式切换到读模式时读取数据，
 * 通道channel读取缓冲区数据时就会从position=上次写入后的position的下标读取到limit的下标。
 * 也就是读取缓冲区数组的还未写入数据的下标，这些下标的元素是没有被写入的，所以是错误的。
 *
 * 直接缓冲区和非直接缓冲区
 *  非直接缓冲区创建在jvm内存中
 *  直接缓冲区创建在系统物理内存中，只有在jvm执行垃圾回收时才会回收
 *
 * 直接缓冲区
 *      cpu-> 物理内存-> 应用程序
 * 非直接缓冲区
 *      cpu-> 内核内存地址空间-> 用户内存地址空间->应用程序
 *
 *  一般系统为了安全，会将物理内存分为内核地址空间和用户地址空间，
 *  用户无法操作内核内存，cpu只和内核内存交互，用户需要从内核内存中获取数据并复制到用户内存中才能操作
 *  直接缓冲区的效率更高，减少一次内存复制的开销
 *  直接缓冲区分配和取消成本耗费资源高于非直接缓冲区
 *
 * @author booty
 * @date 2021/6/1 09:27
 */
public class BufferTest {

    /**
     * 获取ByteBuffer对象的方法
     *
     * 1. 获取非直接缓冲区的ByteBuffer，capacity参数指定缓冲区容量。
     * public static ByteBuffer allocate(int capacity);
     *
     * 2. 通过传入一个字节数组来获取ByteBuffer对象。相当于把传进来的字节数组封装成一个ByteBuffer对象。
     *    如果传进来的数组有数据的话，可以直接进行读，此时ByteBuffer会处于读模式。
     *    创建的初始ByteBuffer的limit=该数组的长度length，position=0，所以读取的话会完全读取该数组的。
     * public static ByteBuffer wrap(byte[] array);
     *
     * 3. 通过传入一个字节数组来获取ByteBuffer对象。相当于把传进来的字节数组封装成一个ByteBuffer对象。
     *    并且可以指定position（用参数offset指定），limit（用length+offset指定）。
     * public static ByteBuffer wrap(byte[] array,int offset, int length)
     *
     * 以上三种方法获取的都是非直接缓冲区
     *
     * 4.获取直接缓冲区，并用capacity指定缓冲区的大小。
     * public static ByteBuffer allocateDirect(int capacity)
     *
     */
    @Test
    public void create(){
        /*
        1. 获取非直接缓冲区的ByteBuffer，capacity参数指定缓冲区容量。
         */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        // position=0,limit=1024,capacity=1024


        /*
        2. 通过传入一个字节数组来获取ByteBuffer对象。相当于把传进来的字节数组封装成一个ByteBuffer对象。
           如果传进来的数组有数据的话，可以直接进行读，此时ByteBuffer会处于读模式。
           创建的初始ByteBuffer的limit=该数组的长度length，position=0，所以读取的话会完全读取该数组的。
         */
        byte[] b1 = new byte[1024];
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(b1);
        System.out.println("position = " +byteBuffer1.position()+ ",limit = " + byteBuffer1.limit()+",capacity = " + byteBuffer1.capacity());
        // position=0,limit=1024,capacity=1024

        /*
        3. 通过传入一个字节数组来获取ByteBuffer对象。相当于把传进来的字节数组封装成一个ByteBuffer对象。
           并且可以指定position（用参数offset指定），limit（用length+offset指定）
         */
        byte[] b2 = new byte[1024];
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(b1,10,500);
        System.out.println("position = " +byteBuffer2.position()+ ",limit = " + byteBuffer2.limit()+",capacity = " + byteBuffer2.capacity());
        // position=10,limit=500,capacity=1024


        /*
        4.获取直接缓冲区，并用capacity指定缓冲区的大小。
         */
        ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(1024);
        System.out.println("position = " +byteBuffer3.position()+ ",limit = " + byteBuffer3.limit()+",capacity = " + byteBuffer3.capacity());
        // position=0,limit=1024,capacity=1024
    }

    /**
     * 对缓存区写数据的API
     *
     * 1.往缓冲区中写入一个字节的数据，游标右移一位。返回缓冲区自身，可进行链式写入。
     * public abstract ByteBuffer put(byte b);
     *
     * 2.往缓冲区数组的某个位置写入一个字节数据，返回缓冲区自身，可进行链式写入。此操作不会使游标移动。
     * public abstract ByteBuffer put(int index, byte b);
     *
     * 3.往缓冲区中添加另一个缓冲区的数据，会读取参数src的position到limit-1下标的数据。
     *   如果该src的可读数据长度比调用该方法的可写缓冲区的数据长度长的话，就会报错。
     * public ByteBuffer put(ByteBuffer src);
     *
     * 4.往缓冲区里面添加一个字节数组，并可以指定src数组的偏移量和长度。
     *   添加之后缓冲区的position=oldPosition+length
     * public ByteBuffer put(byte[] src, int offset, int length);
     *
     *  5.往缓冲区里面添加一个字节数组,添加完后position=oldPosition+length
     *  public final ByteBuffer put(byte[] src)
     */
    @Test
    public void write(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        /*
        1.往缓冲区中写入一个字节的数据，游标右移一位。返回缓冲区自身，可进行链式写入。
         */
        byteBuffer.put("a".getBytes()).put("b".getBytes());
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //ab               position = 2,limit = 10,capacity = 10         代表无数据

        /*
        2.往缓冲区数组的某个位置写入一个字节数据，返回缓冲区自身，可进行链式写入。此操作不会使游标移动。
         */
        byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put(2,(byte) 'c');
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //  c              position = 0,limit = 10,capacity = 10        代表无数据


        /*
        3.往缓冲区中添加另一个缓冲区的数据，会读取参数src的position到limit-1下标的数据。
          如果该src的可读数据长度比调用该方法的可写缓冲区的数据长度长的话，就会报错
         */
        byteBuffer=ByteBuffer.allocate(10);
        ByteBuffer src = ByteBuffer.allocate(5);
        src.put("hh".getBytes());
        src.flip();
        byteBuffer.put(src);
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //hh               position = 2,limit = 10,capacity = 10         代表无数据

        /*
        4.往缓冲区里面添加一个字节数组，并可以指定src数组的偏移量和长度。
          添加之后缓冲区的position=oldPosition+length
         */
        byteBuffer=ByteBuffer.allocate(10);
        byte[] bytes = {64,65,66,67,68};
        byteBuffer.put(bytes,1,3);
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //ABC              position = 3,limit = 10,capacity = 10         代表无数据
        /*
        5.往缓冲区里面添加一个字节数组,添加完后position=oldPosition+length
         */
        byteBuffer=ByteBuffer.allocate(10);
        byteBuffer.put("hello".getBytes(StandardCharsets.UTF_8));
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //hello            position = 5,limit = 10,capacity = 10         代表无数据


    }


    /**
     * 获取缓冲区数据的api
     *
     * 1.读取缓冲区的下一字节数据，position右移一位。
     * public abstract byte get();
     *
     * 2.读取缓冲区的指定下标字节数据，position不移动。
     * public abstract byte get(int index);
     *
     * 3.读取缓冲区的数据到dst数组。
     *   position右移dst.length()。
     *   返回自身，可链式调用
     * public ByteBuffer get(byte[] dst);
     *
     * 4.读取缓冲区的数据到dst数组,可以指定读到dst的偏移量和读取数据的长度。
     *   position右移length。
     *   返回自身，可链式调用
     * public ByteBuffer get(byte[] dst, int offset, int length);
     *
     *
     */


    @Test
    public void read(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put("HelloWorld".getBytes());
        byteBuffer.flip();
        System.out.print(new String(byteBuffer.array())+"       ");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //HelloWorld       position = 0,limit = 10,capacity = 10

        /*
        读取缓冲区的下一字节数据，position右移一位
         */
        byteBuffer.clear();
        byte b = byteBuffer.get();
        System.out.println(new String(new byte[]{b})+"");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //H         position = 1,limit = 10,capacity = 10


        /*
        读取缓冲区的指定下标字节数据，position不移动
         */
        byteBuffer=ByteBuffer.allocate(10);
        byteBuffer.put("HelloWorld".getBytes());
        byteBuffer.flip();
        byte b1 = byteBuffer.get(0);
        System.out.println(new String(new byte[]{b})+"");
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //H         position = 0,limit = 10,capacity = 10

        /*
        读取缓冲区的数据到dst数组,可以指定读到dst的偏移量和读取数据的长度,position右移数组长度length
         */
        byteBuffer=ByteBuffer.allocate(10);
        byteBuffer.put("HelloWorld".getBytes());
        byteBuffer.flip();
        byte[] bytes=new byte[5];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //Hello        position = 5,limit = 10,capacity = 10

        byteBuffer=ByteBuffer.allocate(10);
        byteBuffer.put("HelloWorld".getBytes());
        byteBuffer.flip();
        byte[] bytes2=new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes2,5,5);
        System.out.println(new String(bytes2));
        System.out.println("position = " + byteBuffer.position()+",limit = " + byteBuffer.limit()+",capacity = " + byteBuffer.capacity());
        //     Hello        position = 5,limit = 10,capacity = 10

    }


    /**
     * 字符集和CharBuffer
     */
    @Test
    public void testCharacter() throws Exception{
        //获取字符集
        Charset charset=Charset.forName("UTF-8");
        //获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        CharBuffer charBuffer=CharBuffer.allocate(1024);
        charBuffer.put("aaaa测试cccc");
        charBuffer.flip();

        //编码
        ByteBuffer byteBuffer=charsetEncoder.encode(charBuffer);

        //获取编码后的数据
        while (byteBuffer.hasRemaining()){
            System.out.print(byteBuffer.get()+" ");
        }
        System.out.println("=========");

        //解码
        byteBuffer.flip();
        CharBuffer charBuffer2=charsetDecoder.decode(byteBuffer);

        //获取解码后的数据
        while (charBuffer2.hasRemaining()){
            System.out.print(charBuffer2.get()+" ");
        }

    }


    /*
    其他方法


    //返回一个只读缓冲区，返回的缓冲区与原本的缓冲区不是同一个缓冲区，是不同的对象。
    //与原本缓冲区的数据和元数据都一样，只是他是只读的。
    public ByteBuffer asReadOnlyBuffer();

    //返回一个int缓冲区。其他类型的也差不多。
    public IntBuffer asIntBuffer();

    //将字节缓冲区的当前position加后面3位byte数据（共四位）转换成一个int类型返回。
    //游标position右移4
    public abstract int getInt();

    //将字节缓冲区的i下标加后面3位byte数据（共四位）转换成一个int类型返回。
    //游标不移动
    public int getInt(int i)

    //将value值存进缓冲区当前position+后3位，存进去。
    //游标position右移4
    putInt(int value);

    //将value值存进缓冲区index+后3位，存进去。
    //游标不移动
    putInt(int index, int value)

    //上面的操作其他类型的相关操作类似，只是转成的byte数组长度不一样，例如int会转成4，long转成8.//

    //返回一个当前ByteBuffer 的副本。是一个新对象。
    public ByteBuffer duplicate();
     */






    /**
     * 缓冲区的4个核心属性：
     * capacity 缓冲区的最大容量，声明后不能改变
     * limit    界限，缓冲区中可以操作的数据大小，limit后的数据不能进行读写
     * position 表示缓冲区中正在操作数据的位置
     * mark     标记位置，程序员手动标记的位置、
     *
     * 操作该4个属性的方法：
     *
     * flip()方法
     * 该方法用于将缓冲区切换到读模式，
     * 使用该方法后，position会重置为，limit会重置为原position的位置
     * （此时，position-limit的范围为能读取数据的范围，也就是之前写入的数据所占据的范围）
     *
     * rewind()方法
     * 使读取的数据可以再次重复读取
     *
     * clear()方法
     * 清空缓冲区，但缓冲区内之前的数据仍然存在
     * 只是数据处于被遗忘状态，仍然可以被读取到，再次写入相同位置数据后才会覆盖原数据
     *
     * mark()方法
     * 标记当前position
     *
     * reset()方法
     * 将position设置为mark()方法标记的位置
     *
     * remaining()方法
     * 获取缓冲区可操作的剩余数据量（长度）
     */
    @Test
    public void testHandleMethod(){
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //存入数据
        buffer.put("ABCD".getBytes(StandardCharsets.UTF_8));

        //切换为读模式
        buffer.flip();

        //一般获取方法
        while (buffer.hasRemaining()){
            char c=(char)buffer.get();
            System.out.print(c);
        }

        //重复读
        buffer.rewind();

        //使用数据长度对应长度的数组来接收参数
        byte[] arr=new byte[buffer.limit()];
        buffer.get(arr);
        System.out.println("\n"+new String(arr));

        //清空缓冲区
        buffer.clear();
        System.out.println((char) buffer.get());
        buffer.clear();

        //使用数据接收指定位置参数offset：开始位置，length：需要获取的长度
        buffer.put("ABCD".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        byte[] bytes=new byte[buffer.limit()];
        buffer.get(bytes,0,2);
        System.out.println(new String(bytes,0,2));

        //标记(此时position为2，对应的字符为C)
        buffer.mark();
        //读取数据，将position的位置改变
        buffer.get(bytes,2,2);
        System.out.println(new String(bytes,2,2));

        //重置到之前标记的位置
        buffer.reset();
        System.out.println((char) buffer.get());

        //获取缓冲区可操作的数据的剩余数量
        System.out.println(buffer.remaining());
    }




}
