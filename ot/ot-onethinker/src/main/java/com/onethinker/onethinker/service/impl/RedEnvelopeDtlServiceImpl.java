package com.onethinker.onethinker.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.github.pagehelper.PageHelper;
import com.onethinker.common.constant.AwardConstants;
import com.onethinker.common.enums.SysConfigKeyEnum;
import com.onethinker.common.enums.SysStatusTypeEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.MathUtils;
import com.onethinker.common.utils.QrCodeUtils;
import com.onethinker.onethinker.domain.RedEnvelopeDtl;
import com.onethinker.onethinker.dto.CalculateRedEnvelopeDTO;
import com.onethinker.onethinker.dto.RedEnvelopeCtrlDTO;
import com.onethinker.onethinker.mapper.RedEnvelopeDtlMapper;
import com.onethinker.onethinker.service.IRedEnvelopeDtlService;
import com.onethinker.system.service.ISysConfigService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 红包明细Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class RedEnvelopeDtlServiceImpl implements IRedEnvelopeDtlService {
    @Resource
    private RedEnvelopeDtlMapper redEnvelopeDtlMapper;

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 查询红包明细
     *
     * @param id 红包明细主键
     * @return 红包明细
     */
    @Override
    public RedEnvelopeDtl selectRedEnvelopeDtlById(Long id) {
        return redEnvelopeDtlMapper.selectRedEnvelopeDtlById(id);
    }

    /**
     * 查询红包明细列表
     *
     * @param redEnvelopeDtl 红包明细
     * @return 红包明细
     */
    @Override
    public List<RedEnvelopeDtl> selectRedEnvelopeDtlList(RedEnvelopeDtl redEnvelopeDtl) {
        return redEnvelopeDtlMapper.selectRedEnvelopeDtlList(redEnvelopeDtl);
    }

    /**
     * 新增红包明细
     *
     * @param redEnvelopeDtlDTO 红包明细
     * @return 结果
     */
    @Override
    public int insertRedEnvelopeDtl(RedEnvelopeCtrlDTO redEnvelopeDtlDTO) {
        PageHelper.startPage(1, 1);
        RedEnvelopeDtl reqParams = new RedEnvelopeDtl();
        reqParams.setBatchNo(redEnvelopeDtlDTO.getBatchNo());
        List<RedEnvelopeDtl> redEnvelopeDtls = redEnvelopeDtlMapper.selectRedEnvelopeDtlList(reqParams);
        // 当前批次
        Integer nowBatch = redEnvelopeDtls.isEmpty() ? 0 : redEnvelopeDtls.get(0).getBatch();
        // 需要更新的最终结果
        List<RedEnvelopeDtl> saveEntitys = new ArrayList<>();

        // 计算出一批需要生成多少明细红包数据
        Long batchNum = MathUtils.divide(redEnvelopeDtlDTO.getTotalSum(), redEnvelopeDtlDTO.getBatchSum());
        // 生成红包明细
        if (AwardConstants.TYPE_FIXED.equals(redEnvelopeDtlDTO.getType())) {
            // 固定生成
            if (AwardConstants.LUCKY_AWARD_TYPE_NO.equals(redEnvelopeDtlDTO.getLuckyAward())) {
                CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = CalculateRedEnvelopeDTO.calculateRedEnvelopeBatchBaseInfo(redEnvelopeDtlDTO.getTotalMoney(), redEnvelopeDtlDTO.getTotalSum(), redEnvelopeDtlDTO.getBatchSum());
                for (Integer i = 0; i < batchNum; i++) {
                    RedEnvelopeDtl redEnvelopeDtl = new RedEnvelopeDtl();
                    // 基本内容
                    redEnvelopeDtl.setCreateTime(new Date());
                    redEnvelopeDtl.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
                    redEnvelopeDtl.setWeight(System.currentTimeMillis());
                    // 特别内容
                    redEnvelopeDtl.setBatchNo(redEnvelopeDtlDTO.getBatchNo());
                    redEnvelopeDtl.setMoney(calculateRedEnvelopeDTO.getBatchMoney());
                    redEnvelopeDtl.setLuckyAwardType(AwardConstants.LUCKY_AWARD_TYPE_NO);
                    redEnvelopeDtl.setBatch(nowBatch + 1);
                    redEnvelopeDtl.setQrCodeStatus(AwardConstants.CREATE_QR_CODE_STATUS_INIT);
                    saveEntitys.add(redEnvelopeDtl);
                }
            } else if (AwardConstants.LUCKY_AWARD_TYPE_YES.equals(redEnvelopeDtlDTO.getLuckyAward())) {
                CalculateRedEnvelopeDTO calculateRedEnvelopeDTO = CalculateRedEnvelopeDTO.calculateRedEnvelopeBatchBaseInfo(redEnvelopeDtlDTO.getTotalMoney(), redEnvelopeDtlDTO.getTotalSum(), redEnvelopeDtlDTO.getBatchSum(), redEnvelopeDtlDTO.getLuckyAwardMoney(), redEnvelopeDtlDTO.getLuckyAwardCount());
                // 需要计算大奖再哪个位置先
                Set<Integer> luckyAwards = CalculateRedEnvelopeDTO.calculatedLuckyPosition(batchNum, redEnvelopeDtlDTO.getLuckyAwardCount());
                for (Integer i = 0; i < batchNum; i++) {
                    RedEnvelopeDtl redEnvelopeDtl = new RedEnvelopeDtl();
                    // 基本内容
                    redEnvelopeDtl.setCreateTime(new Date());
                    redEnvelopeDtl.setEnabled(SysStatusTypeEnum.STATUS_TYPE_ENABLED.getCode());
                    redEnvelopeDtl.setWeight(System.currentTimeMillis());
                    // 特别内容
                    redEnvelopeDtl.setBatchNo(redEnvelopeDtlDTO.getBatchNo());
                    if (luckyAwards.contains(i)) {
                        redEnvelopeDtl.setLuckyAwardType(AwardConstants.LUCKY_AWARD_TYPE_YES);
                        redEnvelopeDtl.setMoney(redEnvelopeDtlDTO.getLuckyAwardMoney());
                    } else {
                        redEnvelopeDtl.setLuckyAwardType(AwardConstants.LUCKY_AWARD_TYPE_NO);
                        redEnvelopeDtl.setMoney(calculateRedEnvelopeDTO.getBatchMoney());
                    }
                    redEnvelopeDtl.setBatch(nowBatch + 1);
                    redEnvelopeDtl.setQrCodeStatus(AwardConstants.CREATE_QR_CODE_STATUS_INIT);

                    saveEntitys.add(redEnvelopeDtl);
                }

            } else {
                log.warn("生成红包明细类型有误，请开发排除问题，目前类型为：{}", redEnvelopeDtlDTO.getLuckyAward());
            }
        } else {
            // 随机生成
            log.warn("-----随机生成红包  暂未开发-----");
        }
        return redEnvelopeDtlMapper.insertRedEnvelopeDtls(saveEntitys);
    }

    /**
     * 修改红包明细
     *
     * @param redEnvelopeDtl 红包明细
     * @return 结果
     */
    @Override
    public int updateRedEnvelopeDtl(RedEnvelopeDtl redEnvelopeDtl, Integer status) {
        redEnvelopeDtl.setOrgQrCodeStatus(redEnvelopeDtl.getQrCodeStatus());
        redEnvelopeDtl.setQrCodeStatus(status);
        redEnvelopeDtl.setUpdateTime(DateUtils.getNowDate());
        return redEnvelopeDtlMapper.updateRedEnvelopeDtl(redEnvelopeDtl);
    }

    /**
     * 批量删除红包明细
     *
     * @param ids 需要删除的红包明细主键
     * @return 结果
     */
    @Override
    public int deleteRedEnvelopeDtlByIds(Long[] ids) {
        return redEnvelopeDtlMapper.deleteRedEnvelopeDtlByIds(ids);
    }

    /**
     * 删除红包明细信息
     *
     * @param id 红包明细主键
     * @return 结果
     */
    @Override
    public int deleteRedEnvelopeDtlById(Long id) {
        return redEnvelopeDtlMapper.deleteRedEnvelopeDtlById(id);
    }

    @Override
    public String createQrCodeBaseInfo(RedEnvelopeDtl redEnvelopeDtl) {
        // 二维码内容 随机字符串##批次号##明细id##当前批次
        RSA rsa = new RSA(sysConfigService.selectConfigByKey(SysConfigKeyEnum.QR_CODE_RSA_PRIVATE_KEY), sysConfigService.selectConfigByKey(SysConfigKeyEnum.QR_CODE_RSA_PUBLIC_KEY));
        String content = UUID.randomUUID().toString().replaceAll("-", "") + AwardConstants.CREATE_QR_CODE_CONTENT_DELIMITER
                + redEnvelopeDtl.getBatchNo() + AwardConstants.CREATE_QR_CODE_CONTENT_DELIMITER
                + redEnvelopeDtl.getId() + AwardConstants.CREATE_QR_CODE_CONTENT_DELIMITER
                + redEnvelopeDtl.getBatch();
        String encrypt = rsa.encryptBase64(content, KeyType.PublicKey);

        String filePath = sysConfigService.selectConfigByKey(SysConfigKeyEnum.DETAIL_FILE_PATH) + "/" + redEnvelopeDtl.getBatchNo();
        // 生成规则 批次号 + 批次 + 时间戳后8位 + 随机数
        String fileName = redEnvelopeDtl.getBatchNo() + "_" + redEnvelopeDtl.getBatch() + "_" + new String(System.currentTimeMillis() + "").substring(4) + "_" + RandomUtils.nextInt() + ".jpg";
        try {
            QrCodeUtils.encode(encrypt, filePath, fileName);
        } catch (Exception e) {
            throw new RuntimeException("生成二维码失败:" + e.getMessage());
        }
        return filePath + "/" + fileName;
    }
}
