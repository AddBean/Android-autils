package com.addbean.autils.core.task;

/**
 * Created by AddBean on 2016/2/13.
 */
public class TaskRunable implements Runnable {
    public Runnable mRunnable;

    public TaskRunable(Runnable runable) {
        this.mRunnable = runable;
    }

    @Override
    public void run() {
        this.mRunnable.run();
    }
}
