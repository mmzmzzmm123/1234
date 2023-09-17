package com.ruoyi.office.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserAmountMapper;
import com.ruoyi.office.domain.TWxUserAmount;
import com.ruoyi.office.service.ITWxUserAmountService;

/**
 * 微信用户余额Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserAmountServiceImpl extends ServiceImpl<TWxUserAmountMapper, TWxUserAmount> implements ITWxUserAmountService {
    @Autowired
    private TWxUserAmountMapper tWxUserAmountMapper;

    /**
     * 查询微信用户余额
     *
     * @param id 微信用户余额主键
     * @return 微信用户余额
     */
    @Override
    public TWxUserAmount selectTWxUserAmountById(Long id) {
        return tWxUserAmountMapper.selectTWxUserAmountById(id);
    }

    /**
     * 查询微信用户余额列表
     *
     * @param tWxUserAmount 微信用户余额
     * @return 微信用户余额
     */
    @Override
    public List<TWxUserAmount> selectTWxUserAmountList(TWxUserAmount tWxUserAmount) {
        return tWxUserAmountMapper.selectTWxUserAmountList(tWxUserAmount);
    }

    /**
     * 新增微信用户余额
     *
     * @param tWxUserAmount 微信用户余额
     * @return 结果
     */
    @Override
    public int insertTWxUserAmount(TWxUserAmount tWxUserAmount) {
        TWxUserAmount wxUserAmount = new TWxUserAmount();
        wxUserAmount.setUserId(tWxUserAmount.getUserId());
        wxUserAmount.setWxUserId(tWxUserAmount.getWxUserId());
        List<TWxUserAmount> wxUserAmounts = tWxUserAmountMapper.selectTWxUserAmountList(wxUserAmount);
        if (wxUserAmounts.size() > 0) {
            wxUserAmount.setAmount(tWxUserAmount.getAmount().negate());
            wxUserAmount.setUpdateTime(DateUtils.getNowDate());
            return tWxUserAmountMapper.minusCardValue(wxUserAmount);
        } else {
            tWxUserAmount.setCreateTime(DateUtils.getNowDate());
            return tWxUserAmountMapper.insertTWxUserAmount(tWxUserAmount);
        }
    }

    /**
     * 修改微信用户余额
     *
     * @param tWxUserAmount 微信用户余额
     * @return 结果
     */
    @Override
    public int updateTWxUserAmount(TWxUserAmount tWxUserAmount) {
        tWxUserAmount.setUpdateTime(DateUtils.getNowDate());
        return tWxUserAmountMapper.updateTWxUserAmount(tWxUserAmount);
    }

    /**
     * 批量删除微信用户余额
     *
     * @param ids 需要删除的微信用户余额主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserAmountByIds(Long[] ids) {
        return tWxUserAmountMapper.deleteTWxUserAmountByIds(ids);
    }

    /**
     * 删除微信用户余额信息
     *
     * @param id 微信用户余额主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserAmountById(Long id) {
        return tWxUserAmountMapper.deleteTWxUserAmountById(id);
    }

    @Override
    public void minusCardValue(TWxUserAmount wxUserAmount) {
        tWxUserAmountMapper.minusCardValue(wxUserAmount);
    }


    @Override
    public void addUserMerchantAmount(TWxUserAmount wxUserAmount) {
        tWxUserAmountMapper.addUserMerchantAmount(wxUserAmount);
    }
}
