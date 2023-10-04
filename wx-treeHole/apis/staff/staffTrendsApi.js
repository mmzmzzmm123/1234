import request from "../request";

// 基础url
let bashRequestUrl = "/api/staffTrends";

let selectStaffTrendsUrl = "/selectStaffTrends";
let submitUrl = "/submit";
let deleteByIdUrl = "/deleteById";

/**
 * 获取店员动态数据
 */
let selectStaffTrends = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectStaffTrendsUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 发布动态
 */
let submit = function(params, onStart, onSuccess, onFailed){
  request.post(bashRequestUrl + submitUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}
/**
 * 根据主键删除动态记录
 */
let deleteById = function(params, onStart, onSuccess, onFailed){
  request.get(bashRequestUrl + deleteByIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}



module.exports = {
  selectStaffTrends: selectStaffTrends,
  submit: submit,
  submitUrl: bashRequestUrl+submitUrl,
  deleteById: deleteById
}