import request from "../request";

// 基础url
let bashRequestUrl = "/api/payment";

let rechargeUrl = "/recharge";
let selectPaymentOrderBodyUrl = "/selectPaymentOrderBody";

/**
 * 充值金额
 */
let recharge = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + rechargeUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 查询打赏支付单body数据
 */
let selectPaymentOrderBody = function(params, onSuccess){
  request.get(bashRequestUrl + selectPaymentOrderBodyUrl, params, null, onSuccess, null, null, null);
}

module.exports = {
  recharge: recharge,
  selectPaymentOrderBody: selectPaymentOrderBody
}