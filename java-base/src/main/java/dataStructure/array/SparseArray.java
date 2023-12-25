package dataStructure.array;

/**
 * 判断五子棋游戏的输赢，
 * 并完成存盘退出和继续上局的功能
 * 棋盘-二维数组-稀疏数组-文件
 *
 *
 *
 * @author booty
 * @date 2021/5/26 17:03
 */
public class SparseArray {
    /**
     * 二维数组 链表
     * 该数组用于储存棋盘上的子，元素在数组中的位置[行][列]代表棋盘位置，
     * 对应的值代表落的什么子假设默认0代表无子，1代表黑，2代表白
     * 因二维数据很多位置都是没有使用的，记录没有意义的数据，所以使用稀疏数组来对数据进行压缩
     *
     * 例如该数组，占用空间为11*11，121个int，
     * 例如该数组仅储存了29个有效数据，那么可以用一个10*3的稀疏数组来代替，占用空间为30个int
     */
    private  static int[][] ARR=new int[11][11];


    /**
     * 稀疏数组
     * 稀疏数组也是一个二维数组，
     * 二维数据的一级长度可根据实际自行指定，二级长度固定为3
     *
     * 稀疏数组储存方式
     *      第一个子数组（第一行）储存的3个数据分别为：二维数组总行数，二维数组总列数，二维数据中一共有多少个值
     *      其他子数组（非第一行）储存的3个数据分别为：二维数组行，二维数组列，行列对应的值
     *
     * 稀疏数组的一级长度*二级长度 小于 二维数组的一级长度*二级长度
     * 否则稀疏数组的内存占用大于二维数组，无实际使用意义
     *
     * 例如该数组，占用空间为10*3，30个int
     *
     */
    private static int[][] sparseArray=new int[10][3];


    /**
     * 二维数组转稀疏数组的方式：
     * 遍历二维数组，获取有效个数的数据sum
     * 创建稀疏数组，数组长度为[sum+1][3]
     * @param twoDimensionalArray 二维数组
     * @return 稀疏数组
     */
    public static int[][]  twoDimensionalArrayToSparseArray(int[][] twoDimensionalArray){
        //稀疏数组的长度，默认第一行储存数据量，所以至少为1
        int length = 1;
        //遍历二维数组
        for (int x = 0; x < twoDimensionalArray.length; x++) {
            for (int y = 0; y < twoDimensionalArray[x].length; y++) {
                //每读取到一个有效数据，稀疏数组长度+1
                if (twoDimensionalArray[x][y]!=0){
                    length++;
                }
            }
        }
        //创建稀疏数组
        int[][] sparseArray=new int[length][3];

        //存入二维数组的行数
        sparseArray[0][0]=twoDimensionalArray.length;
        //存入二维数组的列数（子数组长度均相同，随意取一个取长度）
        sparseArray[0][1]=twoDimensionalArray[0].length;
        //二维数组有效数据的总数
        sparseArray[0][2]=length;
        //稀疏数组放入数据量的计数器（第一行）
        int count=1;

        //遍历二维数组
        for (int x = 0; x < twoDimensionalArray.length; x++) {
            for (int y = 0; y < twoDimensionalArray[x].length; y++) {
                if (twoDimensionalArray[x][y]!=0){
                    //存入对应行列的值
                    sparseArray[count][0]=x;
                    sparseArray[count][1]=y;
                    sparseArray[count][2]=twoDimensionalArray[x][y];
                    count++;
                }

            }
        }
        return sparseArray;
    }


    /**
     * 稀疏数组转二维数组
     * 先读取稀疏数组的第一行，根据第一行的总行数总列数创建指定大小的二维数组
     * 然后遍历稀疏数组，赋值给二维数组
     * @param sparseArray 稀疏数组
     * @return 二维数组
     */
    public static int[][]  sparseArrayToTwoDimensionalArray(int[][] sparseArray){
        //创建二维数组
        int[][] twoDimensionalArray=new int[sparseArray[0][0]][sparseArray[0][1]];
        //遍历稀疏数组，将数据放入二维数组
        for (int i = 1; i < sparseArray.length; i++) {
            //稀疏数组一行中依次对应二维数组的 行，列，值
            int row=sparseArray[i][0];
            int column=sparseArray[i][1];
            int value=sparseArray[i][2];
            twoDimensionalArray[row][column]=value;
        }

        return twoDimensionalArray;
    }


}
