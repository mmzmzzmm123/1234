const app = getApp();
import { $wuxDialog } from '../../../components/wux/dist/index';
import utils from "../../../utils/util";
import requestUtil from "../../../apis/request";
import tokenUtil from "../../../utils/token";
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import globalCanstanats from "../../../constans/globalConstant";
import staffApi from "../../../apis/staff/staffApi";
import soundRecordUtil from "../../../utils/soundRecord";
import requestApi from "../../../apis/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    userInfo: app.globalData.userInfo, // 用户信息
    staffInfo: app.globalData.staffInfo, // 店员信息
    sexRange: [
      {
        label: "男",
        value: "0"
      }, {
        label: "女",
        value: "1"
      }
    ],
    ifChangeSoundRecord: false, // 是否改变录音文件
    uploadSoundRecordType: 0, // 0直接录音，1上传文件
    soundRecordState: -1, // -1 停止录音 0 录音中
    audioState: -1, // -1 停止 0 播放中
    fileUploadUrl: requestUtil.uploadSingleFileUrl,
    photoArr: [], // 员工相册集合
    fileUploadForm: { // 文件上传oss类型
      ossKey: "staff_photo"
    },
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
    app.globalData.audioContext.onEnded(() => {
      this.setData({
        audioState: -1
      })
    })
  },
  onShow() {
    // 加载用户信息和店员信息
    this.setData({
      userInfo: app.globalData.userInfo
    })
    // 店员信息
    let staffInfo = storage.get(storageConstant.staffInfo, null);
    if (staffInfo != null) {
      this.setData({
        staffInfo: staffInfo
      })
      // 构建员工相册数据
      this.buildStaffPhotoArr();
    }
  },
  onUnload(){
    app.globalData.audioContext.pause();
  },
  /**
   * 构建员工相册数据
   */
  buildStaffPhotoArr: function () {
    if (this.data.photoArr.length > 0) {
      return;
    }
    let staffInfo = this.data.staffInfo;
    let arr = [...staffInfo.photoVoList];
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
   * 昵称输入事件
   */
  nickNameInput: function (e) {
    this.setData({
      ["staffInfo.nickName"]: e.detail.value
    })
  },
  /**
   * 性别改变事件
   */
  sexChange: function (e) {
    this.setData({
      ["staffInfo.sex"]: e.detail.value
    })
  },
  /**
   * 微信号输入事件
   */
  weChatNumInput: function (e) {
    this.setData({
      ["staffInfo.weChatNum"]: e.detail.value
    })
  },
  /**
   * 手机号码输入事件
   */
  phoneInput: function (e) {
    this.setData({
      ["staffInfo.phone"]: e.detail.value
    })
  },
  /**
   * 出生日期选择事件
   */
  birthDateChange: function (e) {
    this.setData({
      ["staffInfo.birthDate"]: e.detail.value
    })
  },
  /**
   * 所在地区选择事件
   */
  regionChange: function (e) {
    this.setData({
      ["staffInfo.province"]: e.detail.value[0],
      ["staffInfo.city"]: e.detail.value[1],
      ["staffInfo.region"]: e.detail.value[2]
    })
  },
  /**
   * 个人介绍输入事件
   */
  selfIntroductionInput: function (e) {
    this.setData({
      ["staffInfo.selfIntroduction"]: e.detail.value
    })
  },
  /**
   * 添加标签事件
   */
  addLabelCli: function () {
    let that = this;
    let staffInfo = this.data.staffInfo;
    let selfTags = staffInfo.selfTags;
    let labelTotle = 0;
    if (selfTags != null) {
      let arr = selfTags.split(",");
      for (let index in arr) {
        labelTotle += arr[index].length;
      }
    }
    if ((30 - labelTotle) <= 1) {
      wx.showToast({
        title: '目前总标签字符：' + labelTotle + '，已不足再添加标签的量拉',
        icon: "none"
      })
      return;
    }
    $wuxDialog().prompt({
      resetOnClose: true,
      title: '添加标签',
      content: '提示：标签内容最少输入两个字符，最多8个字符，总标签最多30字符哟，目前总标签字符数：' + labelTotle,
      defaultText: "",
      placeholder: '请输入标签内容',
      maxlength: (30 - labelTotle >= 8 ? 8 : (30 - labelTotle)),
      onConfirm(e, response) {
        if (response == null || response == "" || response == undefined) {
          wx.showToast({
            title: '标签内容不能为空',
            icon: "none"
          })
          return;
        }
        if (response.length < 2) {
          wx.showToast({
            title: '标签内容最少输入两个字符',
            icon: "none"
          })
          return;
        }
        if (selfTags != null && selfTags != "") {
          selfTags += ("," + response);
        } else {
          selfTags = response;
        }
        that.setData({
          ['staffInfo.selfTags']: selfTags
        })
      },
    })
  },
  /**
   * 删除标签值
   */
  deleteLabel: function (e) {
    let index = e.currentTarget.dataset.index;
    let selfTagsArr = this.data.staffInfo.selfTags.split(",");
    selfTagsArr.splice(index, 1);
    this.setData({
      ["staffInfo.selfTags"]: selfTagsArr.join(",")
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
      soundRecordState: 0,
      ifChangeSoundRecord: true
    })
  },
  soundRecordStopOnSuccess: function (res) {
    let staffInfo = this.data.staffInfo;
    staffInfo.voiceUrl = res.tempFilePath;
    staffInfo.voiceTime = Math.round(res.duration * 0.001);
    this.setData({
      soundRecordState: -1,
      staffInfo: staffInfo,
      ifChangeSoundRecord: true
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
          ['staffInfo.voiceUrl']: path,
          ['staffInfo.voiceTime']: null,
          ifChangeSoundRecord: true
        })
      }
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
      let staffInfo = this.data.staffInfo;
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = staffInfo.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
      state = 0;
    }
    this.setData({
      audioState: state
    })
  },
  /**
   * 图片选择
   */
  chooseImage: function () {
    let that = this;
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        wx.showLoading({
          title: '正在上传',
          mask: true
        })
        wx.uploadFile({
          filePath: res.tempFiles[0].tempFilePath,
          name: 'file',
          url: requestApi.uploadSingleFileUrl,
          formData: { ossKey: "avatar" },
          timeout: 10000,
          success: function (e) {
            let fileUrl = JSON.parse(e.data).data.fileUrl;
            that.setData({
              ["staffInfo.avatarUrl"]: fileUrl
            })
          }, fail: function (e) {
            wx.showToast({
              title: '上传失败，请重试',
              icon: "none"
            })
          }, complete: function (e) {
            wx.hideLoading();
          }
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
    let tempArr = [...this.data.photoArr];
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
   * 提交数据
   */
  submit: function () {
    if (!this.checkData()) {
      return;
    }
    let that = this;
    let ifChangeSoundRecord = this.data.ifChangeSoundRecord;
    let staffInfo = this.data.staffInfo;
    let photoArr = this.data.photoArr;
    let images = [];
    for (let index in photoArr) {
      images.push(photoArr[index].imgUrl);
    }
    staffInfo.imageArr = images.join(",");
    if (ifChangeSoundRecord) {
      that.updateOnStart();
      wx.uploadFile({
        filePath: staffInfo.voiceUrl,
        name: 'soundRecordFile',
        url: requestUtil.prefix + staffApi.updateUrl,
        formData: requestUtil.removeNullProperty(staffInfo),
        timeout: 10000,
        header: tokenUtil.buildHeader(),
        success: function (res) {
          if (res.statusCode == globalCanstanats.httpState.ok) {
            let data = JSON.parse(res.data);
            let requestCode = data.code;
            switch (requestCode) {
              case globalCanstanats.httpState.ok:
                that.updateOnSuccess(res.data);
                break;
              case globalCanstanats.httpState.warn:
                that.updateOnWarn(res.data);
                break;
              case globalCanstanats.httpState.operate:
                that.uploadFileFail(res.data);
                break;
              default:
                that.uploadFileFail(res.data);
                break;
            }
          } else {
            that.uploadFileFail(res.data);
          }
        }, fail: function (e) {
          that.uploadFileFail(res.data);
        }
      })
    } else {
      staffApi.update(staffInfo, this.updateOnStart, this.updateOnSuccess, this.updateOnFailed, this.updateOnWarn);
    }
  },
  updateOnStart: function () {
    wx.showLoading({
      title: '正在保存',
      mask: true
    })
  },
  updateOnSuccess: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '保存成功',
      icon: "success",
      ifChangeSoundRecord: true
    })
    staffApi.selectByUserId({ userId: this.data.staffInfo.userId }, null, this.loadStaffInfoOnSuccess, null);
  },
  loadStaffInfoOnSuccess: function (res) {
    app.updateStaffInfo(res);
  },
  updateOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '保存失败',
      icon: "error"
    })
  },
  updateOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none"
    })
  },
  /**
   * 校验数据
   */
  checkData: function () {
    let form = this.data.staffInfo;
    let photoArr = this.data.photoArr;
    if(this.data.ifHide == '0' && (utils.isEmpty(form.weChatNum) || utils.isEmpty(form.phone))){
      wx.showToast({
        title: '请完善您的基本信息后再提交哟',
        icon: "none"
      })
      return false;
    }
    // 基本信息必填校验
    if (utils.isEmpty(form.avatarUrl) || utils.isEmpty(form.selfTags) || utils.isEmpty(form.nickName) || utils.isEmpty(form.sex) || utils.isEmpty(form.birthDate) || utils.isEmpty(form.region) || utils.isEmpty(form.selfIntroduction)) {
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
    return true;
  }
})