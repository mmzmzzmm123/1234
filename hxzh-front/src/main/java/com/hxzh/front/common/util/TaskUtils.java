package com.hxzh.front.common.util;

import com.hxzh.front.common.exception.ServiceException;
import com.hxzh.front.security.authentication.TaskAuthCodeAuthentiction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 任务工具类
 *
 *
 * @author JasonYou
 * @date 2024/01/11
 */
public class TaskUtils {

    /**
     * 获取任务id
     *
     * @return {@link Long}
     */
    public static Long getTaskId(){
        return Long.valueOf(getAuthentication().getPrincipal().toString());
    }



    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null||! (authentication instanceof TaskAuthCodeAuthentiction))
        {
            throw new ServiceException("未认证通过或token失效");
        }
        return (TaskAuthCodeAuthentiction)authentication;
    }
}
