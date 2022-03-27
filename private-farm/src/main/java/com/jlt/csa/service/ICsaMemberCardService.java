package com.jlt.csa.service;

import java.util.List;
import com.jlt.csa.domain.CsaMemberCard;

/**
 * 会员卡Service接口
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
public interface ICsaMemberCardService 
{
    /**
     * 查询会员卡
     * 
     * @param id 会员卡主键
     * @return 会员卡
     */
    public CsaMemberCard selectCsaMemberCardById(Long id);

    /**
     * 查询会员卡列表
     * 
     * @param csaMemberCard 会员卡
     * @return 会员卡集合
     */
    public List<CsaMemberCard> selectCsaMemberCardList(CsaMemberCard csaMemberCard);

    /**
     * 新增会员卡
     * 
     * @param csaMemberCard 会员卡
     * @return 结果
     */
    public int insertCsaMemberCard(CsaMemberCard csaMemberCard);

    /**
     * 修改会员卡
     * 
     * @param csaMemberCard 会员卡
     * @return 结果
     */
    public int updateCsaMemberCard(CsaMemberCard csaMemberCard);

    /**
     * 批量删除会员卡
     * 
     * @param ids 需要删除的会员卡主键集合
     * @return 结果
     */
    public int deleteCsaMemberCardByIds(Long[] ids);

    /**
     * 删除会员卡信息
     * 
     * @param id 会员卡主键
     * @return 结果
     */
    public int deleteCsaMemberCardById(Long id);
}
