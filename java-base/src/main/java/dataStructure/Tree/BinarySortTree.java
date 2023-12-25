package dataStructure.Tree;

/**
 * 二叉排序树
 * 该树在进行中序遍历时,始终会以从小到大的顺序进行顺序遍历
 * 遍历、添加和删除
 *
 * @author booty
 * @date 2021/7/6 13:58
 */
public class BinarySortTree<T extends Comparable<T>> {

    public static void main(String[] args) {
        BinarySortTree<Integer> tree = new BinarySortTree<>();
//        tree.add(4).add(2).add(6).add(1).add(3).add(5).add(7);
        tree.add(6).add(2).add(4).add(1).add(3).add(5).add(7).add(9).add(8).add(0).add(10);
        tree.inOrderPrint();
        tree.delete(2).delete(10).delete(4).delete(0).delete(6).delete(8).delete(3).delete(1).delete(5);
        tree.inOrderPrint();
        System.out.println(tree.root);
        tree.delete(9);
        tree.inOrderPrint();


    }

    private final Node<T> root;

    public BinarySortTree() {
        this.root = new Node<>(null);
    }

    /**
     * 添加元素
     *
     * @param t 添加元素
     * @return 自身, 链式调用
     */
    public BinarySortTree<T> add(T t) {
        if (root.data == null) {
            root.data = t;
            return this;
        }
        if (t.equals(root.data)) throw new RuntimeException("该值已存在");
        int i = t.compareTo(root.data);
        if (i <= 0) {
            if (root.left == null) {
                root.left = new Node<>(t);
            } else {
                root.left.add(t);
            }
        } else {
            if (root.right == null) {
                root.right = new Node<>(t);
            } else {
                root.right.add(t);
            }
        }
        return this;
    }

    /**
     * 中序遍历
     */
    public void inOrderPrint() {
        if (root.data == null) {
            System.out.println("无数据");
        } else {
            root.inOrderPrint();
            System.out.println();
        }
    }


    /**
     * 根据目标数据删除节点
     *
     * @param t 数据
     * @return 自身,链式调用
     */
    public BinarySortTree<T> delete(T t) {
        //找到数据对应的节点
        Node<T> target = search(t);
        assert target != null;
        //为根节点
        if (target == root) {
            if (target.left != null) {
                //找出左侧最大的节点
                Node<T> leftMax = getLeftMax(target.left);
                //交换数据
                exchangeData(leftMax, target);
            } else if (target.right != null) {
                //找出右侧最小的节点
                Node<T> rightMin = getRightMin(target.right);
                //交换数据
                exchangeData(rightMin, target);
            } else {
                target.data = null;
            }
            return this;
        }
        //搜索父节点
        Node<T> parent = searchParent(target);

        //左右子节点均不为空,将当前节点与左子节点中最大值或右子节点种最小值交换
        if (target.left != null && target.right != null) {
            //获取当前数据节点右侧最小节点(此处左右都可)
            Node<T> rightMin = getRightMin(target.right);
            //交换目标节点的数据
            exchangeData(rightMin, target);
        }
        //当前节点左子节点不为空,
        else if (target.left != null) {
            if (target == parent.left) {
                parent.left = target.left;
            } else {
                parent.right = target.left;
            }
        }
        //当前节点右子节点不为空
        else if (target.right != null) {
            if (target == parent.left) {
                parent.left = target.right;
            } else {
                parent.right = target.right;
            }
        }
        //当前节点无子节点
        else {
            if (target == parent.left) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        return this;
    }


    /**
     * 寻找数据对应节点
     *
     * @param target 数据
     * @return 对应节点或null
     */
    public Node<T> search(T target) {
        return root.search(target);
    }


    /**
     * 寻找目标父节点
     *
     * @param target 目标节点
     * @return 父节点或null
     */
    public Node<T> searchParent(Node<T> target) {
        return root.searchParent(target);
    }


    /**
     * 获取目标节点左子节点种最大的节点
     *
     * @param start 目标节点的第一个左子节点
     * @return 右子树种最小的节点
     */
    private Node<T> getLeftMax(Node<T> start) {
        return start.right == null ? start : getLeftMax(start.right);
    }

    /**
     * 获取目标节点右子节点种最小的节点
     *
     * @param start 目标节点的第一个右子节点
     * @return 右子树种最小的节点
     */
    private Node<T> getRightMin(Node<T> start) {
        return start.left == null ? start : getRightMin(start.left);
    }


    /**
     * 将子节点与目标节点的数据进行替换
     *
     * @param child  叶子节点
     * @param target 目标节点
     */
    private void exchangeData(Node<T> child, Node<T> target) {
        Node<T> parent = searchParent(child);
        if (parent != null) {
            if (child == parent.left) {
                if (child.left==null && child.right==null){
                    parent.left = null;
                }else {
                    if (child.left!=null){
                        parent.left=child.left;
                    }else {
                        parent.left=child.right;
                    }
                }

            } else {
                if (child.left==null && child.right==null){
                    parent.right = null;
                }else {
                    if (child.left!=null){
                        parent.right=child.left;
                    }else {
                        parent.right=child.right;
                    }
                }
            }
        }
        target.data = child.data;
    }


    /**
     * 节点类
     *
     * @param <T>
     */
    private static class Node<T extends Comparable<T>> {
        private T data;
        private Node<T> left;
        private Node<T> right;

        /**
         * 查找指定节点
         *
         * @param target 目标数据
         * @return 节点或null
         */
        private Node<T> search(T target) {
            if (data.equals(target)) {
                return this;
            } else {
                int i = target.compareTo(data);
                if (i <= 0) {
                    if (left != null) {
                        return left.search(target);
                    } else {
                        return null;
                    }
                } else {
                    if (right != null) {
                        return right.search(target);
                    } else {
                        return null;
                    }
                }
            }
        }

        /**
         * 寻找目标节点的父节点
         *
         * @param target 目标节点
         * @return 父节点或null
         */
        private Node<T> searchParent(Node<T> target) {
            if (target == left || target == right) {
                return this;
            } else {
                int i = target.data.compareTo(data);
                if (i <= 0) {
                    if (left!=null){
                        return left.searchParent(target);
                    }
                } else {
                    if (right!=null){
                        return right.searchParent(target);
                    }
                }
            }
            return null;
        }

        /**
         * 中序遍历
         */
        private void inOrderPrint() {
            if (left != null) left.inOrderPrint();
            System.out.print(data+"  ");
            if (right != null) right.inOrderPrint();
        }

        /**
         * 添加节点
         *
         * @param t 需要添加的数据
         */
        private void add(T t) {
            if (t.equals(data)) throw new RuntimeException("该值已存在");
            int i = t.compareTo(data);
            if (i <= 0) {
                if (left == null) {
                    left = new Node<>(t);
                } else {
                    left.add(t);
                }
            } else {
                if (right == null) {
                    right = new Node<>(t);
                } else {
                    right.add(t);
                }
            }
        }


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
