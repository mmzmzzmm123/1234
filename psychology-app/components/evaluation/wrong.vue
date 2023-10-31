<template>
  <view class="main">
    <view class="header">
      <view class="header-text">错题分析</view>
    </view>
    <view class="list" v-if="list.length > 0">
      <view class="item" v-for="item in list">
        <view class="title">{{ item.no }}、{{ item.title }}</view>
        <img class="img" v-if="item.img" :src="item.img" />
        <view v-for="p in item.options" class="option" :class="{ active: item.answerTitle.includes(p.name) }">
          {{ p.name }}
        </view>
        <view class="answer">你的答案：{{ item.answerTitle.join(',') }}</view>
        <view class="answer">正确答案：{{ item.referenceAnswerTitle.join(',') }}</view>
        <view class="result">{{ item.remark }}</view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "Wrong",
  props: {
    list: {
      type: Array,
      default: [],
    }
  },
  data() {
    return {
    }
  },
  created() {
  },
  methods: {

  }
}
</script>

<style lang="scss">
.main {
  box-shadow: 0px 4px 16px 0px rgba(255,112,63,0.160000);
  background-color: rgba(255,255,255,1.000000);
  border-radius: 20upx;
  position: relative;
  width: 686upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 32upx;
  padding-bottom: 32upx;
  .text-red {
    color: #ff703f;
  }
  .header {
    border-radius: 20upx;
    border: 2px solid #ff703f;
    margin-top: 40upx;
    padding: 16upx 52upx;
    text-align: center;
  }

  .header-text {
    height: 40upx;
    color: rgba(255, 112, 63, 1);
    font-size: 36upx;
    font-family: PingFangSC-Semibold;
    font-weight: 600;
    border-bottom: 17upx solid #ffe4db;
  }
  .list {
    margin-top: 46upx;
    .item {
      width: 622upx;
      margin: 32upx;
      .title {
        line-height: 48upx;
        color: #464646;
        letter-spacing: 1upx;
        margin-bottom: 10upx;
      }
      .img {
        max-width: 100%!important;
        height: auto!important;
        margin: 10upx auto;
      }
      .option {
        width: 100%;
        height: 76upx;
        line-height: 76upx;
        border: 2upx solid #e9e9e9;
        border-radius: 8upx;
        margin-bottom: 32upx;
        color: #464646;
        letter-spacing: 1upx;
        text-align: center;
        &.active {
          background: #FF703F;
          color: #FFF;
        }
      }
      .answer {
        width: 100%;
        padding: 6upx 0;
        line-height: 36upx;
        margin-bottom: 10upx;
      }
      .result {
        padding: 36upx;
        background: #fff0eb;
        border-radius: 8upx;
        font-size: 24upx;
        line-height: 72upx;
        color: #464646;
      }
    }
  }
}
</style>