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
		return get('office/api/userInfo')
	},
	bindMerchantAccount(param){
		return post('binding', param)
	},
	getStoreList(){
		return get('office/mapi/store/list').then(res=>{
			res.rows.forEach(x=>{
				if(x.logo){
					x.logoList = x.logo.split(',').map(y=>BaseApiUrl + y)
					x.logo = x.logoList[0]
				}else{
					x.logoList = []
				}
			})
			return res
		})
	},
	getRoomList(param){
		return get('office/mapi/room/list')
	},
	getUserListReg(param){
		return get('office/mapi/user/reg', param)
	},
	getUserListAct(param){
		return get('office/mapi/user/act', param)
	},
	getUserListTimes(param){
		return get('office/mapi/user/times', param)
	},
	getUserListHour(param){
		return get('office/mapi/user/hour', param)
	},
	openRoom(param){
		return post('office/mapi/roomopen', param)
	}
	// getStoreList(param){
	// 	return get("office/api/store/list", param).then(res=>{
	// 		 res.rows.forEach(x=>x.logo = BaseApiUrl + x.logo)
	// 		 return res
	// 	})
	// },
	// getRoomList(param){
	// 	return get('office/api/room/list', param).then(res=>{
	// 		res.rows.forEach(x=>{
	// 			x.logo = BaseApiUrl + x.logo
	// 			if(x.remark){
	// 				x.labelList = x.remark.split(',')
	// 			}
	// 		})
	// 		return res
	// 	})
	// },
	// getOrderList(param){
	// 	return get('office/api/room/order', param)
	// },
	// getOpenOrderList(param){
	// 	return get('office/api/order/open', param)
	// },
	// getCurrentOrder(){
	// 	return get('office/roomorder/continue')
	// },
	// meiTuanConsume(code){
	// 	return get('office/api/meiTuanConsume/'+code)
	// },
	// openStore(storeId){
	// 	return post('office/api/store/' + storeId)
	// },
	// openRoom(roomId){
	// 	return post('office/api/room/' + roomId)
	// },
	// addOrder(param){
	// 	return post('office/roomorder/order', param)
	// },
	// reOrder(param){
	// 	return post('office/api/reorder', param)
	// },
	// getRoomAvailable(param){
	// 	return get('office/api/room/available', param)
	// },
	// getValidCouponList(param){
	// 	return get('office/api/validlist', param)
	// },
	// getValidCouponCount(){
	// 	return get('office/api/validCount')
	// },
	// wxPaySuccess(param){
	// 	return post('office/roomorder/payquery', param)
	// },
	// getStoreAmount(storeId){
	// 	return get('office/api/amount/' + storeId)
	// },
	// getAmount(){
	// 	return get('office/api/amount')
	// },
	// getNoticeList(){
	// 	return get('office/api/notice/list').then(res=>{
	// 		res.rows.forEach(x=>{
	// 			x.remark = BaseApiUrl + x.remark
	// 		})
	// 		return res
	// 	})
	// }
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