package com.stdiet.custom.domain;

import com.alibaba.fastjson.JSONArray;
import com.stdiet.common.annotation.Excel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 客户数据对象 sys_customer_physical_signs
 *
 * @author xzj
 * @date 2020-12-31
 */
@Data
public class SysCustomerPhysicalSigns {

    /**
     * 体征ID
     */
    private Long id;

    /**
     * 客户id
     */
    //@Excel(name = "客户id")
    private Long customerId;

    /**
     * 客户姓名,非持久化字段
     */
    @Excel(name = "客户姓名")
    private String name;

    /**
     * 客户手机号,非持久化字段
     */
    @Excel(name = "客户手机号")
    private String phone;

    /**
     * 客户性别  0男 1女
     */
    @Excel(name = "客户性别  0男 1女")
    private Integer sex;

    /**
     * 客户年龄（岁）
     */
    @Excel(name = "客户年龄", readConverterExp = "岁=")
    private Integer age;

    /**
     * 客户身高（厘米）
     */
    @Excel(name = "客户身高", readConverterExp = "厘=米")
    private Integer tall;

    /**
     * 客户体重（斤）
     */
    @Excel(name = "客户体重", readConverterExp = "斤=")
    private Integer weight;

    /**
     * 客户病史体征id
     */
    @Excel(name = "客户病史体征id")
    private String physicalSignsId;

    /**
     * 客户忌口不爱吃食材id
     */
    @Excel(name = "客户忌口不爱吃食材id")
    private String dishesIngredientId;

    /**
     * 客户照片
     */
    @Excel(name = "客户照片")
    private String photo;

    /**
     * 是否便秘  0是 1否
     */
    @Excel(name = "是否便秘  0是 1否")
    private Integer constipation;

    /**
     * 是否熬夜、失眠 0是 1否
     */
    @Excel(name = "是否熬夜、失眠 0是 1否")
    private Integer staylate;

    /**
     * 是否经常运动 0是 1否
     */
    @Excel(name = "是否经常运动 0是 1否")
    private Integer motion;

    /**
     * 饮食方式  0自己做 1外面吃
     */
    @Excel(name = "饮食方式  0自己做 1外面吃")
    private Integer makeFoodType;

    /**
     * 饮食特点 0清淡 1重口味
     */
    @Excel(name = "饮食特点 0清淡 1重口味")
    private Integer makeFoodTaste;

    /**
     * 平时是否久坐  0久坐多 1走动多
     */
    @Excel(name = "平时是否久坐  0久坐多 1走动多")
    private Integer walk;

    /**
     * 减脂过程遇到的困难
     */
    @Excel(name = "减脂过程遇到的困难")
    private String difficulty;

    /**
     * 是否浑身乏力 0是 1否
     */
    @Excel(name = "是否浑身乏力 0是 1否")
    private Integer weakness;

    /**
     * 是否减脂反弹 0是 1否
     */
    @Excel(name = "是否减脂反弹 0是 1否")
    private Integer rebound;

    /**
     * 能否认识到生活习惯的改善才是减脂的关键 0是 1否
     */
    @Excel(name = "能否认识到生活习惯的改善才是减脂的关键 0是 1否")
    private Integer crux;

    /**
     * 南方人还是北方人 0南方 1北方
     */
    @Excel(name = "南方人还是北方人 0南方 1北方")
    private Integer position;

    /**
     * 睡觉时间（24小时制）
     */
    @Excel(name = "睡觉时间", readConverterExp = "2=4小时制")
    private Integer sleepTime;

    /**
     * 起床时间（24小时制）
     */
    @Excel(name = "起床时间", readConverterExp = "2=4小时制")
    private Integer getupTime;

    /**
     * 联系沟通时间（24小时制）
     */
    @Excel(name = "联系沟通时间", readConverterExp = "2=4小时制")
    private Integer connectTime;

    /**
     * 饮食备注
     */
    @Excel(name = "饮食备注")
    private String remarks;

    /**
     * 营养师备注
     */
    private String remark;

    /**
     * 湿气数据
     */
    @Excel(name = "湿气数据")
    private String bloodData;

    /**
     * 气血数据
     */
    @Excel(name = "气血数据")
    private String moistureDate;

    /**
     * 工作职业
     */
    @Excel(name = "工作职业")
    private String vocation;

    /**
     * 是否上夜班
     */
    @Excel(name = "是否上夜班")
    private Integer night;

    /**
     * 减脂经历
     */
    @Excel(name = "减脂经历")
    private String experience;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String comments;

    /**
     * 删除标识(0 未删除 1已删除)
     */
    private Integer delFlag;

    /**
     * 体征对象集合
     **/
    private List<SysPhysicalSigns> signList;

    private JSONArray avoidFood;

    private Date createTime;

    private Date updateTime;

    private String createBy;

    private String updateBy;


}