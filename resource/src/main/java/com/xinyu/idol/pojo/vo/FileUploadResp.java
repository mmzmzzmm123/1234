package com.xinyu.idol.pojo.vo;

import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class FileUploadResp implements Serializable {

    private String ossKey;

    private String fileSize;
}
