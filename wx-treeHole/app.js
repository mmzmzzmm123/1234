import storage from "./utils/storage";
import storageConstant from "./constans/storageConstant";
import userApi from "./apis/user/userApi";
// app.js
App({
  onLaunch() {
    //版本更新
    this.versionUpdates();
    // 获取手机参数
    wx.getSystemInfo({
      success: function (res) {
        storage.set(storageConstant.stateBarHeight, null, res.statusBarHeight, 0);
      }
    })
    // 获取缓存中用户信息
    let userInfo = storage.get(storageConstant.userInfo, null);
    if(userInfo != null){
      this.globalData.userInfo = userInfo;
    }
    // 用户自动登录
    wx.login({
      complete: (res) => {
        if (res.errMsg == "login:ok") {
          userApi.wxMiniLogin({ "code": res.code }, null, this.loginOnSuccess, null);
        }
      }
    })

  },
  /**
  * 新版本检查更新
  */
  versionUpdates: function () {
    var self = this;
    if (wx.canIUse('getUpdateManager')) {
      const updateManager = wx.getUpdateManager()
      //1. 检查小程序是否有新版本发布
      updateManager.onCheckForUpdate(function (res) {
        if (res.hasUpdate) {
          //检测到新版本，需要更新，给出提示
          wx.showModal({
            title: '更新提示',
            content: '检测到新版本，是否加载最新小程序？',
            success: function (res) {
              if (res.confirm) {
                //2. 用户确定下载更新小程序，小程序下载及更新静默进行
                self.downLoadAndUpdate(updateManager)
              }
            }
          })
        }
      })
    }
  },
  /**
   * 登录成功事件
   */
  loginOnSuccess: function (res) {
    console.log("用户登录结果：", res);
    let data = res.data;
    this.globalData.userInfo = data;
    storage.set(storageConstant.userInfo, null, data, 0);
  },
  /**
   * 全局参数
   */
  globalData: {
    userInfo: null
  }
})
