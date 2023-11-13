<script>

export default {
  globalData: {
    logo: 'http://wx.qlogo.cn/mmopen/Q3auHgzwzM7ibjK0zgMSEgozDR40A2CgKLqZxEmcDI9HlQsVD27pM38F5KCDHTEaGeTfh3yWnHVgKPfzf8oKPLLITtib7xWLyMicAd1C3icfE1Q/64',
    spid: 0,
    code: 0,
    isLogin: false,
    userInfo: {},
    MyMenus: [],
    windowHeight: 0,
  },
  onLaunch: function () {
    let that = this;
    // #ifdef H5
    uni.getSystemInfo({
      success: function(res) {
        // 首页没有title获取的整个页面的高度，里面的页面有原生标题要减掉就是视口的高度
        // 状态栏是动态的可以拿到 标题栏是固定写死的是44px
        // #ifdef H5
        that.globalData.windowHeight = res.windowHeight
        // #endif
        console.log(that.globalData.windowHeight)
      }
    });
    // #endif
  },
  mounted() {
	
	console.log("VConsole")
  },
  onShow: function () {
    console.log("App Show");
  },
  onHide: function () {
    console.log("App Hide");
  },
};


</script>

<style>
@import "colorui/icon.css";
@import "colorui/main.css";

page {
  background-color: #F8F8F8;
  font-family: Source Han Sans CN, PingFangSC-Regular, PingFang SC;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 10px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 10px); /* 兼容 iPhone X 及以上设备 */
}
.txt-overflow {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 1;
  text-overflow: ellipsis;
}
.txt-overflow-line2 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 2;
  text-overflow: ellipsis;
}
.psy-box-upload {
  padding-left: 25upx;
  padding-bottom: 20upx;
  box-sizing: border-box;
}
</style>
