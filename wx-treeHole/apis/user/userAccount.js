import request from "../request";

// 基础url
let bashRequestUrl = "/api/userAccount";

let selectByUserIdUrl = "/selectByUserId";
let addUrl = "/add";
let updateUrl = "/update";
let deleteByIdUrl = "/deleteById";

/**
 * 获取用户账号列表
 */
let selectByUserId = function (params, onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectByUserIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}

/**
 * 新增用户账号
 */
let add = function(params, onStart, onSuccess, onFailed, onWarn){
  request.post(bashRequestUrl + addUrl, params, onStart, onSuccess, onFailed, onWarn, onFailed);
}
/**
 * 更新用户账号
 */
let update = function(params, onStart, onSuccess, onFailed){
  request.post(bashRequestUrl + updateUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}
/**
 * 新增用户账号
 */
let deleteById = function(params, onStart, onSuccess, onFailed){
  request.post(bashRequestUrl + deleteByIdUrl, params, onStart, onSuccess, onFailed, onFailed, onFailed);
}


module.exports = {
  selectByUserId: selectByUserId,
  add: add,
  update: update,
  deleteById: deleteById
}