import utils from "@/utils/common";

async function login() {
	let redirectUri = location.origin + location.pathname + "?t=" + new Date().getTime()
	let userInfo = uni.getStorageSync("userInfo")
	//从微信登录返回会有code
	let code = utils.getParam(window.location.href, "code");
	if (!userInfo && !code) {
	  userInfo = {};//防止为null报错
	  //添加登录标志,为callback做返回判断
	  uni.setStorageSync("wxLogining", true);
	  await utils.loginWx(redirectUri);   
	}	
}
	
export default {
	login
}
