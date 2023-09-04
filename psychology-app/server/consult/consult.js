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
    getConsultWorksById: async (id) => {
        let res = await httprequest.get(`/app/consult/getConsultWorksById/` + id);
        if (res.code == 200) {
            return res.data;
        } else {
            uni.showToast({
                icon: "error",
                title: "获取咨询师信息出错",
            });
        }
    },
    getConsultCourseByName: async (name) => {
        let res = await httprequest.post("/app/course/list", {userName: name});
        if (res.code == 200) {
            return res.rows;
        } else {
            return [];
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
