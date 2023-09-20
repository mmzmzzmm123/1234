Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    currentIndex: 0, // 当前tab数据
    currentKey: "selectStaffOrder",
    tabs:[
      {
        key: "selectStaffOrder",
        title: "选人下单",
      },{
        key: "randomOrder",
        title: "盲盒下单"
      }
    ]
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
   * tab改变事件
   */
  onTabsChange:function(e){
    const { key } = e.detail
    const index = this.data.tabs.map((n) => n.key).indexOf(key)
    this.setData({
      currentKey: key,
      currentIndex: index
    })
  },
  swiperChange:function(e){
    let index = e.detail.current;
    this.setData({
      currentIndex: index,
      currentKey: this.data.tabs[index].key
    })
  },
  /**
   * 热门容器滑动事件
   */
  hotScrollViewMove:function(e){
    console.log(e);
  }
})