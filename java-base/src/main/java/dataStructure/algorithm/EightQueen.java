package dataStructure.algorithm;

/**
 * 递归回溯
 * 八皇后摆法
 * 要求：在8*8的棋盘上放置8个棋子，每一行、列、斜 均不能出现两个以上棋子
 *
 * 编程实现：有多少种摆法
 * 二维数组x和y的值都是唯一的， 此处8*8的棋盘可以使用一个一维数组来实现
 * 数组下标index+1第n个棋子的和该x坐标，index+1对应的值表示棋子的y坐标
 * 例如 arr = {1，2，3，4，5，6，7，8}表示的棋子位置：
 * 0 0 0 0 0 0 0 8
 * 0 0 0 0 0 0 7 0
 * 0 0 0 0 0 6 0 0
 * 0 0 0 0 5 0 0 0
 * 0 0 0 4 0 0 0 0
 * 0 0 3 0 0 0 0 0
 * 0 2 0 0 0 0 0 0
 * 1 0 0 0 0 0 0 0
 *
 * 答案共92种
 *
 * @author booty
 * @date 2021/6/24 13:45
 */
public class EightQueen {
    private int[] arr = new int[8];
//    private int[] arr = {1,3,5,7,4,2,8,6};
    int max = 8;
    int count= 0 ;

    /**
     * 打印棋盘上的棋子
     */
    public void show() {
        System.out.println(" ---------"+count+"---------");
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < arr.length; j++) {
                if (j == arr[i]){
                    System.out.print(" "+"田");
                }else {
                    System.out.print(" 口");
                }
            }
            System.out.println();
        }
    }

    /**
     * 判断是否冲突
     *
     * @param n 第n个棋子
     * @return b
     */
    public boolean check(int n) {
        for (int i = 0; i < n; i++) {
            /*
            第n个棋子，对应的x轴就是array[n-1]，对应的y轴为arr[n-1]的值
            循环所有之前放置的所有棋子，获取其y坐标，若y坐标有相等的，说明放在了同一行
             */
            if (arr[i] == arr[n]) return false;
            /*
            判断是否在同一斜线上
            将之前放置的棋子的坐标设想为原点
            第n个棋子的x轴 - 第i个棋子的x轴 得到相差的x的绝对值，也就是距离原点的横坐标位置
            第n个棋子的y轴- 第i个棋子的y轴 得到相差的y的绝对值，也就是距离原点的纵坐标位置
            若两个绝对值相等，说明x/y=1 或y/x=1，那么该线的斜率是45度，这两点处于棋盘的同一斜线上
             */
            if (Math.abs(n - i) == Math.abs(arr[n] - arr[i])) return false;
            //此处不需要考虑x轴相同，因棋子对应的x轴就是数组的index+1，而每次放入棋子后index都会加1，所以不会冲突
        }
        return true;
    }

    /**
     * 放置棋子
     * @param n 第n-1个棋子（从0开始）
     * @return 放置的方法
     */
    private int sum(int n) {
        if (n==8){
            //实际棋子为01234567，当n等于8时说明所有棋子都放置完毕，使放置方式+1，并直接结束
            count++;
            show();
        }else {
            //循环，使当前棋子的Y值不断变化，每次变化判断是否与其他棋子冲突
            for (int i =0; i<arr.length ;i++){
                arr[n]=i;
                //若不冲突，放入下一颗棋子(递归本方法)
                if (check(n)){
                    sum(n+1);
                }
                //冲突则不做操作，进入下一循环判断Y值与其他是否冲突
            }
        }
        return count;

    }

    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.sum(0);

    }



}
