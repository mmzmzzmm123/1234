package com.onethinker.user.domain;

import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 平台用户对象 t_platform_user
 *
 * @author yangyouqi
 * @date 2023-10-23
 */

@Data
public class PlatformUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Integer enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

    /**
     * 抽象id： 可能是电话，也有可能是邮箱,但肯定是唯一
     */
    @Excel(name = "抽象id： 可能是电话，也有可能是邮箱,但肯定是唯一")
    private String dataId;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String avatarUrl;
}
