package com.addbean.autils.core.task;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by AddBean on 2016/2/14.
 */
public abstract class BaseAsyncTask<Params, Progress, Result> {

    private static final int MESSAGE_POST_RESULT = 0x1;
    private static final int MESSAGE_POST_PROGRESS = 0x2;

    private static final InternalHandler sHandler = new InternalHandler();

    public static final Executor sDefaultExecutor = new TaskExecutors();
    private final WorkerRunnable<Params, Result> mWorker;
    private final FutureTask<Result> mFuture;

    private volatile boolean mExecuteInvoked = false;

    private final AtomicBoolean mCancelled = new AtomicBoolean();
    private final AtomicBoolean mTaskInvoked = new AtomicBoolean();


    /**
     * 创建一个异步线程，该构造函数必须在UI线程调用；
     */
    public BaseAsyncTask() {
        //创建一个工作线程；
        mWorker = new WorkerRunnable<Params, Result>() {
            public Result call() throws Exception {
                mTaskInvoked.set(true);
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                return postResult(doInBackground(mParams));//noinspection unchecked
            }
        };

        mFuture = new FutureTask<Result>(mWorker) {
            @Override
            protected void done() {
                try {
                    postResultIfNotInvoked(get());
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    throw new RuntimeException("An error occured while executing doInBackground()",e.getCause());
                } catch (CancellationException e) {
                    postResultIfNotInvoked(null);
                }
            }
        };
    }

    private void postResultIfNotInvoked(Result result) {
        final boolean wasTaskInvoked = mTaskInvoked.get();
        if (!wasTaskInvoked) {
            postResult(result);
        }
    }

    private Result postResult(Result result) {
        @SuppressWarnings("unchecked")
        Message message = sHandler.obtainMessage(MESSAGE_POST_RESULT,
                new AsyncTaskResult<Result>(this, result));
        message.sendToTarget();
        return result;
    }


    protected abstract Result doInBackground(Params... params);

    protected void onPreExecute() {
    }


    @SuppressWarnings({"UnusedDeclaration"})
    protected void onPostExecute(Result result) {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    protected void onProgressUpdate(Progress... values) {
    }


    @SuppressWarnings({"UnusedParameters"})
    protected void onCancelled(Result result) {
        onCancelled();
    }


    protected void onCancelled() {
    }


    public final boolean cancel(boolean mayInterruptIfRunning) {
        mCancelled.set(true);
        return mFuture.cancel(mayInterruptIfRunning);
    }


    public final Result get() throws InterruptedException, ExecutionException {
        return mFuture.get();
    }

    public final Result get(long timeout, TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        return mFuture.get(timeout, unit);
    }


    public final BaseAsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }


    public final BaseAsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec,
                                                                           Params... params) {
        if (mExecuteInvoked) {
            throw new IllegalStateException("Cannot execute task:"
                    + " the task is already executed.");
        }

        mExecuteInvoked = true;

        onPreExecute();

        mWorker.mParams = params;
        exec.execute(new TaskRunable(mFuture));

        return this;
    }


    public static void execute(Runnable runnable) {
        sDefaultExecutor.execute(new TaskRunable(runnable));
    }

    public final boolean isCancelled() {
        return mCancelled.get();
    }

    protected final void publishProgress(Progress... values) {
        if (!isCancelled()) {
            sHandler.obtainMessage(MESSAGE_POST_PROGRESS,
                    new AsyncTaskResult<Progress>(this, values)).sendToTarget();
        }
    }

    private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
    }

    private static class InternalHandler extends Handler {

        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        @SuppressWarnings({"unchecked", "RawUseOfParameterizedType"})
        @Override
        public void handleMessage(Message msg) {
            AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
            switch (msg.what) {
                case MESSAGE_POST_RESULT:
                    // There is only one result
                    result.mTask.finish(result.mData[0]);
                    break;
                case MESSAGE_POST_PROGRESS:
                    result.mTask.onProgressUpdate(result.mData);
                    break;
            }
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;
    }

    @SuppressWarnings({"RawUseOfParameterizedType"})
    private static class AsyncTaskResult<Data> {
        final BaseAsyncTask mTask;
        final Data[] mData;

        AsyncTaskResult(BaseAsyncTask task, Data... data) {
            mTask = task;
            mData = data;
        }
    }
}
