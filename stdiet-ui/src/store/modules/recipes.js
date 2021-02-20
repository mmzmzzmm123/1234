import { getOrder } from "@/api/custom/order";
import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import { getRecipesPlan } from "@/api/custom/recipesPlan";
import {
  getRecipesApi,
  updateDishesDetailApi,
  addDishesApi,
  deleteDishesApi
} from "@/api/custom/recipes";
import { getDicts } from "@/api/system/dict/data";
import produce from "immer";
import { updateDishes } from "@/api/custom/dishes";

const oriState = {
  recipesId: undefined,
  healthyData: {},
  healthyDataType: 0,
  recipesData: [],
  cusUnitOptions: [],
  cusWeightOptions: [],
  dishesTypeOptions: [],
  typeOptions: [],
  currentDay: -1
};

const mutations = {
  setRecipiesId(state, payload) {
    state.recipesId = payload.recipesId;
  },
  setHealtyData(state, payload) {
    state.healthyDataType = payload.healthyDataType;
    state.healthyData = payload.healthyData;
  },
  setRecipesData(state, payload) {
    state.recipesData = payload.recipesData;
    // console.log(payload.recipesData);
  },
  updateRecipesDishesDetail(state, payload) {
    const tarDishes = state.recipesData[payload.num].dishes.find(
      obj => obj.id === payload.dishesId
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
  updateOptions(state, payload) {
    Object.keys(payload).forEach(key => {
      state[key] = payload[key];
    });
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
    //
    getDicts("cus_cus_unit").then(response => {
      commit("updateOptions", { cusUnitOptions: response.data });
    });
    getDicts("cus_cus_weight").then(response => {
      commit("updateOptions", { cusWeightOptions: response.data });
    });
    getDicts("cus_dishes_type").then(response => {
      commit("updateOptions", { typeOptions: response.data });
    });
    getDicts("cus_dishes_type").then(response => {
      commit("updateOptions", { dishesTypeOptions: response.data });
    });

    // 健康数据
    const healthyDataResult = await getCustomerPhysicalSignsByCusId(
      orderResult.data.cusId
    );
    if (healthyDataResult.code === 200) {
      if (!healthyDataResult.data.customerHealthy) {
        throw new Error("客户还没填写健康评估表");
      }
      commit("setHealtyData", {
        healthyDataType: healthyDataResult.data.type,
        healthyData: dealHealthy(healthyDataResult.data.customerHealthy)
      });
    } else {
      throw new Error(healthyDataResult.msg);
    }

    // 食谱数据
    if (payload.recipesId) {
      const recipesDataResult = await getRecipesApi(payload.recipesId);
      if (recipesDataResult.code === 200) {
        commit("setRecipesData", {
          recipesData: recipesDataResult.data.map(dayData => {
            return {
              id: dayData.id,
              numDay: dayData.numDay,
              dishes: dayData.dishes.reduce((arr, cur) => {
                if (
                  cur.id > -1 &&
                  cur.name &&
                  cur.igdList.length > 0 &&
                  cur.type !== "0"
                ) {
                  arr.push({
                    id: cur.id,
                    cid: cur.cid,
                    name: cur.name,
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
            };
          })
        });
      } else {
        throw new Error(recipesDataResult.msg);
      }
    }
  },
  async saveRecipes({ commit, state }, payload) {
    const { recipesData } = state;
    const params = recipesData.map(menu => {
      return menu.dishes.map(dObj => {
        return {
          dishesId: dObj.id,
          type: dObj.type,
          detail: dObj.igdList.map(igd => ({
            id: igd.id,
            weight: igd.weight,
            cus_unit: igd.cusUnit,
            cus_weight: igd.cusWeight
          }))
        };
      });
    });
    console.log(params);
  },
  async addDishes({ commit, state }, payload) {
    const tarRecipesObj = state.recipesData[payload.num];
    if (tarRecipesObj && payload.data) {
      const { id, type, igdList } = payload.data;
      const params = {
        menuId: tarRecipesObj.id,
        id: id,
        type: type,
        detail: igdList.map(igd => ({
          id: igd.id,
          weight: igd.weight,
          cus_unit: igd.cusUnit,
          cus_weight: igd.cusWeight
        }))
      };
      const result = await addDishesApi(params);
      if (result.code === 200) {
        payload.data.cid = result.data;
        commit("addRecipesDishes", payload);
      }
      console.log(result);
    }
  },
  async updateDishes({ commit, state }, payload) {
    const tarDishes = state.recipesData[payload.num].dishes.find(
      obj => obj.id === payload.dishesId
    );
    if (tarDishes) {
      const mTarDishes = JSON.parse(JSON.stringify(tarDishes));
      const tarIgd = mTarDishes.igdList.find(obj => obj.id === payload.igdId);
      if (tarIgd) {
        payload.weight && (tarIgd.weight = payload.weight);
        payload.cusWeight && (tarIgd.cusWeight = payload.cusWeight);
        payload.cusUnit && (tarIgd.cusUnit = payload.cusUnit);

        const params = {
          cid: mTarDishes.cid,
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
  },
  async deleteDishes({ commit, state }, payload) {
    const tarDishes = state.recipesData[payload.num].dishes.find(
      obj => obj.id === payload.dishesId
    );
    if (tarDishes) {
      const result = await deleteDishesApi(tarDishes.cid);
      if (result.code === 200) {
        commit("deleteSomeDayDishes", payload);
      }
      // console.log(params);
    }
  },
  async deleteMenu({ commit }, payload) {}
};

const getters = {
  analyseData: state => {
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
