package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，3个线程id分别为abc，每个线程将自己的id打印10遍
 * 输出的结果必须按顺序显示
 *
 * @author booty
 * @date 2021/5/31 10:48
 */
public class OrderPrint {
    public static void main(String[] args) {
        OrderDemo demo=new OrderDemo();
        new Thread(demo::loopA,"A").start();
        new Thread(demo::loopB,"B").start();
        new Thread(demo::loopC,"C").start();

    }

}

class OrderDemo {
    private int num = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void loopA() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                while (num != 1) {
                    c1.await();
                }
                System.out.print(Thread.currentThread().getName());

                num = 2;
                c2.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    public void loopB() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                while (num != 2) {
                    c2.await();
                }
                System.out.print(Thread.currentThread().getName());

                num = 3;
                c3.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    public void loopC() {
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                while (num != 3) {
                    c3.await();
                }
                System.out.print(Thread.currentThread().getName());                System.out.println("----");
                System.out.print("-");
                num = 1;
                c1.signalAll();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }


}