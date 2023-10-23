package com.xinyu.idol.pojo.vo;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ClassificationsReq implements Serializable {

    private String classification1;

    private String classification2;

    private String classification3;

    private String parentId;

}
