import request from "../request";

// 基础url
let bashRequestUrl = "/api/staff";

// 获取员工等级配置
let selectStaffLevelConfigUrl = "/selectStaffLevelConfig";

/**
 * 获取员工等级配置
 */
let selectStaffLevelConfig = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectStaffLevelConfigUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}


module.exports = {
  selectStaffLevelConfig: selectStaffLevelConfig
}