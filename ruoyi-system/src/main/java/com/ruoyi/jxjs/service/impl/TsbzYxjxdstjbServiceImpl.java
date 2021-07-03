package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzYxjxdstjbMapper;
import com.ruoyi.jxjs.domain.TsbzYxjxdstjb;
import com.ruoyi.jxjs.service.ITsbzYxjxdstjbService;

/**
 * 优秀见习导师推荐Service业务层处理
 *
 * @author tsbz
 * @date 2021-07-03
 */
@Service
public class TsbzYxjxdstjbServiceImpl implements ITsbzYxjxdstjbService {
    @Autowired
    private TsbzYxjxdstjbMapper tsbzYxjxdstjbMapper;

    /**
     * 查询优秀见习导师推荐
     *
     * @param id 优秀见习导师推荐ID
     * @return 优秀见习导师推荐
     */
    @Override
    public TsbzYxjxdstjb selectTsbzYxjxdstjbById(Long id) {
        return tsbzYxjxdstjbMapper.selectTsbzYxjxdstjbById(id);
    }

    /**
     * 查询优秀见习导师推荐列表
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 优秀见习导师推荐
     */
    @Override
    public List<TsbzYxjxdstjb> selectTsbzYxjxdstjbList(TsbzYxjxdstjb tsbzYxjxdstjb) {
        return tsbzYxjxdstjbMapper.selectTsbzYxjxdstjbList(tsbzYxjxdstjb);
    }

    /**
     * 新增优秀见习导师推荐
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 结果
     */
    @Override
    public int insertTsbzYxjxdstjb(TsbzYxjxdstjb tsbzYxjxdstjb) {
        tsbzYxjxdstjb.setCreateTime(DateUtils.getNowDate());
        return tsbzYxjxdstjbMapper.insertTsbzYxjxdstjb(tsbzYxjxdstjb);
    }

    /**
     * 修改优秀见习导师推荐
     *
     * @param tsbzYxjxdstjb 优秀见习导师推荐
     * @return 结果
     */
    @Override
    public int updateTsbzYxjxdstjb(TsbzYxjxdstjb tsbzYxjxdstjb) {
        return tsbzYxjxdstjbMapper.updateTsbzYxjxdstjb(tsbzYxjxdstjb);
    }

    /**
     * 批量删除优秀见习导师推荐
     *
     * @param ids 需要删除的优秀见习导师推荐ID
     * @return 结果
     */
    @Override
    public int deleteTsbzYxjxdstjbByIds(Long[] ids) {
        return tsbzYxjxdstjbMapper.deleteTsbzYxjxdstjbByIds(ids);
    }

    /**
     * 删除优秀见习导师推荐信息
     *
     * @param id 优秀见习导师推荐ID
     * @return 结果
     */
    @Override
    public int deleteTsbzYxjxdstjbById(Long id) {
        return tsbzYxjxdstjbMapper.deleteTsbzYxjxdstjbById(id);
    }
}
