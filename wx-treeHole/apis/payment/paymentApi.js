import request from "../request";

// 基础url
let bashRequestUrl = "/api/payment";

let rechargeUrl = "/recharge";

/**
 * 充值金额
 */
let recharge = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + rechargeUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

  module.exports = {
    recharge: recharge
  }