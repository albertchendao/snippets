package org.example.spring.boot2.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 校验测试请求参数
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/28 20:36
 */
@Data
public class ValidatedBodyReq {

    @NotEmpty(message = "请输入名称")
    private String name;
}