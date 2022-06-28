package org.example.io;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 参考链接: https://www.jianshu.com/p/465ecd909f8c
 *
 * Buffer 测试
 * <p>
 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
 * <p>
 * Buffer 使用过程:
 * 1. 写入数据到Buffer
 * 2. 调用flip()方法
 * 3. 从Buffer中读取数据
 * 4. 调用 clear() 方法(清空全部缓冲区)或者 compact() 方法(只清除已读过的数据)
 * <p>
 * 三个属性:
 * 1. capacity 容量, 在读写模式下都是一样的
 * 2. position 位置, 写模式下表示当前写的位置(从 0 到 capacity-1), 在读模式下表示当前读的位置(从 0 到 limit-1)
 * 3. limit 限制, 写模式下表示你最多能往 Buffer 里写多少数据, 在读模式下表示你最多能读到多少数据
 * <p>
 * 常用 Buffer: ByteBuffer, MappedByteBuffer, CharBuffer, DoubleBuffer, FloatBuffer, IntBuffer, LongBuffer, ShortBuffer
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/27 9:04 PM
 */
public class BufferTest {

    /**
     * 通过 Channel.read(Buffer) 写入 Buffer 数据
     * 通过 Channel.write(Buffer) 读取 Buffer 数据
     *
     * Buffer.rewind() 将 position 设回 0, 所以你可以重读 Buffer 中的所有数据
     * Buffer.clear() 将 position 设回 0, limit 被设置成 capacity 的值, 数据被清空
     * Buffer.compact() 将所有未读数据拷贝到 Buffer 起始处, 然后将 position 设到最后一个未读元素正后面, limit 属性依然像设置成 capacity
     * Buffer.mark() 可以标记 Buffer 中的一个特定 position. 之后可以通过调用 Buffer.reset() 方法恢复到这个 position
     */
    public static void testByChannel() throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("./pom.xml", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf); //read into buffer.
        buf.flip();
        StringBuilder builder = new StringBuilder();
        while (buf.hasRemaining()) {
            builder.append((char) buf.get());
        }
        System.out.println(builder);
    }

    /**
     * 直接 Buffer.put() 写入 Buffer 数据
     * 直接 Buffer.get() 读取 Buffer 数据
     */
    public static void testByBuffer() throws Exception {
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.putChar('a');
        buf.flip();
        System.out.println((char) buf.get());
    }

    public static void main(String[] args) throws Exception {
        testByChannel();
    }
}