package org.example.spring.boot2.dubbo.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
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
@Service
@Component
public class HelloServiceProvider implements HelloService {

    @Override
    public RpcResult<String> sayHi() {
        return RpcResult.buildSuccess("world");
    }
}