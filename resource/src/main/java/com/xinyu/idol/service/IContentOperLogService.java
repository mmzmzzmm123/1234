package com.xinyu.idol.service;

import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 资源冗余大表操作记录表，作为备份留存 服务类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
public interface IContentOperLogService extends IService<ContentOperLogEntity> {

    void addContentOperaLog(ContentOperLogEntity contentOperLogEntity);


}
