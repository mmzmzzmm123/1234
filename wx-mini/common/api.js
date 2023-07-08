import HttpClient from '@/common/http-client.js'
import { BaseApiUrl } from "@/common/config.js"
var i18n

const client = new HttpClient({
	baseApiUrl: BaseApiUrl + '/',
	appName: 'office',
	loginHandler(){
		return new Promise((resolve, reject)=>{
			uni.login({
				success: (wxAuth)=>{
					resolve(wxAuth.code)
				}
			})
		})
	}
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
	login(){
		return new Promise((resolve, reject)=>{
			uni.login({
				success: (wxAuth)=>{
					client.getToken(wxAuth.code).then(resolve)
				}
			})
		})
	},
	getUserInfo(){
		return request('getInfo', null, true, 'GET')
	},
	getStoreList(param){
		return get("office/api/store/list", param).then(res=>{
			 res.rows.forEach(x=>x.logo = BaseApiUrl + x.logo)
			 return res
		})
	},
	getRoomList(param){
		return get('office/api/room/list', param).then(res=>{
			res.rows.forEach(x=>{
				x.logo = BaseApiUrl + x.logo
				if(x.remark){
					x.labelList = x.remark.split(',')
				}
			})
			return res
		})
	},
	getOrderList(param){
		return get('office/api/room/order', param)
	},
	meiTuanConsume(code){
		return get('office/api/meiTuanConsume/'+code)
	},
	openStore(storeId){
		return post('office/api/store/' + storeId)
	},
	openRoom(roomId){
		return post('office/api/room/' + roomId)
	}
}
const install = (Vue, options) => {
	Vue.prototype.$api = api
	i18n = options.i18n
}

export default {
	install,
	api
}