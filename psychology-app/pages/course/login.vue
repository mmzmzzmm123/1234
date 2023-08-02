<template>
    <view class="login-page">
        <img class="logo" src="/static/evaluation/logo.png" />
        <view class="system-title">三束光心理</view>
        <view class="title">验证码登录</view>
        <view class="ipt-box">
            <input class="uni-input" placeholder="请输入手机号" v-model="phone" maxlength="11" minlength="11" type="number"
                @blur="phoneBlur" />
        </view>
        <view class="error" v-show="!phoneValid">手机号码不正确</view>
        <view class="ipt-box code-ipt-box">
            <input class="uni-input" placeholder="请输入验证码" maxlength="6" minlength="6" v-model="code" />
            <span class="code-btn" @tap="getCode" v-show="!timeOut">获取验证码</span>
            <span class="code-btn timer" v-show="timeOut">{{ timer }}S后可重新获取</span>
        </view>
        <view class="error" v-show="!codeValid">验证码错误</view>
        <view class="cue-txt">未注册手机号验证后将自动注册新账户</view>
        <view class="login-btn" @tap="loginSubmit">登录</view>
        <view class="agreement-txt">
            <view class="user-agree-rd" :class="{ 'active': userAgree }" @tap="userAgree = !userAgree"></view>
            已阅读并同意&nbsp;<navigator open-type="redirect" url="/pages/evaluation/index" class="link-btn">用户协议</navigator>
            、<navigator url="/pages/evaluation/index" open-type="redirect" class="link-btn">隐私条款</navigator>
        </view>
    </view>
</template>
<script>
import utils from '@/utils/common'
import loginServer from '@/server/evaluation/login.js'
export default {
    data() {
        return {
            userAgree: false,
            code: '',
            phone: '',
            timer: 60,
            timeOut: null,
            loading: false,
            phoneValid: true,
            codeValid: true
        }
    },
    methods: {
        phoneBlur() {
            if (!(/^1\d{10}$/).test(this.phone)) {
                this.phoneValid = false;
            } else {
                this.phoneValid = true;
            }
        },
        async loginSubmit() {
            console.log(this.loading, this.phoneValid == false, this.code.length == 0)
            if (this.loading || this.phoneValid == false || this.code.length == 0) return;
            if (!this.userAgree) {
                uni.showToast({
                    icon: "error",
                    title: "请先同意用户协议、隐私条款",
                });
                return;
            }
            this.loading = true;
            uni.showLoading();
            let res = await loginServer.loginByPhone(this.phone, this.code);
            if (res == 1) {
                let callbacktype = utils.getParam(location.href, "callbacktype");
                switch (callbacktype) {
                    case '1':
                        uni.navigateTo({
                            url: "/pages/evaluation/user",
                        }); break;
                    case '2':
                        uni.navigateTo({
                            url: "/pages/evaluation/product/index?payOrder=1&id=" + utils.getParam(location.href, "courseId"),
                        }); break;
                    default:
                        uni.navigateTo({
                            url: "/pages/evaluation/user",
                        }); break;
                }
            } else {
                this.codeValid = false;
            }
            uni.hideLoading();
            this.loading = false;
        },
        async getCode() {
            if (this.timeOut || this.phoneValid == false || this.phone.length == 0 || this.loading) return;
            this.loading = true;
            uni.showLoading();
            let res = await loginServer.getCode(this.phone);
            uni.hideLoading();
            this.loading = false;
            if (res) {
                this.code = res;
                this.timeOut = setInterval(() => {
                    if (this.timer == 1) {
                        clearInterval(this.timeOut);
                        this.timeOut = null;
                        this.timer = 60;
                        return;
                    }
                    this.timer--;
                }, 1000);
            }
        }
    },
}</script>
<style lang="scss">
page {
    min-height: 667upx;

    .login-page {
        padding: 128upx 54upx 0;

        .logo {
            width: 120upx;
            height: 120upx;
            margin: 0 auto 20upx;
            display: block;
        }

        .system-title {
            font-size: 40upx;
            font-weight: 600;
            color: #333333;
            line-height: 56upx;
            text-align: center;
            margin-bottom: 96upx;
        }

        .title {
            font-size: 36upx;
            font-weight: 600;
            color: #333333;
            line-height: 50upx;
            margin-bottom: 64upx;
        }

        .ipt-box {
            height: 80upx;
            line-height: 40upx;
            padding: 20upx 0;
            border-bottom: 1px solid #ccc;
            box-sizing: border-box;
            color: #333;

            uni-input {
                font-size: 28upx;
            }

            .uni-input {
                flex: 1;
            }

            &.code-ipt-box {
                display: flex;
                flex-direction: row;
                justify-content: space-around;

            }

            .code-btn {
                font-size: 28upx;
                font-weight: 400;
                color: #FF703F;

                &.timer {
                    color: rgba(51, 51, 51, 0.8)
                }
            }
        }

        .cue-txt {
            font-size: 24upx;
            margin-top: 25upx;
            color: rgba(51, 51, 51, 0.5);
        }

        .login-btn {
            width: 639upx;
            height: 90upx;
            background: #FF703F;
            border-radius: 45upx;
            margin: 72upx auto;
            font-size: 36upx;
            font-weight: 600;
            color: #FFFFFF;
            line-height: 90upx;
            text-align: center;
        }

        .error {
            font-size: 24upx;
            font-weight: 400;
            color: #FF3F64;
            line-height: 33upx;
        }

        .agreement-txt {
            margin-top: 80upx;
            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: center;
            color: rgba(51, 51, 51, 0.8);

            .link-btn {
                color: rgba(31, 92, 228, 0.8);
            }

            .user-agree-rd {
                margin-right: 16upx;
                width: 32upx;
                height: 32upx;
                background-image: url("/static/icon/rd.png");
                background-size: 100% 100%;

                &.active {
                    background-image: url("/static/icon/rd-active.png");
                }
            }
        }
    }

}
</style>