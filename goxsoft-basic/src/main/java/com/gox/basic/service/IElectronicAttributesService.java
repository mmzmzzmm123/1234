package com.gox.basic.service;


import com.gox.common.utils.file.Chunk;
import com.gox.basic.domain.ElectronicAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 电子文件信息Service接口
 *
 * @author gox
 * @date 2020-12-29
 */
public interface IElectronicAttributesService {
    /**
     * 查询电子文件信息
     *
     * @param id 电子文件信息ID
     * @return 电子文件信息
     */
    public ElectronicAttributes selectElectronicAttributesById(Long id);


    String fileUploadPost(Chunk chunk, HttpServletResponse response, Long metadataId) throws IOException;

    void fileUploadGet(Chunk chunk, HttpServletResponse response);


    /**
     * 获取文件base64编码
     *
     * @param id 电子文件id
     */
    String getBase64(String id);

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

    int insertElectronicAttributesBatch(Collection<ElectronicAttributes> electronicAttributes);

    /**
     * 修改电子文件信息
     *
     * @param electronicAttributes 电子文件信息
     * @return 结果
     */
    public int updateElectronicAttributes(ElectronicAttributes electronicAttributes);

    /**
     * 批量删除电子文件信息
     *
     * @param ids 需要删除的电子文件信息ID
     * @return 结果
     */
    public int deleteElectronicAttributesByIds(Long[] ids);

    /**
     * 删除电子文件信息信息
     *
     * @param id 电子文件信息ID
     * @return 结果
     */
    public int deleteElectronicAttributesById(Long id);
}
