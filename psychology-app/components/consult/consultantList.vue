<template>
    <view class="consultant-list-box">
        <view class="consultant-item" v-if="consultantList.length > 0" v-for="consultant in consultantList">
          <view class="upper-box">
            <view class="img-box">
                <img :src="consultant.avatar" />
            </view>
            <view class="txt-box">
                <view class="title">
                  <view class="name">{{ consultant.userName }}</view>
                  <view v-for="tag in consultant.tabs.split(',')" class="tag-list">
                    <view class="tag-item">{{ tag }}</view>
                  </view>
                </view>
                <view class="introduce txt-overflow txt-overflow-line2">{{ consultant.info }}</view>
            </view>
          </view>            
            
            
          <view class="bottom-box">
            <view class="consult-button" @tap="toConsultant(consultant)">咨询TA</view>
          </view>            
        </view>
        <view class="footer" v-show="consultantList.length > 0">已经到底了</view>
    </view>
</template>
<script>
export default {
    props: ['consultantList'],
    data() {
        return {
        }
    },
    methods: {
        toConsultant(consultant) {
            uni.navigateTo({
                url: "/pages/consult/consultant?id=" + consultant.id,
            });
        }
    }
}
</script>
<style lang="scss" scoped>
@import "@/style/common.scss";
.consultant-list-box {
  .consultant-item {
      width: calc(100% -48upx);
      padding: 24upx;
      background: #ffffff;
      border-radius: 12upx;
      margin-bottom: 16upx;
      .upper-box {
        display: flex;
        align-items: center;
        .img-box {
            width: 172upx;
            height: 172upx;
            img {
              border-radius: 50%;
            }
        }
        .txt-box {
          width: calc(100% - 198upx);
          margin-left: 32upx;
          .title {
            display: flex;
            align-items: center;
            margin-bottom: 8upx;
            .name {
              font-size: 30upx;
              font-family: PingFangSC-Semibold, PingFang SC;
              font-weight: 600;
              color: #333333;
              line-height: 42upx;
              margin-right: 14upx;
              
            }
            .tag-list {
              .tag-item {
                background: rgba(255,112,63,0.1);
                border-radius: 4upx;
                margin-right: 8upx;
                font-size: 22upx;
                font-weight: 400;
                color: #FF703F;
                line-height: 30upx;
                padding: 2upx 10upx;
              }
            }
          }  
          .introduce {
            font-size: 26upx;
            font-family: PingFangSC-Regular, PingFang SC;
            font-weight: 400;
            color: #777777;
            line-height: 37upx;
          }
        }
      }
      
      .bottom-box {
        display: flex;
        flex-direction: row-reverse;
        .consult-button {
          width: fit-content;
          height: fit-content;
          border-radius: 25upx;
          border: 1upx solid #FF703F;
          padding: 8upx 20upx;
          font-size: 24upx;
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          color: #FF703F;
          text-align: center;
        }
      }
  
      
  }
}

</style>