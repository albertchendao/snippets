package org.example.spring.boot2.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.helper.JsonHelper;
import org.example.spring.boot2.resp.ResultResp;
import org.example.spring.boot2.resp.ResultRespFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 测试 GlobalResponseBodyAdvice
 */
@Slf4j
@RestController
@RequestMapping("/test/body")
public class ResponseBodyController {

    /**
     * http -v GET :8080/test/body/resp
     *
     * body: {"code":0,"data":"body","msg":"ok"}
     * body class: class org.example.spring.boot2.resp.ResultResp
     * MethodParameter.getParameterType(): class org.example.spring.boot2.resp.ResultResp
     */
    @RequestMapping(value = "resp", method = RequestMethod.GET)
    public ResultResp<String> resp() {
        return ResultRespFactory.success("body");
    }

    /**
     * http -v GET :8080/test/body/entity/resp
     *
     * body: {"code":0,"data":"body","msg":"ok"}
     * body class: class org.example.spring.boot2.resp.ResultResp
     * MethodParameter.getParameterType(): class org.springframework.http.ResponseEntity
     */
    @RequestMapping(value = "entity/resp", method = RequestMethod.GET)
    public ResponseEntity<ResultResp<String>> entityResp() {
        return ResponseEntity.ok(ResultRespFactory.success("body"));
    }

    /**
     * http -v GET :8080/test/body/entity/str
     *
     * 注意:
     * body: "{\"code\":0,\"data\":\"body\",\"msg\":\"ok\"}"
     * body class: class java.lang.String
     * MethodParameter.getParameterType(): class org.springframework.http.ResponseEntity
     *
     */
    @RequestMapping(value = "entity/str", method = RequestMethod.GET)
    public ResponseEntity<String> entityStr() {
        return ResponseEntity.ok(JsonHelper.toString(ResultRespFactory.success("body")));
    }

    /**
     * http -v GET :8080/test/body/void
     *
     * 不会被 GlobalResponseBodyAdvice 拦截
     */
    @RequestMapping(value = "void", method = RequestMethod.GET)
    public void voidQuery(HttpServletResponse response) throws Exception {
        response.getWriter().write("body");
    }

    /**
     * http -v GET :8080/test/body/void/resp
     *
     * 异常: getWriter() has already been called for this response
     */
    @RequestMapping(value = "void/resp", method = RequestMethod.GET)
    public ResultResp<String> voidResp(HttpServletResponse response) throws Exception {
        response.getWriter().write("body");
        return ResultRespFactory.success("body");
    }
}
