const app = getApp();
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import platformApi from "../../../apis/platform/platformApi";
import distributionApi from "../../../apis/distribution/distributionApi";
import userApi from "../../../apis/user/userApi";
var wxParse = require('../../../wxParse/wxParse');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    bgImgHeight: 0, // 背景图片高度
    currentIndex: 0,
    currentKey: "all",
    tabs: [
      {
        key: "all",
        title: "全部",
        bottomDataLoadState: false, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20
        }
      },
      {
        key: "oneLvel",
        title: "一级分销",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          level: "0"
        }
      },
      {
        key: "twoLevel",
        title: "二级分销",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          level: "1"
        }
      }
    ],
    dataLoadState: false, // 数据加载状态
    userInfo: app.globalData.userInfo, // 用户信息
    distributionAgreementDrawer: false, // 分销协议容器
    commissionRecordDrawer: false, // 分销佣金记录容器
    loadCommissionRecordState: false, // 加载分销佣金记录状态
    commissionRecordData: [], // 分销佣金记录数据
    commissionRecordTotal: 0, // 分销佣金记录总条数
    commissionRecordParams: { // 分销佣金记录请求参数
      pageNum: 1,
      pageSize: 20
    },
    loadCommissionRecordNextPageState: false, // 加载下一页销佣金记录数据
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
    wx.createSelectorQuery().selectAll('.wuxTab').boundingClientRect(function (rect) {
      that.setData({
        tabHeight: rect[0].height
      })
    }).exec();
    this.loadData(this.loadDataOnStart);
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.loadUserInfo();
  },
  /**
   * 加载用户最新信息
   */
  loadUserInfo: function () {
    userApi.getNewUserInfo(null, this.loadUserInfoOnSuccess, null);
  },
  loadUserInfoOnSuccess: function (res) {
    this.setData({
      userInfo: res.data
    })
    app.globalData.userInfo = res.data;
    storage.set(storageConstant.userInfo, null, res.data, 0);
  },
  /**
   * 加载分销关系数据
   */
  loadData: function (onStart) {
    let params = this.buildParams();
    distributionApi.pageDistributionRelation(params, onStart, this.loadDataOnSuccess, this.loadDataOnFailed, this.loadDataOnWarn);
  },
  loadDataOnStart: function () {
    this.setData({
      dataLoadState: true
    })
  },
  loadDataOnSuccess: function (res) {
    this.handleData(res.data);
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
      icon: 'none'
    })
  },
  /**
   * 构建请求参数
   */
  buildParams: function () {
    let currentIndex = this.data.currentIndex;
    let tabs = this.data.tabs;
    let currentTab = tabs[currentIndex];
    let params = currentTab.params;
    return params;
  },
  /**
   * 处理请求回来的数据
   */
  handleData: function (data) {
    let currentIndex = this.data.currentIndex;
    let tabs = this.data.tabs;
    let currentTab = tabs[currentIndex];
    let dataArr = currentTab.data;
    let bottomDataLoadState = currentTab.bottomDataLoadState;
    let newData = data.data;
    if (bottomDataLoadState) {
      for (let index in newData) {
        dataArr.push(newData[index]);
      }
    } else {
      dataArr = newData;
    }
    currentTab.data = dataArr;
    currentTab.total = data.total;
    let currentTabSetDataStr = 'tabs[' + currentIndex + ']';
    this.setData({
      [currentTabSetDataStr]: currentTab
    })
    this.closeLoadState();
  },
  /**
   * 关闭当前tab请求状态
   */
  closeLoadState: function () {
    let that = this;
    let currentIndex = this.data.currentIndex;
    let tabs = this.data.tabs;
    let currentTab = tabs[currentIndex];
    currentTab.refreshState = false;
    currentTab.bottomDataLoadState = false;
    let currentTabSetDataStr = 'tabs[' + currentIndex + ']';
    this.setData({
      [currentTabSetDataStr]: currentTab
    })
    setTimeout(function () {
      that.setData({
        dataLoadState: false
      })
    }, 1000);
  },
  /**
   * 背景图片加载事件
   */
  bgImgLoad: function () {
    let thumbnailHeight = this.data.bgImgHeight;
    if (thumbnailHeight != null && thumbnailHeight > 0) {
      return;
    }
    let that = this;
    setTimeout(function () {
      wx.createSelectorQuery().selectAll('.bgImg').boundingClientRect(function (rect) {
        that.setData({
          bgImgHeight: rect[0].height
        })
      }).exec();
    }, 500)
  },
  /**
   * 头部tab改变事件
   */
  onTabsChange: function (e) {
    const {
      key
    } = e.detail
    const index = this.data.tabs.map((n) => n.key).indexOf(key);
    this.setData({
      currentIndex: index,
      currentKey: key
    })
  },
  /**
   * 页面改变事件
   */
  onSwiperChange: function (e) {
    let tabs = this.data.tabs;
    let currentIndex = e.detail.current;
    this.setData({
      currentIndex: currentIndex,
      currentKey: tabs[currentIndex].key
    });
    this.loadData(null);
  },
  /**
   * 下拉刷新数据
   */
  refresh: function () {
    let currentIndex = this.data.currentIndex;
    let tabs = this.data.tabs;
    let currentTab = tabs[currentIndex];
    let params = currentTab.params;
    params.pageNum = 1;
    params.pageSize = 20;
    currentTab.refreshState = true;
    currentTab.params = params;
    let currentTabSetDataStr = 'tabs[' + currentIndex + ']';
    this.setData({
      [currentTabSetDataStr]: currentTab
    })
    this.loadData(this.loadDataOnStart);
  },
  /**
   * 滑动条触底事件（需要判断对应的key数据是否存在下一页）
   */
  loadNextPageData: function () {
    let currentIndex = this.data.currentIndex;
    let tabs = this.data.tabs;
    let currentTab = tabs[currentIndex];
    let params = currentTab.params;
    if (currentTab.bottomDataLoadState || (Number(params.pageNum) * Number(params.pageSize) >= currentTab.total)) {
      return;
    }
    params.pageNum = params.pageNum + 1;
    currentTab.bottomDataLoadState = true;
    currentTab.params = params;
    let currentTabSetDataStr = 'tabs[' + currentIndex + ']';
    this.setData({
      [currentTabSetDataStr]: currentTab
    })
    this.loadData(null);
  },
  /**
   * 查看佣金总额
   */
  totalDistributionCommission: function () {
    let userInfo = this.data.userInfo;
    let amount = 0.00;
    if (userInfo != null) {
      amount = userInfo.userWalletVo.totalDistributionCommission;
    }
    wx.showModal({
      title: '佣金总额',
      content: '您加入分销计划开始，您总共积累了' + amount + "分销佣金哟，继续努力吧!!!",
      showCancel: false,
      confirmText: "知道啦",
      complete: (res) => {
      }
    })
  },
  /**
   * 提现事件
   */
  withdraw: function () {
    wx.showModal({
      title: '温馨提示',
      content: '目前仅支持线下结算，请联系在线客服->我的->在线客服，线上提现功能即将上线',
      showCancel: false,
      confirmText: "好的",
      complete: (res) => {
      }
    })
  },
  /**
   * 分销协议容器改变事件
   */
  changeDistributionAgreementDrawer: function () {
    let temp = this.data.distributionAgreementDrawer;
    if (temp) {
      this.setData({
        distributionAgreementDrawer: false
      })
    } else {
      // 判断有没有数据，没有就需要去加载
      let distributionAgreement = storage.get(storageConstant.distributionAgreement, null);
      if (distributionAgreement != null) {
        wxParse.wxParse('distributionAgreement', 'html', distributionAgreement, this, 5);
        this.setData({
          distributionAgreementDrawer: true
        })
      } else {
        platformApi.selectTextContentByType({ type: 3 }, this.selectTextContentByTypeOnStart, this.selectTextContentByTypeOnSuccess, this.selectTextContentByTypeOnFailed);
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
    wxParse.wxParse('distributionAgreement', 'html', res.msg, this, 5);
    this.setData({
      distributionAgreementDrawer: true
    })
    storage.set(storageConstant.distributionAgreement, null, res.msg, 0);
  },
  selectTextContentByTypeOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  /**
   * 改变分销佣金记录容器事件
   */
  changeCommissionRecordDrawer: function () {
    let temp = this.data.commissionRecordDrawer;
    this.setData({
      commissionRecordDrawer: !temp
    })
  },
  /**
   * 加载分销佣金记录
   */
  loadCommissionRecord: function (onStart) {
    let params = this.data.commissionRecordParams;
    distributionApi.pageDistributionCommissionRecord(params, onStart, this.loadCommissionRecordOnSuccess, this.loadCommissionRecordOnFailed, this.loadCommissionRecordOnWarn)
  },
  loadCommissionRecordOnStart: function () {
    this.setData({
      loadCommissionRecordState: true
    })
  },
  loadCommissionRecordOnSuccess: function (res) {
    this.closeLoadCommissionRecordOnState();
    let dataArr = res.data.data;
    let commissionRecordData = this.data.commissionRecordData;
    let loadCommissionRecordNextPageState = this.data.loadCommissionRecordNextPageState;
    if(loadCommissionRecordNextPageState){
      for (let i = 0; i < dataArr.length; i++) {
        commissionRecordData.push(dataArr[i]);
      }
    }else{
      commissionRecordData = dataArr;
    }
    this.setData({
      commissionRecordData: commissionRecordData,
      commissionRecordTotal: res.data.total
    })
  },
  loadCommissionRecordOnFailed: function (res) {
    this.closeLoadCommissionRecordOnState();
    wx.showToast({
      title: '请求失败',
      icon: "none"
    })
  },
  loadCommissionRecordOnWarn: function (res) {
    this.closeLoadCommissionRecordOnState();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  closeLoadCommissionRecordOnState: function () {
    let that = this;
    setTimeout(function () {
      that.setData({
        loadCommissionRecordState: false,
        loadCommissionRecordNextPageState: false
      })
    }, 500)
  },
  /**
   * 刷新分销佣金记录数据
   */
  commissionRecordRefresh: function () {
    this.setData({
      ["commissionRecordParams.pageNum"]: 1
    })
    this.loadCommissionRecord(this.loadCommissionRecordOnStart);
  },
  /**
   * 加载下一页分销佣金记录数据
   */
  loadCommissionRecordNextPageData: function () {
    let bottomDataLoadState = this.data.loadCommissionRecordNextPageState;
    let total = this.data.commissionRecordTotal;
    let params = this.data.commissionRecordParams;
    if (bottomDataLoadState || (Number(params.pageNum) * Number(params.pageSize) >= total)) {
      return;
    }
    this.setData({
      ["commissionRecordParams.pageNum"]: params.pageNum + 1,
      loadCommissionRecordNextPageState: true
    })
    this.loadCommissionRecord(null);
  },
  /**
   * 查看佣金记录
   */
  lookCommissionRecord:function(){
    let data = this.data.commissionRecordData;
    if(data == null || data.length <= 0){
      this.loadCommissionRecord(this.loadCommissionRecordOnStart);
    }
    this.setData({
      commissionRecordDrawer: true
    })
  }
})