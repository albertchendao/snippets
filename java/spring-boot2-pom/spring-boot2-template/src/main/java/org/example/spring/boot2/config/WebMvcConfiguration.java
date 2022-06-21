package org.example.spring.boot2.config;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Spring MVC 配置
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/13 10:06
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String API_URL_PREFIX = "/api-v2";

    private static final String TEST_URL_PREFIX = "/test";

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String dateTimeFormat;
    @Value("${spring.jackson.time-format:HH:mm:ss}")
    private String timeFormat;
    @Value("${spring.jackson.day-format:yyyy-MM-dd}")
    private String dateFormat;

    /**
     * 会自动给路径加上前缀
     *
     * @author Albert
     * @version 1.0
     * @since 2021/12/29 21:19
     */
    @Documented
    @Controller
    @ResponseBody
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface V2RestController {
        /**
         * Alias for {@link Controller#value}.
         */
        @AliasFor(annotation = Controller.class)
        String value() default "";
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(API_URL_PREFIX, c -> c.isAnnotationPresent(V2RestController.class));
    }

    /**
     * 解决请求体和返回体中的时间格式化
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilder() {
        return builder -> {
            // 解决 Date 时间格式化
            builder.deserializerByType(Date.class, new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance, new SimpleDateFormat(dateTimeFormat), dateTimeFormat));
            builder.serializerByType(Date.class, new DateSerializer(false, new SimpleDateFormat(dateTimeFormat)));
            // 解决 LocalTime 时间格式化
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)));
            // 解决 LocalDate 时间格式化
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            // 解决 LocalDateTime 时间格式化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }

    /**
     * 解决非请求体和返回体中的时间格式化
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(dateFormat));
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(dateTimeFormat));
        dateTimeRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern(timeFormat));
        dateTimeRegistrar.registerFormatters(registry);

        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
        dateRegistrar.setFormatter(new DateFormatter(dateTimeFormat));
        dateRegistrar.registerFormatters(registry);
    }
}