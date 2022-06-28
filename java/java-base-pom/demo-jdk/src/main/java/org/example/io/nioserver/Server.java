package org.example.io.nioserver;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 参考: https://github.com/jjenkov/java-nio-server
 */
public class Server {

    private SocketAccepter socketAccepter = null;
    private SocketProcessor socketProcessor = null;

    private int tcpPort = 0;
    private IMessageReaderFactory messageReaderFactory = null;
    private IMessageProcessor messageProcessor = null;

    public Server(int tcpPort, IMessageReaderFactory messageReaderFactory, IMessageProcessor messageProcessor) {
        this.tcpPort = tcpPort;
        this.messageReaderFactory = messageReaderFactory;
        this.messageProcessor = messageProcessor;
    }

    public void start() throws IOException {
        //move 1024 to ServerConfig
        Queue<Socket> socketQueue = new ArrayBlockingQueue<>(1024);
        this.socketAccepter = new SocketAccepter(tcpPort, socketQueue);
        Thread accepterThread = new Thread(this.socketAccepter);

        MessageBuffer readBuffer = new MessageBuffer();
        MessageBuffer writeBuffer = new MessageBuffer();
        this.socketProcessor = new SocketProcessor(socketQueue,
                readBuffer, writeBuffer,
                this.messageReaderFactory,
                this.messageProcessor);
        Thread processorThread = new Thread(this.socketProcessor);

        accepterThread.start();
        processorThread.start();
    }


}
