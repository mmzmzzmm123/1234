package com.onethinker.quartz.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.onethinker.activity.domain.RedEnvelopeDtl;
import com.onethinker.activity.dto.RedEnvelopeCtrlDTO;
import com.onethinker.activity.platform.ActivityStorage;
import com.onethinker.activity.service.ActivityStorageService;
import com.onethinker.activity.service.IRedEnvelopeDtlService;
import com.onethinker.common.constant.AwardConstants;
import com.onethinker.common.enums.ActivityTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @Title: CreateRedEnvelopeDtlScheduled
 * @Author yangyouqi
 * @Date 2023/7/23 17:06
 * @description: 创建红包明细记录
 */
@Component("redEnvelopeDtlTask")
@EnableScheduling
@Log4j2
public class RedEnvelopeDtlTask {

    private final ActivityStorage activityStorage;

    @Autowired
    private IRedEnvelopeDtlService redEnvelopeDtlService;

    public RedEnvelopeDtlTask(ActivityStorageService activityStorageService) {
        this.activityStorage = activityStorageService.getActivityStorageVerify(ActivityTypeEnum.RED_ENVELOPE);
    }

    /**
     * 创建红包明细数据
     */
    public void createRedEnvelopDtlTask() {
//        log.info("------开始执行生成红包明细记录------");
        RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = new RedEnvelopeCtrlDTO();
        redEnvelopeCtrlDTO.setStatus(AwardConstants.CREATE_QR_CODE_STATUS_INIT);
        // 分页处理
        PageHelper.startPage(1, 200);
        List<Map> redEnvelopeCtrlMap = activityStorage.queryCtrlEntryByParams(redEnvelopeCtrlDTO);
        if (redEnvelopeCtrlMap.isEmpty()) {
            return;
        }
        log.info("开始生成红包明细，生成数量：{}", redEnvelopeCtrlMap.size());
        for (Map entry : redEnvelopeCtrlMap) {
            ObjectMapper mapper = new ObjectMapper();
            RedEnvelopeCtrlDTO reqDTO = mapper.convertValue(entry, RedEnvelopeCtrlDTO.class);
            try {
                // 更新成生成中状态
                activityStorage.updateEntry(reqDTO, AwardConstants.CREATE_DTL_STATUS_DOING);
                // 创建红包明细记录信息
                int createQrcodeStatus = redEnvelopeDtlService.insertRedEnvelopeDtl(reqDTO);
                if (createQrcodeStatus != 0) {
                    activityStorage.updateEntry(reqDTO, AwardConstants.CREATE_DTL_STATUS_SUCCESS);
                } else {
                    activityStorage.updateEntry(reqDTO, AwardConstants.CREATE_DTL_STATUS_INIT);
                }
            } catch (Exception e) {
                reqDTO.setRemark(e.getMessage());
                activityStorage.updateEntry(reqDTO, AwardConstants.CREATE_QR_CODE_STATUS_DOING);
            }
        }
    }


    /**
     * 创建二维码数据
     */
    public void createQrCode() {
//        log.info("创建二维码数据");
        //  分页处理
        PageHelper.startPage(1, 200);
        RedEnvelopeDtl params = new RedEnvelopeDtl();
        params.setQrCodeStatus(AwardConstants.CREATE_QR_CODE_STATUS_INIT);
        List<RedEnvelopeDtl> redEnvelopeDtlList = redEnvelopeDtlService.selectRedEnvelopeDtlList(params);

        if (redEnvelopeDtlList.isEmpty()) {
//            log.warn("暂不需要执行生成二维码操作");
            return;
        }
        log.info("开始执行生成二维码处理，生成数量：{}", redEnvelopeDtlList.size());
        for (RedEnvelopeDtl redEnvelopeDtl : redEnvelopeDtlList) {
            try {
                // 更新红包明细状态
                int result = redEnvelopeDtlService.updateRedEnvelopeDtl(redEnvelopeDtl, AwardConstants.CREATE_QR_CODE_STATUS_DOING);
                if (result == 0) {
                    log.error("有并发任务同时更新二维码，更新失败");
                    continue;
                }
                // 生成二维码内容
                String qrCodeUrl = redEnvelopeDtlService.createQrCodeBaseInfo(redEnvelopeDtl);
                // 更新二维码红包明细状态
                redEnvelopeDtl.setQrCodeUrl(qrCodeUrl);
                redEnvelopeDtlService.updateRedEnvelopeDtl(redEnvelopeDtl, AwardConstants.CREATE_QR_CODE_STATUS_SUCCESS);
            } catch (Exception e) {
                redEnvelopeDtl.setRemark(e.getMessage());
                redEnvelopeDtlService.updateRedEnvelopeDtl(redEnvelopeDtl, AwardConstants.CREATE_QR_CODE_STATUS_INIT);
            }

        }
    }
}
