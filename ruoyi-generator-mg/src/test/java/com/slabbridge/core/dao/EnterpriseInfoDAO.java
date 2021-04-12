package com.slabbridge.core.dao;

import com.slabbridge.core.entity.EnterpriseInfo;
import com.slabbridge.core.mapper.EnterpriseInfoMapper;
import org.apache.ibatis.annotations.Param;

public interface EnterpriseInfoDAO extends EnterpriseInfoMapper {
    EnterpriseInfo selectByDAO(@Param("id") Long id);
}