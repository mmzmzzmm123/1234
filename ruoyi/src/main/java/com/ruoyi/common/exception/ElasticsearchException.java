package com.ruoyi.common.exception;


/**
 * es异常
 *
 * @author lihe
 */
public class ElasticsearchException extends RuntimeException {

    private String errmsg;

    public String getErrmsg() {
        return errmsg;
    }

    public ElasticsearchException(String message) {
        super(message);
    }

    public ElasticsearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticsearchException(Throwable cause) {
        super(cause);
    }

    public ElasticsearchException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
