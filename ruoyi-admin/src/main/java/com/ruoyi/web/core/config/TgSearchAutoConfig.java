package com.ruoyi.web.core.config;

import cc.linkmaster.telegram.search.client.TelegramSearchClient;
import cc.linkmaster.telegram.search.client.TelegramSearchConfig;
import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.config.dymic.TelegramSearchConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱少波
 * @date 2024/1/29/029 9:02
 */
@Configuration
public class TgSearchAutoConfig {

    @Autowired
    private TelegramSearchConfigure configure;


    @Bean
    public TelegramSearchClient createTelegramSearchClient() {
        TelegramSearchClient telegramSearchClient = new TelegramSearchClient();
        telegramSearchClient.setGlobalConfig(BeanUtil.copyProperties(configure, TelegramSearchConfig.class));
        return telegramSearchClient;
    }

}
