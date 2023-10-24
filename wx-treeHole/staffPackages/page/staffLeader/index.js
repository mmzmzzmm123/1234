const app = getApp();
import staffApi from "../../../apis/staff/staffApi";
Page({

  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    loadState: false, // 加载状态
    staffData: [], // 队员数据
    staffLevelConfig: app.globalData.staffLevelConfig, //员工等级配置
  },

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
    this.loadData(this.loadDataOnStart);
  },
  onShow:function(){
    // 员工等级数据
    let staffLevelConfig = this.data.staffLevelConfig;
    if (staffLevelConfig == null || staffLevelConfig.length <= 0) {
      // 请求后台加载数据
      staffApi.selectStaffLevelConfig(null, this.staffLevelConfigOnSuccess, null);
    } else {
      this.setData({
        staffLevelConfig: staffLevelConfig
      })
    }
  },
  /**
   * 加载员工配置
   */
  staffLevelConfigOnSuccess: function (res) {
    app.getStaffLevelConfigOnSuccess(res);
    this.setData({
      staffLevelConfig: res.data
    })
  },
  /**
   * 加载数据
   */
  loadData: function (onStart) {
    staffApi.selectTeamMembers(onStart, this.loadDataOnSuccess, this.loadDataOnFailed, this.loadDataOnWarn)
  },
  loadDataOnStart: function () {
    this.setData({
      loadState: true
    })
  },
  loadDataOnSuccess: function (res) {
    this.closeLoadState();
    this.setData({
      staffData: res.data
    })
  },
  loadDataOnFailed: function (res) {
    this.closeLoadState();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  loadDataOnWarn: function (res) {
    this.closeLoadState();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  closeLoadState:function(){
    let that = this;
    setTimeout(function(){
      that.setData({
        loadState: false
      })
    },500)
  }
})