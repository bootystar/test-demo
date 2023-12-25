package thread;

/**
 * 线程8锁
 *
 * 1.一个对象，两个普通同步方法，两个线程标准打印
 *      one two
 * 2.一个对象，两个普通同步方法，在getOne()方法内使线程休眠2秒
 *      one two
 * 3.一个对象，两个普通同步方法，一个普通方法getThree；3
 *      three one two
 * 4.两个对象，两个普通同步方法，分别调用
 *      two  one
 * 5.一个对象，一个普通同步方法，一个静态同步方法getOne
 *      two one
 * 6.一个对象，两个静态同步方法
 *      one two
 * 7.两个对象，一个普通同步方法，一个静态同步方法getOne，分别调用
 *      two one
 * 8.两个对象，两个静态同步方法
 *      one two
 *
 * 结论：对象锁A，静态锁B，
 *      AB两锁之间互相无关系
 *      多个对象之间的对象锁互相独立，
 *      多个对象之间的静态锁为同一把，唯一
 *      对象锁锁的是当前对象，静态锁锁的是该类对应的.class实例
 *
 * @author booty
 * @date 2021/5/31 15:28
 */
public class Tread8LockTest {
    public static void main(String[] args) {
        NumberDemo num=new NumberDemo();
        NumberDemo num2=new NumberDemo();
        new Thread(num::getOne).start();
//        new Thread(num::getTwo).start();
        new Thread(num2::getTwo).start();
        new Thread(num::getThree).start();
    }
}


class NumberDemo{

    public synchronized void getOne(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo(){
        System.out.println("two");
    }

    public void getThree(){
        System.out.println("three");
    }
}