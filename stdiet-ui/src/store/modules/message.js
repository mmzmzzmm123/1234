import {
  getCustomerPhysicalSignsByCusId
  // getCustomer
} from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import {
  listRecipesPlanByCusId,
  updateRecipesPlan
} from "@/api/custom/recipesPlan";

import {
  fetchCustomerList,
  fetchTopicList,
  postTopicReply,
  fetchTopicDetail,
  postTopicComment
} from "@/api/custom/message";

const oriState = {
  pageNum: 1,
  cusLoading: false,
  customerList: [],
  selCusId: "",
  //
  topicList: [],
  topicLoading: false,
  selTopicId: "",
  //
  detailData: {},
  detailLoading: false,
  //
  healthyData: {},
  healthDataLoading: false,
  healthyDataType: 0,
  avoidFoodIds: [],
  //
  planList: [],
  planListLoading: false
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
    dispatch("fetchCustomerListActions", {});
  },

  async fetchCustomerListActions(
    { dispatch, commit, rootGetters, state },
    payload
  ) {
    // prettier-ignore
    const { roles: [role], userId } = rootGetters;
    const { customerList, pageNum } = state;
    commit("save", { cusLoading: true });
    const result = await fetchCustomerList({
      role,
      uid: userId,
      pageSize: 20,
      pageNum
    });
    let mPageNum = pageNum,
      mCustomerList = customerList;
    if (result.code === 200 && result.rows.length) {
      //
      if (!customerList.length) {
        const [defCustomer] = result.rows;
        dispatch("fetchTopicListApi", { fromUid: defCustomer.uid });
      }
      //
      mPageNum += 1;
      mCustomerList = [...customerList, ...result.rows];
    }
    commit("save", {
      pageNum: mPageNum,
      cusLoading: false,
      customerList: mCustomerList
    });
  },
  async fetchTopicListApi({ dispatch, commit, rootGetters, state }, payload) {
    // prettier-ignore
    const { roles: [role], userId } = rootGetters;
    const { customerList } = state;
    const { fromUid } = payload;
    const newCustomerList = JSON.parse(JSON.stringify(customerList));

    const tarIdx = newCustomerList.findIndex(obj => obj.reSort);
    if (tarIdx > -1) {
      const [tarCustomer] = newCustomerList.splice(tarIdx, 1);
      const injectIdx = newCustomerList.findIndex(obj => obj.read == 1);
      tarCustomer.reSort = false;
      if (injectIdx === -1) {
        newCustomerList.splice(newCustomerList.length, 0, tarCustomer);
      } else {
        newCustomerList.splice(injectIdx, 0, tarCustomer);
      }
    }

    commit("save", {
      selCusId: fromUid,
      topicLoading: true,
      customerList: newCustomerList
    });
    const result = await fetchTopicList({
      role,
      uid: userId,
      fromUid
    });
    let mTopicList = [];
    if (result.code === 200 && result.rows.length) {
      // 默认展示第一个
      setTimeout(() => {
        const [defTopic] = result.rows;
        dispatch("fetchTopicDetailActions", {
          topicId: defTopic.topicId,
          id: defTopic.id,
          uid: defTopic.uid,
          callback: payload.callback
        });
      }, 100);
      mTopicList = result.rows;
    }
    commit("save", {
      topicList: mTopicList,
      topicLoading: false
    });
  },
  async fetchTopicDetailActions({ commit, dispatch, state }, payload) {
    const { topicId, id = 0, uid } = payload;
    const {
      healthyData,
      planList,
      topicList,
      customerList
    } = state;
    commit("save", { selTopicId: topicId, detailLoading: true });
    // 客户信息
    if (healthyData.customerId !== parseInt(uid)) {
      dispatch("getHealthyData", { cusId: uid, callback: payload.callback });
    }
    // 食谱计划
    if (!planList.length || planList[0].cusId !== parseInt(uid)) {
      dispatch("getRecipesPlanActions", { cusId: uid });
    }
    const result = await fetchTopicDetail({ topicId, id });
    if (result.code === 200) {
      // 设置已读
      const newTopicList = JSON.parse(JSON.stringify(topicList));
      const preState = newTopicList.some(t => t.read == 0);
      newTopicList.find(obj => obj.topicId === topicId).read = 1;
      const newCutomers = JSON.parse(JSON.stringify(customerList));
      const afterState = newTopicList.some(t => t.read == 0);
      if (!afterState) {
        const tarCustomer = newCutomers.find(cus => cus.uid === uid);
        tarCustomer.read = 1;
        tarCustomer.reSort = preState;
      }
      commit("save", {
        detailData: result.data[0],
        detailLoading: false,
        topicList: newTopicList,
        customerList: newCutomers
      });
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
    const newState = { healthyData: {}, avoidFoodIds: [] };
    if (healthyDataResult.code === 200) {
      if (!healthyDataResult.data.customerHealthy) {
        // throw new Error("客户还没填写健康评估表");
        payload.callback && payload.callback("客户还没填写健康评估表");
      } else {
        newState.healthyDataType = healthyDataResult.data.type;
        newState.healthyData = dealHealthy(
          healthyDataResult.data.customerHealthy
        );
        newState.avoidFoodIds = (newState.healthyData.avoidFood || []).map(
          obj => obj.id
        );
        newState.healthyData.dietitianName =
          healthyDataResult.data.customerInfo.dietitianName;
        newState.healthyData.assDietitianName =
          healthyDataResult.data.customerInfo.assDietitianName;
        newState.healthyData.afterDietitianName =
          healthyDataResult.data.customerInfo.afterDietitianName;
      }
    }
    commit("save", {
      healthDataLoading: false,
      ...newState
    });
  },
  async getRecipesPlanActions({ commit }, payload) {
    commit("save", { planListLoading: true, planList: [] });
    const result = await listRecipesPlanByCusId(payload.cusId);
    let planList = [];
    if (result.code === 200) {
      planList = result.data;
    }
    commit("save", {
      planList,
      planListLoading: false
    });
  },
  async updateRecipesPlanActions({ commit, state }, payload) {
    const { id, sendFlag, callback } = payload;
    const { planList } = state;
    const result = await updateRecipesPlan({ id, sendFlag });
    if (result.code === 200) {
      callback && callback("success", result.msg);
      const newPlanList = JSON.parse(JSON.stringify(planList));
      const tarPlan = newPlanList.find(obj => obj.id === id);
      if (tarPlan) {
        tarPlan.sendFlag = sendFlag;
      }
      commit("save", {
        planList: newPlanList
      });
    }
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
