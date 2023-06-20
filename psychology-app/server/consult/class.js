import httprequest from "../httpRequest";
export default {
  //根据字典类型获取banner图
  getClassList: async (bannerType) => {
    let res = await httprequest.get("/app/consultant/class/list");
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  getcourseByClassId: async (type) => {
    let res = await httprequest.post("/app/course/list", { type });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
  getcourseByTitle: async (title) => {
    let res = await httprequest.get("/app/home/gauge/list", { title });
    if (res.code == 200) {
      return res.rows;
    } else {
      return [];
    }
  },
};
