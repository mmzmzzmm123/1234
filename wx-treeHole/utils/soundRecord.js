// 录音js
const recorderManager = wx.getRecorderManager();

/**
 * 开始录音
 */
let start = function (onSuccess) {
  let options = {
    duration: 20000,//指定录音的时长，单位 ms
    sampleRate: 16000,//采样率
    numberOfChannels: 1,//录音通道数
    encodeBitRate: 96000,//编码码率
    format: 'mp3',//音频格式，有效值 aac/mp3
    frameSize: 50,//指定帧大小，单位 KB
  }
  // 鉴权用户是否已授权
  wx.authorize({
    scope: 'scope.record',
    success: function (e) {
      recorderManager.start(options);
      recorderManager.onStart(() => {
        wx.vibrateLong({
          complete:function(){
          }
        });
        onSuccess();
      });
      //错误回调
      recorderManager.onError((res) => {
        console.log(res);
      })
    }, fail: function (e) {
      wx.showModal({
        title: '提示',
        content: '您未授权录音，功能将无法使用',
        showCancel: true,
        confirmText: "授权",
        confirmColor: "#8978E4",
        success: function (res) {
          if (res.confirm) {
            // 确认则打开设置页面（重点）
            wx.openSetting({
              success: (res) => {
                if (!res.authSetting['scope.record']) {
                  //未设置录音授权
                  wx.showToast({
                    title: '您未授权录音，功能将无法使用',
                    icon: "none"
                  })
                } else {
                  //第二次才成功授权
                  recorderManager.start(options);
                  recorderManager.onStart(() => {
                    onSuccess();
                    wx.vibrateLong({
                      complete:function(){
                      }
                    });
                  });
                  //错误回调
                  recorderManager.onError((res) => {
                    wx.showToast({
                      title: '录音失败',
                      icon: "none"
                    })
                  })
                }
              },
              fail: function () {
                wx.showToast({
                  title: '您未授权录音，功能将无法使用',
                  icon: "none"
                })
              }
            })
          } else if (res.cancel) {
            wx.showToast({
              title: '您未授权录音，功能将无法使用',
              icon: "none"
            })
          }
        },
        fail: function () {
          wx.showToast({
            title: '您未授权录音，功能将无法使用',
            icon: "none"
          })
        }
      })
    }
  })
}

let stop = function(onSuccess){
  recorderManager.stop();
  recorderManager.onStop((res) => {
    onSuccess(res);
    wx.vibrateLong({
      complete:function(){
      }
    });
  })
}

module.exports = {
  start: start,
  stop: stop
}