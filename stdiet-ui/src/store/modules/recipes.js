import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import {
  getRecipesApi,
  updateDishesDetailApi,
  addDishesApi,
  deleteDishesApi,
  addRecipesApi
} from "@/api/custom/recipes";
import { getDicts } from "@/api/system/dict/data";
import dayjs from "dayjs";

const oriState = {
  cusId: undefined,
  planId: undefined,
  recipesId: undefined,
  healthyData: {},
  healthDataLoading: false,
  healthyDataType: 0,
  recipesData: [],
  recipesDataLoading: false,
  cusUnitOptions: [],
  cusWeightOptions: [],
  dishesTypeOptions: [],
  typeOptions: [],
  currentDay: -1,
  startDate: "",
  endDate: ""
};

const mutations = {
  updateRecipesDishesDetail(state, payload) {
    const tarDishes = state.recipesData[payload.num].dishes.find(
      obj => obj.dishesId === payload.dishesId
    );
    if (tarDishes) {
      const tarIgd = tarDishes.igdList.find(obj => obj.id === payload.igdId);
      if (tarIgd) {
        Object.keys(payload).forEach(key => {
          tarIgd[key] = payload[key];
        });
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
    ].dishes.filter(obj => obj.id !== payload.dishesId);
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
    const orderResult = await getOrder(payload.cusId);
    if (!orderResult.data.cusId) {
      throw new Error("未找到用户id");
    }

    commit("updateStateData", {
      ...payload,
      cusId: orderResult.data.cusId
    });
    //
    getDicts("cus_cus_unit").then(response => {
      commit("updateStateData", { cusUnitOptions: response.data });
    });
    getDicts("cus_cus_weight").then(response => {
      commit("updateStateData", { cusWeightOptions: response.data });
    });
    getDicts("cus_dishes_type").then(response => {
      commit("updateStateData", { typeOptions: response.data });
    });
    getDicts("cus_dishes_type").then(response => {
      commit("updateStateData", { dishesTypeOptions: response.data });
    });

    //
    if (orderResult.data.cusId) {
      dispatch("getHealthyData", { cusId: orderResult.data.cusId });
    }

    // 食谱数据
    if (payload.recipesId) {
      dispatch("getRecipesInfo", payload);
    }
  },
  async getHealthyData({ commit }, payload) {
    commit("updateStateData", { healthDataLoading: true });
    // 健康数据
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
  async getRecipesInfo({ commit }, payload) {
    commit("updateStateData", {
      recipesDataLoading: true
    });
    const recipesDataResult = await getRecipesApi(payload.recipesId);
    let recipesData = undefined;
    if (recipesDataResult.code === 200) {
      recipesData = recipesDataResult.data.map(dayData => {
        return {
          id: dayData.id,
          numDay: dayData.numDay,
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
                      cusUnit: tarDetail ? tarDetail.cus_unit : igdData.cusUnit,
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
        };
      });
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
        date: dayjs(state.startDate)
          .add(idx, "day")
          .format("YYYY-MM-DD"),
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
      dispatch("getRecipesInfo", { recipesId: result.data });
    }
    // console.log(params);
  },
  async addDishes({ commit, state }, payload) {
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
      } else {
        commit("addRecipesDishes", payload);
      }
      // console.log(result);
    }
  },
  async updateDishes({ commit, state }, payload) {
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
      } else {
        commit("updateRecipesDishesDetail", payload);
      }
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
