package org.example.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RequestMapping("/dev")
@RestController
public class DevController implements Lifecycle {

    private volatile boolean isRunning;

//    @Override
//    public boolean isAutoStartup() {
//        return true;
//    }
//
//    @Override
//    public void stop(Runnable callback) {
//        callback.run();
//    }
//
//    @Override
//    public int getPhase() {
//        return 0;
//    }

    @Override
    public void start() {
        isRunning = true;
        log.debug("start()");
    }

    @Override
    public void stop() {
        isRunning = false;
        log.debug("stop()");
    }

    @Override
    public boolean isRunning() {
        log.debug("isRunning()");
        return isRunning;
    }

    @GetMapping("/hello")
    public String sayHello() throws UnknownHostException {
        String hostname = "Unknown";
        InetAddress address = InetAddress.getLocalHost();
        hostname = address.getHostName();
        return hostname;
    }
}
