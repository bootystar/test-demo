package thread;

/**
 * 生产者，消费者模式
 *
 * @author booty
 * @date 2021/5/31 13:56
 */
public class ProduceAndCustomerTest {
    public static void main(String[] args){
        ProducerCustomerDemo demo = new ProducerCustomerDemo();
        new Thread(demo::produce,"1").start();
        new Thread(demo::produce,"2").start();
        new Thread(demo::produce,"3").start();
        new Thread(demo::custom,"====").start();

    }

}


class ProducerCustomerDemo {
    private int i=0;


    public void produce()  {
        synchronized (this) {
            while (i < 5) {
                System.out.println("生产者"+Thread.currentThread().getName()+"生产，数量：" + ++i);
                try {
                    Thread.sleep(500);
                    notifyAll();
                    System.out.println("生产者"+Thread.currentThread().getName()+"等待");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void custom() {
        synchronized (this) {
            while (i > 0) {

                System.out.println("消费者"+Thread.currentThread().getName()+"消费，数量：" + --i);

                try {
                    Thread.sleep(500);
                    notifyAll();
                    System.out.println("消费者"+Thread.currentThread().getName()+"等待");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}