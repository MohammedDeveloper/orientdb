package com.orientechnologies.common.thread;

import com.orientechnologies.common.log.OLogManager;

import java.util.concurrent.*;

/**
 * The same as thread {@link ThreadPoolExecutor} but also logs all exceptions happened inside of the tasks which caused
 * tasks to stop.
 */
public class OThreadPoolExecutorWithLogging extends ThreadPoolExecutor {
  public OThreadPoolExecutorWithLogging(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public OThreadPoolExecutorWithLogging(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
  }

  public OThreadPoolExecutorWithLogging(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
  }

  public OThreadPoolExecutorWithLogging(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
      BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    super.afterExecute(r, t);

    if (r instanceof Future<?>) {
      final Future<?> future = (Future<?>) r;
      try {
        future.get();
      } catch (CancellationException ce) {
        t = ce;
      } catch (ExecutionException ee) {
        t = ee.getCause();
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt(); // ignore/reset
      }
    }

    if (t != null) {
      final Thread thread = Thread.currentThread();
      OLogManager.instance().error(this, "Exception in thread '%s'", t, thread.getName());
    }
  }
}
