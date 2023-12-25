package thread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 一般读取数据需要线程安全，可以多个线程同时读取
 * 写入数据需要单次只能一个线程写入
 *
 * 也就是线程之间
 * 读读可同时进行，
 * 读写互斥，
 * 写写互斥
 *
 * @author booty
 * @date 2021/5/31 15:14
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {

    }
}

class ReadWriteDemo {
    private int num = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void getNum() {
        //读锁允许多个线程同时持有
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setNum(int num) {
        //写锁单个线程独占
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
        } finally {
            this.num = num;
        }
    }

}