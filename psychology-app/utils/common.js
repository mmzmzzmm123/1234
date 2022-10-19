export default {
  getParam(path, name) {
    var reg = new RegExp("(^|\\?|&)" + name + "=([^&]*)(\\s|&|$)", "i");
    if (reg.test(path)) return unescape(RegExp.$2.replace(/\+/g, " "));
    return "";
  },
};
