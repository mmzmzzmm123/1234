import HttpClient from '@/common/http-client.js'
import {
	BaseApiUrl,
	LoginRedirectBase,
	AppId
} from "@/common/config.js"
var i18n

function toLogin(){
	location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${AppId}&redirect_uri=${LoginRedirectBase}/pages/login/mp-login&scope=snsapi_userinfo&connect_redirect=1#wechat_redirect`
}
const client = new HttpClient({
	baseApiUrl: BaseApiUrl + '/',
	appId: AppId,
	loginHandler: toLogin
})

function request(url, data, showLoading, method, hideError) {
	return client.request(url, data, showLoading, method, hideError)
}

function post(url, data, showLoading, hideError) {
	return client.request(url, data, showLoading, 'POST', hideError)
}

function get(url, data, showLoading, hideError) {
	return client.request(url, data, showLoading, 'GET', hideError)
}

const api = {
	setAfterLoginToGo(url){
		uni.setStorageSync("afterLoginToGo", '/' + url)
	},
	getAfterLoginToGo(){
		let url = uni.getStorageSync("afterLoginToGo")
		if(url){
			uni.removeStorage({
				key: "afterLoginToGo"
			})
		}
		return url
	},
	toLogin,
	login(param) {
		return client.getToken(param)
	},
	logout(){
		return post('logout').then(res=>{
			client.token = ''
		})
	},
	bindMerchant(param){
		return post('binding', param)
	},
	getUserInfo() {
		return get('getInfo')
	},
	getCaptchaImage() {
		return get('captchaImage')
	},
	getStoreList() {
		return get('office/store/list')
	},
	cleanerGetStoreList(){
		return get('office/capi/store/list')
	},
	updateStoreInfo(params) {
		return request('office/store', params, true, 'put')
	},
	uploadImage(imagePath) {
		uni.showLoading({
			mask: true
		})
		let files
		if (Array.isArray(imagePath)) {
			files = imagePath
		} else {
			files = [imagePath]
		}
		const tasks = []
		const uploadUrl = client.baseApiUrl + "common/upload"
		for (let i in files) {
			tasks.push(new Promise((resolve, reject) => {
				uni.uploadFile({
					url: uploadUrl,
					name: 'file',
					header: {
						Authorization: client.token
					},
					filePath: files[i],
					success: res => {
						if (res.statusCode != 200) {
							reject()
						} else {
							const data = JSON.parse(res.data)
							if (!data || data.code != 200) {
								reject(data)
							} else {
								resolve({
									index: i,
									path: data.fileName
								})
							}
						}
					},
					fail: reject
				})
			}))
		}
		return Promise.all(tasks)
			.then(res => {
				if (Array.isArray(imagePath)) {
					const ret = []
					for (let i in res) {
						ret[res[i].index] = res[i].path
					}
					return ret
				} else {
					return res[0].path
				}
			})
			.catch(error => {
				uni.showModal({
					showCancel: false,
					content: '图片上传失败'
				})
			})
			.finally(() => {
				uni.hideLoading();
			})
	},
	getStroeRoomList(params) {
		return get('office/room/list', params)
	},
	getRoomPackList(params) {
		return get('office/roompackage/list', params)
	},
	addRoomPack(params) {
		return post('office/roompackage', params)
	},
	getRoomPack(id) {
		return get('office/roompackage/' + id)
	},
	updateRoomPack(params) {
		return request('office/roompackage', params, true, 'put')
	},
	delRoomPack(id) {
		return request('office/roompackage/' + id, null, true, 'delete')
	},
	getRoomChargePriceList(params){
		return get('office/roomchargeprice/list', params)
	},
	getRoomChargePrice(id){
		return get('office/roomchargeprice/' + id)
	},
	addRoomChargePrice(params){
		return post('office/roomchargeprice', params)
	},
	updateRoomChargePrice(params){
		return request('office/roomchargeprice', params, true, 'PUT')
	},
	deleteRoomChargePrice(id){
		return request('office/roomchargeprice/' + id, null, true, 'DELETE')
	},
	getStorePromotionList(params){
		return get('office/storepromotion/h5list', params)
	},
	getStorePromotion(id){
		return get('office/storepromotion/' + id)
	},
	updateStorePromotion(params){
		return request('office/storepromotion', params, true, 'put')
	},
	addStorePromotion(params){
		return post('office/storepromotion', params)
	},
	delStorePromotion(id) {
		return request('office/storepromotion/' + id, null, false, 'delete')
	},
	getRoomList(params) {
		return get('office/room/list', params)
	},
	getRoomOrderList(params){
		return get('office/roomorder/h5list', params)
	},
	getCleanerRoomList(params){
		return get('office/capi/room/list', params)
	},
	getCleanRecordList(params){
		debugger
		return get('office/cleanrecord/h5list', params)
	},
	getCleanerCleanRecordList(params){
		return get('office/capi/record', params)
	},
	getNoticeList(params){
		return get('system/notice/list', params)
	},
	getStore(id){
		return get('office/store/' + id)
	},
	getRoom(id){
		return get('office/room/' + id)
	},
	addRoom(params){
		return post('office/room', params)
	},
	editRoom(params){
		return request('office/room', params, true, 'PUT')
	},
	deleteRoom(id){
		return request('office/room/' + id, null, true, 'delete')
	},
	getDict(dictType){
		return get('system/dict/data/type/' + dictType)
	},
	openStore(storeId){
		return post('office/api/openStore/'+storeId)
	},
	openRoom(param){
		return post('office/mapi/roomopenH5',param)
	},
	closeRoom(param){
		return post('office/mapi/roomcloseH5',param)
	},
	comingSoon(){
		uni.showToast({
			icon:"none",
			title:"即将上线"
		})
	},
	addRoomOrder(params){
		return post('office/roomorder/order4Guest', params)
	},
	getStoreCrewList(params){
		return get('office/storeuser/h5list', params)
	},
	editStoreCrew(params){
		return request('office/storeuser', params, true, 'PUT')
	},
	addStoreCrew(params){
		return post('office/storeuser', params)
	},
	getStoreUser(id){
		return get('office/storeuser/' + id)
	},
	removeStoreUser(id){
		return request('office/storeuser/'+id, null, true, 'DELETE')
	},
	startCleanRoom(roomId) {
		return post('office/cleanrecord/startClean/' + roomId)
	},
	finishCleanRoom(roomId) {
		return post('office/cleanrecord/finishClean/' + roomId)
	},
	cleanerOpenStore(storeId) {
		return post('office/capi/storeopen/' + storeId)
	},
	cleanerOpenRoom(params) {
		return post('office/capi/roomopen/temp', params)
	},
	cleanerCloseRoom(params) {
		return post('office/capi/roomclose/temp', params)
	},
	toBindRole(params){
		return post('office/mapi/role/toBind', params)
	},
	bindRole(params){
		return post('binding/role', params)
	},
	getToBindRole(id){
		return get('office/mapi/role/toBindInfo/' + id)
	},
	bindPhoneNumber(phone){
		return post('office/api/binding', {phone})
	},
	shareOrder(order){
		return post('office/xxx/shareOrder', order)
	},
	changeRoom(order){
		return post('office/roomorder/changeRoom', order)
	},
	renewOrder(order){
		return post('office/roomorder/charge', order)
	},
	chargeOrder(order){
		return post('office/roomorder/charge', order)
	},
	createQrCode(id) {
		return get('office/api/createQrCodeH5/' + id)
	},
	createXudanCode(id) {
		return get('office/api/createXudanCodeH5/' + id)
	}
}
const install = (Vue, options) => {
	Vue.prototype.$api = api
	Vue.prototype.$toast = function(title) {
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
