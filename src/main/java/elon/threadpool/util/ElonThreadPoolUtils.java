package elon.threadpool.util;

import elon.threadpool.service.ThreadTaskLinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static void setCorePoolSize(int corePoolSize) {
        ElonThreadPoolUtils.corePoolSize = corePoolSize;
    }

    public static void setMaximumPoolSize(int maximumPoolSize) {
        ElonThreadPoolUtils.maximumPoolSize = maximumPoolSize;
    }

    public static void initThreadPool(){
        ThreadTaskLinkedBlockingQueue<Runnable> queue = new ThreadTaskLinkedBlockingQueue<>();
        poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 10,
                TimeUnit.SECONDS, queue);

        queue.setPoolExecutor(poolExecutor);
        queue.setMaximumPoolSize(maximumPoolSize);

        LOGGER.info("Init thread pool success. corePoolSize:{}|maximumPoolSize:{}", corePoolSize, maximumPoolSize);
    }

    synchronized public static void executeTask(Runnable task){
        poolExecutor.execute(task);
    }

    synchronized public static void submit(Runnable task) {
        poolExecutor.submit(task);
    }
}
