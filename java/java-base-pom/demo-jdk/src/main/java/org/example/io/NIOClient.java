package org.example.io;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {
    private Selector selector;
    private static int port = 8090;
    private static int BUF_SIZE = 10240;
    private static ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);

    private void initClient() throws Exception {
        this.selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(port));
        channel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            final Set<SelectionKey> keys = selector.selectedKeys();
            final Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                final SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()) {
                    doConnect(key);
                } else if (key.isReadable()) {
                    doRead(key);
                }
            }
        }
    }

    private void doConnect(SelectionKey key) throws Exception {
        final SocketChannel channel = (SocketChannel) key.channel();
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        String info = "Good Server";
        byteBuffer.clear();
        byteBuffer.put(info.getBytes("UTF-8"));
        byteBuffer.flip();
        channel.write(byteBuffer);
        channel.close();
    }


    private void doRead(SelectionKey key) throws Exception {
        final SocketChannel channel = (SocketChannel) key.channel();
        int read = channel.read(byteBuffer);
        final byte[] array = byteBuffer.array();
        System.out.println("read: " + new String(array).trim());
        channel.close();
        key.selector().close();
    }

    public static void main(String[] args) throws Exception {
        NIOClient server = new NIOClient();
        server.initClient();
    }
}
