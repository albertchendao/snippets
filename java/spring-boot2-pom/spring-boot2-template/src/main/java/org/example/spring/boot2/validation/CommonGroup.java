package org.example.spring.boot2.validation;

import javax.validation.groups.Default;

/**
 * 参数校验公共分组, 业务模块自定义分组请写在自己模块下
 *
 * @author Albert
 * @version 1.0
 * @since 2021/12/29 10:33
 */
public interface CommonGroup extends Default {

    /**
     * 数据新增分组
     */
    interface Create extends Default {
    }

    /**
     * 数据查询分组
     */
    interface Read extends Default {
    }

    /**
     * 数据更新分组
     */
    interface Update extends Default {
    }

    /**
     * 数据删除分组
     */
    interface Delete extends Default {
    }

}