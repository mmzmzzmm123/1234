import request from "../request";

// 基础url
let bashRequestUrl = "/api/server";

let selectUrl = "/select";

/**
 * 查询服务全数据
 */
let select = function (onStart, onSuccess, onFailed) {
  request.get(bashRequestUrl + selectUrl, null, onStart, onSuccess, onFailed, onFailed, onFailed);
}


module.exports = {
  select: select
}