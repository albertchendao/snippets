package org.example.spring.boot2.common.api;

import org.example.spring.boot2.common.dto.RpcResult;

/**
 * HelloService
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/21 4:59 PM
 */
public interface HelloService {

    /**
     * sayHi
     */
    RpcResult<String> sayHi();
}