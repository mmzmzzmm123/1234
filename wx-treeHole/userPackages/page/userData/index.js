const app = getApp();
import { $wuxDialog } from '../../../components/wux/dist/index';
import storage from "../../../utils/storage";
import storageConstant from "../../../constans/storageConstant";
import userApi from "../../../apis/user/userApi";
import requestApi from "../../../apis/request";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pWidth: 0, //获取手机宽度
    pHeight: 0, //获取手机高度
    userInfo: app.globalData.userInfo,
    tempUserInfo: null,
    sexArr: [
      {
        "label": "男",
        value: "0"
      },
      {
        "label": "女",
        value: "1"
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
  },
  /**
   * 名字点击事件
   */
  nickNameCli: function () {
    let that = this;
    let userInfo = this.data.userInfo;
    $wuxDialog().prompt({
      resetOnClose: true,
      title: '昵称修改',
      content: '提示：名称最多10个字符哟',
      defaultText: userInfo.nickName,
      placeholder: '请输入您的昵称',
      maxlength: 10,
      onConfirm(e, response) {
        if (response == null || response == "" || response == undefined) {
          wx.showToast({
            title: '昵称不能为空哟',
            icon: "none"
          })
          return;
        }
        let tempUserInfo = userInfo;
        tempUserInfo.nickName = response;
        that.setData({
          tempUserInfo: tempUserInfo
        })
        that.updateUser({nickName: response});
      },
    })
  },
  /**
   * 性别修改
   */
  sexChange:function(e){
    let userInfo = this.data.userInfo;
    let sex = e.detail.value;
    let tempUserInfo = userInfo;
    tempUserInfo.sex = sex;
    this.setData({
      tempUserInfo: tempUserInfo
    })
    this.updateUser({sex: sex});
  },
  /**
   * 用户邀请码生成
   */
  buildReferralCode:function(){
    let userInfo = this.data.userInfo;
    if(userInfo.referralCode != null){
      return;
    }
    userApi.generateReferralCode({userId:userInfo.id},this.generateReferralCodeOnStart,this.generateReferralCodeOnSuccess,this.generateReferralCodeOnFailed);
  },
  generateReferralCodeOnStart:function(){
    wx.showLoading({
      title: '正在生成',
      mask: true
    })
  },
  generateReferralCodeOnSuccess:function(res){
    wx.hideLoading();
    let userInfo = this.data.userInfo;
    userInfo.referralCode = res.msg;
    this.setData({
      userInfo: userInfo
    })
    app.globalData.userInfo = userInfo;
    storage.set(storageConstant.userInfo, null, userInfo, 0);
  },
  generateReferralCodeOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '生成失败',
      icon: "error"
    })
  },
  /**
   * 等级展示改变事件
   */
  hideLevelChange:function(e){
    let userInfo = this.data.userInfo;
    let tempUserInfo = userInfo;
    tempUserInfo.hideLevel = e.detail.value?0:1;
    this.setData({
      tempUserInfo: tempUserInfo
    })
    this.updateUser({hideLevel: tempUserInfo.hideLevel});
  },
  /**
   * 图片选择
   */
  chooseImage:function(){
    let that = this;
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      success:function(res){
        wx.showLoading({
          title: '正在上传',
          mask: true
        })
        wx.uploadFile({
          filePath: res.tempFiles[0].tempFilePath,
          name: 'file',
          url: requestApi.uploadSingleFileUrl,
          formData: {ossKey: "avatar"},
          timeout: 10000,
          success:function(e){
            let fileUrl = JSON.parse(e.data).data.fileUrl;
            let userInfo = that.data.userInfo;
            let tempUserInfo = userInfo;
            tempUserInfo.avatarUrl = fileUrl;
            that.setData({
              tempUserInfo: tempUserInfo
            })
            that.updateUser({avatarUrl: fileUrl});
          },fail:function(e){
            wx.showToast({
              title: '上传失败，请重试',
              icon: "none"
            })
          },complete:function(e){
            wx.hideLoading();
          }
        })
      }
    })
  },
  /**
   * 修改用户信息
   */
  updateUser:function(params){
    let userInfo = this.data.userInfo;
    params.id = userInfo.id;
    userApi.update(params,this.updateUserOnStart,this.updateUserOnSuccess,this.updateUserOnFailed);
  },
  updateUserOnStart:function(){
    wx.showLoading({
      title: '正在修改',
      mask: true
    })
  },
  updateUserOnSuccess:function(res){
    wx.hideLoading();
    let userInfo = this.data.tempUserInfo;
    this.setData({
      userInfo: userInfo,
      tempUserInfo: null
    })
    app.globalData.userInfo = userInfo;
    storage.set(storageConstant.userInfo, null, userInfo, 0);
  },
  updateUserOnFailed:function(res){
    wx.hideLoading();
    wx.showToast({
      title: '修改失败',
      icon: "error"
    })
  },
})