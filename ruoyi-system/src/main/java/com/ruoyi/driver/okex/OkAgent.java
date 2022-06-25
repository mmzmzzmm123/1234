package com.ruoyi.driver.okex;

import lombok.Data;
import lombok.experimental.Accessors;

public class OkAgent {
    @Data
    @Accessors(chain = true)
    public static class Credential {
        private String apiKey;
        private String secretKey;
        private String passphrase;
    }
}
