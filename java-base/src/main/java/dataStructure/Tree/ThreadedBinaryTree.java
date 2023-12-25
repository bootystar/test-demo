package dataStructure.Tree;

/**
 * 线索化二叉树
 * 二叉树可以使用两种存储结构：
 * 顺序存储和二叉链表。
 * 在使用二叉链表的存储结构的过程中，会存在大量的空指针域，
 * 为了充分利用这些空指针域，引申出了“线索二叉树”
 *
 * 对于一个有n个节点的二叉链表，
 * 每个节点有指向左右节点的2个指针域，整个二叉链表存在2n个指针域。
 * 而n个节点的二叉链表有n-1条分支线，那么空指针域的个数=2n-(n-1) = n+1个空指针域，
 * 从存储空间的角度来看，这n+1个空指针域浪费了内存资源
 *
 * 充分利用二叉链表中的空指针域，存放节点在某种遍历方式下的前驱和后继节点的指针
 * 这种指向前驱和后继的指针成为线索，加上线索的二叉链表成为线索链表，
 * 对应的二叉树就成为“线索二叉树(Threaded Binary Tree)
 * 此时,二叉链表就成为了一个双向链表,可以从尾部往前查找
 *
 * https://blog.csdn.net/jisuanjiguoba/article/details/81092812
 *
 * @author booty
 * @date 2021/7/2 11:16
 */
public class ThreadedBinaryTree {


    public static void main(String[] args) {

        int[] arr={1,2,3,4,5,6};
        ThreadedBinaryTree tree=new ThreadedBinaryTree(arr);
        tree.add(7);


        tree.inOrderList();
        System.out.println();
        tree.preOrderList();
    }


    //线索化时记录前一个节点
    private ThreadedNode preNode;
    //根节点
    private ThreadedNode root;


    /**
     * 无参构造初始化
     */
    public ThreadedBinaryTree() {
        root = new ThreadedNode();
    }

    /**
     * 数组构造器初始化
     * (不考虑大小顺序)
     * (依照数组元素顺序的前驱后继组成二叉树)
     */
    public ThreadedBinaryTree(int[] nums) {
        root= arrayInit(nums,0);
        inOrderThreading(root);
    }

    /**
     * 数组初始化
     */
    private ThreadedNode arrayInit(int[]nums, int index){
        ThreadedNode node = null;
        if (index < nums.length) {
            node = new ThreadedNode(nums[index]);
            node.left = arrayInit(nums, 2*index + 1);
            node.right = arrayInit(nums, 2*index + 2);
        }
        return node;
    }




    /**
     * 添加单个元素
     *
     * @param num 元素
     */
    public void add(int num) {
        if (root.num == null) {
            root.num = num;
            inOrderThreading(root);
        } else {
            ThreadedNode node = root.add(num);
            inOrderThreading(node);
            System.out.println();
        }
    }

    /**
     * 中序遍历
     */
    public void inOrderList(){
        root.inOrderList();
    }

    /**
     * 前序遍历
     */
    public void preOrderList(){
        root.preOrderList();
    }




    /**
     * 中序线索化
     *
     * @param node 当前节点
     */
    public void inOrderThreading(ThreadedNode node) {
        //当前节点不为空才处理
        if (node != null) {

            //递归线索化左树(左节点不为前驱节点时)
            if (!node.isLeftThread){
                inOrderThreading(node.left);
            }

            //当前节点的左子节点为空,将当前节点的左子节点设置为上个节点(前驱节点)
            if (node.left == null) {
                node.left = preNode;
                node.isLeftThread = true;
            }
            //上个节点的右子节点为空,将上个节点的右子节点设置为当前节点(后继节点)
            //因在当前方法中,当前节点的后继节点还未获取到,所以此处设置上个节点的后继节点为当前
            //当前节点的后继节点设置会在下一次递归时设置(若为下个空则不会递归,后继节点也就为空)
            //增加preNode!=null,第一个节点进入时,preNode为空, 此时不考虑上个节点
            if (preNode != null && preNode.right == null) {
                preNode.right = node;
                preNode.isRightThread = true;
            }
            //当前节点已经设置完毕,将当前节点设置为上个节点,执行下一个递归;
            preNode = node;

            //递归线索化右边(右节点不为前驱节点时)
            if (node.right!=null && !node.isRightThread){
                inOrderThreading(node.right);
            }

        }
    }



    static class ThreadedNode {
        //存放的数据(此处使用排序的编号替代数据)
        private Integer num;
        //左子节点
        private ThreadedNode left;
        //左子节点指向是否为前驱节点
        private boolean isLeftThread;

        //右子节点
        private ThreadedNode right;
        //右子节点指向是否为后置节点
        private boolean isRightThread;

        public ThreadedNode() {
        }

        public ThreadedNode(Integer num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "ThreadedNode{" +
                    "num=" + num +
                    '}';
        }

        /**
         * 添加
         * @param num 添加元素
         * @return 新添加节点的父节点(当前节点)
         */
        public ThreadedNode add(int num) {
            int value = num - this.num;
            if (value < 0) {
                if (left == null || left.isLeftThread) {
                    left = new ThreadedNode(num);
                } else {
                    return left.add(num);
                }
            } else if (value > 0) {
                if (right == null || right.isRightThread) {
                    right = new ThreadedNode(num);
                } else {
                    return right.add(num);
                }
            } else {
                throw new RuntimeException("编号相同");
            }
            return this;
        }

        /**
         * 中序遍历
         */
        public void inOrderList() {
            if (left != null && !this.isLeftThread) {
                left.inOrderList();
            }
            System.out.println(this);
            if (right != null && !this.isRightThread) {
                right.inOrderList();
            }
        }


        /**
         * 前序遍历
         */
        public void preOrderList() {
            System.out.println(this);
            if (left != null && !this.isLeftThread) {
                left.preOrderList();
            }
            if (right != null && !this.isRightThread) {
                right.preOrderList();
            }
        }
    }


}

