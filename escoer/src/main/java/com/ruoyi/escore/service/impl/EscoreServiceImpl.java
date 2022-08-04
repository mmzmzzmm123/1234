package com.ruoyi.escore.service.impl;

import java.util.List;

import com.ruoyi.escore.domain.EscoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.escore.mapper.EscoreMapper;
import com.ruoyi.escore.domain.Escore;
import com.ruoyi.escore.service.IEscoreService;

/**
 * 分数Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
@Service
public class EscoreServiceImpl implements IEscoreService 
{
    @Autowired
    private EscoreMapper escoreMapper;

    /**
     * 查询分数
     * 
     * @param id 分数主键
     * @return 分数
     */
    @Override
    public EscoreVo selectEscoreById(Long id)
    {
        return escoreMapper.selectEscoreById(id);
    }

    /**
     * 查询分数列表
     * 
     * @param escore 分数
     * @return 分数
     */
//    @Override
//    public List<Escore> selectEscoreList(Escore escore)
//    {
//        return escoreMapper.selectEscoreList(escore);
//    }

    /**
     * 新增分数
     * 
     * @param escore 分数
     * @return 结果
     */
    @Override
    public int insertEscore(EscoreVo escore)
    {
        return escoreMapper.insertEscore(escore);
    }

    /**
     * 修改分数
     * 
     * @param escore 分数
     * @return 结果
     */
    @Override
    public int updateEscore(Escore escore)
    {
        return escoreMapper.updateEscore(escore);
    }

    /**
     * 批量删除分数
     * 
     * @param ids 需要删除的分数主键
     * @return 结果
     */
    @Override
    public int deleteEscoreByIds(Long[] ids)
    {
        return escoreMapper.deleteEscoreByIds(ids);
    }

    /**
     * 删除分数信息
     * 
     * @param id 分数主键
     * @return 结果
     */
    @Override
    public int deleteEscoreById(Long id)
    {
        return escoreMapper.deleteEscoreById(id);
    }

    @Override
    public List<EscoreVo> selectEscoreVoList(EscoreVo escoreVo) {
        return escoreMapper.selectEscoreVoList(escoreVo);
    }
}
