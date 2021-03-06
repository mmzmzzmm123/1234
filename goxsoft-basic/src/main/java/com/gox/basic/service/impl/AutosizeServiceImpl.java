package com.gox.basic.service.impl;

import com.gox.basic.domain.form.Autosize;
import com.gox.basic.mapper.AutosizeMapper;
import com.gox.basic.service.IAutosizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   Service业务层处理
 *
 * @author gox
 * @date 2021-02-02
 */
@Service
public class AutosizeServiceImpl implements IAutosizeService {
    @Autowired
    private AutosizeMapper autosizeMapper;

    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    @Override
    public Autosize selectAutosizeById(Long id) {
        return autosizeMapper.selectAutosizeById(id);
    }

    /**
     * 查询  列表
     *
     * @param autosize
     * @return
     */
    @Override
    public List<Autosize> selectAutosizeList(Autosize autosize) {
        return autosizeMapper.selectAutosizeList(autosize);
    }

    /**
     * 新增
     *
     * @param autosize
     * @return 结果
     */
    @Override
    public int insertAutosize(Autosize autosize) {
        return autosizeMapper.insertAutosize(autosize);
    }

    /**
     * 修改
     *
     * @param autosize
     * @return 结果
     */
    @Override
    public int updateAutosize(Autosize autosize) {
        return autosizeMapper.updateAutosize(autosize);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    @Override
    public int deleteAutosizeByIds(Long[] ids) {
        return autosizeMapper.deleteAutosizeByIds(ids);
    }

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    @Override
    public int deleteAutosizeById(Long id) {
        return autosizeMapper.deleteAutosizeById(id);
    }
}
