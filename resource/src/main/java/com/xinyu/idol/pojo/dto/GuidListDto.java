package com.xinyu.idol.pojo.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class GuidListDto implements Serializable {

    private List<String> guidList;
}
