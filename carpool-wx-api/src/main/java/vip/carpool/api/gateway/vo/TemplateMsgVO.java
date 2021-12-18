package vip.carpool.api.gateway.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 刘荣华
 */
@Data
@ApiModel(value = "推送模版消息入参")
public class TemplateMsgVO {

    @NotBlank(message = "必传")
    @ApiModelProperty(value = "openId")
    private String openId;

    @NotBlank(message = "模板id必传")
    @ApiModelProperty(value = "模板id")
    private String templateId;

    @ApiModelProperty(value = "需要跳转的url")
    private String url;

    @NotBlank(message = "消息体必传")
    @ApiModelProperty(value = "消息体")
    private String msgs;
}
