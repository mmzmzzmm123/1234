package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeMapper;
import com.ruoyi.project.benyi.domain.ByTheme;
import com.ruoyi.project.benyi.service.IByThemeService;

/**
 * 主题整合Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-01
 */
@Service
public class ByThemeServiceImpl implements IByThemeService {
    @Autowired
    private ByThemeMapper byThemeMapper;

    /**
     * 查询主题整合
     *
     * @param id 主题整合ID
     * @return 主题整合
     */
    @Override
    public ByTheme selectByThemeById(Long id) {
        return byThemeMapper.selectByThemeById(id);
    }

    /**
     * 查询主题整合列表
     *
     * @param byTheme 主题整合
     * @return 主题整合
     */
    @Override
    public List<ByTheme> selectByThemeList(ByTheme byTheme) {
        return byThemeMapper.selectByThemeList(byTheme);
    }

    /**
     * 新增主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    @Override
    public int insertByTheme(ByTheme byTheme) {
        byTheme.setCreateTime(DateUtils.getNowDate());
        return byThemeMapper.insertByTheme(byTheme);
    }

    /**
     * 修改主题整合
     *
     * @param byTheme 主题整合
     * @return 结果
     */
    @Override
    public int updateByTheme(ByTheme byTheme) {
        return byThemeMapper.updateByTheme(byTheme);
    }

    /**
     * 批量删除主题整合
     *
     * @param ids 需要删除的主题整合ID
     * @return 结果
     */
    @Override
    public int deleteByThemeByIds(Long[] ids) {
        return byThemeMapper.deleteByThemeByIds(ids);
    }

    /**
     * 删除主题整合信息
     *
     * @param id 主题整合ID
     * @return 结果
     */
    @Override
    public int deleteByThemeById(Long id) {
        return byThemeMapper.deleteByThemeById(id);
    }
}
