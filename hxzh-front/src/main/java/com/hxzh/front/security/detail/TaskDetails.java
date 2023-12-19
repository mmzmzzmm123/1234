package com.hxzh.front.security.detail;

import com.alibaba.fastjson2.annotation.JSONField;

import com.hxzh.front.moudle.domain.Task;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.Set;

@Data
public class TaskDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 权限
     */
    private Set<? extends GrantedAuthority> permissions;

    /**
     * 任务
     */
    private Task task;

    public TaskDetails() {
    }

    public TaskDetails(Set<? extends GrantedAuthority> permissions, Task task) {
        this.permissions = permissions;
        this.task = task;
    }

    public TaskDetails(Long taskId, Set<? extends GrantedAuthority> permissions, Task task) {
        this.taskId = taskId;
        this.permissions = permissions;
        this.task = task;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return task.getTaskAuthCode();
    }

    @Override
    public String getUsername() {
        return task.getTaskKey();
    }
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
