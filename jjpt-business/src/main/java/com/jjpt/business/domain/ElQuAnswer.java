package com.jjpt.business.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
* <p>
* 候选答案实体类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
@Data
public class ElQuAnswer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 答案ID
     */

    private String id;

    /**
     * 问题ID
     */

    private String quId;

    /**
     * 是否正确
     */

    private Boolean isRight;

    /**
     * 选项图片
     */
    private String image;

    /**
     * 答案内容
     */
    private String content;


    /**
     * 答案分析
     */
    private String analysis;
    
}
