const app = getApp();
const { Player } = require("../../../libs/svgaplayer.weapp")
const player = new Player;
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import globalConstant from "../../../constans/globalConstant";
import platformGiftApi from "../../../apis/platform/platformApi";
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
    ]
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
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

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
})