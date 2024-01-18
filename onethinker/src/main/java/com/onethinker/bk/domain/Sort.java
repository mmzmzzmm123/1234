package com.onethinker.bk.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onethinker.bk.enums.SortTypeEnum;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分类对象 bk_sort
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Data
@TableName("bk_sort")
public class Sort extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String sortName;

    /**
     * 分类描述
     */
    @Excel(name = "分类描述")
    private String sortDescription;

    /**
     * 分类类型[0:导航栏分类，1:普通分类]
     */
    @Excel(name = "分类类型[0:导航栏分类，1:普通分类]")
    private Integer sortType;

    /**
     * 导航栏分类优先级：数字小的在前面
     */
    @Excel(name = "导航栏分类优先级：数字小的在前面")
    private Long priority;

    @TableField(exist = false)
    private Integer countOfSort;

    @TableField(exist = false)
    private List<Label> labels;

    public void existsParams() {
        if (!StringUtils.hasText(sortName) || !StringUtils.hasText(sortDescription)) {
            throw new RuntimeException("分类名称和分类描述不能为空！");
        }

        if (sortType != null && sortType == SortTypeEnum.SORT_TYPE_BAR.getCode() && priority == null) {
            throw new RuntimeException("导航栏分类必须配置优先级！");
        }
    }

}
