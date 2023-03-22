import httprequest from "../httpRequest";
export default {
   //根据用户编号获取订单
   getCourseList: async (userId) => {
    let res = await httprequest.post("/app/course/list", {
        userId,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
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
