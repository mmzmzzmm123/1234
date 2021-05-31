import {
  fetchTopicList,
  postTopicReply,
  fetchTopicDetail,
  postTopicComment
} from "@/api/custom/message";

const oriState = {
  topicList: undefined,
  detailData: undefined
};

const mutations = {
  save(state, payload) {
    Object.keys(payload).forEach(key => {
      state[key] = payload[key];
    });
  },
  clean(state) {
    Object.keys(oriState).forEach(key => {
      state[key] = oriState[key];
    });
  }
};

const actions = {
  async init({ rootGetters, commit }, payload) {
    const {
      roles: [role],
      userId
    } = rootGetters;
    const result = await fetchTopicList({ role: "dietician", uid: 131 });
    console.log({ result });
  }
};

const getters = {};

export default {
  namespaced: "message",
  state: Object.assign({}, oriState),
  mutations,
  actions,
  getters
};
