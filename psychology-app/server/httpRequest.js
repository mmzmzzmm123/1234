import configdata from "../common/config";
import cache from "../common/cache";
import loginServer from "@/server/login.js"

function tokenExpired(result) {    
    
    uni.showModal({
      title: '提示',
      content: '由于长时间未使用，token已过期，请重新登录',
      showCancel: false,
      success: function (res) {
        // 清除本地缓存的userInfo 和 token
        uni.setStorageSync('userInfo', null)
        uni.setStorageSync('token', null)
        
        uni.redirectTo({
          url: '/pages/evaluation/index'
        })
      }
    });    
  
}

module.exports = {
  config: function (name) {
    var info = null;
    if (name) {
      var name2 = name.split("."); //字符分割
      if (name2.length > 1) {
        info = configdata[name2[0]][name2[1]] || "";
      } else {
        info = configdata[name] || "";
      }
      if (info == null) {
        let web_config = cache.get("web_config");
        if (web_config) {
          if (name2.length > 1) {
            info = web_config[name2[0]][name2[1]] || "";
          } else {
            info = web_config[name] || "";
          }
        }
      }
    }
    return info;
  },
  post: function (url, data, header) {
    header = header || "application/json;charset=UTF-8";
    url = this.config("APIHOST") + url;
    let token = uni.getStorageSync("token");
    return new Promise((succ, error) => {
      uni.request({
        url: url,
        data: data,
        method: "POST",
        header: {
          "content-type": header,
          Authorization: token,
        },
        success: function (result) { 
          if (result.data.code == 401) {
            tokenExpired(result)
            return
          }
          succ.call(self, result.data);
        },
        fail: function (e) {
          error.call(self, e);
        },
      });
    });
  },
  put: function (url, data, header) {
    header = header || "application/json;charset=UTF-8";
    url = this.config("APIHOST") + url;
    let token = uni.getStorageSync("token");
    return new Promise((succ, error) => {
      uni.request({
        url: url,
        data: data,
        method: "PUT",
        header: {
          "content-type": header,
          Authorization: token,
        },
        success: function (result) {
          succ.call(self, result.data);
        },
        fail: function (e) {
          error.call(self, e);
        },
      });
    });
  },
  get: function (url, data, header) {
    header = header || "application/x-www-form-urlencoded";
    url = this.config("APIHOST") + url;
    let token = uni.getStorageSync("token");
    return new Promise((succ, error) => {
      uni.request({
        url: url,
        data: data,
        method: "GET",
        header: {
          "content-type": header,
          Authorization: token,
        },
        success: function (result) {
          if (result.data.code == 401) {
            tokenExpired(result)
            return
          }
          succ.call(self, result.data);
        },
        fail: function (e) {
          error.call(self, e);
        },
      });
    });
  },
};
