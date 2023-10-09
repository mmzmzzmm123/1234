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
            v-show="(currentIndex === 1 && index === 0) || (currentIndex > 1 && index < currentIndex)"
            :id="'main-' + index">
        <view class="q-index">{{ (index + 1) + '/' + questionList.length }}</view>
        <view class="q-chat-left">
          <image :src="logo" class="q-user-pic q-right"></image>
          <view class="q-chatbox q-chatbox-left">
            <view class="type">{{ item.selectType === 0 ? '单选题' : '多选题' }}</view>
            <view class="title">{{ item.no }}、{{ item.title }}</view>
            <view class="cue">请选择最贴合实际的情况：</view>
            <view class="warn-txt" v-show="checkNull">请至少选择一个选项</view>
            <view class="answer-box">
              <view class="item" v-for="(option, index) in item.options" @tap="answerOptions(item, option)">
                <view class="check-box" :class="{
                        'radio': item.selectType === 0, 'check': item.selectType === 1,
                        'active': (item.answers || []).findIndex(i => { return option.id === i }) > -1
                    }"></view>
                {{ indexArr[index] }}、{{ option.name }}
              </view>
            </view>
          </view>
        </view>
        <view class="q-chat-right" v-if="item.answers.length > 0">
          <view class="q-chatbox q-chatbox-right">{{ getAnswer(item) }}</view>
          <image :src="userInfo.avatar" class="q-user-pic q-left"></image>
        </view>
      </view>
      <view class="btn next-btn" @tap="submitEvent" v-if="currentIndex > 1 && lastIndex + 1 === questionList.length">提交
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
      questionList: [],
      addInterceptorList: ["navigateTo", "redirectTo", "reLaunch", "switchTab"],
      lastIndex: 0,
      currentIndex: 1,
      productId: 0,
      orderId: '',
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
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }

    this.productId = utils.getParam(location.href, "productId");
    this.orderId = utils.getParam(location.href, "orderId");
    this.questionList = await questionServer.getQuestionList(this.productId, this.orderId);
    this.getLast()

    if (this.currentIndex > 2) {
      this.confirmMessage.cancelBtn.callback = this.toFirstQuestion;
      this.confirmMessage.submitBtn.callback = this.toLastQuestion;
      this.showMessage = true;
    }
  },
  mounted() {
    if (this.lastIndex === 0) {
      callTimeLoad(document.getElementById("timerBox"), true);
    }
    this.addInterceptor()
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
      })
    },
    getLast(next = 1) {
      const index = this.questionList.findLastIndex(a => a.answers.length > 0)
      this.lastIndex = index !== -1 ? index + 1 : 0
      this.currentIndex = Math.min(this.lastIndex + 1, this.questionList.length)
      console.log(this.lastIndex)
      console.log(this.currentIndex)
    },
    //重新答题，把之前的答案清空
    toFirstQuestion() {
      this.showMessage = false
      this.currentIndex = 1
      this.lastIndex = 0
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
      setTimeout(() => {
        this.scrollInto = 'main-' + this.lastIndex
      })
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
      let index = (item.answers || []).findIndex(i => {
        return option.id === i
      });
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
      // 已答题完成，移除页面跳转拦截器
      this.rmInterceptor()
      const order = await questionServer.getOrder(this.orderId)
      let result = await questionServer.setResult(this.orderId);
      if (result.code === 200) {
        uni.setStorageSync("result", result.data);
        clearTimeLoad();
        if ([4, 5].includes(order.gaugeType)) {
          return  uni.navigateTo({
            url: "/pages/evaluation/sdsResult?orderId=" + order.orderId,
          });
        }
        uni.navigateTo({
          url: "/pages/evaluation/result?productId=" + this.productId,
        });
      }
    },
    //下一题
    async toNext(item) {
      if (this.lastIndex + 1 === this.questionList.length) {
        console.log('aaaa')
        console.log(this.lastIndex)
        console.log(this.questionList.length)
        return;
      }

      this.checkNull = item.answers.length === 0;

      let res = await questionServer.setAnswer(this.productId, item.id, item.answers, this.orderId);
      if (res.code === 200) {
        this.getLast(2)
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

    .item {
      margin-bottom: 24upx;
      display: flex;
      flex-direction: row;
      align-items: center;

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
    margin: 10upx auto;
    width: 590upx;
    height: 88upx;
    border-radius: 44upx;
    line-height: 88upx;
    text-align: center;
    font-weight: 600;
    font-size: 32upx;
    //position: absolute;
    bottom: 56upx;

    &.next-btn {
      background: #FF703F;
      box-shadow: 0px 8px 32px 0px rgba(255, 112, 63, 0.3);
      color: #FFFFFF;
    }
  }

  .content-y {
    margin-top: 100upx;
    padding-bottom: 100upx;
    height: 1500upx;
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