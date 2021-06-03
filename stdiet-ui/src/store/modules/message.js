import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";

import {
  fetchTopicList,
  postTopicReply,
  fetchTopicDetail,
  postTopicComment
} from "@/api/custom/message";

const oriState = {
  pageNum: 1,
  topicList: [],
  detailData: {},
  selTopicId: "",
  healthyData: {},
  healthDataLoading: false,
  healthyDataType: 0,
  avoidFoodIds: []
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
  async init({ dispatch }, payload) {
    dispatch("fetchTopicListApi", {});
  },
  async fetchTopicListApi({ dispatch, commit, rootGetters, state }, payload) {
    const {
      roles: [role],
      userId
    } = rootGetters;
    const { detailData, pageNum, topicList } = state;
    const result = await fetchTopicList({
      role,
      uid: userId,
      pageSize: 20,
      pageNum
    });
    if (result.code === 200) {
      if (!detailData.topicId) {
        // 默认展示第一个
        const [defTopic] = result.rows;
        dispatch("fetchTopicDetailActions", {
          topicId: defTopic.topicId,
          id: defTopic.id,
          uid: defTopic.uid
        });
      }
      if (result.rows.length) {
        commit("save", {
          pageNum: pageNum + 1,
          topicList: [...topicList, ...result.rows]
        });
      }
    }
  },
  async fetchTopicDetailActions({ commit, dispatch, state }, payload) {
    const { topicId, id, uid } = payload;
    const { healthyData } = state;
    commit("save", { selTopicId: topicId });
    // 客户信息
    if (healthyData.customerId !== parseInt(uid)) {
      dispatch("getHealthyData", { cusId: uid });
    }
    //
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
          id: tarTopic.id,
          uid: tarTopic.uid
        });
      }
    }
    return result;
  },
  async getHealthyData({ commit }, payload) {
    commit("save", { healthDataLoading: true });
    const healthyDataResult = await getCustomerPhysicalSignsByCusId(
      payload.cusId
    );
    let healthyData = undefined,
      healthyDataType = 0;
    if (healthyDataResult.code === 200) {
      if (!healthyDataResult.data.customerHealthy) {
        throw new Error("客户还没填写健康评估表");
      }
      healthyDataType = healthyDataResult.data.type;
      healthyData = dealHealthy(healthyDataResult.data.customerHealthy);
    } else {
      throw new Error(healthyDataResult.msg);
    }
    commit("save", {
      healthDataLoading: false,
      healthyDataType,
      healthyData,
      avoidFoodIds: (healthyData.avoidFood || []).map(obj => obj.id)
    });
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
