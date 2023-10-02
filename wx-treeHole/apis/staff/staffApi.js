import request from "../request";

// 基础url
let bashRequestUrl = "/api/staff";

let selectStaffLevelConfigUrl = "/selectStaffLevelConfig";
let selectByUserIdUrl = "/selectByUserId";
let selectPhotoByUserIdUrl = "/selectPhotoByUserId";
let applyUrl = "/apply";
let updateApplyUrl = "/updateApply";

/**
 * 获取员工等级配置
 */
let selectStaffLevelConfig = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectStaffLevelConfigUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 根据用户标识查询员工信息
 */
let selectByUserId = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectByUserIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 根据用户标识查询员工相册
 */
let selectPhotoByUserId = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectPhotoByUserIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 修改申请数据
 */
let updateApply = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + updateApplyUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}



module.exports = {
  selectStaffLevelConfig: selectStaffLevelConfig,
  selectByUserId: selectByUserId,
  selectPhotoByUserId: selectPhotoByUserId,
  updateApply: updateApply,
  applyUrl: bashRequestUrl+applyUrl,
  updateApplyUrl: bashRequestUrl+updateApplyUrl
}