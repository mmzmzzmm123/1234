package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                        import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByChildCheckinDetailMapper;
import com.ruoyi.project.benyi.domain.ByChildCheckinDetail;
import com.ruoyi.project.benyi.service.IByChildCheckinDetailService;

/**
 * 幼儿考勤Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-21
 */
@Service
public class ByChildCheckinDetailServiceImpl implements IByChildCheckinDetailService
{
    @Autowired
    private ByChildCheckinDetailMapper byChildCheckinDetailMapper;

    /**
     * 查询幼儿考勤
     *
     * @param id 幼儿考勤ID
     * @return 幼儿考勤
     */
    @Override
    public ByChildCheckinDetail selectByChildCheckinDetailById(Long id)
    {
        return byChildCheckinDetailMapper.selectByChildCheckinDetailById(id);
    }

    /**
     * 查询幼儿考勤列表
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 幼儿考勤
     */
    @Override
    public List<ByChildCheckinDetail> selectByChildCheckinDetailList(ByChildCheckinDetail byChildCheckinDetail)
    {
        return byChildCheckinDetailMapper.selectByChildCheckinDetailList(byChildCheckinDetail);
    }

    /**
     * 新增幼儿考勤
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 结果
     */
    @Override
    public int insertByChildCheckinDetail(ByChildCheckinDetail byChildCheckinDetail)
    {
                                                                                                                                                                                byChildCheckinDetail.setCreateTime(DateUtils.getNowDate());
                            return byChildCheckinDetailMapper.insertByChildCheckinDetail(byChildCheckinDetail);
    }

    /**
     * 修改幼儿考勤
     *
     * @param byChildCheckinDetail 幼儿考勤
     * @return 结果
     */
    @Override
    public int updateByChildCheckinDetail(ByChildCheckinDetail byChildCheckinDetail)
    {
                                                                                                                                                                                return byChildCheckinDetailMapper.updateByChildCheckinDetail(byChildCheckinDetail);
    }

    /**
     * 批量删除幼儿考勤
     *
     * @param ids 需要删除的幼儿考勤ID
     * @return 结果
     */
    @Override
    public int deleteByChildCheckinDetailByIds(Long[] ids)
    {
        return byChildCheckinDetailMapper.deleteByChildCheckinDetailByIds(ids);
    }

    /**
     * 删除幼儿考勤信息
     *
     * @param id 幼儿考勤ID
     * @return 结果
     */
    @Override
    public int deleteByChildCheckinDetailById(Long id)
    {
        return byChildCheckinDetailMapper.deleteByChildCheckinDetailById(id);
    }
}
