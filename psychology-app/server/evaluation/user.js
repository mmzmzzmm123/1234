import httprequest from "../httpRequest";
export default {
  getOrderList: async (data) => {
    let res = await httprequest.post(
      "/app/gauge/order/list",
	  data || {}
    );
    if (res.code == 200) {
      return res.rows;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取订单出错",
      });
    }
  },
  getOrderListNum: async () => {
    let res = await httprequest.post("/app/gauge/order/getMyReportNum");
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取数量出错",
      });
    }
  },
};
