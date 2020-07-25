package com.muster.logic.BO;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-04.
 *
 * @author 刘江涛.
 */
public class DaoContext {

    public DaoContext() {
    }

    public DaoContext(final String appId, final String bizId, final String uhId, final int userId, final int token) {
        this.appId = appId;
        this.bizId = bizId;
        this.uhId = uhId;
        this.userId = userId;
        this.token = token;
    }

    /**
     * 系统级设定appid
     */
    private String appId = "10001";

    /**
     * 系统级设定bizid 原则上 fx_bizid>0 时，将无权注册企业
     */
    private String bizId = "0";

    /**
     * 用户操作的终端硬件参数
     */
    private String uhId = "test";

    /**
     * 用户id
     */
    private int userId = 1;

    /**
     * 用户token
     */
    private int token = 1;

    /**
     * Gets appId.
     *
     * @return the appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Sets appId.
     *
     * @param appId the appId
     */
    public void setAppId(final String appId) {
        this.appId = appId;
    }

    /**
     * Gets bizId.
     *
     * @return the bizId
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * Sets bizId.
     *
     * @param bizId the bizId
     */
    public void setBizId(final String bizId) {
        this.bizId = bizId;
    }

    /**
     * Gets uhId.
     *
     * @return the uhId
     */
    public String getUhId() {
        return uhId;
    }

    /**
     * Sets uhId.
     *
     * @param uhId the uhId
     */
    public void setUhId(final String uhId) {
        this.uhId = uhId;
    }

    /**
     * Gets userId.
     *
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets userId.
     *
     * @param userId the userId
     */
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public int getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(final int token) {
        this.token = token;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DaoContext{");
        sb.append("appId='").append(appId).append('\'');
        sb.append(", bizId='").append(bizId).append('\'');
        sb.append(", uhId='").append(uhId).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", token=").append(token);
        sb.append('}');
        return sb.toString();
    }
}
