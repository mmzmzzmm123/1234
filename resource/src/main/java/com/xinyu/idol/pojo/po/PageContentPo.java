package com.xinyu.idol.pojo.po;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class PageContentPo implements Serializable {

    private String classification1;

    private String classification2;

    private String classification3;

    private String classification4;

    private String idOrName;

    private Integer page;

    private Integer size;
}
