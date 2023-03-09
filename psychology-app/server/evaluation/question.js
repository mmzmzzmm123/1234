import httprequest from "../httpRequest";
export default {
  //获取测评问题
  getQuestionList: async (gaugeId, orderId) => {
    let res = await httprequest.get("/app/gauge/questions/list", {
      gaugeId,
      orderId,
    });
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取题目出错",
      });
    }
  },
  //提交答案
  setAnswer: async (gaugeId, questionsId, questionsOptionsIdList, orderId) => {
    let res = await httprequest.post("/app/gauge/result", {
      gaugeId,
      questionsId,
      questionsOptionsIdList,
      orderId,
    });
    if (res.code == 200) {
      return 1;
    } else {
      uni.showToast({
        icon: "error",
        title: "保存题目出错",
      });
    }
  },
  setResult: async (orderId) => {
    let res = await httprequest.post("/app/gauge/result/commit", {
      orderId,
    });
    if (res.code == 200) {
      return res;
    } else {
      uni.showToast({
        icon: "error",
        title: "保存题目出错",
      });
    }
  },
};
