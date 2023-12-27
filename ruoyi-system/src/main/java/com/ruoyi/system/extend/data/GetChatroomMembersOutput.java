package com.ruoyi.system.extend.data;

@lombok.Data
public class GetChatroomMembersOutput {
    private String memberSerialNo;
    private String fristName;
    private String lastName;
    private String userName;
    private Integer isOwner;
    private Integer isAdmin;
    private Integer isTargetOwner;
}
