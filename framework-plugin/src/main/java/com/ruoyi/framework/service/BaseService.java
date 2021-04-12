package com.ruoyi.framework.service;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService<T> implements IService<T> {
    @Autowired
    protected T baseDAO;

    @Override
    public T getBaseDAO() {
        return baseDAO;
    }
}
