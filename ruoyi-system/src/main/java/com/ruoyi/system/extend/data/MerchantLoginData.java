package com.ruoyi.system.extend.data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Jing.Zhang
 */
@Data
@Accessors(chain = true)
public class MerchantLoginData {

    private String merchantAccount;

    private String merchantPwd;
}
