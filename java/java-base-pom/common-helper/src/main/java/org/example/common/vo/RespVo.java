package org.example.common.vo;

import lombok.Data;

/**
 * 封装统一返回码
 */
@Data
public class RespVo<T> {
    /**
     * 接口状态码
     */
    private Integer code;

    /**
     * 接口状态信息
     */
    private String content;

    /**
     * 接口返回数据
     */
    private T data;
}
