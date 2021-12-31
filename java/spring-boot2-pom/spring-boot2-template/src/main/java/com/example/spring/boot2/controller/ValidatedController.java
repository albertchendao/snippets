package com.example.spring.boot2.controller;

import com.example.spring.boot2.req.ValidatedBodyReq;
import com.example.spring.boot2.req.ValidatedCustomizedReq;
import com.example.spring.boot2.resp.ResultResp;
import com.example.spring.boot2.resp.ResultRespFactory;
import com.example.spring.boot2.validation.CommonGroup;
import com.example.spring.boot2.req.ValidatedGroupReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 参数校验测试
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/28 20:36
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/test/validated")
public class ValidatedController {

    /**
     * 请求参数测试
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResultResp<Boolean> validatedGet(@RequestParam(value = "name", required = false) @NotEmpty(message = "请输入名称") String name) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 表单测试
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultResp<Boolean> validatedPost(@Validated ValidatedBodyReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 请求体测试
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultResp<Boolean> validatedPut(@Validated @RequestBody ValidatedBodyReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 请求参数测试
     */
    @RequestMapping(value = "/must", method = RequestMethod.GET)
    public ResultResp<Boolean> validatedMustGet(@RequestParam("name") String name) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }


    /**
     * 请求体测试
     */
    @RequestMapping(value = "/customized", method = RequestMethod.PUT)
    public ResultResp<Boolean> validatedCustomized(@Validated @RequestBody ValidatedCustomizedReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 请求体测试
     */
    @RequestMapping(value = "/validated/group", method = RequestMethod.POST)
    public ResultResp<Boolean> validatedGroupPost(@Validated(CommonGroup.Create.class) @RequestBody ValidatedGroupReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 请求体测试
     */
    @RequestMapping(value = "/validated/group", method = RequestMethod.PUT)
    public ResultResp<Boolean> validatedGroupPut(@Validated(CommonGroup.Update.class) @RequestBody ValidatedGroupReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }

    /**
     * 请求体测试
     */
    @RequestMapping(value = "/validated/group", method = RequestMethod.DELETE)
    public ResultResp<Boolean> validatedGroupDelete(@Validated(CommonGroup.Delete.class) @RequestBody ValidatedGroupReq req) {
        return ResultRespFactory.error(ResultRespFactory.SYS_ERROR, "不应该返回这个");
    }
}