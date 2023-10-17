package com.ruoyi.common.core.domain;

import java.util.Collection;

public interface IStoreRole {
    Long getUserId();
    Long getStoreId();
    Collection<String> getRoles();
}
