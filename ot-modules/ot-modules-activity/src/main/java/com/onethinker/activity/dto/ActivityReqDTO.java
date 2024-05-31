package com.onethinker.activity.dto;

import com.onethinker.common.enums.ActivityTypeEnum;
import com.onethinker.common.utils.SecurityUtils;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:09
 * 活动DTO
 */
@Data
public class ActivityReqDTO {

    /**
     * id
     **/
    private Long id;

    /**
     * 权重
     */
    private Long weight;

    /**
     * 活动开始时间
     */
    private Long startTime;

    /**
     * 活动结束时间
     */
    private Long endTime;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动背景图
     */
    private String bgUrl;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 活动类型id
     */
    private ActivityTypeEnum activityType;

    /**
     * 活动明细-红包
     **/
    private RedEnvelopeCtrlDTO redEnvelopeCtrlDTO;

    /**
     * 创建活动用户id
     **/
    private Long sysUserId;

    /**
     * 活动状态
     **/
    private Integer enabled;

    /**
     * 备注
     **/
    private String remark;

    public void existsParams() {
        Assert.notNull(activityType,"活动类型有误");
        if (ObjectUtils.isEmpty(startTime) || ObjectUtils.isEmpty(endTime)) {
            throw new RuntimeException("活动时间不能为空");
        }
        if (startTime > endTime) {
            throw new RuntimeException("活动设置有误，开始时间不能大于结束时间");
        }
        if (ObjectUtils.isEmpty(title) || ObjectUtils.isEmpty(activityType)) {
            throw new RuntimeException("标题或活动类型不能为空");
        }
        if (ObjectUtils.isEmpty(batchNo)) {
            batchNo = System.currentTimeMillis() + "";
        }
        sysUserId = SecurityUtils.getUserId();


        if (ActivityTypeEnum.RED_ENVELOPE.equals(activityType)) {
            // 红包相关校验
            Assert.isTrue(!ObjectUtils.isEmpty(redEnvelopeCtrlDTO), "生成红包数据不能为空");
            redEnvelopeCtrlDTO.setCreateUserId(sysUserId);
            redEnvelopeCtrlDTO.setBatchNo(batchNo);
            redEnvelopeCtrlDTO.existsReqParams();
        }
    }
}
