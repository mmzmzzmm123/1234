import request from '@/utils/request'

// 调用微信接口
export function getOpenId() {
  return request({
    //此接口部分代码过于简单就不展示了
    url: '/front/auth/getOpenId',
    params: "",
    type: "POST",
    async: true,
    successCallback: function (r) {
        if(r.data.openid == null){
            //返回的openid如果为空就进入微信授权接口
            window.location.href = "/front/auth/wx_login";
        }
    }
  })
}