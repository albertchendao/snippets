package org.example.spring.boot2.controller;

import com.alibaba.fastjson.JSONObject;
import org.example.spring.boot2.resp.ResultResp;
import org.example.spring.boot2.resp.ResultRespFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 参数绑定测试
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/17 4:33 PM
 */
@Slf4j
@RestController
@RequestMapping("/test/bind")
public class ParamBindController {

    @Autowired
    private HttpServletRequest request;

    private JSONObject body() {
        JSONObject result = new JSONObject();
        try {
            result.put("parameterMap", request.getParameterMap());
            result.put("body", IOUtils.toString(request.getInputStream()));
        } catch (IOException e) {
            log.error("body()", e);
        }
        return result;
    }

    @Data
    public static class NameBody {
        private String name;
    }

    /**
     * http -v GET :8080/test/bind/query name==hello
     */
    @RequestMapping(value = "query", method = RequestMethod.GET)
    public ResultResp<Object> queryGet(@RequestParam(value = "name", defaultValue = "Albert") String name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }

    /**
     * http -v POST :8080/test/bind/query name==hello
     */
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public ResultResp<Object> queryPost(@RequestParam(value = "name", defaultValue = "Albert") String name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }

    /**
     * http -v GET :8080/test/bind/body name==hello
     */
    @RequestMapping(value = "body", method = RequestMethod.GET)
    public ResultResp<Object> bodyGet(NameBody name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }

    /**
     * <p>
     * http -v --form POST :8080/test/bind/body name=hello
     *
     * <code>
     * POST /test/bind/body HTTP/1.1
     * Accept-Encoding: gzip, deflate
     * Connection: keep-alive
     * Content-Length: 10
     * Content-Type: application/x-www-form-urlencoded; charset=utf-8
     * Host: localhost:8080
     * User-Agent: HTTPie/3.1.0
     * <p>
     * name=hello
     * </code>
     * <p>
     * 1. 必须没有 @RequestBody
     * 2. body 无值
     * 3. parameterMap 有值
     */
    @RequestMapping(value = "body", consumes = "application/x-www-form-urlencoded", method = RequestMethod.POST)
    public ResultResp<Object> bodyPostFormUrlencoded(NameBody name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }

    /**
     * <p>
     * http -v --multipart --boundary POST :8080/test/bind/body name=hello
     *
     * <code>
     * POST /test/bind/body HTTP/1.1
     * Accept-Encoding: gzip, deflate
     * Connection: keep-alive
     * Content-Length: 72
     * Content-Type: multipart/form-data; boundary=POST
     * Host: localhost:8080
     * User-Agent: HTTPie/3.1.0
     * <p>
     * --POST
     * Content-Disposition: form-data; name="name"
     * <p>
     * hello
     * --POST--
     * </code>
     * <p>
     * 1. 必须没有 @RequestBody
     * 2. body 无值
     * 3. parameterMap 有值
     */
    @RequestMapping(value = "body", consumes = "multipart/form-data", method = RequestMethod.POST)
    public ResultResp<Object> bodyPostFormData(NameBody name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }

    /**
     * <p>
     * http -v --json POST :8080/test/bind/body name=hello
     *
     * <code>
     * POST /test/bind/body HTTP/1.1
     * Connection: keep-alive
     * Content-Length: 17
     * Content-Type: application/json
     * Host: localhost:8080
     * User-Agent: HTTPie/3.1.0
     * <p>
     * {
     * "name": "hello"
     * }
     * </code>
     * <p>
     * 1. 必须要有 @RequestBody
     * 2. body 有值
     * 3. parameterMap 无值
     */
    @RequestMapping(value = "body", consumes = "application/json", method = RequestMethod.POST)
    public ResultResp<Object> bodyPostJson(@RequestBody NameBody name) {
        log.info("{}", name);
        return ResultRespFactory.success(body());
    }
}