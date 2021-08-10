package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.QzBrithdayPu;

/**
 * 生日溥Mapper接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface QzBrithdayPuMapper 
{
    /**
     * 查询生日溥
     * 
     * @param id 生日溥主键
     * @return 生日溥
     */
    public QzBrithdayPu selectQzBrithdayPuById(Long id);

    /**
     * 查询生日溥列表
     * 
     * @param qzBrithdayPu 生日溥
     * @return 生日溥集合
     */
    public List<QzBrithdayPu> selectQzBrithdayPuList(QzBrithdayPu qzBrithdayPu);

    /**
     * 新增生日溥
     * 
     * @param qzBrithdayPu 生日溥
     * @return 结果
     */
    public int insertQzBrithdayPu(QzBrithdayPu qzBrithdayPu);

    /**
     * 修改生日溥
     * 
     * @param qzBrithdayPu 生日溥
     * @return 结果
     */
    public int updateQzBrithdayPu(QzBrithdayPu qzBrithdayPu);

    /**
     * 删除生日溥
     * 
     * @param id 生日溥主键
     * @return 结果
     */
    public int deleteQzBrithdayPuById(Long id);

    /**
     * 批量删除生日溥
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteQzBrithdayPuByIds(Long[] ids);
}
