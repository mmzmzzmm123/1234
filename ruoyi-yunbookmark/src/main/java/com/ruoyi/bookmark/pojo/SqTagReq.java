package com.ruoyi.bookmark.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 书签_标签对象 sq_tag
 *
 * @author ruoyi
 * @date 2020-09-05
 */
@Data
public class SqTagReq
{
    /** 书签标签id */
    private Long id;

    /** 标签名字 */
    private String name;

    /** 链接 */
    private String url;

    /** 状态  0显示默认*/
    private Integer status;


    /** 标签的字体颜色 */
    private String isFontColor;

    /** 标签的背景颜色 */
    private String isBgColor;



}
