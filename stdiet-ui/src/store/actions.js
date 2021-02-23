const actions = {
  async init({ dispatch }, payload) {
    dispatch("global/init", payload);
  }
};

export default actions;
