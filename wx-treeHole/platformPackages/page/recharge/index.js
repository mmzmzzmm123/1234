const app = getApp();
import storageConstant from "../../../constans/storageConstant";
import storageUtil from "../../../utils/storage";
import platformApi from "../../../apis/platform/platformApi";
import userLevelApi from "../../../apis/user/userLevelApi";
import paymentApi from "../../../apis/payment/paymentApi";
import userApi from "../../../apis/user/userApi";
var wxParse = require('../../../wxParse/wxParse');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    rechargeData: [], // 充值配置数据
    userInfo: app.globalData.userInfo, // 用户信息
    userLevelConfigList: [], // 用户等级配置信息
    userLevelData: null, // 用户等级数据
    gradeEquityDrawer: false, // 用户等级权益容器状态
    userAgreementDrawer: false, // 用户协议容器状态
    rechargeAmount: null, // 自定义充值金额
    rechargeConfigIndex: null, // 选择充值配置项下标
    refreshState: false, // 下拉刷新状态
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
    // 获取充值配置数据
    let storageData = storageUtil.get(storageConstant.rechargeConfig, null);
    let onStart = null;
    if (storageData == null) {
      onStart = this.loadRechargeConfigDataOnStart;
    } else {
      this.setData({
        rechargeData: storageData
      })
    }
    this.loadRechargeConfigData(onStart, this.loadRechargeConfigDataOnFailed);
    // 加载用户等级配置信息
    let userLevelConfigList = storageUtil.get(storageConstant.userLevelConfigList, null);
    if (userLevelConfigList != null) {
      this.setData({
        userLevelConfigList: userLevelConfigList
      })
      this.handleUserLevelData();
    } else {
      userLevelApi.selectUserLevelConfig(null, this.selectUserLevelConfigOnSuccess, null);
    }
  },
  onShow:function(){
    // 加载用户信息
    this.setData({
      userInfo: app.globalData.userInfo
    })
  },
  selectUserLevelConfigOnSuccess: function (res) {
    this.setData({
      userLevelConfigList: res.data
    })
    app.getUserLevelConfigOnSuccess(res);
    this.handleUserLevelData();
  },
  /**
   * 加载充值配置数据
   */
  loadRechargeConfigData: function (onStart, onFiled) {
    platformApi.selectRechargeConfig(onStart, this.loadRechargeConfigDataOnSuccess, onFiled);
  },
  loadRechargeConfigDataOnStart: function () {
    wx.showLoading({
      title: '加载中',
      mask: true
    })
  },
  loadRechargeConfigDataOnSuccess: function (res) {
    wx.hideLoading();
    this.setData({
      rechargeData: res.data
    })
    storageUtil.set(storageConstant.rechargeConfig, null, res.data, null);
  },
  loadRechargeConfigDataOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '加载失败',
      icon: "error"
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
    let userLevelConfigList = this.data.userLevelConfigList;
    if (userLevelConfigList == null) {
      return;
    }
    let userLevelVo = userInfo.userLevelVo;
    let currentLevel, nextLevel = null;
    for (let i = 0; i < userLevelConfigList.length; i++) {
      let temp = userLevelConfigList[i];
      if (temp.id == userLevelVo.levelConfigId) {
        currentLevel = temp;
        if (i != userLevelConfigList.length - 1) {
          nextLevel = userLevelConfigList[i + 1];
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
   * 用户等级权益改变事件
   */
  changeGradeEquityDrawer: function () {
    let temp = this.data.gradeEquityDrawer;
    this.setData({
      gradeEquityDrawer: !temp
    })
  },
  /**
   * 选择充值项
   */
  chooseRechargeItem: function (e) {
    this.setData({
      rechargeConfigIndex: e.currentTarget.dataset.index,
      rechargeAmount: null
    })
  },
  /**
   * 用户协议容器改变事件
   */
  changeUserAgreementDrawer: function () {
    let temp = this.data.userAgreementDrawer;
    if (temp) {
      this.setData({
        userAgreementDrawer: false
      })
    } else {
      // 判断有没有数据，没有就需要去加载
      let userAgreement = storageUtil.get(storageConstant.userAgreement, null);
      if (userAgreement != null) {
        wxParse.wxParse('userAgreement', 'html', userAgreement, this, 5);
        this.setData({
          userAgreementDrawer: true
        })
      } else {
        platformApi.selectTextContentByType({ type: 0 }, this.selectTextContentByTypeOnStart, this.selectTextContentByTypeOnSuccess, this.selectTextContentByTypeOnFailed);
      }
    }
  },
  selectTextContentByTypeOnStart: function () {
    wx.showLoading({
      title: '加载中',
      mask: true
    })
  },
  selectTextContentByTypeOnSuccess: function (res) {
    wx.hideLoading();
    wxParse.wxParse('userAgreement', 'html', res.msg, this, 5);
    this.setData({
      userAgreementDrawer: true
    })
    storageUtil.set(storageConstant.userAgreement, null, res.msg, 0);
  },
  selectTextContentByTypeOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  /**
   * 自定义充值金额输入事件
   */
  rechargeAmountChange:function(e){
    this.setData({
      rechargeAmount: parseInt(e.detail.value)
    })
  },
  /**
   * 立即充值事件
   */
  submitRecharge:function(){
    let that = this;
    let rechargeAmount = this.data.rechargeAmount;
    let rechargeConfigIndex = this.data.rechargeConfigIndex;
    if(rechargeAmount == null && rechargeConfigIndex == null){
      wx.showToast({
        title: '亲爱的，请输入您要充值的枫叶币哟',
        icon: "none"
      })
      return;
    }
    let params = {
      amount: null,
      rechargeConfigId: null
    };
    let amount = 0;
    let rechargeTotle = 0;
    if(rechargeAmount != null){
      amount = rechargeAmount;
      rechargeTotle = rechargeAmount;
      params.amount = rechargeAmount;
    }
    if(rechargeConfigIndex != null){
      let rechargeData = this.data.rechargeData[rechargeConfigIndex];
      amount = rechargeData.amount;
      rechargeTotle = rechargeData.amount + rechargeData.giveAmount;
      params.rechargeConfigId = rechargeData.id;
    }
    wx.showModal({
      title: '温馨提示',
      content: '亲爱的，本次订单金额为：'+amount+"元，将充值 "+rechargeTotle+" 个枫叶币",
      confirmText: "立即充值",
      complete: (res) => {
        if (res.confirm) {
          paymentApi.recharge(params,that.rechargeOnStart,that.rechargeOnSuccess,that.rechargeOnFailed,that.rechargeOnWarn);
        }
      }
    })
  },
  rechargeOnStart:function(){
    wx.showLoading({
      title: '正在充值',
      mask: true
    })
  },
  rechargeOnSuccess:function(res){
    let that = this;
    wx.hideLoading();
    wx.requestPayment({
      "nonceStr": res.data.nonceStr,
      "package": res.data.packageValue,
      "signType": res.data.signType,
      "paySign": res.data.paySign,
      "timeStamp": res.data.timeStamp,
      success:function(res){
        // 拉取最新的用户信息
        that.getNewUserInfo(null,null);
        wx.showModal({
          title: '温馨提示',
          content: '亲爱的，充值成功啦，枫叶币即将到账哟，充值高峰可能会导致延迟1-2分钟导致，感谢支持与理解',
          confirmText: "好的哟",
          showCancel: false,
          complete: (res) => {
            that.setData({
              rechargeAmount: null,
              rechargeConfigIndex: null
            })
            // 拉取最新的用户信息
            that.getNewUserInfo(null,null);
          }
        })
      },fail:function(res){
        if(res.errMsg == "requestPayment:fail cancel"){ 
          //取消支付,关闭当前页面并跳转至订单页
          wx.showToast({
            title: '支付取消啦',
            icon: "none",
            duration: 2500
          })
        }else{
          wx.showToast({
            title: '亲爱的，支付失败了哟',
            icon: "none",
            duration: 2500
          })
        }
      }
    })
  },
  rechargeOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '充值失败，请重试',
      icon: "none"
    })
  },
  rechargeOnWarn:function(res){
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 加载最新的用户数据
   */
  getNewUserInfo:function(onStart,onFailed){
    userApi.getNewUserInfo(onStart,this.getNewUserInfoOnSuccess,onFailed);
  },
  getNewUserInfoOnStart:function(){
    wx.showLoading({
      title: '正在加载',
      mask: true
    })
  },
  getNewUserInfoOnSuccess:function(res){
    wx.hideLoading();
    let data = res.data;
    app.globalData.userInfo = data;
    storageUtil.set(storageConstant.userInfo, null, data, 0);
    this.setData({
      userInfo: data,
      refreshState: false
    })
    this.handleUserLevelData();
  },
  getNewUserInfoOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  /**
   * 刷新事件
   */
  refresh: function () {
    this.setData({
      refreshState: true
    })
    this.getNewUserInfo(this.getNewUserInfoOnStart,this.getNewUserInfoOnFailed);
    this.loadRechargeConfigData(null,null);
    userLevelApi.selectUserLevelConfig(null, this.selectUserLevelConfigOnSuccess, null);
  },
})