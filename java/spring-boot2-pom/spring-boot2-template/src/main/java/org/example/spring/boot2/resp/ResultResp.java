package org.example.spring.boot2.resp;

import lombok.Data;

/**
 * 标准返回体
 * <p>
 * 注意:
 * 1. 如果要扩展字段, 比如返回分页信息, 请添加到 data 对象里
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/30 17:45
 */
@Data
public class ResultResp<T> {
    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
}