package com.ruoyi.system.extend.data;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Jing.Zhang
 */
@Data
public class TaskDetailResultData {

    private String phone;
    private String userName;

    @Excel(name = "目标名字")
    private String objName;
    private String friendSerialNo;
//    @Excel(name = "执行状态", replace = {"待执行_0", "执行中_1", "执行完成_2", "取消_-1"})
    private Integer executeStatus;
    private Integer resultCode;

    @Excel(name = "原因描述")
    private String resultMsg;
    private Integer status;
//    @Excel(name = "执行结果", replace = {"成功_1", "待回调_0", "失败_-1", "待发送_10", "未开通_-20", "取消_-30"})
    private String result;
    @Excel(name = "机器人手机号码")
    private String tgPhone;
    @Excel(name = "机器人所属国家")
    private String tgCountry;
    @Excel(name = "机器人Id")
    private String tgRobotId;
    @Excel(name = "消息内容")
    private String messageContent;
    private String actualSendContent;
    private String messageTitle;
//    @Excel(name = "消息类型", replace = {"文本_2001", "图片_2002", "视频_2004", "PostBot_2035", "图片+文字_20021", "视频+文字_20041"})
    private Integer messageType;
//    @Excel(name = "发送时间", format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;
    @Excel(name = "目标国家")
    private String country;
//    @Excel(name = "是否已读", replace = {"是_true", "否_false"})
    private Boolean isRead;
//    @Excel(name = "是否回复", replace = {"是_true", "否_false"})
    private Boolean isReply;
    private String replyContent;
    private LocalDateTime replyTime;
//    @Excel(name = "是否拉黑", replace = {"是_true", "否_false"})
    private Boolean isBlock;
    private Integer importType;
    private String contactPackageTaskId;
    private String postBotMsgJson;
    private Integer getRobotStatus;
    private String autoReplyMsgContent;
    private String autoReplyMsgTitle;
    private Integer autoReplyMsgType;
    private LocalDateTime autoReplyTime;
}
