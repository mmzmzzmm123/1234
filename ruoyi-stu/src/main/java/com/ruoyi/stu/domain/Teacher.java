package com.ruoyi.stu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private Integer teaId;
    private String teaName;
    private String teaSex;
    private String teaPhone;
    private String teaAddress;
    private String teaType;
}
