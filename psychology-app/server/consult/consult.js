import httprequest from "../httpRequest";
export default {
    //获取课程详情
    getConsultInfo: async (consultId) => {
      let res = await httprequest.get(`/app/consult/` + consultId);
      if (res.code == 200) {
        return res.data;
      } else {
        uni.showToast({
          icon: "error",
          title: "获取咨询师详情出错",
        });
      }
    },
    getConsultServe: async (consultId) => {
      let res = await httprequest.get(`/app/consult/serve/` + consultId);
      if (res.code == 200) {
        return res.data;
      } else {
        uni.showToast({
          icon: "error",
          title: "获取咨询师服务出错",
        });
      }
    },
};
