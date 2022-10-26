<template>
    <view class="question-template">
        <view class="speed-progress">
            <view class="no">{{currentIndex}}/{{questionList.length}}</view>
            <view class="progress">
                <view class="percentage" :style="{width:parseInt(currentIndex/questionList.length*100)+'%'}"></view>
            </view>
            <view class="timer" id="timerBox"></view>
        </view>
        <view class="question-box">
            <view class="type">{{currentQuestion.type==1?'单选题':'多选题'}}</view>
            <view class="title">{{currentIndex}}、{{currentQuestion.title}}</view>
            <view class="cue">请选择最贴合实际的情况：</view>
            <view class="warn-txt" v-show="checkNull">请至少选择一个选项</view>
            <view class="answer-box">
                <view class="item" v-for="(option,index) in currentQuestion.options"
                    @tap="currentQuestion.answers=option.id">
                    <view class="check-box" :class="{'radio':currentQuestion.selectType==0,'check':currentQuestion.selectType==1,
                    'active':currentQuestion.answers==option.id}
                    "></view>
                    {{indexArr[index]}}、{{option.name}}
                </view>
            </view>
            <view class="btn prev-btn" @tap="toPrev" v-show="currentIndex>1">上一题</view>
            <view class="btn next-btn" @tap="toNext" v-show="currentIndex<questionList.length">下一题</view>
            <view class="btn next-btn" @tap="submitEvent" v-show="currentIndex==questionList.length">提交</view>
        </view>
        <view class="footer">
            @XXXXX 提供支持
        </view>
    </view>
</template>
<script>
import { callTimeLoad } from '../../utils/time'
import utils from '../../utils/common'
import questionServer from '@/server/question'
export default {
    data() {
        return {
            checkNull: false,
            indexArr: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'],
            questionList: [],
            currentIndex: 1,
            currentQuestion: {},
            currentAnswer: null,
            productId: 0
        }
    },
    async created() {
        this.productId = utils.getParam(location.href, "productId");
        this.questionList = await questionServer.getQuestionList(this.productId);
        this.currentQuestion = this.questionList[this.currentIndex];
    },
    mounted() {
        callTimeLoad(document.getElementById("timerBox"), true);
    },
    methods: {
        //提交
        submitEvent() {
            uni.navigateTo({
                url: "/pages/result/index?productId=" + this.productId,
            });
        },
        //上一题
        toPrev() {
            this.currentIndex--;
            this.currentQuestion = this.questionList[this.currentIndex];
        },
        //下一题
        toNext() {
            if (this.currentIndex == this.questionList.length) {
                return;
            }
            if (!this.currentQuestion.answers) {
                this.checkNull = true; return;
            }
            this.currentIndex++;
            this.currentQuestion = this.questionList[this.currentIndex];
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