package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                        import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMathMapper;
import com.ruoyi.project.benyi.domain.ByMath;
import com.ruoyi.project.benyi.service.IByMathService;

/**
 * 游戏数学Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-20
 */
@Service
public class ByMathServiceImpl implements IByMathService
{
    @Autowired
    private ByMathMapper byMathMapper;

    /**
     * 查询游戏数学
     *
     * @param id 游戏数学ID
     * @return 游戏数学
     */
    @Override
    public ByMath selectByMathById(Long id)
    {
        return byMathMapper.selectByMathById(id);
    }

    /**
     * 查询游戏数学列表
     *
     * @param byMath 游戏数学
     * @return 游戏数学
     */
    @Override
    public List<ByMath> selectByMathList(ByMath byMath)
    {
        return byMathMapper.selectByMathList(byMath);
    }

    /**
     * 新增游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    @Override
    public int insertByMath(ByMath byMath)
    {
                                                                                                                                                                                byMath.setCreateTime(DateUtils.getNowDate());
                            return byMathMapper.insertByMath(byMath);
    }

    /**
     * 修改游戏数学
     *
     * @param byMath 游戏数学
     * @return 结果
     */
    @Override
    public int updateByMath(ByMath byMath)
    {
                                                                                                                                                                                return byMathMapper.updateByMath(byMath);
    }

    /**
     * 批量删除游戏数学
     *
     * @param ids 需要删除的游戏数学ID
     * @return 结果
     */
    @Override
    public int deleteByMathByIds(Long[] ids)
    {
        return byMathMapper.deleteByMathByIds(ids);
    }

    /**
     * 删除游戏数学信息
     *
     * @param id 游戏数学ID
     * @return 结果
     */
    @Override
    public int deleteByMathById(Long id)
    {
        return byMathMapper.deleteByMathById(id);
    }
}
