package org.example.io;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private Selector selector;
    private static int port = 8090;
    private static int BUF_SIZE = 10240;

    private void initServer() throws Exception {
        this.selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(port));
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            final Set<SelectionKey> keys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    doAccept(key);
                } else if (key.isReadable()) {
                    doRead(key);
                } else if (key.isWritable() && key.isValid()) {
                    doWrite(key);
                } else if (key.isConnectable()) {
                    System.out.println("连接成功！");
                }
            }
        }
    }

    private void doAccept(SelectionKey key) throws Exception {
        final ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        System.out.println("正在监听");
        final SocketChannel channel = serverSocketChannel.accept();
        channel.configureBlocking(false);
        channel.register(key.selector(), SelectionKey.OP_READ);
    }


    private void doRead(SelectionKey key) throws Exception {
        final SocketChannel channel = (SocketChannel) key.channel();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        StringBuilder builder = new StringBuilder();
        int read = channel.read(byteBuffer);
        while (read > 0) {
            byteBuffer.clear();
            builder.append(new String(byteBuffer.array()).trim());
            read = channel.read(byteBuffer);
        }
        System.out.println("read: " + builder.toString());
        byteBuffer.clear();
        final String write = builder.toString().toUpperCase();
        byteBuffer.put(write.getBytes("UTF-8"));
        byteBuffer.flip();
        channel.write(byteBuffer);
        System.out.println("write: " + write);
    }

    private void doWrite(SelectionKey key) throws Exception {
//        final ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
//        byteBuffer.flip();
//        final SocketChannel channel = (SocketChannel) key.channel();
//        while (byteBuffer.hasRemaining()) {
//            channel.write(byteBuffer);
//        }
//        byteBuffer.compact();
    }

    public static void main(String[] args) throws Exception {
        NIOServer server = new NIOServer();
        server.initServer();
    }

}
