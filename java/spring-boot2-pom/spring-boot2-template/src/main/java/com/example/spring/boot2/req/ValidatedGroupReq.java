package com.example.spring.boot2.req;

import com.example.spring.boot2.validation.CommonGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 校验测试请求参数
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/28 20:36
 */
@Data
public class ValidatedGroupReq {

    /**
     * 针对特定的 group 生效
     */
    @Null(groups = {CommonGroup.Create.class}, message = "新增时不能传入 ID")
    @NotNull(groups = {CommonGroup.Update.class, CommonGroup.Delete.class}, message = "请传入 ID")
    private Integer id;

    /**
     * 没有指定 groups 则对所有继承了 Default 的分组生效
     */
    @NotBlank(message = "请输入名称")
    private String name;
}