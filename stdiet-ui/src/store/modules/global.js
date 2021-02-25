import { getOptions } from "@/api/custom/global";

const oriState = {
  nutritionistIdOptions: [],
  nutriAssisIdOptions: [],
  preSaleIdOptions: [],
  onSaleIdOptions: [],
  afterSaleIdOptions: [],
  plannerIdOptions: [],
  plannerAssisIdOptions: [],
  operatorIdOptions: [],
  operatorAssisIdOptions: []
};

const mutations = {
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
  async init({ commit }, payload) {
    const { data: optionsData } = await getOptions();
    const options = optionsData.reduce((opts, cur) => {
      if (!opts[cur.postCode]) {
        opts[cur.postCode] = [{ dictValue: 0, dictLabel: "无", remark: null }];
      }
      opts[cur.postCode].push({
        dictValue: cur.userId,
        dictLabel: cur.userName,
        remark: cur.remark
      });
      return opts;
    }, {});
    commit("save", {
      nutritionistIdOptions: options["nutri"] || [],
      nutriAssisIdOptions: options["nutri_assis"] || [],
      preSaleIdOptions: options["pre_sale"] || [],
      onSaleIdOptions: options["on_sale"] || [],
      afterSaleIdOptions: options["after_sale"] || [],
      plannerIdOptions: options["planner"] || [],
      plannerAssisIdOptions: options["planner_assis"] || [],
      operatorIdOptions: options["operator"] || [],
      operatorAssisIdOptions: options["operator_assis"] || []
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
