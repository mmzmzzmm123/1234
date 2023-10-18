const ReturnCode = {
	NOT_LOGIN: 401,
	FORBIDDEN: 403,
	SUCCESS: 200,
	NOT_FOUND: 404
};

const LoadingHelper = {
	loadingCount: 0,
	showLoading(){
		if(!this.loadingCount){
			// #ifdef MP-ALIPAY
			uni.showLoading()
			// #endif
			// #ifndef MP-ALIPAY
			uni.showLoading({
				mask: true
			})
			// #endif
		}
		this.loadingCount++
	},
	hideLoading(){
		this.loadingCount--
		if(!this.loadingCount){
			uni.hideLoading()
		}
	}
}

function HttpClient(option){
	this.baseApiUrl = option.baseApiUrl
	this.loginPage = option.loginPage
	this.loginHandler = option.loginHandler
	this.appName = option.appName
	this.appId = option.appId
	this.token = uni.getStorageSync('token')
	this.tokenTask = null
}

HttpClient.prototype.request = function(url, data, showLoading, method, hideError){
	showLoading = showLoading === undefined || showLoading
	if(showLoading){
		LoadingHelper.showLoading()
	}
	return new Promise((resolve, reject) => {
		uni.request({
			header: {
				Authorization: this.token
			},
			method: method ? method : 'POST',
			url: this.baseApiUrl + url,
			data: data,
			success: res => {
				const response = res.data
				const code = response.code
				const responseData = response.data === undefined ? response : response.data
				if(response.code == ReturnCode.SUCCESS){
					resolve(responseData)
				}else if(response.code == ReturnCode.NOT_LOGIN){
					this.token = ''
					if(this.tokenTask){
						this.tokenTask.then(()=>{
							this.request(url, data, showLoading, method, hideError).then(resolve)
						})
					}
					else if(this.loginPage){
						uni.reLaunch({
							url: this.loginPage
						})
					}else if(this.loginHandler){
						this.loginHandler().then((code)=>{
							this.getToken(code).then(()=>{
								this.request(url, data, showLoading, method, hideError).then(resolve)
							})
						})
					}
				}else if(hideError){
					reject(responseData)
				}else{
					uni.showModal({
						showCancel: false,
						content: responseData.msg,
						complete: ()=>{
							reject(responseData)
						}
					})
				}
			},
			fail(res) {
				uni.showModal({
					showCancel: false,
					title: '网络请求出错',
					content: res.errMsg,
					complete: ()=>{
						reject(res)
					}
				})
			},
			complete: () => {
				if(showLoading){
					LoadingHelper.hideLoading()
				}
			},
		})
	})
}

HttpClient.prototype.getToken = function(param){
	this.token = ''
	let getTokenUrl = 'login'
	if(param.code){
		getTokenUrl = 'wxLogin'
		param.appId = this.appId
	}
	this.tokenTask = this.request(getTokenUrl, param).then((auth)=>{
		this.token = 'Bearer ' + auth.token
		uni.setStorage({
			key: 'token',
			data: auth.token
		})
		this.tokenTask = null
	})
	return this.tokenTask
}


export default HttpClient
