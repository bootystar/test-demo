package dataStructure.queue;

/**
 * 数组模拟普通队列
 *
 * @author booty
 * @date 2021/6/21 09:08
 */
public class ArrayNormalQueue {
    /**
     * 队列头
     */
    private int front;
    /**
     * 队列尾部
     */
    private int rear;
    /**
     * 队列数据
     */
    private int[] data;

    public ArrayNormalQueue(int maxSize) {
        //使其指向队列头的前一个位置
        front=-1;
        //指向队列尾部（队列最后一个数据）
        rear=-1;
        data=new int[maxSize];
    }

    private boolean isFull(){
        return rear==data.length-1;
    }

    private boolean isEmpty(){
        return rear==front;
    }

    private void put(int i){
        if (isFull()) throw new RuntimeException();
        data[++rear]=i;
    }


    private int get(){
        if(isEmpty()) throw new RuntimeException();
        return data[++front];
    }





}
