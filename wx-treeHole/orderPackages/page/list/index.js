const app = getApp();
import orderApi from "../../../apis/order/orderApi";
import { $wuxDialog } from '../../../components/wux/dist/index';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
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
          pageSize: 20,
          formUser: '0'
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
          formUser: '0'
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
          formUser: '0'
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
          formUser: '0'
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
          formUser: '0'
        }
      },
      {
        key: "cancel",
        title: "已取消",
        bottomDataLoadState: false,
        refreshState: false,
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          orderState: "-1",
          formUser: '0'
        }
      }
    ],
    dataLoadState: false, // 数据加载状态
    operIndex: -1, // 当前操作的数据下标
    operObj: null, // 当前操作的对象
    orderDetailsDrawer: false, // 订单详情容器
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
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
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
   * 取消订单点击事件
   */
  cancelOrderCli: function (e) {
    let that = this;
    let index = e.currentTarget.dataset.index;
    this.setData({
      operIndex: index
    })
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    let params = {
      orderNo: obj.orderNo
    };
    $wuxDialog().prompt({
      resetOnClose: true,
      title: '取消原因',
      content: '亲爱的，您可说明一下原因吗？让我们更好的改进，给您提供更优质的体验！',
      defaultText: "暂时取消",
      placeholder: '请输入取消原因',
      maxlength: 32,
      onConfirm(e, response) {
        if (response == null || response == "" || response == undefined) {
          wx.showToast({
            title: '请输入原因哟',
            icon: "none"
          })
          return;
        }
        params.cancelRemark = response;
        orderApi.orderCancel(params, that.cancelOrderOnStart, that.cancelOrderOnSuccess, that.cancelOrderOnFailed, that.cancelOrderOnWarn);
      }
    })
  },
  cancelOrderOnStart: function () {
    wx.showLoading({
      title: '正在取消',
      mask: true
    })
  },
  cancelOrderOnSuccess: function (e) {
    wx.hideLoading();
    this.loadData(null);
    let index = this.data.operIndex;
    let currentIndex = this.data.currentIndex;
    let obj = this.data.tabs[currentIndex].data[index];
    // 待支付
    if (obj.orderState == '1') {
      wx.showToast({
        title: '取消成功',
        icon: "none"
      })
      return;
    }
    // 待服务
    wx.showModal({
      title: '温馨提示',
      content: '您的支付款将原路返还，请留意信息，感谢您的支持！',
      showCancel: false,
      confirmText: "知道啦",
      complete: (res) => { }
    })
  },
  cancelOrderOnFailed: function (e) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  cancelOrderOnWarn: function (e) {
    wx.hideLoading();
    wx.showToast({
      title: e.msg,
      icon: "none",
      duration: 2000
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
  lookOrderDetails:function(e){
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
  changeOrderDetailsDrawer:function(){
    let temp = this.data.orderDetailsDrawer;
    this.setData({
      orderDetailsDrawer: !temp
    })
  }
})