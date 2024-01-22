//package com.ruoyi.system.components.spi;
//
//import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
//import com.ruoyi.common.utils.spi.SPI;
//import com.ruoyi.system.openapi.OpenApiClient;
//import com.ruoyi.system.openapi.OpenApiResult;
//import com.ruoyi.system.openapi.model.input.ThirdTgGetGroupMemberInputDTO;
//import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.Date;
//import java.util.Map;
//
//@SPI("TgGroupMemberSettings")
//public class TgGroupMemberSettings implements Settings {
//    @Override
//    public PlayRobotPackLog set(Map<String, Object> param) {
//        ThirdTgGetGroupMemberInputDTO dto = new ThirdTgGetGroupMemberInputDTO();
//        dto.setChatroomSerialNo(param.get(Settings.Key_GroupId).toString());
//        dto.setTgRobotId(param.get(Settings.Key_RobotId).toString());
//        @SuppressWarnings("rawtypes")
//        OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.getGroupMemberByThirdKpTg(dto);
//        PlayRobotPackLog data = new PlayRobotPackLog();
//        data.setChatroomId(param.get(Settings.Key_GroupId).toString());
//        data.setCreateTime(new Date());
//        data.setIsFinish(0);
//        // 1-设置机器人姓名，姓氏 2-设置机器人头像 3-获取群hash 4-设置群管理员 5-获取群成员
//        data.setOp(5);
//        data.setPlayId(param.get(Settings.Key_PlayId).toString());
//        data.setRetryCount(0);
//        data.setRobotId(dto.getTgRobotId());
//        data.setIsBackup(((boolean) param.get(Settings.Key_Backup_Flag)) ? 1 : 0);
//        if (ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo())) {
//            // 成功
//            data.setStatus(0);
//            data.setOpt(ret.getData().getOptSerNo());
//            return data;
//        }
//        // 失败
//        data.setStatus(2);
//        data.setErrMsg(ret.getMessage());
//        return data;
//    }
//}
