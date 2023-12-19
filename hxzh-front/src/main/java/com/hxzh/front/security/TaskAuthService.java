package com.hxzh.front.security;


import com.hxzh.front.common.exception.task.TaskAuthCodeNotMatchException;
import com.hxzh.front.common.exception.task.TaskNotExistsException;
import com.hxzh.front.moudle.domain.Task;
import com.hxzh.front.moudle.dto.TaskAuthDTO;
import com.hxzh.front.security.tokenservice.impl.TaskTokenService;
import com.hxzh.front.service.TaskService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskAuthService {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskTokenService taskTokenService;

    public String auth(TaskAuthDTO taskAuthDTO) {
        //前置校验
        authPreCheck(taskAuthDTO);

        //任务验证

        Task task = taskService.getTaskByTaskKeyAndTasksetId(taskAuthDTO.getTaskKey(), taskAuthDTO.getTasksetId());
        //任务不存在
        if(ObjectUtils.isEmpty(task)) {
            throw new TaskNotExistsException();
        }
        //任务授权码不匹配
        if(!task.getTaskAuthCode().equals(taskAuthDTO.getTaskAuthCode())) {
            throw new TaskAuthCodeNotMatchException();
        }else {
            String taskId= String.valueOf(task.getTaskId());
            String token = getToken(taskId);
            return token;
        }
    }

    public void authPreCheck(TaskAuthDTO taskAuthDTO) {
        // 任务集或者任务ID为空
        if (StringUtils.isEmpty(taskAuthDTO.getTasksetId()) || StringUtils.isEmpty(taskAuthDTO.getTaskKey())) {
            throw new TaskNotExistsException();
        }
        // TODO 检验设备是否可用
    }
    private String getToken(String taskId){
        Claims claims = Jwts.claims();
        claims.setId(taskId);
        String token = taskTokenService.createToken(claims);
        return token;
    }
}
