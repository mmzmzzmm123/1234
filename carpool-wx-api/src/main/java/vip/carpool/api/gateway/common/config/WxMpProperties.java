package vip.carpool.api.gateway.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import vip.carpool.api.gateway.common.utils.JsonUtils;

import java.util.List;

/**
 * wechat mp properties
 *
 * @author 刘荣华
 */
@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
    private List<MpConfig> configs;

    @Data
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
    }

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
