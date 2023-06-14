import { wxJSConfig } from '@/server/wxApi.js'
//引入js-sdk
var wx = require('weixin-js-sdk');

export default {
    getConfig(infoTitle, infoDesc, infoLink, infoImg, url) {  //自定义分享所需要的参数
      wxJSConfig(url).then(res => {
        console.log(res)
        
        wx.config({
            debug: false,  //测试时候用true 能看见wx.config的状态是否是config:ok
            appId: res.data.appId, // 必填，公众号的唯一标识（公众号的APPid）
            timestamp: res.data.timestamp, // 必填，生成签名的时间戳
            nonceStr: res.data.nonceStr, // 必填，生成签名的随机串
            signature: res.data.signature, // 必填，签名
            jsApiList: [
                'onMenuShareTimeline', // 分享给好友
                'onMenuShareAppMessage', // 分享到朋友圈
                'updateAppMessageShareData', // 分享给好友1.4
                'updateTimelineShareData' // 分享到朋友圈1.4
            ], // 必填，需要使用的JS接口列表
            openTagList: ['wx-open-launch-app'] // 可选，需要使用的开放标签列表，例如['wx-open-launch-app']
        });
        wx.ready(function() {
            var shareData = {
                title: infoTitle, // 分享标题
                desc: infoDesc, // 分享描述
                link: infoLink, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                imgUrl: infoImg, // 分享图标
                success: function(res) {
                     console.log('分享成功： ', res)
                }
            };
            //自定义“分享到朋友圈”及“分享到QQ空间”按钮的分享内容（1.4.0）
            wx.updateAppMessageShareData(shareData);
            //自定义“分享给朋友”及“分享到QQ”按钮的分享内容（1.4.0）
            wx.updateTimelineShareData(shareData);
        })
        wx.error(function(res){
          // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
          console.error(res)
        });
      })   
        
    }
}