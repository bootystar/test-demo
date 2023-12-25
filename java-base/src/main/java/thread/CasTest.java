package thread;

/**
 * CAS自旋锁
 * 比较并交换
 *
 * @author booty
 * @date 2021/5/28 15:45
 */
public class CasTest {
    public static void main(String[] args) {
        final CompareAndSwap cas=new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int value = cas.getValue();
                boolean b = cas.compareAndSet(value, (int)(Math.random()*10));
                int value1 = cas.getValue();
                System.out.println(b+"====>"+Thread.currentThread().getName()+"====>"+value1);
            }).start();
        }
    }
}

class CompareAndSwap{
    private int value;

    /**
     * 获取内存值
     * @return 内存值
     */
    public synchronized int getValue() {
        return value;
    }

    /**
     * 比较交换
     * @param expect 预估内存值
     * @param newValue 要设置的值
     * @return 内存中原来值
     */
    public synchronized int compareAndSwap(int expect,int newValue){
        //比较预估值和内存中的值是否相等
        int oldValue= this.value;
        if (oldValue==expect){
            //相等则设置内存值
            this.value=newValue;
        }
        return oldValue;
    }

    /**
     * 比较预估值是否等于内存值决定是否设置成功
     * @param expect 预估值
     * @param newValue 要设置的值
     * @return 是否成功设置
     */
    public synchronized boolean compareAndSet(int expect,int newValue){
        //预估值与旧值不一致则不做操作
        return expect==compareAndSwap(expect,newValue);
    }
}
