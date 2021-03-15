package com.stdiet.custom.domain;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysSalaryRate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数初始化，全部设为0
     */
    public SysSalaryRate(){
        this.baseSalary = BigDecimal.valueOf(0.0);
        this.postAllowance = BigDecimal.valueOf(0.0);
        this.overtimeAllowance = BigDecimal.valueOf(0.0);
        this.meritsAllowance = BigDecimal.valueOf(0.0);
        this.carAllowance = BigDecimal.valueOf(0.0);
        this.mealAllowance = BigDecimal.valueOf(0.0);
        this.fullAttendanceSalary = BigDecimal.valueOf(0.0);
        this.houseAllowance = BigDecimal.valueOf(0.0);
        this.travelAllowance = BigDecimal.valueOf(0.0);
        this.skillAllowance = BigDecimal.valueOf(0.0);
        this.trainAllowance = BigDecimal.valueOf(0.0);
        this.departmentAllowance = BigDecimal.valueOf(0.0);
        this.educationAllowance = BigDecimal.valueOf(0.0);
    }

    /** $column.columnComment */
    private Long id;

    /** 员工姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 岗位名称ID，对应工资岗位字典表的键值 */
    private String salaryPostId;

    @Excel(name = "岗位")
    private String salaryPostName;

    /** 基本薪资 */
    @Excel(name = "基本薪资")
    private BigDecimal baseSalary;

    /** 岗位津贴 */
    @Excel(name = "岗位津贴")
    private BigDecimal postAllowance;

    /** 加班补贴 */
    @Excel(name = "加班补贴")
    private BigDecimal overtimeAllowance;

    @Excel(name = "绩效")
    private BigDecimal meritsAllowance;

    @Excel(name = "车补")
    private BigDecimal carAllowance;

    /** 餐补*/
    @Excel(name = "餐补")
    private BigDecimal mealAllowance;

    /** 全勤奖 */
    @Excel(name = "全勤奖")
    private BigDecimal fullAttendanceSalary;

    /** 房补 */
    @Excel(name = "房补")
    private BigDecimal houseAllowance;

    /** 差旅费 */
    @Excel(name = "差旅费")
    private BigDecimal travelAllowance;

    /** 技能补贴 */
    @Excel(name = "技能补贴")
    private BigDecimal skillAllowance;

    /** 培训补贴 */
    @Excel(name = "培训补贴")
    private BigDecimal trainAllowance;

    /** 部门补贴 */
    @Excel(name = "部门补贴")
    private BigDecimal departmentAllowance;

    /** 继续教育补贴*/
    @Excel(name = "继续教育补贴")
    private BigDecimal educationAllowance;

    /**
     * 获取固定比例的薪资总和
     * @return
     */
    public BigDecimal getTotalRateSalary(){
        return overtimeAllowance.add(carAllowance).add(mealAllowance).add(houseAllowance).add(travelAllowance)
                .add(skillAllowance).add(trainAllowance).add(departmentAllowance).add(educationAllowance);
    }
}
