package dataStructure.stack;

import java.util.ArrayList;

/**
 * @author booty
 * @date 2021/6/22 11:42
 */
public class ArrayStack {
    private int top;
    private int [] data;

    public ArrayStack(int cap) {
        top=-1;
        data=new int[cap];
    }


    public boolean isFull(){
        return top==data.length-1;
    }
    public boolean isEmpty(){
        return top==-1;
    }

    public int pop(){
        if (isEmpty()) throw new RuntimeException();
        return data[top--];
    }

    public void push(int in){
        if (isFull()) throw new RuntimeException();
        data[++top]=in;
    }

    public void show(){
        for (int i = top; i >=0 ; i--) {
            System.out.println(data[i]);
        }
    }




}
