package com.xinyu.idol.pojo.vo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UpdateContentWebListReq implements Serializable {
    private List<UpdateContentWebReq> updateContentWebReqList;

}
