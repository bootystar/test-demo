package dataStructure.queue;

import java.util.Arrays;

/**
 * 利用数组编写一个环形队列
 *
 * @author booty
 * @date 2021/6/18 15:52
 */
public class ArrayCircleQueue {
    /**
     * 队列头
     */
    private int front;
    /**
     * 队列尾
     */
    private int rear;
    /**
     * 队列数据
     */
    private int[] data;

    /**
     * 构造器，需要指定最大队列长度
     * 此处+1的原因是要留出一个空位来判断是否满，
     * 不然当rear==front的时候无法判断队列是空还是满
     * @param maxSize 最大长度
     */
    public ArrayCircleQueue(int maxSize) {
        front=0;
        rear=0;
        data=new int[maxSize+1];
    }

    /**
     * 判断队列是否已满
     * 因其为环型队列，所以可能出现队列尾小于队列头的情况（例如头部为下标为4，总长度为5，尾部为0）
     * @return 队列是否已满
     */
    public boolean isFull(){
        // 尾部后面是头则满，此处取余数可以始终获取尾部后一个元素的下标
        return  (rear+1) % data.length == front;
    }

    /**
     * 判断队列是否为空
     * @return 是否为空
     */
    public boolean isEmpty(){
        //头尾指向同一个格时表示无数据
        return rear==front;
    }

    /**
     * 添加方法
     * @param i 添加的元素
     */
    public void add(int i){
        //队列已满不允许添加
        if(isFull())  throw new RuntimeException();

        //放入数据
        data[rear]=i;

        //队列尾部位置++,若大于总长度则取余
        rear=++rear % data.length;

    }

    /**
     * 获取方法
     */
    public int get(){
        //队列为空不能获取
        if(isEmpty()) throw new RuntimeException();
        int value = data[front];
        //将队列头向后移
        front = ++front % data.length;
        return value;
    }

    /**
     * 获取有效数据的个数
     * @return 有效数据个数
     */
    public int total(){
        //此处使用 队列尾+总长-队列头，避免队列尾小于队列头，若结果大于总长，则减去一个总长（取余）
        return (rear+data.length-front) % data.length;
    }


    /**
     * 获取所有有效数据（不改变队列头尾）
     *
     */
    public int[] show(){

        //创建临时数组，长度为有效数据长度
        int[] nums=new int[total()];
        //遍历
        for (int i = 0; i < nums.length; i++) {
            //此处依次获取队列头+i的值，若大于等于队列总厂则减去一个队列总厂（取余）
            nums[i]=data[ (front+i) % data.length ];
        }
        return nums;
    }


    public static void main(String[] args) {
        ArrayCircleQueue ca=new ArrayCircleQueue(5);

        for (int i = 0; i < 5; i++) {
            ca.add(i);
        }
        System.out.println(Arrays.toString(ca.show()));
        for (int i = 0; i < 3; i++) {
            System.out.print(ca.get());
        }
        System.out.println();


        for (int i = 0; i < 3; i++) {
            ca.add(i+10);
        }
        System.out.println(Arrays.toString(ca.show()));
        System.out.println(ca.get());

    }
}