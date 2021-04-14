package com.ruoyi.zcrpsgl.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.zcrpsgl.domain.TsbzZcrpsfa;
import com.ruoyi.zcrpsgl.mapper.TsbzZcrpsfaMapper;
import com.ruoyi.zcrpsgl.service.ITsbzZcrpsfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 评审方案(主持人评审管理-评审方案)Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-14
 */
@Service
public class TsbzZcrpsfaServiceImpl implements ITsbzZcrpsfaService
{
    @Autowired
    private TsbzZcrpsfaMapper tsbzZcrpsfaMapper;

    /**
     * 查询评审方案(主持人评审管理-评审方案)
     * 
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 评审方案(主持人评审管理-评审方案)
     */
    @Override
    public TsbzZcrpsfa selectTsbzZcrpsfaById(Long id)
    {
        return tsbzZcrpsfaMapper.selectTsbzZcrpsfaById(id);
    }

    /**
     * 查询评审方案(主持人评审管理-评审方案)列表
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 评审方案(主持人评审管理-评审方案)
     */
    @Override
    public List<TsbzZcrpsfa> selectTsbzZcrpsfaList(TsbzZcrpsfa tsbzZcrpsfa)
    {
        return tsbzZcrpsfaMapper.selectTsbzZcrpsfaList(tsbzZcrpsfa);
    }

    /**
     * 新增评审方案(主持人评审管理-评审方案)
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 结果
     */
    @Override
    public int insertTsbzZcrpsfa(TsbzZcrpsfa tsbzZcrpsfa)
    {
        tsbzZcrpsfa.setCreateTime(DateUtils.getNowDate());
        return tsbzZcrpsfaMapper.insertTsbzZcrpsfa(tsbzZcrpsfa);
    }

    /**
     * 修改评审方案(主持人评审管理-评审方案)
     * 
     * @param tsbzZcrpsfa 评审方案(主持人评审管理-评审方案)
     * @return 结果
     */
    @Override
    public int updateTsbzZcrpsfa(TsbzZcrpsfa tsbzZcrpsfa)
    {
        return tsbzZcrpsfaMapper.updateTsbzZcrpsfa(tsbzZcrpsfa);
    }

    /**
     * 批量删除评审方案(主持人评审管理-评审方案)
     * 
     * @param ids 需要删除的评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrpsfaByIds(Long[] ids)
    {
        return tsbzZcrpsfaMapper.deleteTsbzZcrpsfaByIds(ids);
    }

    /**
     * 删除评审方案(主持人评审管理-评审方案)信息
     * 
     * @param id 评审方案(主持人评审管理-评审方案)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrpsfaById(Long id)
    {
        return tsbzZcrpsfaMapper.deleteTsbzZcrpsfaById(id);
    }

    @Override
    public int openstatus(Long id) {
        TsbzZcrpsfa tsbzZcrpsfa = selectTsbzZcrpsfaById(id);
        tsbzZcrpsfa.setStatus("1");
        return updateTsbzZcrpsfa(tsbzZcrpsfa);
    }

    @Override
    public int stopstatus(Long id) {
        TsbzZcrpsfa tsbzZcrpsfa = selectTsbzZcrpsfaById(id);
        tsbzZcrpsfa.setStatus("0");
        return updateTsbzZcrpsfa(tsbzZcrpsfa);
    }
}
