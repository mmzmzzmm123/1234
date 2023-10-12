
const app = getApp();
import storage from "../../utils/storage";
import storageConstant from "../../constans/storageConstant";
import globalConstant from "../../constans/globalConstant";
import staffInfoApi from "../../apis/staff/staffApi";
import platformApi from "../../apis/platform/platformApi";
import userApi from "../../apis/user/userApi";
import staffApi from "../../apis/staff/staffApi";
import paymentApi from "../../apis/payment/paymentApi";
Page({

  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    stateBarHeight: "20px", //状态栏高度
    barHeight: "44.8px", //状态栏标题高度
    showStateBarHeight: 0, //展示状态栏高度
    barThemeColor: null, // 状态栏主题颜色
    themecolor: "#555d8c", //主题颜色
    refreshState: false, //下拉刷新状态
    banners: null,
    bannersLoadState: false, // banner加载状态
    bannerCurrent: 0, //当前banner展示位置
    newStaffLoadState: true, // 新人推荐加载状态
    newStaffList: [], // 新人推荐数据
    staffDataLoadState: true, // 店员数据加载状态
    bottomLoadState: false, // 触底加载下一页状态
    staffDataParams: { // 店员数据请求参数
      pageNum: 1,
      pageSize: 20,
      sortType: "2",
      showIfTop: "Y"
    },
    staffDataList: [], // 店员数据集合
    staffDataTotal: 0, // 店员数据总条数
    userLikeStaffUserIdList: app.globalData.userLikeStaffUserIdList, // 用户收藏店员记录
    preAudioSource: -1, // 上一个播放的数据源类型 -1无 0新人推荐 1店员
    preAudioIndex: -1, // 数据中的下标
    rankTabIndex: 0, // 周榜tab下标
    boyWeeklyRankingTopThree: [], // 男生周榜前三
    girlWeeklyRankingTopThree: [], // 女生周榜前三
    weeklyRankingTopThreeLoadState: false, // 周榜前三加载状态
    defaultRewardPublicityData: [ // 默认公示数据
      "张伟 收到打赏枫叶币 17.00 个",
      "Olivia 收到打赏枫叶币 12.00 个",
      "John 收到打赏枫叶币 15.25 个",
      "🍦Hannah🍦 收到了打赏礼物[幸运雨] x1",
      "💖David💖 收到了打赏礼物[星梦庄园] x1",
      "Emma 收到打赏枫叶币 8.75 个",
      "陈静 收到打赏枫叶币 11.50 个",
      "🌟Alice🌟 收到了打赏礼物[棒棒糖] x1",
      "🎉Charlie🎉 收到了打赏礼物[月夜船头] x1",
      "Lucy 收到打赏枫叶币 8.25 个",
      "Michael 收到打赏枫叶币 6.25 个",
      "Frank 收到了打赏礼物[梦幻海岛] x1",
      "Emily 收到打赏枫叶币 9.75 个",
      "😊Bob😊 收到了打赏礼物[比心心] x1",
      "💫Grace💫 收到了打赏礼物[冰激凌] x1",
      "林涛 收到打赏枫叶币 10.50 个",
      "Tom 收到打赏枫叶币 20.25 个",
      "王芳 收到打赏枫叶币 14.75 个",
      "李明 收到打赏枫叶币 5.00 个",
      "🌙Eva🌙 收到了打赏礼物[一起流星雨] x1"
    ],
    rewardPublicityData: [], // 公示数据
    rewardPublicityIndex: 0, // 打赏公示下标
  },

  onLoad() {
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

    let defaultRewardPublicityData = this.data.defaultRewardPublicityData;
    this.setData({
      stateBarHeight: storage.get(storageConstant.stateBarHeight, null),
      barHeight: globalConstant.titleBarHeight,
      showStateBarHeight: (Number(storage.get(storageConstant.stateBarHeight, null)) + Number(globalConstant.titleBarHeight)) - 10,
      rewardPublicityData: defaultRewardPublicityData
    })

    // 加载banner
    this.loadBanner();
    // 加载新人推荐数据
    this.loadNewStaffData();
    // 加载店员数据
    this.loadStaffData(null);
    // 加载周榜前三
    this.getWeeklyRankingTopThree();
    // 加载公示数据
    this.selectPaymentOrderBody();

    // 全局语音监听暂停事件
    app.globalData.audioContext.onEnded(() => {
      let preAudioSource = this.data.preAudioSource;
      let preAudioIndex = this.data.preAudioIndex;
      if (preAudioIndex != -1) {
        let objArr = null;
        let keyStr = null;
        if (preAudioSource == 0) {
          objArr = this.data.newStaffList;
          keyStr = 'newStaffList[' + preAudioIndex + ']';
        } else {
          objArr = this.data.staffDataList;
          keyStr = 'staffDataList[' + preAudioIndex + ']';
        }
        objArr[preAudioIndex].audioState = -1;

        this.setData({
          [keyStr]: objArr[preAudioIndex],
          preAudioSource: -1,
          preAudioIndex: -1
        })
      }
    })
    // 开始公示
    this.updateRewardPublicity(defaultRewardPublicityData[Math.floor(Math.random() * 19)]);
    this.startRewardPublicity();
  },
  onShow() {
    // 加载用户点击关注数据
    this.loadUserLikeData();
    // 加载公示数据
    this.selectPaymentOrderBody();
  },
  onHide() {
    app.globalData.audioContext.pause();
    let preAudioSource = this.data.preAudioSource;
    let preAudioIndex = this.data.preAudioIndex;
    if (preAudioIndex != -1) {
      let objArr = null;
      let keyStr = null;
      if (preAudioSource == 0) {
        objArr = this.data.newStaffList;
        keyStr = 'newStaffList[' + preAudioIndex + ']';
      } else {
        objArr = this.data.staffDataList;
        keyStr = 'staffDataList[' + preAudioIndex + ']';
      }
      objArr[preAudioIndex].audioState = -1;

      this.setData({
        [keyStr]: objArr[preAudioIndex],
        preAudioSource: -1,
        preAudioIndex: -1
      })
    }
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 开始打赏公示
   */
  startRewardPublicity: function () {
    let that = this;
    setInterval(() => {
      let rewardPublicityData = this.data.rewardPublicityData;
      let index = this.data.rewardPublicityIndex;
      if(index >= (rewardPublicityData.length-1)){
        index = 0;
      }else{
        index += 1;
      }
      that.updateRewardPublicity(rewardPublicityData[index]);
      that.setData({
        rewardPublicityIndex: index
      })
    }, 2000)
  },
  /**
   * 更新公示数据
   */
  updateRewardPublicity(content) {
    let that = this;
    let animation = wx.createAnimation();
    // 旧消息向上平移，以低速开始，动画时间300ms
    animation.translateY(-30).step({ duration: 300, timingFunction: 'ease-in' })
    // 为了实现下一条新内容向上平移的效果，必须把内容很快平移到下方，并且不能被用户看见
    // 实现方法：动画时间设置为1ms，过渡效果设置为’动画第一帧就跳至结束状态直到结束‘
    animation.opacity(0).translateY(30).step({ duration: 1, timingFunction: 'step-start' })
    // 新消息向上平移的同时恢复透明度，以低速结束，动画时间300ms
    animation.opacity(1).translateY(0).step({ duration: 300, timingFunction: 'ease-out' })
    that.setData({
      animationData: animation.export()
    })
    // 更新内容的延时必须大于第一步动画时间
    setTimeout(that.setData.bind(that, { content: content }), 300)
  },
  /**
   * 加载公示数据
   */
  selectPaymentOrderBody: function () {
    paymentApi.selectPaymentOrderBody({ pageNum: 1, pageSize: 20 }, this.selectPaymentOrderBodyOnSuccess);
  },
  selectPaymentOrderBodyOnSuccess: function (res) {
    let defaultRewardPublicityData = this.data.defaultRewardPublicityData;
    for (let index = 0; index < res.data.length; index++) {
      defaultRewardPublicityData.push(res.data[index]);
    }
    this.setData({
      rewardPublicityData: defaultRewardPublicityData,
      rewardPublicityIndex: 19
    })
  },
  /**
   * 加载周榜前三数据
   */
  getWeeklyRankingTopThree: function () {
    staffApi.weeklyRankingTopThree(this.getWeeklyRankingTopThreeOnStart, this.getWeeklyRankingTopThreeOnSuccess, this.getWeeklyRankingTopThreeOnFailed);
  },
  getWeeklyRankingTopThreeOnStart: function () {
    this.setData({
      weeklyRankingTopThreeLoadState: true
    })
  },
  getWeeklyRankingTopThreeOnSuccess: function (res) {
    let dataArr = res.data;
    let boyArr = [];
    let girlArr = [];
    for (let index = 0; index < dataArr.length; index++) {
      let obj = dataArr[index];
      if (obj.sex == '0') {
        boyArr.push(obj);
      } else {
        girlArr.push(obj);
      }
    }
    this.setData({
      weeklyRankingTopThreeLoadState: false,
      boyWeeklyRankingTopThree: boyArr,
      girlWeeklyRankingTopThree: girlArr
    })
  },
  getWeeklyRankingTopThreeOnFailed: function (res) {
    this.setData({
      weeklyRankingTopThreeLoadState: false
    })
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
      userLikeStaffUserIdList: res.data.likeStaffUserIdList
    })
  },
  /**
   * 加载店员数据
   */
  loadStaffData: function (onStart) {
    let params = this.data.staffDataParams;
    staffInfoApi.page(params, onStart, this.loadStaffDataOnSuccess, this.loadStaffDataOnFailed);
  },
  loadStaffDataOnStart: function () {
    this.setData({
      staffDataLoadState: true
    })
  },
  loadStaffDataOnSuccess: function (res) {
    let data = res.data.data;
    let staffDataList = this.data.staffDataList;
    let bottomLoadState = this.data.bottomLoadState;
    if (bottomLoadState) {
      for (let index in data) {
        staffDataList.push(data[index]);
      }
    } else {
      staffDataList = data;
    }
    this.setData({
      staffDataLoadState: false,
      bottomLoadState: false,
      staffDataTotal: res.data.total,
      staffDataList: staffDataList
    })
  },
  loadStaffDataOnFailed: function (res) {
    this.setData({
      bottomLoadState: false,
      staffDataLoadState: false
    })
    wx.showToast({
      title: '加载店员数据失败，请刷新重试',
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 滑动条触底事件（需要判断对应的key数据是否存在下一页）
   */
  loadNextPageData: function () {
    let staffDataTotal = this.data.staffDataTotal;
    let params = this.data.staffDataParams;
    let bottomLoadState = this.data.bottomLoadState;
    if (bottomLoadState || (Number(params.pageNum) * Number(params.pageSize) >= staffDataTotal)) {
      return;
    }
    params.pageNum = params.pageNum + 1;
    this.setData({
      bottomLoadState: true,
      staffDataParams: params
    })
    this.loadStaffData(null);
  },
  /**
   * 加载新人推荐数据
   */
  loadNewStaffData: function () {
    let params = {
      pageNum: 1,
      pageSize: 6,
      sortType: 0,
      showIfTop: 'N'
    }
    staffInfoApi.page(params, this.loadNewStaffDataOnStart, this.loadNewStaffDataOnSuccess, this.loadNewStaffDataOnFailed);
  },
  loadNewStaffDataOnStart: function () {
    this.setData({
      newStaffLoadState: true
    })
  },
  loadNewStaffDataOnSuccess: function (res) {
    this.setData({
      newStaffList: res.data.data,
      newStaffLoadState: false
    })
  },
  loadNewStaffDataOnFailed: function (res) {
    this.setData({
      newStaffLoadState: false
    })
    wx.showToast({
      title: '加载新人推荐数据失败，请刷新重试',
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 加载banner
   */
  loadBanner: function () {
    // 判断是否存在缓存，如果存在侧不需要请求接口获取
    let bannerData = storage.get(storageConstant.bannerByPosition + globalConstant.banner_position.index, null);
    let startObj = this.loadBannerOnStart;
    if (bannerData != null) {
      this.setData({
        banners: bannerData
      })
      startObj = null;
    }
    platformApi.getBannerByPosition({ position: globalConstant.banner_position.index }, startObj, this.loadBannerOnSuccess, this.loadBannerOnFailed);
  },
  loadBannerOnStart: function () {
    this.setData({
      bannersLoadState: true
    })
  },
  loadBannerOnSuccess: function (res) {
    this.setData({
      banners: res.data
    })
    storage.set(storageConstant.bannerByPosition + globalConstant.banner_position.index, null, res.data, null);
    this.closeBannerLoadState();
  },
  loadBannerOnFailed: function (res) {
    this.closeBannerLoadState();
  },
  closeBannerLoadState: function () {
    let that = this;
    setTimeout(function () {
      that.setData({
        bannersLoadState: false
      })
    }, 1000)
  },
  /**
   * 轮播图改变事件
   */
  swiperChange: function (e) {
    this.setData({
      bannerCurrent: e.detail.current
    })
  },
  /**
   * 滚动事件
   */
  scrollView: function (e) {
    if (e.detail.scrollTop > this.data.showStateBarHeight && this.data.barThemeColor == null) {
      this.setData({
        barThemeColor: "linear-gradient(to right,#a79de0,#b7b0dd)"
      })
    }
    if (this.data.showStateBarHeight > e.detail.scrollTop && this.data.barThemeColor != null) {
      this.setData({
        barThemeColor: null
      })
    }
  },
  /**
   * 前往选人页面
   */
  goSelectPage: function () {
    wx.switchTab({
      url: '../select/index',
    })
  },
  /**
   * 热门容器滑动事件
   */
  hotScrollViewMove: function (e) {
    console.log(e);
  },
  /**
   * 关注用户
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
   * 列表语音点击事件
   */
  listAudioCli: function (e) {
    let source = e.currentTarget.dataset.source;
    let index = e.currentTarget.dataset.index;
    // 先暂停上一条播放中的语音
    let preAudioSource = this.data.preAudioSource;
    let preAudioIndex = this.data.preAudioIndex;
    if (preAudioIndex != -1 && ((preAudioSource == source && preAudioIndex != index) || (preAudioIndex == index && preAudioSource != source) || (preAudioIndex != index && preAudioSource != source))) {
      let preObj = null;
      let preKey = null;
      if (preAudioSource == 0) {
        preObj = this.data.newStaffList[preAudioIndex];
        preKey = 'newStaffList[' + preAudioIndex + ']';
      } else {
        preObj = this.data.staffDataList[preAudioIndex];
        preKey = 'staffDataList[' + preAudioIndex + ']';
      }
      preObj.audioState = -1;
      this.setData({
        [preKey]: preObj
      })
      app.globalData.audioContext.pause();
    }
    // 开始处理当前的语音条
    let obj = null;
    let key = null;
    if (source == 0) {
      obj = this.data.newStaffList[index];
      key = 'newStaffList[' + index + ']';
    } else {
      obj = this.data.staffDataList[index];
      key = 'staffDataList[' + index + ']';
    }
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
    this.setData({
      [key]: obj,
      preAudioSource: source,
      preAudioIndex: index
    })
  },
  /**
   * 前往店员资料页
   */
  toShowStaffInfo: function (e) {
    wx.navigateTo({
      url: '../../staffPackages/page/staffInfoShow/index?staffId=' + e.currentTarget.dataset.staffid,
    })
  },
  /**
   * 周榜tab改变事件
   */
  changeRankTab: function (e) {
    this.setData({
      rankTabIndex: e.currentTarget.dataset.index
    })
  },
  /**
   * 下拉刷新数据
   */
  refresh: function () {
    let that = this;
    setTimeout(function () {
      that.setData({
        refreshState: false
      }, 2000)
    })
    // 加载banner
    this.loadBanner();
    // 加载新人推荐数据
    this.loadNewStaffData();
    // 加载店员数据
    this.loadStaffData(null);
    // 加载周榜前三
    this.getWeeklyRankingTopThree();
    // 加载用户点击关注数据
    this.loadUserLikeData();
    // 加载打赏公示数据
    this.selectPaymentOrderBody();
  },
})
