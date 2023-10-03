import HttpClient from '@/common/http-client.js'
import {
	BaseApiUrl
} from "@/common/config.js"
var i18n

const client = new HttpClient({
	baseApiUrl: BaseApiUrl + '/',
	appName: 'merchant',
	loginPage: '/pages/login/index'
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
	login(param) {
		return client.getToken(param)
	},
	logout(){
		return post('logout').then(res=>{
			client.token = ''
		})
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
		return request('office/roompackage/' + id, null, false, 'delete')
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
	getCleanRecordList(params){
		return get('office/cleanrecord/h5list', params)
	},
	getNoticeList(params){
		return get('system/notice/list', params)
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
	comingSoon(){
		uni.showToast({
			icon:"none",
			title:"即将上线"
		})
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
