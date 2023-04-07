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
        <view class="question-box">
            <view class="type">{{ currentQuestion.type == 1 ? '单选题' : '多选题' }}</view>
            <view class="title">{{ currentIndex }}、{{ currentQuestion.title }}</view>
            <view class="cue">请选择最贴合实际的情况：</view>
            <view class="warn-txt" v-show="checkNull">请至少选择一个选项</view>
            <view class="answer-box">
                <view class="item" v-for="(option, index) in currentQuestion.options" @tap="answerOptions(option)">
                    <view class="check-box" :class="{
                        'radio': currentQuestion.selectType == 0, 'check': currentQuestion.selectType == 1,
                        'active': (currentQuestion.answers || []).findIndex(i => { return option.id == i }) > -1
                    }"></view>
                    {{ indexArr[index] }}、{{ option.name }}
                </view>
            </view>
            <view class="btn prev-btn" @tap="toPrev" v-show="currentIndex > 1">上一题</view>
            <view class="btn next-btn" @tap="toNext" v-show="currentIndex < questionList.length">下一题</view>
            <view class="btn next-btn" @tap="submitEvent" v-show="currentIndex == questionList.length">提交</view>
        </view>
        <view class="footer">
            @XXXXX 提供支持
        </view>
        <message-com :message="confirmMessage" v-if="showMessage"></message-com>
        <evaluation-tab-bar></evaluation-tab-bar>
    </view>
</template>
<script>
import messageCom from '@/components/evaluation/message'
import { callTimeLoad, clearTimeLoad } from '@/utils/time'
import utils from '@/utils/common'
import questionServer from '@/server/evaluation/question'
export default {
    components: { messageCom },
    data() {
        return {
            checkNull: false,
            indexArr: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
            questionList: [],
            currentIndex: 1,
            currentQuestion: {},
            currentAnswer: null,
            productId: 0,
            orderId: '',
            lastIndex: 0,
            showMessage: false,
            confirmMessage: {
                title: '是否从上次中断的位置继续答题？',
                cancelBtn: {
                    text: '重做',
                    callback: () => { }
                },
                submitBtn: {
                    text: '继续答题',
                    callback: () => { }
                },
            },
        }
    },
    async created() {
        this.productId = utils.getParam(location.href, "productId");
        this.orderId = utils.getParam(location.href, "orderId");
        this.questionList = await questionServer.getQuestionList(this.productId, this.orderId);
        this.questionList.forEach((question, index) => {
            question.answers = question.options.filter(item => { return !!item.selectedFlag }).map(i => { return i.id })
            if (question.answers.length > 0) {//上次答到第几题
                this.lastIndex = index;
            }
        });
        if (this.lastIndex + 1 > 1) {
            this.confirmMessage.cancelBtn.callback = this.toFirstQuestion;
            this.confirmMessage.submitBtn.callback = this.toLastQuestion;
            this.showMessage = true;
            this.currentQuestion = this.questionList[this.currentIndex - 1];
        } else {
            this.currentQuestion = this.questionList[this.currentIndex - 1];
        }
    },
    mounted() {
        if (this.lastIndex == 1) {
            callTimeLoad(document.getElementById("timerBox"), true);
        }
        /*
          页面跳转拦截器
        */
        let list = ["navigateTo", "redirectTo", "reLaunch", "switchTab"];
        list.forEach(item => { 
         
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
    methods: {
      showDailog() {
          this.confirmMessage = {
              title: `你已完成${this.currentIndex}题，确认要退出吗？`,
              cancelBtn: {
                  text: '狠心退出',
                  callback: async () => {                    
                      uni.removeInterceptor("navigateTo");
                      uni.navigateTo({
                          url: `/pages/evaluation/index`,
                      });                    
                  }
              },
              submitBtn: {
                  text: '继续答题',
                  callback: () => {
                      this.showMessage = false;
                  }
              },
          }
          this.showMessage = true;
      },
        //重新答题，把之前的答案清空
        toFirstQuestion() {
            this.showMessage = false;
            this.currentIndex = 1;
            this.questionList.forEach((question, index) => {
                question.answers = [];
            });
            callTimeLoad(document.getElementById("timerBox"), true);
            this.currentQuestion = this.questionList[this.currentIndex - 1];
        },
        //继续答题，跳转到最后一题
        toLastQuestion() {
            this.showMessage = false;
            this.currentIndex = this.lastIndex + 2;
            callTimeLoad(document.getElementById("timerBox"), true);
            this.currentQuestion = this.questionList[this.currentIndex - 1];
        },
        //答题
        answerOptions(option) {
            let index = (this.currentQuestion.answers || []).findIndex(i => { return option.id == i });
            if (index > -1) {
                this.currentQuestion.answers.splice(index, 1);
            } else {
                if (this.currentQuestion.selectType == 0) {
                    this.currentQuestion.answers = [option.id];
                } else {
                    this.currentQuestion.answers = [...(this.currentQuestion.answers || []), ...[option.id]];
                }
            }
        },
        //提交
        async submitEvent() {
          // 已答题完成，移除页面跳转拦截器
          uni.removeInterceptor("navigateTo");
            let res = await questionServer.setAnswer(this.productId, this.currentQuestion.id, this.currentQuestion.answers, this.orderId);
            if (res.code == 200) {
                let result = await questionServer.setResult(this.orderId);
                if (result.code == 200) {
                    uni.setStorageSync("result", result.data);
                    clearTimeLoad();
                    uni.navigateTo({
                        url: "/pages/evaluation/result?productId=" + this.productId,
                    });
                }
            }
        },
        //上一题
        toPrev() {
            this.currentIndex--;
            this.currentQuestion = this.questionList[this.currentIndex - 1];
        },
        //下一题
        async toNext() {
            if (this.currentIndex == this.questionList.length) {
                return;
            }
            if (!this.currentQuestion.answers || this.currentQuestion.answers.length == 0) {
                this.checkNull = true; return;
            } else {
                this.checkNull = false;
            }
            let res = await questionServer.setAnswer(this.productId, this.currentQuestion.id, this.currentQuestion.answers, this.orderId);
            if (res.code == 200) {
                this.currentIndex++;
                this.currentQuestion = this.questionList[this.currentIndex - 1];
            }
        }
    }
}
</script>
<style lang="scss">
page {
    background-color: #F8F8F8;

    .speed-progress {
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

    .question-box {
        width: 686upx;
        background: #FFFFFF;
        border-radius: 16px;
        margin: 35upx auto;
        padding: 48upx;
        box-sizing: border-box;
        min-height: calc(100vh - 350upx);

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

        position: relative;

        .btn {
            width: 590upx;
            height: 88upx;
            border-radius: 12upx;
            line-height: 88upx;
            text-align: center;
            font-weight: 600;
            font-size: 32upx;
            position: absolute;
            bottom: 56upx;

            &.prev-btn {
                background: rgba(255, 112, 63, 0.08);
                color: #FF703F;
                bottom: 172upx;
            }

            &.next-btn {
                background: #FF703F;
                box-shadow: 0px 8px 32px 0px rgba(255, 112, 63, 0.3);
                color: #FFFFFF;
            }
        }

    }

    .footer {
        text-align: center;
        font-size: 24upx;
        font-weight: 400;
        color: #AAAAAA;
        line-height: 33upx;
        padding: 32upx 0;
    }
}
</style>