package org.example.io;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Channel 测试
 * <p>
 * Java NIO的通道类似流，但又有些不同：
 * 1. 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
 * 2. 通道可以异步地读写。
 * 3. 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。
 * <p>
 * 有如下 Channel:
 * 1. FileChannel 从文件中读写数据。
 * 2. DatagramChannel 能通过UDP读写网络中的数据。
 * 3. SocketChannel 能通过TCP读写网络中的数据。
 * 4. ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/27 8:58 PM
 */
public class ChannelTest {

    /**
     * FileChannel 示例
     */
    public static void testFileChannel() throws Exception {
        StringBuilder builder = new StringBuilder();
        RandomAccessFile aFile = new RandomAccessFile("./pom.xml", "rw");
        FileChannel inChannel = aFile.getChannel();
        // 获取当前文件大小
        inChannel.size();
        // 1024 后面的部分将被删除
//        inChannel.truncate(1024);
        // 尚未写入磁盘的数据强制写到磁盘上
//        inChannel.force(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int byteReader = inChannel.read(byteBuffer);

        while (byteReader != -1) {
            byteBuffer.flip();

            while (byteBuffer.hasRemaining()) {
                builder.append((char) byteBuffer.get());
            }

            byteBuffer.clear();

            byteReader = inChannel.read(byteBuffer);
        }
        // 用完之后关闭
        inChannel.close();
        System.out.println(builder.toString());
    }

    /**
     * SocketChannel 示例
     */
    public static void testSocketChannel() throws Exception {
        // 第一中方式: 打开一个SocketChannel并连接到互联网上的某台服务器
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
        // 第二种方式: 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel

        // 读取数据
        ByteBuffer readBuf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(readBuf);

        // 写入数据
        String newData = "New String to write to file..." + System.currentTimeMillis();
        // 生成Buffer，并向Buffer中写数据
        ByteBuffer writeBuf = ByteBuffer.allocate(48);
        writeBuf.clear();
        writeBuf.put(newData.getBytes());
        // 切换 buffer 为读模式
        writeBuf.flip();
        while (writeBuf.hasRemaining()) {
            socketChannel.write(writeBuf);
        }

        // 设置非阻塞模式
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
        // 非阻塞模式下使用 finishConnect() 确认链接已经建立
        while(!socketChannel.finishConnect() ){
            //wait, or do something else...
        }

        // 用完之后关闭
        socketChannel.close();
    }

    public static void testServerSocketChannel() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        // 设置非阻塞模式, 此时 accept() 会立即返回
        serverSocketChannel.configureBlocking(false);
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //使用socketChannel做一些工作...
        }
        // 用完之后关闭
//        serverSocketChannel.close();
    }

    public static void main(String[] args) throws Exception {
        testFileChannel();
    }
}