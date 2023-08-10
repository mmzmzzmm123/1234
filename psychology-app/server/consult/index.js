import httprequest from "../httpRequest";
export default {
  //根据字典类型获取banner图
  getBannerList: async (type) => {
    let res = await httprequest.get("/app/consult/banner/list", {
      type,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  //获取咨询师
  getConsult: async (data, pageNum, pageSize) => {
    let res = await httprequest.post(`/app/consult/search?pageNum=${pageNum}&pageSize=${pageSize}`, data);
    return res
  },
  //获取配置
  getConfigByType: async (dictType) => {
    let res = await httprequest.get("/app/consult/config/getConfigByType/" + dictType);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getTypes: async () => {
    let res = await httprequest.get("/app/consult/config/getTrees");
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getAttrs: async (dictTypes) => {
    let res = await httprequest.get("/app/consult/config/getConfigByTypes/" + dictTypes);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getDates: async (num) => {
    let res = await httprequest.get("/app/consult/config/getDateNum/" + num);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
};
