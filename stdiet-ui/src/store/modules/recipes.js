import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import {
  getRecipesApi,
  updateDishesDetailApi,
  addDishesApi,
  deleteDishesApi,
  addRecipesApi
} from "@/api/custom/recipes";
import { getRecipesTemplateDetail } from "@/api/custom/recipesTemplate";
import { getRecipesPlan, updateRecipesPlan } from "@/api/custom/recipesPlan";
import { getDicts } from "@/api/system/dict/data";

const oriState = {
  cusId: undefined,
  planId: undefined,
  temId: undefined,
  recipesId: undefined,
  healthyData: {},
  healthDataLoading: false,
  healthyDataType: 0,
  recipesData: [],
  recipesDataLoading: false,
  cusUnitOptions: [],
  cusWeightOptions: [],
  typeOptions: [],
  currentDay: -1,
  startNum: 0,
  endNum: 0,
  reviewStatus: 0,
  templateInfo: undefined
};

const mutations = {
  updateRecipesDishesDetail(state, payload) {
    const tarDishes = state.recipesData[payload.num].dishes.find(
      obj => obj.dishesId === payload.dishesId
    );
    if (tarDishes) {
      const tarIgd = tarDishes.igdList.find(obj => obj.id === payload.igdId);
      if (tarIgd) {
        payload.weight && (tarIgd.weight = payload.weight);
        payload.cusWeight && (tarIgd.cusWeight = payload.cusWeight);
        payload.cusUnit && (tarIgd.cusUnit = payload.cusUnit);
      }
    }
  },
  addRecipesDishes(state, payload) {
    state.recipesData[payload.num].dishes.push(payload.data);
  },
  setCurrentDay(state, payload) {
    state.currentDay =
      payload.currentDay === state.currentDay ? -1 : payload.currentDay;
  },
  deleteSomeDayDishes(state, payload) {
    // console.log(payload);
    state.recipesData[payload.num].dishes = state.recipesData[
      payload.num
    ].dishes.filter(obj => obj.id !== payload.id);
  },
  updateStateData(state, payload) {
    Object.keys(payload).forEach(key => {
      state[key] = payload[key];
    });
  },
  setDate(state, payload) {
    state.startDate = payload.startDate;
    state.endDate = payload.endDate;
  },
  clean(state) {
    // console.log("clean");
    Object.keys(oriState).forEach(key => {
      state[key] = oriState[key];
    });
  }
};

const actions = {
  async init({ commit, dispatch }, payload) {
    // console.log(payload);
    const planResponse = await getRecipesPlan(payload.planId);
    const {
      startNumDay,
      endNumDay,
      recipesId,
      cusId,
      reviewStatus
    } = planResponse.data;
    commit("updateStateData", {
      cusId,
      recipesId,
      reviewStatus,
      temId: payload.temId,
      planId: payload.planId,
      startNum: startNumDay,
      endNum: endNumDay
    });
    getDicts("cus_cus_unit").then(response => {
      commit("updateStateData", { cusUnitOptions: response.data });
    });
    getDicts("cus_cus_weight").then(response => {
      commit("updateStateData", { cusWeightOptions: response.data });
    });
    getDicts("cus_dishes_type").then(response => {
      commit("updateStateData", { typeOptions: response.data });
    });

    return new Promise((res, rej) => {
      // 健康数据
      if (cusId) {
        dispatch("getHealthyData", { cusId }).catch(err => rej(err));
      }

      // 模板信息
      if (payload.temId) {
        dispatch("getRecipesTemplate", payload).catch(err => rej(err));
      }

      // 食谱数据
      if (recipesId) {
        dispatch("getRecipesInfo", { recipesId }).catch(err => rej(err));
      }
    });
  },
  async getRecipesTemplate({ commit, state }, { temId }) {
    const response = await getRecipesTemplateDetail(temId);
    if (response.code === 200) {
      commit("updateStateData", {
        templateInfo: response.data
      });
    }
  },
  async updateReviewStatus({ commit, state }, payload) {
    const response = await updateRecipesPlan({
      id: state.planId,
      reviewStatus: payload.reviewStatus
    });
    if (response.code === 200) {
      commit("updateStateData", payload);
    }
  },
  async getHealthyData({ commit }, payload) {
    commit("updateStateData", { healthDataLoading: true });
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
    commit("updateStateData", {
      healthDataLoading: false,
      healthyDataType,
      healthyData
    });
  },
  async getRecipesInfo({ commit, state }, payload) {
    commit("updateStateData", {
      recipesDataLoading: true
    });
    const recipesDataResult = await getRecipesApi(payload.recipesId);
    let recipesData = [];
    if (recipesDataResult.code === 200) {
      const { endNum, startNum, recipesId } = state;
      // 计算
      let length = endNum - startNum;
      recipesData = recipesDataResult.data.reduce((outArr, dayData, idx) => {
        if (!recipesId || length >= idx) {
          outArr.push({
            id: dayData.id,
            numDay: !recipesId ? startNum + idx : dayData.numDay,
            dishes: dayData.dishes.reduce((arr, cur) => {
              if (
                cur.dishesId > -1 &&
                cur.name &&
                cur.igdList.length > 0 &&
                cur.type !== "0"
              ) {
                arr.push({
                  id: cur.id,
                  dishesId: cur.dishesId,
                  name: cur.name,
                  menuId: cur.menuId,
                  methods: cur.methods,
                  type: cur.type,
                  isMain: cur.isMain,
                  igdList: cur.igdList.reduce((igdArr, igdData) => {
                    if (igdData.id > 0) {
                      const tarDetail = cur.detail.find(
                        obj => obj.id === igdData.id
                      );
                      igdArr.push({
                        id: igdData.id,
                        name: igdData.name,
                        carbonRatio: igdData.carbonRatio,
                        fatRatio: igdData.fatRatio,
                        proteinRatio: igdData.proteinRatio,
                        cusUnit: tarDetail
                          ? tarDetail.cus_unit
                          : igdData.cusUnit,
                        cusWeight: tarDetail
                          ? parseFloat(tarDetail.cus_weight)
                          : igdData.cusWeight,
                        weight: tarDetail
                          ? parseFloat(tarDetail.weight)
                          : igdData.weight,
                        notRec: igdData.notRec,
                        rec: igdData.rec,
                        type: igdData.type
                      });
                    }
                    return igdArr;
                  }, [])
                });
              }
              return arr;
            }, [])
          });
        }
        return outArr;
      }, []);
    } else {
      throw new Error(recipesDataResult.msg);
    }
    commit("updateStateData", { recipesDataLoading: false, recipesData });
  },
  async saveRecipes({ commit, dispatch, state }, payload) {
    const { recipesData, cusId, planId } = state;
    const params = {
      cusId,
      planId,
      menus: recipesData.map((menu, idx) => ({
        numDay: menu.numDay,
        cusId,
        dishes: menu.dishes.map(dObj => ({
          dishesId: dObj.dishesId,
          type: dObj.type,
          detail: dObj.igdList.map(igd => ({
            id: igd.id,
            weight: igd.weight,
            cus_unit: igd.cusUnit,
            cus_weight: igd.cusWeight
          }))
        }))
      }))
    };

    const result = await addRecipesApi(params);
    if (result.code === 200) {
      const recipesId = result.data;
      commit("updateStateData", { recipesId });
      dispatch("getRecipesInfo", { recipesId });
      payload.callback &&
        payload.callback({
          recipesId: result.data,
          name: state.healthyData.name,
          cusId: state.cusId,
          planId: state.planId
        });
    }
    // console.log(params);
  },
  async addDishes({ commit, state }, payload) {
    const tarDishesList = state.recipesData[payload.num].dishes.filter(
      obj => obj.type === payload.type
    );
    if (tarDishesList.some(obj => obj.dishesId === payload.dishesId)) {
      console.log("目标餐类已有相同的菜品");
      throw new Error("目标餐类已有相同的菜品");
    }
    if (state.recipesId) {
      const tarRecipesObj = state.recipesData[payload.num];
      if (tarRecipesObj && payload.data) {
        const { dishesId, type, igdList } = payload.data;
        const params = {
          type,
          dishesId,
          menuId: tarRecipesObj.id,
          detail: igdList.map(igd => ({
            id: igd.id,
            weight: igd.weight,
            cus_unit: igd.cusUnit,
            cus_weight: igd.cusWeight
          }))
        };
        const result = await addDishesApi(params);
        if (result.code === 200) {
          payload.menuId = tarRecipesObj.id;
          // 更新id
          payload.data.id = result.data;
          commit("addRecipesDishes", payload);
        }
      }
      // console.log(result);
    } else {
      commit("addRecipesDishes", payload);
    }
  },
  async updateDishes({ commit, state }, payload) {
    // console.log(payload);
    if (state.recipesId) {
      const tarDishes = state.recipesData[payload.num].dishes.find(
        obj => obj.dishesId === payload.dishesId
      );
      if (tarDishes) {
        const mTarDishes = JSON.parse(JSON.stringify(tarDishes));
        const tarIgd = mTarDishes.igdList.find(obj => obj.id === payload.igdId);
        if (tarIgd) {
          payload.weight && (tarIgd.weight = payload.weight);
          payload.cusWeight && (tarIgd.cusWeight = payload.cusWeight);
          payload.cusUnit && (tarIgd.cusUnit = payload.cusUnit);

          const params = {
            id: mTarDishes.id,
            detail: mTarDishes.igdList.map(igd => ({
              id: igd.id,
              weight: igd.weight,
              cus_unit: igd.cusUnit,
              cus_weight: igd.cusWeight
            }))
          };
          const result = await updateDishesDetailApi(params);
          if (result.code === 200) {
            commit("updateRecipesDishesDetail", payload);
          }
        }
      }
    } else {
      commit("updateRecipesDishesDetail", payload);
    }
  },
  async deleteDishes({ commit, state }, payload) {
    if (state.recipesId) {
      const tarDishes = state.recipesData[payload.num].dishes.find(
        obj => obj.id === payload.id
      );
      if (tarDishes) {
        const result = await deleteDishesApi(tarDishes.id);
        if (result.code === 200) {
          commit("deleteSomeDayDishes", payload);
        }
        // console.log(params);
      }
    } else {
      commit("deleteSomeDayDishes", payload);
    }
  },
  async deleteMenu({ commit }, payload) {}
};

const getters = {
  analyseData: state => {
    if (!state.recipesData.length) {
      return [];
    }
    const datas =
      state.currentDay > -1
        ? [state.recipesData[state.currentDay]]
        : state.recipesData;

    const nutriData = datas.map(data =>
      data.dishes.reduce(
        (obj, cur) => {
          cur.igdList.forEach(igd => {
            obj.pWeight += (igd.weight / 100) * igd.proteinRatio;
            obj.pHeat = obj.pWeight * 4;
            obj.fWeight += (igd.weight / 100) * igd.fatRatio;
            obj.fHeat = obj.fWeight * 9;
            obj.cWeight += (igd.weight / 100) * igd.carbonRatio;
            obj.cHeat = obj.cWeight * 4;
          });
          return obj;
        },
        {
          name: `第${data.numDay}天`,
          pWeight: 0,
          fWeight: 0,
          cWeight: 0,
          pHeat: 0,
          fHeat: 0,
          cHeat: 0
        }
      )
    );
    // console.log(nutriData);
    return nutriData;
  },
  cusUnitDict: state =>
    state.cusUnitOptions.reduce((obj, cur) => {
      obj[cur.dictValue] = cur.dictLabel;
      return obj;
    }, {}),
  cusWeightDict: state =>
    state.cusWeightOptions.reduce((obj, cur) => {
      obj[cur.dictValue] = cur.dictLabel;
      return obj;
    }, {}),
  typeDict: state =>
    state.typeOptions.reduce((obj, cur) => {
      obj[cur.dictValue] = cur.dictLabel;
      return obj;
    }, {})
};

export default {
  namespaced: true,
  state: Object.assign({}, oriState),
  mutations,
  actions,
  getters
};
