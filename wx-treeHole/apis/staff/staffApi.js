import request from "../request";

// 基础url
let bashRequestUrl = "/api/staff";

let selectStaffLevelConfigUrl = "/selectStaffLevelConfig";
let selectByUserIdUrl = "/selectByUserId";

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

module.exports = {
  selectStaffLevelConfig: selectStaffLevelConfig,
  selectByUserId: selectByUserId
}