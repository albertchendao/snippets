package org.example.spring.boot2.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.external.ExternalSayService;
import org.example.spring.boot2.resp.ResultResp;
import org.example.spring.boot2.resp.ResultRespFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 外部模块调用
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 3:19 PM
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/test/external")
public class ExternalController {

    @Autowired
    private ExternalSayService externalSayService;

    /**
     * 请求参数测试
     * <p>
     * http -v :8080/test/external/say
     */
    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public ResultResp<String> say() {
        String data = externalSayService.say();
        return ResultRespFactory.success(data);
    }
}
