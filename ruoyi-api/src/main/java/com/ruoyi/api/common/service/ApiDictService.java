package com.ruoyi.api.common.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.mapper.SysDictDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LAM
 * @date 2023/10/13 12:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiDictService {

    private final SysDictDataMapper sysDictDataMapper;

    /**
     * 查询是否隐藏隐私项
     *
     * @return 结果
     * */
    public String getIfHidePrivacyItem() {
        String result = "0";
        List<SysDictData> sysDictDataList = sysDictDataMapper.selectDictDataByType("if_hide_privacy_item");
        if (ObjectUtil.isNotEmpty(sysDictDataList)){
            result = sysDictDataList.get(0).getDictValue();
        }
        return result;
    }
}
