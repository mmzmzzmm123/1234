package com.hxzh.front.security.detailservice;

import com.hxzh.front.common.exception.task.TaskNotExistsException;
import com.hxzh.front.moudle.domain.Task;
import com.hxzh.front.security.detail.TaskDetails;
import com.hxzh.front.moudle.dto.TaskAuthDTO;
import com.hxzh.front.service.TaskService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TaskDetailsServiceImpl  {
    @Autowired
    TaskService taskService;
    String role="ROLE_TASK";
    public TaskDetails loadTaskByTaskBody(TaskAuthDTO taskAuthDTO) {
        TaskDetails taskDetails = new TaskDetails();
        String taskKey = taskAuthDTO.getTaskKey();
        String tasksetId = taskAuthDTO.getTasksetId();
        //获取task
        Task task = taskService.getTaskByTaskKeyAndTasksetId(taskKey, tasksetId);
        if (ObjectUtils.isNotEmpty(task)) {
            taskDetails.setTask(task);
            taskDetails.setTaskId(task.getTaskId());
            taskDetails.setPermissions(getPermissions());
            return taskDetails;
        }else{
            throw new TaskNotExistsException();
        }
    }
    private Set<? extends GrantedAuthority> getPermissions(){
        Set<GrantedAuthority> permissions = new HashSet<>();
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(this.role);
        permissions.add(role);
        return permissions;
    }
}
