package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.psychology.domain.PsyConsultantDebitcard;
import com.renxin.psychology.mapper.PsyConsultantDebitcardMapper;
import com.renxin.psychology.service.IPsyConsultantDebitcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 客户银行卡Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-20
 */
@Service
public class PsyConsultantDebitcardServiceImpl implements IPsyConsultantDebitcardService
{
    @Autowired
    private PsyConsultantDebitcardMapper psyConsultantDebitcardMapper;

    /**
     * 查询客户银行卡
     * 
     * @param cardNumber 客户银行卡主键
     * @return 客户银行卡
     */
    @Override
    public PsyConsultantDebitcard selectPsyConsultantDebitcardByCardNumber(String cardNumber)
    {
        return psyConsultantDebitcardMapper.selectPsyConsultantDebitcardByCardNumber(cardNumber);
    }

    /**
     * 查询客户银行卡列表
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 客户银行卡
     */
    @Override
    public List<PsyConsultantDebitcard> selectPsyConsultantDebitcardList(PsyConsultantDebitcard psyConsultantDebitcard)
    {
        return psyConsultantDebitcardMapper.selectPsyConsultantDebitcardList(psyConsultantDebitcard);
    }

    /**
     * 新增客户银行卡
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 结果
     */
    @Override
    public int insertPsyConsultantDebitcard(PsyConsultantDebitcard psyConsultantDebitcard)
    {
        psyConsultantDebitcard.setCreateTime(DateUtils.getNowDate());
        return psyConsultantDebitcardMapper.insertPsyConsultantDebitcard(psyConsultantDebitcard);
    }

    /**
     * 修改客户银行卡
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 结果
     */
    @Override
    public int updatePsyConsultantDebitcard(PsyConsultantDebitcard psyConsultantDebitcard)
    {
        psyConsultantDebitcard.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantDebitcardMapper.updatePsyConsultantDebitcard(psyConsultantDebitcard);
    }

    /**
     * 批量删除客户银行卡
     * 
     * @param cardNumbers 需要删除的客户银行卡主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantDebitcardByCardNumbers(String[] cardNumbers)
    {
        return psyConsultantDebitcardMapper.deletePsyConsultantDebitcardByCardNumbers(cardNumbers);
    }

    /**
     * 删除客户银行卡信息
     * 
     * @param cardNumber 客户银行卡主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantDebitcardByCardNumber(String cardNumber)
    {
        return psyConsultantDebitcardMapper.deletePsyConsultantDebitcardByCardNumber(cardNumber);
    }
}
