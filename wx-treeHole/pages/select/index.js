const app = getApp();
import staffApi from "../../apis/staff/staffApi";
import serviceInfoApi from "../../apis/service/serviceInfoApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    tabHeight: 0, // tab高度
    randomBottomViewHeight: 0, // 底部操作按钮高度
    currentIndex: 0, // 当前tab数据
    currentKey: "selectStaffOrder",
    tabs: [
      {
        key: "selectStaffOrder",
        title: "选人下单",
      }, {
        key: "randomOrder",
        title: "盲盒下单"
      }
    ],
    staffLevelConfig: app.globalData.staffLevelConfig, //员工等级配置
    staffCustomizeLabel: [ // 员工标签
      ["无", "青年", "温柔", "幽默", "高冷", "要猛", "小奶狗", "大男人", "嘴巴真甜"],
      ["无", "可爱", "少女", "温柔", "御姐", "高冷", "变装", "想要爱", "欲求不满"]
    ],
    // 随机下单表单
    randomForm: {
      sex: 1, // 默认女生
      chooseLabel: [], // 所选员工标签
      staffLevelIndex: 0, // 所选员工等级下标
      serviceInfoIndex: 0, // 所选服务类型下标
      serviceInfoItemIndex: 0, // 所选服务内容下标
      num: 1, // 购买数量
      price: 0, // 订单金额
      ifExclude: "N", // 是否排除服务过的员工
      remark: null, // 备注信息
      accountServiceProvider: null, // 社交账号服务商
      customNum: null, // 社交账号
      payWay: 0, // 0微信支付，1树币支付
    },
    // 服务数据
    serviceList: app.globalData.serviceList,
    // 用户信息
    userInfo: app.globalData.userInfo
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
          if(divHeight != sysHeight){
            divHeight = sysHeight;
          }
        }
      });
      that.setData({
        pWidth: rect[0].width,
        pHeight: divHeight
      })
    }).exec();
    wx.createSelectorQuery().selectAll('.randomBottomView').boundingClientRect(function (rect) {
      that.setData({
        randomBottomViewHeight: rect[0].height
      })
    }).exec();
    wx.createSelectorQuery().selectAll('.wuxTab').boundingClientRect(function (rect) {
      that.setData({
        tabHeight: rect[0].height
      })
    }).exec();
  },
  onShow: function () {
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
    // 服务数据
    let serviceList = this.data.serviceList;
    if (serviceList == null || serviceList.length <= 0) {
      serviceInfoApi.select(null, this.loadServiceInfoOnSuccess, null);
    } else {
      this.setData({
        serviceList: app.globalData.serviceList
      })
      this.buildRandomOrderPrice();
    }
    this.setData({
      userInfo: app.globalData.userInfo
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 加载服务数据
   */
  loadServiceInfoOnSuccess: function (res) {
    app.loadServiceInfoDataOnSuccess(res);
    this.setData({
      serviceList: res.data
    })
    this.buildRandomOrderPrice();
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
  swiperChange: function (e) {
    let index = e.detail.current;
    this.setData({
      currentIndex: index,
      currentKey: this.data.tabs[index].key
    })
  },
  /**
   * 随机单 性别选择
   */
  randomChooseSex: function (e) {
    this.setData({
      ["randomForm.sex"]: e.currentTarget.dataset.sex,
      ['randomForm.chooseLabel']: []
    })
  },
  /**
   * 随机单 选择员工标签
   */
  chooseRandomStaffLabel: function (e) {
    let label = e.currentTarget.dataset.label;
    let randomForm = this.data.randomForm;
    let chooseLabel = randomForm.chooseLabel;
    // 先判断是否存在已选，存在就删除
    let hasIndex = chooseLabel.indexOf(label);
    if (hasIndex > -1) {
      chooseLabel.splice(hasIndex, 1);
    } else {
      if (chooseLabel.length > 2) {
        wx.showToast({
          title: '最多选择三个标签哟',
          icon: "none"
        })
        return;
      }
      chooseLabel.push(e.currentTarget.dataset.label);
    }
    randomForm.chooseLabel = chooseLabel;
    this.setData({
      randomForm: randomForm
    })
  },
  /**
   * 随机单 员工等级选择改变事件
   */
  randomStaffLevelChoose: function (e) {
    this.setData({
      ["randomForm.staffLevelIndex"]: e.currentTarget.dataset.index
    })
    this.buildRandomOrderPrice();
  },
  /**
   * 随机单 服务类型选择改变事件
   */
  randomServiceChoose: function (e) {
    this.setData({
      ["randomForm.serviceInfoIndex"]: e.currentTarget.dataset.index,
      ["randomForm.serviceInfoItemIndex"]: 0
    })
    this.buildRandomOrderPrice();
  },
  /**
   * 随机单 服务项选择改变事件
   */
  randomServiceItemChoose: function (e) {
    this.setData({
      ["randomForm.serviceInfoItemIndex"]: e.currentTarget.dataset.index
    })
    this.buildRandomOrderPrice();
  },
  /**
   * 随机单购买数量改变事件
   */
  randomBuyNumChange: function (e) {
    this.setData({
      ["randomForm.num"]: e.detail.value
    })
    this.buildRandomOrderPrice();
  },
  /**
   * 构建随机单金额
   */
  buildRandomOrderPrice: function () {
    let randomForm = this.data.randomForm;
    let staffLevelConfig = this.data.staffLevelConfig;
    let serviceList = this.data.serviceList;
    let serviceItemPriceList = serviceList[randomForm.serviceInfoIndex].serviceItemList[randomForm.serviceInfoItemIndex].serviceItemPriceList;
    let tempPrice = 0;
    let levelConfigId = staffLevelConfig[randomForm.staffLevelIndex].id;
    for (let index in serviceItemPriceList) {
      if (levelConfigId == serviceItemPriceList[index].staffLevelConfigId) {
        tempPrice = serviceItemPriceList[index].price * randomForm.num;
        break;
      }
    }
    this.setData({
      ["randomForm.price"]: tempPrice
    })
  },
  /**
   * 是否排除服务过员工
   */
  changeIfExclude: function (e) {
    this.setData({
      ["randomForm.ifExclude"]: e.detail.value ? "Y" : "N"
    })
  },
  /**
   * 随机单 备注输入事件
   */
  randomRemark: function (e) {
    this.setData({
      ["randomForm.remark"]: e.detail.value
    })
  },
  /**
   * 选择用户账号信息
   */
  selectUserAccount: function () {
    wx.navigateTo({
      url: '../../userPackages/page/accounts/index?type=1',
    })
  },
  /**
   * 选择用户账号回调事件
   */
  chooseUserAccountCallBack: function (data) {
    this.setData({
      ["randomForm.accountServiceProvider"]: data.accountServiceProvider,
      ["randomForm.customNum"]: data.num
    })
  },
  /**
   * 支付方式点击事件
   */
  payWayChange: function (e) {
    this.setData({
      ["randomForm.payWay"]: e.currentTarget.dataset.pay
    })
  },
  /**
   * 前往员工资料展示页
   */
  toStaffInfoShow:function(){
    wx.navigateTo({
      url: '../../staffPackages/page/staffInfoShow/index',
    })
  },
})