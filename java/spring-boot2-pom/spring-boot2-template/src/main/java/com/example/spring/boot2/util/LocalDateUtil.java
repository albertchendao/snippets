package com.example.spring.boot2.util;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * 日期工具
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 17:58
 */
public final class LocalDateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYYMM = "yyyyMM";
    public static final String MM_DD = "MM-dd";
    public static final String MMDD = "MMdd";

    private LocalDateUtil() {
    }

    public static final ZoneId GLOBAL_ZONE_ID = TimeZone.getTimeZone("Asia/Shanghai").toZoneId();

    /**
     * 获取当前时间
     */
    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(GLOBAL_ZONE_ID);
    }

    /**
     * 获取当前时间
     */
    public static LocalDate nowDate() {
        return LocalDate.now(GLOBAL_ZONE_ID);
    }

    /**
     * 获取当前时间
     */
    public static LocalTime nowTime() {
        return LocalTime.now(GLOBAL_ZONE_ID);
    }


    /**
     * 旧日期转新日期
     *
     * @param from 旧日期
     * @return 新日志
     */
    public static LocalDateTime toDateTime(Date from) {
        if (from != null) {
            return from.toInstant().atZone(GLOBAL_ZONE_ID).toLocalDateTime();
        }
        return null;
    }

    /**
     * 旧日期转新日期
     *
     * @param from 旧日期
     * @return 新日志
     */
    public static LocalDateTime toDateTime(LocalDate from) {
        if (from != null) {
            return from.atTime(0, 0, 0);
        }
        return null;
    }

    /**
     * 旧日期转新日期
     *
     * @param from 旧日期
     * @return 新日志
     */
    public static LocalDate toDate(Date from) {
        if (from != null) {
            return from.toInstant().atZone(GLOBAL_ZONE_ID).toLocalDate();
        }
        return null;
    }

    /**
     * 旧日期转新日期
     *
     * @param from 旧日期
     * @return 新日志
     */
    public static LocalDate toDate(LocalDateTime from) {
        if (from != null) {
            return from.toLocalDate();
        }
        return null;
    }


    /**
     * 新日期转旧日期
     *
     * @param from 新日期
     * @return 旧日志
     */
    public static Date toOldDate(LocalDateTime from) {
        if (from != null) {
            return Date.from(from.atZone(GLOBAL_ZONE_ID).toInstant());
        }
        return null;
    }

    /**
     * 新日期转旧日期
     *
     * @param from 新日期
     * @return 旧日志
     */
    public static Date toOldDate(LocalDate from) {
        if (from != null) {
            ZonedDateTime zonedDateTime = from.atStartOfDay(GLOBAL_ZONE_ID);
            return Date.from(zonedDateTime.toInstant());
        }
        return null;
    }


    /**
     * 时间格式化
     *
     * @param date   时间
     * @param format 格式
     * @return 格式化之后的时间字符串
     */
    public static String formatDate(LocalDate date, String format) {
        if (Objects.isNull(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 时间格式化
     *
     * @param date   时间
     * @param format 格式
     * @return 格式化之后的时间字符串
     */
    public static String formatDateTime(LocalDateTime date, String format) {
        if (Objects.isNull(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析时间
     *
     * @param date   时间字符串
     * @param format 时间格式
     */
    public static LocalDateTime parseDateTime(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析时间
     *
     * @param date   时间字符串
     * @param format 时间格式
     */
    public static LocalDate parseDate(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    /**
     * 解析时间
     *
     * @param date   时间字符串
     * @param format 时间格式
     */
    public static LocalTime parseTime(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
            return null;
        }
        return LocalTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static void main(String[] args) {
        String dateStr = "2020-02-29";
        String dateStrAtZero = "2020-02-29 00:00:00";
        String dateTimeStr = "2020-02-29 01:10:59";
        LocalDate localDate = LocalDate.of(2020, 2, 29);
        LocalDateTime localDateTime = LocalDateTime.of(2020, 2, 29, 1, 10, 59);
        LocalDateTime localDateAtZero = LocalDateTime.of(2020, 2, 29, 0, 0, 0);

        isEquals(toDateTime(toOldDate(localDateTime)), localDateTime);
        isEquals(toDateTime(localDate), localDateAtZero);
        isEquals(toDate(toOldDate(localDate)), localDate);
        isEquals(toDate(localDateTime), localDate);

        isEquals(formatDate(localDate, YYYY_MM_DD), dateStr);
        isEquals(parseDate(dateStr, YYYY_MM_DD), localDate);

        isEquals(formatDateTime(localDateTime, YYYY_MM_DD_HH_MM_SS), dateTimeStr);
        isEquals(parseDateTime(dateTimeStr, YYYY_MM_DD_HH_MM_SS), localDateTime);

        System.out.println(localDate.atStartOfDay(GLOBAL_ZONE_ID));
        System.out.println(localDateTime.atZone(GLOBAL_ZONE_ID));

        System.out.println("\n");
        System.out.println(new Date());
        System.out.println(new Date().toInstant().toEpochMilli());
        // 当前时间戳 格林威治时间1970年01月01日起至现在的总秒数 与时区无关
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.ofEpochMilli(System.currentTimeMillis()));
        System.out.println(Instant.now());
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(Instant.now().getEpochSecond());
        System.out.println(ZoneId.systemDefault());
        System.out.println(LocalDateTime.now());
        System.out.println(nowDateTime());
        System.out.println(LocalDateTime.now().atZone(GLOBAL_ZONE_ID));
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("Z")));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")));
    }

    private static void isEquals(Object expect, Object actual) {
        System.out.println(Objects.equals(expect, actual));
    }
}