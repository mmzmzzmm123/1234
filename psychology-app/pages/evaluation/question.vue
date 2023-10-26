<template>
  <view class="question-template">
    <view class="speed-progress">
      <view class="no">{{ currentIndex }}/{{ questionList.length }}</view>
      <view class="progress">
        <view class="percentage" :style="{ width: parseInt(currentIndex / questionList.length * 100) + '%' }">
        </view>
      </view>
      <view class="timer" id="timerBox"></view>
    </view>
    <scroll-view class="content-y" scroll-with-animation scroll-y :scroll-into-view="scrollInto">
      <view class="q-chat-block" v-for="(item, index) in questionList"
            v-show="index <= currentIndex">
        <view class="q-index">{{ (index + 1) + '/' + questionList.length }}</view>
        <view class="q-chat-left">
          <image :src="logo" class="q-user-pic q-right"></image>
          <view class="q-chatbox q-chatbox-left">
            <view class="type">{{ item.selectType === 0 ? '单选题' : '多选题' }}</view>
            <view class="title">{{ item.no }}、{{ item.title }}</view>
<!--            <view class="cue">请选择最贴合实际的情况：</view>-->
            <img v-if="item.img" class="item-img" :src="item.img" />
            <view class="warn-txt" v-show="checkNull">请至少选择一个选项</view>
            <view class="answer-box">
              <view class="item" v-for="(option, index) in item.options" @tap="answerOptions(item, option)">
                <view class="check-box" :class="{
                        'radio': item.selectType === 0, 'check': item.selectType === 1,
                        'active': (item.answers || []).findIndex(i => { return option.id === i }) > -1
                    }"></view>
<!--                {{ indexArr[index] }}、{{ option.name }}-->
                {{ option.name }}
              </view>
            </view>
          </view>
        </view>
        <view class="q-chat-right" v-if="item.answers.length > 0">
          <view class="q-chatbox q-chatbox-right">{{ getAnswer(item) }}</view>
          <image :src="userInfo.avatar" class="q-user-pic q-left"></image>
        </view>
        <view class="bottom-block" :id="'main-' + index"/>
      </view>
      <view class="q-chat-block" v-if="currentIndex > 1 && lastIndex + 1 === questionList.length">
        <view class="q-chat-left">
          <image :src="logo" class="q-user-pic q-right"></image>
          <view class="q-chatbox q-chatbox-left">
            <view class="info-title">测评结束，请填写信息</view>
            <view class="info-desc">请务必选择<text style="color: #FF703F">真实性别和年龄</text>，这将决定本次测评的计算准确度</view>
            <view class="answer-box">
              <view class="item" style="justify-content: space-between;">
                <view class="item-title">性别</view>
                <radio-group @change="sexChange">
                  <radio class='item-radio' color="#FF703F" :checked="form.sex==='0'" value="0">男</radio>
                  <radio class='item-radio' color="#FF703F" :checked="form.sex==='1'" value="1">女</radio>
                </radio-group>
              </view>
              <view class="item" style="justify-content: space-between;">
                <view class="item-title">年龄</view>
                <picker @change="ageChange" :value="form.age" :range="ages">
                  <view class="uni-input">{{ form.age > 0 ? form.age + '岁' : '请选择年龄' }} ></view>
                </picker>
              </view>
              <view class="item" style="justify-content: space-between;">
                <view class="item-title">手机号</view>
                <input class="item-input" v-model="form.mobile" type="number" maxlength="11" placeholder="请输入手机号"/>
              </view>
            </view>
          </view>
        </view>
      </view>
      <view class="btn next-btn" @tap="submitEvent" v-if="order.gaugeStatus === 2 && currentIndex > 1 && lastIndex + 1 === questionList.length">提交
      </view>
    </scroll-view>
    <message-com :message="confirmMessage" v-if="showMessage"></message-com>
    <evaluation-tab-bar></evaluation-tab-bar>
  </view>
</template>
<script>
import messageCom from '@/components/evaluation/message'
import {callTimeLoad, clearTimeLoad} from '@/utils/time'
import utils from '@/utils/common'
import questionServer from '@/server/evaluation/question'

let app = getApp();

export default {
  components: {messageCom},
  data() {
    return {
      logo: app.globalData.logo,
      checkNull: false,
      indexArr: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
      lats: [],
      form: {
        orderId: null,
        mobile: null,
        sex: '0',
        age: 20
      },
      ages: [],
      questionList: [],
      addInterceptorList: ["navigateTo", "redirectTo", "reLaunch", "switchTab"],
      lastIndex: 0,
      currentIndex: 1,
      productId: 0,
      orderId: '',
      order: {},
      scrollInto: '',
      showMessage: false,
      confirmMessage: {
        title: '提示',
        subTitle: '是否从上次中断的位置继续答题？',
        cancelBtn: {
          text: '重做',
          callback: () => {
          }
        },
        submitBtn: {
          text: '继续答题',
          callback: () => {
          }
        },
      },
    }
  },
  async created() {
    this.ages = Array.from({ length: 200 }, (_, i) => i + 1);
    console.log(this.ages)
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }

    this.productId = utils.getParam(location.href, "productId");
    this.orderId = utils.getParam(location.href, "orderId");
    this.order = await questionServer.getOrder(this.orderId)
    this.questionList = await questionServer.getQuestionList(this.productId, this.orderId);
    // 问题维度处理
    this.lats = await questionServer.getLats(this.productId);
    if (this.lats.length > 0) {
      this.lats.forEach(a => {
        if (a.questionIds) {
          const arr = a.questionIds.split(",")
          this.questionList.filter(b => arr.includes(b.id + '')).forEach(c => c.lat = a.id)
        }
      })
    }

    this.getLast()

    if (this.order.gaugeStatus === 1) {
      const that = this
      this.form.age = that.order.age
      this.form.sex = that.order.sex
      this.form.mobile = that.order.mobile

      return uni.showModal({
        showCancel: false,
        confirmColor:'#FF703F',
        title: '提示',
        content: '测评已提交,可查看结果报告',
        success: function (res) {
          if (res.confirm) {
            uni.navigateTo({
              url: "/pages/evaluation/mResult?orderId=" + that.order.orderId,
            });
          }
        }
      });
    } else {
      this.addInterceptor()
    }

    if (this.currentIndex > 1) {
      this.confirmMessage.cancelBtn.callback = this.toFirstQuestion;
      this.confirmMessage.submitBtn.callback = this.toLastQuestion;
      this.showMessage = true;
    }
  },
  mounted() {
    if (this.lastIndex === 0) {
      callTimeLoad(document.getElementById("timerBox"), true);
    }
  },
  onHide: function () {
    console.log('onHide')
    this.rmInterceptor()
  },
  onUnload: function () {
    console.log('onUnload')
    this.rmInterceptor()
  },
  methods: {
    resetForm() {
      this.form = {
        orderId: null,
        mobile: null,
        sex: '0',
        age: 20
      }
    },
    ageChange(e) {
      this.form.age = e.detail.value + 1
    },
    sexChange(e) {
      this.form.sex = e.detail.value
    },
    /*
        页面跳转拦截器
      */
    addInterceptor() {
      this.addInterceptorList.forEach(item => {
        //遍历的方式,navigateTo,redirectTo,reLaunch,switchTab这4个路由方法添加拦截器
        let that = this
        uni.addInterceptor(item, {
          invoke(e) {
            // 调用前拦截
            /* 获取用户的token，自己在登录接口中保存的。 */
            // const token = uni.getStorageSync('userToken')
            // 获取要跳转的页面路径（url去掉"?"和"?"后的参数）
            that.showDailog()
            return false
          },
          fail(err) { // 失败回调拦截
            console.log(err);
            if (Debug) {
              uni.showModal({
                content: JSON.stringify(err),
                showCancel: false
              });
            }
          }
        })
      })
    },
    rmInterceptor() {
      this.addInterceptorList.forEach(item => {
        uni.removeInterceptor(item)
      })
    },
    showDailog() {
      this.confirmMessage = {
        title: '提示',
        subTitle: `你已完成${this.lastIndex + 1}题，确认要退出吗？`,
        cancelBtn: {
          text: '狠心退出',
          callback: async () => {
            this.rmInterceptor()
            uni.navigateTo({
              url: `/pages/evaluation/index`,
            });
          }
        },
        submitBtn: {
          text: '继续答题',
          callback: () => {
            this.scrollTo()
            this.showMessage = false;
          }
        },
      }
      this.showMessage = true;
    },
    scrollTo() {
      setTimeout(() => {
        this.scrollInto = 'main-' + this.lastIndex
        // this.scrollInto = 'bottom'
      })
    },
    getLast() {
      this.lastIndex = this.questionList.findLastIndex(a => a.answers.length > 0)
      this.currentIndex = Math.min(this.lastIndex + 1, this.questionList.length)
      // console.log(this.lastIndex)
      // console.log(this.currentIndex)
    },
    //重新答题，把之前的答案清空
    toFirstQuestion() {
      this.showMessage = false
      this.currentIndex = 0
      this.lastIndex = -1
      this.questionList.forEach((question, index) => {
        question.answers = [];
      });
      callTimeLoad(document.getElementById("timerBox"), true);
    },
    //继续答题，跳转到最后一题
    toLastQuestion() {
      this.showMessage = false;
      this.currentIndex = Math.min(this.lastIndex + 1, this.questionList.length);
      callTimeLoad(document.getElementById("timerBox"), true);
      this.scrollTo()
    },
    getAnswer(item) {
      if (item.answers.length > 0) {
        const idx = []
        item.options.forEach((a, i) => {
          if (item.answers.includes(a.id)) {
            idx.push(this.indexArr[i])
          }
        })
        return idx.join(',')
      }
    },
    //答题
    answerOptions(item, option) {
      if (this.order.gaugeStatus === 1) {
        return
      }
      let index = (item.answers || []).findIndex(i => option.id === i);
      if (index > -1 && item.answers.length > 1) {
        item.answers.splice(index, 1);
      } else {
        if (item.selectType === 0) {
          item.answers = [option.id];
        } else {
          item.answers = [...(item.answers || []), ...[option.id]];
        }
      }
      this.toNext(item)
    },
    //提交
    async submitEvent() {
      if (!this.form.age) {
        return uni.showToast({
          icon: 'none',
          title: '请选择年龄',
        })
      }

      const phoneRegex = /^1[3-9]\d{9}$/
      if (!this.form.mobile || !phoneRegex.test(this.form.mobile)) {
        return uni.showToast({
          icon: 'none',
          title: '请输入正确的手机号',
        })
      }

      // 已答题完成，移除页面跳转拦截器
      this.rmInterceptor()
      this.form.orderId = this.orderId
      let result = await questionServer.setResult(this.form);
      if (result.code === 200) {
        this.resetForm()
        clearTimeLoad();
        uni.navigateTo({
          url: "/pages/evaluation/mResult?orderId=" + this.order.orderId,
        });
      }
    },
    //下一题
    async toNext(item) {
      // if (this.lastIndex + 1 === this.questionList.length) {
      //   console.log('aaaa')
      //   console.log(this.lastIndex)
      //   console.log(this.questionList.length)
      //   return;
      // }

      this.checkNull = item.answers.length === 0;
      const data = {
        gaugeId: this.productId,
        questionsId: item.id,
        questionsLat: item.lat,
        questionsOptionsIdList: item.answers,
        orderId: this.orderId,
      }

      let res = await questionServer.setAnswer(data);
      if (res.code === 200) {
        this.getLast()
        this.scrollTo()
      }
    }
  }
}
</script>
<style lang="scss">
page {
  background-color: #F8F8F8;
  position: relative;

  .speed-progress {
    width: 100%;
    position: fixed;
    top: 0;
    z-index: 99;
    height: 88upx;
    display: flex;
    flex-direction: row;
    background-color: #fff;
    align-items: center;
    justify-content: space-between;
    padding: 22upx 32upx;
    box-sizing: border-box;

    .no {
      height: 45upx;
      font-size: 32upx;
      font-weight: 400;
      color: #333333;
      line-height: 45upx;
    }

    .progress {
      width: 400upx;
      height: 18upx;
      background: #F2F2F2;
      border-radius: 12upx;
      margin: 0 35upx;

      .percentage {
        height: 18upx;
        background: #FF703F;
        border-radius: 12upx;
      }
    }

    .timer {
      line-height: 50upx;
      padding: 0 24upx;
      height: 50upx;
      background: #FFF3F3;
      border-radius: 25upx;
      font-size: 32upx;
      font-weight: 400;
      color: #FF2020;
    }
  }

  .type {
    font-size: 28upx;
    font-weight: 400;
    color: #AAAAAA;
    line-height: 44upx;
    margin-bottom: 24upx;
  }

  .title {
    font-size: 30upx;
    font-weight: 400;
    color: #333333;
    line-height: 50upx;
    margin-bottom: 16upx;
  }

  .cue {
    font-size: 28upx;
    font-weight: 400;
    color: #AAAAAA;
    line-height: 44upx;
    margin-bottom: 28upx;
  }

  .item-img {
    //width: 339upx;
    //height: 168upx;
    max-width: 100%!important;
    height: auto!important;
    margin: 0 auto;
  }

  .warn-txt {
    font-size: 28upx;
    font-weight: 400;
    color: #FF2020;
    line-height: 44upx;
    margin-top: -12upx;
    margin-bottom: 16upx;
  }

  .answer-box {
    font-size: 28upx;
    font-weight: 400;
    color: #333333;
    line-height: 44upx;
    margin-top: 10upx;

    .item {
      margin-bottom: 24upx;
      display: flex;
      flex-direction: row;
      align-items: center;

      .item-title {
        min-width: calc(4em + 15px);
        text-align: justify;
        padding-right: 16px;
        font-size: 16px;
        position: relative;
        //height: 32px;
        //line-height: 32px;
      }

      .item-input {
        text-align: right;
      }

      .item-radio {
        margin-right: 20upx;
        //background: #FF703F;
      }

      .check-box {
        width: 32upx;
        height: 32upx;
        margin-right: 20upx;
        background-size: 100% 100%;

        &.radio {
          background-image: url('/static/icon/radio.png');

          &.active {
            background-image: url('/static/icon/radio-active.png');
          }
        }

        &.check {
          background-image: url('/static/icon/check.png');

          &.active {
            background-image: url('/static/icon/check-active.png');
          }
        }

      }
    }
  }

  .btn {
    margin: 32upx auto;
    width: 590upx;
    height: 88upx;
    border-radius: 44upx;
    line-height: 88upx;
    text-align: center;
    font-weight: 600;
    font-size: 32upx;
    //position: absolute;
    //bottom: 56upx;

    &.next-btn {
      background: #FF703F;
      box-shadow: 0px 8px 32px 0px rgba(255, 112, 63, 0.3);
      color: #FFFFFF;
    }
  }

  .content-y {
    margin-top: 100upx;
    //padding-bottom: 100upx;
    height: 1500upx;
    padding-bottom: calc(constant(safe-area-inset-bottom) + 50px); /* 兼容 iOS 设备 */
    padding-bottom: calc(env(safe-area-inset-bottom) + 50px); /* 兼容 iPhone X 及以上设备 */
  }

  .bottom-block {
    width: 100%;
    height: 20upx;
  }

  .q-chat-block {
    margin-top: 30upx;
    margin-bottom: 30upx;
    position: relative;

    .q-index {
      margin: 0 auto;
      width: 150upx;
      background: #F2F2F2;
      color: #B2B2B2;
      text-align: center;
      border-radius: 40upx;
      padding: 10upx;
    }

    .q-chat-left {
      display: flex;
      align-items: flex-start;
      justify-content: flex-start;
      padding-top: 30upx;
      margin-left: 20upx;
      //width: 680upx;
    }

    .q-chat-right {
      display: flex;
      justify-content: flex-end;
      align-items: flex-start;
      padding-top: 30upx;
      margin-right: 20upx;
    }

    .q-user-pic {
      width: 80upx;
      height: 80upx;
      flex-shrink: 0;
      border-radius: 50%;
      display: block;
    }

    .q-left {
      margin-left: 26upx;
    }

    .q-right {
      margin-right: 26upx;
    }

    .q-chatbox {
      border-radius: 10upx;
      position: relative;
      padding: 20upx 22upx;
      font-size: 32upx;
      color: #333;
      word-break: break-all;
      word-wrap: break-word;
    }

    .q-chatbox::before {
      content: '';
      position: absolute;
      width: 0;
      height: 0;
      top: 20upx;
      border: 16upx solid;
    }

    .q-chatbox-left {
      background: #fff;
      border: 1upx solid #fff;
      display: inline-block;
      width: 530upx;
    }
    
    .info-title {
      text-align: center;
      font-size: 28upx;
      font-weight: 500;
      color: #333333;
      line-height: 40upx;
      margin: 30upx auto;
    }

    .info-desc{
      font-size: 26rpx;
      font-weight: 400;
      color: #777777;
      margin-bottom: 32upx;
    }

    .q-chatbox-left::before {
      right: 100%;
      border-color: transparent #fff transparent transparent;
    }

    .q-chatbox-right {
      border: 1upx solid #ffe4db;
      background: #FFF3F3;
      color: #FF2020;
    }

    .q-chatbox-right::before {
      left: 100%;
      border-color: transparent transparent transparent #FFF3F3;
    }

  }
}
</style>