<template>
    <view class="mask">
        <view class="msg-box" :class="{'has-sub-title':message.subTitle&&message.subTitle.length>0}">
            <view class="title">{{message.title}}</view>
            <view class="sub-title" v-if="message.subTitle&&message.subTitle.length>0">{{message.subTitle}}</view>
            <view class="btn-box">
                <view class="btn cancel" @tap="message.cancelBtn.callback&&message.cancelBtn.callback()">
                    {{message.cancelBtn.text}}</view>
                <view class="btn" @tap="message.submitBtn.callback&&message.submitBtn.callback()">
                    {{message.submitBtn.text}}</view>
            </view>
        </view>
    </view>
</template>
<script>
export default {
    props: {
        message: {
            type: Object,
            default: () => {
                return {
                    title: '',
                    subTitle: '',
                    submitBtn: {
                        text: '',
                        callback: () => { }
                    },
                    cancelBtn: {
                        text: '',
                        callback: () => { }
                    },
                }
            }
        }
    }
}
</script>
<style lang="scss">
.mask {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(51, 51, 51, 0.5);

    .msg-box {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        margin: auto;
        width: 560upx;
        background-color: #fff;
        border-radius: 26upx;
        height: 216upx;

        text-align: center;

        &.has-sub-title {
            height: 290upx;
        }

        .title {
            font-size: 34upx;
            font-weight: 600;
            color: #333333;
            line-height: 128upx;
        }

        .sub-title {
            font-size: 30upx;
            font-weight: 400;
            color: #333333;
            line-height: 42upx;
            margin-top: -8upx;
            margin-bottom: 40upx;
            ;
        }

        .btn-box {
            display: flex;
            flex-direction: row;
            height: 88upx;
            line-height: 88upx;
            border-top: 1px solid #ccc;

            .btn {
                flex: 1;
                font-size: 32upx;
                color: #FF703F;

                &:first-child {
                    color: #777;
                    border-right: 1px solid #CCC;
                }
            }
        }
    }

}
</style>