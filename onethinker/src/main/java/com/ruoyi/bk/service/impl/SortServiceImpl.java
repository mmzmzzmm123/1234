package com.ruoyi.bk.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.SortMapper;
import com.ruoyi.bk.domain.Sort;
import com.ruoyi.bk.service.ISortService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 分类Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class SortServiceImpl extends ServiceImpl<SortMapper,Sort> implements ISortService {
    @Resource
    private SortMapper sortMapper;

    /**
     * 查询分类
     *
     * @param id 分类主键
     * @return 分类
     */
    @Override
    public Sort selectSortById(Long id) {
        return sortMapper.selectSortById(id);
    }

    /**
     * 查询分类列表
     *
     * @param sort 分类
     * @return 分类
     */
    @Override
    public List<Sort> selectSortList(Sort sort) {
        return sortMapper.selectSortList(sort);
    }

    /**
     * 新增分类
     *
     * @param sort 分类
     * @return 结果
     */
    @Override
    public int insertSort(Sort sort) {
            return sortMapper.insertSort(sort);
    }

    /**
     * 修改分类
     *
     * @param sort 分类
     * @return 结果
     */
    @Override
    public int updateSort(Sort sort) {
        return sortMapper.updateSort(sort);
    }

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类主键
     * @return 结果
     */
    @Override
    public int deleteSortByIds(Long[] ids) {
        return sortMapper.deleteSortByIds(ids);
    }

    /**
     * 删除分类信息
     *
     * @param id 分类主键
     * @return 结果
     */
    @Override
    public int deleteSortById(Long id) {
        return sortMapper.deleteSortById(id);
    }
}
