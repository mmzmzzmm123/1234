import request from "../request";

// 基础url
let bashRequestUrl = "/api/staffTrends";

let selectUrl = "/select";
let selectStaffTrendsUrl = "/selectStaffTrends";
let submitUrl = "/submit";
let deleteByIdUrl = "/deleteById";
let likeStaffTrendsUrl = "/likeStaffTrends";
let cancelLikeStaffTrendsUrl = "/cancelLikeStaffTrends";

/**
 * 查询店员动态
 */
let select = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 获取店员动态数据
 */
let selectStaffTrends = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectStaffTrendsUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 发布动态
 */
let submit = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + submitUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}
/**
 * 根据主键删除动态记录
 */
let deleteById = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + deleteByIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 点赞店员动态
 */
let likeStaffTrends = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + likeStaffTrendsUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

/**
 * 点赞店员动态
 */
let cancelLikeStaffTrends = function(params, onStart, onSuccess, onFailed, onWarn){
  request.get(bashRequestUrl + cancelLikeStaffTrendsUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}

module.exports = {
  select: select,
  selectStaffTrends: selectStaffTrends,
  submit: submit,
  submitUrl: bashRequestUrl+submitUrl,
  deleteById: deleteById,
  likeStaffTrends: likeStaffTrends,
  cancelLikeStaffTrends: cancelLikeStaffTrends,
}