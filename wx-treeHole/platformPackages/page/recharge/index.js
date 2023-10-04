import storageConstant from "../../../constans/storageConstant";
import storageUtil from "../../../utils/storage";
import platformApi from "../../../apis/platform/platformApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    rechargeData: [], // 充值配置数据
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
    // 获取充值配置数据
    let storageData = storageUtil.get(storageConstant.rechargeConfig, null);
    let onStart = null;
    if(storageData == null){
      onStart = this.loadRechargeConfigDataOnStart;
    }else{
      this.setData({
        rechargeData: storageData
      })
    }
    this.loadRechargeConfigData(onStart);
  },
  /**
   * 加载充值配置数据
   */
  loadRechargeConfigData:function(onStart){
    platformApi.selectRechargeConfig(onStart,this.loadRechargeConfigDataOnSuccess,this.loadRechargeConfigDataOnFailed);
  },
  loadRechargeConfigDataOnStart:function(){
    wx.showLoading({
      title: '加载中',
      mask: true
    })
  },
  loadRechargeConfigDataOnSuccess:function(res){
    wx.hideLoading();
    this.setData({
      rechargeData: res.data
    })
    storageUtil.set(storageConstant.rechargeConfig, null, res.data, null);
  },
  loadRechargeConfigDataOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '加载失败',
      icon: "error"
    })
  },
})