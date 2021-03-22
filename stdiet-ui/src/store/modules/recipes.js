import { getCustomerPhysicalSignsByCusId } from "@/api/custom/customer";
import { dealHealthy } from "@/utils/healthyData";
import {
  getRecipesApi,
  updateDishesDetailApi,
  addDishesApi,
  deleteDishesApi,
  addRecipesApi
} from "@/api/custom/recipes";
import { getDishesMenuTypes } from "@/api/custom/dishes";
import { getRecipesTemplateDetail } from "@/api/custom/recipesTemplate";
import { getRecipesPlan, updateRecipesPlan } from "@/api/custom/recipesPlan";
import { getDicts } from "@/api/system/dict/data";

const oriState = {
  cusId: undefined,
  name: undefined,
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
  templateInfo: undefined,
  copyData: undefined,
  canCopyMenuTypes: [],
  fontSize: parseInt(localStorage.getItem("fontSize")) || 12,
  dishBigClassOptions: [],
  dishSmallClassOptions: [],
  //
  leftShow: false,
  notRecIgds: [],
  igdTypeOptions: []
};

const mutations = {
  updateRecipesDishesDetail(state, payload) {
    const { num, actionType } = payload;
    const tarDishes = state.recipesData[num].dishes.find(
      obj =>
        obj.id === (actionType === "replace" ? payload.data.id : payload.id)
    );
    if (tarDishes) {
      if (actionType === "replace") {
        tarDishes.remark = "";
        // 替换菜品,修改类型
        Object.keys(payload.data).forEach(key => {
          tarDishes[key] = payload.data[key];
        });
      } else if (actionType === "remark") {
        tarDishes.remark = payload.remark;
      } else if (actionType === "menuType") {
        tarDishes.type = payload.type;
      } else if (actionType === "weight" || actionType === "unit") {
        const tarIgd = tarDishes.igdList.find(obj => obj.id === payload.igdId);
        if (tarIgd) {
          payload.weight && (tarIgd.weight = payload.weight);
          payload.cusWeight && (tarIgd.cusWeight = payload.cusWeight);
          payload.cusUnit && (tarIgd.cusUnit = payload.cusUnit);
        }
        // console.log(JSON.parse(JSON.stringify(state.recipesData)));
      } else if (actionType === "delIgd") {
        tarDishes.igdList = tarDishes.igdList.filter(
          igd => igd.id !== payload.igdId
        );
      }
    }
  },
  updateFontSize(state, payload) {
    state.fontSize = payload.fontSize;
    localStorage.setItem("fontSize", payload.fontSize);
  },
  addRecipesDishes(state, payload) {
    state.recipesData[payload.num].dishes.push(payload.data);
  },
  setCurrentDay(state, payload) {
    if (state.currentDay !== payload.currentDay) {
      state.currentDay = payload.currentDay;
    }
  },
  resetCurrentDay(state, payload) {
    // console.log(payload);
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
  toggleLeftShow(state, payload) {
    state.leftShow = !state.leftShow;
  },
  setNotRecIgds(state, payload) {
    state.notRecIgds = payload.data;
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
    //
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
      name: payload.name,
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
    getDicts("dish_class_big").then(response => {
      commit("updateStateData", { dishBigClassOptions: response.data });
    });
    getDicts("dish_class_small").then(response => {
      commit("updateStateData", { dishSmallClassOptions: response.data });
    });
    getDicts("cus_ing_type").then(response => {
      commit("updateStateData", { igdTypeOptions: response.data });
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
                  remark: cur.remark,
                  type: cur.type,
                  isMain: cur.isMain,
                  igdList: cur.igdList.reduce((igdArr, igdData) => {
                    if (igdData.id > 0) {
                      const tarDetail = cur.detail.find(
                        obj => obj.id === igdData.id
                      );
                      if (tarDetail) {
                        igdArr.push({
                          id: igdData.id,
                          name: igdData.name,
                          carbonRatio: igdData.carbonRatio,
                          fatRatio: igdData.fatRatio,
                          proteinRatio: igdData.proteinRatio,
                          cusUnit: tarDetail.cus_unit,
                          cusWeight: tarDetail.cus_weight,
                          weight: parseFloat(tarDetail.weight),
                          notRec: igdData.notRec,
                          rec: igdData.rec,
                          type: igdData.type
                        });
                      }
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
      cusId: payload.cusId !== undefined ? payload.cusId : cusId,
      planId: payload.planId || planId,
      menus: recipesData.map((menu, idx) => ({
        numDay: menu.numDay,
        cusId,
        dishes: menu.dishes.map(dObj => ({
          dishesId: dObj.dishesId,
          type: dObj.type,
          remark: dObj.remark,
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
      if (!payload.planId) {
        // 非保存模板
        commit("updateStateData", { recipesId, reviewStatus: 3 });
        dispatch("getRecipesInfo", { recipesId });
      }
      payload.callback &&
        payload.callback({
          name: state.name,
          planId: state.planId
        });
    }
    // console.log(params);
  },
  async addDishes({ commit, state }, payload) {
    // console.log(payload);
    const tarDishesList = state.recipesData[payload.num].dishes.filter(
      obj => obj.type === payload.data.type
    );
    if (tarDishesList.some(obj => obj.dishesId === payload.data.dishesId)) {
      return new Promise((res, rej) =>
        rej(`目标餐类已有相同的菜品「${payload.data.name}」`)
      );
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
    const { num, actionType } = payload;
    if (actionType === "replace") {
      const tarDishesList = state.recipesData[payload.num].dishes.filter(
        obj => obj.type === payload.data.type
      );
      if (tarDishesList.some(obj => obj.dishesId === payload.data.dishesId)) {
        return new Promise((res, rej) =>
          rej(`目标餐类已有相同的菜品「${payload.data.name}」`)
        );
      }
    }
    // console.log(payload);
    if (state.recipesId) {
      const tarDishes = state.recipesData[num].dishes.find(
        obj =>
          obj.id === (actionType === "replace" ? payload.data.id : payload.id)
      );
      if (tarDishes) {
        const mTarDishes = JSON.parse(JSON.stringify(tarDishes));
        const params = {
          id: mTarDishes.id
        };
        if (actionType === "menuType") {
          // 修改餐类
          params.type = payload.type;
        } else if (actionType === "remark") {
          params.remark = payload.remark;
        } else if (actionType === "replace") {
          params.dishesId = payload.data.dishesId;
          params.remark = "";
          params.detail = payload.data.igdList.map(igd => ({
            id: igd.id,
            weight: igd.weight,
            cus_unit: igd.cusUnit,
            cus_weight: igd.cusWeight
          }));
        } else if (actionType === "delIgd") {
          // 删除某食材
          params.detail = mTarDishes.igdList.reduce((arr, igd) => {
            if (igd.id !== payload.igdId) {
              arr.push({
                id: igd.id,
                weight: igd.weight,
                cus_unit: igd.cusUnit,
                cus_weight: igd.cusWeight
              });
            }
            return arr;
          }, []);
        } else if (actionType === "unit" || actionType === "weight") {
          // 修改食材
          params.detail = mTarDishes.igdList.map(igd => {
            const isTarIgd = igd.id === payload.igdId;
            return {
              id: igd.id,
              weight: isTarIgd && payload.weight ? payload.weight : igd.weight,
              cus_unit:
                isTarIgd && payload.cusUnit ? payload.cusUnit : igd.cusUnit,
              cus_weight:
                isTarIgd && payload.cusWeight
                  ? payload.cusWeight
                  : igd.cusWeight
            };
          });
        }
        const result = await updateDishesDetailApi(params);
        if (result.code === 200) {
          commit("updateRecipesDishesDetail", payload);
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
  async deleteMenu({ commit }, payload) {},
  async setCopyData({ commit, state }, payload) {
    return new Promise(async (res, rej) => {
      const tarDishes = state.recipesData[payload.num].dishes.find(
        obj => obj.id === payload.id
      );
      if (tarDishes) {
        const response = await getDishesMenuTypes(tarDishes.dishesId);
        if (response.code === 200) {
          commit("updateStateData", {
            copyData: tarDishes,
            canCopyMenuTypes: response.data.type.split(",")
          });
          res("复制成功");
        } else {
          rej("复制失败");
        }
      } else {
        rej("复制失败");
      }
    });
  }
};

const getters = {
  dishClassOptions: state => {
    const dishClass = [];
    state.dishBigClassOptions.forEach((item, index) => {
      dishClass.push({
        value: parseInt(item.dictValue),
        label: item.dictLabel,
        children: []
      });
      if (index == state.dishBigClassOptions.length - 1) {
        state.dishSmallClassOptions.forEach((smallClass, i) => {
          if (smallClass.remark) {
            dishClass[parseInt(smallClass.remark - 1)].children.push({
              value: parseInt(smallClass.dictValue),
              label: smallClass.dictLabel
            });
          }
        });
      }
    });
    return dishClass;
  },
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
            obj.pCalories = obj.pWeight * 4;
            obj.fWeight += (igd.weight / 100) * igd.fatRatio;
            obj.fCalories = obj.fWeight * 9;
            obj.cWeight += (igd.weight / 100) * igd.carbonRatio;
            obj.cCalories = obj.cWeight * 4;
            obj.totalCalories = obj.pCalories + obj.fCalories + obj.cCalories;
            obj[`calories${cur.type}`] +=
              (igd.weight / 100) * igd.proteinRatio * 4 +
              (igd.weight / 100) * igd.fatRatio * 9 +
              (igd.weight / 100) * igd.carbonRatio * 4;
            obj[`weight${cur.type}`] += igd.weight;
            obj.totalWeight += igd.weight;
          });
          return obj;
        },
        {
          name: `第${data.numDay}天`,
          pWeight: 0,
          fWeight: 0,
          cWeight: 0,
          pCalories: 0,
          fCalories: 0,
          cCalories: 0,
          //
          totalCalories: 0,
          calories1: 0,
          calories2: 0,
          calories3: 0,
          calories4: 0,
          calories5: 0,
          calories6: 0,
          //
          totalWeight: 0,
          weight1: 0,
          weight2: 0,
          weight3: 0,
          weight4: 0,
          weight5: 0,
          weight6: 0
        }
      )
    );
    // console.log(nutriData);
    return nutriData;
  },
  verifyNotRecData: state => {
    const data = state.recipesData.reduce((arr, cur, dayIdx) => {
      cur.dishes.forEach(dObj => {
        dObj.igdList.forEach(iObj => {
          (iObj.notRec || "").split(",").forEach(nRec => {
            if (nRec) {
              const tarObj = arr.find(obj => obj.name === nRec);
              if (tarObj) {
                if (!tarObj.igdIds.includes(iObj.id)) {
                  tarObj.igdIds.push(iObj.id);
                }
                const tarIgdObj = tarObj.data.find(obj => obj.id === iObj.id);
                if (tarIgdObj) {
                  if (!tarIgdObj.data.some(obj => obj.day === cur.numDay)) {
                    tarIgdObj.data.push({ num: dayIdx, day: cur.numDay });
                  }
                } else {
                  tarObj.data.push({
                    name: iObj.name,
                    id: iObj.id,
                    data: [{ num: dayIdx, day: cur.numDay }]
                  });
                }
              } else {
                arr.push({
                  name: nRec,
                  igdIds: [iObj.id],
                  data: [
                    {
                      name: iObj.name,
                      id: iObj.id,
                      data: [{ num: dayIdx, day: cur.numDay }]
                    }
                  ]
                });
              }
            }
          });
        });
      });
      return arr;
    }, []);
    // console.log(data);
    return data;
  },
  igdTypeData: state => {
    const data = state.recipesData.reduce((arr, cur, idx) => {
      cur.dishes.forEach(dObj => {
        dObj.igdList.forEach(iObj => {
          let tarObj = arr.find(obj => obj.id === iObj.id);
          if (!tarObj) {
            tarObj = {
              name: iObj.name,
              id: iObj.id,
              data: [{ num: idx, day: cur.numDay }]
            };
            arr.push(tarObj);
          } else if (!tarObj.data.some(obj => obj.day === cur.numDay)) {
            tarObj.data.push({ num: idx, day: cur.numDay });
          }
        });
      });
      return arr;
    }, []);

    // console.log(data);
    return data;
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
    }, {}),
  dishBigClassDict: state =>
    state.dishBigClassOptions.reduce((obj, cur) => {
      obj[cur.dictValue] = cur.dictLabel;
      return obj;
    }, {}),
  dishSmallClassDict: state =>
    state.dishSmallClassOptions.reduce((obj, cur) => {
      obj[cur.dictValue] = cur.dictLabel;
      return obj;
    }, {}),
  igdTypeDict: state =>
    state.igdTypeOptions.reduce((obj, cur) => {
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
