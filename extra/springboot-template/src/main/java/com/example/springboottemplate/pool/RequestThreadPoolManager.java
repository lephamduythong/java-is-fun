package com.example.springboottemplate.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestThreadPoolManager {
    private static final int THREAD_POOL_SIZE = 32;
    private static final long TASK_TIMEOUT_SECONDS = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger("MY_SYSTEM");

    // 1. Biến static giữ instance duy nhất (Bill Pugh Singleton approach / Eager initialization)
    private static final RequestThreadPoolManager INSTANCE = new RequestThreadPoolManager();

    // Khai báo ExecutorService
    private final ThreadPoolExecutor executorService;
    private final ScheduledThreadPoolExecutor timeoutScheduler;

    // 2. Private constructor để ngăn việc khởi tạo từ bên ngoài bằng từ khóa 'new'
    private RequestThreadPoolManager() {
        ThreadFactory defaultFactory = Executors.defaultThreadFactory();
        AtomicLong threadNumber = new AtomicLong(1);
        this.timeoutScheduler = new ScheduledThreadPoolExecutor(1, task -> {
            Thread thread = defaultFactory.newThread(task);
            thread.setName("event-noti-timeout");
            return thread;
        });
        this.timeoutScheduler.setRemoveOnCancelPolicy(true);
        this.executorService = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), task -> {
            Thread thread = defaultFactory.newThread(task);
            thread.setName("event-noti-pool-" + threadNumber.getAndIncrement());
            return thread;
        }) {
            @Override
            protected void terminated() {
                timeoutScheduler.shutdown();
            }
        };
    }

    // 3. Phương thức public toàn cục để lấy instance duy nhất
    public static RequestThreadPoolManager getInstance() {
        return INSTANCE;
    }

    // 4. Phương thức push một tác vụ (không trả về kết quả) vào pool để chạy
    public void runTask(Runnable task) {
        submit(Executors.callable(task, null), true);
    }

    // 5. (Mở rộng) Phương thức push tác vụ có trả về kết quả (dùng Callable)
    public <T> Future<T> submitTask(Callable<T> task) {
        return submit(task, false);
    }

    private <T> Future<T> submit(Callable<T> task, boolean logFailure) {
        AtomicReference<ScheduledFuture<?>> timeout = new AtomicReference<>();
        FutureTask<T> future = new FutureTask<T>(task) {
            @Override
            public void run() {
                if (isDone()) {
                    return;
                }
                ScheduledFuture<?> scheduled;
                try {
                    scheduled = timeoutScheduler.schedule(() -> {
                        if (cancel(true)) {
                            // LOGGER.warn("Request pool task timed out after {} seconds", TASK_TIMEOUT_SECONDS);
                            System.err.println("Request pool task timed out after " + TASK_TIMEOUT_SECONDS + " seconds");
                        }
                    }, TASK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
                } catch (RejectedExecutionException e) {
                    setException(e);
                    return;
                } catch (Exception e) {
                    setException(e);
                    return;
                }
                timeout.set(scheduled);
                if (isDone()) {
                    scheduled.cancel(false);
                    return;
                }
                super.run();
            }

            @Override
            protected void done() {
                ScheduledFuture<?> scheduled = timeout.get();
                if (scheduled != null) {
                    scheduled.cancel(false);
                }
                if (isCancelled()) {
                    executorService.remove(this);
                } else if (logFailure) {
                    try {
                        get();
                    } catch (ExecutionException e) {
                        LOGGER.error("Request pool task failed", e.getCause());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.error("Interrupted while checking request pool task", e);
                    } catch (CancellationException e) {
                        LOGGER.warn("Request pool task was cancelled", e);
                    } catch (Exception e) {
                        LOGGER.error("Error while checking request pool task", e);
                    }
                }
            }
        };
        try {
            executorService.execute(future);
            if (future.isCancelled()) {
                executorService.remove(future);
            }
        } catch (RejectedExecutionException e) {
            future.cancel(false);
            throw e;
        } catch (Exception e) {
            future.cancel(false);
            throw e;
        }
        return future;
    }

    // Phương thức đóng Thread Pool khi ứng dụng tắt hẳn (tránh rò rỉ luồng)
    public void shutdown() {
        executorService.shutdown();
    }

    // Force close all tasks
    public void shutdownNow() {
        for (Runnable task : executorService.shutdownNow()) {
            if (task instanceof Future<?>) {
                ((Future<?>) task).cancel(false);
            }
        }
    }
}
