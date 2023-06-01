package org.example.self;

import org.junit.Test;

/**
 * AutoCloseable 测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/3/16 8:09 PM
 */
public class AutoCloseableTest {

    interface AutoInitAndCloseable extends AutoCloseable {
        void init();

        void clear();

        @Override
        default void close() {
            clear();
        }

        static <T extends AutoInitAndCloseable> T init(T instance) {
            if (instance != null) {
                instance.init();
            }
            return instance;
        }

        static <T extends AutoInitAndCloseable> T clear(T instance) {
            if (instance != null) {
                instance.clear();
            }
            return instance;
        }
    }

    static class TestClzImp implements AutoInitAndCloseable {
        @Override
        public void init() {
            System.out.println("init");
        }

        @Override
        public void clear() {
            System.out.println("clear");
        }
    }

    /**
     * 为 null 时代码块能执行, init() clear() 不执行
     */
    @Test
    public void testNull() {
        try (TestClzImp impl = null) {
            System.out.println("执行");
        }
    }

    /**
     * 非 null 时代码块能执行, init() clear() 执行
     */
    @Test
    public void testNotNull() {
        try (TestClzImp impl = AutoInitAndCloseable.init(new TestClzImp())) {
            System.out.println("执行");
        }
    }
}
