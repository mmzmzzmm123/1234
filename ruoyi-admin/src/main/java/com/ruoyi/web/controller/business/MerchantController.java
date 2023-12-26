package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.MerchantAmount;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.system.domain.MerchantAmountDetail;
import com.ruoyi.system.domain.dto.AmountTransferDTO;
import com.ruoyi.system.domain.dto.QueryAmountDetailPageDTO;
import com.ruoyi.system.domain.dto.QueryMerchantPageDTO;
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
    public AjaxResult page(@Validated @RequestBody QueryMerchantPageDTO dto) {
        Integer merchantType = Optional.ofNullable(getMerchantInfo())
                .map(MerchantInfo::getMerchantType)
                .orElse(-1);
        // 非运营 只能看自己下属商家
        if (merchantType != 2) {
            dto.setPlMerchantId(getMerchantId());
        }
        Page<MerchantInfo> page = merchantInfoService.page(dto);
        return AjaxResult.success(page);
    }

    @ApiOperation("获取商家资金")
    @PostMapping("getAmount")
    public AjaxResult getAmount() {
        String merchantId = getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            return AjaxResult.error();
        }
        return AjaxResult.success(merchantAmountService.getById(merchantId));
    }

    @ApiOperation("商家资金明细-分页")
    @PostMapping("amountDetailPage")
    public AjaxResult amountDetailPage(@Validated @RequestBody QueryAmountDetailPageDTO dto) {
        dto.setMerchantId(getMerchantId());
        return AjaxResult.success(merchantAmountDetailService.page(dto));
    }


    @ApiOperation("资金划拨")
    @PostMapping("amountTransfer")
    public AjaxResult amountTransfer(@Validated @RequestBody AmountTransferDTO dto) {
        Integer merchantType = Optional.ofNullable(getMerchantInfo())
                .map(MerchantInfo::getMerchantType)
                .orElse(-1);
        Assert.isTrue(merchantType != 0, "商家权限不足,资金划拨失败");
        dto.setMerchantId(getMerchantId());
        merchantAmountService.amountTransfer(dto);
        return AjaxResult.success();
    }

}
