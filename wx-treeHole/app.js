const { Parser } = require("./libs/svgaplayer.weapp")
import storage from "./utils/storage";
import storageConstant from "./constans/storageConstant";
import userApi from "./apis/user/userApi";
import userLevelApi from "./apis/user/userLevelApi";
import staffApi from "./apis/staff/staffApi";
import serviceInfoApi from "./apis/service/serviceInfoApi";
import platformApi from "./apis/platform/platformApi";
import dictApi from "./apis/platform/dictApi";
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
    this.getIfHidePrivacyItem();
    // 用户登录
    this.userLogin();
    // 用户等级配置
    this.getUserLevelConfig();
    // 员工等级配置
    this.getStaffLevelConfig();
    // 服务数据加载
    this.loadServiceInfoData();
    // 加载平台礼物数据
    this.loadPlatformGift();
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
   * 用户登录
   */
  userLogin: function () {
    // 获取缓存中用户信息
    let userInfo = storage.get(storageConstant.userInfo, null);
    if (userInfo != null) {
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
   * 登录成功事件
   */
  loginOnSuccess: function (res) {
    console.log("用户登录结果：", res);
    let data = res.data;
    this.globalData.userInfo = data;
    storage.set(storageConstant.userInfo, null, data, 0);
    // 加载用户的点赞收藏数据
    userApi.selectUserLikeData(null, this.selectUserLikeDataOnSuccess, null);
  },
  selectUserLikeDataOnSuccess: function (res) {
    this.globalData.userLikeStaffUserIdList = res.data.likeStaffUserIdList;
    this.globalData.userLikeStaffTrendsIdList = res.data.likeStaffTrendsIdList;
  },
  /**
   * 获取用户等级配置信息
   */
  getUserLevelConfig: function () {
    let userLevelConfigList = storage.get(storageConstant.userLevelConfigList, null);
    if (userLevelConfigList != null) {
      this.globalData.userLevelConfig = userLevelConfigList;
    }
    userLevelApi.selectUserLevelConfig(null, this.getUserLevelConfigOnSuccess, null);
  },
  getUserLevelConfigOnSuccess: function (res) {
    console.log("获取用户等级配置结果：", res);
    let data = res.data;
    this.globalData.userLevelConfig = data;
    storage.set(storageConstant.userLevelConfigList, null, data, 0);
  },
  /**
   * 获取员工等级配置
   */
  getStaffLevelConfig: function () {
    let staffLevelConfigList = storage.get(storageConstant.staffLevelConfigList, null);
    if (staffLevelConfigList != null) {
      this.globalData.staffLevelConfig = staffLevelConfigList;
    }
    staffApi.selectStaffLevelConfig(null, this.getStaffLevelConfigOnSuccess, null);
  },
  getStaffLevelConfigOnSuccess: function (res) {
    console.log("获取员工等级配置结果：", res);
    let data = res.data;
    this.globalData.staffLevelConfig = data;
    storage.set(storageConstant.staffLevelConfigList, null, data, 0);
  },
  /**
   * 加载服务数据 
   */
  loadServiceInfoData: function () {
    let serviceList = storage.get(storageConstant.serviceList, null);
    if (serviceList != null) {
      this.globalData.serviceList = serviceList;
    }
    serviceInfoApi.select(null, this.loadServiceInfoDataOnSuccess, null);
  },
  loadServiceInfoDataOnSuccess: function (res) {
    console.log("获取服务数据：", res);
    this.globalData.serviceList = res.data;
    storage.set(storageConstant.serviceList, null, res.data, 0);
  },
  /**
   * 加载平台礼物
   */
  loadPlatformGift: function () {
    let giftList = storage.get(storageConstant.giftList, null);
    if (giftList != null) {
      this.globalData.giftList = giftList;
    }
    platformApi.selectGiftList(null, this.loadPlatformGiftOnSuccess, null);
  },
  loadPlatformGiftOnSuccess: function (res) {
    console.log("加载平台礼物", res);
    this.globalData.giftList = res.data;
    storage.set(storageConstant.giftList, null, res.data, 0);
    this.initPlayerData(res.data);
  },
  /**
   * 初始化礼物数据
   */
  async initPlayerData(list) {
    if (this.globalData.parserArr.length > 0) {
      return;
    }
    for (let index in list) {
      const parser = new Parser();
      const videoItem = await parser.load(list[index].videoUrl);
      await this.globalData.parserArr.push(videoItem);
    }
  },
  /**
   * 更新全局店员信息
   */
  updateStaffInfo: function (res) {
    console.log("加载店员数据完成", res);
    this.globalData.staffInfo = res.data;
    if (res.data != null) {
      storage.set(storageConstant.staffInfo, null, res.data, 0);
    } else {
      storage.removeStorageSync(storageConstant.staffInfo);
    }
  },
  getIfHidePrivacyItem: function () {
    dictApi.getIfHidePrivacyItem(null, this.getIfHidePrivacyItemOnSuccess, null);
  },
  getIfHidePrivacyItemOnSuccess:function(res){
    this.globalData.hidePrivacy = res.msg;
  },
  /**
   * 全局参数
   */
  globalData: {
    // 登录用户信息
    userInfo: null,
    // 店员信息
    staffInfo: null,
    // 用户收藏店员记录
    userLikeStaffUserIdList: [],
    // 用户点赞店员动态记录
    userLikeStaffTrendsIdList: [],
    // 用户等级配置
    userLevelConfig: null,
    // 员工等级配置
    staffLevelConfig: null,
    // 服务数据
    serviceList: [],
    // 礼物数据
    giftList: [],
    // 拉取礼物初始化数据
    parserArr: [],
    // 全局音频播放器对象
    audioContext: wx.createInnerAudioContext(), 
    // 隐藏
    hidePrivacy: "0"
  }
})
