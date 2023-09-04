package com.ruoyi.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 咨询服务对象 psy_consult_focus
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("psy_user_liked_consult")
public class PsyUserLikedConsult implements Serializable
{
    private static final long serialVersionUID = -139254167831601253L;

    /** 用户 */
    private Integer userId;

    /** 咨询师 */
    private Long consultId;

    /** 创建时间 */
    private Date createTime;

}
