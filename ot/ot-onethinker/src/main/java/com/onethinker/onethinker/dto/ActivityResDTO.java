package com.onethinker.onethinker.dto;

import com.onethinker.common.annotation.Excel;
import com.onethinker.file.dto.FileInfoDTO;
import lombok.Data;

import java.util.List;

/**
 * @Title: ActivityResDTO
 * @Author itmei
 * @Package com.onethinker.onethinker.dto
 * @Date 2023/11/7 21:54
 * @description: 活动类返回数据
 */
@Data
public class ActivityResDTO {

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Integer enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 活动开始时间
     */
    @Excel(name = "活动开始时间")
    private Long startTime;

    /**
     * 活动结束时间
     */
    @Excel(name = "活动结束时间")
    private Long endTime;

    /**
     * 活动标题
     */
    @Excel(name = "活动标题")
    private String title;

    /**
     * 活动背景图
     */
    @Excel(name = "活动背景图")
    private String bgUrl;

    /**
     * 批次号
     */
    @Excel(name = "批次号")
    private String batchNo;

    /**
     * 活动类型id
     */
    @Excel(name = "活动类型id")
    private Integer activityType;

    private RedEnvelopeCtrlDTO redEnvelopeCtrlDTO;

    /**
     * 上传图片信息
     **/
    private FileInfoDTO bgUrlInfo;

    /**
     * 活动轮播图信息
     **/
    private List<FileInfoDTO> bannerInfo;
}
