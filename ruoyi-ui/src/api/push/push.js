import request from '@/utils/request'
import MD5 from "js-md5"
/**
 * 盐
 */
const ss = "8489A0AC-7EF1-273B-41AE-71F784B7951A";
/**
 * 拉取推送消息
 * @param {*} lastTime 最后拉取的时间
 * @returns 
 */
export function pullSelf(lastTime) {
    return new Promise((resolve, reject) => {
        request({
            url: '/push/pullSelf',
            method: 'get',
            params: { lastTime }
        }).then((res) => {
            let ds = res.data ? res.data : [];
            let ds_ = [];
            let m = "";
            for (let d of ds) {
                m = MD5(d.c + d.b + d.d + ss);
                if (m == d.s) {
                    ds_.push(d);
                }
            }
            resolve(ds_);
        }).catch((error) => { reject(error) });
    });
}

export const CMD = {
    PULL_SITEMSG: "PULL_SITEMSG", // 拉取站内消息
    PULL_SITECOUNT: "PULL_SITECOUNT", // 拉取最新未读数量
    GLOBAL_NOTICE: "GLOBAL_NOTICE", // 全局通知
    PULL_LAST_TIME: "PULL_LAST_TIME" // 最后拉取时间
}