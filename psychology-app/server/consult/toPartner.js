import httprequest from "../httpRequest";

export default {
    getInfo: async () => {
        let res = await httprequest.post(`/app/consult/partner/getInfo`);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
    draft: async () => {
        let res = await httprequest.post(`/app/consult/partner/draft`);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
    save: async (data) => {
        let res = await httprequest.post(`/app/consult/partner/save`, data);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
    addItem: async (data) => {
        let res = await httprequest.post(`/app/consult/partner/addItem`, data);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
    editItem: async (data) => {
        let res = await httprequest.post(`/app/consult/partner/editItem`, data);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
    delItem: async (id) => {
        let res = await httprequest.post(`/app/consult/partner/delItem/` + id);
        if (res.code == 200) {
            return res;
        } else {
            return [];
        }
    },
};
