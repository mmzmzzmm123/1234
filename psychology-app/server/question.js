import httprequest from "./httpRequest";
export default {
  //获取测评详情
  getQuestionList: async (gaugeId) => {
    let res = await httprequest.get("/app/gauge/questions/list", { gaugeId });
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取题目出错",
      });
    }
  },
};
