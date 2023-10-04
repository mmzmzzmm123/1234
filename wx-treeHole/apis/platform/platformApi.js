import request from "../request";

// 基础url
let bashRequestUrl = "/api/platform";

let selectTextContentByTypeUrl = "/selectTextContentByType";
let selectGiftListUrl = "/selectGiftList";
let selectRechargeConfigUrl = "/selectRechargeConfig";

/**
 * 根据类型获取平台文本内容
 */
let selectTextContentByType = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectTextContentByTypeUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 查询礼物列表
 */
let selectGiftList = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectGiftListUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 查询充值配置
 */
let selectRechargeConfig = function(onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + selectRechargeConfigUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

  module.exports = {
    selectTextContentByType: selectTextContentByType,
    selectGiftList: selectGiftList,
    selectRechargeConfig: selectRechargeConfig
  }