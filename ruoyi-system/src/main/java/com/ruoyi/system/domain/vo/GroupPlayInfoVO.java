package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/24/024 16:02
 * @Description :
 */
@Data
@ApiModel("群剧本信息VO")
public class GroupPlayInfoVO {
    private String playId;
    private String groupId;
    private String originalGroupId;


    private String groupSerialNo;
    private String groupName;
}
