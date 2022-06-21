package org.example.spring.boot2.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.example.spring.boot2.common.api.HelloService;
import org.example.spring.boot2.common.dto.RpcResult;
import org.springframework.stereotype.Component;

/**
 * HelloServiceProvider
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/21 5:00 PM
 */
@Slf4j
@Component
public class HelloServiceConsumer {

    @Reference
    private HelloService helloService;

    public String sayHi() {
        return RpcResult.safeData(helloService.sayHi());
    }
}