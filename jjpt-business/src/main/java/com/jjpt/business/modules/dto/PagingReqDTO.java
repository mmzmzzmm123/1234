package com.jjpt.business.modules.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 分页查询类
 * @param <T>
 * @author bool
 */

@Data
public class PagingReqDTO<T> {



    private Integer current;


    private Integer size;


    private T params;

    private String orderBy;

    @JsonIgnore
    private String userId;

    /**
     * 转换成MyBatis的简单分页对象
     * @return
     */
    public Page toPage(){
        Page page = new Page();
        page.setCurrent(this.current);
        page.setSize(this.size);
        return page;
    }


}
