package dataStructure.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 编写一个栈，用来计算给定字符串的结果
 * 括号
 *
 * @author booty
 * @date 2021/6/22 13:57
 */
public class MathStack<T> {
    private int top;
    private Object[] data;

    public MathStack(int cap) {
        top = -1;
        data = new Object[cap];
    }

    public boolean isFull() {
        return top == data.length - 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public T pop() {
        if (isEmpty()) throw new RuntimeException("栈已空");
        return (T) data[top--];
    }

    public void push(T in) {
        if (isFull()) throw new RuntimeException("栈已满");
        data[++top] = in;
    }

    public void show() {
        for (int i = top; i >= 0; i--) {
            System.out.println(data[i]);
        }
    }

    public int math(String str) {
        //验证是否只有数字和运算符号
        boolean matches = str.matches("\\d+([+*/-]\\d+)+");
        if (!matches) throw new RuntimeException("只能包含数字和加减乘除");
        //根据运算符号分割字符串，获取数字的数组
        String[] nums = str.split("[+*/-]");
        //根据数字分割字符串，获取运算符号的数组
        String[] symbols = str.split("\\d+");
//        Object[] symbols = Arrays.stream(str.split("\\d+")).filter(e -> !e.isEmpty()).toArray();


        //符号栈
        MathStack<Character> symbolStack = new MathStack<>(symbols.length);
        //数字栈
        MathStack<Integer> numStack = new MathStack<>(nums.length);
        //将第一个数字入栈
        numStack.push(Integer.parseInt(nums[0]));

        //根据数字分割后的符号数组第一位是空白，此处直接获取
        for (int i = 1; i < symbols.length; i++) {
            char symbol = symbols[i].charAt(0);
//            //判断运算符号栈是否为空
//            if (symbolStack.isEmpty()) {
//                symbolStack.push(symbol);
//                numStack.push(Integer.parseInt(nums[i]));
//                break;
//            }

            //乘除直接计算
            switch (symbol) {
                case '+':
                case '-': {
                    //若前方还有加减操作未完成，先将其完成
                    if (!symbolStack.isEmpty()) {
                        //因乘除操作不会入栈，所以只有加减
                        Character pop = symbolStack.pop();
                        Integer last = numStack.pop();
                        Integer front = numStack.pop();
                        if (pop.equals('+')) {
                            numStack.push(last + front);
                        } else {
                            numStack.push(front - last);
                        }
                    }
                    //将下一个加减操作入栈
                    symbolStack.push(symbol);
                    numStack.push(Integer.parseInt(nums[i]));
                    break;
                }
                case '*': {
                    Integer pop = numStack.pop();
                    numStack.push(pop * Integer.parseInt(nums[i]));
                    break;
                }
                case '/': {
                    Integer pop = numStack.pop();
                    numStack.push(pop / Integer.parseInt(nums[i]));
                    break;
                }
                default:
                    throw new RuntimeException("符号不正确");
            }
        }

        //继续未完成的加减操作
        while (!symbolStack.isEmpty()) {
            Character pop = symbolStack.pop();

            switch (pop) {
                case '+': {
                    Integer last = numStack.pop();
                    Integer front = numStack.pop();
                    numStack.push(last + front);
                    break;
                }
                case '-': {
                    Integer last = numStack.pop();
                    Integer front = numStack.pop();
                    numStack.push(front - last);
                    break;
                }
                default:
                    throw new RuntimeException("符号不正确");
            }
        }


        return numStack.pop();
    }


    public static void main(String[] args) {
        MathStack<String> stack = new MathStack<String>(10);
        int math = stack.math("20-2*3*4/2+6/2-10/2/5+2/2");
        System.out.println(math);


    }

}
