package dataStructure.Tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 平衡二叉树
 * <p>
 * 添加元素时,若左右子树的高度相差大于1,则对数进行旋转
 * 始终保持树的左右节点高度差小于1;
 * <p>
 * 增删效率较低,查询效率很高
 *
 * @author booty
 * @date 2021/7/7 09:18
 */
public class BalancedBinaryTree<T extends Comparable<T>> {

    public static void main(String[] args) {
        BalancedBinaryTree<Integer> tree = new BalancedBinaryTree<>();

        tree.add(1).add(2).add(3).add(4).add(5).add(6).add(7).add(8).add(9).add(10).add(11).add(12).add(13).add(14);
        tree.floorPrint();
        tree.delete(10);
        tree.delete(8);
        tree.floorPrint();

    }


    private Node<T> root;

    public BalancedBinaryTree() {
        root = new Node<>(null);
    }


    /**
     * 添加
     *
     * @param data 数据
     * @return this
     */
    public BalancedBinaryTree<T> add(T data) {
        if (root.data == null) {
            root.data = data;
        } else {
            assert data != null;
            int i;
            Node<T> next = root;
            while (true) {
                if (data.equals(next.data)) throw new RuntimeException("数据已存在");
                i = data.compareTo(next.data);
                if (i < 0) {
                    if (next.left == null) {
                        next.left = new Node<>(data);
                        break;
                    } else {
                        next = next.left;
                    }
                } else {
                    if (next.right == null) {
                        next.right = new Node<>(data);
                        break;
                    } else {
                        next = next.right;
                    }
                }
            }
        }
        //检查树的高度,并进行旋转
        checkTreeHeight(root);
        return this;
    }


    /**
     * 删除单个元素对应的节点
     *
     * @param data 删除的元素
     * @return this
     */
    public BalancedBinaryTree<T> delete(T data) {
        Node<T> delete = search(data);
        Node<T> parent = searchParent(delete);

        if (delete.left != null && delete.right != null) {
            //左右均不为空,获取右子树的最小节点
            Node<T> min = minNode(delete.right);
            //获取最小节点的父节点
            Node<T> minParent = searchParent(min);
            //将节点数据置换为右子树的最小节点数据
            delete.data = min.data;
            //最小节点的父节点指向最小节点的右子节点(因节点为最小,所以无左子节点,直接让父节点指向右子节点即可)
            replaceNode(min, minParent, min.right);
        } else if (delete.left != null) {
            //左子节点不为空
            if (parent == null) {
                //无父节点,说明是根节点,将根节点的左子节点设为根节点
                root = root.left;
            } else {
                //非根节点将父节点和左子节点对接
                replaceNode(delete, parent, delete.left);
            }
        } else if (delete.right != null) {
            //右子节点不为空
            if (parent == null) {
                //无父节点,说明是根节点,将根节点的右子节点设为根节点
                root = root.right;
            } else {
                //非根节点将父节点和右子节点对接
                replaceNode(delete, parent, delete.right);
            }
        } else {
            //无子节点
            if (parent == null) {
                //无父节点,说明是根节点,将根节点数据置空
                root.data = null;
            } else {
                //非根节点直接删除
                replaceNode(delete, parent, null);
            }
        }
        //检查高度
        checkTreeHeight(root);
        return this;
    }


    /**
     * 检查树的高度,并进行旋转
     */
    private void checkTreeHeight(Node<T> root) {
        if (root != null) {
            checkTreeHeight(root.left);
            checkTreeHeight(root.right);
            int left = getHeight(root.left);
            int right = getHeight(root.right);

            //左子树高度大于右子树高度1层以上
            if (left - right > 1) {
                //检查子树的右树高度是否大于左树高度,大于的话,先左旋子节点
                if (root.left != null) {
                    int childLeft = getHeight(root.left.left);
                    int childRight = getHeight(root.left.right);
                    if (childRight > childLeft) {
                        leftRotate(root.left);
                    }
                }
                //右旋根节点
                rightRotate(root);
            }
            //右子树高度大于左子树高度一层以上
            else if (right - left > 1) {
                //检查子树的左树高度是否大于右树高度,大于的话,先右旋子节点
                if (root.right != null) {
                    int childLeft = getHeight(root.right.left);
                    int childRight = getHeight(root.right.right);
                    if (childLeft > childRight) {
                        rightRotate(root.right);
                    }
                }
                //左旋根节点
                leftRotate(root);
            }
        }


    }

    /**
     * 左旋转
     *
     * @param old 要旋转的节点
     */
    private void leftRotate(Node<T> old) {
        //新建节点,保存旋转节点的数据
        Node<T> newLeft = new Node<>(old.data);
        //新节点的左子节点,指向旋转节点的左子节点
        newLeft.left = old.left;
        //新节点的右子节点,指向旋转节点右子节点的左子节点
        newLeft.right = old.right.left;


        //将旋转节点的数据替换为右子节点的数据(原数据已保存到新节点上)
        old.data = old.right.data;
        //将旋转节点的右子节点指向 右子节点下一个右子节点(原右子节点的左子节点已经接驳到新结点,不用处理)
        old.right = old.right.right;
        //将旋转节点的左子节点指向新节点
        old.left = newLeft;

    }

    /**
     * 右旋转
     *
     * @param old 要旋转的节点
     */
    private void rightRotate(Node<T> old) {
        //新建节点,保存旋转节点的数据
        Node<T> newRight = new Node<>(old.data);
        //新结点的右子节点,指向旋转节点的右子节点
        newRight.right = old.right;
        //新结点的左子节点,指向旋转节点的左子节点的右子节点
        newRight.left = old.left.right;

        //将旋转接的数据替换为其左节点的数据(原数据已保存到新节点上)
        old.data = old.left.data;
        //将旋转节点的右子节点指向新节点
        old.right = newRight;
        //将旋转接节点的左子节点指向下一个左子节点
        old.left = old.left.left;

    }


    /**
     * 获取指定节点子树的高度
     *
     * @param start 开始节点
     * @return 高度
     */
    private int getHeight(Node<T> start) {
        return start == null ? 0 : Math.max(getHeight(start.left), getHeight(start.right)) + 1;
    }


    /**
     * 接驳替换父子节点(解除父节点和子节点的关联,并将替换节点与父节点关联上)
     *
     * @param child   被替换的节点
     * @param parent  被替换的节点的父节点
     * @param replace 新替换的节点
     */
    private void replaceNode(Node<T> child, Node<T> parent, Node<T> replace) {
        assert parent != null;
        //判断左右
        if (child == parent.left) {
            parent.left = replace;
        } else if (child == parent.right) {
            parent.right = replace;
        } else {
            throw new RuntimeException("非父子节点");
        }
    }


    /**
     * 寻找最大值
     *
     * @param start 开始节点
     * @return 最大值对应节点
     */
    public Node<T> maxNode(Node<T> start) {
        if (start.right != null) return maxNode(start.right);
        return start;
    }

    /**
     * 寻找最小值
     *
     * @param start 开始节点
     * @return 最小值对应节点
     */
    public Node<T> minNode(Node<T> start) {
        if (start.left != null) return minNode(start.left);
        return start;
    }

    /**
     * 搜索数据对应的节点
     *
     * @param data 数据
     * @return 数据对应的节点
     */
    public Node<T> search(T data) {
        Node<T> node = this.root;
        assert data != null;
        while (node != null) {
            if (data.equals(node.data)) return node;
            int i = data.compareTo(node.data);
            if (i < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }


    /**
     * 搜索父节点
     *
     * @param child 子节点
     * @return 父节点
     */
    public Node<T> searchParent(Node<T> child) {
        Node<T> node = this.root;
        assert child != null;
        while (node != null) {
            if (node.left == null && node.right == null) return null;
            int i = child.data.compareTo(node.data);
            if (i < 0) {
                if (child.equals(node.left)) return node;
                node = node.left;
            } else {
                if (child.equals(node.right)) return node;
                node = node.right;
            }
        }
        return null;
    }


    /**
     * 按照层次遍历
     */
    public void floorPrint() {
        System.out.println("第" + 1 + "层:");
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        //当前正在遍历的元素
        Node<T> current;
        //下一层的最后一个节点
        Node<T> nextFloorEnd = root;
        //当前层的最后一个节点
        Node<T> currentFloorEnd = root;
        //楼层
        int floor = 1;
        while (!queue.isEmpty()) {
            //取出队列最前的节点
            current = queue.poll();

            //打印当前节点
            System.out.print(current + "   ");

            //若该元素的左子节点不为空,将其加入队列
            if (current.left != null) {
                queue.offer(current.left);
                //标记最后加入队列中的子元素
                nextFloorEnd = current.left;
            }

            //若该元素的右子节点不为空,将其加入队列
            if (current.right != null) {
                queue.offer(current.right);
                //标记最后加入队列中的子元素
                nextFloorEnd = current.right;
            }

            // 若为当前层的结束点,换行,并将结束点改为下一层
            if (currentFloorEnd == current) {
                System.out.println();
                floor++;
                //将结束点设置为下一层的结束点
                currentFloorEnd = nextFloorEnd;
                System.out.println("第" + floor + "层:");
                 /*
                若当前点为该层的结束点,也就是说当前元素是该层的最后一个元素,队列中后面的元素都是下一层的
                如  第一层
                        遍历顺序:root
                        结束点为 root,
                        队列中 新添加的节点依次为 root.left 和 root.right
                        最后添加的节点为root.right; nextFloorEnd指向root.right
                    第二层
                        遍历顺序:root.left,root.right
                        结束点为 root.right,
                        队列中 新添加的节点依次为 root.left.left 和 root,left.right 和 root.right.left 和 root.right.right
                        最后添加的节点为root.right.right; nextFloorEnd指向root.right.right
                    第三层
                        ......
             */
            }
        }
        System.out.println("遍历结束,第" + floor + "层无数据");

    }


    /**
     * 前序遍历
     * (循环实现)
     */
    public void preOrderPrint() {
        if (root.data == null) {
            System.out.println("无数据");
        } else {
            Stack<Node<T>> stack = new Stack<>();
            stack.push(root);
            //外层循环(遍历压入栈中的右节点)
            while (!stack.isEmpty()) {
                //取出元素
                Node<T> pop = stack.pop();
                //内层循环,输出自己和左节点
                while (pop != null) {
                    //先输出自己
                    System.out.print(pop + "  ");
                    //若右子节点不为空,将其加入栈中
                    if (pop.right != null) {
                        stack.push(pop.right);
                    }
                    //使下次内循环指向自己的左节点
                    pop = pop.left;
                }
            }
            System.out.println();
        }
    }

    /**
     * 中序遍历
     * (循环实现)
     */
    public void inOrderPrint() {
        if (root.data == null) {
            System.out.println("无数据");
        } else {
            Stack<Node<T>> stack = new Stack<>();
            Node<T> node = this.root;
            do {
                //内层循环,当前节点和其将左节点依次压入栈中
                while (node != null) {
                    //当前节点不为空,则将指定节点压入栈中
                    stack.push(node);
                    //使当前节点等于左节点
                    node = node.left;
                }
                //左节点为空,从栈中取出上一个节点(自身)遍历
                Node<T> pop = stack.pop();
                //遍历节点(经过循环后,被遍历节点左节点必定为空)
                System.out.print(pop + "  ");
                //外层循环,使外层下个节点等于被遍历节点的右节点(若右节点为空,重新进入外层循环后,会从栈中取出被遍历节点的父节点)
                node = pop.right;
                //外层循环,当栈中有元素,或当前节点不为空则进入循环
            } while (!stack.isEmpty() || node != null);
            System.out.println();
        }
    }

    /**
     * 后序遍历
     * (循环实现)
     */
    public void postOrderPrint() {
        if (root.data == null) {
            System.out.println("无数据");
        } else {
            Stack<Node<T>> stack = new Stack<>();
            Node<T> node = this.root;
            Node<T> lastNode = null;


            //外层循环,打印右节点和当前节点
            do {
                //内层循环,将当前节点和左节点全部压入栈中
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                //弹出上次压栈的节点(最左节点)
                Node<T> printNode = stack.pop();
                if (printNode.right == null || printNode.right == lastNode) {
                    //待打印节点右节点为空,或已经打印,则打印
                    System.out.print(printNode + "  ");
                    //使上次打印节点为打印节点
                    lastNode = printNode;
                } else {
                    //待打印节点右节点不为空且未打印,将待打印节点压入栈中
                    stack.push(printNode);
                    //使当前节点等于待打印右节点,进入下次循环
                    node = printNode.right;
                }
            } while (!stack.isEmpty() || node != null);
            System.out.println();
        }

    }


    /**
     * 节点
     *
     * @param <T> 泛型(可比较排序)
     */
    private static class Node<T extends Comparable<T>> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        public Node(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }


}
