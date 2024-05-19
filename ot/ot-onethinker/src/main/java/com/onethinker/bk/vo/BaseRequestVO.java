package com.onethinker.bk.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author : yangyouqi
 * @date : 2024/1/16 18:19
 */
@Data
public class BaseRequestVO {
    private String order;

    private boolean desc = true;

    private Long source;

    private String commentType;

    private Long floorCommentId;

    private String searchKey;

    private String articleSearch;

    /**
     * 是否推荐[0:否，1:是]
     */
    private Boolean recommendStatus;

    private Integer sortId;

    private Integer labelId;

    private Boolean userStatus;

    private Integer userType;

    private Integer userId;

    private String resourceType;

    private Boolean status;

    private String classify;

    public void existsParams() {
        if (source == null || !StringUtils.hasText(commentType)) {
            throw new RuntimeException("来源有误");
        }
    }
}
