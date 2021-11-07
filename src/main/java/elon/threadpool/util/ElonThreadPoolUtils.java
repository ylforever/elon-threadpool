package elon.threadpool.util;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池基础类。
 *
 * @author elon
 * @since 2021/11/6
 */
public class ElonThreadPoolUtils {
    private static final Logger LOGGER = LogManager.getLogger(ElonThreadPoolUtils.class);

    private static int corePoolSize = 10;

    private static int maximumPoolSize = 100;

    private static ThreadPoolExecutor poolExecutor = null;

    private static ThreadTaskLinkedBlockingQueue<Runnable> queue = new ThreadTaskLinkedBlockingQueue<>();

    public static void initThreadPool(int corePoolSize, int maximumPoolSize){
        ElonThreadPoolUtils.corePoolSize = corePoolSize;
        ElonThreadPoolUtils.maximumPoolSize = maximumPoolSize;

        poolExecutor = new ThreadPoolExecutor(ElonThreadPoolUtils.corePoolSize, ElonThreadPoolUtils.maximumPoolSize, 10,
                TimeUnit.SECONDS, queue);

        LOGGER.info("[ElonThreadPoolUtils]Init thread pool success. corePoolSize:{}|maximumPoolSize:{}", corePoolSize,
                maximumPoolSize);
    }

    synchronized public static void executeTask(Runnable task){
        int activeThreadNum = poolExecutor.getActiveCount();
        LOGGER.info("[ElonThreadPoolUtils]Number of active threads:{}", activeThreadNum);
        LOGGER.info("[ElonThreadPoolUtils]The number of tasks waiting to be processed in the queue:{}", queue.size());

        poolExecutor.execute(task);
    }

    /**
     * 自定义线程任务阻塞队列. 在活跃线程数小于最大支持线程数的情况下，新任务不放到队列从而激发线程池创建新线程及时处理.
     * 解决使用LinkedBlockingDeque无限队列，线程池只有核心线程在处理。maximumPoolSize未启作用的问题。
     *
     * @author elon
     * @since 2021/11/6
     */
    @Setter
    private static class ThreadTaskLinkedBlockingQueue<E> extends LinkedBlockingDeque<E> {
        @Override
        public boolean offer(E e) {
            int activeThreadNum = poolExecutor.getActiveCount();
            if (activeThreadNum < maximumPoolSize) {
                return false;
            }

            return offerLast(e);
        }
    }
}
