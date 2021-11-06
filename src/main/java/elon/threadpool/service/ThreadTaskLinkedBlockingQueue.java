package elon.threadpool.service;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程任务阻塞队列.
 *
 * @author elon
 * @since 2021/11/6
 */
@Setter
public class ThreadTaskLinkedBlockingQueue<E> extends LinkedBlockingDeque<E> {
    private static final Logger LOGGER = LogManager.getLogger(ThreadTaskLinkedBlockingQueue.class);
    /**
     * 线程池
     */
    private ThreadPoolExecutor poolExecutor = null;

    /**
     * 线程池支持的最大线程数
     */
    private int maximumPoolSize = 0;

    @Override
    public boolean offer(E e) {
        int activeThreadNum = poolExecutor.getActiveCount();
        LOGGER.info("Number of active threads:{}", activeThreadNum);
        LOGGER.info("The number of tasks waiting to be processed in the queue:{}", size());

        if (activeThreadNum < maximumPoolSize) {
            return false;
        }
        return offerLast(e);
    }
}
