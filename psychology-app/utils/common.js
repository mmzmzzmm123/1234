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
};
