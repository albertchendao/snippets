package org.example.spring.boot2.req;

import org.example.spring.boot2.validation.EnumString;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 自定义参数校验
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/28 21:30
 */
@Data
public class ValidatedCustomizedReq {
    /**
     * 性别
     */
    @NotEmpty(message = "性别不能为空")
    @EnumString(value = {"F", "M"}, message = "性别只允许为F或M")
    private String sex;

}