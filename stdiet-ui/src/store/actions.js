const actions = {
  async init({ dispatch }, payload) {
    dispatch("global/init", payload);
  },
  async updateUnreadCount({ commit }, payload) {
    commit("global/save", payload);
  }
};

export default actions;
