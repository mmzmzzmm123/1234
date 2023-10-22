import timeUtil from "../../../utils/time";
import staffWalletApi from "../../../apis/staff/staffWalletApi";
import orderApi from "../../../apis/order/orderApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    currentIndex: 0,
    currentKey: "schedule",
    tabs: [
      {
        key: "schedule",
        title: "一览表"
      },
      {
        key: "commission_records",
        title: "佣金记录"
      },
      {
        key: "settlement_records",
        title: "往期薪资"
      }
    ],
    orderTabCurrentIndex: 0,
    orderTabCurrentKey: "all",
    orderTabs: [
      {
        key: "all",
        title: "全部",
        data: []
      },
      {
        key: "0",
        title: "已完成",
        data: []
      },
      {
        key: "-1",
        title: "已取消",
        data: []
      }
    ],
    refreshStaffWalletDataState: false, // 刷新店员钱包数据状态
    refreshSettlementRecordDataState: false, // 刷新店员钱包数据状态
    weekIndex: 1, // 统计周期下标
    weekList: [], // 统计周期
    weekListOption: [], // 统计周期选择项
    loadStaffWalletState: false, // 加载店员钱包数据
    staffWallet: null, // 店员钱包数据
    waitOrderFinishCommission: 0.00, // 待订单完结佣金
    loadStaffWalletRecordState: false, // 加载店员钱包记录状态
    loadStaffWalletRecordNextPageState: false, // 加载下一页店员钱包记录状态
    staffWalletRecordParams: { // 店员钱包记录请求参数
      pageNum: 1,
      pageSize: 40
    },
    staffWalletRecordData: [], // 店员钱包记录
    staffWalletDataTotal: 0, // 店员钱包记录总数
    loadThisWeekOrderInfoState: false, // 加载店员订单概况状态
    thisWeekOrderInfo: null, // 店员订单概况
    loadSettlementRecordState: false, // 加载店员往期结算信息数据
    loadNextPageOfSettlementRecordState: false, // 加载下一页店员往期结算记录
    settlementRecordData: [], // 往期薪资记录
    settlementRecordTotal: 0, // 往期薪资记录总数
    settlementRecordParams: { // 往期薪资记录请求参数
      pageNum: 1,
      pageSize: 60
    },
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

    let weekList = [];
    let weekListOption = [];
    let prevWeekDataArr = timeUtil.getPrevWeekDataList();
    let thisWeekDataArr = timeUtil.getWeekDataList();
    weekList.push(prevWeekDataArr.join("至"));
    weekListOption.push({
      label: prevWeekDataArr.join("至")
    })
    weekList.push(thisWeekDataArr.join("至"));
    weekListOption.push({
      label: thisWeekDataArr.join("至")
    })
    this.setData({
      weekList: weekList,
      weekListOption: weekListOption
    })
    // 加载店员钱包数据
    this.loadStaffWallet();
    // 加载店员钱包记录数据
    this.loadStaffWalletRecord(this.loadStaffWalletRecordOnStart);
    // 加载店员订单概况
    this.loadThisWeekOrderInfo();
    // 加载店员结算记录
    this.loadSettlementRecordData(this.loadSettlementRecordDataOnStart);
  },
  /**
   * 结算周期时间改变事件
   */
  weekChange: function (e) {
    this.setData({
      weekIndex: e.detail.value
    })
    // 加载店员订单概况
    this.loadThisWeekOrderInfo();
  },
  /**
   * 加载店员结算记录
   */
  loadSettlementRecordData: function (onStart) {
    staffWalletApi.pageSettlementRecord(onStart, this.loadSettlementRecordDataOnSuccess, this.loadSettlementRecordDataOnFailed, this.loadSettlementRecordDataOnWarn);
  },
  loadSettlementRecordDataOnStart: function () {
    this.setData({
      loadSettlementRecordState: true
    })
  },
  loadSettlementRecordDataOnSuccess: function (res) {
    let dataArr = res.data.data;
    let settlementRecordData = this.data.settlementRecordData;
    let loadNextPageState = this.data.loadNextPageOfSettlementRecordState;
    if (loadNextPageState) {
      for (let i = 0; i < dataArr.length; i++) {
        settlementRecordData.push(dataArr[i]);
      }
    } else {
      settlementRecordData = dataArr;
    }
    this.setData({
      loadSettlementRecordState: false,
      loadNextPageOfSettlementRecordState: false,
      refreshSettlementRecordDataState: false,
      settlementRecordTotal: res.data.total,
      settlementRecordData: settlementRecordData
    })
  },
  loadSettlementRecordDataOnFailed: function (res) {
    this.setData({
      loadSettlementRecordState: false,
      refreshSettlementRecordDataState: false,
      loadNextPageOfSettlementRecordState: false
    })
    wx.showToast({
      title: '加载往期薪资数据失败',
      icon: "none"
    })
  },
  loadSettlementRecordDataOnWarn: function (res) {
    this.setData({
      loadSettlementRecordState: false,
      refreshSettlementRecordDataState: false,
      loadNextPageOfSettlementRecordState: false
    })
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 加载店员订单概况
   */
  loadThisWeekOrderInfo: function () {
    let weekList = this.data.weekList;
    let weekIndex = this.data.weekIndex;
    let week = weekList[weekIndex];
    let weekArr = week.split("至");
    let params = {
      beginCreateTime: weekArr[0],
      endCreateTime: weekArr[1]
    }
    orderApi.selectStaffOrderInfoByDate(params, this.loadThisWeekOrderInfoOnStart, this.loadThisWeekOrderInfoOnSuccess, this.loadThisWeekOrderInfoOnFailed, this.loadThisWeekOrderInfoOnWarn);
  },
  loadThisWeekOrderInfoOnStart: function () {
    this.setData({
      loadThisWeekOrderInfoState: true
    })
  },
  loadThisWeekOrderInfoOnSuccess: function (res) {
    let orderTabs = this.data.orderTabs;
    let dataArr = res.data.orderInfoVoList;
    orderTabs[0].data = dataArr;
    for (let i = 0; i < dataArr.length; i++) {
      let tempObj = dataArr[i];
      if (tempObj.orderState == '0') {
        orderTabs[1].data.push(tempObj);
      }
      if (tempObj.orderState == '-1') {
        orderTabs[2].data.push(tempObj);
      }
    }
    this.setData({
      loadThisWeekOrderInfoState: false,
      orderTabs: orderTabs,
      thisWeekOrderInfo: res.data,
      waitOrderFinishCommission: res.data.waitOrderFinishCommission
    })
  },
  loadThisWeekOrderInfoOnFailed: function (res) {
    this.setData({
      loadThisWeekOrderInfoState: false
    })
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  loadThisWeekOrderInfoOnWarn: function (res) {
    this.setData({
      loadThisWeekOrderInfoState: false
    })
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 加载下一页店员薪资结算记录
   */
  loadNextPageSettlementRecordData: function () {
    let settlementRecordTotal = this.data.settlementRecordTotal;
    let loadNextPageOfSettlementRecordState = this.data.loadNextPageOfSettlementRecordState;
    let params = this.data.settlementRecordParams;
    if (loadNextPageOfSettlementRecordState || (Number(params.pageNum) * Number(params.pageSize) >= settlementRecordTotal)) {
      return;
    }
    params.pageNum = params.pageNum + 1;
    this.setData({
      loadNextPageOfSettlementRecordState: true,
      ["settlementRecordParams.pageNum"]: params.pageNum
    })
    this.loadSettlementRecordData(null);
  },
  /**
   * 加载下一页店员钱包数据事件
   */
  loadNextPageStaffWalletRecordData: function () {
    let staffWalletDataTotal = this.data.staffWalletDataTotal;
    let loadStaffWalletRecordNextPageState = this.data.loadStaffWalletRecordNextPageState;
    let params = this.data.staffWalletRecordParams;
    if (loadStaffWalletRecordNextPageState || (Number(params.pageNum) * Number(params.pageSize) >= staffWalletDataTotal)) {
      return;
    }
    params.pageNum = params.pageNum + 1;
    this.setData({
      loadStaffWalletRecordNextPageState: true,
      ["staffWalletRecordParams.pageNum"]: params.pageNum
    })
    this.loadStaffWalletRecord(null);
  },
  /**
   * 刷新店员薪资结算记录事件
   */
  refreshSettlementRecordData: function () {
    this.setData({
      ["loadSettlementRecordState"]: true,
      ["settlementRecordParams.pageNum"]: 1
    })
    // 加载店员结算记录
    this.loadSettlementRecordData(this.loadSettlementRecordDataOnStart);
  },
  /**
   * 刷新店员钱包数据事件
   */
  refreshStaffWalletData: function () {
    this.setData({
      ["refreshStaffWalletDataState"]: true,
      ["staffWalletRecordParams.pageNum"]: 1
    })
    this.loadStaffWallet();
    this.loadStaffWalletRecord(this.loadStaffWalletRecordOnStart);
    this.loadThisWeekOrderInfo();
  },
  /**
   * 加载店员钱包记录数据
   */
  loadStaffWalletRecord: function (onStart) {
    let params = this.data.staffWalletRecordParams;
    staffWalletApi.pageWalletRecord(params, onStart, this.loadStaffWalletRecordOnSuccess, this.loadStaffWalletRecordOnFailed);
  },
  loadStaffWalletRecordOnStart: function () {
    this.setData({
      loadStaffWalletRecordState: true
    })
  },
  loadStaffWalletRecordOnSuccess: function (res) {
    let dataArr = res.data.data;
    let staffWalletRecordData = this.data.staffWalletRecordData;
    let loadNextPageState = this.data.loadStaffWalletRecordNextPageState;
    if (loadNextPageState) {
      for (let i = 0; i < dataArr.length; i++) {
        staffWalletRecordData.push(dataArr[i]);
      }
    } else {
      staffWalletRecordData = dataArr;
    }

    this.setData({
      loadStaffWalletRecordState: false,
      loadStaffWalletRecordNextPageState: false,
      staffWalletRecordData: staffWalletRecordData,
      staffWalletDataTotal: res.data.total
    })

  },
  loadStaffWalletRecordOnFailed: function (res) {
    this.setData({
      loadStaffWalletRecordState: false
    })
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  /**
   * 加载店员钱包数据
   */
  loadStaffWallet: function () {
    staffWalletApi.selectStaffWallet(this.loadStaffWalletOnStart, this.loadStaffWalletOnSuccess, this.loadStaffWalletOnFailed, this.loadStaffWalletOnWarn);
  },
  loadStaffWalletOnStart: function () {
    this.setData({
      loadStaffWalletState: true
    })
  },
  loadStaffWalletOnSuccess: function (res) {
    this.setData({
      loadStaffWalletState: false,
      refreshStaffWalletDataState: false,
      staffWallet: res.data
    })
  },
  loadStaffWalletOnFailed: function () {
    wx.hideLoading();
    this.setData({
      loadStaffWalletState: false,
      refreshStaffWalletDataState: false,
    })
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  loadStaffWalletOnWarn: function (res) {
    wx.hideLoading();
    this.setData({
      loadStaffWalletState: false,
      refreshStaffWalletDataState: false,
    })
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 订单状态tab改变事件
   */
  orderOnTabsChange:function(e){
    const {
      key
    } = e.detail
    const index = this.data.orderTabs.map((n) => n.key).indexOf(key);
    this.setData({
      orderTabCurrentIndex: index,
      orderTabCurrentKey: key
    })
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
  },
})