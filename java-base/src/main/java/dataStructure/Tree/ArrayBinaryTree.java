package dataStructure.Tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 二叉树顺序储存
 * 二叉树的顺序存储结构就是用一维数组存储二叉树中的节点，
 *     并且节点的存储位置，也就是数组下标要能够体现出节点之间的逻辑关系，
 *     比如父子关系、左右节点关系等
 *
 *     数组的下标体现出节点之间的逻辑关系，比如节点A的下标i是0，则
 *     2 * i + 1 是节点A的左节点，即节点B是A的左节点；2 *（ i + 1） 是节点A的右节点，
 *     即节点C是A的右节点。对于一棵完全二叉树，可以很好的利用上面的存储结构，数组的每个位置都有值，
 *     但是对于普通二叉树，由于节点不是连续的形式，对于一些极端的情况可能只存在右子树，不存在左子树，
 *     则数组中会存在大量的存储空间被浪费
 *
 * https://www.jianshu.com/p/6b6447ec4285
 * @author booty
 * @date 2021/7/2 09:02
 */
public class ArrayBinaryTree {

    public static void main(String[] args) {
        ArrayBinaryTree tree = new ArrayBinaryTree(new int[]{1, 2, 3, 4, 5, 6, 7});
        tree.preOrder(0);
    }




    private int[] arr ;


    public ArrayBinaryTree(int[] arr) {
               this.arr=arr;
    }



    /**
     * 前序遍历
     * @param index 开始下标
     */
    void preOrder(int index) {
        if (index < arr.length) {
            System.out.println(arr[index]);
            preOrder(index * 2 + 1);
            preOrder(index * 2 + 2);
        }
    }





}
