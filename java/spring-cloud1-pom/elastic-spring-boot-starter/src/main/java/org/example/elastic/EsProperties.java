package org.example.elastic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@ConfigurationProperties(prefix = "elastic")
public class EsProperties {
    /**
     * 是否支持
     */
    private Boolean enable;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * IP
     */
    private List<String> ips;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 过期时间
     */
    private Long keepAlive;
}
