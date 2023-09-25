import request from "../request";

// 基础url
let bashRequestUrl = "/api/platform";

let selectTextContentByTypeUrl = "/selectTextContentByType";

/**
 * 根据类型获取平台文本内容
 */
let selectTextContentByType = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectTextContentByTypeUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

module.exports = {
  selectTextContentByType: selectTextContentByType
}