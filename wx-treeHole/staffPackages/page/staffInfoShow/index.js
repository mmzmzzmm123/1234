const app = getApp();
const { Player } = require("../../../libs/svgaplayer.weapp")
const player = new Player;
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import globalConstant from "../../../constans/globalConstant";
import platformGiftApi from "../../../apis/platform/platformApi";
import staffApi from "../../../apis/staff/staffApi";
import serviceInfoApi from "../../../apis/service/serviceInfoApi";
import staffTrendsApi from "../../../apis/staff/staffTrendsApi";
import userApi from "../../../apis/user/userApi";
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
    giftList: app.globalData.giftList, // 礼物列表
    showGift: false, // 展示礼物状态
    currentIndex: 0, // 当前tab数据
    currentKey: "trends",
    tabs: [
      {
        key: "trends",
        title: "动态"
      }, {
        key: "comment",
        title: "评论"
      }
    ],
    serviceList: app.globalData.serviceList, // 服务数据
    staffLevelConfig: app.globalData.staffLevelConfig, //员工等级配置
    loadStaffInfoState: false, // 加载店员数据状态
    staffInfo: null, // 店员数据
    loadStaffTrendsState: false, // 店员动态数据加载状态
    loadStaffTrendsStateOfBottom: false, // 店员动态数据下一页加载状态
    trendsParams: { // 动态请求参数
      pageNum: 1,
      pageSize: 20,
      userId: null,
    },
    staffTrendsData: [], // 店员动态数据
    staffTrendsDataTotal: 0, // 店员动态数据总数
    userLikeStaffUserIdList: app.globalData.userLikeStaffUserIdList, // 用户收藏店员记录
    userLikeStaffTrendsIdList: app.globalData.userLikeStaffTrendsIdList, // 用户点赞店员动态记录
    preListAudioPlayIndex: -1, // 上一条列表播放语音下标
    staffGiftArr: [], // 店员已点亮礼物id集合
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
    this.setData({
      stateBarHeight: storage.get(storageConstant.stateBarHeight, null),
      barHeight: globalConstant.titleBarHeight,
      showStateBarHeight: (Number(storage.get(storageConstant.stateBarHeight, null)) + Number(globalConstant.titleBarHeight)) - 10
    })
    let staffId = options.staffId;
    this.setData({
      ["trendsParams.userId"]: staffId
    })
    // 加载店员数据
    this.loadStaffInfo(staffId);
    // 加载店员已点亮礼物数据
    this.loadStaffGiftRecord(staffId);
    // 加载店员动态数据
    this.loadStaffTrendsData(this.loadStaffTrendsDataOnStart);
    
    // 全局语音监听暂停事件
    app.globalData.audioContext.onEnded(() => {
      this.stopPreAudioState(null);
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    let giftList = storage.get(storageConstant.giftList, null);
    if (giftList != null) {
      this.setData({
        giftList: giftList
      })
    } else {
      platformGiftApi.selectGiftList(null, this.selectGiftListOnSuccess, null);
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
    // 加载用户点击关注数据
    this.loadUserLikeData();
  },
  onUnload(){
    this.stopPreAudioState(null);
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 查询店员已点亮礼物数据
   */
  loadStaffGiftRecord:function(staffId){
    staffApi.selectStaffGiftRecordId({staffId: staffId}, null, this.loadStaffGiftRecordOnSuccess, null);
  },
  loadStaffGiftRecordOnSuccess:function(res){
    let dataArr = res.data;
    let giftIdArr = dataArr.map(item => item.giftId);
    this.setData({
      staffGiftArr: giftIdArr
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
      userLikeStaffUserIdList: res.data.likeStaffUserIdList,
      userLikeStaffTrendsIdList: res.data.likeStaffTrendsIdList
    })
  },
  /**
   * 加载店员的动态数据
   */
  loadStaffTrendsData: function (onStart) {
    let params = this.data.trendsParams;
    staffTrendsApi.select(params, onStart, this.loadStaffTrendsDataOnSuccess, this.loadStaffTrendsDataOnFailed);
  },
  loadStaffTrendsDataOnStart: function () {
    this.setData({
      loadStaffTrendsState: true
    })
  },
  loadStaffTrendsDataOnSuccess: function (res) {
    let dataArr = res.data.data;
    let staffTrendsData = this.data.staffTrendsData;
    let loadStaffTrendsStateOfBottom = this.data.loadStaffTrendsStateOfBottom;
    if (loadStaffTrendsStateOfBottom) {
      for (let index in dataArr) {
        staffTrendsData.push(dataArr[index]);
      }
    } else {
      staffTrendsData = dataArr;
    }
    this.setData({
      loadStaffTrendsState: false,
      loadStaffTrendsStateOfBottom: false,
      staffTrendsDataTotal: res.data.data.total,
      staffTrendsData: staffTrendsData
    })
  },
  loadStaffTrendsDataOnFailed: function (res) {
    this.setData({
      loadStaffTrendsState: false,
      loadStaffTrendsStateOfBottom: false
    })
    wx.showToast({
      title: '加载店员动态失败，请稍后重试',
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 店员语音条点击事件
   */
  staffAudioCli: function () {
    let staffInfo = this.data.staffInfo;
    if (staffInfo.audioState == 0) {
      // 暂停语音
      this.stopPreAudioState(null);
    } else {
      // 开始构建数据播放语音
      staffInfo.audioState = 0;
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = staffInfo.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
      this.setData({
        staffInfo: staffInfo
      })
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
    // 当前语音条业务处理
    let staffTrendsData = this.data.staffTrendsData;
    let obj = staffTrendsData[index];
    if(obj.audioState == 0){
      obj.audioState = -1;
      app.globalData.audioContext.pause();
    }else{
      obj.audioState = 0;
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = obj.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
    }
    let tempTabStr = 'staffTrendsData[' + index + ']';
    this.setData({
      [tempTabStr]: obj,
      preListAudioPlayIndex: index
    })
  },
  /**
   * 暂停上一条语音
   */
  stopPreAudioState: function (index) {
    // 店员信息语音
    let staffInfo = this.data.staffInfo;
    if (staffInfo.audioState == 0) {
      this.setData({
        ["staffInfo.audioState"]: -1
      })
      app.globalData.audioContext.pause();
    }
    // 上一条语音下标
    let preIndex = this.data.preListAudioPlayIndex;
    if (preIndex != -1 && (index == null || preIndex != index)) {
      let dataArr = this.data.staffTrendsData;
      let key = 'staffTrendsData[' + preIndex + ']';
      let obj = dataArr[preIndex];
      obj.audioState = -1;
      this.setData({
        [key]: obj
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
   * 加载服务数据
   */
  loadServiceInfoOnSuccess: function (res) {
    app.loadServiceInfoDataOnSuccess(res);
    this.setData({
      serviceList: res.data
    })
  },
  /**
   * 请求加载店员数据
   */
  loadStaffInfo: function (id) {
    staffApi.selectByStaffId({ staffId: id }, this.loadStaffInfoOnStart, this.loadStaffInfoOnSuccess, this.loadStaffInfoOnFailed, this.loadStaffInfoOnWarn);
  },
  loadStaffInfoOnStart: function () {
    this.setData({
      loadStaffInfoState: true
    })
  },
  loadStaffInfoOnSuccess: function (res) {
    this.setData({
      loadStaffInfoState: false
    })
    this.setData({
      staffInfo: res.data
    })
  },
  loadStaffInfoOnFailed: function (res) {
    this.setData({
      loadStaffInfoState: false
    })
    wx.showToast({
      title: '加载店员数据失败，请稍后重试',
      icon: "none",
      duration: 2000
    })
  },
  loadStaffInfoOnWarn: function (res) {
    this.setData({
      loadStaffInfoState: false
    })
    wx.showToast({
      title: res.msg,
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 礼物请求成功
   */
  selectGiftListOnSuccess: function (res) {
    app.loadPlatformGiftOnSuccess(res);
    this.setData({
      giftList: res.data
    })
  },
  /**
   * 展示数据
   */
  async showSvga(e) {
    let that = this;
    let index = e.currentTarget.dataset.index;
    try {
      wx.showLoading({
        title: '加载中',
      })
      let parser = app.globalData.parserArr[index];
      await player.setCanvas('#canvas');
      await player.setVideoItem(parser);
      player.startAnimation();
      player.onFrame(function (number) {
        if (number == 1) {
          wx.hideLoading();
        }
        if (number == 0) {
          player.stopAnimation(true);
          that.setData({
            showGift: false
          })
        }
      });
      that.setData({
        showGift: true
      })
    } catch (error) {
      wx.showToast({
        title: '加载失败',
        icon: "none"
      })
    }
  },
  stopCanvas() {
    player.stopAnimation(true);
    this.setData({
      showGift: false
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
   * 返回上一页
   */
  toBack: function () {
    let pages = getCurrentPages();
    if (pages.length > 1) {
      wx.navigateBack();
    } else {
      wx.switchTab({
        url: '../../../pages/index/index',
      })
    }
  },
  /**
   * tab改变事件
   */
  onTabsChange: function (e) {
    const { key } = e.detail
    const index = this.data.tabs.map((n) => n.key).indexOf(key)
    this.setData({
      currentKey: key,
      currentIndex: index
    })
  },
  /**
   * 查看店员相册
   */
  showStaffImg: function (e) {
    let index = e.currentTarget.dataset.index;
    let staffInfo = this.data.staffInfo;
    let photoObjList = staffInfo.photoVoList;
    let imgArr = photoObjList.map(item => item.imgUrl);
    wx.previewImage({
      urls: imgArr,
      current: imgArr[index]
    })
  },
  /**
   * 查看店员全部相册
   */
  showStaffImgAll: function (e) {
    let staffInfo = this.data.staffInfo;
    let photoObjList = staffInfo.photoVoList;
    let imgArr = photoObjList.map(item => item.imgUrl);
    wx.previewImage({
      urls: imgArr
    })
  },
  /**
   * 列表视频播放按钮
   */
  videoPlay: function (e) {
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
    let staffTrendsData = this.data.staffTrendsData;
    let obj = staffTrendsData[dataset.index];
    let imgListStr = obj.imgList;
    let imgArr = imgListStr.split(",");
    wx.previewImage({
      urls: imgArr,
      current: imgArr[dataset.imgindex]
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
    // 加载店员动态数据
    this.loadStaffTrendsData(null);
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
    // 加载店员动态数据
    this.loadStaffTrendsData(null);
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
    let id = this.data.staffInfo.userId;
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
  cancelLikeStaff: function () {
    let id = this.data.staffInfo.userId;
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
})