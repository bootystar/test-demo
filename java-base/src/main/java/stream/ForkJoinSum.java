package stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.RecursiveTask;

/**
 * 使用fork/join框架 执行n-m的累加求和计算
 *
 * @author booty
 * @date 2021/5/26 10:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForkJoinSum extends RecursiveTask<Long> {

    /*
     * fork/join框架：
     *     将一个任务拆分成n个小任务（fork），将小任务以队列的形式装到不同线程中进行运算
     *     之后将小任务结果进行汇总(join)
     *     采用工作窃取模式，效率高
     *
     * 工作窃取模式
     *      当前cpu(线程)的任务队列完成时，会从其他未完成的任务队列末尾拿出一个任务执行
     *      避免了cpu空闲浪费资源的情况
     *
     * 使用fork/join，需要继承
     *      RecursiveAction  无返回值
     *      RecursiveTask <V>   V类型返回值
     */

    private static final long serialVersionUID = 1L;
    private long start;
    private long end;
    private static final long THRESHOLD = 1000000;


    @Override
    protected Long compute() {
        long length = end - start;
        //小于临界值时不进行拆分,临界值设置要合理，过小时拆分时间大于计算时间，反而效率低
        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
//            stream.ForkJoinSum left = new stream.ForkJoinSum().setStart(start).setEnd(middle);
            ForkJoinSum left = new ForkJoinSum(start,middle);

            left.fork();
//            stream.ForkJoinSum right = new stream.ForkJoinSum().setStart(middle+1).setEnd(end);
            ForkJoinSum right = new ForkJoinSum(middle+1,end);
            right.fork();
            return left.join() + right.join();
        }
    }




}
