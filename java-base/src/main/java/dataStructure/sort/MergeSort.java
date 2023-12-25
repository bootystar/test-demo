package dataStructure.sort;

/**
 * @author booty
 * @date 2021/6/28 11:06
 */
public class MergeSort {

    /**
     * 归并排序
     * 将数组分为左右两个区域，
     * 一直不停拆分，
     * 直到左右两个区域各只有一个值，
     * 然后将这两个区域的值进行对比，根据大小依次放入临时数组，
     * 再使用有序的临时数组的值覆盖区域中的值，这样经过排序后的区域就是有序的
     * 排序后的区域递归到上一级，上一级再递归，最终整个数组有序
     *
     * 归并的效率与快速排序差不多
     *
     *
     */
    public static int[] mergerSort(int[] source, int start, int end ,int[] temp) {

        //判断是否需要排序（开始下标等于结束下标说明数组中只有一个元素了（不会大于））
        if (start < end) {
            //获取start至end之间的中间下标
            int middle = (start + end) / 2;
            //递归，先对数组左边的区域进行排序
            mergerSort(source, start, middle,temp);
            //递归，先对数组右边的区域进行排序
            mergerSort(source, middle+1, end,temp);

            /*
            因递归了左右，
            所以此时，以middle为分界，start-middle 和middle+1-end 两段都是有序的

            排序对比的逻辑：
            将当前两段有序区域的值取出来进行比较，并根据大小放入临时数组
            放入完成后，临时数组中的值肯定为有序，
            将临时数组的值从左至右覆盖原数组左右两端区域，此时原数组左右两端也就有序了
            （所以需要先对左右进行递归，递归到最后时，需要排序的只有两个值（start和start+1），所以比较之后一定有序）
             */


            //左侧区域的指向下标
            int left = start;
            //右侧区域的指向下标
            int right = middle + 1;
            //临时数组的指向下标
            int index = 0;
            //循环
            while (left <= middle && right <= end) {
                //判断左右区域的数字谁大，谁大将谁放入临时数组，并使该区域的指向下标指向下一位
                if (source[left] < source[right]) {

                    temp[index] = source[left];
                    left++;
                } else {
                    temp[index] = source[right];
                    right++;
                }
                //临时数组下标指向下一位
                index++;
            }
            //若遍历完左边区域有剩余数字未遍历，说明剩余数字均大于临时数组中数字的，直接放入
            while (left <= middle){
                temp[index]=source[left];
                index++;
                left++;
            }
            //若遍历完左边区域有剩余数字未遍历，说明剩余数字均大于临时数组中数字的，直接放入
            while (right<=end){
                temp[index]=source[right];
                index++;
                right++;
            }
            //使用有序的临时数字，覆盖需要排序的左右区域的值
            index=0;
            while (start<=end){
                source[start]=temp[index];
                index++;
                start++;
            }
        }
        return source;
    }


    /**
     * 归并排序
     * 此种写法会频繁创建数组，非常影响效率，舍弃
     *
     */
    public static int[] mergerSort2(int[] source, int start, int end) {

        //判断是否需要排序（开始下标等于结束下标说明数组中只有一个元素了（不会大于））
        if (start < end) {
            //获取start至end之间的中间下标
            int middle = (start + end) / 2;
            //递归，先对数组左边的区域进行排序
            mergerSort2(source, start, middle);
            //递归，先对数组右边的区域进行排序
            mergerSort2(source, middle+1, end);

            /*
            因递归了左右，
            所以此时，以middle为分界，start-middle 和middle+1-end 两段都是有序的

            排序对比的逻辑：
            将当前两段有序区域的值取出来进行比较，并根据大小放入临时数组
            放入完成后，临时数组中的值肯定为有序，
            将临时数组的值从左至右覆盖原数组左右两端区域，此时原数组左右两端也就有序了
            （所以需要先对左右进行递归，递归到最后时，需要排序的只有两个值（start和start+1），所以比较之后一定有序）
             */

            //创建临时数组（开始区域至结束区域的下标需要+1才为数组容量，比如数组中两个下标，0，1，那么数组长度为2）
            int[] temp = new int[end - start + 1];
            //左侧区域的指向下标
            int left = start;
            //右侧区域的指向下标
            int right = middle + 1;
            //临时数组的指向下标
            int index = 0;
            //循环
            while (left <= middle && right <= end) {
                //判断左右区域的数字谁大，谁大将谁放入临时数组，并使该区域的指向下标指向下一位
                if (source[left] < source[right]) {

                    temp[index] = source[left];
                    left++;
                } else {
                    temp[index] = source[right];
                    right++;
                }
                //临时数组下标指向下一位
                index++;
            }
            //若遍历完左边区域有剩余数字未遍历，说明剩余数字均大于临时数组中数字的，直接放入
            while (left <= middle){
                temp[index]=source[left];
                index++;
                left++;
            }
            //若遍历完左边区域有剩余数字未遍历，说明剩余数字均大于临时数组中数字的，直接放入
            while (right<=end){
                temp[index]=source[right];
                index++;
                right++;
            }
            //使用有序的临时数字，覆盖需要排序的左右区域的值
            for (int j : temp) {
                source[start] = j;
                start++;
            }
        }


        return source;
    }

}
