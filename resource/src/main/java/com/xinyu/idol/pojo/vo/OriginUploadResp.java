package com.xinyu.idol.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class OriginUploadResp implements Serializable {

    //private Map<String,String> nameOssKeyMap;

    private List<String> ossKeyList;

    private String ossKeyListStr;


}
