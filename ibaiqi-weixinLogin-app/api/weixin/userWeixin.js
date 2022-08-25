/* 
@author zhangxuDev 
 */

import request from '@/utils/request'

// 测试是否获取到test接口的数据
export function getTest() {
	return request({
		url: '/wxapi/test',
		method: 'get'
	})
}
//微信授权登陆
export function wxlogin(form) {
	console.log("form的值:", form);
	const data = {
		nickName: form.nickName,
		gender: form.gender,
		avatarUrl: form.avatarUrl,
		code: form.code
	}
	console.log("data为转换后的值:", data);
	return request({
		// url: '/wxapi/wxlogin',
		url: '/api/v1/wxUser/wxlogin',
		method: 'post',
		header: {
			'Content-Type': 'application/json'
		} // 已经在request.js里面进行全局设置，也可以在请求里面局部设置其他headers
		,
		data: data
	})
}

//--------------------------------------以下复制过来，不是本页代码，无用，后期删除
// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
	const data = {
		oldPassword,
		newPassword
	}
	return request({
		url: '/system/user/profile/updatePwd',
		method: 'put',
		params: data
	})
}

// 查询用户个人信息
export function getUserProfile() {
	return request({
		url: '/system/user/profile',
		method: 'get'
	})
}

// 修改用户个人信息
export function updateUserProfile(data) {
	return request({
		url: '/system/user/profile',
		method: 'put',
		data: data
	})
}
