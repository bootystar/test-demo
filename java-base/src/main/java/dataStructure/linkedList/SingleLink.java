package dataStructure.linkedList;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Iterator;
import java.util.Stack;

/**
 * 手写一个单链表
 * 并实现其增删改查和遍历方法
 * @author booty
 * @date 2021/6/21 09:38
 */
public class SingleLink {
    public static void main(String[] args) {
        Node node1 = new Node(1,"zhangsan",20);
        Node node2 = new Node(3,"lisi",31);
        Node node3 = new Node(5,"wangwu",32);
        Node node4 = new Node(7,"zhaoliu",24);
        MyLinkedList link = new MyLinkedList();

//        link.add(node1);
//        link.add(node2);
//        link.add(node3);
//        link.add(node4);

        link.addByOrder(node2);
        link.addByOrder(node1);
        link.addByOrder(node3);
        link.addByOrder(node4);


        link.update(new Node(3,"lisisi",22));

//        link.remove(node3);

        link.list();


        System.out.println(link.length());
        System.out.println(link.getLastIndexNode(2));

        System.out.println("========");
        link.reverse().list();


        System.out.println("========");
        link.reversePrint();



        //仅正序有效，先将原队列恢复到正序
        link.reverse();
        System.out.println("========");
        Node node5 = new Node(2,"tianqi",20);
        Node node6 = new Node(4,"wangba",31);
        Node node7 = new Node(8,"lijiu",32);
        Node node8 = new Node(6,"wushi",24);
        MyLinkedList link2 = new MyLinkedList();
        link2.add(node8);
        link2.addByOrder(node6);
        link2.add(node7);
        link2.addByOrder(node5);


        link.insertLink(link2);
        link.list();
    }

}


class MyLinkedList {


    /**
     * 链表头
     * 该节点不储存任何信息，仅用于作为链表开始的标识
     */
    private Node head;


    public MyLinkedList() {
        this.head = new Node();
    }

    /**
     * 遍历
     */
    public void list() {
        Node current = head.next();
        while (current != null) {
            System.out.println(current);
            current = current.next();
        }
    }

    /**
     * 增加
     * 遍历并添加到最后一个节点
     */
    public void add(Node current){
        Node next = head.next();

        if (next==null){
            //插入第一个
            head.setNext(current);
        }else {
            //遍历，插入到后方
            while (next.next()!=null){
                next=next.next();
            }
            next.setNext(current);
        }
    }

    /**
     * 增加
     * 按照node的no属性顺序添加，若出现相同no抛出异常
     */
    public void addByOrder(Node current){
        //思路：找到下一个节点编号大于传入节点的 对应节点即可，将传入节点插入该节点和下一节点之间
        Node temp = head;
        while (true) {
            //无下一个节点则终止遍历
            if (temp.next()==null) break;
            //有下一个节点且下一个节点编号大于传入节点则终止遍历
            if (temp.next().getNo()> current.getNo()) break;
            //编号重复则抛出异常
            if (temp.next().getNo()== current.getNo()) throw new RuntimeException("编号重复");
            //将当前节点修改为下一个节点继续遍历
            temp=temp.next();
        }
        //运行到此处说明无下一个节点或下一个节点编号大于传入节点，将传入节点的下一个节点设置为当前节点的下一个节点
        current.setNext(temp.next());
        //将当前节点的下一个节点修改为传入节点，完成插入
        temp.setNext(current);
    }

    /**
     * 根据编号修改信息
     */
    public void update(Node update){
        Node next=head;
        while (next.next()!=null){
            if(next.next().getNo()==update.getNo()) {
                update.setNext(next.next().next());
                next.setNext(update);
                return;
            }
            next=next.next();
        }
        throw new RuntimeException("未找到对应编号");
    }


    /**
     * 移除对应节点
     */
    public  void  remove(Node remove){
        Node next=head;
        while (next.next()!=null){
            if(next.next().equals(remove)) {
                next.setNext(next.next().next());
                return;
            }
            next=next.next();
        }
        throw new RuntimeException("未找到对应编号");
    }

    /**
     * 获取单链表中有效数据的个数
     */
    public int length(){

        Node current=head;
        int count=0;
        while (current.next()!=null){
            count++;
            current=current.next();
        }
        return count;
    }

    /**
     * 获取单链表指定倒数第N个数据
     */
    public Node getLastIndexNode(int n){
        int length = length()-n;
        if(n<=0||n>length()) throw new RuntimeException("数值不正确");
        Node next = head.next();
        for (int i = 0; i < length; i++) {
            next=next.next();
        }
        return next;
    }


    /**
     * 单链表的顺序反转
     */
    public MyLinkedList reverse(){
        //当前节点的上一个节点
        Node reverse=null;
        //获取正序的第一个节点
        Node next = head.next();
        //正序遍历
        while (next!=null){
            //上个节点
            Node last=reverse;
            //当前节点
            reverse=next;
            //下一节点
            next=next.next();
            //将当前的下一节点，修改为上个节点
            reverse.setNext(last);
        }
        head.setNext(reverse);
        return this;
    }

    /**
     * 单链表的逆序打印
     * (反转打印或使用栈)
     */
    public void reversePrint(){
        Stack<Node> stack=new Stack<>();
        Node next = head.next();
        while (next!=null){
            stack.push(next);
            next=next.next();
        }
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }


    /**
     * 插入一个单链表到链表中，使插入后的链表依然有序
     */
    public void insertLink(MyLinkedList inList){
        Node node = inList.head.next();
        while (node!=null){
            Node current= node;
            node=node.next();
            addByOrder(current);
        }
    }

}
@Data
class Node {
    /**
     * 指向下一个节点的属性
     */
    private Node next;


    private int no;
    private String name;
    private int age;

    /**
     * 重写toString，避免打印子节点
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Node next() {
        return next;
    }

    public Node(int no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }

    public Node() {
    }
}
