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
let saveStateKeys = ['loginUser', 'currentStore', 'wxUser'];

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
		loginUser: lifeData.loginUser ? lifeData.loginUser : {},
		currentStore: lifeData.currentStore ? lifeData.currentStore : {id: 0, name: '暂无门店'},
		storeList: [],
		dicts: {},
		storeRoles: [],
		wxUser: lifeData.wxUser ? lifeData.wxUser : null
	},
	mutations: {
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
				return dispatch("getUserInfo")
			})
		},
		logout({state}){
			return Api.api.logout().then(()=>{
				state.loginUser = {}
				lifeData["loginUser"] = {}
				uni.setStorage({
					key: 'lifeData',
					data: lifeData
				})
			})
		},
		getUserInfo({state}){
			return Api.api.getUserInfo().then(res=>{
				if(res.user){
					state.loginUser = res.user
					lifeData["loginUser"] = res.user
				}else{
					state.loginUser = {}
					lifeData['loginUser'] = {}
				}
				state.wxUser = res.wxUser
				lifeData['wxUser'] = res.wxUser
				state.storeRoles = res.storeRoles
				if(!state.storeRoles.length && !state.loginUser.userId){
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
		},
		loadDict({state}, dictType){
			if(!state.dicts[dictType]){
				Api.api.getDict(dictType).then(res=>{
					state.dicts = {...state.dicts, [dictType]: res.map(x=>{
						return {
							name: x.dictLabel,
							value: x.dictValue
						}
					})}
				})
			}
		}
	}
})

export default store
