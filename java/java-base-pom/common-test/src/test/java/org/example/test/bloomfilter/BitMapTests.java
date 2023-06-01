package org.example.test.bloomfilter;

import org.junit.Assert;
import org.junit.Test;

import java.util.BitSet;

/**
 * 位图测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/3/1 20:47 AM
 */
public class BitMapTests {

    private static class MyBitMap {
        /**
         * 位图提供的最大长度
         */
        private long length;
        /**
         * 位图桶, 每个桶保存 32 个 bit
         */
        private static int[] bucket;
        /**
         * int用来表示32位二进制数,
         * BIT_VALUE[0]表示第1个二进制数存在、
         * BIT_VALUE[1]表示第2个二进制数存在，以此类推
         * <p>
         * BIT_VALUE[0] = 00000000 00000000 00000000 00000001
         * BIT_VALUE[1] = 00000000 00000000 00000000 00000010
         * BIT_VALUE[2] = 00000000 00000000 00000000 00000100
         * ...
         * BIT_VALUE[31] = 10000000 00000000 00000000 00000000
         */
        private static final int[] BIT_VALUE = {
                0x00000001, 0x00000002, 0x00000004, 0x00000008,
                0x00000010, 0x00000020, 0x00000040, 0x00000080,
                0x00000100, 0x00000200, 0x00000400, 0x00000800,
                0x00001000, 0x00002000, 0x00004000, 0x00008000,
                0x00010000, 0x00020000, 0x00040000, 0x00080000,
                0x00100000, 0x00200000, 0x00400000, 0x00800000,
                0x01000000, 0x02000000, 0x04000000, 0x08000000,
                0x10000000, 0x20000000, 0x40000000, 0x80000000};

        /**
         * length为1 - 32: 需要1个桶
         * length为33 - 64: 需要2个桶
         * ...
         */
        public MyBitMap(long length) {
            this.length = length;
            bucket = new int[toBucketIndex(length) + (toBucketOffset(length) > 0 ? 1 : 0)];
        }

        /**
         * 计算 bit 位在桶数组的下标
         */
        private int toBucketIndex(long bitIndex) {
            // 如下等效于 bitIndex / 32
            return (int) (bitIndex >> 5);
        }

        /**
         * 计算 bit 位在桶内的下标
         */
        private int toBucketOffset(long bitIndex) {
            // 31 二进制为 11111, 如下等效于 bitIndex % 32
            return (int) (bitIndex & 31);
        }

        /**
         * 判断对应的 bit 位是否为 1
         *
         * @param bitIndex 要查询的 bit 位
         */
        public boolean getBit(long bitIndex) {
            if (bitIndex < 0 || bitIndex >= length) {
                throw new IllegalArgumentException("只支持 [0, " + (length - 1) + "]");
            }
            // 计算该number在哪个桶
            int belowIndex = toBucketIndex(bitIndex);
            // 求出该number在桶里的下标,（求出该值在32位中的哪一位, 下标0 - 31）
            int offset = toBucketOffset(bitIndex);
            // 拿到该桶的值
            int currentValue = bucket[belowIndex];
            // 计算该number是否存在
            return ((currentValue & BIT_VALUE[offset])) != 0;
        }

        /**
         * 设置对应的 bit 位是否为 1
         *
         * @param bitIndex 要查询的 bit 位
         */
        public void setBit(long bitIndex) {
            if (bitIndex < 0 || bitIndex >= length) {
                throw new IllegalArgumentException("只支持 [0, " + (length - 1) + "]");
            }
            // 计算该number在哪个桶
            int belowIndex = toBucketIndex(bitIndex);
            // 求出该number在桶里的下标,（求出该值在32位中的哪一位, 下标0 - 31）
            int offset = toBucketOffset(bitIndex);
            // 拿到该桶的当前值
            int currentValue = bucket[belowIndex];
            // 将number在桶里标记
            bucket[belowIndex] = currentValue | BIT_VALUE[offset];
        }

        public long getLength() {
            return length;
        }

        public long getBucketSize() {
            return bucket.length;
        }
    }

    /**
     * 测试自定义 BitMap
     */
    @Test
    public void testMyBitMap() {
        MyBitMap bitMap = new MyBitMap(Integer.MAX_VALUE);
        bitMap.setBit(Integer.MAX_VALUE - 1);
        Assert.assertEquals(bitMap.getBucketSize(), 1);
        Assert.assertEquals(bitMap.getBit(Integer.MAX_VALUE - 2), false);
        Assert.assertEquals(bitMap.getBit(Integer.MAX_VALUE - 1), true);
//        Assert.assertEquals(bitMap.getBit(Integer.MAX_VALUE), true);
    }

    /**
     * 测试自定义 BitMap
     */
    @Test
    public void testBitSet() {
        BitSet bitMap = new BitSet(Integer.MAX_VALUE);
        bitMap.set(Integer.MAX_VALUE - 1, true);
        Assert.assertEquals(bitMap.get(Integer.MAX_VALUE - 2), false);
        Assert.assertEquals(bitMap.get(Integer.MAX_VALUE - 1), true);
//        Assert.assertEquals(bitMap.get(Integer.MAX_VALUE), false);
    }
}
