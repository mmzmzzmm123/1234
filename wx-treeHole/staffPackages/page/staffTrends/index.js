const app = getApp();
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import globalCanstanats from "../../../constans/globalConstant";
import requestUtil from "../../../apis/request";
import soundRecordUtil from "../../../utils/soundRecord";
import tokenUtil from "../../../utils/token";
import staffTrendsApi from "../../../apis/staff/staffTrendsApi";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    firstJoin: true, // 首次进入
    staffInfo: app.globalData.staffInfo, // 店员信息
    requestParams: { // 请求数据参数
      pageNum: 1,
      pageSize: 20
    },
    total: 0, // 总条数
    dataList: [], // 记录数
    trendsFormDrawer: false, // 发布动态容器状态
    currentIndex: 0, // 当前tab数据
    currentKey: "image",
    tabs: [
      {
        key: "image",
        title: "图片"
      }, {
        key: "video",
        title: "视频"
      }, {
        key: "voice",
        title: "语音"
      }
    ],
    fileUploadUrl: requestUtil.uploadSingleFileUrl,
    fileUploadForm: { // 文件上传oss类型
      ossKey: "staff_trends"
    },
    trendsContent: null, // 动态文本
    photoArr: [], // 图片数组
    videoArr: [], // 视频数组
    voiceUrl: null, // 语音地址
    voiceTime: null, // 语音秒数
    uploadSoundRecordType: 0, // 0直接录音，1上传文件
    soundRecordState: -1, // -1 停止录音 0 录音中
    audioState: -1, // -1 停止 0 播放中
    listAudioIndex: -1, // 列表播放语音下标
    loadState: { // 列表刷新状态
      refreshState: false, // 刷新状态
      bottomDataLoadState: false, // 下拉加载状态
    }
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
    // 全局语音监听暂停事件
    app.globalData.audioContext.onEnded(() => {
      this.setData({
        audioState: -1
      })
      let dataList = this.data.dataList;
      let listAudioIndex = this.data.listAudioIndex;
      if (listAudioIndex != -1) {
        dataList[listAudioIndex].audioState = -1;
        this.setData({
          dataList: dataList,
          listAudioIndex: -1
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    // 店员信息
    let staffInfo = storage.get(storageConstant.staffInfo, null);
    if (staffInfo != null) {
      this.setData({
        staffInfo: staffInfo
      })
    }
    // 加载店员动态数据
    let onStart = null;
    if (this.data.firstJoin) {
      onStart = this.loadStaffTrendsDataOnStart;
      this.setData({
        firstJoin: false
      })
    }
    this.loadStaffTrendsData(onStart);
  },
  onUnload() {
    app.globalData.audioContext.pause();
  },
  /**
   * 开始请求数据
   */
  loadStaffTrendsData: function (onStart) {
    let params = this.data.requestParams;
    staffTrendsApi.selectStaffTrends(params, onStart, this.loadStaffTrendsDataOnSuccess, this.loadStaffTrendsDataOnFailed);
  },
  loadStaffTrendsDataOnStart: function () {
    wx.showLoading({
      title: '正在加载',
      mask: true
    })
  },
  loadStaffTrendsDataOnSuccess: function (res) {
    this.closeLoadState();
    let data = res.data.data;
    let dataList = this.data.dataList;
    let bottomDataLoadState = this.data.loadState.bottomDataLoadState;
    if (bottomDataLoadState) {
      for (let index in data) {
        dataList.push(data[index]);
      }
    } else {
      dataList = data;
    }
    this.setData({
      dataList: dataList,
      total: res.data.total
    })
  },
  loadStaffTrendsDataOnFailed: function (res) {
    this.closeLoadState();
    wx.showToast({
      title: '请求失败',
      icon: "none"
    })
  },
  /**
   * 关闭加载状态
   */
  closeLoadState: function () {
    wx.hideLoading();
    let that = this;
    setTimeout(function () {
      that.setData({
        ["loadState.refreshState"]: false,
        ["loadState.bottomDataLoadState"]: false
      })
    }, 500)
  },
  /**
   * 刷新事件
   */
  refresh: function () {
    this.setData({
      ["loadState.refreshState"]: true,
      ["requestParams.pageNum"]: 1
    })
    this.loadStaffTrendsData(this.loadStaffTrendsDataOnStart);
    app.globalData.audioContext.pause();
  },
  /**
   * 加载下一页数据
   */
  loadNextPageData: function () {
    let requestParams = this.data.requestParams;
    let bottomDataLoadState = this.data.loadState.bottomDataLoadState;
    if (((requestParams.pageNum * requestParams.pageSize) >= this.data.total) || bottomDataLoadState) {
      return;
    }
    let pageNum = this.data.requestParams.pageNum;
    this.setData({
      ["loadState.bottomDataLoadState"]: true,
      ["requestParams.pageNum"]: pageNum + 1
    })
    this.loadStaffTrendsData(null);
  },
  /**
   * 改变发布动态容器状态
   */
  changeTrendsFormDrawer: function () {
    let temp = this.data.trendsFormDrawer;
    this.setData({
      trendsFormDrawer: !temp,
      trendsContent: null,
      photoArr: [],
      videoArr: [],
      voiceUrl: null,
      voiceTime: null
    })
  },
  /**
   * 动态内容输入事件
   */
  trendsContentInput: function (e) {
    this.setData({
      trendsContent: e.detail.value
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
      currentIndex: index,
      photoArr: [],
      videoArr: [],
      voiceUrl: null,
      voiceTime: null
    })
    // 如果正在录音，那么就直接停止并保存
    let state = this.data.soundRecordState;
    if (state == 0) {
      this.setData({
        soundRecordState: -1
      })
      soundRecordUtil.stop(null);
    }
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
    if (this.data.currentIndex == 0) {
      let tempArr = [...this.data.photoArr];
      tempArr.push(addObj);
      this.setData({
        photoArr: tempArr
      })
    } else {
      let tempArr = [...this.data.videoArr];
      tempArr.push(addObj);
      this.setData({
        videoArr: tempArr
      })
    }
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
    if (this.data.currentIndex == 0) {
      let tempArr = this.data.photoArr;
      tempArr.splice(this.matchPhotoArrItem(tempArr, requestId), 1);
      this.setData({
        photoArr: tempArr
      })
    } else {
      let tempArr = this.data.videoArr;
      tempArr.splice(this.matchPhotoArrItem(tempArr, requestId), 1);
      this.setData({
        videoArr: tempArr
      })
    }
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
      soundRecordState: 0
    })
  },
  soundRecordStopOnSuccess: function (res) {
    let staffInfo = this.data.staffInfo;
    staffInfo.voiceUrl = res.tempFilePath;
    staffInfo.voiceTime = Math.round(res.duration * 0.001);
    this.setData({
      soundRecordState: -1,
      voiceUrl: res.tempFilePath,
      voiceTime: Math.round(res.duration * 0.001)
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
          voiceUrl: path,
          voiceTime: null
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
      app.globalData.audioContext.autoplay = true;
      app.globalData.audioContext.src = this.data.voiceUrl;
      app.globalData.audioContext.loop = false;
      app.globalData.audioContext.play();
      state = 0;
    }
    this.setData({
      audioState: state
    })
  },
  /**
   * 提交事件
   */
  submit: function () {
    let trendsContent = this.data.trendsContent;
    let photoArr = this.data.photoArr;
    let videoArr = this.data.videoArr;
    let voiceUrl = this.data.voiceUrl;
    let voiceTime = this.data.voiceTime;
    // 判断数据是否为空
    if ((trendsContent == null || trendsContent == '') && photoArr.length <= 0 && videoArr.length <= 0 && voiceUrl == null) {
      wx.showToast({
        title: '请至少输入文本或完善一种多媒体文件',
        icon: "none"
      })
      return;
    }
    // 开始封装数据
    let params = {};
    // 文本数据
    if (trendsContent != null && trendsContent != '') {
      params.content = trendsContent;
    }
    // 图片数据
    if (photoArr.length > 0) {
      let tempArr = [];
      for (let index in photoArr) {
        tempArr.push(photoArr[index].imgUrl);
      }
      params.imgList = tempArr.join(",");
    }
    // 视频数据
    if (videoArr.length > 0) {
      let video = videoArr[0];
      params.videoUrl = video.imgUrl;
      params.ossKey = "scound_record";
    }
    // 语音数据
    if (voiceUrl != null) {
      params.voiceUrl = voiceUrl;
      if (voiceTime != null) {
        params.voiceTime = voiceTime;
      }
    }
    // 直接调用
    if (voiceUrl == null || voiceUrl == '') {
      staffTrendsApi.submit(params, this.submitOnStart, this.submitOnSuccess, this.submitOnFailed, this.submitOnWarn);
    } else {
      // 使用上传附件方式进行
      let that = this;
      this.submitOnStart();
      wx.uploadFile({
        filePath: params.voiceUrl,
        name: 'voiceFile',
        url: requestUtil.prefix + staffTrendsApi.submitUrl,
        formData: requestUtil.removeNullProperty(params),
        timeout: 10000,
        header: tokenUtil.buildHeader(),
        success: function (res) {
          if (res.statusCode == globalCanstanats.httpState.ok) {
            let data = JSON.parse(res.data);
            let requestCode = data.code;
            switch (requestCode) {
              case globalCanstanats.httpState.ok:
                that.submitOnSuccess(res.data);
                break;
              case globalCanstanats.httpState.warn:
                that.submitOnWarn(res.data);
                break;
              case globalCanstanats.httpState.operate:
                that.submitOnFailed(res.data);
                break;
              default:
                that.submitOnFailed(res.data);
                break;
            }
          } else {
            that.submitOnFailed(res.data);
          }
        }, fail: function (e) {
          that.submitOnFailed(e);
        }
      })
    }
  },
  submitOnStart: function () {
    wx.showLoading({
      title: '正在发布',
      mask: true
    })
  },
  submitOnSuccess: function (res) {
    wx.hideLoading();
    this.setData({
      ["requestParams.pageNum"]: 1,
      trendsFormDrawer: false
    })
    this.loadStaffTrendsData(null);
  },
  submitOnFailed: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
  submitOnWarn: function (res) {
    wx.hideLoading();
    wx.showToast({
      title: res.msg,
      icon: "none",
      duration: 2000
    })
  },
  /**
   * 列表语音点击事件
   */
  listAudioCli: function (e) {
    let index = e.currentTarget.dataset.index;
    let dataList = this.data.dataList;
    // 先判断是否需要暂停上一个语音条
    let listAudioIndex = this.data.listAudioIndex;
    if (listAudioIndex != -1 && listAudioIndex != index) {
      dataList[listAudioIndex].audioState = -1;
      app.globalData.audioContext.pause();
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
    this.setData({
      dataList: dataList,
      listAudioIndex: index
    })
  },
  /**
   * 列表相片点击事件
   */
  listImgCli: function (e) {
    let dataset = e.currentTarget.dataset;
    let obj = this.data.dataList[dataset.index];
    let imgListStr = obj.imgList;
    let imgArr = imgListStr.split(",");
    wx.previewImage({
      urls: imgArr,
      current: imgArr[dataset.imgindex]
    })
  },
  /**
   * 列表视频播放按钮
   */
  videoPlay: function (e) {
    let videoContext = wx.createVideoContext(e.currentTarget.id);
    videoContext.requestFullScreen({ direction: 0 });
  },
  /**
   * 视频进入/退出全屏
   */
  videoScreenChange: function (e) {
    if (!e.detail.fullScreen) {
      let videoContext = wx.createVideoContext(e.currentTarget.id);
      videoContext.pause();
    }
  },
  /**
   * 删除点击事件
   */
  deleteCli:function(e){
    let that = this;
    wx.showModal({
      title: '提示',
      content: '您确定要删除此动态吗？',
      complete: (res) => {
        if (res.confirm) {
          staffTrendsApi.deleteById({id:e.currentTarget.dataset.id},that.deleteByIdOnStart,that.deleteByIdOnSuccess,that.deleteByIdOnFailed);
        }
      }
    })
  },
  deleteByIdOnStart:function(){
    wx.showLoading({
      title: '正在删除',
      mask: true
    })
  },
  deleteByIdOnSuccess:function(res){
    wx.hideLoading();
    this.setData({
      ["requestParams.pageNum"]: 1
    })
    this.loadStaffTrendsData(null);
  },
  deleteByIdOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '请求失败',
      icon: "error"
    })
  },
})