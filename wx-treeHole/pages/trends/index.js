const app = getApp();
import staffTrendsApi from "../../apis/staff/staffTrendsApi";
import userApi from "../../apis/user/userApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    firstJoin: true, // 第一次进入页面
    dataLoadState: false, // 数据加载状态
    tabHeight: 0, // tab高度
    currentIndex: 0, // 当前tab数据
    currentKey: "all",
    tabs: [
      {
        key: "all",
        title: "全部",
        ico: "",
        bottomDataLoadState: false, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
        }
      },
      {
        key: "1",
        title: "女生",
        ico: "../../static/ico/theme_girl.png",
        bottomDataLoadState: false, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          sex: 1,
        }
      },
      {
        key: "0",
        title: "男生",
        ico: "../../static/ico/theme_boy.png",
        bottomDataLoadState: false, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: [],
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
          sex: 0
        }
      }
    ],
    videoPlayIndex: 0, // 当前播放的video下标
    preTabIndex: -1, // 上一个播放语音的tab下标
    listAudioIndex: -1, // 上一个列表播放语音下标
    userLikeStaffUserIdList: app.globalData.userLikeStaffUserIdList, // 用户收藏店员记录
    userLikeStaffTrendsIdList: app.globalData.userLikeStaffTrendsIdList, // 用户点赞店员动态记录
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
    // 全局语音监听暂停事件
    app.globalData.audioContext.onEnded(() => {
      let preTabIndex = this.data.preTabIndex;
      let listAudioIndex = this.data.listAudioIndex;
      if (listAudioIndex != -1) {
        let tabs = this.data.tabs;
        tabs[preTabIndex].data[listAudioIndex].audioState = -1;
        this.setData({
          tabs: tabs,
          preTabIndex: -1,
          listAudioIndex: -1
        })
      }
    })
  },
  onShow() {
    let firstJoin = this.data.firstJoin;
    let loadDataOnStart = null;
    if (firstJoin) {
      loadDataOnStart = this.loadDataOnStart;
      this.setData({
        firstJoin: false
      })
    }
    // 加载最新的数据
    this.loadData(loadDataOnStart);
    // 加载用户点击关注数据
    this.loadUserLikeData();
  },
  onHide() {
    app.globalData.audioContext.pause();
    let tabs = this.data.tabs;
    let currentIndex = this.data.currentIndex;
    let tab = tabs[currentIndex];
    if(tab.data != null && tab.data.length > 0){
      for(let index in tab.data){
        tab.data[index].audioState = -1;
      }
      let key = 'tabs['+currentIndex+']';
      this.setData({
        [key]: tab
      })
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 加载用户点击关注数据
   */
  loadUserLikeData: function () {
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
  },
  loadUserLikeDataOnSuccess: function (res) {
    app.selectUserLikeDataOnSuccess(res);
    this.setData({
      userLikeStaffUserIdList: res.data.likeStaffUserIdList,
      userLikeStaffTrendsIdList: res.data.likeStaffTrendsIdList
    })
  },
  /**
   * 加载动态数据
   */
  loadData: function (onStart) {
    let params = this.buildParams();
    staffTrendsApi.select(params, onStart, this.loadDataOnSuccess, this.loadDataOnFailed);
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
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
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
    // 先暂停上一个语音条
    this.stopPreAudioState();
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
    // 先暂停上一个语音条
    this.stopPreAudioState();
  },
  /**
   * 列表视频播放按钮
   */
  videoPlay: function (e) {
    // 先暂停上一个语音条
    this.stopPreAudioState();
    let videoContext = wx.createVideoContext(e.currentTarget.id);
    videoContext.requestFullScreen({ direction: 0 });
  },
  /**
   * 视频进入/退出全屏
   */
  videoScreenChange: function (e) {
    if (!e.detail.fullScreen) {
      let videoContext = wx.createVideoContext(e.currentTarget.id);
      videoContext.pause();
    }
  },
  /**
   * 列表相片点击事件
   */
  listImgCli: function (e) {
    let dataset = e.currentTarget.dataset;
    let tabs = this.data.tabs;
    let currentIndex = this.data.currentIndex;
    let tab = tabs[currentIndex];
    let obj = tab.data[dataset.index];
    let imgListStr = obj.imgList;
    let imgArr = imgListStr.split(",");
    wx.previewImage({
      urls: imgArr,
      current: imgArr[dataset.imgindex]
    })
  },
  /**
   * 暂停上一个语音条
   */
  stopPreAudioState: function (index) {
    // 先判断是否需要暂停上一个语音条
    let preTabIndex = this.data.preTabIndex;
    let listAudioIndex = this.data.listAudioIndex;
    if (listAudioIndex != -1) {
      if (index == null || (index != null && listAudioIndex != index)) {
        let tabs = this.data.tabs;
        let preTab = tabs[preTabIndex];
        let obj = preTab.data[listAudioIndex];
        obj.audioState = -1;
        let tempTabStr = 'tabs[' + preTabIndex + '].data[' + listAudioIndex + ']';
        this.setData({
          [tempTabStr]: obj
        })
        app.globalData.audioContext.pause();
      }
    }

  },
  /**
   * 列表语音点击事件
   */
  listAudioCli: function (e) {
    // 开始处理当前下标的语音是播放还是暂停
    let index = e.currentTarget.dataset.index;
    // 先暂停上一个语音条
    this.stopPreAudioState(index);
    let tabs = this.data.tabs;
    let currentIndex = this.data.currentIndex;
    let tab = tabs[currentIndex];
    let obj = { ...tab.data[index] };

    if (obj.audioState == 0) {
      obj.audioState = -1;
      app.globalData.audioContext.pause();
    } else {
      obj.audioState = 0;
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = obj.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
    }
    let tempTabStr = 'tabs[' + currentIndex + '].data[' + index + ']';
    this.setData({
      [tempTabStr]: obj,
      preTabIndex: currentIndex,
      listAudioIndex: index
    })
  },
  /**
   * 点赞动态
   */
  likeStaffTrends: function (e) {
    let id = e.currentTarget.dataset.id;
    staffTrendsApi.likeStaffTrends({ staffTrendsId: id }, this.likeStaffTrendsOnStart, this.likeStaffTrendsOnSuccess, this.likeStaffTrendsOnFailed, this.likeStaffTrendsOnWaen)
  },
  likeStaffTrendsOnStart: function () {
    wx.showLoading({
      title: '点赞中',
      mask: true
    })
  },
  likeStaffTrendsOnSuccess: function (res) {
    wx.hideLoading();
    this.loadData(null);
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
  },
  likeStaffTrendsOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '点赞失败，稍后重试',
      icon: "none"
    })
  },
  likeStaffTrendsOnWaen: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 取消点赞动态
   */
  cancelLikeStaffTrends: function (e) {
    let id = e.currentTarget.dataset.id;
    staffTrendsApi.cancelLikeStaffTrends({ staffTrendsId: id }, this.cancelLikeStaffTrendsOnStart, this.cancelLikeStaffTrendsOnSuccess, this.cancelLikeStaffTrendsOnFailed, this.cancelLikeStaffTrendsOnWarn)
  },
  cancelLikeStaffTrendsOnStart: function () {
    wx.showLoading({
      title: '正在取消',
      mask: true
    })
  },
  cancelLikeStaffTrendsOnSuccess: function () {
    wx.hideLoading();
    this.loadData(null);
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
  },
  cancelLikeStaffTrendsOnFailed: function () {
    wx.hideLoading();
    wx.showToast({
      title: '取消点赞失败，稍后重试',
      icon: "none"
    })
  },
  cancelLikeStaffTrendsOnWarn: function () {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 关注店员
   */
  likeStaff: function (e) {
    let id = e.currentTarget.dataset.id;
    userApi.likeStaff({ staffId: id }, this.likeStaffOnStart, this.likeStaffOnSuccess, this.likeStaffOnFailed, this.likeStaffOnWarn)
  },
  likeStaffOnStart: function () {
    wx.showLoading({
      title: '正在关注',
      mask: true
    })
  },
  likeStaffOnSuccess: function (res) {
    wx.hideLoading();
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
  },
  likeStaffOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '关注失败，稍后重试',
      icon: "none"
    })
  },
  likeStaffOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /** 
   * 用户取消关注店员
   */
  cancelLikeStaff: function (e) {
    let id = e.currentTarget.dataset.id;
    userApi.cancelLikeStaff({ staffId: id }, this.cancelLikeStaffOnStart, this.cancelLikeStaffOnSuccess, this.cancelLikeStaffOnFailed, this.cancelLikeStaffOnWarn);
  },
  cancelLikeStaffOnStart: function () {
    wx.showLoading({
      title: '正在取消',
      mask: true
    })
  },
  cancelLikeStaffOnSuccess: function (res) {
    wx.hideLoading();
    userApi.selectUserLikeData(null, this.loadUserLikeDataOnSuccess, null);
  },
  cancelLikeStaffOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '关注失败，稍后重试',
      icon: "none"
    })
  },
  cancelLikeStaffOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 前往店员资料页
   */
  toShowStaffInfo:function(e){
    wx.navigateTo({
      url: '../../staffPackages/page/staffInfoShow/index?staffId='+e.currentTarget.dataset.staffid,
    })
  }
})