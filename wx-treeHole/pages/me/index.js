const app = getApp();
const loadUserInfoTimer = null;
import storage from "../../utils/storage";
import storageConstant from "../../constans/storageConstant";
import globalConstant from "../../constans/globalConstant";
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
    userInfo: null //用户信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this;
    wx.createSelectorQuery().selectAll('.bView').boundingClientRect(function (rect) {
      that.setData({
        pWidth: rect[0].width,
        pHeight: rect[0].height,
        userInfo: app.globalData.userInfo
      })
    }).exec();
    this.setData({
      stateBarHeight: storage.get(storageConstant.stateBarHeight, null),
      barHeight: globalConstant.titleBarHeight,
      showStateBarHeight: (Number(storage.get(storageConstant.stateBarHeight, null)) + Number(globalConstant.titleBarHeight)) - 10
    })
    // 20秒内，每秒去读取获取用户信息
    let i = 0;
    this.loadUserInfoTimer = setInterval(function () {
      if (i > 10) {
        clearInterval(this.loadUserInfoTimer);
      }
      that.loadUserInfo();
      i++;
    }, 1000)
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 加载app.js中存放的用户信息
   */
  loadUserInfo: function () {
    let userInfo = app.globalData.userInfo;
    if(userInfo != null){
      this.setData({
        userInfo: userInfo
      })
      clearInterval(this.loadUserInfoTimer);
    }
  },
  /**
   * 前往用户资料页面
   */
  toUserData: function () {
    wx.navigateTo({
      url: '../../userPackages/page/userData/index',
    })
  }
})