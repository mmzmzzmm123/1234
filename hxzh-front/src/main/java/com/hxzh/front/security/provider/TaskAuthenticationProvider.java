package com.hxzh.front.security.provider;

import com.hxzh.front.moudle.dto.TaskAuthDTO;
import com.hxzh.front.security.authentication.TaskAuthCodeAuthentiction;
import com.hxzh.front.security.detail.TaskDetails;
import com.hxzh.front.security.detailservice.TaskDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class TaskAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TaskDetailsServiceImpl taskDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(TaskAuthCodeAuthentiction.class, authentication, "Only TaskAuthCodeAuthentiction is supported");
        //    获取TaskDetails
        TaskAuthCodeAuthentiction taskAuthCodeAuthentiction = (TaskAuthCodeAuthentiction) authentication;
        Object principal = taskAuthCodeAuthentiction.getPrincipal();
        Assert.isInstanceOf(TaskAuthDTO.class, principal, "Only TaskAuthDTO is supported");
        TaskAuthDTO taskAuthDTO = (TaskAuthDTO) principal;
        TaskDetails taskDetails = null;
        taskDetails = taskDetailsService.loadTaskByTaskBody(taskAuthDTO);
        //验证密码
        if (ObjectUtils.isEmpty(taskDetails)) {
            log.info("taskDetails is not null");
            throw new AuthenticationServiceException("taskDetails is not null");
        }
        String pw = taskDetails.getPassword();
        String authCode = taskAuthDTO.getTaskAuthCode().trim();
        if(!pw.equals(authCode)){
            log.info("taskAuthCode is not correct");
            throw new AuthenticationServiceException("taskAuthCode is not correct");
        }
        return new TaskAuthCodeAuthentiction(taskDetails.getTaskId(), null, taskDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TaskAuthCodeAuthentiction.class.isAssignableFrom(authentication);
    }
}
