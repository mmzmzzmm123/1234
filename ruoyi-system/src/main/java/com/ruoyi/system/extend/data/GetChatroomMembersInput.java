package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class GetChatroomMembersInput {
    private Integer pageIndex;
    private Integer pageSize;
    private String sortName;
    private Integer sortOrder;
    private Integer taskId;
    private String chatroomSerialNo;
    private String keyword;
    private String userCode;
}
