import request from "../request";

// 基础url
let bashRequestUrl = "/api/userLevel";

// 获取用户等级配置数据
let selectUserLevelConfigUrl = "/selectUserLevelConfig";

/**
 * 获取用户等级配置数据
 */
let selectUserLevelConfig = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectUserLevelConfigUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}


module.exports = {
  selectUserLevelConfig: selectUserLevelConfig
}