package com.xinyu.idol.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ClassificationsResp implements Serializable {

    private Long id;

    private String name;

    private Integer classificationLevel;
}
