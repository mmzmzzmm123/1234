package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.TStorePackage;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WxUserStoreInfo extends TStore{
    private BigDecimal amount;
    private List<TStorePackage> packageList;
}
