package com.xinyu.idol.service;

import com.xinyu.idol.pojo.entity.PakPathIdMapEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * pak资源path和guid的映射表 服务类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
public interface IPakPathIdMapService extends IService<PakPathIdMapEntity> {

        PakPathIdMapEntity insertGetId(PakPathIdMapEntity pakPathIdMapEntity);

        PakPathIdMapEntity getByPath(PakPathIdMapEntity pakPathIdMapEntity);

}
