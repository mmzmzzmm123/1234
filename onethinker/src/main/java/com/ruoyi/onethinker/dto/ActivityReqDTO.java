package com.ruoyi.onethinker.dto;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.enums.ActivityTypeEnum;
import com.ruoyi.common.enums.SysConfigKeyEnum;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/11/3 0003 14:09
 * 活动DTO
 */
@Data
public class ActivityReqDTO {

    /** 权重 */
    private Long weight;

    /** 活动开始时间 */
    private Long startTime;

    /** 活动结束时间 */
    private Long endTime;

    /** 活动标题 */
    private String title;

    /** 活动背景图 */
    private String bgUrl;

    /** 批次号 */
    private String batchNo;

    /** 活动类型id */
    private Integer activityType;

    /** 活动明细-红包 **/
    private RedEnvelopeCtrlDTO redEnvelopeCtrlDTO;

    /** 创建活动用户id **/
    private Long sysUserId;

    public void existsParams() {
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

        ActivityTypeEnum.existsActivityTypeEnumByCode(activityType);

        if (ActivityTypeEnum.RED_ENVELOPE.getCode().equals(activityType)) {
            // 红包相关校验
            Assert.isTrue(!ObjectUtils.isEmpty(redEnvelopeCtrlDTO),"生成红包数据不能为空");
            redEnvelopeCtrlDTO.setCreateUserId(sysUserId);
            redEnvelopeCtrlDTO.setBatchNo(batchNo);
            redEnvelopeCtrlDTO.existsReqParams();
        }
    }
}
