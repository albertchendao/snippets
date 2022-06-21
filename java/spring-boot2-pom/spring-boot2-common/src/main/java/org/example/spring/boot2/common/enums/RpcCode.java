package org.example.spring.boot2.common.enums;

import lombok.Getter;

/**
 * 自定义 code
 */
@Getter
public enum RpcCode {

    TIME_OUT( 5001, "request time out"),

    // 通用错误
    SUCCESS(2000, "success"),
    SERVER_ERROR( 5000, "bad server"),
    ;


    private int code;
    private String message;

    RpcCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
