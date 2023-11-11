package com.xinyu.idol.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class InnerClassificationDto implements Serializable {

    private List<ClassificationsEntity> classificationsEntityList;
}
