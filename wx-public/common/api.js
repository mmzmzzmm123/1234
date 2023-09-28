import HttpClient from '@/common/http-client.js'
import { BaseApiUrl } from "@/common/config.js"
var i18n

const client = new HttpClient({
	baseApiUrl: BaseApiUrl + '/',
	appName: 'merchant',
	loginPage: '/pages/login/index'
})

function request(url, data, showLoading, method, hideError){
	return client.request(url, data, showLoading, method, hideError)
}
function post(url, data, showLoading, hideError){
	return client.request(url, data, showLoading, 'POST', hideError)
}
function get(url, data, showLoading, hideError){
	return client.request(url, data, showLoading, 'GET', hideError)
}

const api = {
	login(param){
		return client.getToken(param)
	},
	getUserInfo(){
		return get('getInfo')
	},
	getCaptchaImage(){
		return get('captchaImage')
	},
	getStoreList(){
		return get('office/mapi/store/list').then(res=>{
			res.rows.forEach(x=>{
				if(x.logo){
					x.logoList = x.logo.split(',')
					x.logo = x.logoList[0]
				}else{
					x.logoList = []
				}
			})
			return res
		})
	},
}
const install = (Vue, options) => {
	Vue.prototype.$api = api
	Vue.prototype.$toast = function(title){
		uni.showToast({
			icon: 'none',
			title
		})
	}
	i18n = options.i18n
}

export default {
	install,
	api
}