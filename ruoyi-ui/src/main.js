import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './assets/styles/element-variables.scss'
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel,selectMoeDictLabel, download, handleTree } from "@/utils/ruoyi";
import Pagination from "@/components/Pagination";
import VideoPlayer from 'vue-video-player';
import Video from 'video.js'
// import 'video.js/dist/video-js.css'
// require videojs style
import 'video.js/dist/video-js.css';
import 'vue-video-player/src/custom-theme.css';
import Print from '@/utils/print'
import PrintECharts from '@/utils/print_echarts'
import { preventReClick } from '@/utils/plugins';
import watermark from '@/utils/watermark';  //路径不要写错
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectMoeDictLabel = selectMoeDictLabel
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

Vue.prototype.$video = Video
Vue.use(VideoPlayer);
Vue.use(Print);
Vue.use(PrintECharts);
Vue.component('swiper', Swiper)
Vue.component('swiperSlide', SwiperSlide)
Vue.prototype.$watermark = watermark

import LunarFullCalendar from "vue-lunar-full-calendar";
Vue.use(LunarFullCalendar);



Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// 全局组件挂载
Vue.component('Pagination', Pagination)

Vue.use(permission)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
