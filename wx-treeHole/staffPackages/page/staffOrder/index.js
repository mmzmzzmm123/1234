const app = getApp();
import orderApi from "../../../apis/order/orderApi";
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import { $wuxDialog } from '../../../components/wux/dist/index';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    currentIndex: 1,
    currentKey: "all",
    tabs: [
      {
        key: "taking",
        title: "接单大厅",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "2",
          formUser: '2',
        }
      },
      {
        key: "all",
        title: "全部",
        bottomDataLoadState: false, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          formUser: '1'
        }
      },
      {
        key: "waitReceive",
        title: "待接单",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "2",
          formUser: '1'
        }
      },
      {
        key: "waitService",
        title: "待服务",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "3",
          formUser: '1'
        }
      },
      {
        key: "serviceing",
        title: "服务中",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "4",
          formUser: '1'
        }
      },
      {
        key: "finish",
        title: "已完成",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "0",
          formUser: '1'
        }
      }
    ],
    dataLoadState: false, // 数据加载状态
    operIndex: -1, // 当前操作的数据下标
    operObj: null, // 当前操作的对象
    orderDetailsDrawer: false, // 订单详情容器
    staffInfo: app.globalData.staffInfo, // 店员信息
    servedUserIdList: [], // 服务过的用户id集合
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
    wx.createSelectorQuery().selectAll('.wuxTab').boundingClientRect(function (rect) {
      that.setData({
        tabHeight: rect[0].height
      })
    }).exec();

    let index = options.index;
    if (index != null && index != 0) {
      let key = this.data.tabs[index].key;
      this.setData({
        currentIndex: index,
        currentKey: key
      })
    }
    this.loadData(this.loadDataOnStart);
    this.loadServedUserIdData();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    // 店员信息
    let staffInfo = storage.get(storageConstant.staffInfo, null);
    if (staffInfo != null) {
      this.setData({
        staffInfo: staffInfo
      })
    }
  },
  /**
   * 查询服务过的用户id集合
   */
  loadServedUserIdData: function () {
    orderApi.selectServedUserId(this.loadServedUserIdDataOnSuccess);
  },
  loadServedUserIdDataOnSuccess: function (res) {
    this.setData({
      servedUserIdList: res.data
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
   * 数据加载
   */
  loadData: function (onStart) {
    let params = this.buildParams();
    orderApi.page(params, onStart, this.loadDataOnSuccess, this.loadDataOnFailed, this.loadDataOnWarn);
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
   * 再下一单
   */
  anotherOrder: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let staffInfo = this.data.tabs[currentIndex].data[index].apiStaffInfoVo;
    wx.navigateTo({
      url: '../../../staffPackages/page/staffInfoShow/index?staffId=' + staffInfo.userId
    })
  },
  /**
   * 订单再次支付
   */
  payAgainCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    orderApi.payAgain({ orderNo: obj.orderNo }, this.payAgainOnStart, this.payAgainOnSuccess, this.payAgainOnFailed, this.payAgainOnWarn);
  },
  payAgainOnStart: function () {
    wx.showLoading({
      title: '正在请求',
      mask: true
    })
  },
  payAgainOnSuccess: function (res) {
    wx.hideLoading();
    let that = this;
    wx.requestPayment({
      "nonceStr": res.data.nonceStr,
      "package": res.data.packageValue,
      "signType": res.data.signType,
      "paySign": res.data.paySign,
      "timeStamp": res.data.timeStamp,
      success: function (res) {
        this.loadData(null);
        wx.showModal({
          title: '提示',
          content: '支付成功啦，店员收到消息将火速回应，感谢您的支持！',
          showCancel: false,
          confirmText: "知道啦",
          complete: (res) => { }
        })
      }, fail: function (res) {
        if (res.errMsg == "requestPayment:fail cancel") {
          wx.showToast({
            title: '支付已取消啦',
            icon: "none"
          })
          that.loadData(null);
        } else {
          wx.showToast({
            title: '亲爱的，支付失败了哟',
            icon: "none",
            duration: 2500
          })
        }
      }
    })
  },
  payAgainOnFailed: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  payAgainOnWarn: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showModal({
      title: '温馨提示',
      content: res.msg,
      showCancel: false,
      confirmText: "好的",
      complete: (res) => {
      }
    })
  },
  /**
   * 订单完成点击事件
   */
  orderFinishCli: function (e) {
    let that = this;
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    let params = {
      orderNo: obj.orderNo
    }
    $wuxDialog().prompt({
      resetOnClose: true,
      title: '服务评价',
      content: '亲爱的，可以简短的评价此次服务质量吗？这样可以让我们有更好机会改进，为日后的服务质量更上一层楼！',
      defaultText: "默认好评",
      placeholder: '请输入您的宝贵评论',
      maxlength: 32,
      onConfirm(e, response) {
        if (response == null || response == "" || response == undefined) {
          wx.showToast({
            title: '请输入原因哟',
            icon: "none"
          })
          return;
        }
        params.comment = response;
        orderApi.orderFinish(params, that.orderFinishOnStart, that.orderFinishOnSuccess, that.orderFinishOnFailed, that.orderFinishOnWarn);
      }
    })
  },
  orderFinishOnStart: function () {
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  orderFinishOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '操作成功',
      icon: "none"
    })
  },
  orderFinishOnFailed: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  orderFinishOnWarn: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showModal({
      title: '温馨提示',
      content: res.msg,
      showCancel: false,
      confirmText: "好的",
      complete: (res) => {
      }
    })
  },
  /**
   * 查看当前订单详情
   */
  lookOrderDetails: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    this.setData({
      operObj: obj,
      orderDetailsDrawer: true
    })
  },
  /**
   * 订单详情容器改变事件
   */
  changeOrderDetailsDrawer: function () {
    let temp = this.data.orderDetailsDrawer;
    this.setData({
      orderDetailsDrawer: !temp
    })
  },
  /**
   * 指定单接单
   */
  orderTakingCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    orderApi.orderTaking({ orderNo: obj.orderNo }, this.orderTakingOnStart, this.orderTakingOnSuccess, this.orderTakingOnFailed, this.orderTakingOnWarn);
  },
  orderTakingOnStart: function () {
    wx.showLoading({
      title: '正在接单',
      mask: true
    })
  },
  orderTakingOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '接单成功',
      icon: "success"
    })
  },
  orderTakingOnFailed: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  orderTakingOnWarn: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 订单开始服务
   */
  orderStartServiceCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    orderApi.orderStartService({ orderNo: obj.orderNo }, this.orderStartServiceOnStart, this.orderStartServiceOnSuccess, this.orderStartServiceOnFailed, this.orderStartServiceOnWarn);
  },
  orderStartServiceOnStart: function () {
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  orderStartServiceOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '服务已经开始',
      icon: "success"
    })
  },
  orderStartServiceOnFailed: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  orderStartServiceOnWarn: function (res) {
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 随机单接单点击事件
   */
  randomOrderTakingCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    orderApi.randomOrderTaking({ orderNo: obj.orderNo }, this.randomOrderTakingOnStart, this.randomOrderTakingOnSuccess, this.randomOrderTakingOnFailed, this.randomOrderTakingOnWarn);
  },
  randomOrderTakingOnStart: function () { 
    wx.showLoading({
      title: '正在接单',
      mask: true
    })
  },
  randomOrderTakingOnSuccess: function (res) { 
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '接单成功',
      icon: "success"
    })
  },
  randomOrderTakingOnFailed: function (res) { 
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  randomOrderTakingOnWarn: function (res) { 
    wx.hideLoading();
    this.loadData(null);
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
})