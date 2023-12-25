package thread;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池
 *
 * 底层维护了一个线程队列,队列中保存了所有正在等待的队列，减少了创建和销毁线程的开销
 * 线程池的结果体系
 *  java.util.concurrent.Executor               负责线程使用和调度的根接口
 *      ExecutorService                         子接口，线程池的主要接口
 *          ThreadPoolExecutor                  实现类
 *          ScheduledExecutorService            子接口，负责线程池的调度
 *              ScheduledThreadPoolExecutor     实现类，实现ScheduledExecutorService， 继承了ThreadPoolExecutor
 *
 * Executors工具类
 *   newSingleThreadExecutor()              创建单个线程的线程池，池中只有一个线程
 *   newFixedThreadPool()                   创建固定大小的线程池
 *   newCachedThreadPool()                  创建缓存线程池，线程的数量可变，根据需求自动更改
 *
 *   newSingleThreadScheduledExecutor()     创建固定大小的线程池，可以执行延迟或定时任务
 *
 *
 * @author booty
 * @date 2021/5/31 16:56
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        //创建任务
        Runnable task=()-> System.out.println(Thread.currentThread().getName()+"执行任务");

        //提交任务
        pool.submit(task);

        //关闭线程池（软关闭，会等待线程执行完任务后再回收）
        pool.shutdown();


        //定时任务
        ScheduledExecutorService pool2 = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> schedule = pool2.schedule((Callable<Integer>) () -> {
                System.out.println(LocalDateTime.now());
                return new Random().nextInt(10);
            }, 2, TimeUnit.SECONDS);
            System.out.println(schedule.get());
        }
        pool2.shutdown();



    }
}
