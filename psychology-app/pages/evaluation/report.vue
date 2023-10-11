<template>
  <view class="report-list">
    <view class="item" v-for="item in reportList">
      <view class="title">{{ item.gaugeTitle }}</view>
      <view class="date">完成时间：{{ item.createTime }}</view>
      <view class="btn" @tap="toResult(item)">查看报告</view>
    </view>
    <no-data v-if="reportList.length == 0"></no-data>
    <view class="footer" v-else>已经到底了</view>
    <evaluation-tab-bar></evaluation-tab-bar>
  </view>
</template>
<script>
  import utils from "@/utils/common";
  import noData from '@/components/evaluation/noData'
  import userServer from '@/server/evaluation/user'
  import questionServer from '@/server/evaluation/question'
  export default {
    components: {
      noData
    },
    data() {
      return {
        userInfo: {},
        reportList: [],
        productId: ''
      }
    },
    async created() {
      // this.userInfo = uni.getStorageSync('userInfo')
      this.userInfo = utils.getUserInfo()
      this.reportList = await userServer.getOrderList({
        userId: this.userInfo.userId,
        gaugeStatus: 1
      });
    },
    methods: {
      async toResult(item) {
        let result = await questionServer.setResult(item.id);
        if (result.code == 200) {
          if (item.gaugeType === 3) {
            return  uni.navigateTo({
              url: "/pages/evaluation/mbtiResult?orderId=" + item.orderId,
            });
          } else if (item.gaugeType === 4) {
            return  uni.navigateTo({
              url: "/pages/evaluation/sdsResult?orderId=" + item.orderId,
            });
          } else if (item.gaugeType === 5) {
            return  uni.navigateTo({
              url: "/pages/evaluation/sasResult?orderId=" + item.orderId,
            });
          }

          uni.setStorageSync("result", result.data);
          uni.navigateTo({
            url: "/pages/evaluation/result?productId=" + item.gaugeId,
          });
        }
      }
    },
  }
</script>
<style lang="scss">
  @import "@/style/common.scss";

  page {
    background-color: #f8f8f8;

    .report-list {
      padding-top: 32upx;

      .item {
        width: 686upx;
        height: 261upx;
        background: #FFFFFF;
        box-shadow: 0px 4upx 28upx 0px rgba(119, 119, 119, 0.06);
        border-radius: 16upx;
        padding: 32upx;
        box-sizing: border-box;
        margin: 0 auto 24upx;
        position: relative;

        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 40upx;
          width: 6upx;
          height: 32upx;
          background: #FF703F;
          border-radius: 3upx;
        }

        .title {
          font-size: 32upx;
          font-weight: 600;
          color: #333333;
          line-height: 45upx;
          margin-bottom: 16upx;
        }

        .date {
          font-size: 28upx;
          font-weight: 400;
          color: #AAAAAA;
          line-height: 40upx;
        }

        .btn {
          height: 96upx;
          line-height: 96upx;
          font-size: 32upx;
          font-weight: 400;
          color: #FF703F;
          text-align: center;
          margin-top: 32upx;
          border-top: 1px solid rgba(204, 204, 204, 0.5);
        }
      }
    }
  }
</style>
