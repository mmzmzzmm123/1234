import {wxLogin, wxLoginCallBack, checkUserLogin} from "@/server/wxApi";
import wxJS from "@/server/wxJS.js"

const redis = "psy";
export const clientTypeObj = { wx: "JSAPI", zfb: "FWC", no: "unknow" };
export default {
  /**
   * 设置
   * k 键key
   * v 值value
   * t 秒
   */
  put(k, v, t) {
    uni.setStorageSync(k, v)
    const seconds = parseInt(t);
    if (seconds > 0) {
      let newtime = Date.parse(new Date());
      newtime = newtime / 1000 + seconds;
      uni.setStorageSync(k + redis, newtime + "")
    } else {
      uni.removeStorageSync(k + redis)
    }
  },
  /**
   * 获取
   * k 键key
   */
  get(k) {
    const deadtime = parseInt(wx.getStorageSync(k + redis));
    if (deadtime) {
      if (parseInt(deadtime) < Date.parse(new Date()) / 1000) {
        uni.removeStorageSync(k);
        console.log("过期了")
        return null
      }
    }
    const res = uni.getStorageSync(k);
    if (res) {
      return res
    } else {
      return null
    }
  },
  //清除所有key
  clear() {
    uni.clearStorageSync()
  },
  //删除key
  remove(k) {
    uni.removeStorageSync(k)
    uni.removeStorageSync(k + redis)
  },

  getUserInfo() {
    const user = uni.getStorageSync("userInfo");
    if (this.getClientType() === 'JSAPI') {
      return user
    }
    return user ? user.userId ? user : JSON.parse(user) : {}
  },
  async checkLogin() {
    const user = this.getUserInfo()
    if (user && user !== {} && user.userId) {
      const res = await checkUserLogin()
      if (res.code === 200) {
        return true
      }
    }
    return false
  },
  share(title, desc, img, link = window.location.href, url = window.location.href) {
    img = img ? img : 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM7ibjK0zgMSEgozDR40A2CgKLqZxEmcDI9HlQsVD27pM38F5KCDHTEaGeTfh3yWnHVgKPfzf8oKPLLITtib7xWLyMicAd1C3icfE1Q/64'

    wxJS.getConfig(title, desc, link, img, url);
  },
  getParam(path, name) {
    var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
    if (reg.test(path)) return unescape(RegExp.$2.replace(/\+/g, " "));
    return "";
  },
  //用来区分设备和浏览器
  getClientType() {
    let ua = window.navigator.userAgent;
    if (ua.match(/MicroMessenger/i) == "MicroMessenger") {
      //判断是不是微信内置浏览器
      return clientTypeObj.wx;
    } else if (ua.match(/AlipayClient/i) == "AlipayClient") {
      //判断是支付宝内置浏览器
      return clientTypeObj.zfb;
    } else {
      return clientTypeObj.no;
    }
  },
  // 调用后端接口,返回 微信登录url,重定向发起登录
  async loginWx(redirectUri) {
	uni.setStorageSync("redirectUri", redirectUri);
	if (this.getClientType() !== clientTypeObj.wx) return;
	//未绑定微信
	const res = await wxLogin({
        redirectState: uni.getStorageSync('redirectState') || 'ok',
		redirectUri: location.origin + location.pathname // 回调地址
	})
	if (res.code == 200) {
		window.location.href = res.data;
	}
    
  },
  // 登录后,页面内调用回调,保存登录状态
  async loginCallback(redirectUri) {
    //是否登录返回
    if (!uni.getStorageSync("wxLogining")) {
      return;
    }
    //从微信登录返回
    let code = this.getParam(window.location.href, "code");
    if (code) {
      uni.setStorageSync("wxLogining", false);
      let res = await wxLoginCallBack(code);
      if (res.code == 200) {
        uni.setStorageSync("userInfo", res.data);
        uni.setStorageSync("token", res.data.token);
        return true;
      } else {
        uni.showToast({
          icon: "error",
          title: "微信授权失败！",
        });
      }
      return false;
    }
    return false;
  },
};
