package com.ruoyi.stu.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 班级年级参数对象 stu_cls
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public class StuCls extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** 班级 */
    @Excel(name = "班级")
    private String cls;

    /** 年级 */
    @Excel(name = "年级")
    private String clsYear;
    /**
     * 班级人数
     */
    private int stuNum;
    /*
    已完成提交材料数量
     */
    private int mNum;

    @Override
    public String toString() {
        return "StuCls{" +
                "id=" + id +
                ", cls='" + cls + '\'' +
                ", clsYear='" + clsYear + '\'' +
                ", stuNum=" + stuNum +
                ", mNum=" + mNum +
                '}';
    }

    public StuCls(Long id, String cls, String clsYear, int stuNum, int mNum) {
        this.id = id;
        this.cls = cls;
        this.clsYear = clsYear;
        this.stuNum = stuNum;
        this.mNum = mNum;
    }

    public int getmNum() {
        return mNum;
    }

    public void setmNum(int mNum) {
        this.mNum = mNum;
    }

    public  StuCls(){

    }

    public StuCls(Long id, String cls, String clsYear, int stuNum) {
        this.id = id;
        this.cls = cls;
        this.clsYear = clsYear;
        this.stuNum = stuNum;
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCls(String cls)
    {
        this.cls = cls;
    }

    public String getCls()
    {
        return cls;
    }
    public void setClsYear(String clsYear)
    {
        this.clsYear = clsYear;
    }

    public String getClsYear()
    {
        return clsYear;
    }

}
