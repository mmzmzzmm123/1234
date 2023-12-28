package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.MerchantInfoVO;
import com.ruoyi.system.domain.vo.QueryAmountDetailPageVO;
import com.ruoyi.system.service.MerchantAmountDetailService;
import com.ruoyi.system.service.MerchantAmountService;
import com.ruoyi.system.service.MerchantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/23/023 10:25
 * @Description : 商家管理
 */
@Api("商家数据")
@RestController
@RequestMapping("merchant")
public class MerchantController extends BaseController {

    @Resource
    private MerchantInfoService merchantInfoService;

    @Resource
    private MerchantAmountService merchantAmountService;

    @Resource
    private MerchantAmountDetailService merchantAmountDetailService;


    @ApiOperation("商家列表-分页")
    @PostMapping("page")
    public R<Page<MerchantInfoVO>> page(@Validated @RequestBody QueryMerchantPageDTO dto) {
        Integer merchantType = getMerchantType();
        String merchantId = getMerchantId();
        if (merchantType != null) {
            if (merchantType == 0) {
                dto.setMerchantId(merchantId);
            } else {
                dto.setPlMerchantId(merchantId);
            }
        }
        Page<MerchantInfoVO> page = merchantInfoService.page(dto);
        return R.ok(page);
    }

    @ApiOperation("获取商家资金")
    @PostMapping("getAmount")
    public R<MerchantAmount> getAmount() {
        String merchantId = getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            return R.fail();
        }
        return R.ok(merchantAmountService.getById(merchantId));
    }

    @ApiOperation("商家资金明细-分页")
    @PostMapping("amountDetailPage")
    public R<Page<QueryAmountDetailPageVO>> amountDetailPage(@Validated @RequestBody QueryAmountDetailPageDTO dto) {
        dto.setMerchantId(getMerchantId());
        return R.ok(merchantAmountDetailService.page(dto));
    }


    @ApiOperation("资金划拨")
    @PostMapping("amountTransfer")
    public R amountTransfer(@Validated @RequestBody AmountTransferDTO dto) {
        String merchantId = getMerchantId();
        MerchantInfo merchantInfo = merchantInfoService.getById(dto.getTargetMerchantId());
        Assert.notNull(merchantInfo,"划拨商家不存在");
        Assert.isTrue(StringUtils.equals(merchantInfo.getPlMerchantId(),merchantId),"商家权限不足");

        dto.setMerchantId(merchantId);
        merchantAmountService.amountTransfer(dto);
        return R.ok();
    }

    @ApiOperation("资金回收")
    @PostMapping("amountRecovery")
    public R amountRecovery(@Validated @RequestBody AmountRecoveryDTO dto) {
        String merchantId = getMerchantId();
        MerchantInfo merchantInfo = merchantInfoService.getById(dto.getTargetMerchantId());
        Assert.notNull(merchantInfo,"回收商家不存在");
        Assert.isTrue(StringUtils.equals(merchantInfo.getPlMerchantId(),merchantId),"商家权限不足");

        dto.setMerchantId(merchantId);
        merchantAmountService.amountRecovery(dto);
        return R.ok();
    }

    @ApiOperation("资金充值（仅限运营使用）")
    @PostMapping("amountRecharge")
    public R amountRecharge(@Validated @RequestBody AmountRechargeDTO dto) {
        Integer merchantType = Optional.ofNullable(getMerchantInfo())
                .map(MerchantInfo::getMerchantType)
                .orElse(-1);
        Assert.isTrue(merchantType == 2, "商家权限不足");
        dto.setMerchantId(getMerchantId());
        dto.setUserId(getUserId());
        dto.setCreateBy(getUsername());
        merchantAmountService.amountRecharge(dto);
        return R.ok();
    }

}
