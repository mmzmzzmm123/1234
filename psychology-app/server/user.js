import httprequest from "./httpRequest";
export default {
  //获取测评详情
  getProductInfo: async (id) => {
    let res = await httprequest.post("/app/home/gauge/getInfo?id=" + id);
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取详情出错",
      });
    }
  },
  getOrderList: async () => {
    let res = await httprequest.get("/app/gauge/order/list");
    if (res.code == 200) {
      return res.rows;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取订单出错",
      });
    }
  },
};
