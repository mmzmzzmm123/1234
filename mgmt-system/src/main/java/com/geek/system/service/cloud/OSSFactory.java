package com.geek.system.service.cloud;


import cn.hutool.json.JSONUtil;
import com.geek.system.service.ISysConfigService;
import com.geek.system.service.cloud.utils.ConfigConstant;
import com.geek.system.service.cloud.utils.Constant;
import com.geek.system.service.cloud.utils.SpringContextUtils;
import com.geek.system.service.impl.SysConfigServiceImpl;

/**
 * 文件上传Factory
 * @author Mark sunlightcs@gmail.com
 */
public final class OSSFactory {
    private static ISysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigServiceImpl) SpringContextUtils.getBean("sysConfigService");
    }

    public static AbstractCloudStorageService build() {
        //获取云存储配置信息
        String configStr = sysConfigService.selectConfigByKey(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY);
        CloudStorageConfig config = JSONUtil.toBean(configStr , CloudStorageConfig.class);
        // sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuAbstractCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunAbstractCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            return new QcloudAbstractCloudStorageService(config);
        }

        return null;
    }

}
