import request from "../request";

// 基础url
let bashRequestUrl = "/api/user";

let wxMiniLoginUrl = "/wxMiniLogin";
let updateUrl = "/update";
let generateReferralCodeUrl = "/generateReferralCode";
let selectUserLikeDataUrl = "/selectUserLikeData";
let getNewUserInfoUrl = "/getNewUserInfo";
let likeStaffUrl = "/likeStaff";
let cancelLikeStaffUrl = "/cancelLikeStaff";

/**
 * 用户登录
 */
let wxMiniLogin = function (params, onStart, onSuccess, onFailed) {
  request.post(bashRequestUrl + wxMiniLoginUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}
/**
 * 用户信息更新
 */
let update = function (params, onStart, onSuccess, onFailed) {
  request.post(bashRequestUrl + updateUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 生成用户推荐码
 */
let generateReferralCode = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + generateReferralCodeUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 获取用户点赞收藏记录数据
 */
let selectUserLikeData = function(onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + selectUserLikeDataUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 获取最新的用户信息
 */
let getNewUserInfo = function(onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + getNewUserInfoUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 用户关注店员
 */
let likeStaff = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + likeStaffUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 用户取消关注店员
 */
let cancelLikeStaff = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + cancelLikeStaffUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

module.exports = {
  wxMiniLogin: wxMiniLogin,
  update: update,
  generateReferralCode: generateReferralCode,
  selectUserLikeData: selectUserLikeData,
  getNewUserInfo: getNewUserInfo,
  likeStaff: likeStaff,
  cancelLikeStaff: cancelLikeStaff
}