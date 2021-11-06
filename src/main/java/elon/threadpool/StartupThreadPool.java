package elon.threadpool;

import elon.threadpool.service.ThreadTask;
import elon.threadpool.util.ElonThreadPoolUtils;

/**
 * 应用启动类
 *
 * @author elon
 * @since 2021/11/6
 */
public class StartupThreadPool {
    public static void main(String[] args) {
        ElonThreadPoolUtils.initThreadPool();
        for (int i = 1; i <= 1000; ++i) {
            ElonThreadPoolUtils.executeTask(new ThreadTask(String.valueOf(i)));
        }
    }
}
