package com.ruoyi.gbxxgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gbxxgl.mapper.TsbzGbjtcyMapper;
import com.ruoyi.gbxxgl.domain.TsbzGbjtcy;
import com.ruoyi.gbxxgl.service.ITsbzGbjtcyService;

/**
 * 家庭成员(干部管理-个人状况)Service业务层处理
 *
 * @author tsbz
 * @date 2020-12-21
 */
@Service
public class TsbzGbjtcyServiceImpl implements ITsbzGbjtcyService {
    @Autowired
    private TsbzGbjtcyMapper tsbzGbjtcyMapper;

    /**
     * 查询家庭成员(干部管理-个人状况)
     *
     * @param id 家庭成员(干部管理-个人状况)ID
     * @return 家庭成员(干部管理 - 个人状况)
     */
    @Override
    public TsbzGbjtcy selectTsbzGbjtcyById(Long id) {
        return tsbzGbjtcyMapper.selectTsbzGbjtcyById(id);
    }

    /**
     * 查询家庭成员(干部管理-个人状况)列表
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 家庭成员(干部管理 - 个人状况)
     */
    @Override
    public List<TsbzGbjtcy> selectTsbzGbjtcyList(TsbzGbjtcy tsbzGbjtcy) {
        return tsbzGbjtcyMapper.selectTsbzGbjtcyList(tsbzGbjtcy);
    }

    /**
     * 新增家庭成员(干部管理-个人状况)
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 结果
     */
    @Override
    public int insertTsbzGbjtcy(TsbzGbjtcy tsbzGbjtcy) {
        tsbzGbjtcy.setCreateTime(DateUtils.getNowDate());
        return tsbzGbjtcyMapper.insertTsbzGbjtcy(tsbzGbjtcy);
    }

    /**
     * 修改家庭成员(干部管理-个人状况)
     *
     * @param tsbzGbjtcy 家庭成员(干部管理-个人状况)
     * @return 结果
     */
    @Override
    public int updateTsbzGbjtcy(TsbzGbjtcy tsbzGbjtcy) {
        return tsbzGbjtcyMapper.updateTsbzGbjtcy(tsbzGbjtcy);
    }

    /**
     * 批量删除家庭成员(干部管理-个人状况)
     *
     * @param ids 需要删除的家庭成员(干部管理-个人状况)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbjtcyByIds(Long[] ids) {
        return tsbzGbjtcyMapper.deleteTsbzGbjtcyByIds(ids);
    }

    /**
     * 删除家庭成员(干部管理-个人状况)信息
     *
     * @param id 家庭成员(干部管理-个人状况)ID
     * @return 结果
     */
    @Override
    public int deleteTsbzGbjtcyById(Long id) {
        return tsbzGbjtcyMapper.deleteTsbzGbjtcyById(id);
    }
}
