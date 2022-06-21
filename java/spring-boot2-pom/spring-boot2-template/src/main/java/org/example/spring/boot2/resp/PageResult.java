package org.example.spring.boot2.resp;

import lombok.Data;

import java.util.List;

/**
 * 分页结果
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/30 18:36
 */
@Data
public class PageResult<T> {
    /**
     * 当前野马
     */
    private Integer pageNo;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 总记录条数
     */
    private Integer totalCount;
    /**
     * 分页记录结果
     */
    private List<T> data;
}