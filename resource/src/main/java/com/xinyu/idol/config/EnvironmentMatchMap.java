package com.xinyu.idol.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "environment-match")

public class EnvironmentMatchMap {

    public Map<String, String> getDomains() {
        return domains;
    }

    public void setDomains(Map<String, String> domains) {
        this.domains = domains;
    }

    private Map<String,String> domains;

    public String getDomain(String env){
        Assert.notNull(env,"env非空");
        String domain = domains.get(env);
        Assert.notNull(domain,"domain非空");
        return domain;
    }



}
