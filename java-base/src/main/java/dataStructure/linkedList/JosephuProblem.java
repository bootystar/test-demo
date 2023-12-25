package dataStructure.linkedList;

/**
 * 约瑟夫（丢手帕问题）
 *
 * 设编号1，2。。。。。n 的n个人围坐一圈，
 * 约定编号为K的人开始报数，数到m的人出列，
 * 依次类推
 * 由此产出一个出队编号的序列
 *
 * 提示：
 * 用一个不带头结点的循环链表来处理，
 * 构成一个n结点链表，然后由k起从1计数，计到m时，删除对应结点，
 * 然后从被删除结点的下一个结点从1开始计数，
 * 直到删除最后一个结点
 * @author booty
 * @date 2021/5/26 17:06
 */
public class JosephuProblem {
    public static void main(String[] args) {
        new JLinkedList(5,3,4);
    }

}
class JLinkedList{

    JNode root;
    JNode current;

    public JLinkedList(int total,int startNo,int count) {
        root = new JNode(1);
        root.next= root;
        root.pre= root;
        for (int i = 2; i < total+1; i++) {
            JNode node = new JNode(i);
            this.add(node);
        }
        gameStart(startNo,count);
    }



    private void add(JNode node) {
        JNode next = root.next;
        while (true){
            if (next== root){
                next.pre.next=node;
                node.pre=next.pre;
                node.next= root;
                root.pre=node;
                break;
            }
            next=next.next;
        }
    }

    /**
     * 此处仅使用单向的next即可完成，
     * 所以可以不用双向链表，仅使用单向环形链表
     * （双向和单向链表仅增删逻辑不同，遍历逻辑相同）
     */
    private void gameStart(int startNo, int count) {
        System.out.println("从"+startNo+"号开始，每次计数："+count);

        //找到从谁开始
        current=root;
        while (current.no != startNo) {
            current = current.next;
        }

        //将root标记为current的上一个，用于判断游戏结束
        root=current.pre;
        //开始游戏
        while (true){
            if (current==root) {
                System.out.println("结束");
                break;
            }
            for (int i = 1; i < count; i++) {
                System.out.println("计数："+i+"，当前："+current);
                current=current.next;
            }
            System.out.println("计数："+count+"，当前："+current+"=>出局");
            current.pre.next=current.next;
            current.next.pre=current.pre;
            current=current.next;
            root=current.pre;
            System.out.println("========================>剩余："+left());
        }
    }

    private String left() {
        String str="";
        JNode node=root.next;
        while (true){
            str=str + node.no+",";
            if (node==root){
                if (str.length()>0){
                    str=str.substring(0,str.length()-1);
                }
                break;
            }
            node=node.next;
        }
        return str;
    }
}
class JNode{
    JNode pre;
    JNode next;
    int no;

    public JNode(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "JNode{" +
                "no=" + no +
                "号}";
    }
}