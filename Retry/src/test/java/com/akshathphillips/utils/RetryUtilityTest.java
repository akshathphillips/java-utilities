package com.akshathphillips.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class RetryUtilityTest {

    @Test
    public void test_retryable_runnable() {
        RetryUtility.retry(() -> System.out.println("Something retryable"), new IOException(), "error occurs");
    }

    @Test
    public void test_retryable_runnable_with_exception() {
        Assertions.assertThrows(Exception.class,
                () -> {
                    RetryUtility.retry(() -> {
                        throw new Exception();
                    }, new IOException(), "An error has occurred");
                });
    }

    @Test
    public void test_retryable_callable() {
        String result = RetryUtility.retry(() -> "Something retryable", new IOException(), "An error has occurred");
        System.out.println(result);

        List<String> results = RetryUtility.retry(() -> Arrays.asList("Test1", "Test2", "Test3"), new IOException(), "An error has occurred");
        results.forEach(str -> System.out.println("List: " + str));
    }

    @Test
    public void test_retryable_callable_with_exception() {
        Assertions.assertThrows(Exception.class, () -> {
            String result = RetryUtility.retry(() -> {
                throw new Exception();
            }, new IOException(), "An error has occurred");
        });
    }
}
