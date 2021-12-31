package com.example.spring.boot2.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Albert
 * @version 1.0
 * @since 2021/12/31 19:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResp {
    private String fullName;
    private String name;
}