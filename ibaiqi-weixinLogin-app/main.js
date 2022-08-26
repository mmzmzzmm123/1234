import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import stu2 from "@/utils/ib-test.js"
import './permission' // permission
Vue.use(plugins)
Vue.config.productionTip = false
Vue.prototype.$store = store
Vue.prototype.$stu2=stu2


App.mpType = 'app'

const app = new Vue({
  ...App
})

app.$mount()
