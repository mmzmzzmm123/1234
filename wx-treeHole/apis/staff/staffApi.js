import request from "../request";

// 基础url
let bashRequestUrl = "/api/staff";

let pageUrl = "/page";
let selectStaffLevelConfigUrl = "/selectStaffLevelConfig";
let selectByUserIdUrl = "/selectByUserId";
let selectPhotoByUserIdUrl = "/selectPhotoByUserId";
let applyUrl = "/apply";
let updateUrl = "/update";
let selectServiceConfigIdsUrl = "/selectServiceConfigIds";
let handleServiceIdUrl = "/handleServiceId";
let selectByStaffIdUrl = "/selectByStaffId";
let selectStaffGiftRecordIdUrl = "/selectStaffGiftRecordId";
let weeklyRankingTopThreeUrl = "/weeklyRankingTopThree";
let selectTeamMembersUrl = "/selectTeamMembers";

/**
 * 分页查询
 */
let page = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + pageUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

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
 * 修改数据
 */
let update = function (params, onStart, onSuccess, onFailed, onWarn) {
  request.post(bashRequestUrl + updateUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 获取店员配置接单服务id
 */
let selectServiceConfigIds = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + selectServiceConfigIdsUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 处理店员服务数据
 */
let handleServiceId = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + handleServiceIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 根据店员标识查询数据
 */
let selectByStaffId = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + selectByStaffIdUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 获取店员总礼物数据
 */
let selectStaffGiftRecordId = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + selectStaffGiftRecordIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 周排名前三
 */
let weeklyRankingTopThree = function(onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + weeklyRankingTopThreeUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 查询我的组员
 */
let selectTeamMembers = function(onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + selectTeamMembersUrl, null, onStart, onSuccess, onFailed, onWarn, onFailed);
}

module.exports = {
  page: page,
  selectStaffLevelConfig: selectStaffLevelConfig,
  selectByUserId: selectByUserId,
  selectPhotoByUserId: selectPhotoByUserId,
  update: update,
  applyUrl: bashRequestUrl+applyUrl,
  updateUrl: bashRequestUrl+updateUrl,
  selectServiceConfigIds: selectServiceConfigIds,
  handleServiceId: handleServiceId,
  selectByStaffId: selectByStaffId,
  selectStaffGiftRecordId: selectStaffGiftRecordId,
  weeklyRankingTopThree: weeklyRankingTopThree,
  selectTeamMembers: selectTeamMembers
}