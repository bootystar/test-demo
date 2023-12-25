package thread;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子操作
 * 多线程安全问题
 *
 *
 * @author booty
 * @date 2021/5/28 13:41
 */
public class AtomicTest {
    /*
       i++的原子性问题，
        该操作实际分为3步，读改写
        i++;
        int temp = i ;
        i = i+1;
        i = temp

        JUC提供atomic原子包提供数据的原子性操作
            1、使用volatil 保证内存可见性
            2、CAS(compare-and-swap)保证数据的原子性
                  CAS包含3个值
                  内存值   V
                  预估值   A
                  更新值   B
                当且仅当V==A 时，令V=B，否则将不做任何操作
     */

    public static void main(String[] args) throws InterruptedException {
        AtomicDemo demo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo).start();
        }
        Thread.sleep(1000);
        System.out.println("\n================");

        AtomicDemo2 demo2=new AtomicDemo2();
        for (int i = 0; i < 10; i++) {
            new Thread(demo2).start();
        }
        /*
        10个线程同时运行，运行调用get方法获取数字并使数字+1，
        若正常单线程，此处应不会出现重复数字
        实测多线程出现会重复数字，此时造成了线程安全问题，
        更换为atomic原子类后可解决该问题
         */

    }

}


class AtomicDemo implements Runnable {
    private int num = 0;

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(200);
        System.out.print(getNum());
    }

    public int getNum() {
        return num++;
    }
}

class AtomicDemo2 implements Runnable {
    private AtomicInteger num = new AtomicInteger(0);

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(200);
        System.out.print(getNum());
    }

    public int getNum() {
        return num.getAndIncrement();
    }

}

