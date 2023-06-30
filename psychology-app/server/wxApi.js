import httprequest from "./httpRequest";
const APPID = "wx8604d98104f67e66";
const APPSECRET = "98dca366602a0806da390ed138e4a7ea";

// 更新tonken
function updateToken() {
  return new Promise((resolve, reject) => {
    httprequest
      .get(
        `/weixin/cgi-bin/token?grant_type=client_credential&appid=${APPID}&secret=${APPSECRET}`
      )
      .then((res) => {
        resolve(res.data.access_token);
      })
      .catch((err) => {
        reject(err);
      });
  });
}
export const wxLogin = (data) => {
  return new Promise((resolve, reject) => {
    httprequest
      .post(`/app/user/wxLogin`, data)
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};
export const wxLoginCallBack = (code) => {
  return new Promise((resolve, reject) => {
    httprequest
      .get(`/app/user/callBack?code=` + code)
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
};
// 根据code获取网站授权token
export function getTokenFormCode(code) {
  return new Promise((resolve, reject) => {
    httprequest
      .get(
        `/weixin/sns/oauth2/access_token?appid=${APPID}&secret=${APPSECRET}&code=${code}&grant_type=authorization_code`
      )
      .then((res) => {
        resolve(res);
      })
      .catch((err) => {
        reject(err);
      });
  });
}

// 通知后台给微信预支付下单，并返回支付签名
export function getPaySign(userId, produteId, amount, extra) {
  return new Promise((resolve, reject) => {
	  let params = null
	  if (extra.module === 'course') {
		  params = {
			  userId: userId,
			  courseId: produteId,
			  module: extra.module,
        amount: amount
		  }
	  } else if (extra.module === 'gauge') {
		  params = {
			  userId: userId,
			  gaugeId: produteId,
			  module: extra.module,
        amount: amount
		  }
	  } else if (extra.module === 'consult') {
          params = {
              userId: userId,
              serveId: produteId,
              workId: extra.workId,
              module: extra.module,
              amount: amount
          }
      }
	  httprequest
		.post(
		  `/app/api/wechatPay/v3/wechatPay`, params
		)
		.then((res) => {
		  resolve(res);
		})
		.catch((err) => {
		  reject(err);
		});
	});
}


function onBridgeReady(wxParam, successCallback) {
    WeixinJSBridge.invoke('getBrandWCPayRequest', {
        "appId": wxParam.appId,     //公众号ID，由商户传入     
        "timeStamp": wxParam.timeStamp,     //时间戳，自1970年以来的秒数     
        "nonceStr": wxParam.nonceStr,      //随机串     
        "package": wxParam.package,
        "signType": wxParam.signType,     //微信签名方式：     
        "paySign": wxParam.paySign
	},
    function(res) {
        if (res.err_msg == "get_brand_wcpay_request:ok") {
            // 使用以上方式判断前端返回,微信团队郑重提示：
            //res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
			successCallback()
			
        }
    });
}
export function wxPay(wxParam, successCallback) {
	if (typeof WeixinJSBridge == "undefined") {
	    if (document.addEventListener) {
	        document.addEventListener('WeixinJSBridgeReady', onBridgeReady(wxParam, successCallback), false);
	    } else if (document.attachEvent) {
	        document.attachEvent('WeixinJSBridgeReady', onBridgeReady(wxParam, successCallback));
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(wxParam, successCallback));
	    }
	} else {
	    onBridgeReady(wxParam, successCallback);
	}
}


export function wxJSConfig(url) {
  let params = {
    url
  }
  return new Promise((resolve, reject) => {
    httprequest
    .post(
      `/app/wxJS/config`, params
    )
    .then((res) => {
      resolve(res);
    })
    .catch((err) => {
      reject(err);
    });
  })
}