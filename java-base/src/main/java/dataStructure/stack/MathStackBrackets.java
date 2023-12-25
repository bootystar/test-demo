package dataStructure.stack;

/**
 * 字符串计算器
 * 输入指定字符串的计算表达式，进行运算
 * 仅支持 加减乘除和括号
 * @author booty
 * @date 2021/6/22 16:11
 */
public class MathStackBrackets<T> {
    private int top;
    private Object[] data;

    public MathStackBrackets(int cap) {
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

    public static Double math(String str) {
        //将字符串转换为char数组
        char[] chars = str.toCharArray();
        //用于存放待运算数字的栈（因每次运算会检查之前是否有未完成的运算，所以栈中数字最多为3个）
        MathStackBrackets<Double> numStack = new MathStackBrackets<>(3);
        //用于存放待运算符号的栈（因每次运算会检查之前是否有未完成的运算，所以栈中运算符号最多为2个）
        MathStackBrackets<String> symbolStack = new MathStackBrackets<>(2);
        //循环遍历char数组，针对对应符号做处理
        for (int i = 0; i < chars.length; i++) {
            String current = chars[i] + "";
            //若当前为数字，则检查下一位是否还为数字
            if (current.matches("\\d")) {
                //获取i的下一位
                while (i < chars.length - 1) {
                    String next = chars[++i] + "";
                    if (next.matches("[0-9.]")) {
                        //若还为数字或小数点则拼接
                        current = current + next;
                    } else {
                        //若不为数字，将i-1，使下次for循环的i指向符号
                        i--;
                        break;
                    }
                }
                //将数字入数字栈
                numStack.push(Double.parseDouble(current));
            }
            //加减乘除
            else if (current.matches("[+-/*]")) {
                //若运算符栈为空，则直接入栈
                if (symbolStack.isEmpty()) {
                    symbolStack.push(current);
                } else {
                    //判断当前操作为加减还是乘除
                    if (current.matches("[+-]")) {
                        //若为加减，先完成之前的所有操作，然后将运算符入栈
                        while (!symbolStack.isEmpty()) {
                            String oldSymbol = symbolStack.pop();
                            Double last = numStack.pop();
                            Double front = numStack.pop();
                            switch (oldSymbol) {
                                case "+": {
                                    numStack.push(last + front);
                                    break;
                                }
                                case "-": {
                                    numStack.push(front - last);
                                    break;
                                }
                                case "*": {
                                    numStack.push(last * front);
                                    break;
                                }
                                case "/": {
                                    numStack.push(front / last);
                                    break;
                                }
                            }
                        }
                        symbolStack.push(current);
                    }
                    //若为乘除，则完成上一个乘除操作后入栈,否则直接入栈
                    else {
                        String oldSymbol = symbolStack.pop();
                        switch (oldSymbol) {
                            case "*": {
                                Double last = numStack.pop();
                                Double front = numStack.pop();
                                numStack.push(last * front);
                                break;
                            }
                            case "/": {
                                Double last = numStack.pop();
                                Double front = numStack.pop();
                                numStack.push(front / last);
                                break;
                            }
                            default: {
                                //之前操作不为乘除，还原上一个操作符号
                                symbolStack.push(oldSymbol);
                            }
                        }
                        symbolStack.push(current);
                    }
                }
            }
            //若为小括号，则将小括号内的字符串进行拼串，并循环调用
            else if (current.matches("[(]")) {
                int count = 1;
                String childExpression = "";
                while (true) {
                    if (i==chars.length-1) throw new RuntimeException("左右括号不匹配");
                    String next = chars[++i] + "";
                    if (next.equals("(")) count++;
                    if (next.equals(")")) count--;
                    if (count == 0) break;
                    childExpression = childExpression + next;
                }
                Double bracketsResult = math(childExpression);
                numStack.push(bracketsResult);
            }
            //剩下均为非法字符
            else {
                throw new RuntimeException("第"+i+"个字符为非法符号：" + chars[i]);
            }

        }

        //检查是否有未完成的运算符
        while (!symbolStack.isEmpty()) {
            String symbol = symbolStack.pop();
            Double last = numStack.pop();
            Double front = numStack.pop();
            switch (symbol) {
                case "+": {
                    numStack.push(last + front);
                    break;
                }
                case "-": {
                    numStack.push(front - last);
                    break;
                }
                case "*": {
                    numStack.push(last * front);
                    break;
                }
                case "/": {
                    numStack.push(front / last);
                    break;
                }
            }
        }
        return numStack.pop();
    }


    public static void main(String[] args) {
        Double math = MathStackBrackets.math("21.5-2*3*4/(2+6)/2-10/2/5+2/2");
        System.out.println(math);
        Double math2 = MathStackBrackets.math("((20-2*(3-1)-6+10)+80)/2");
        System.out.println(math2);
    }

}
