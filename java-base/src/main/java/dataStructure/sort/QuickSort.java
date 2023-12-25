package dataStructure.sort;

/**
 * @author booty
 * @date 2021/6/28 11:06
 */
public class QuickSort {
    /**
     * 快速排序
     * 随机取数组中一个数作为基准，并对数组中的元素进行交换
     * 使数组中基准左边元素均小于基准，基准右边元素均大于基准
     * 然后递归调用
     * <p>
     * 综合效率高于希尔排序，基准值的选取有一定影响
     */
    public static int[] quickSort(int[] arr, int start, int end) {
        //仅当高位大于低位时才排序，若高位小于低位说明已经排序完了
        if (start < end) {
            //低位
            int low = start;
            //高位
            int high = end;
            //取数组的第一个数作为基准
            int stand = arr[low];

            //循环处理
            while (low < high) {
                //若右边的数字大于基准，则不做任何操作
                while (low < high && arr[high] >= stand) {
                    high--;
                }
                //若小于基准，则将右边的数字放到基准左侧的低位（此时低位的数字已经基准位上了，不会覆盖，之后循环时始终有一个多的）
                arr[low] = arr[high];

                //若左边的数字小于基准，不做任何操作
                while (low < high && arr[low] <= stand) {
                    low++;
                }
                //左边的数字大于基准，将左边的数字扔到之前放到了基准位上的高位
                arr[high] = arr[low];
            }
            //最后low的位置的赋值给了其他位置，所以将基准值填入low位置（此时高位的数字已经上一步的低位上了，不会覆盖，之后循环时始终有一个多的）
            arr[low] = stand;
            quickSort(arr, start, low);
            quickSort(arr, low + 1, end);
        }
        return arr;
    }


    /**
     * 快速排序
     * (取中间数作为基准)
     */
    public static int[] quickSort2(int[] arr, int start, int end) {
        //左下标
        int low = start;
        //右下标
        int high = end;
        //取开始下标和结束下标的中位数作为基准
        int pivot = arr[(start + end) / 2];
        //若低位大于等于高位说明低位和高位之前没有需要处理的元素，直接返回
        while (low < high) {
            //遍历查找，直到找到左侧大于等于基准的值(最差情况找到基准所在位置退出循环)
            while (arr[low] < pivot) {
                low++;
            }

            //遍历查找，直到找到右侧小于等于基准的值(最差情况找到基准所在位置退出循环)
            while (arr[high] > pivot) {
                high--;
            }

            //若此时低位比高位大，说明低位的值都小于基准，高位的值都大于基准，不用执行任何操作，直接返回
            if (low >= high) break;


            //将 基准左侧高于基准的值 的位置 与 基准右侧低于基准的值 互换位置，使基准左侧永远低于基准左侧，基准右侧永远高于基准右侧
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;

            //若原来基准左侧（交换后为右侧）的值与基准相等，将左侧的指针+1，使下次扫描不再扫描该位置，否则下一个始终扫描到该位置
            if (arr[high] == pivot) low++;
            //若原来基准右侧（交换后为左侧）的值与基准相等，将左侧的指针+1，使下次扫描不再扫描该位置，否则下一个始终扫描到该位置
            if (arr[low] == pivot) high--;

        }
        /*
        因在循环内增加了当前值相等则左移高位指针，右移低位指针的操作，
        处理完成后，指针的指向有几种情况，
        第一种 低位和高位最后扫描到的都是基准所在的位置自身
              此时
              低位指针low 指向基准pivot后一位
              高位指针high指向基准pivot前一位
        第二种 低位扫描的最后位置是基准自身，高位扫描的是基准+1
              此时
              低位指针low 指向基准pivot后一位
              高位指针high指向基准pivot
        第三种 高位扫描的最后位置是基准自身，低位指针扫描的是基准-1
              此时
              低位指针low 指向基准pivot
              高位指针high指向基准pivot前一位
        第四种 高位扫描的最后位置是基准+1，低位指针扫描的是基准-1
              此时
              低位指针low 指向基准pivot
              高位指针high指向基准pivot

        第4种情况较为特殊，因其他情况完成后，
        高位指针总是指向基准左侧，低位指针总是指向基准右侧，两指针位置指向不同
        所以当出现第四种情况时，需要将高位指针-1；将低位指针+1；避免之后两个递归调用都递归到基准自身

        以上逻辑完成后
        实际高位指针指向的位置 对应值小于基准，低位指针指向的位置 对应的值大于基准，
        所以在下面的递归调用中，
        对基准左侧进行排序时，范围应该是 0-高位指针位置
        对基准右侧进行排序时，范围应该是 低位指针位置-length-1
         */
        if (low == high) {
            low++;
            high--;
        }
        if (start < high) {
            quickSort2(arr, start, high);
        }

        if (low < end) {
            quickSort2(arr, low, end);
        }
        return arr;
    }
}
