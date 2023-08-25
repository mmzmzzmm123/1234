import httprequest from "../httpRequest";
export default {
    getIntegral: async (data) => {
        let res = await httprequest.get(`/app/user/integral/list`, data);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },

    getUserIntegral: async () => {
        let res = await httprequest.get(`/app/user/integral/getUserIntegral`);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
 
};