package com.stdiet.custom.domain;

import java.math.BigDecimal;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 工资比例配置对象 sys_salary_compose_config
 *
 * @author xiezhijun
 * @date 2021-03-13
 */
@Data
public class SysSalaryComposeConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    //继续教育补贴界限，超过该值才有继续教育补贴
    public static final Double educationAllowanceLimit = 15000.00;

    //工资低于该值时，基本薪资+岗位津贴+绩效等于{baseSalarySum}
    public static final Double baseSalarySumLimit = 6000.00;

    //工资低于{baseSalarySumLimit}时，基本薪资+岗位津贴+绩效之和
    public static final Double lowerBaseSalarySum = 3000.00;

    //工资高于{baseSalarySumLimit}时，基本薪资+岗位津贴+绩效之和
    public static final Double higherBaseSalarySum = 5000.00 * 0.99;

    /** $column.columnComment */
    private Long id;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String salaryPostName;

    /** 基本薪资 */
    @Excel(name = "基本薪资")
    private BigDecimal baseSalary;

    /** 岗位津贴比例(非全额比例)，百分制，与绩效比例加起来为100% */
    @Excel(name = "岗位津贴比例(非全额比例)，百分制，与绩效比例加起来为100%")
    private Integer postAllowanceRate;

    /** 加班补贴占全额比例，百分制 */
    @Excel(name = "加班补贴占全额比例，百分制")
    private Integer overtimeAllowanceRate;

    /** 绩效补贴比例(非全额比例)，百分制，与岗位津贴加起来100% */
    @Excel(name = "绩效补贴比例(非全额比例)，百分制，与岗位津贴加起来100%")
    private Integer meritsAllowanceRate;

    /** 车补贴占全额比例，百分制 */
    @Excel(name = "车补贴占全额比例，百分制")
    private Integer carAllowanceRate;

    /** 餐补占全额比例，百分制 */
    @Excel(name = "餐补占全额比例，百分制")
    private Integer mealAllowanceRate;

    /** 全勤奖 */
    @Excel(name = "全勤奖")
    private BigDecimal fullAttendanceSalary;

    /** 房补贴占全额比例，百分制 */
    @Excel(name = "房补贴占全额比例，百分制")
    private Integer houseAllowanceRate;

    /** 差旅补贴占全额比例，百分制 */
    @Excel(name = "差旅补贴占全额比例，百分制")
    private Integer travelAllowanceRate;

    /** 技能补贴占全额比例，百分制 */
    @Excel(name = "技能补贴占全额比例，百分制")
    private Integer skillAllowanceRate;

    /** 培训补贴占全额比例，百分制 */
    @Excel(name = "培训补贴占全额比例，百分制")
    private Integer trainAllowanceRate;

    /** 部门补贴占全额比例，百分制 */
    @Excel(name = "部门补贴占全额比例，百分制")
    private Integer departmentAllowanceRate;

    /** 继续教育补贴占全额比例，百分制 */
    @Excel(name = "继续教育补贴占全额比例，百分制")
    private Integer educationAllowanceRate;

    /** 删除标识 0未删除 1已删除 */
    private Integer delFlag;

    /**
     * 获取固定比例之和，不能超过100%
     * @return
     */
    public int getBaseRateSum(){
          return overtimeAllowanceRate + carAllowanceRate + mealAllowanceRate + houseAllowanceRate + travelAllowanceRate + skillAllowanceRate + trainAllowanceRate
                  + departmentAllowanceRate + educationAllowanceRate;
    }
}