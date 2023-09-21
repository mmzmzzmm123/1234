import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

import Api from '../common/api.js';

let lifeData = {};

try{
	// 尝试获取本地是否存在lifeData变量，第一次启动APP时是不存在的
	lifeData = uni.getStorageSync('lifeData');
	if(!lifeData){
		lifeData = {};
	}
}catch(e){
	
}

// 需要永久存储，且下次APP启动需要取出的，在state中的变量名
let saveStateKeys = ['loginUser', 'currentStore'];

// 保存变量到本地存储中
const saveLifeData = function(key, value){
	// 判断变量名是否在需要存储的数组中
	if(saveStateKeys.indexOf(key) != -1) {
		lifeData[key] = value;
		// 执行这一步后，所有需要存储的变量，都挂载在本地的lifeData对象中
		uni.setStorage({
			key: 'lifeData',
			data: lifeData
		})
	}
}
const store = new Vuex.Store({
	state: {
		// 如果上面从本地获取的lifeData对象下有对应的属性，就赋值给state中对应的变量
		hasLogin: false,
		loginUser: lifeData.loginUser ? lifeData.loginUser : {},
		roles: lifeData.roles ? lifeData.roles : [],
		currentStore: lifeData.currentStore ? lifeData.currentStore : {name: '当前店铺'},
		storeList: []
	},
	mutations: {
		setCanLocation(state, value){
			state.canLocation = value
		},
		$uStore(state, payload) {
			// 判断是否多层级调用，state中为对象存在的情况，诸如user.info.score = 1
			let nameArr = payload.name.split('.');
			let saveKey = '';
			let len = nameArr.length;
			if(len >= 2) {
				let obj = state[nameArr[0]];
				for(let i = 1; i < len - 1; i ++) {
					obj = obj[nameArr[i]];
				}
				obj[nameArr[len - 1]] = payload.value;
				saveKey = nameArr[0];
			} else {
				// 单层级变量，在state就是一个普通变量的情况
				state[payload.name] = payload.value;
				saveKey = payload.name;
			}
			// 保存变量到本地，见顶部函数定义
			saveLifeData(saveKey, state[saveKey])
		}
	},
	actions: {
		login({state, dispatch}, para){
			return Api.api.login(para).then(()=>{
				state.hasLogin = true
				return dispatch("getUserInfo")
			})
		},
		getUserInfo({state}){
			return Api.api.getUserInfo().then(res=>{
				state.hasLogin = true
				state.loginUser = res
				lifeData["loginUser"] = res
				if(!res.userType){
					uni.reLaunch({
						url: '/pages/bind/index'
					})
					return
				}
				Api.api.getStoreList().then(res=>{
					state.storeList = res.rows
					const currentStoreId = state.currentStore ? state.currentStore.id : 0
					if(currentStoreId){
						state.currentStore = res.rows.find(x=>x.id == currentStoreId)
					}else{
						state.currentStore = res.rows[0]
					}
					lifeData['currentStore'] = state.currentStore
					uni.setStorage({
						key: 'lifeData',
						data: lifeData
					})
				})
			})
		}
	}
})

export default store
