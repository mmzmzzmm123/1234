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
	formatImgUrl(url){
		return BaseApiUrl + url
	},
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
		return request('office/api/userInfo', null, true, 'GET')
	},
	bindPhoneNumber(code){
		return post('office/api/binding', {code})
	},
	getStoreList(param){
		return get("office/api/store/list", param).then(res=>{
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
	getStoreById(id){
		return this.getStoreList({id}).then(res=>{
			if(res.rows.length){
				return res.rows[0]
			}
			return null
		})
	},
	getRoomList(param){
		return get('office/api/room/list', param).then(res=>{
			res.rows.forEach(x=>{
				if(x.logo){
					x.logoList = x.logo.split(',').map(y=>BaseApiUrl + y)
					x.logo = x.logoList[0]
				}else{
					x.logoList = []
				}
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
	getOpenOrderList(param){
		return get('office/api/order/open', param)
	},
	getCurrentOrder(){
		return get('office/roomorder/continue')
	},
	meiTuanConsume(code){
		return get('office/api/meiTuanConsume/'+code)
	},
	openStore(storeId){
		return post('office/api/store/' + storeId)
	},
	openRoom(orderId){
		return post('office/api/room/' + orderId)
	},
	addOrder(param){
		return post('office/roomorder/order', param)
	},
	reOrder(param){
		return post('office/api/reorder', param)
	},
	addOrderByPack(param){
		return post('office/api/pack/order', param)
	},
	addPromotionOrder(param){
		return post('office/api/promotion/order', param)
	},
	getRoomAvailable(param){
		return get('office/api/room/available', param)
	},
	getValidCouponList(param){
		return get('office/api/validlist', param)
	},
	getValidCouponCount(){
		return get('office/api/validCount')
	},
	wxPaySuccess(param){
		return post('office/roomorder/payquery', param)
	},
	getStoreAmount(storeId){
		return get('office/api/amount/' + storeId)
	},
	getAmount(){
		return get('office/api/amount')
	},
	getNoticeList(){
		return get('office/api/notice/list')
	},
	buyStorePackage(param){
		return post('office/api/package/buy', param)
	},
	buyStorePackageSuccess(param){
		return post('office/api/package/payquery', param)
	},
	getPromotionList(param){
		return get('office/api/promotionList', param).then(res=>{
			res.rows.forEach(x=>{
				if(x.logo){
					x.logo = BaseApiUrl + x.logo
				}
			})
			return res
		})
	},
	getStorePromotionList(storeId){
		return getPromotionList({storeId})
	},
	addJoinUs(param){
		return post('office/api/joinUs',param)
	}
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