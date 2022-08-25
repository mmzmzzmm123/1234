import Vue from 'vue'
import Vuex from 'vuex'
import user from '@/store/modules/user'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
	// module：可以让每一个模块拥有自己的state、mutation、action、getters,使得结构非常清晰，方便管理
	modules: {
		user
	},
	// 作用把各个组件需要关联的计算属性computed定义的函数提取出来。
	getters
})

export default store
