package thread;

import lombok.Data;

/**
 * volatile关键字
 * 线程可见性
 *
 * @author booty
 * @date 2021/5/28 10:45
 */
public class VolatileTest {


    public static void main(String[] args) {
        ThreadDemo threadDemo=new ThreadDemo();
        Thread thread = new Thread(threadDemo);
        //线程1将flag从false改为true
        thread.start();

        //主线程在flag为true时打印数据并退出循环
        while (true){
            if (threadDemo.isFlag()){
                System.out.println("========");
                break;
            }
        }

        /*
        线程之间共享变量的修改不可见，main线程会一直死循环，读取的flag一直为false
        所以共享变量需要使用volatile关键字修饰，让其在多线程中数据修改可见，但不能保证数据的原子性
         */
    }

}

@Data
class ThreadDemo implements Runnable {
    private boolean flag = false;

    @Override
    public void run() {
        try {
            //确保该线程在主线程之后执行
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag=true;
        System.out.println("flag="+isFlag());
    }
}