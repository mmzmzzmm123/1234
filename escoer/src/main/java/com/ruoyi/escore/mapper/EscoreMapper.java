package com.ruoyi.escore.mapper;

import java.util.List;
import com.ruoyi.escore.domain.Escore;
import com.ruoyi.escore.domain.EscoreVo;

/**
 * 分数Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
public interface EscoreMapper 
{
    /**
     * 查询分数
     * 
     * @param id 分数主键
     * @return 分数
     */
    public EscoreVo selectEscoreById(Long id);

    /**
     * 查询分数列表
     * 
     * @param escore 分数
     * @return 分数集合
     */
//    public List<Escore> selectEscoreList(Escore escore);

    /**
     * 新增分数
     * 
     * @param escore 分数
     * @return 结果
     */
    public int insertEscore(EscoreVo escore);

    /**
     * 修改分数
     * 
     * @param escore 分数
     * @return 结果
     */
    public int updateEscore(Escore escore);

    /**
     * 删除分数
     * 
     * @param id 分数主键
     * @return 结果
     */
    public int deleteEscoreById(Long id);

    /**
     * 批量删除分数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEscoreByIds(Long[] ids);


    public List<EscoreVo> selectEscoreVoList(EscoreVo escoreVo);
}
