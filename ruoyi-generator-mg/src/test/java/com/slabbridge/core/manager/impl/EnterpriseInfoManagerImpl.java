package com.slabbridge.core.manager.impl;

import com.slabbridge.core.dao.EnterpriseInfoDAO;
import com.slabbridge.core.manager.EnterpriseInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseInfoManagerImpl implements EnterpriseInfoManager {
    @Autowired
    private EnterpriseInfoDAO dao;
}