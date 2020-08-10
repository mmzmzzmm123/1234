package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                                                                                                                                import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildHealthCheckMapper;
import com.ruoyi.project.benyi.domain.ByChildHealthCheck;
import com.ruoyi.project.benyi.service.IByChildHealthCheckService;

/**
 * 儿童常规体检记录Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-07
 */
@Service
public class ByChildHealthCheckServiceImpl implements IByChildHealthCheckService
{
    @Autowired
    private ByChildHealthCheckMapper byChildHealthCheckMapper;

    /**
     * 查询儿童常规体检记录
     *
     * @param id 儿童常规体检记录ID
     * @return 儿童常规体检记录
     */
    @Override
    public ByChildHealthCheck selectByChildHealthCheckById(Long id)
    {
        return byChildHealthCheckMapper.selectByChildHealthCheckById(id);
    }

    /**
     * 查询儿童常规体检记录列表
     *
     * @param byChildHealthCheck 儿童常规体检记录
     * @return 儿童常规体检记录
     */
    @Override
    public List<ByChildHealthCheck> selectByChildHealthCheckList(ByChildHealthCheck byChildHealthCheck)
    {
        return byChildHealthCheckMapper.selectByChildHealthCheckList(byChildHealthCheck);
    }

    /**
     * 新增儿童常规体检记录
     *
     * @param byChildHealthCheck 儿童常规体检记录
     * @return 结果
     */
    @Override
    public int insertByChildHealthCheck(ByChildHealthCheck byChildHealthCheck)
    {
                                                                                                                                                                                                                                                                                                                                                                    return byChildHealthCheckMapper.insertByChildHealthCheck(byChildHealthCheck);
    }

    /**
     * 修改儿童常规体检记录
     *
     * @param byChildHealthCheck 儿童常规体检记录
     * @return 结果
     */
    @Override
    public int updateByChildHealthCheck(ByChildHealthCheck byChildHealthCheck)
    {
                                                                                                                                                                                                                                                                                                                                                                    return byChildHealthCheckMapper.updateByChildHealthCheck(byChildHealthCheck);
    }

    /**
     * 批量删除儿童常规体检记录
     *
     * @param ids 需要删除的儿童常规体检记录ID
     * @return 结果
     */
    @Override
    public int deleteByChildHealthCheckByIds(Long[] ids)
    {
        return byChildHealthCheckMapper.deleteByChildHealthCheckByIds(ids);
    }

    /**
     * 删除儿童常规体检记录信息
     *
     * @param id 儿童常规体检记录ID
     * @return 结果
     */
    @Override
    public int deleteByChildHealthCheckById(Long id)
    {
        return byChildHealthCheckMapper.deleteByChildHealthCheckById(id);
    }
}
