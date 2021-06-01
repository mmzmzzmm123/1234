import {
  fetchTopicList,
  postTopicReply,
  fetchTopicDetail,
  postTopicComment
} from "@/api/custom/message";

const oriState = {
  topicList: [],
  detailData: {},
  selTopicId: ""
};

const mutations = {
  updateSelTopicId(state, payload) {
    state.selTopicId = payload.selTopicId;
  },
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
  async init({ rootGetters, commit, dispatch }, payload) {
    const {
      roles: [role],
      userId
    } = rootGetters;
    const result = await fetchTopicList({ role, uid: userId });
    if (result.code === 200) {
      const [defTopic] = result.rows;

      dispatch("fetchTopicDetailActions", {
        topicId: defTopic.topicId,
        id: defTopic.id
      });

      commit("save", {
        topicList: result.rows
      });
    }
  },
  async fetchTopicDetailActions({ commit }, payload) {
    const { topicId, id } = payload;
    commit("save", { selTopicId: topicId });
    const result = await fetchTopicDetail({ topicId, id });
    if (result.code === 200) {
      commit("save", { detailData: result.data[0] });
    }
  },
  async postTopicReplyActions(
    { commit, rootGetters, dispatch, state },
    payload
  ) {
    const {
      roles: [role],
      userId
    } = rootGetters;
    const { detailData, topicList } = state;
    const params = { ...payload, fromRole: role, fromUid: userId };

    const result = payload.commentId
      ? await postTopicReply(params)
      : await postTopicComment(params);
    if (result.code === 200) {
      const tarTopic = topicList.find(
        obj => obj.topicId === detailData.topicId
      );
      if (tarTopic) {
        dispatch("fetchTopicDetailActions", {
          topicId: tarTopic.topicId,
          id: tarTopic.id
        });
      }
    }
    return result;
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
