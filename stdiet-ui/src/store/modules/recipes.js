import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import { getRecipesPlan } from "@/api/custom/recipesPlan";

const oriState = {
  healthyData: {},
  healthyDataType: 0
};

const mutations = {
  setHealtyData(state, payload) {
    state.healthyDataType = payload.healthyDataType;
    state.healthyData = payload.healthyData;
  },
  clean(state) {
    console.log("clean");
    Object.keys(oriState).forEach(key => {
      state[key] = oriState[key];
    });
  }
};

const actions = {
  async init({ commit }, payload) {
    const orderResult = await getOrder(payload.cusId);
    if (!orderResult.data.cusId) {
      throw new Error("未找到用户id");
    }

    const healthyDataResult = await getCustomerPhysicalSignsByCusId(
      orderResult.data.cusId
    );
    // 设置健康数据
    commit("setHealtyData", {
      healthyDataType: healthyDataResult.data.type,
      healthyData: dealHealthy(healthyDataResult.data.customerHealthy)
    });
  }
};

const getters = {};

export default {
  namespaced: true,
  state: Object.assign({}, oriState),
  mutations,
  actions,
  getters
};
