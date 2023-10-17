package com.ruoyi.common.core.service;

import com.ruoyi.common.core.domain.IStoreRole;

import java.util.Collection;

public interface IStoreRoleService {
    Collection<IStoreRole> getWxUserStoreRole(Long wxUserId);
}
