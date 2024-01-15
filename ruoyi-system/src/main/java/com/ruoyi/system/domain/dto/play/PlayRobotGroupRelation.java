package com.ruoyi.system.domain.dto.play;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("水军群关系")
public class PlayRobotGroupRelation {

    @ApiModelProperty("自增主键")
    @TableId
    private String playRobotGroupRelationId;

    @ApiModelProperty("商家Id")
    private String merchantId;

    @ApiModelProperty("是否可以被设置管理员 0否 1是")
    private Integer isAdmin;

    @ApiModelProperty("水军ID")
    private String robotId;

    @ApiModelProperty("群ID")
    private String groupId;

    @ApiModelProperty("入群时间")
    private Date incomeGroupTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("状态：1.在群内 2.已退群 3.等待退群 4.待续费")
    private Integer state;

    @ApiModelProperty("修改时间")
    private Date modifyTime;

    @ApiModelProperty("0正常 1删除")
    private Integer isDelete;
}
