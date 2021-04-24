package com.fast_build_auth.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自动装配线程池
 *
 * @ConditionalOnClass 检测ThreadPoolExecutor存在，才会SpringBoot装配
 */
@Configuration
public class ThreadPoolAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadPoolAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(ThreadPoolExecutor.class)
    public ThreadPoolExecutor FastBuildThreadPool() {
        LOG.info("=========== Load FastBuildThreadPool ========");
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = new FastBuildThreadFactory("FastBuildAuth");
        RejectedExecutionHandler handler = new FastBuildAbortPolicy("FastBuildAuth");
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}

/**
 * 1、自定义的线程工厂
 * 2、一定要起线程别名
 * 3、通过 jstack 或者 authas 查看线程名称
 */
class FastBuildThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNum = new AtomicInteger(1);
    private String threadName;

    public FastBuildThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, threadName + "_" + threadNum.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(true);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}

/**
 * 1、自定义拒绝策略
 * 2、队列满后进行阻塞
 * 3、默认：异常，抛弃，最老抛弃，调用线程运行
 */
class FastBuildAbortPolicy extends ThreadPoolExecutor.AbortPolicy {
    private static final Logger LOG = LoggerFactory.getLogger(FastBuildAbortPolicy.class);

    private final String threadName;

    public FastBuildAbortPolicy(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        String msg = String.format("Provider端线程池满!" +
                        " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d)," +
                        " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        LOG.warn(msg);
        if (!e.isShutdown()) {
            try {
                LOG.info("Start get queue");
                e.getQueue().put(r);
                LOG.info("End get queue");
            } catch (InterruptedException ee) {
                LOG.error(ee.toString(), ee);
                Thread.currentThread().interrupt();
            }
        }
    }
}