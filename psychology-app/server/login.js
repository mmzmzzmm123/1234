import httprequest from "./httpRequest";
export default {
  getCode: async (account) => {
    let res = await httprequest.post("/app/login/code/get", { account });

    if (res.code == 200) {
      //   uni.showToast({
      //     title: res.data.msg,
      //   });
      return 1;
    } else {
      uni.showToast({
        title: res.data.msg,
      });
    }
  },
  loginByPhone: async (account, validStr) => {
    let res = await httprequest.post("/app/login/code/verify", {
      account,
      validStr,
    });
    if (res.code == 200) {
      console.log(res);
      //   uni.showToast({
      //     title: res.data.msg,
      //   });
      //没有openid，获取用户信息
      uni.setStorageSync("type", res.data.openIdFlag);
      uni.setStorageSync("token", res.data.token);
      return 1;
    } else {
      //   uni.showToast({
      //     title: res.data.msg,
      //   });
      return res.data.msg;
    }
  },
};
