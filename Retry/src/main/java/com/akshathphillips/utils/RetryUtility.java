package com.akshathphillips.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class RetryUtility {
    private static final int RETRY = 3;
    private static final long DELAY = 1000L;
    private static Logger logger = LoggerFactory.getLogger(RetryUtility.class);

    @FunctionalInterface
    public interface Retryable {
        void run() throws Exception;
    }

    public static <V> V retry(Callable<V> callable, Throwable throwable, String message) {
        return doWork(callable, throwable, message);
    }

    public static void retry(Retryable runnable, Throwable throwable, String message) {
        doWork(() -> {
            runnable.run();
            return null;
        }, throwable, message);
    }

    private static <T> T doWork(Callable<T> callable, Throwable throwable, String message) {
        int counter = 0;

        while (counter < RETRY) {
            try {
                return callable.call();
            } catch (Exception e) {
                counter++;
                logger.error("Retry # <{}> / <{}>, <{}>", counter, RETRY, message, e);

                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        throw new RuntimeException(throwable);
    }
}
