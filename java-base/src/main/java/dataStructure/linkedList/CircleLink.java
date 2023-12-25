package dataStructure.linkedList;

/**
 * 环形链表
 * 首尾相连的链表
 * @author booty
 * @date 2021/6/22 09:35
 */
public class CircleLink {
    public static void main(String[] args) {
        CNode node1 = new CNode(1,"zhangsan");
        CNode node2 = new CNode(3,"lisi");
        CNode node3 = new CNode(5,"wangwu");
        CNode node4 = new CNode(7,"zhaoliu");
        CLinkedList link=new CLinkedList();
        link.add(node1);
        link.add(node3);
        link.add(node2);
        link.add(node4);

        link.print();
    }

}
class CLinkedList{

    CNode root;

    public CLinkedList() {
        root=new CNode(0,"root");
        root.next=root;
        root.pre=root;
    }

    /**
     * 添加
     */
    public void add( CNode node){
        CNode current = root.next;
        while (true){
            if (current==root){
                //最后节点的下个节点
                current.pre.next=node;
                //插入节点的上个节点
                node.pre=current.pre;
                //插入节点的下个节点=root
                node.next=root;
                //root节点的上个节点
                root.pre=node;
                break;
            }
            current=current.next;
        }
    }

    /**
     * 遍历打印
     */
    public void print(){
        CNode current = root.next;
        while (current!=root){
            System.out.println(current);
            current=current.next;
        }
        System.out.println("=====");
    }



}
class CNode{
    CNode pre;
    CNode next;
    int no;
    String name;


    public CNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}