import { pullSelf, CMD } from "@/api/push/push"

const state = {
    initOver: false,// 初始化状态
    pullIgnoreTime: 0, // 忽略执行次数
    lastTime: 0, // 最后拉取的服务器时间
    listenMap: new Map(), // 监听事件
}
const mutations = {
    CHANGE_INITOVER: (state) => {
        state.initOver = true;
    },
    CHANGE_PULLIGNORETIME: (state, pullIgnoreTime) => {
        state.pullIgnoreTime = pullIgnoreTime;
    },
    CHANGE_LASTTIME: (state, lastTime) => {
        state.lastTime = lastTime;
    }, addListen: (state, { cmd, fn }) => {
        let fns = state.listenMap.get(cmd);
        if (!fns) {
            fns = new Set();
        }
        fns.add(fn);
        state.listenMap.set(cmd, fns);
        // console.log("addPushListen--" + fns.size, cmd);
    }, removeListen: (state, { cmd, fn }) => {
        let fns = state.listenMap.get(cmd);
        if (fns) {
            fns.delete(fn);
        }
        // console.log("removePushListen--" + fns ? fns.size : 0, cmd);
    }
}

const actions = {
    init({ state, commit, dispatch, }) {
        if (state.initOver) {
            return;
        }
        // 添加更新拉取时间事件
        commit("addListen", {
            cmd: CMD.PULL_LAST_TIME,
            fn: (lastTime) => {
                dispatch("changeLastTime", lastTime);
            }
        });

        // 每15s拉取消息
        setInterval(() => {
            let t = state.pullIgnoreTime;
            // 不忽略时候执行
            if (t == 0) {
                dispatch("pullSelfMsg");
            } else {
                // 减少拉取次数
                commit("CHANGE_PULLIGNORETIME", t > 0 ? t - 1 : 0);
            }
        }, 15000);

        // 初始化完成
        commit("CHANGE_INITOVER");

    },
    // 立即拉取消息
    dispatchPullSelfMsg({ commit, dispatch }) {
        // 指定忽略执行的次数
        commit("CHANGE_PULLIGNORETIME", 1);
        // 拉取消息
        dispatch("pullSelfMsg");

    },
    // 修改最后拉取时间
    changeLastTime({ commit }, lastTime) {
        commit('CHANGE_LASTTIME', lastTime)
    },
    // 拉取消息
    pullSelfMsg({ state, rootGetters }) {
        if (rootGetters.roles.length === 0) {
            return;
        }
        let lastTime = state.lastTime;
        pullSelf(lastTime).then((res) => {
            if (res && res.length > 0) {
                let fns = null;
                for (let item of res) {
                    fns = state.listenMap.get(item.c);
                    if (fns) {
                        for (let fn of fns) {
                            try {
                                fn(item.b);
                            } catch (e) {
                                console.error(e)
                            }
                        }
                    }
                }

            }
        })
    }, addListen({ commit }, { cmd, fn }) {
        if (!CMD[cmd]) {
            return;
        }
        // 添加更新拉取时间事件
        commit("addListen", {
            cmd,
            fn
        });
    }, removeListen({ commit }, { cmd, fn }) {
        if (!CMD[cmd]) {
            return;
        }
        // 添加更新拉取时间事件
        commit("removeListen", {
            cmd,
            fn
        });
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

