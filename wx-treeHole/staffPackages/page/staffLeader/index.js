const app = getApp();
import staffApi from "../../../apis/staff/staffApi";
import serviceInfoApi from "../../../apis/service/serviceInfoApi";
Page({

  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    loadState: false, // 加载状态
    staffData: [], // 队员数据
    staffLevelConfig: app.globalData.staffLevelConfig, //员工等级配置
    serviceList: app.globalData.serviceList, // 服务数据
    operObj: null, // 操作的店员对象
    updateStaffInfoDrawer: false, // 修改店员信息容器状态
    levelList: [ // 等级列表
      { label: '显眼包' },
      { label: '镇店' },
      { label: '首席' },
      { label: '神级' }
    ],
    stateList: [ // 状态列表
      { label: '正常', value: '0' },
      { label: '审核中', value: '1' },
      { label: '不通过', value: '2' },
      { label: '禁用', value: '-1' }
    ]
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
  onShow: function () {
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
  closeLoadState: function () {
    let that = this;
    setTimeout(function () {
      that.setData({
        loadState: false
      })
    }, 500)
  },
  /**
   * 前往查看对应店员的薪资情况
   */
  toSeeStaffSalary: function (e) {
    let userId = e.currentTarget.dataset.userid;
    wx.navigateTo({
      url: "../staffSalary/index?staffUserId=" + userId,
    })
  },
  /**
   * 修改店员信息
   */
  updateStaffInfo: function (e) {
    let index = e.currentTarget.dataset.index;
    let tempObj = this.data.staffData[index];
    this.setData({
      operObj: {
        userId: tempObj.userId,
        state: tempObj.state,
        staffLevel: tempObj.staffLevel,
        ifOnline: tempObj.ifOnline
      },
      updateStaffInfoDrawer: true
    })
  },
  /**
   * 修改店员信息容器状态改变事件
   */
  changeUpdateStaffInfoDrawer: function () {
    let temp = this.data.updateStaffInfoDrawer;
    this.setData({
      updateStaffInfoDrawer: !temp
    })
  },
  /**
   * 店员在线状态修改
   */
  staffOnlineChange: function () {
    let operObj = this.data.operObj;
    let ifOnline = operObj.ifOnline;
    this.setData({
      ['operObj.ifOnline']: ifOnline == 'Y' ? 'N' : 'Y'
    })
  },
  /**
   * 店员等级改变事件
   */
  levelChange: function (e) {
    this.setData({
      ["operObj.staffLevel"]: (Number(e.detail.value) + 1)
    })
  },
  /**
   * 店员状态改变事件
   */
  stateChange: function (e) {
    let index = e.detail.value;
    let stateList = this.data.stateList;
    let value = stateList[index].value;
    this.setData({
      ["operObj.state"]: value
    })
  },
  /**
   * 提交更新数据
   */
  submitUpdate: function () {
    let params = this.data.operObj;
    staffApi.updateTeamMembersInfo(params, this.submitUpdateOnStart, this.submitUpdateOnSuccess, this.submitUpdateOnFailed, this.submitUpdateOnWarn);
  },
  submitUpdateOnStart: function () {
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  submitUpdateOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
    this.setData({
      updateStaffInfoDrawer: false
    })
    wx.showToast({
      title: '更新成功',
      icon: "none"
    })
  },
  submitUpdateOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '提交失败',
      icon: "error"
    })
  },
  submitUpdateOnWarn: function (res) {
    wx.hideLoading()
    wx.showToast({
      title: res.msg,
      icon: 'none'
    })
  },
})