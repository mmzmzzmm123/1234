import globalCanstanats from "../constans/globalConstant";
// let prefix = "http://localhost/dev-api";
// let uploadSingleFileUrl = "http://localhost/dev-api/api/file/uploadSingleFile";
let prefix = "https://listen.vipwei.cn/prod-api";
let uploadSingleFileUrl = "https://listen.vipwei.cn/prod-api/api/file/uploadSingleFile";

import util from "../utils/util";

let post_header = {
  "Content-Type": "application/x-www-form-urlencoded",
  "flag": "yingde"
}

let get_header = {
  'Content-Type': 'application/json',
  "flag": "yingde"
}

/**                                  post                                          */
function post(url, params, onStart, onSuccess, onFailed, onWarn, onOperate) {
  if (util.isNotEmpty(params) && util.isNotEmpty(params.idempotent)) {
    post_header.idempotent = params.idempotent;
    delete params.idempotent;
  }else{
    delete post_header.idempotent;
  }
  request(url, params, "POST", post_header, onStart, onSuccess, onFailed, onWarn, onOperate);
}
function get(url, params, onStart, onSuccess, onFailed, onWarn, onOperate) {
  if (util.isNotEmpty(params) && util.isNotEmpty(params.idempotent)) {
    get_header.idempotent = params.idempotent;
    delete params.idempotent;
  }else{
    delete get_header.idempotent;
  }
  request(url, params, "GET", get_header, onStart, onSuccess, onFailed, onWarn, onOperate);
}


/**
 * function: 封装网络请求
 * @url URL地址
 * @params 请求参数
 * @method 请求方式：GET/POST
 * @onStart 开始请求,初始加载loading等处理
 * @onSuccess 成功回调
 * @onFailed 失败回调
 */
function request(url, params, method, header, onStart, onSuccess, onFailed, onWarn, onOperate) {
  if (onStart != null) {
    onStart(); //request start
  }
  wx.request({
    url: prefix + url,
    data: removeNullProperty(params),
    method: method,
    header: header,
    success: function (res) {
      if (res.statusCode == globalCanstanats.httpState.ok) {
        let requestCode = res.data.code;
        let data = res.data;
        switch (requestCode) {
          case globalCanstanats.httpState.ok:
            onSuccess != null ? onSuccess(data) : null;
            break;
          case globalCanstanats.httpState.warn:
            onWarn != null ? onWarn(data) : null;
            break;
          case globalCanstanats.httpState.operate:
            onOperate != null ? onOperate(data) : null;
            break;
          default:
            console.warn("请求失败，错误信息", res);
            onFailed != null ? onFailed(data) : null;
            break;
        }
        return;
      }
      onFailed != null ? onFailed(data) : null;
    }, fail: function (error) {
      onFailed != null ? onFailed(error) : null;
    }, complete: function(res){
      if(res.statusCode == 404){
        wx.hideLoading({
          success: (res) => {},
        })
        wx.showToast({
          title: '请求失败',
          icon: "error"
        })
      }
    }
  })
}

/**
 * 删除值为空的字段并返回
 */
let removeNullProperty = function (params) {
  if (params == null) {
    return;
  }
  for (let key in params) {
    let value = params[key];
    let result = value !== null && value !== "" && value !== "null" && value !== "undefined";
    if (!result) {
      delete params[key];
    }
  }
  return params
}

module.exports = {
  prefix: prefix,
  post: post,
  get: get,
  uploadSingleFileUrl: uploadSingleFileUrl,
  removeNullProperty: removeNullProperty
}
