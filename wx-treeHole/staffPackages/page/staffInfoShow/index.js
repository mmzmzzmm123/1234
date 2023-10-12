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
import orderApi from "../../../apis/order/orderApi";
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
    giftDrawer: false, // 赠送礼物容器状态
    giftIndex: 0, // 赠送礼物的下标
    giftForm: {
      num: 1, // 赠送礼物数量
      remark: null, // 备注信息
      payWay: 0, // 支付方式
    },
    userInfo: app.globalData.userInfo,// 用户信息
    rewardDrawer: false, // 打赏容器
    rewardForm: { // 打赏表单
      amount: null, // 最少一元
      remark: null, // 备注信息
      payWay: 0, // 支付方式
    },
    appointDrawer: false, // 立即下单容器事件
    appointServiceIndex: -1, // 指定服务下标
    appointServiceItemIndex: -1, // 指定服务子项目下标
    appointForm: {
      num: 1, // 购买数量
      remark: null, // 备注信息
      payWay: 0, // 支付方式
      amount: null, // 订单金额
      accountServiceProvider: null, // 社交账号服务商
      customNum: null, // 社交账号
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
  onUnload() {
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
  loadStaffGiftRecord: function (staffId) {
    staffApi.selectStaffGiftRecordId({ staffId: staffId }, null, this.loadStaffGiftRecordOnSuccess, null);
  },
  loadStaffGiftRecordOnSuccess: function (res) {
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
  /**
   * 礼物赠送事件
   */
  giveGiftCli: function (e) {
    this.setData({
      giftIndex: e.currentTarget.dataset.index,
      ["giftForm.remark"]: null,
      ["giftForm.num"]: 1,
      giftDrawer: true
    })
  },
  /**
   * 改变送礼容器状态
   */
  changeGiftDrawer: function () {
    let temp = this.data.giftDrawer;
    this.setData({
      giftDrawer: !temp
    })
  },
  /**
   * 赠送礼物数量改变事件
   */
  giftNumChange: function (e) {
    this.setData({
      ["giftForm.num"]: e.detail.value
    })
  },
  /**
   * 支付方式点击事件
   */
  giftFormPayWayChange: function (e) {
    this.setData({
      ["giftForm.payWay"]: e.currentTarget.dataset.pay
    })
  },
  /**
   * 选择赠送礼物
   */
  giftChange: function (e) {
    this.setData({
      giftIndex: Number(e.detail.value)
    })
  },
  /**
   * 赠送礼物备注输入事件
   */
  giftRemarkInput: function (e) {
    this.setData({
      ["giftForm.remark"]: e.detail.value
    })
  },
  /**
   * 前往充值页面
   */
  toRechargePage: function () {
    wx.navigateTo({
      url: '../../../platformPackages/page/recharge/index',
    })
  },
  /**
   * 赠送礼物提交
   */
  submitGiceGift: function () {
    let userInfo = this.data.userInfo;
    let staffInfo = this.data.staffInfo;
    let giftList = this.data.giftList;
    let giftIndex = this.data.giftIndex;
    let form = this.data.giftForm;
    let gift = giftList[giftIndex];
    // 判断是否余额支付
    if (form.payWay == 1) {
      let userWalletVo = userInfo.userWalletVo;
      let balance = userWalletVo.balance + userWalletVo.giftBalance;
      let payAmount = gift.price * form.num;
      if (payAmount > balance) {
        wx.showModal({
          title: '温馨提示',
          content: '亲爱的，您的枫叶币不足本次礼物赠送哟，换个支付方式试试吧，或前去充值枫叶币哟，有优惠呢！',
          confirmText: "去充值",
          complete: (res) => {
            if (res.confirm) {
              wx.navigateTo({
                url: '../../../platformPackages/page/recharge/index',
              })
            }
          }
        })
        return;
      }
    }
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
    form.giftId = gift.id;
    form.staffId = staffInfo.userId;
    let that = this;
    // let noticeState = storage.get(storageConstant.giveGiftSuccessNotice, null);
    // if (noticeState != null) {
    //   orderApi.giftOrderSubmit(form, null, that.giftOrderSubmitOnSuccess, that.giftOrderSubmitOnFailed, that.giftOrderSubmitOnWarn);
    // } else {
    //   wx.requestSubscribeMessage({
    //     tmplIds: ['AfCNc1Y43ygsVXiVxgyyd1BsFNg_v6k1zXqrgLChahs'],
    //     success: function (res) {
    //       if (res.AfCNc1Y43ygsVXiVxgyyd1BsFNg_v6k1zXqrgLChahs == 'accept') {
    //         storage.set(storageConstant.giveGiftSuccessNotice, null, "accept", null);
    //       }
    //     }, complete: function (res) {
    //       orderApi.giftOrderSubmit(form, null, that.giftOrderSubmitOnSuccess, that.giftOrderSubmitOnFailed, that.giftOrderSubmitOnWarn);
    //     }
    //   })
    // }
    wx.requestSubscribeMessage({
      tmplIds: ['AfCNc1Y43ygsVXiVxgyyd1BsFNg_v6k1zXqrgLChahs'],
      success: function (res) {
      }, complete: function (res) {
        orderApi.giftOrderSubmit(form, null, that.giftOrderSubmitOnSuccess, that.giftOrderSubmitOnFailed, that.giftOrderSubmitOnWarn);
      }
    })
  },
  giftOrderSubmitOnSuccess: function (res) {
    let that = this;
    wx.hideLoading();
    let form = this.data.giftForm;
    // 微信支付
    if (form.payWay == 0) {
      wx.requestPayment({
        "nonceStr": res.data.nonceStr,
        "package": res.data.packageValue,
        "signType": res.data.signType,
        "paySign": res.data.paySign,
        "timeStamp": res.data.timeStamp,
        success: function (res) {
          that.setData({
            giftDrawer: false
          })
          wx.showModal({
            title: '提示',
            content: '礼物已经发出，店员收到将会第一时间向您道谢，感谢您的支持，祝您生活愉快!',
            confirmText: "查看订单",
            cancelText: "知道啦",
            complete: (res) => {
              if (res.cancel) { }
              if (res.confirm) {
                wx.navigateTo({
                  url: '../../../orderPackages/page/list/index?currentIndex',
                })
              }
            }
          })
        }, fail: function (res) {
          if (res.errMsg == "requestPayment:fail cancel") {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          } else {
            wx.showToast({
              title: '亲爱的，支付失败了哟',
              icon: "none",
              duration: 2500
            })
          }
        }
      })
    } else {
      userApi.getNewUserInfo(null, this.getNewUserInfoOnSuccess, null);
      this.setData({
        giftDrawer: false
      })
      wx.showModal({
        title: '提示',
        content: '礼物已经发出，店员收到将会第一时间向您道谢，感谢您的支持，祝您生活愉快!',
        confirmText: "查看订单",
        cancelText: "知道啦",
        complete: (res) => {
          if (res.cancel) { }
          if (res.confirm) {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          }
        }
      })
    }
  },
  giftOrderSubmitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "none"
    })
  },
  giftOrderSubmitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none",
      direction: 2000
    })
  },
  getNewUserInfoOnSuccess: function (res) {
    let data = res.data;
    app.globalData.userInfo = data;
    storage.set(storageConstant.userInfo, null, data, 0);
    this.setData({
      userInfo: data
    })
  },
  /**
   * 打赏容器改变事件
   */
  changeRewardDrawer: function () {
    let temp = this.data.rewardDrawer;
    this.setData({
      rewardDrawer: !temp
    })
  },
  /**
   * 打赏金额输入事件
   */
  rewardAmountChange: function (e) {
    this.setData({
      ["rewardForm.amount"]: parseInt(e.detail.value)
    })
  },
  /**
   * 打赏备注输入事件
   */
  rewardRemarkInput: function (e) {
    this.setData({
      ["rewardForm.remark"]: e.detail.value
    })
  },
  /**
   * 支付方式点击事件
   */
  rewardFormPayWayChange: function (e) {
    this.setData({
      ["rewardForm.payWay"]: e.currentTarget.dataset.pay
    })
  },
  /**
   * 打赏事件
   */
  rewardCli: function () {
    this.setData({
      rewardForm: {
        amount: null,
        payWay: 0,
        remark: null
      },
      rewardDrawer: true
    })
  },
  /**
   * 打赏表单提交
   */
  rewardSubmit: function () {
    let rewardForm = this.data.rewardForm;
    if (rewardForm.amount == null && rewardForm.amount == '' && rewardForm.amount <= 0) {
      wx.showToast({
        title: '请输入正确的打赏金额哟',
        icon: "none",
        direction: 2000
      })
      return;
    }
    let staffInfo = this.data.staffInfo;
    rewardForm.staffId = staffInfo.userId;
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
    orderApi.rewardOrderSubmit(rewardForm, null, this.rewardOrderSubmitOnSuccess, this.rewardOrderSubmitOnFailed, this.rewardOrderSubmitOnWarn);
  },
  rewardOrderSubmitOnSuccess: function (res) {
    let that = this;
    wx.hideLoading();
    let form = this.data.rewardForm;
    // 微信支付
    if (form.payWay == 0) {
      wx.requestPayment({
        "nonceStr": res.data.nonceStr,
        "package": res.data.packageValue,
        "signType": res.data.signType,
        "paySign": res.data.paySign,
        "timeStamp": res.data.timeStamp,
        success: function (res) {
          that.setData({
            rewardDrawer: false
          })
          wx.showModal({
            title: '提示',
            content: '打赏成功啦，店员收到将会第一时间向您道谢，感谢您的支持，祝您生活愉快!',
            confirmText: "查看订单",
            cancelText: "知道啦",
            complete: (res) => {
              if (res.cancel) { }
              if (res.confirm) {
                wx.navigateTo({
                  url: '../../../orderPackages/page/list/index?currentIndex',
                })
              }
            }
          })
        }, fail: function (res) {
          if (res.errMsg == "requestPayment:fail cancel") {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          } else {
            wx.showToast({
              title: '亲爱的，支付失败了哟',
              icon: "none",
              duration: 2500
            })
          }
        }
      })
    } else {
      userApi.getNewUserInfo(null, this.getNewUserInfoOnSuccess, null);
      this.setData({
        rewardDrawer: false
      })
      wx.showModal({
        title: '提示',
        content: '打赏成功啦，店员收到将会第一时间向您道谢，感谢您的支持，祝您生活愉快!',
        confirmText: "查看订单",
        cancelText: "知道啦",
        complete: (res) => {
          if (res.cancel) { }
          if (res.confirm) {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          }
        }
      })
    }
  },
  rewardOrderSubmitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "none"
    })
  },
  rewardOrderSubmitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none",
      direction: 2000
    })
  },
  /**
   * 指定下单容器改变事件
   */
  changeAppointDrawer: function () {
    let temp = this.data.appointDrawer;
    this.setData({
      appointDrawer: !temp
    })
  },
  /**
   * 服务改变
   */
  appointServiceChoose: function (e) {
    this.setData({
      appointServiceIndex: e.currentTarget.dataset.index,
      appointServiceItemIndex: 0
    })
    this.buildAppointOrderAmount();
  },
  /**
   * 服务子项改变
   */
  appointServiceItemChoose: function (e) {
    this.setData({
      appointServiceItemIndex: e.currentTarget.dataset.index
    })
    this.buildAppointOrderAmount();
  },
  /**
   * 指定单数量修改
   */
  appointNumChange: function (e) {
    this.setData({
      ["appointForm.num"]: e.detail.value
    })
    this.buildAppointOrderAmount();
  },
  /**
   * 指定单备注
   */
  appointRemarkInput: function (e) {
    this.setData({
      ["appointForm.remark"]: e.detail.value
    })
  },
  /**
   * 指定单支付方式点击事件
   */
  appointFormPayWayChange: function (e) {
    this.setData({
      ["appointForm.payWay"]: e.currentTarget.dataset.pay
    })
  },
  /**
   * 构建指定订单金额
   */
  buildAppointOrderAmount: function () {
    let appointServiceIndex = this.data.appointServiceIndex;
    let appointServiceItemIndex = this.data.appointServiceItemIndex;
    // 其中一个选项为-1，直接退出
    if (appointServiceIndex == -1 || appointServiceItemIndex == -1) {
      return;
    }
    let appointForm = this.data.appointForm;
    let serviceList = this.data.serviceList;
    let staffInfo = this.data.staffInfo;
    let staffLevelConfig = this.data.staffLevelConfig;
    let staffLevelId = -1;
    for (let index in staffLevelConfig) {
      if (staffInfo.staffLevel == staffLevelConfig[index].level) {
        staffLevelId = staffLevelConfig[index].id;
      }
    }
    let levelAmount = 0; // 当前等级的服务金额
    // 所选服务item
    let serviceItemObj = serviceList[appointServiceIndex].serviceItemList[appointServiceItemIndex];
    for (let index in serviceItemObj.serviceItemPriceList) {
      if (staffLevelId == serviceItemObj.serviceItemPriceList[index].staffLevelConfigId) {
        levelAmount = serviceItemObj.serviceItemPriceList[index].price;
      }
    }
    this.setData({
      ["appointForm.amount"]: levelAmount * appointForm.num
    })
  },
  /**
   * 指定单提交
   */
  appointSubmit: function () {
    // 提交之前构建一次，防止有误
    this.buildAppointOrderAmount();
    let appointServiceIndex = this.data.appointServiceIndex;
    let appointServiceItemIndex = this.data.appointServiceItemIndex;
    // 其中一个选项为-1，直接退出
    if (appointServiceIndex == -1 || appointServiceItemIndex == -1) {
      wx.showToast({
        title: '请选择服务类型与服务内容项进行下单',
        icon: "none",
        duration: 2000
      })
      return;
    }
    let appointForm = this.data.appointForm;
    if (appointForm.amount == null || appointForm.amount == "" || appointForm.amount <= 0) {
      wx.showToast({
        title: '订单金额有误，请刷新重试',
        icon: "none",
        duration: 2000
      })
      return;
    }
    // 判断社交账号是否已选择
    if (appointForm.accountServiceProvider == null || appointForm.customNum == null) {
      wx.showToast({
        title: '亲爱的，请选择社交账号哟',
        icon: "none",
        duration: 2000
      })
      return;
    }
    let staffInfo = this.data.staffInfo;
    let serviceList = this.data.serviceList;
    let service = serviceList[appointServiceIndex];
    let serviceItem = service.serviceItemList[appointServiceItemIndex];
    appointForm.staffId = staffInfo.userId;
    appointForm.serviceId = service.id;
    appointForm.serviceItemId = serviceItem.id;
    let that = this;
    wx.requestSubscribeMessage({
      tmplIds: ['XQkRpWhVAxE99X6XkE7EajVVIleRvnNA_7wEYOMRx7Q', 'f--gE3LkP87xwyzA9py0SaWf3UcNTGd7m0htGbbBQfE'],
      success: function (res) {

      }, complete: function (res) {
        orderApi.appointOrderSubmit(appointForm, that.appointOrderSubmitOnStart, that.appointOrderSubmitOnSuccess, that.appointOrderSubmitOnFailed, that.appointOrderSubmitOnWarn);
      }
    })
  },
  appointOrderSubmitOnStart: function () {
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  appointOrderSubmitOnSuccess: function (res) {
    wx.hideLoading();
    let that = this;
    let form = this.data.appointForm;
    // 微信支付
    if (form.payWay == 0) {
      wx.requestPayment({
        "nonceStr": res.data.nonceStr,
        "package": res.data.packageValue,
        "signType": res.data.signType,
        "paySign": res.data.paySign,
        "timeStamp": res.data.timeStamp,
        success: function (res) {
          that.setData({
            appointDrawer: false
          })
          wx.showModal({
            title: '提示',
            content: '下单成功，已通知店员火速接单啦，感谢您的支持，祝您生活愉快!',
            confirmText: "查看订单",
            cancelText: "知道啦",
            complete: (res) => {
              if (res.cancel) { }
              if (res.confirm) {
                wx.navigateTo({
                  url: '../../../orderPackages/page/list/index?currentIndex',
                })
              }
            }
          })
        }, fail: function (res) {
          if (res.errMsg == "requestPayment:fail cancel") {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          } else {
            wx.showToast({
              title: '亲爱的，支付失败了哟',
              icon: "none",
              duration: 2500
            })
          }
        }
      })
    } else {
      userApi.getNewUserInfo(null, this.getNewUserInfoOnSuccess, null);
      this.setData({
        appointDrawer: false
      })
      wx.showModal({
        title: '提示',
        content: '下单成功，已通知店员火速接单啦，感谢您的支持，祝您生活愉快!',
        confirmText: "查看订单",
        cancelText: "知道啦",
        complete: (res) => {
          if (res.cancel) {
            userApi.getNewUserInfo(null, this.getNewUserInfoOnSuccess, null);
          }
          if (res.confirm) {
            wx.navigateTo({
              url: '../../../orderPackages/page/list/index?currentIndex',
            })
          }
        }
      })
    }
  },
  appointOrderSubmitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: "请求失败",
      icon: "none",
      direction: 2000
    })
  },
  appointOrderSubmitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none",
      direction: 2000
    })
  },
  /**
   * 指定单下单点击事件
   */
  appointCli: function () {
    this.setData({
      appointServiceIndex: -1, // 指定服务下标
      appointServiceItemIndex: -1, // 指定服务子项目下标
      appointForm: {
        num: 1, // 购买数量
        remark: null, // 备注信息
        payWay: 0, // 支付方式
        amount: null, // 订单金额
        accountServiceProvider: null, // 社交账号服务商
        customNum: null, // 社交账号
      },
      appointDrawer: true
    })
  },
  /**
   * 选择用户账号信息
   */
  selectUserAccount: function () {
    wx.navigateTo({
      url: '../../../userPackages/page/accounts/index?type=1',
    })
  },
  /**
   * 选择用户账号回调事件
   */
  chooseUserAccountCallBack: function (data) {
    this.setData({
      ["appointForm.accountServiceProvider"]: data.accountServiceProvider,
      ["appointForm.customNum"]: data.num
    })
  },
})