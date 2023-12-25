package dataStructure.Tree;

import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 赫(哈)夫曼树
 *
 * 路径：
 *      在一棵树中，一个结点到另一个结点之间的通路，称为路径。
 * 路径长度：
 *      在一条路径中，每经过一个结点，路径长度都要加 1 。
 *      例如在一棵树中，规定根结点所在层数为1层，那么从根结点到第 i 层结点的路径长度为 i - 1 。
 * 结点的权：
 *      给每一个结点赋予一个新的数值，被称为这个结点的权。
 * 结点的带权路径长度：
 *      指的是从根结点到该结点之间的路径长度与该结点的权的乘积。
 * 树的带权路径长度为树中所有叶子结点的带权路径长度之和。通常记作 “WPL”
 *
 * @author booty
 * @date 2021/7/5 10:46
 */
public class HuffmanTree {

    public static void main(String[] args) {
        int [] arr= {5,29,7,8,14,23,3,11    };
        HuffNode huffmanTree = createHuffmanTree(arr);
        huffmanTree.preOrder();
    }



    public static HuffNode createHuffmanTree(int[] arr){
        List<HuffNode> list = Arrays.stream(arr).boxed().map(HuffNode::new).collect(Collectors.toList());
        while (list.size()>1){
            Collections.sort(list);
            HuffNode left = list.get(0);
            HuffNode right = list.get(1);
            HuffNode parent = new HuffNode(left.value + right.value);
            parent.left=left;
            parent.right=right;
            list.remove(left);
            list.remove(right);
            list.add(parent);
        }
        return list.get(0);
    }


    @Data
    private static class HuffNode implements Comparable<HuffNode>{
        private Integer value;
        private HuffNode left;
        private HuffNode right;

        public HuffNode(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "HuffNode{" +
                    "value=" + value +
                    '}';
        }

        public void preOrder(){
            System.out.println(this);
            if (left!=null){
                left.preOrder();
            }
            if (right!=null){
                right.preOrder();
            }
        }
        @Override
        public int compareTo(HuffNode o) {
            return value-o.value;
        }
    }
}
