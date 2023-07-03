import httprequest from "../httpRequest";
export default {
  //获取订单列表
  getOrderList: async (data) => {
    let res = await httprequest.post(`/app/consult/order/getOrderList`, data);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getOrderInfo: async (id) => {
    let res = await httprequest.get(`/app/consult/order/getOrderInfo/` + id);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getConsultInfoByServe: async (id) => {
    let res = await httprequest.get(`/app/consult/order/getConsultInfoByServe/` + id);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取订单详情出错",
      });
    }
  },
  doConsult: async (id, workId) => {
    let res = await httprequest.get(`/app/consult/order/doConsult/` + id + '/' + workId);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "取消失败",
      });
    }
  },
  cancel: async (id) => {
    let res = await httprequest.get(`/app/consult/order/cancel/` + id);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "取消失败",
      });
    }
  }
};
