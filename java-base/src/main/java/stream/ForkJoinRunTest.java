package stream;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author booty
 * @date 2021/5/26 11:17
 */
public class ForkJoinRunTest {

    /**
     *  数值     fork/join耗时     for循环耗时
     * 一百亿      9639             10242
     * 一千亿      61115            84316
     */
    private final long end= 10000000000L;



    /**
     * 使用fork/join框架
     * 计算累加
     */
    @Test
    public void test() {

        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinSum(0,end);
        Long invoke = pool.invoke(task);
        System.out.println(invoke);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    /**
     * 普通for循环
     * 计算累加
     *
     */
    @Test
    public void test2() {
        Instant start = Instant.now();
    
        int sum = 0;
        for (long i = 0; i <= end; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

}
