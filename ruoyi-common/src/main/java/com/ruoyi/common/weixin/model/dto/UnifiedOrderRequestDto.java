package com.ruoyi.common.weixin.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LAM
 * @date 2023/4/21 23:26
 */
@Data
@Accessors(chain = true)
public class UnifiedOrderRequestDto implements Serializable {

    private static final long serialVersionUID = 5801546770601966574L;

    /**
     * <pre>
     * 字段名：商户订单号
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：string[6,32]
     * 描述：
     *  商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     *  示例值：1217752501201407033233368018
     * </pre>
     */
    @NotBlank(message = "商户订单号不能为空")
    private String outTradeNo;

    /**
     * <pre>
     * 字段名：交易结束时间
     * 变量名：time_expire
     * 是否必填：是
     * 类型：string[1,64]
     * 描述：
     *  示例值：2018-06-08 23:23:23
     * </pre>
     */
    @NotNull(message = "交易结束时间不能为空")
    private String timeExpire;

    /**
     * <pre>
     * 字段名：订单金额(分)
     * 变量名：amount
     * 是否必填：是
     * 类型：object
     * 描述：
     *  订单金额信息
     * </pre>
     */
    @NotNull(message = "订单金额不能为空")
    @Min(value = 1)
    private Integer amount;

    /**
     * <pre>
     * 字段名：用户标识
     * 变量名：openid
     * 是否必填：是
     * 类型：string[1,128]
     * 描述：
     *  用户在直连商户appid下的唯一标识。
     *  示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * </pre>
     */
    @NotBlank(message = "openid不能为空")
    private String openid;

    /**
     * <pre>
     * 字段名：用户终端IP
     * 变量名：payer_client_ip
     * 是否必填：是
     * 类型：string[1,45]
     * 描述：
     *  用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。
     *  示例值：14.23.150.211
     * </pre>
     */
    @NotBlank(message = "用户ip不能为空")
    private String payerClientIp;

    /**
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值： 腾讯充值中心-QQ会员充值
     * 描述：商品简单描述，该字段须严格按照规范传递，具体请见参数规定
     */
    @NotBlank(message = "")
    protected String body;

    /**
     * 字段名：商品详情.
     * 变量名：detail
     * 是否必填：否
     * 类型：String(6000)
     * 示例值： {  "goods_detail":[
     * {
     * "goods_id":"iphone6s_16G",
     * "wxpay_goods_id":"1001",
     * "goods_name":"iPhone6s 16G",
     * "goods_num":1,
     * "price":528800,
     * "goods_category":"123456",
     * "body":"苹果手机"
     * },
     * {
     * "goods_id":"iphone6s_32G",
     * "wxpay_goods_id":"1002",
     * "goods_name":"iPhone6s 32G",
     * "quantity":1,
     * "price":608800,
     * "goods_category":"123789",
     * "body":"苹果手机"
     * }
     * ]
     * }
     * 描述：商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
     * goods_detail []：
     * └ goods_id String 必填 32 商品的编号
     * └ wxpay_goods_id String 可选 32 微信支付定义的统一商品编号
     * └ goods_name String 必填 256 商品名称
     * └ goods_num Int 必填 商品数量
     * └ price Int 必填 商品单价，单位为分
     * └ goods_category String 可选 32 商品类目Id
     * └ body String 可选 1000 商品描述信息
     */
    @NotBlank(message = "商品描述不能为空")
    protected String description;

    /**
     * <pre>
     * 字段名：附加数据
     * 变量名：attach
     * 是否必填：否
     * 类型：string[1,128]
     * 描述：
     *  附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     *  示例值：自定义数据
     * </pre>
     */
    @SerializedName(value = "attach")
    protected String attach;
}
