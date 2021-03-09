import Vue from "vue";
import Vuex from "vuex";
import app from "./modules/app";
import user from "./modules/user";
import tagsView from "./modules/tagsView";
import permission from "./modules/permission";
import settings from "./modules/settings";
import recipes from "./modules/recipes";
import recipesShow from "./modules/recipesShow";
import global from "./modules/global";

import getters from "./getters";
import actions from "./actions";

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    app,
    user,
    tagsView,
    permission,
    settings,
    recipes,
    recipesShow,
    global
  },
  getters,
  actions
});

export default store;
