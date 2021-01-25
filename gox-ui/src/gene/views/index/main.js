import Vue from 'vue'
import App from './App.vue'
import router from '@/gene/router'
import '@/gene/styles/index.scss'
import '@/gene/icons'
import axios from 'axios'
import Tinymce from '@/gene/components/tinymce/index.vue'

Vue.component('tinymce', Tinymce)

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
