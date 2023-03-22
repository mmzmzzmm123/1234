import configdata from "../common/config";
import cache from "../common/cache";

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
          succ.call(self, result.data);
        },
        fail: function (e) {
          error.call(self, e);
        },
      });
    });
  },
};
