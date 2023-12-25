package dataStructure.linkedList;

/**
 * 双向链表
 * node节点多出一个指针指向上一个节点
 * 其他逻辑和单链表相同
 * @author booty
 * @date 2021/6/22 09:03
 */
public class DoubleLink {
    public static void main(String[] args) {

    }
}
class TLinkedList{
    private TNode head;
    private TNode tail;

    public TLinkedList() {
        this.head=new TNode(0,"head");
        this.tail=new TNode(0,"tail");
        head.next=tail;
        tail.pre=head;
    }

    /**
     * 增加（尾部）
     */
    public void add(TNode tNode){
        TNode next = head.next;
        while (next!=tail){
            next=next.next;
        }
        next.next=tNode;
        tNode.pre=next;
        tNode.next=tail;
        tail.pre=tNode;
    }

    /**
     * 根据编号有序添加
     */
    public void addByOrder(TNode tNode){
        TNode next = head.next;
        while (true){
            if (next==tail) throw new RuntimeException();
            if (next.no==tNode.no) throw new RuntimeException();
            if (next.no>tNode.no){
                tNode.next=next.next;
                next.next=tNode;
                break;
            }
            next=next.next;
        }
    }


    /**
     * 删除
     */
    public void remove(TNode tNode){
        TNode next = head.next;
        while (true){
            if(next==tail) throw new RuntimeException();
            if (next.equals(tNode)){
                next.pre.next=next.next;
                if(next.next!=null){
                    next.next.pre=next.pre;
                }
                break;
            }
            next=next.next;
        }
    }

}
class TNode{
    int no;
    String name;
    TNode pre;
    TNode next;

    public TNode(int no, String name) {
        this.no = no;
        this.name = name;
    }
}