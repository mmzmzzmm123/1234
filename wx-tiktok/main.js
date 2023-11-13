import Vue from 'vue';
import App from './App';
import util from 'common/util.js'

// i18n部分的配置
// 引入语言包，注意路径
import Chinese from '@/common/locales/zh.js';
import English from '@/common/locales/en.js';

// VueI18n
import VueI18n from '@/common/vue-i18n.min.js';

// VueI18n
Vue.use(VueI18n);

const i18n = new VueI18n({
	// 默认语言
	locale: 'zh',
	// 引入语言文件
	messages: {
		'zh': Chinese,
		'en': English,
	}
});

// #ifdef MP-WEIXIN
// 由于微信小程序的运行机制问题，需声明如下一行，H5和APP非必填
Vue.prototype._i18n = i18n;
// #endif

import Api from '@/common/api.js';
Vue.use(Api, {i18n})

Vue.config.productionTip = false;

App.mpType = 'app';

// 引入全局uView
import uView from '@/uni_modules/uview-ui'
Vue.use(uView);

import store from '@/store';

// uni.getSystemInfo({
// 	success(res) {
// 		if(res.language.indexOf('zh') >= 0){
// 			i18n.locale = 'zh'
// 		}else{
// 			i18n.locale = 'en'
// 		}
// 	}
// })
uni.getSetting({
	success(res){
		// scope.userLocation
		if(res.authSetting['scope.userFuzzyLocation']){
			store.commit('setCanLocation', true)
		}else{
			// uni.authorize({
			// 	scope: 'scope.userFuzzyLocation',
			// 	success(){
			// 		store.commit('setCanLocation', true)
			// 	}
			// })
		}
	}
})
// store.dispatch("login")
const app = new Vue({
	i18n,
	store,
	...App
});
app.$mount();
