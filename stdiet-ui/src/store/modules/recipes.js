import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import { getRecipesPlan } from "@/api/custom/recipesPlan";
import { getRecipes } from "@/api/custom/recipes";

const oriState = {
  healthyData: {},
  healthyDataType: 0,
  recipesData: []
};

const mutations = {
  setHealtyData(state, payload) {
    state.healthyDataType = payload.healthyDataType;
    state.healthyData = payload.healthyData;
  },
  setRecipesData(state, payload) {
    state.recipesData = payload.recipesData;
  },
  clean(state) {
    // console.log("clean");
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

    // 健康数据
    const healthyDataResult = await getCustomerPhysicalSignsByCusId(
      orderResult.data.cusId
    );
    if (healthyDataResult.code === 200) {
      commit("setHealtyData", {
        healthyDataType: healthyDataResult.data.type,
        healthyData: dealHealthy(healthyDataResult.data.customerHealthy)
      });
    } else {
      throw new Error(healthyDataResult.msg);
    }

    // 食谱数据
    if (payload.recipesId) {
      const recipesDataResult = await getRecipes(payload.recipesId);
      if (recipesDataResult.code === 200) {
        commit("setRecipesData", {
          recipesData: recipesDataResult.data
        });
      } else {
        throw new Error(recipesDataResult.msg);
      }
    }
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
