package elon.threadpool.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 线程任务
 *
 * @author elon
 * @since 2021/11/6
 */
public class ThreadTask implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ThreadTask.class);

    private final String threadName;

    public ThreadTask(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        LOGGER.info("threadName:{}", threadName);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
