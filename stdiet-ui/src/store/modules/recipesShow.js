const oriState = {
  recipes: {},
  shoppingCart: {}
};

const mutations = {
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
  async fetchFullRecipes({commit, dispatch},payload ) {

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
