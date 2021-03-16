package com.gox.basic.domain.vo;

import com.gox.common.core.domain.BaseEntity;

public class TemplatesCopyVo extends BaseEntity {
    private Long scid;
    private Long sdid;
    private Long [] dcid;
    private Long [] ddid;

    public Long getScid() {
        return scid;
    }

    public void setScid(Long scid) {
        this.scid = scid;
    }

    public Long getSdid() {
        return sdid;
    }

    public void setSdid(Long sdid) {
        this.sdid = sdid;
    }

    public Long[] getDcid() {
        return dcid;
    }

    public void setDcid(Long[] dcid) {
        this.dcid = dcid;
    }

    public Long[] getDdid() {
        return ddid;
    }

    public void setDdid(Long[] ddid) {
        this.ddid = ddid;
    }
}
