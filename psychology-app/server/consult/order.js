import httprequest from "../httpRequest";
export default {
  //获取课程详情
  getConsultInfoByServe: async (id) => {
    let res = await httprequest.get(`/app/consult/order/getConsultInfoByServe/` + id);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取咨询师详情出错",
      });
    }
  }
};
