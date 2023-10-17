const app = getApp();
import storage from "../../utils/storage";
import storageConstant from "../../constans/storageConstant";
import staffApi from "../../apis/staff/staffApi";
import serviceInfoApi from "../../apis/service/serviceInfoApi";
import orderApi from "../../apis/order/orderApi";
import userApi from "../../apis/user/userApi";
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
      chooseStaffSex: 1, // 默认女生
      chooseLabel: [], // 所选员工标签
      staffLevelIndex: 0, // 所选员工等级下标
      serviceInfoIndex: 0, // 所选服务类型下标
      serviceInfoItemIndex: 0, // 所选服务内容下标
      num: 1, // 购买数量
      price: 0, // 订单金额
      filterServedStaff: "N", // 是否排除服务过的员工
      remark: null, // 备注信息
      accountServiceProvider: null, // 社交账号服务商
      customNum: null, // 社交账号
      payWay: 0, // 0微信支付，1树币支付
    },
    // 服务数据
    serviceList: app.globalData.serviceList,
    // 用户信息
    userInfo: app.globalData.userInfo,
    // 热门推荐加载状态
    hotLoadState: true,
    // 热门推荐数据
    hotStaffList: [],
    // 店员数据请求参数
    params: {
      pageNum: 1,
      pageSize: 20,
      showIfTop: 'Y', // 是否展示置顶数据
      sortType: '4', // 等级倒序
      sex: null, // 0男 1女
      staffLevel: null, // 店员等级
      serviceIdArrStr: null, // 服务标识集合字符串
    },
    loadState: true, // 请求数据状态
    staffDataList: [], // 店员数据集合
    staffDataTotal: 0, // 店员数据总条数
    refreshState: false, //下拉刷新状态
    bottomLoadState: false, // 触底加载下一页状态
    sexRange: [
      {
        label: "男",
        value: "0"
      }, {
        label: "女",
        value: "1"
      }
    ],
    preAudioIndex: -1, // 语音条下标
    ifHide: app.globalData.hidePrivacy
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
    // 加载热门推荐数据
    this.loadHotStaff();
    // 加载店员数据
    this.loadStaffData(null);

    // 全局语音监听暂停事件
    app.globalData.audioContext.onEnded(() => {
      let dataList = this.data.staffDataList;
      let index = this.data.preAudioIndex;
      if (index != -1) {
        let key = 'staffDataList[' + index + ']';
        dataList[index].audioState = -1;
        this.setData({
          [key]: dataList[index],
          preAudioIndex: -1
        })
      }
    })
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
  onHide() {
    app.globalData.audioContext.pause();
    let datas = this.data.staffDataList;
    for (let index in datas) {
      datas[index].audioState = -1;
    }
    this.setData({
      staffDataList: datas
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 加载店员数据
   */
  loadStaffData: function (onStart) {
    let params = this.data.params;
    staffApi.page(params, onStart, this.loadStaffDataOnSuccess, this.loadStaffDataOnFailed);
  },
  loadStaffDataOnStart: function () {
    this.setData({
      loadState: true
    })
  },
  loadStaffDataOnSuccess: function (res) {
    let data = res.data.data;
    let staffDataList = this.data.staffDataList;
    let bottomLoadState = this.data.bottomLoadState;
    if (bottomLoadState) {

      for (let i = 0; i < staffDataList.length; i++) {
        let oldObj = staffDataList[i];
        for (let j = 0; j < data.length; j++) {
          let newObj = data[j];
          if(oldObj.userId == newObj.userId){
            data.splice(j, 1);
          }
        }
        
      }
      for (let index in data) {
        staffDataList.push(data[index]);
      }
    } else {
      staffDataList = data;
    }
    this.setData({
      loadState: false,
      bottomLoadState: false,
      refreshState: false,
      staffDataTotal: res.data.total,
      staffDataList: staffDataList
    })
  },
  loadStaffDataOnFailed: function (res) {
    this.setData({
      bottomLoadState: false,
      refreshState: false,
      loadState: false
    })
    wx.showToast({
      title: '加载店员数据失败，请刷新重试',
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 刷新事件
   */
  refresh: function () {
    this.setData({
      ["refreshState"]: true,
      ["params.pageNum"]: 1
    })
    this.loadHotStaff();
    this.loadStaffData(this.loadStaffDataOnStart);
    app.globalData.audioContext.pause();
  },
  /**
   * 滑动条触底事件（需要判断对应的key数据是否存在下一页）
   */
  loadNextPageData: function () {
    let staffDataTotal = this.data.staffDataTotal;
    let params = this.data.params;
    let bottomLoadState = this.data.bottomLoadState;
    if (bottomLoadState || (Number(params.pageNum) * Number(params.pageSize) >= staffDataTotal)) {
      return;
    }
    params.pageNum = params.pageNum + 1;
    this.setData({
      bottomLoadState: true,
      params: params
    })
    this.loadStaffData(null);
  },
  /**
   * 加载热门推荐事件
   */
  loadHotStaff: function () {
    let params = {
      pageNum: 1,
      pageSize: 20,
      sortType: 2,
      showIfTop: 'N'
    }
    // 随机获取sortType的值
    if (Math.round(Math.random()) == 1) {
      params.sortType = 5;
    }
    staffApi.page(params, this.loadNewStaffDataOnStart, this.loadNewStaffDataOnSuccess, this.loadNewStaffDataOnFailed);
  },
  loadNewStaffDataOnStart: function () {
    this.setData({
      hotLoadState: true
    })
  },
  loadNewStaffDataOnSuccess: function (res) {
    this.setData({
      hotLoadState: false,
      hotStaffList: res.data.data
    })
  },
  loadNewStaffDataOnFailed: function () {
    this.setData({
      hotLoadState: false
    })
    wx.showToast({
      title: '加载热门推荐数据失败，请稍后重试',
      icon: "none",
      duration: 2000
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
      ["randomForm.chooseStaffSex"]: e.currentTarget.dataset.sex,
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
      ["randomForm.filterServedStaff"]: e.detail.value ? "Y" : "N"
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
  toShowStaffInfo: function (e) {
    wx.navigateTo({
      url: '../../staffPackages/page/staffInfoShow/index?staffId=' + e.currentTarget.dataset.staffid,
    })
  },
  /**
   * 条件-性别改变事件
   */
  sexFilterChange: function (e) {
    this.setData({
      ["params.sex"]: e.detail.value,
      ["params.pageNum"]: 1
    })
    this.loadStaffData(null);
  },
  /**
   * 条件-店员等级过滤
   */
  staffLevelFilterChange: function (e) {
    this.setData({
      ["params.staffLevel"]: Number(e.detail.value) + 1,
      ["params.pageNum"]: 1
    })
    this.loadStaffData(null);
  },
  /**
   * 条件-服务类型改变事件
   */
  serviceFilterChange: function (e) {
    let index = e.detail.value;
    let obj = this.data.serviceList[index];
    this.setData({
      ["params.serviceIdArrStr"]: obj.id,
      ["params.pageNum"]: 1
    })
    this.loadStaffData(null);
  },
  /**
   * 清除筛选条件
   */
  clearFilterItem: function (e) {
    let index = e.currentTarget.dataset.index;
    if (index == 0) {
      this.setData({
        ["params.serviceIdArrStr"]: null,
        ["params.pageNum"]: 1
      })
    }
    if (index == 1) {
      this.setData({
        ["params.sex"]: null,
        ["params.pageNum"]: 1
      })
    }
    if (index == 2) {
      this.setData({
        ["params.staffLevel"]: null,
        ["params.pageNum"]: 1
      })
    }
    this.loadStaffData(null);
  },
  /**
   * 列表语音点击事件
   */
  listAudioCli: function (e) {
    let that = this;
    let index = e.currentTarget.dataset.index;
    let dataList = this.data.staffDataList;

    // 先判断是否需要暂停上一个语音条
    let preIndex = this.data.preAudioIndex;
    if (preIndex != -1 && preIndex != index) {
      dataList[preIndex].audioState = -1;
      app.globalData.audioContext.pause();
      let preKey = 'staffDataList[' + preIndex + ']';
      that.setData({
        [preKey]: dataList[preIndex]
      })
    }

    // 开始处理当前下标的语音是播放还是暂停
    let currObj = dataList[index];
    if (currObj.audioState == 0) {
      dataList[index].audioState = -1;
      app.globalData.audioContext.pause();
    } else {
      dataList[index].audioState = 0;
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = currObj.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
    }
    let key = 'staffDataList[' + index + ']';
    that.setData({
      [key]: dataList[index],
      preAudioIndex: index
    })
  },
  /**
   * 提交随机订单
   */
  submitRandomOrder: function () {
    let randomForm = this.data.randomForm;
    if (this.data.ifHide=='0'&&(randomForm.accountServiceProvider == null && randomForm.customNum == null)) {
      wx.showToast({
        title: '请选择您的社交账号',
        icon: "none"
      })
      return;
    }
    let remark = randomForm.remark;
    let chooseLabel = randomForm.chooseLabel;
    if (chooseLabel != null && chooseLabel.length > 0) {
      let label = chooseLabel.join(",");
      if (remark != null && remark != '') {
        remark = remark + "-" + label;
      } else {
        remark = label;
      }
    }
    let service = this.data.serviceList[randomForm.serviceInfoIndex];
    let serviceItem = service.serviceItemList[randomForm.serviceInfoItemIndex];
    let form = {
      accountServiceProvider: randomForm.accountServiceProvider,
      customNum: randomForm.customNum,
      chooseStaffSex: randomForm.chooseStaffSex,
      filterServedStaff: randomForm.filterServedStaff,
      num: randomForm.num,
      remark: remark,
      payWay: randomForm.payWay,
      staffLevel: this.data.staffLevelConfig[randomForm.staffLevelIndex].level,
      serviceId: service.id,
      serviceItemId: serviceItem.id
    }
    orderApi.randomOrderSubmit(form, this.randomOrderSubmitOnStart, this.randomOrderSubmitOnSuccess, this.randomOrderSubmitOnFailed, this.randomOrderSubmitOnWarn);
  },
  randomOrderSubmitOnStart: function () {
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  randomOrderSubmitOnSuccess: function (res) {
    wx.hideLoading();
    let form = this.data.randomForm;
    // 微信支付
    if (form.payWay == 0) {
      wx.requestPayment({
        "nonceStr": res.data.nonceStr,
        "package": res.data.packageValue,
        "signType": res.data.signType,
        "paySign": res.data.paySign,
        "timeStamp": res.data.timeStamp,
        success: function (res) {
          wx.showModal({
            title: '提示',
            content: '下单成功，已通知符合条件的店员火速接单啦，感谢您的支持，祝您生活愉快!',
            confirmText: "查看订单",
            cancelText: "知道啦",
            complete: (res) => {
              if (res.cancel) { }
              if (res.confirm) {
                wx.navigateTo({
                  url: '../../orderPackages/page/list/index?currentIndex',
                })
              }
            }
          })
        }, fail: function (res) {
          if (res.errMsg == "requestPayment:fail cancel") {
            wx.navigateTo({
              url: '../../orderPackages/page/list/index?currentIndex',
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
      wx.showModal({
        title: '提示',
        content: '下单成功，已通知符合条件的店员火速接单啦，感谢您的支持，祝您生活愉快!',
        confirmText: "查看订单",
        cancelText: "知道啦",
        complete: (res) => {
          if (res.cancel) {
            userApi.getNewUserInfo(null, this.getNewUserInfoOnSuccess, null);
          }
          if (res.confirm) {
            wx.navigateTo({
              url: '../../orderPackages/page/list/index?currentIndex',
            })
          }
        }
      })
    }
  },
  randomOrderSubmitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  randomOrderSubmitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none",
      duration: 2000
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
})