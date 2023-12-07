package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName taskset
 */
@TableName(value ="taskset")
@Data
public class Taskset implements Serializable {
    /**
     * 任务集合ID
     */
    @TableId
    private Integer tasksetId;

    /**
     * 任务集合名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 配置文件
     */
    private String configfile;

    /**
     * 子集(数组)
     */
    private String childsetId;

    /**
     * 父子集id
     */
    private Integer parentsetId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Taskset other = (Taskset) that;
        return (this.getTasksetId() == null ? other.getTasksetId() == null : this.getTasksetId().equals(other.getTasksetId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getDeptId() == null ? other.getDeptId() == null : this.getDeptId().equals(other.getDeptId()))
            && (this.getConfigfile() == null ? other.getConfigfile() == null : this.getConfigfile().equals(other.getConfigfile()))
            && (this.getChildsetId() == null ? other.getChildsetId() == null : this.getChildsetId().equals(other.getChildsetId()))
            && (this.getParentsetId() == null ? other.getParentsetId() == null : this.getParentsetId().equals(other.getParentsetId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTasksetId() == null) ? 0 : getTasksetId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getDeptId() == null) ? 0 : getDeptId().hashCode());
        result = prime * result + ((getConfigfile() == null) ? 0 : getConfigfile().hashCode());
        result = prime * result + ((getChildsetId() == null) ? 0 : getChildsetId().hashCode());
        result = prime * result + ((getParentsetId() == null) ? 0 : getParentsetId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tasksetId=").append(tasksetId);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", deptId=").append(deptId);
        sb.append(", configfile=").append(configfile);
        sb.append(", childsetId=").append(childsetId);
        sb.append(", parentsetId=").append(parentsetId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}