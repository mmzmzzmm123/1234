package com.renxin.psychology.mapper;

import com.renxin.psychology.domain.PsyConsultantDebitcard;

import java.util.List;

/**
 * 客户银行卡Mapper接口
 * 
 * @author renxin
 * @date 2024-06-20
 */
public interface PsyConsultantDebitcardMapper 
{
    /**
     * 查询客户银行卡
     * 
     * @param cardNumber 客户银行卡主键
     * @return 客户银行卡
     */
    public PsyConsultantDebitcard selectPsyConsultantDebitcardByCardNumber(String cardNumber);

    /**
     * 查询客户银行卡列表
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 客户银行卡集合
     */
    public List<PsyConsultantDebitcard> selectPsyConsultantDebitcardList(PsyConsultantDebitcard psyConsultantDebitcard);

    /**
     * 新增客户银行卡
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 结果
     */
    public int insertPsyConsultantDebitcard(PsyConsultantDebitcard psyConsultantDebitcard);

    /**
     * 修改客户银行卡
     * 
     * @param psyConsultantDebitcard 客户银行卡
     * @return 结果
     */
    public int updatePsyConsultantDebitcard(PsyConsultantDebitcard psyConsultantDebitcard);

    /**
     * 删除客户银行卡
     * 
     * @param cardNumber 客户银行卡主键
     * @return 结果
     */
    public int deletePsyConsultantDebitcardByCardNumber(String cardNumber);

    /**
     * 批量删除客户银行卡
     * 
     * @param cardNumbers 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultantDebitcardByCardNumbers(String[] cardNumbers);
}
