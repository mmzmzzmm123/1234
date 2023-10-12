const app = getApp();
import globalCanstanats from "../../../constans/globalConstant";
import utils from "../../../utils/util";
import tokenUtil from "../../../utils/token";
import requestUtil from "../../../apis/request";
import soundRecordUtil from "../../../utils/soundRecord";
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import platformApi from "../../../apis/platform/platformApi";
import staffApi from "../../../apis/staff/staffApi";
var wxParse = require('../../../wxParse/wxParse');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    sexRange: [
      {
        label: "男",
        value: "0"
      }, {
        label: "女",
        value: "1"
      }
    ],
    soundRecordState: -1, // -1 停止录音 0 录音中
    audioState: -1, // -1 停止 0 播放中
    uploadSoundRecordType: 0, // 0直接录音，1上传文件
    form: { // 表单
      nickName: null, // 昵称
      sex: null, // 性别
      weChatNum: null, // 微信号
      phone: null, // 手机号码
      province: null, // 省
      city: null, // 市
      region: null, // 地区信息
      selfIntroduction: null, //个人简介
      birthDate: null, // 出生日期
      remark: null, // 备注
      referralCode: null, // 推荐码
      voiceUrl: null, // 录音地址
      voiceTime: null, // 录音时长
    },
    fileUploadUrl: requestUtil.uploadSingleFileUrl,
    fileUploadForm: { // 文件上传oss类型
      ossKey: "staff_photo"
    },
    photoArr: [], // 图片集合
    ifAgree: false, // 是否同意协议
    staffAgreementDrawer: false, // 协议容器
    userInfo: app.globalData.userInfo,
    staffInfo: null, // 店员信息
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
    // 设置监听audio组件
    app.globalData.audioContext.onTimeUpdate(() => {
      if (this.form.voiceTime == null) {
        this.setData({
          ["form.voiceTime"]: Math.round(app.globalData.audioContext.duration)
        })
      }
    })
    app.globalData.audioContext.onEnded(() => {
      this.setData({
        audioState: -1
      })
    })
    this.loadStaffTextContent();
    // 加载员工信息
    this.loadStaffInfo();
  },
  onUnload(){
    app.globalData.audioContext.pause();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  /**
   * 加载员工信息
   */
  loadStaffInfo: function () {
    let userInfo = this.data.userInfo;
    staffApi.selectByUserId({ userId: userInfo.id }, this.loadStaffInfoOnStart, this.loadStaffInfoOnSuccess, this.loadStaffInfoOnFailed);
  },
  loadStaffInfoOnStart: function () {
    wx.showLoading({
      title: '加载中',
      mask: true
    })
  },
  loadStaffInfoOnSuccess: function (res) {
    wx.hideLoading();
    if (res.data != null) {
      app.updateStaffInfo(res);
      let staffInfo = res.data;
      this.setData({
        staffInfo: staffInfo,
        ifAgree: true
      })
      // 构建店员相册数据
      this.buildStaffPhotoArr();
      // 还原数据
      this.buildForm();
      // 看看是否审核不通过
      if (staffInfo.state == 2) {
        wx.showModal({
          title: '审核不通过',
          content: staffInfo.notPassReason,
          showCancel: false,
          complete: (res) => {
          }
        })
      }
    }
  },
  loadStaffInfoOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '数据加载失败',
      icon: "none"
    })
  },
  /**
   * 构建员工相册数据
   */
  buildStaffPhotoArr: function () {
    let staffInfo = this.data.staffInfo;
    let arr = staffInfo.photoVoList;
    let imgArr = [];
    for (let index in arr) {
      imgArr.push({
        requestId: arr[index].id,
        uid: arr[index].id,
        url: arr[index].imgUrl,
        imgUrl: arr[index].imgUrl,
        status: 'done',
        res: arr[index],
      });
    }
    this.setData({
      photoArr: imgArr
    })
  },
  /**
   * 根据员工信息还原form表单
   */
  buildForm: function () {
    let staffInfo = this.data.staffInfo;
    let form = {...staffInfo};
    form.avatarUrl = null;
    form.selfTags = null;
    // 地区信息
    this.setData({
      form: form
    })

  },
  /**
   * 加载平台店员协议
   */
  loadStaffTextContent: function () {
    let params = { type: "1" }
    let staffAgreement = storage.get(storageConstant.staffAgreement, null);
    if (staffAgreement != null) {
      wxParse.wxParse('staffAgreement', 'html', staffAgreement, this, 5);
      platformApi.selectTextContentByType(params, null, this.selectTextContentByTypeOnSuccess, null);
    } else {
      platformApi.selectTextContentByType(params, this.selectTextContentByTypeOnStart, this.selectTextContentByTypeOnSuccess, this.selectTextContentByTypeOnFailed);
    }
  },
  selectTextContentByTypeOnStart: function () {
    wx.showLoading({
      title: '加载协议中',
      mask: true
    })
  },
  selectTextContentByTypeOnSuccess: function (res) {
    wx.hideLoading();
    wxParse.wxParse('staffAgreement', 'html', res.msg, this, 5);
    storage.set(storageConstant.staffAgreement, null, res.msg, 0);
  },
  selectTextContentByTypeOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '协议加载失败',
      icon: "none"
    })
  },
  /**
   * 录音事件
   */
  soundRecordCli: function () {
    let state = this.data.soundRecordState;
    if (state == 0) {
      soundRecordUtil.stop(this.soundRecordStopOnSuccess);
    } else {
      soundRecordUtil.start(this.soundRecordStartOnSuccess);
    }
  },
  soundRecordStartOnSuccess: function () {
    this.setData({
      soundRecordState: 0
    })
  },
  soundRecordStopOnSuccess: function (res) {
    let form = this.data.form;
    form.voiceUrl = res.tempFilePath;
    form.voiceTime = Math.round(res.duration * 0.001);
    this.setData({
      soundRecordState: -1,
      form: form
    })
  },
  /**
   * 录音播放组件点击事件
   */
  audioCli: function () {
    let state = this.data.audioState;
    if (state == 0) {
      app.globalData.audioContext.pause();
      state = -1;
    } else {
      let form = this.data.form;
      app.globalData.audioContext.autoplay = true;
      app.globalData. audioContext.src = form.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
      state = 0;
    }
    this.setData({
      audioState: state
    })
  },
  /**
   * 上传录音文件点击事件
   */
  soundRecordUploadCli: function () {
    let that = this;
    wx.chooseMessageFile({
      count: 1,
      type: 'file',
      success(res) {
        // 判断文件的正确性(m4a、mp3)
        let relFormat = ["m4a", "mp3"];
        let path = res.tempFiles[0].path;
        let pathArr = path.split(".");
        if (relFormat.indexOf(pathArr[1]) <= -1) {
          wx.showToast({
            title: '亲爱的 请选择格式为m4a/mp3的音频文件哟',
            icon: "none",
            duration: 3000
          })
          return;
        }
        // 通过开始获取文件的详细信息
        that.setData({
          ['form.voiceUrl']: path,
          ['form.voiceTime']: null
        })
      }
    })
  },
  /**
   * 文件上传成功
   */
  uploadFileSuccess: function (e) {
    let file = e.detail.file;
    let result = e.detail.result;
    if (result.code != 200) {
      return;
    }
    let data = result.data;
    let addObj = {
      uid: file.uid,
      url: file.url,
      requestId: data.requestId,
      imgUrl: data.fileUrl
    };
    let tempArr = this.data.photoArr;
    tempArr.push(addObj);
    this.setData({
      photoArr: tempArr
    })
  },
  /**
   * 文件上传失败事件
   */
  uploadFileFail: function (e) {
    wx.showToast({
      title: '文件上传失败',
      icon: "none"
    })
  },
  /**
   * 文件删除事件
   */
  uploadFileRemove: function (e) {
    let requestId = e.detail.file.requestId;
    let tempArr = this.data.photoArr;
    tempArr.splice(this.matchPhotoArrItem(tempArr, requestId), 1);
    this.setData({
      photoArr: tempArr
    })
  },
  matchPhotoArrItem: function (arr, requestId) {
    for (let i = 0; i < arr.length; i++) {
      if (arr[i].requestId == requestId) {
        return i;
      }
    }
    return 0;
  },
  /**
   * 协议勾选改变事件
   */
  ifAgreeChange: function (e) {
    let temp = this.data.ifAgree;
    this.setData({
      ifAgree: !temp
    })
  },
  /**
   * 改变协议容器事件
   */
  changeStaffAgreementDrawer: function () {
    let temp = this.data.staffAgreementDrawer;
    this.setData({
      staffAgreementDrawer: !temp
    })
  },
  /**
   * 切换录音上传模式
   */
  switchSoundRecordType: function () {
    let temp = this.data.uploadSoundRecordType;
    this.setData({
      uploadSoundRecordType: temp == '0' ? '1' : '0'
    })
  },
  /**
   * 昵称输入事件
   */
  nickNameInput: function (e) {
    this.setData({
      ["form.nickName"]: e.detail.value
    })
  },
  /**
   * 性别改变事件
   */
  sexChange: function (e) {
    this.setData({
      ["form.sex"]: e.detail.value
    })
  },
  /**
   * 微信号输入事件
   */
  weChatNumInput: function (e) {
    this.setData({
      ["form.weChatNum"]: e.detail.value
    })
  },
  /**
   * 手机号码输入事件
   */
  phoneInput: function (e) {
    this.setData({
      ["form.phone"]: e.detail.value
    })
  },
  /**
   * 个人介绍输入事件
   */
  selfIntroductionInput: function (e) {
    this.setData({
      ["form.selfIntroduction"]: e.detail.value
    })
  },
  /**
   * 备注/留言输入事件
   */
  remarkInput: function (e) {
    this.setData({
      ["form.remark"]: e.detail.value
    })
  },
  /**
   * 推荐码输入事件
   */
  referralCodeInput: function (e) {
    this.setData({
      ["form.referralCode"]: e.detail.value
    })
  },
  /**
   * 出生日期选择事件
   */
  birthDateChange: function (e) {
    this.setData({
      ["form.birthDate"]: e.detail.value
    })
  },
  /**
   * 所在地区选择事件
   */
  regionChange: function (e) {
    this.setData({
      ["form.province"]: e.detail.value[0],
      ["form.city"]: e.detail.value[1],
      ["form.region"]: e.detail.value[2]
    })
  },
  /**
   * 提交事件
   */
  submit: function () {
    // 打包数据
    let userInfo = this.data.userInfo;
    let form = this.data.form;
    let photoArr = this.data.photoArr;
    // 先校验用户信息
    if (userInfo == null) {
      let info = app.globalData.userInfo;
      if (info == null) {
        wx.showToast({
          title: '请先提出登录后再进行申请哟',
          icon: "none"
        })
        return;
      }
      userInfo = info;
    }
    if (!this.checkData()) {
      return;
    }
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
    // 开始封装数据
    let params = form;
    let images = [];
    for (let index in photoArr) {
      images.push(photoArr[index].imgUrl);
    }
    params.imageArr = images.join(",");
    params.userId = userInfo.id;
    params.ossKey = "scound_record";
    params.state = "1";
    this.uploadStart(staffApi.applyUrl,params);
  },
  /**
   * 更新申请
   */
  update: function () {
    if (!this.checkData()) {
      return;
    }
    // 开始封装数据
    let form = this.data.form;
    let photoArr = this.data.photoArr;
    let params = {...form};
    let images = [];
    for (let index in photoArr) {
      images.push(photoArr[index].imgUrl);
    }
    params.imageArr = images.join(",");
    params.state = "1";
    // 判断录音是否改变
    let info = this.data.staffInfo;
    if (info.voiceUrl == form.voiceUrl) {
      staffApi.update(params, this.updateOnStart, this.updateOnSuccess, this.updateOnFailed, this.updateOnWarn);
    } else {
      wx.showLoading({
        title: '正在提交',
        mask: true
      })
      params.ossKey = "scound_record";
      this.uploadStart(staffApi.updateUrl,params);
    }
  },
  updateOnStart:function(){
    wx.showLoading({
      title: '正在提交',
      mask: true
    })
  },
  updateOnSuccess:function(res){
    wx.hideLoading();
    wx.showModal({
      title: '提示',
      content: '提交成功，我们将尽快为您审核哟',
      showCancel: false,
      complete: (res) => {
        if (res.confirm) {
          wx.navigateBack({ delta: 1 });
        }
      }
    })
  },
  updateOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '提交失败，请重试',
      icon: "none"
    })
  },
  updateOnWarn:function(res){
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 开始提交上传
   */
  uploadStart: function (url, params) {
    // 开始请求
    wx.uploadFile({
      filePath: params.voiceUrl,
      name: 'soundRecordFile',
      url: requestUtil.prefix + url,
      formData: requestUtil.removeNullProperty(params),
      timeout: 10000,
      header: tokenUtil.buildHeader(),
      success: function (res) {
        if (res.statusCode == globalCanstanats.httpState.ok) {
          let data = JSON.parse(res.data);
          let requestCode = data.code;
          switch (requestCode) {
            case globalCanstanats.httpState.ok:
              wx.showModal({
                title: '提示',
                content: '提交成功，我们将尽快为您审核哟',
                showCancel: false,
                complete: (res) => {
                  if (res.confirm) {
                    wx.navigateBack({ delta: 1 });
                  }
                }
              })
              break;
            case globalCanstanats.httpState.warn:
              wx.showToast({
                title: data.msg,
                icon: "none"
              })
              break;
            case globalCanstanats.httpState.operate:
              wx.showToast({
                title: '提交失败，请重试',
                icon: "none"
              })
              break;
            default:
              wx.showToast({
                title: '提交失败，请重试',
                icon: "none"
              })
              break;
          }
        } else {
          wx.showToast({
            title: '提交失败，请重试',
            icon: "none"
          })
        }
      }, fail: function (e) {
        wx.showToast({
          title: '提交失败，请重试',
          icon: "none"
        })
      }, complete: function (e) {
        wx.hideLoading();
      }
    })
  },
  /**
   * 校验数据
   */
  checkData: function () {
    let form = this.data.form;
    let photoArr = this.data.photoArr;
    // 基本信息必填校验
    if (utils.isEmpty(form.nickName) || utils.isEmpty(form.sex) || utils.isEmpty(form.weChatNum) || utils.isEmpty(form.phone) || utils.isEmpty(form.birthDate) || utils.isEmpty(form.region) || utils.isEmpty(form.selfIntroduction)) {
      wx.showToast({
        title: '请完善您的基本信息后再提交哟',
        icon: "none"
      })
      return false;
    }
    // 录音校验
    if (utils.isEmpty(form.voiceUrl)) {
      wx.showToast({
        title: '请完善您的录音哟',
        icon: "none"
      })
      return false;
    }
    // 图片校验
    if (photoArr == null || photoArr.length <= 0) {
      wx.showToast({
        title: '请上传至少一张图片哟',
        icon: "none"
      })
      return false;
    }
    // 协议接受校验
    if (!this.data.ifAgree) {
      wx.showToast({
        title: '请先阅读店员协议勾选同意后才能提交申请哟',
        icon: "none"
      })
      return false;
    }
    return true;
  }
})
