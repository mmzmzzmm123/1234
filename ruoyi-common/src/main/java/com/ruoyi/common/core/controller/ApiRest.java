package com.ruoyi.common.core.controller;



import com.ruoyi.common.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据结果返回的封装
 * @author bool 
 * @date 2018/11/20 09:48
 */
@Data
@NoArgsConstructor

public class ApiRest<T>{

    /**
     * 响应消息
     */

    private String msg;
    /**
     * 响应代码
     */

    private Integer code;

    /**
     * 请求或响应body
     */

    protected T data;


    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess(){
        return code.equals(0);
    }

    /**
     * 构造函数
     * @param error
     */
    public ApiRest(ServiceException error){
        this.code = error.getCode();
        this.msg = error.getMessage();
    }

    /**
     * 构造函数
     * @param error
     */
    public ApiRest(ApiError error){
        this.code = error.getCode();
        this.msg = error.msg;
    }
}
