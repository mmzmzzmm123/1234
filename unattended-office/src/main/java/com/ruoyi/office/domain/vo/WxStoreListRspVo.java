package com.ruoyi.office.domain.vo;

import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.TStorePackage;
import lombok.Data;

import java.util.List;

@Data
public class WxStoreListRspVo extends TStore {
    private String meters;
    private String balance;
    private List<TStorePackage> storePackageList;
}
