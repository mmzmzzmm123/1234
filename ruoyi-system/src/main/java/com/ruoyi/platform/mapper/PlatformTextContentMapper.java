package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.PlatformTextContent;

/**
 * 文本内容管理Mapper接口
 *
 * @author Lam
 * @date 2023-09-13
 */
public interface PlatformTextContentMapper {

    /**
     * 查询文本内容管理
     *
     * @param id 文本内容管理主键
     * @return 文本内容管理
     */
    public PlatformTextContent selectPlatformTextContentById(Long id);

    /**
     * 查询文本内容管理列表
     *
     * @param platformTextContent 文本内容管理
     * @return 文本内容管理集合
     */
    public List<PlatformTextContent> selectPlatformTextContentList(PlatformTextContent platformTextContent);

    /**
     * 新增文本内容管理
     *
     * @param platformTextContent 文本内容管理
     * @return 结果
     */
    public int insertPlatformTextContent(PlatformTextContent platformTextContent);

    /**
     * 修改文本内容管理
     *
     * @param platformTextContent 文本内容管理
     * @return 结果
     */
    public int updatePlatformTextContent(PlatformTextContent platformTextContent);

    /**
     * 删除文本内容管理
     *
     * @param id 文本内容管理主键
     * @return 结果
     */
    public int deletePlatformTextContentById(Long id);

    /**
     * 批量删除文本内容管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlatformTextContentByIds(Long[] ids);
}
