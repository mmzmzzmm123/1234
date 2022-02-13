package com.ruoyi.common.utils;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;

import java.io.IOException;

/**
 * 浏览器UserAgent工具类
 *
 * @author Gux
 */
public class UserAgentUtils {

    UserAgentParser parser;
    String browser = "";
    String platform = "";

    public static UserAgentUtils parseUserAgentString(String userAgentString) {
        UserAgentUtils userAgentUtils = new UserAgentUtils();
        try {
            userAgentUtils.parser = new UserAgentService().loadParser();
            final Capabilities capabilities = userAgentUtils.parser.parse(userAgentString);
            userAgentUtils.browser = capabilities.getBrowser() + " " + capabilities.getBrowserMajorVersion();
            userAgentUtils.platform = capabilities.getPlatform();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return userAgentUtils;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOperatingSystem() {
        return platform;
    }

}
