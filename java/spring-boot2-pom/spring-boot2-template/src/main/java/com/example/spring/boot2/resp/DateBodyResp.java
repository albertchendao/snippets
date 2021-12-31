package com.example.spring.boot2.resp;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 日期测试返回
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/30 21:31
 */
@Data
public class DateBodyResp {
    private Date date;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private LocalTime localTime;
}