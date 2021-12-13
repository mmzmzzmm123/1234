package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.page.VisualRespEmbData;
import com.ruoyi.system.domain.BladeVisual;
import com.ruoyi.system.domain.vo.BladeVisualVo;
import com.ruoyi.system.domain.vo.IdNameVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 可视化Service接口
 *
 * @author yangsj
 * @date 2021-10-25
 */
public interface IBladeVisualService
{
    /**
     * 查询可视化
     *
     * @param id 可视化主键
     * @return 可视化
     */
    public BladeVisualVo selectBladeVisualById(Long id);

    /**
     * 查询可视化列表
     *
     * @param bladeVisual 可视化
     * @return 可视化集合
     */
    public List<BladeVisual> selectBladeVisualList(BladeVisual bladeVisual);

    /**
     * 新增可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    public Long insertBladeVisual(BladeVisual bladeVisual);

    /**
     * 修改可视化
     *
     * @param bladeVisual 可视化
     * @return 结果
     */
    public int updateBladeVisual(BladeVisual bladeVisual);

    /**
     * 批量删除可视化
     *
     * @param ids 需要删除的可视化主键集合
     * @return 结果
     */
    public int deleteBladeVisualByIds(Long[] ids);

    /**
     * 删除可视化信息
     *
     * @param id 可视化主键
     * @return 结果
     */
    public int deleteBladeVisualById(Long id);

    List<BladeVisual> selectVisualListByCategory(Long category);

    VisualRespEmbData uploadFile(MultipartFile file);

    void copyById(Long id);

    List<IdNameVo> getIdNames();
}
