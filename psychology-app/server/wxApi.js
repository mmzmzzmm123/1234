import httprequest from "./httpRequest";
const APPID = "wx8604d98104f67e66";
const APPSECRET = "98dca366602a0806da390ed138e4a7ea";

// 更新tonken
function updateToken() {
  return new Promise((resolve, reject) => {
    httprequest
      .get(
        `/weixin/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}`
      )
      .then((res) => {
        resolve(res.data.access_token);
      })
      .catch((err) => {
        reject(err);
      });
  });
}
export const wxLogin = () => {
  return new Promise((resolve, reject) => {
    httprequest
      .get(`/app/user/wxLogin`)
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};
export const wxLoginCallBack = (code) => {
  return new Promise((resolve, reject) => {
    httprequest
      .get(`/app/user/callBack?code=` + code)
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};
// 根据code获取网站授权token
export function getTokenFormCode(code) {
  return new Promise((resolve, reject) => {
    httprequest
      .get(
        `/weixin/sns/oauth2/access_token?appid=${APPID}&secret=${APPSECRET}&code=${code}&grant_type=authorization_code`
      )
      .then((res) => {
        alert(1);
        resolve(res);
      })
      .catch((err) => {
        alert(2);
        reject(err);
      });
  });
}
// 获取Tonken
export function getToken() {
  return new Promise(async (resolve, reject) => {
    // 从缓存中读取token
    let stroagetoken = uni.getStorageSync("accessToken");
    // 如果缓存中有token，则直接返回缓存中的token
    if (stroagetoken) {
      console.log("缓存获取的token");
      // 返回结果
      resolve(stroagetoken);
    } else {
      // 如果缓存没有token，则走接口重新获取新的token
      let token = await updateToken();
      // 存入到缓存中
      uni.setStorageSync("accessToken", token);
      // 设置定时任务，每隔7000秒更新一次tonken
      setInterval(async () => {
        // 获取新的token
        let token = await updateToken();
        // 存入到缓存中
        uni.setStorageSync("accessToken", token);
      }, 7000000);
      console.log("接口获取的token");
      // 返回结果
      resolve(token);
    }
  });
}
