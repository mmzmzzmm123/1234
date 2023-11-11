import request from "../request";

// 基础url
let bashRequestUrl = "/api/dict";

let getIfHidePrivacyItemUrl = "/getIfHidePrivacyItem";


let getIfHidePrivacyItem = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + getIfHidePrivacyItemUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

module.exports = {
  getIfHidePrivacyItem: getIfHidePrivacyItem,
}