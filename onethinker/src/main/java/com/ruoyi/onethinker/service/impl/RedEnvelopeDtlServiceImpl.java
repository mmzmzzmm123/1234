package com.ruoyi.onethinker.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.onethinker.mapper.RedEnvelopeDtlMapper;
import com.ruoyi.onethinker.domain.RedEnvelopeDtl;
import com.ruoyi.onethinker.service.IRedEnvelopeDtlService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

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
     * @param redEnvelopeDtl 红包明细
     * @return 结果
     */
    @Override
    public int insertRedEnvelopeDtl(RedEnvelopeDtl redEnvelopeDtl) {
                redEnvelopeDtl.setCreateTime(DateUtils.getNowDate());
            return redEnvelopeDtlMapper.insertRedEnvelopeDtl(redEnvelopeDtl);
    }

    /**
     * 修改红包明细
     *
     * @param redEnvelopeDtl 红包明细
     * @return 结果
     */
    @Override
    public int updateRedEnvelopeDtl(RedEnvelopeDtl redEnvelopeDtl) {
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
}
