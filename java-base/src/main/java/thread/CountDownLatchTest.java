package thread;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁CountDownLatch
 * 在完成某些运算时，只有其他线程的运算全部完成，当前运算才会执行
 * FutureTask取返回值类似此原理，只有线程执行完成后，当前线程才能去的结果
 * @author booty
 * @date 2021/5/31 10:01
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        // 设定计数为10，每个线程执行完成后调用latch.countDown()将计数减一
        final CountDownLatch latch=new CountDownLatch(10);
        LathDemo demo=new LathDemo(latch);
        long start=System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            new Thread(demo).start();
        }

        /*
        多线程计算执行时间是无法计算的，因线程之间互不干扰
        此情况就需要使用CountDownLatch
        新建CountDownLatch时执行计数
        在执行的线程任务中执行完成后调用countDown方法使计数器-1
        在需要等待的线程内调用CountDownLatch的await()方法，
        此时只有在CountDownLatch计数器归零时，等待的线程才会继续执行
         */
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end=System.currentTimeMillis();
        System.out.println("所有线程执行完毕，耗费时间:"+(end-start));

    }
}


class LathDemo implements Runnable{
    private CountDownLatch latch;

    public LathDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
//        for (int i = 0; i < 50000; i++) {
//            if (i%2==0){
//                System.out.println(i);
//            }
//        }
        synchronized (this){
            try {
//                for (int i = 0; i < 50000; i++) {
//                    if (i%2==0){
//                        System.out.println(i);
//                    }
//                }
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName()+ "执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }
}
