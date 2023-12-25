package dataStructure.algorithm;

import java.util.Arrays;

/**
 * KMP
 * 字符串匹配算法
 * (也属于动态规划算法)
 *
 * @author booty
 * @date 2021/7/9 11:25
 */
public class KMP {

    /**
     * 暴力匹配字符串是否包含指定子字符串
     *
     * @param str    母字符串
     * @param target 子字符串
     * @return 匹配下标
     */
    public static int violentMatch(String str, String target) {


        //循环母串的每个字符
        for (int index = 0; index < str.length(); index++) {
            //标记是否匹配
            boolean match = true;
            //母串的匹配下标
            int mother = index;
            //子串的匹配下标
            int child = 0;
            //循环对比字母串
            while (child < target.length()) {
                if (str.charAt(mother) == target.charAt(child)) {
                    //匹配成功,让母串和子串匹配下标都指向下一个元素,继续匹配
                    mother++;
                    child++;
                } else {
                    //未匹配成功,标记失败并终止循环
                    match = false;
                    break;
                }
            }
            //若循环终止后标记未被设置为false,说明当前下标开始,匹配成功
            if (match) return index;
        }
        return -1;
    }


    /**
     * 生成部分匹配值对应的数组
     * (简单逻辑版本)
     *
     * 部分匹配值:
     * 当前元素与之前的多少个元素能够匹配上(重复)
     * 例如
     * 0123450123
     * 后方的0123的部分匹配值 分别是1234
     *
     * @param str 需要匹配的子字符串
     * @return 部分匹配值
     */
    public static int[] simpleKMP(String str) {
        //创建对应子串长度的数组
        int[] next = new int[str.length()];
        //标记,从最前开始,需要匹配的元素下标
        int temp = 0;
        //从第二个元素开始计算部分匹配值(第一个字符默认为0)
        for (int i = 1; i < next.length; i++) {
            if (str.charAt(i) == str.charAt(temp)) {
                //匹配,使当前元素的部分匹配值为上一个元素的部分匹配值+1
                next[i] = next[i - 1] + 1;
                //匹配的元素下标后移一位,指向下一个
                temp++;
            } else {
                //不匹配,下一个元素从头开始匹配
                temp = 0;
            }
        }
        return next;
    }


    /**
     * 生成部分匹配值对应的数组
     * (复杂逻辑版本,效率更高,使用了动态规划的算法)
     *
     * 部分匹配值:
     * 当前元素与之前的多少个元素能够匹配上(重复)
     * 例如
     * 0123450123
     * 后方的0123的部分匹配值 分别是1234
     *
     * @param str 需要匹配的子字符串
     * @return 部分匹配值
     */
    public static int[] completeKMP(String str) {
        //创建对应子串长度的数组
        int[] next = new int[str.length()];
        //标记,从最前开始,需要匹配的元素下标
        int temp = 0;
        for (int i = 1; i < next.length; i++) {
            //若当前未匹配,并且之前匹配过(下标不为0) 进入循环
            while (temp > 0 && str.charAt(i) != str.charAt(temp)) {
                /*

                根据定义，next存储的值是前i-1位的最长相同前后缀的值，
                而我们存这个值的目的是为了指导字符串匹配的操作，
                如果我们用求出next的子串去匹配母串，
                失配时我们会惊喜的发现，
                next[i]的值恰好也就是失配时，
                子串指针应该回溯的下标位置。
                也就是说，
                next[i]不仅仅表示前i-1位的字符串的最长相同前后缀的长度，
                也表示当子串的第 i 位子串与母串失配时，子串应该回溯的位置。
                next数组为了统筹位置和长度这两个量，所以next[i]表示的是前i-1的，而不是前i位的

                比如 A B A B C A B A B D
                当第五位与第十位不同时（前四位 A B A B与除最后一位外的后四位 A B A B 相同），
                只能从第四位之前的子串中寻找能与除最后一位外的末尾匹配的最大串
                1、所以需要寻找满足前i位（A、AB、ABA）与除最后一位外的后i位（B、AB、BAB）相同的子串
                2、又因为前四位 A B A B 与除最后一位外的后四位 A B A B 相同，
                   所以前四位的后i位（BAB、AB、B）与除最后一位外后四位的后i位（BAB、AB、B）相同

                以上两条件同时满足，
                也就是要找前四位中前i位（A、AB、ABA）与后i位（BAB、AB、B）相同（AB）的情况，
                即代码中 temp = next[temp-1] 这一步的由来

                https://blog.csdn.net/qq_21989927/article/details/109494438
                 */
                temp = next[temp - 1];
            }
            //若匹配,使需要匹配元素的下标指向下一个元素(同时匹配值也+1)
            if (str.charAt(i) == str.charAt(temp)) {
                temp++;
            }
            //修改当前元素的部分匹配值 已匹配元素的下标值(每匹配一个就会+1)
            next[i] = temp;
        }
        return next;
    }


    /**
     * KMP算法
     * 匹配字符串是否包含指定子字符串
     *
     * @param str    母字符串
     * @param target 子字符串
     * @return 匹配下标
     */
    public static int KMP_Match(String str, String target) {
        //子串长度小于2时,KMP算法无意义
        if (target.length() < 2) {
            return violentMatch(str, target);
        }

        int[] kmpArray = completeKMP(target);

        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target.charAt(j)) {
                j++;
                if (j == kmpArray.length - 1) {
                    return i - j + 1;
                }
            } else {
                if (j > 0) {
                    //回溯到拥有相同子串元素的下标
                    j = kmpArray[j - 1];
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        String str = "01232343450123456789";
        String str2 = "ABABCABABD";
        int[] ints = simpleKMP(str2);
        System.out.println(Arrays.toString(ints));
        int[] ints1 = simpleKMP(str2);
        System.out.println(Arrays.toString(ints1));

        System.out.println(KMP_Match(str, str2));

    }

}
