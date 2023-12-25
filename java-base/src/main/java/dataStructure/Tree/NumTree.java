package dataStructure.Tree;

import lombok.Data;

/**
 * 一般的二叉树简单基础代码
 * 利用节点的编号来进行路径选择和查找
 * 添加时的根节点将直接决定树木的结构,
 * 若以最小值添加根节点则为右斜树,若以最大值添加根节点则为左斜树
 * 若以中位值添加根节点,则为一般二叉树(数量完全符合树结构平均时为完全二叉树)
 *
 * @author booty
 * @date 2021/7/1 14:14
 */
public class NumTree {
    public static void main(String[] args) {
        NumNode numNode5 = new NumNode(5, "5号");
        NumNode numNode4 = new NumNode(4, "4号");
        NumNode numNode2 = new NumNode(2, "2号");
        NumNode numNode1 = new NumNode(1, "1号");
        NumNode numNode3 = new NumNode(3, "3号");
        NumNode numNode7 = new NumNode(7, "7号");
        NumNode numNode8 = new NumNode(8, "8号");
        NumNode numNode9 = new NumNode(9, "9号");
        NumNode numNode6 = new NumNode(6, "6号");

        NumTree tree = new NumTree();
        tree.add(numNode4);
        tree.add(numNode2);
        tree.add(numNode6);
        tree.add(numNode1);
        tree.add(numNode3);
        tree.add(numNode5);
        tree.add(numNode7);
//        tree.add(numNode9);
//        tree.add(numNode8);

        tree.preOrder();
        System.out.println("==================");
        tree.infixOrder();
        System.out.println("==================");
        tree.postOrder();
        System.out.println("==================");

        tree.search(numNode2);
        tree.search(numNode8);
        System.out.println(tree.delete(numNode9));
//        System.out.println(tree.delete(numNode5));
        System.out.println(tree.delete(numNode6));

        tree.infixOrder();

    }


    private NumNode root;


    public NumTree() {
        this.root = new NumNode();
    }

    public void add(NumNode node) {
        root.add(node);
    }

    public void search(NumNode node) {
        NumNode search = root.search(node);
        System.out.println(search == null ? "未找到:" + node : "已找到:" + search);
    }

    public boolean delete(NumNode node){
        if(node.getNo()==root.getNo()){
            root.setNo(null);
            root.setLeft(null);
            root.setRight(null);
            return true;
        }
        return root.delete(node);
    }

    public void preOrder() {
        root.preOrder();
    }

    public void infixOrder() {
        root.infixOrder();
    }

    public void postOrder() {
        root.postOrder();
    }


    @Data
    static class NumNode {
        private Integer no;
        private String name;
        private NumNode left;
        private NumNode right;

        /**
         * 添加
         *
         * @param node
         */
        void add(NumNode node) {
            if (this.no == null) {
                this.no = node.no;
                this.name = node.name;
            } else {
                int i = this.no - node.no;
                if (i > 0) {
                    if (this.left == null) {
                        this.left = node;
                    } else {
                        this.left.add(node);
                    }
                } else if (i < 0) {
                    if (this.right == null) {
                        this.right = node;
                    } else {
                        this.right.add(node);
                    }
                } else {
                    throw new RuntimeException("编号重复");
                }
            }
        }

        /**
         * 遍历查找
         * 使用大小对比查找
         * 也可以使用前中后序遍历查找(但效率低)
         */
        public NumNode search(NumNode numNode) {
            if (numNode.no == this.no) return this;
            if (numNode.no < this.no) return left != null ? left.search(numNode) : null;
            if (numNode.no > this.no) return right != null ? right.search(numNode) : null;
            return null;
        }


        /**
         * 前序遍历（递归）
         */
        void preOrder() {
            System.out.println(this);
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
        void infixOrder() {
            if (left != null) {
                left.infixOrder();
            }
            System.out.println(this);
            if (right != null) {
                right.infixOrder();
            }
        }

        /**
         * 后序遍历（递归）
         */
        void postOrder() {
            if (left != null) {
                left.postOrder();
            }
            if (right != null) {
                right.postOrder();
            }
            System.out.println(this);
        }


        /**
         * 删除某个节点及其子节点
         */
        boolean delete(NumNode node) {
            //无左右节点表示无该数据
            if (this.left==null && this.right==null) return false;
            //删除左节点(左节点的子节点会断开)
            if(this.left!=null){
                if (left.no == node.no) {
                    this.left = null;
                    return true;
                }
            }
            //删除右节点(右节点的子节点会断开)
            if(this.right!=null){
                if (right.no == node.no) {
                    this.right = null;
                    return true;
                }
            }

            return node.no < this.no?left.delete(node):right.delete(node);
        }


        @Override
        public String toString() {
            return "NumNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }

        public NumNode(Integer no, String name) {
            this.no = no;
            this.name = name;
        }

        public NumNode() {
        }
    }


}

