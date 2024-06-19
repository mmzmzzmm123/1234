package com.renxin.psychology.request;

import lombok.Data;

import java.util.List;

@Data
public class PsyRefConsultServeReq {

    private Long consultId;

    private List<Long> ids;

}
