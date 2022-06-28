package org.example.io;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
 * 这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
 * 仅用单个线程来处理多个Channel的好处是：只需要更少的线程来处理通道，线程之间上下文切换的开销很大。
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/28 9:34 AM
 */
public class SelectorTest {

    /**
     *
     */
    public static void testRegister() throws Exception {
        SocketChannel channel = null;
        Selector selector = Selector.open();
        // 与 Selector 一起使用时，Channel 必须处于非阻塞模式下。
        // 注意 FileChannel 不能切换到非阻塞模式
        channel.configureBlocking(false);
        // 某个channel成功连接到另一个服务器称为“连接就绪”  Connect
        // 一个server socket channel准备好接收新进入的连接称为“接收就绪” Accept
        // 一个有数据可读的通道可以说是“读就绪” Read
        // 等待写数据的通道可以说是“写就绪” Write
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_READ);
        // 判断 channel 什么事件已经就绪
//        selectionKey.isAcceptable();
//        selectionKey.isConnectable();
//        selectionKey.isReadable();
//        selectionKey.isWritable();
        // 可以将一个对象或者更多信息附着到 SelectionKey 上, 比如 buffer
//        selectionKey.attach(theObject);
//        Object attachedObj = selectionKey.attachment();

        // select()阻塞到至少有一个通道在你注册的事件上就绪
        int count = selector.select();
        // select(long timeout) 最长会阻塞 timeout 毫秒
//        key.selector().select(timeout);
        // selectNow()不会阻塞，不管什么通道就绪都立刻返回
//        key.selector().selectNow();

        // 有一个或更多个通道就绪
        if (count > 0) {
            // 访问“已选择键集（selected key set）”中的就绪通道
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // 一个连接被ServerSocketChannel接受
                } else if (key.isConnectable()) {
                    // 与远程服务器建立了连接
                } else if (key.isReadable()) {
                    // 一个channel做好了读准备
                } else if (key.isWritable()) {
                    // 一个channel做好了写准备
                }
                // 需要手动从 set 中移除
                keyIterator.remove();
            }
        }

//        某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。
//        只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回。
//        如果有其它线程调用了wakeup()方法，但当前没有线程阻塞在`select()方法上，下个调用select()方法的线程会立即“醒来（wake up）”。
    }
}