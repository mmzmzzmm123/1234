import { wxLoginCallBack, wxLogin } from "@/server/wxApi";
export const clientTypeObj = { wx: "JSAPI", zfb: "FWC", no: "unknow" };
export default {
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
  loginWx(redirectUri) {
    uni.setStorageSync("redirectUri", redirectUri);
    if (this.getClientType() !== clientTypeObj.wx) return;
    //未绑定微信
    wxLogin().then((res) => {
      if (res.code == 200) {
        window.location.href = res.data;
      }
    });
  },
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
