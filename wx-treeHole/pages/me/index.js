const app = getApp();
import storage from "../../utils/storage";
import storageConstant from "../../constans/storageConstant";
import globalConstant from "../../constans/globalConstant";
import userApi from "../../apis/user/userApi";
import userLevelApi from "../../apis/user/userLevelApi";
import staffApi from "../../apis/staff/staffApi";
import serviceInfoApi from "../../apis/service/serviceInfoApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    stateBarHeight: "20px", //状态栏高度
    barHeight: "44.8px", //状态栏标题高度
    showStateBarHeight: 0, //展示状态栏高度
    barThemeColor: null, // 状态栏主题颜色
    themecolor: "#555d8c", //主题颜色
    refreshState: false, //下拉刷新状态
    userInfo: app.globalData.userInfo,
    staffInfo: null, // 员工信息
    userLevelConfig: app.globalData.userLevelConfig, // 用户等级配置
    userLevelData: null, // 员工等级数据
    userLikeStaffUserIdList: app.globalData.userLikeStaffUserIdList, // 用户收藏店员数据集合
    userLikeStaffTrendsIdList: app.globalData.userLikeStaffTrendsIdList, // 用户点赞店员动态集合
    staffServiceDrawer: false, // 店员服务管理容器状态
    serviceList: app.globalData.serviceList, // 全局服务信息数据
    staffServiceIds: [], // 店员开启服务id集合
    ifHide: app.globalData.hidePrivacy
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this;
    wx.createSelectorQuery().selectAll('.bView').boundingClientRect(function (rect) {
      let divHeight = rect[0].height;
      wx.getSystemInfo({
        success: function (res) {
          let sysHeight = res.windowHeight;
          if (divHeight != sysHeight) {
            divHeight = sysHeight;
          }
        }
      });
      that.setData({
        pWidth: rect[0].width,
        pHeight: divHeight
      })
    }).exec();
    this.setData({
      stateBarHeight: storage.get(storageConstant.stateBarHeight, null),
      barHeight: globalConstant.titleBarHeight,
      showStateBarHeight: (Number(storage.get(storageConstant.stateBarHeight, null)) + Number(globalConstant.titleBarHeight)) - 10
    })
    this.loadUserInfo();
  },
  onShow: function () {
    // 加载用户信息
    this.setData({
      userInfo: app.globalData.userInfo,
      userLevelConfig: app.globalData.userLevelConfig,
      ifHide: app.globalData.hidePrivacy
    })
    // 处理用户等级数据
    this.handleUserLevelData();
    // 请求用户关联数据
    if (this.data.userInfo != null) {
      let userId = this.data.userInfo.id;
      // 加载点赞收藏数据
      userApi.selectUserLikeData(null, this.selectUserLikeDataOnSuccess, null);
      // 加载员工信息
      staffApi.selectByUserId({ userId: userId }, null, this.selectStaffInfoOnSuccess, null);
    }
    // 服务数据
    let serviceList = this.data.serviceList;
    if (serviceList == null || serviceList.length <= 0) {
      serviceInfoApi.select(null, this.loadServiceInfoOnSuccess, null);
    } else {
      this.setData({
        serviceList: app.globalData.serviceList
      })
    }
  },
  /**
   * 加载服务数据
   */
  loadServiceInfoOnSuccess: function (res) {
    app.loadServiceInfoDataOnSuccess(res);
    this.setData({
      serviceList: res.data
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 请求员工信息数据完成
   */
  selectStaffInfoOnSuccess: function (res) {
    this.setData({
      staffInfo: res.data
    })
    app.updateStaffInfo(res);
    if (res.data != null) {
      // 加载员工服务配置数据
      staffApi.selectServiceConfigIds({ userId: this.data.userInfo.id }, null, this.selectServiceConfigIdsOnSuccess, null);
    }
  },
  /**
   * 加载店员服务配置信息成功
   */
  selectServiceConfigIdsOnSuccess: function (res) {
    this.setData({
      staffServiceIds: res.data
    })
  },
  /**
   * 请求用户点赞收藏数据成功
   */
  selectUserLikeDataOnSuccess: function (res) {
    app.globalData.userLikeStaffUserIdList = res.data.likeStaffUserIdList;
    app.globalData.userLikeStaffTrendsIdList = res.data.likeStaffTrendsIdList;
    this.setData({
      userLikeStaffUserIdList: res.data.likeStaffUserIdList,
      userLikeStaffTrendsIdList: res.data.likeStaffTrendsIdList
    })
  },
  /**
   * 登录事件
   */
  login: function () {
    let that = this;
    wx.showLoading({
      title: '正在登录',
      mask: true
    })
    // 用户自动登录
    wx.login({
      complete: (res) => {
        if (res.errMsg == "login:ok") {
          userApi.wxMiniLogin({ "code": res.code }, null, that.loginOnSuccess, that.loginOnFailed);
        } else {
          wx.hideLoading();
          wx.showToast({
            title: '登录失败',
            icon: "error"
          })
        }
      }
    })
  },
  loginOnSuccess: function (res) {
    app.loginOnSuccess(res);
    this.setData({
      userInfo: res.data
    })
    wx.hideLoading();
    // 加载用户等级配置
    this.loadUserLevelConfig();
  },
  loginOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '登录失败',
      icon: "error"
    })
  },
  /**
   * 加载app.js中存放的用户信息
   */
  loadUserInfo: function () {
    let that = this;
    let i = 0;
    let loadUserInfoTimer = setInterval(function () {
      if (i > 20) {
        clearInterval(loadUserInfoTimer);
      }
      let userInfo = app.globalData.userInfo;
      if (userInfo != null) {
        that.setData({
          userInfo: userInfo
        })
        clearInterval(loadUserInfoTimer);
        // 处理用户等级数据
        that.handleUserLevelData();
      }
      i++;
    }, 1000)
  },
  /**
   * 加载用户等级配置
   */
  loadUserLevelConfig: function () {
    let onStart = this.loadUserLevelConfigOnStart;
    // 判断当前的数据是否为空
    let userLevelConfig = app.globalData.userLevelConfig;
    if (userLevelConfig != null) {
      this.setData({
        userLevelConfig: userLevelConfig
      })
      onStart = null;
    }
    // 请求服务器加载
    userLevelApi.selectUserLevelConfig(onStart, this.loadUserLevelConfigOnSuccess, this.loadUserLevelConfigOnFailed);
  },
  loadUserLevelConfigOnStart: function () {
    wx.showLoading({
      title: '加载等级中',
      mask: true
    })
  },
  loadUserLevelConfigOnSuccess: function (res) {
    wx.hideLoading();
    app.getUserLevelConfigOnSuccess(res);
    this.setData({
      userLevelConfig: res.data
    })
    // 处理用户等级数据
    this.handleUserLevelData();
  },
  loadUserLevelConfigOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '加载用户等级配置失败',
      icon: "none"
    })
  },
  /**
   * 处理用户等级相关数据
   */
  handleUserLevelData: function () {
    let userInfo = this.data.userInfo;
    if (userInfo == null) {
      return;
    }
    let userLevelConfig = this.data.userLevelConfig;
    if (userLevelConfig == null) {
      return;
    }
    let userLevelVo = userInfo.userLevelVo;
    let currentLevel, nextLevel = null;
    for (let i = 0; i < userLevelConfig.length; i++) {
      let temp = userLevelConfig[i];
      if (temp.id == userLevelVo.levelConfigId) {
        currentLevel = temp;
        if (i != userLevelConfig.length - 1) {
          nextLevel = userLevelConfig[i + 1];
        } else {
          nextLevel = temp;
        }
      }
    }
    if (currentLevel == null || nextLevel == null) {
      return;
    }
    let userLevelData = {
      currLevel: userLevelVo.currentLevel,
      nextLevel: nextLevel.level,
      nextThreshold: nextLevel.threshold,
      upgradationThreshold: (Number(nextLevel.threshold) - Number(userLevelVo.totalPoints)).toFixed(2),
      upgradationPercent: (Number(userLevelVo.currentPoints) / Number(nextLevel.threshold) * 100)
    };
    this.setData({
      userLevelData: userLevelData
    })
  },
  /**
   * 前往用户资料页面
   */
  toUserData: function () {
    wx.navigateTo({
      url: '../../userPackages/page/userData/index',
    })
  },
  /**
   * 前往用户账号管理页面
   */
  toUserAccount: function () {
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../userPackages/page/accounts/index',
    })
  },
  /**
   * 前往充值页面
   */
  toRecharge: function () {
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../platformPackages/page/recharge/index',
    })
  },
  /**
   * 前往店员资料管理页
   */
  toStaffInfo: function () {
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/staffInfo/index',
    })
  },
  /**
   * 前往店员动态管理页
   */
  toStaffTrends: function () {
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/staffTrends/index',
    })
  },
  /**
   * 前往店员订单页
   */
  toStaffOrder:function(){
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/staffOrder/index',
    })
  },
  /**
   * 前往店员薪资页
   */
  toStaffSalary:function(){
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/staffSalary/index',
    })
  },
  /**
   * 前往店员组长页面
   */
  toStaffLeader:function(){
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/staffLeader/index',
    })
  },
  /**
   * 前往申请员工页面
   */
  toApplyStaff: function () {
    if (this.data.userInfo == null) {
      wx.showToast({
        title: '亲爱的，先登录哟',
        icon: "none"
      })
      return;
    }
    let staffInfo = this.data.staffInfo;
    if (staffInfo != null && staffInfo.state != 2) {
      if (staffInfo.state == '-1') {
        wx.showToast({
          title: '抱歉，您暂不可申请哟',
          icon: "none"
        })
      } else if (staffInfo.state == 0) {
        wx.showToast({
          title: '亲爱的，您已经是店员啦',
          icon: "none"
        })
      } else if (staffInfo.state == 1) {
        wx.showToast({
          title: '亲爱的，您的申请已经提交上去啦，请耐心等候通知哟',
          icon: "none"
        })
      } else {
        wx.showToast({
          title: '亲爱的，暂不能申请哟',
          icon: "none"
        })
      }
      return;
    }
    wx.navigateTo({
      url: '../../staffPackages/page/applyStaff/index',
    })
  },
  /**
   * 店员服务管理
   */
  staffServiceManage: function () {
    this.setData({
      staffServiceDrawer: true
    })
  },
  /**
   * 改变店员服务管理容器状态
   */
  changeStaffServiceDrawer: function () {
    let temp = this.data.staffServiceDrawer;
    this.setData({
      staffServiceDrawer: !temp
    })
  },
  /**
   * 改变店员在线状态
   */
  staffOnlineChange: function (e) {
    let staffInfo = this.data.staffInfo;
    let state = e.detail.value ? "Y" : "N";
    let params = {
      userId: staffInfo.userId,
      ifOnline: state
    }
    staffApi.update(params, this.updateStaffOnlineOnState, this.updateStaffOnlineOnSuccess, this.updateStaffOnlineOnFailed, this.updateStaffOnlineOnWarn)
  },
  updateStaffOnlineOnState: function () {
    wx.showLoading({
      title: '正在修改',
      mask: true
    })
  },
  updateStaffOnlineOnSuccess: function (res) {
    wx.hideLoading();
    let staffInfo = this.data.staffInfo;
    staffInfo.ifOnline = staffInfo.ifOnline == 'Y' ? 'N' : 'Y';
    this.setData({
      staffInfo: staffInfo
    })
    app.globalData.staffInfo = staffInfo;
    storage.set(storageConstant.staffInfo, null, staffInfo, 0);
  },
  updateStaffOnlineOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  updateStaffOnlineOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 店员服务处理事件
   */
  staffServiceCli: function (e) {
    let serviceId = e.currentTarget.dataset.id;
    staffApi.handleServiceId({ serviceId: serviceId }, this.handleServiceIdOnStart, this.handleServiceIdOnSuccess, this.handleServiceIdOnFailed);
  },
  handleServiceIdOnStart: function () {
    wx.showLoading({
      title: '正在保存',
      mask: true
    })
  },
  handleServiceIdOnSuccess: function (res) {
    wx.hideLoading();
    this.setData({
      staffServiceIds: res.data
    })
  },
  handleServiceIdOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '操作失败',
      icon: "none"
    })
  },
  /**
   * 店员消息订阅
   */
  messageSubscription: function () {
    wx.requestSubscribeMessage({
      tmplIds: ['na2EJEzJi9jqzBVfrj6IC18r7fItQAnzQ-U4qn1RQ4o'],
      success: function (res) {
        if (res.na2EJEzJi9jqzBVfrj6IC18r7fItQAnzQ-U4qn1RQ4o == 'accept') {
          wx.showToast({
            title: '订阅成功',
            icon: "success"
          })
        } else {
          wx.showToast({
            title: '订阅失败',
            icon: "error"
          })
        }
      }, complete: function (res) {
        wx.showToast({
          title: '订阅失败',
          icon: "error"
        })
      }
    })
  },
  /**
   * 前往订单页
   */
  toOrderListPage:function(e){
    wx.navigateTo({
      url: '../../orderPackages/page/list/index?index='+e.currentTarget.dataset.index,
    })
  },
  /**
   * 滚动事件
   */
  scrollView: function (e) {
    if (e.detail.scrollTop > this.data.showStateBarHeight && this.data.barThemeColor == null) {
      this.setData({
        barThemeColor: "linear-gradient(to right,#a79de0,#b7b0dd)"
      })
    }
    if (this.data.showStateBarHeight > e.detail.scrollTop && this.data.barThemeColor != null) {
      this.setData({
        barThemeColor: null
      })
    }
  },
  /**
   * 下拉刷新数据
   */
  refresh: function () {
    let that = this;
    setTimeout(function(){
      that.setData({
        refreshState: false
      }, 2000)
    })
    this.loadUserInfo();
    this.loadUserLevelConfig();
    // 加载用户信息
    this.setData({
      userInfo: app.globalData.userInfo,
      userLevelConfig: app.globalData.userLevelConfig,
      ifHide: app.globalData.hidePrivacy
    })
    // 请求用户关联数据
    if (this.data.userInfo != null) {
      let userId = this.data.userInfo.id;
      // 加载点赞收藏数据
      userApi.selectUserLikeData(null, this.selectUserLikeDataOnSuccess, null);
      // 加载员工信息
      staffApi.selectByUserId({ userId: userId }, null, this.selectStaffInfoOnSuccess, null);
    }
    // 服务数据
    let serviceList = this.data.serviceList;
    if (serviceList == null || serviceList.length <= 0) {
      serviceInfoApi.select(null, this.loadServiceInfoOnSuccess, null);
    } else {
      this.setData({
        serviceList: app.globalData.serviceList
      })
    }
  },
  /**
   * 联系我们
   */
  contactUs:function(){
    
  }
})