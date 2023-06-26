import httprequest from "../httpRequest";
export default {
  //根据字典类型获取banner图
  getBannerList: async (type) => {
    let res = await httprequest.get("/app/consultant/banner/list", {
      type,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  //获取热门推荐/精选测评
  getcourseByLabel: async (courseLabel) => {
    let res = await httprequest.post("/app/consultant/class/list", {
      courseLabel,
    });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  //获取配置
  getConfigByType: async (dictType) => {
    let res = await httprequest.get("/app/consultant/config/getConfigByType/" + dictType);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
  getAttrs: async (dictTypes) => {
    console.log(dictTypes)
    let res = await httprequest.get("/app/consultant/config/getConfigByTypes/" + dictTypes);
    if (res.code == 200) {
      return res.data;
    } else {
      return [];
    }
  },
};
