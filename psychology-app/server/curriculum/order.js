import httprequest from "../httpRequest";
export default {
  //根据课程信息生成订单
  orderAdd: async (userId, courseId, amount) => {
    let res = await httprequest.put("/app/course/order/add", {
      userId,
      courseId,
      amount,
    });
    if (res.code == 200) {
      return res;
    } else {
      uni.showToast({
        icon: "error",
        title: "生成订单出错！",
      });
    }
  },
  //根据用户ID获取订单
  getOrderList: async (userId) => {
    let res = await httprequest.post("/app/course/order/list", {
        userId,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  // 根据用户ID和订单状态获取订单
  getOrderListByStatus: async (userId, status) => {
    let res = await httprequest.post("/app/course/order/list", {
        userId,
		status
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  //根据订单获取订单详情
  getOrderDetail: async (orderId) => {
    let res = await httprequest.post("/app/course/order/detail", {
        orderId,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  changeOrderStatus:async ()=>{
    let res = await httprequest.post("/app/gauge/order/changeOrderStatus");
    if (res.code == 200) {
      return res.data;
    } else {
      uni.showToast({
        icon: "error",
        title: "获取数量出错",
      });
    }
  }
};
