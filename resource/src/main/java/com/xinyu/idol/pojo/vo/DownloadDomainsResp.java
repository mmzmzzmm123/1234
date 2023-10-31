package com.xinyu.idol.pojo.vo;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class DownloadDomainsResp implements Serializable {
   private String resource;
}
