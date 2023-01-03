const state = {
    showMessage: false,
    readId: 0, // 监听读取的msgid
}
const mutations = {
    CHANGE_SETTING: (state, { key, value }) => {
        if (state.hasOwnProperty(key)) {
            state[key] = value
        }
    },
    CHANGE_READID: (state, readId) => {
        state.readId = readId;
    }

}

const actions = {
    // 修改站内消息时设置
    changeSetting({ commit }, data) {
        commit('CHANGE_SETTING', data)
    },
    // 修改读取id
    changeReadId({ commit }, readId) {
        commit('CHANGE_READID', readId)
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

