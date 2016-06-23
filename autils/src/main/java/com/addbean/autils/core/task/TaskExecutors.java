package com.addbean.autils.core.task;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by AddBean on 2016/2/13.
 */
public class TaskExecutors implements Executor {
    public int MIN_THREAD_COUNT = 8;//创建线程池，最小线程数为2
    public int MAX_THREAD_COUNT = 10;//最大线程数为4
    public int WITE_TIME = 3;//线程池维护线程的空闲时间为3秒
    public int QUEUE_DEEP = 4;//使用队列深度为4的有界队列，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除,然后重试执行程序（如果再次失败，则重复此过程），里面已经根据队列深度对任务加载进行了控制。
    public static ThreadPoolExecutor mThreadPoolExecutor;

    public TaskExecutors() {
        getThreadPool(MIN_THREAD_COUNT);
    }

    public TaskExecutors(int poolSize) {
        getThreadPool(poolSize);
    }

    public synchronized ThreadPoolExecutor getThreadPool(int poolSize) {
        if (mThreadPoolExecutor == null)
            mThreadPoolExecutor = new ThreadPoolExecutor(poolSize, MAX_THREAD_COUNT, WITE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(QUEUE_DEEP),
                    new ThreadPoolExecutor.DiscardOldestPolicy());
        return mThreadPoolExecutor;
    }

    public boolean isBusy() {
        return mThreadPoolExecutor.getActiveCount() >= mThreadPoolExecutor.getCorePoolSize();
    }

    @Override
    public void execute(Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }
}
