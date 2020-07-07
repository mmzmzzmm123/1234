package com.ruoyi.project.data.cases.domain;

import org.springframework.util.DigestUtils;

/**
 * 租赁挂牌案例
 */
public class OriginalResidencePlatformRentOpeningCase extends OriginalResidenceRentOpeningCase  {

    @Override
    public String getNewCaseId() {
        return DigestUtils.md5DigestAsHex(getCaseUrl().getBytes());
    }

}
