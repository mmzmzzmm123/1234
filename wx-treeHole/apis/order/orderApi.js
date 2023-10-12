import request from "../request";

// 基础url
let bashRequestUrl = "/api/order";

let giftOrderSubmitUrl = "/giftOrderSubmit";
let rewardOrderSubmitUrl = "/rewardOrderSubmit";
let appointOrderSubmitUrl = "/appointOrderSubmit";
let orderCancelUrl = "/orderCancel";
let payAgainUrl = "/payAgain";
let pageUrl = "/page";
let orderFinishUrl = "/orderFinish";
let orderTakingUrl = "/orderTaking";
let randomOrderTakingUrl = "/randomOrderTaking";
let orderStartServiceUrl = "/orderStartService";
let randomOrderSubmitUrl = "/randomOrderSubmit";
let selectServedUserIdUrl = "/selectServedUserId";

/**
 * 礼物赠送订单提交
 */
let giftOrderSubmit = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + giftOrderSubmitUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 打赏订单提交
 */
let rewardOrderSubmit = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + rewardOrderSubmitUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 指定单提交
 */
let appointOrderSubmit = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + appointOrderSubmitUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 随机单提交
 */
let randomOrderSubmit = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + randomOrderSubmitUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 订单取消
 */
let orderCancel = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + orderCancelUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 订单完成
 */
let orderFinish = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + orderFinishUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 订单再次支付
 */
let payAgain = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + payAgainUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 分页查询
 */
let page = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + pageUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 店员接单
 */
let orderTaking = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + orderTakingUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 店员随机单接单
 */
let randomOrderTaking = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + randomOrderTakingUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 店员开始服务
 */
let orderStartService = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + orderStartServiceUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 查询服务过的用户id集合
 */
let selectServedUserId = function(onSuccess){
  request.get(bashRequestUrl + selectServedUserIdUrl, null, null, onSuccess, null, null, null);
}



module.exports = {
  giftOrderSubmit: giftOrderSubmit,
  rewardOrderSubmit: rewardOrderSubmit,
  appointOrderSubmit: appointOrderSubmit,
  orderCancel: orderCancel,
  payAgain: payAgain,
  page: page,
  orderFinish: orderFinish,
  orderTaking: orderTaking,
  randomOrderTaking: randomOrderTaking,
  orderStartService: orderStartService,
  randomOrderSubmit: randomOrderSubmit,
  selectServedUserId: selectServedUserId
}