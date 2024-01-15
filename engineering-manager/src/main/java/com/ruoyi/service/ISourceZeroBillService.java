package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.entity.SourceZeroBill;
import com.ruoyi.req.SourcePageQeq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 原数据---0号台账 服务类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
public interface ISourceZeroBillService extends IService<SourceZeroBill> {

    void importData(MultipartFile file) throws IOException;

    List<SourceZeroBill> dataList(SourcePageQeq req);

    void saveOrUpdateData(SourceZeroBill sourceZeroBill);

}
