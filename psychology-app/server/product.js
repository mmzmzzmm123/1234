import httprequest from "./httpRequest";
export default {
  //获取测评详情
  getProductInfo: async (id) => {
    let res = await httprequest.post("/app/home/gauge/getInfo", {
      id,
    });
    console.log(res, "&&&&&&&&&&&&&&&&&&&&&");
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        title: "获取详情出错",
      });
    }
  },
};
