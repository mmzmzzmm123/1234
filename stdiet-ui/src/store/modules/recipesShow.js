import { getRecipesFullInfo, getDicts } from "@/api/custom/recipesShow";

const oriState = {
  recipes: {},
  cusUnitDict: {},
  cusWeightDict: {},
  menuTypeDict: {},
  idgTypeDict: {},
  igdUnitDict: {}
};

const mutations = {
  setRecipesData(state, payload) {
    // console.log(payload);
    state.recipes[payload.recipesId] = payload.data;
  },
  updateStateData(state, payload) {
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
  async init({ commit, dispatch }, payload) {
    getDicts("cus_cus_unit").then(response => {
      const cusUnitDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
      commit("updateStateData", { cusUnitDict });
    });
    getDicts("cus_cus_weight").then(response => {
      const cusWeightDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
      commit("updateStateData", { cusWeightDict });
    });
    getDicts("cus_dishes_type").then(response => {
      const menuTypeDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
      commit("updateStateData", { menuTypeDict });
    });
    getDicts("cus_ing_type").then(response => {
      const idgTypeDict = response.data.reduce((obj, cur) => {
        obj[cur.dictValue] = cur.dictLabel;
        return obj;
      }, {});
      commit("updateStateData", { idgTypeDict });
    });
    getDicts("sys_ingredient_unit").then(response => {
      const igdUnitDict = response.data.reduce((obj, cur) => {
        obj[cur.dictLabel] = cur.dictValue;
        return obj;
      }, {});
      commit("updateStateData", { igdUnitDict });
    });
  },
  async fetchFullRecipes({ commit, dispatch }, payload) {
    return new Promise((res, rej) => {
      getRecipesFullInfo(payload.recipesId).then(response => {
        if (response.code === 200) {
          commit("setRecipesData", {
            recipesId: payload.recipesId,
            data: response.data.reduce((outArr, dayData, idx) => {
              outArr.push({
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
              return outArr;
            }, [])
          });
        }
        res();
      });
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
