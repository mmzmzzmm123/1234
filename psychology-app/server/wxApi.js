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
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

