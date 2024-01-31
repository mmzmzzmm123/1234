package com.ruoyi.system.domain;

import java.io.Serializable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(tags = "内部工具，不提供出去")
public class InstrumentOpRequest implements Serializable {
    private static final long serialVersionUID = -4655630842132767753L;


    @Data
    @Api(tags = "redisFull操作")
    public static class RedisFullOp implements Serializable {
        private static final long serialVersionUID = 4194433690207524294L;
        private String method;
        private Object[] parameters;
        private String[] parameterTypes;
    }


    @Data
    @Api(tags = "redis操作")
    public static class RedisOp implements Serializable {
        private static final long serialVersionUID = 4194433690207524294L;
        @ApiModelProperty("redis方法:    get ,set,del,lrange,lpush,lpush")
        private String op;
        @ApiModelProperty("redis key")
        private String key;
        @ApiModelProperty("redis 值")
        private String value;
    }

}
