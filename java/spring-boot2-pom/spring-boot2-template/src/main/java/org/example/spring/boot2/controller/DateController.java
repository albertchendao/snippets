package org.example.spring.boot2.controller;

import org.example.spring.boot2.resp.ResultResp;
import org.example.spring.boot2.resp.ResultRespFactory;
import org.example.spring.boot2.req.DateBodyReq;
import org.example.spring.boot2.resp.DateBodyResp;
import org.example.spring.boot2.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 日期测试
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/28 20:36
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/test/date")
public class DateController {

    /**
     * 请求参数测试
     *
     * http -v :8080/test/date/old date=='2022-01-02 03:04:05'
     */
    @RequestMapping(value = "/old", method = RequestMethod.GET)
    public ResultResp<Date> oldDate(@RequestParam(value = "date", required = false) Date date) {
        return ResultRespFactory.success(date);
    }

    /**
     * 请求参数测试
     *
     * http -v :8080/test/date/datetime date=='2022-01-02 03:04:05'
     */
    @RequestMapping(value = "/datetime", method = RequestMethod.GET)
    public ResultResp<LocalDateTime> date(@RequestParam(value = "date", required = false) LocalDateTime date) {
        return ResultRespFactory.success(date);
    }

    /**
     * 请求参数测试
     *
     * http -v :8080/test/date/date date=='2022-01-02'
     */
    @RequestMapping(value = "/date", method = RequestMethod.GET)
    public ResultResp<LocalDate> day(@RequestParam(value = "date", required = true) LocalDate date) {
        return ResultRespFactory.success(date);
    }

    /**
     * 请求参数测试
     *
     * http -v :8080/test/date/time date=='03:04:05'
     */
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public ResultResp<LocalTime> time(@RequestParam(value = "date", required = true) LocalTime time) {
        return ResultRespFactory.success(time);
    }

    /**
     * 请求参数测试
     *
     * http -v :8080/test/date/format date=='01.02.2022 03:04:05'
     */
    @RequestMapping(value = "/format", method = RequestMethod.GET)
    public ResultResp<LocalDateTime> format(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") LocalDateTime date) {
        return ResultRespFactory.success(date);
    }

    /**
     * 请求参数测试, 这个输入非法格式会异常 01.01.2022 10:10:10
     *
     * http -v :8080/test/date/none date=='01.02.2022 03:04:05'
     */
    @RequestMapping(value = "/none", method = RequestMethod.GET)
    public ResultResp<LocalDateTime> none(@RequestParam(value = "date", required = false) LocalDateTime date) {
        return ResultRespFactory.success(date);
    }


    /**
     * 请求参数测试
     *
     * http -v POST :8080/test/date date='2022-01-02 03:04:05' localDate='2022-01-02' localDateTime='2022-01-02 03:04:05' localTime='03:04:05'
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResultResp<DateBodyResp> body(@RequestBody DateBodyReq req) {
        log.debug(req.toString());
        LocalDateTime now = LocalDateUtil.nowDateTime();
        DateBodyResp resp = new DateBodyResp();
        resp.setDate(LocalDateUtil.toOldDate(now));
        resp.setLocalDate(now.toLocalDate());
        resp.setLocalDateTime(now);
        resp.setLocalTime(now.toLocalTime());
        return ResultRespFactory.success(resp);
    }

}