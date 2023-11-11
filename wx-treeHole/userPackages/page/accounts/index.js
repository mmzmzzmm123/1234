const app = getApp();
import userAccountApi from "../../../apis/user/userAccount";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    userInfo: app.globalData.userInfo,
    userAccounts: [],
    loadDataState: false,
    formDrawer: false,
    serviceProviderArr: [
      {
        "label": "微信",
        "value": 0
      }
    ],
    account: {
      accountServiceProvider: "0",
      num: null,
      ifDefault: "N"
    },
    type: 0, // 进入类型：0常规，1选择记录并返回

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    let that = this;
    wx.createSelectorQuery().selectAll('.bView').boundingClientRect(function (rect) {
      that.setData({
        pWidth: rect[0].width,
        pHeight: rect[0].height
      })
    }).exec();
    if (options.type != null) {
      this.setData({
        type: options.type
      })
    }
  },
  onShow: function () {
    this.loadData(this.loadDataOnStart);
  },
  /**
   * 加载数据
   */
  loadData: function (onStart) {
    let userInfo = this.data.userInfo;
    if (userInfo == null) {
      wx.navigateBack();
      return;
    }
    userAccountApi.selectByUserId({ userId: userInfo.id }, onStart, this.loadDataOnSuccess, this.loadDataOnFailed);
  },
  loadDataOnStart: function () {
    this.setData({
      loadDataState: true
    })
  },
  loadDataOnSuccess: function (res) {
    let that = this;
    this.setData({
      userAccounts: res.data
    })
    setTimeout(function () {
      that.setData({
        loadDataState: false
      })
    }, 500)
  },
  loadDataOnFailed: function (res) {
    let that = this;
    setTimeout(function () {
      that.setData({
        loadDataState: false
      })
    }, 500)
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  /**
   * 添加容器改变事件
   */
  changeFormDrawer: function () {
    let temp = this.data.formDrawer;
    this.setData({
      formDrawer: !temp
    })
  },
  /**
   * 添加事件
   */
  addCli: function () {
    this.setData({
      account: {
        accountServiceProvider: "0",
        num: null,
        ifDefault: "N"
      },
      formDrawer: true
    })
  },
  /**
   * 绿泡泡账号改变事件
   */
  carNumberInput: function (e) {
    this.setData({
      ["account.num"]: e.detail.value
    })
  },
  /**
   * 账号服务商改变事件
   */
  serviceProviderChange: function (e) {
    this.setData({
      ["account.accountServiceProvider"]: e.detail.value
    })
  },
  /**
   * 是否默认改变事件
   */
  ifDefaultChange: function (e) {
    this.setData({
      ["account.ifDefault"]: e.detail.value ? 'Y' : 'N'
    })
  },
  /**
   * 表单提交
   */
  submit: function () {
    let account = this.data.account;
    if (account.num == null || account.num == "") {
      wx.showToast({
        title: '亲爱的，绿泡泡不能为空哟',
        icon: "none"
      })
      return;
    }
    let userInfo = this.data.userInfo;
    account.userId = userInfo.id;
    if (account.id != null) {
      userAccountApi.update(account, this.submitOnState, this.submitOnSuccess, this.submitOnFailed);
      return;
    }
    userAccountApi.add(account, this.submitOnState, this.submitOnSuccess, this.submitOnFailed, this.submitOnWarn);
  },
  submitOnState: function () {
    wx.showLoading({
      title: '正在保存',
      mask: true
    })
  },
  submitOnSuccess: function (res) {
    wx.hideLoading();
    this.setData({
      formDrawer: false
    })
    wx.showToast({
      title: '保存成功',
      icon: "none"
    })
    this.loadData(null);
  },
  submitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  submitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 修改默认记录
   */
  updateDefault: function (e) {
    let params = {
      id: e.currentTarget.dataset.id,
      ifDefault: e.detail.value ? "Y" : "N"
    }
    userAccountApi.update(params, this.submitOnState, this.submitOnSuccess, this.submitOnFailed);
  },
  /**
   * 点击修改
   */
  updateCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let tempObj = this.data.userAccounts[index];
    let type = this.data.type;
    if (type == 1) {
      let pages = getCurrentPages();
      let prevPage = pages[pages.length - 2];
      prevPage.chooseUserAccountCallBack(tempObj);
      wx.navigateBack({
        delta: 0,
      })
      return;
    }
    this.setData({
      account: tempObj,
      formDrawer: true
    })
  },
  /**
   * 删除点击事件
   */
  deleteCli: function (e) {
    let id = e.currentTarget.dataset.id;
    userAccountApi.deleteById({ id: id }, this.deleteByIdOnStart, this.deleteByIdOnSuccess, this.submitOnFailed);
  },
  deleteByIdOnStart: function () {
    wx.showLoading({
      title: '正在删除',
      mask: true
    })
  },
  deleteByIdOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
  },
})