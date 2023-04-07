import httprequest from "../httpRequest";
export default {
	//根据搜素条件查询课程列表
	getCourseList: async (searchValue) => {
	  let res = await httprequest.post(`/app/course/search?searchValue=${searchValue}`);
	  if (res.code == 200) {
	    return res.data;
	  } else {
	    return [];
	  }
	},
}