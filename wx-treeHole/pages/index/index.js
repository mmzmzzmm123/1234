
import storage from "../../utils/storage";
import storageConstant from "../../constans/storageConstant";
import globalConstant from "../../constans/globalConstant";
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
    banners: [{  //banner数据
      themeColor: "#0700B6",
      imgUrl: "http://m.qpic.cn/psc?/V10yc3o10KV3MJ/bqQfVz5yrrGYSXMvKr.cqS4Tj83wR6B6MNByVUVz0mZLGpm2kYlKAFSq2oaxy7DHt6lNnx1Ka*LUq6RTwAMMMxw0CED4SU7By1UGowX*4Yk!/b&bo=7gKGAQAAAAADB0k!&rf=viewer_4"
    }, {
      themeColor: "#014B93",
      imgUrl: "http://m.qpic.cn/psc?/V10yc3o10KV3MJ/bqQfVz5yrrGYSXMvKr.cqVx01KrlpWpGH7ydZfIxtba2IQLc2oT.PQ7ulqLnuQbgPzwTUBLQ7VxL15CWtosShBCADg2A4jPfatKNkERWc70!/b&bo=7gKGAe4ChgEDByI!&rf=viewer_4"
    }],
    bannerCurrent: 0, //当前banner展示位置
    playVoiceIndex: 0,
    playVoiceState: false
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
    this.setData({
      stateBarHeight: storage.get(storageConstant.stateBarHeight, null),
      barHeight: globalConstant.titleBarHeight,
      showStateBarHeight: (Number(storage.get(storageConstant.stateBarHeight, null)) + Number(globalConstant.titleBarHeight)) - 10
    })
    setInterval(() => { 
      let generateRandomNumber = () => Math.floor(Math.random() * 1900 + 1) // 生成1到1999的随机数
      that.update('你获得了' + generateRandomNumber() + '个金币') 
    }, 2000)
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

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
  update(content) {
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
   * 前往选人页面
   */
  goSelectPage:function(){
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
})
