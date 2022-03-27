package com.jlt.csa.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlt.csa.mapper.CsaMemberCardMapper;
import com.jlt.csa.domain.CsaMemberCard;
import com.jlt.csa.service.ICsaMemberCardService;

/**
 * 会员卡Service业务层处理
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@Service
public class CsaMemberCardServiceImpl implements ICsaMemberCardService 
{
    @Autowired
    private CsaMemberCardMapper csaMemberCardMapper;

    /**
     * 查询会员卡
     * 
     * @param id 会员卡主键
     * @return 会员卡
     */
    @Override
    public CsaMemberCard selectCsaMemberCardById(Long id)
    {
        return csaMemberCardMapper.selectCsaMemberCardById(id);
    }

    /**
     * 查询会员卡列表
     * 
     * @param csaMemberCard 会员卡
     * @return 会员卡
     */
    @Override
    public List<CsaMemberCard> selectCsaMemberCardList(CsaMemberCard csaMemberCard)
    {
        return csaMemberCardMapper.selectCsaMemberCardList(csaMemberCard);
    }

    /**
     * 新增会员卡
     * 
     * @param csaMemberCard 会员卡
     * @return 结果
     */
    @Override
    public int insertCsaMemberCard(CsaMemberCard csaMemberCard)
    {
        csaMemberCard.setCreateTime(DateUtils.getNowDate());
        return csaMemberCardMapper.insertCsaMemberCard(csaMemberCard);
    }

    /**
     * 修改会员卡
     * 
     * @param csaMemberCard 会员卡
     * @return 结果
     */
    @Override
    public int updateCsaMemberCard(CsaMemberCard csaMemberCard)
    {
        csaMemberCard.setUpdateTime(DateUtils.getNowDate());
        return csaMemberCardMapper.updateCsaMemberCard(csaMemberCard);
    }

    /**
     * 批量删除会员卡
     * 
     * @param ids 需要删除的会员卡主键
     * @return 结果
     */
    @Override
    public int deleteCsaMemberCardByIds(Long[] ids)
    {
        return csaMemberCardMapper.deleteCsaMemberCardByIds(ids);
    }

    /**
     * 删除会员卡信息
     * 
     * @param id 会员卡主键
     * @return 结果
     */
    @Override
    public int deleteCsaMemberCardById(Long id)
    {
        return csaMemberCardMapper.deleteCsaMemberCardById(id);
    }
}
