package com.ruoyi.headmaster.domain;

import com.ruoyi.common.annotation.Excel;

public class HeadmasterVo {
    private Long id;

    /** 班主任id */

    private Long headmasterId;

    /** 班主任姓名 */

    private String headmasterName;

    /** 性别 */

    private String sex;

    /** 年龄 */

    private Integer age;

    /** 电话 */

    private String phone;

    /** 班级id */

    private Long clazzId;

    /** 年级id */

    private Long gradeId;

    private String clazzName;
    private String gradeName;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setHeadmasterId(Long headmasterId)
    {
        this.headmasterId = headmasterId;
    }

    public Long getHeadmasterId()
    {
        return headmasterId;
    }
    public void setHeadmasterName(String headmasterName)
    {
        this.headmasterName = headmasterName;
    }

    public String getHeadmasterName()
    {
        return headmasterName;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setClazzId(Long clazzId)
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId()
    {
        return clazzId;
    }
    public void setGradeId(Long gradeId)
    {
        this.gradeId = gradeId;
    }

    public Long getGradeId()
    {
        return gradeId;
    }
}
