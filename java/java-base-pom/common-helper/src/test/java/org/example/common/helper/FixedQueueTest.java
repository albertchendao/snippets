package org.example.common.helper;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.google.common.base.Equivalence;
import com.google.common.collect.EvictingQueue;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 固定长度队列测试
 *
 * @author Albert
 * @version 1.0
 * @since 2022/11/17 8:34 PM
 */
public class FixedQueueTest {

    /**
     * 使用 LinkedHashMap
     */
    @Test
    public void testLinkedHashMap() {
        LinkedHashMap<Integer, Integer> queue = new LinkedHashMap<Integer, Integer>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return this.size() > 2;
            }
        };
        queue.put(1, 1);
        queue.put(2, 2);
        queue.put(3, 3);
        System.out.println(queue.containsKey(1));
        // {2=2, 3=3}
        System.out.println(queue);
    }

    /**
     * 使用 CircularFifoQueue
     */
    @Test
    public void testCircularFifoQueue() {
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(2);
        queue.add(1);
        queue.add(1);
        queue.add(1);
        System.out.println(queue.contains(1));
        // [1, 1]
        System.out.println(queue);
    }

    /**
     * 使用 EvictingQueue
     */
    @Test
    public void testEvictingQueue() {
        EvictingQueue<Integer> queue = EvictingQueue.create(2);
        queue.add(1);
        queue.add(1);
        queue.add(1);
        System.out.println(queue.contains(1));
        // [1, 1]
        System.out.println(queue);
    }

    /**
     * 使用 Equivalence
     */
    @Test
    public void testEquivalence() {
        Equivalence<Object> eq = null;

        eq = Equivalence.equals();
        // true
        System.out.println(eq.equivalent(1, 1));
        // true
        System.out.println(eq.equivalent(300, 300));
        // true
        System.out.println(eq.equivalent(Equivalence.equals().wrap(300), Equivalence.equals().wrap(300)));

        eq = Equivalence.identity();
        // true
        System.out.println(eq.equivalent(1, 1));
        // false
        System.out.println(eq.equivalent(300, 300));
        // false
        System.out.println(eq.equivalent(Equivalence.equals().wrap(300), Equivalence.equals().wrap(300)));
    }
}
