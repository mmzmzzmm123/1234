package com.jjpt.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jjpt.business.modules.dto.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jjpt.business.modules.entity.Qu;

import java.util.List;

/**
 * QuService
 *
 * @author 卢亚峰
 * @version 2024/05/13 11:23
 **/
public interface QuService extends IService<Qu> {
    /**
     * 分页查询数据
     * @param reqDTO
     * @return
     */
    IPage<QuDTO> paging(PagingReqDTO<QuQueryReqDTO> reqDTO);

    /**
     * 删除试题
     * @param ids
     */
    void delete(List<String> ids);

    /**
     * 随机抽取题库的数据
     * @param repoId
     * @param quType
     * @param level  难度等级
     * @param excludes 要排除的ID列表
     * @param size
     * @return
     */
    List<Qu> listByRandom(String repoId,
                          Integer quType,
                          List<String> excludes,
                          Integer size);

    /**
     * 问题详情
     * @param id
     * @return
     */
    QuDetailDTO detail(String id);

    /**
     * 保存试题
     * @param reqDTO
     */
    void save(QuDetailDTO reqDTO);

    /**
     * 查找导出列表
     * @param query
     * @return
     */
    List<QuExportDTO> listForExport(QuQueryReqDTO query);

    /**
     * 导入Excel
     * @param dtoList
     * @return
     */
    int importExcel(List<QuExportDTO> dtoList);
}

