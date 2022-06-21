package org.example.spring.boot2.common.dto;

import lombok.Data;
import org.example.spring.boot2.common.enums.RpcCode;

import java.io.Serializable;

/**
 * RpcResult
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/21 4:30 PM
 */
@Data
public class RpcResult<T> implements Serializable {
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 消息
     */
    private String msg;

    public RpcResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RpcResult(Integer code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> RpcResult<T> build(RpcCode rpcCode) {
        return new RpcResult<>(rpcCode.getCode(), rpcCode.getMessage());
    }

    public static <T> RpcResult<T> buildErrorMsg(RpcCode rpcCode, String msg) {
        return new RpcResult<>(rpcCode.getCode(), rpcCode.getMessage().concat(msg));
    }

    public static <T> RpcResult<T> build(RpcCode rpcCode, T data) {
        return new RpcResult<>(rpcCode.getCode(), rpcCode.getMessage(), data);
    }

    public static <T> RpcResult<T> buildSuccess(T data) {
        return build(RpcCode.SUCCESS, data);
    }

    public static <T> T safeData(RpcResult<T> rpcResult) {
        return rpcResult != null ? rpcResult.getData() : null;
    }
}