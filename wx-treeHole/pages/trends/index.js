Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    currentIndex: 0, // 当前tab数据
    currentKey: "all",
    tabs: [
      {
        key: "all",
        title: "全部",
        ico: "",
        bottomDataLoadState: true, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: null,
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
        bottomDataLoadState: true, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: null,
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
        }
      },
      {
        key: "0",
        title: "男生",
        ico: "../../static/ico/theme_boy.png",
        bottomDataLoadState: true, // 上拉加载数据状态
        refreshState: false, //刷新状态
        data: null,
        total: 0,
        params: {
          pageNum: 1,
          pageSize: 20,
        }
      }
    ],
    videoPlayIndex: 0, // 当前播放的video下标
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
    wx.createSelectorQuery().selectAll('.wuxTab').boundingClientRect(function (rect) {
      that.setData({
        tabHeight: rect[0].height
      })
    }).exec();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 视频播放
   */
  videoPlay: function (e) {
    
  }
})