package com.ruoyi.system.domain.model.credit;

/**
 * @author renjf
 * @date 2022年06月30日 8:39
 */
public class PartnersRsaKey {

    String merchantId;
    String platformPublicKey;
    String platformPrivateKey;
    String salt;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPlatformPublicKey() {
        return platformPublicKey;
    }

    public void setPlatformPublicKey(String platformPublicKey) {
        this.platformPublicKey = platformPublicKey;
    }

    public String getPlatformPrivateKey() {
        return platformPrivateKey;
    }

    public void setPlatformPrivateKey(String platformPrivateKey) {
        this.platformPrivateKey = platformPrivateKey;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "PartnersRsaKey{" +
                "merchantId='" + merchantId + '\'' +
                ", platformPublicKey='" + platformPublicKey + '\'' +
                ", platformPrivateKey='" + platformPrivateKey + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
