package com.gox.basic.mapper;

import com.gox.basic.domain.ElectronicAttributes;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 电子文件信息Mapper接口
 *
 * @author gox
 * @date 2020-12-29
 */
@Repository
public interface ElectronicAttributesMapper {
    /**
     * 查询电子文件信息
     *
     * @param id 电子文件信息ID
     * @return 电子文件信息
     */
    public ElectronicAttributes selectElectronicAttributesById(Long id);

    /**
     * 查询电子文件信息列表
     *
     * @param electronicAttributes 电子文件信息
     * @return 电子文件信息集合
     */
    public List<ElectronicAttributes> selectElectronicAttributesList(ElectronicAttributes electronicAttributes);

    /**
     * 新增电子文件信息
     *
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    public int insertElectronicAttributes(ElectronicAttributes electronicAttributes);

    /**
     * 修改电子文件信息
     *
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    public int updateElectronicAttributes(ElectronicAttributes electronicAttributes);

    /**
     * 删除电子文件信息
     *
     * @param id 电子文件信息ID
     * @return 结果
     */
    public int deleteElectronicAttributesById(Long id);

    /**
     * 批量删除电子文件信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteElectronicAttributesByIds(Long[] ids);

    /**
     * 根据元数据id查询
     *
     * @param id
     * @return 电子文件列表
     */
    List<ElectronicAttributes> selectEleByMdId(Long id);
}
