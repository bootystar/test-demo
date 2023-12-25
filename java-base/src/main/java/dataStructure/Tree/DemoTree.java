package dataStructure.Tree;

import lombok.Data;

/**
 *
 * 二叉树的定义:
 * 二叉树（Binary Tree）是n（n≥0）个节点的有限集合，n = 0时，
 * 该集合为空集合称为空二叉树，或者由一个根节点和两棵互不相交的，
 * 分别称为根节点的左子树和右子树的二叉树组成
 *
 *
 * 二叉树的特点:
 * 1.每个节点最多有两颗子树（也就是说每个节点最多有两个子节点），所以在二叉树中不存在度大于2的节点。
 * 2.左子树和右子树是有顺序的，次序不能颠倒。
 * 3.即使树中某节点只有一棵子树，也要区分是左子树还是右子树。
 *
 *
 * 特殊二叉树:
 *    斜树:
 *          所有的节点都只有左子树的二叉树叫左斜树；所有节点都只有右子树的二叉树叫右斜树。
 *          这两者统称为斜树。斜树的特点很明显就是每一层只有一个节点，
 *          节点的个数与二叉树的深度相同。其实线性表可以理解为树的一种极其特殊的形式。
 *    满二叉树:
 *          在一棵二叉树中，如果所有分支节点都存在左子树和右子树，
 *          并且所有的叶子节点都在同一层上，这样的二叉树称为”满二叉树“
 *              满二叉树的特点：
 *              （1）叶子只能出现在最下一层。
 *              （2）非叶子节点的度一定为2。
 *              （3）在同样深度的二叉树中，满二叉树的节点个数最多，叶子数最多。
 *    完全二叉树:
 *          对于一棵具有n个节点的二叉树按照层序编号，
 *          如果编号为i（1≤ i ≤ n）的节点与同样深度的满二叉树中编号为i的节点在二叉树中位置完全相同，
 *          则这棵二叉树称为”完全二叉树“(满二叉树一定是完全二叉树,而完全二叉树不一定是满二叉树)
 *              完全二叉树的特点：
 *              （1） 叶子节点只能出现在最下两层。
 *              （2）最下层的叶子一定集中左部连续位置。
 *              （3）倒数第二层，若有叶子节点，一定都在右部连续位置。
 *              （4）如果节点度为1，则该节点只有左孩子，即不存在右子树的情况。
 *              （5）同样节点的二叉树，完全二叉树的深度最小。
 *
 *
 * 二叉树存储结构:
 *      1.二叉树顺序存储结构
 *          二叉树的顺序存储结构就是用一维数组存储二叉树中的节点，
 *          并且节点的存储位置，也就是数组下标要能够体现出节点之间的逻辑关系，
 *          比如父子关系、左右节点关系等
 *
 *          数组的下标体现出节点之间的逻辑关系，比如节点A的下标i是0，则
 *          2 * i + 1 是节点A的左节点，即节点B是A的左节点；2 *（ i + 1） 是节点A的右节点，
 *          即节点C是A的右节点。对于一棵完全二叉树，可以很好的利用上面的存储结构，数组的每个位置都有值，
 *          但是对于普通二叉树，由于节点不是连续的形式，对于一些极端的情况可能只存在右子树，不存在左子树，
 *          则数组中会存在大量的存储空间被浪费，此时可以考虑另一种存储结构——二叉链表
 *
 *      2.二叉链表:
 *          由于二叉树的每个节点最多有两个孩子，
 *          所以二叉链表的每个节点有一个数据域和两个指针域，指针域分别指向左子节点和右子节点
 *
 *
 * 二叉树的遍历:
 *      1.前序遍历
 *          当前节点->左子节点->右子节点
 *
 *      2.中序遍历
 *          左子节点->当前节点->右子节点
 *
 *      3.后序遍历
 *          左子节点->右子节点->当前节点
 *
 *      注:若当前节点的子节点存在子节点,在遍历左子或右子节点时,会遍历完所有子节点,
 *         例如中序遍历,会在左子节点的所有子节点遍历完后再遍历自己
 *
 * https://blog.csdn.net/uncleming5371/article/details/54096034
 * @author booty
 * @date 2021/7/1 13:37
 */
public class DemoTree {

    public static void main(String[] args) {
        DemoTree tree = new DemoTree();

        tree.add("zhangsan");
        tree.add("lisi");
        tree.add("wangwu");
        tree.add("zhaoliu");
        tree.add("tianqi");

        tree.preOrder();
        System.out.println("=====================");
        tree.infixOrder();
        System.out.println("=====================");
        tree.postOrder();
    }


    private DemoNode root;

    public DemoTree() {
        root = new DemoNode();
    }

    public void add(Object newObj) {
        root.add(newObj);
    }

    public void preOrder(){
        root.preOrder();
    }
    public void infixOrder(){
        root.infixOrder();
    }
    public void postOrder(){
        root.postOrder();
    }


    @Data
    private class DemoNode {
        private Object obj;
        private DemoNode left;
        private DemoNode right;

        /**
         * 添加数据
         * 根据hashcode决定位置,每次运行位置可能不同
         */
        public void add(Object newObj) {
            if (this.obj == null) {
                this.obj = newObj;
            } else {
                int i = this.obj.hashCode() - newObj.hashCode();
                if (i < 0) {
                    if (this.left == null) {
                        this.left = new DemoNode();
                    }
                    this.left.add(newObj);

                } else if (i > 0) {
                    if (this.right == null) {
                        this.right = new DemoNode();
                    }
                    this.right.add(newObj);
                } else {
                    throw new RuntimeException();
                }
            }
        }


        /**
         * 前序遍历（递归）
         */
        private void preOrder() {
            System.out.println(this.obj);
            if (left != null) {
                left.preOrder();
            }
            if (right != null) {
                right.preOrder();
            }
        }

        /**
         * 中序遍历（递归）
         */
        private void infixOrder() {
            if (left != null) {
                left.infixOrder();
            }
            System.out.println(this.obj);
            if (right != null) {
                right.infixOrder();
            }
        }

        /**
         * 后序遍历（递归）
         */
        private void postOrder() {
            if (left != null) {
                left.postOrder();
            }
            if (right != null) {
                right.postOrder();
            }
            System.out.println(this.obj);
        }

    }
}



