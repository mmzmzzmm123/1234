import Vue from "vue";

import Cookies from "js-cookie";

import "normalize.css/normalize.css"; // a modern alternative to CSS resets
import Element from "element-ui";
import "./assets/styles/element-variables.scss";

import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/ruoyi.scss"; // ruoyi css
import App from "./App";
import store from "./store";
import router from "./router";
import permission from "./directive/permission";

import "./assets/icons"; // icon
import "./permission"; // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import VueScrollTo from "vue-scrollto";
import VueResource from "vue-resource";
import HighchartsVue from "highcharts-vue";
import Highcharts from "highcharts";
import { init } from "@/utils/websocket";
//图片导出模块
import exportingInit from "highcharts/modules/exporting";
exportingInit(Highcharts);

init();

import {
  addDateRange,
  download,
  downloadResource,
  handleTree,
  parseTime,
  resetForm,
  digitUppercase,
  selectDictLabel,
  selectDictLabels,
  toThousands,
  validatorIDCard
} from "@/utils/ruoyi";
import Pagination from "@/components/Pagination";
//自定义表格工具扩展
import RightToolbar from "@/components/RightToolbar";

// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.selectDictLabels = selectDictLabels;
Vue.prototype.download = download;
Vue.prototype.downloadResource = downloadResource;
Vue.prototype.handleTree = handleTree;
Vue.prototype.toThousands = toThousands;
Vue.prototype.digitUppercase = digitUppercase;
Vue.prototype.validatorIDCard = validatorIDCard;

Vue.prototype.msgSuccess = function(msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
};

Vue.prototype.msgError = function(msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
};

Vue.prototype.msgInfo = function(msg) {
  this.$message.info(msg);
};

// 全局组件挂载
Vue.component("Pagination", Pagination);
Vue.component("RightToolbar", RightToolbar);

Vue.use(permission);
Vue.use(HighchartsVue);

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium" // set element-ui default size
});

Vue.config.productionTip = false;

Vue.directive("title", {
  inserted: function(el, binding) {
    document.title = el.dataset.title;
  }
});

Vue.use(VueScrollTo, {
  container: "body",
  duration: 500,
  easing: "ease",
  offset: 0,
  force: true,
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false,
  y: true
});

Vue.use(VueResource);

new Vue({
  el: "#app",
  router,
  store,
  render: h => h(App)
});
