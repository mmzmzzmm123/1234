<template>
    <view class="consultant-list-box">
        <view class="consultant-item" v-if="consultantList.length > 0" v-for="consultant in consultantList" :style="consultant.indexQualification ? { background: 'url(/static/consult/bg.png) 100% no-repeat', backgroundSize: '100% 100%'} : {}">
          <view class="header-icon" v-if="consultant.indexQualification">
            <image src="/static/consult/crown.png" class="header-icon-img"/>
            <text class="header-icon-text">{{ consultant.indexQualification }}</text>
          </view>
          <view class="upper-box">
            <view class="img-box" @tap="toConsultant(consultant)">
                <image :src="consultant.avatar" />
                <view v-if="consultant.buy > 0" class="img-tab">今日可约</view>
            </view>
            <view class="txt-box">
                <view class="title" @tap="toConsultant(consultant)">
                  <view class="name">{{ consultant.userName }}</view>
                </view>
                <view @tap="toConsultant(consultant)" class="introduce txt-overflow txt-overflow-line2">{{ consultant.info }}</view>
                <view @tap="toConsultant(consultant)" class="title">
                  <view v-for="tag in consultant.tabs.split(',')" class="tag-list">
                    <view class="tag-item">{{ tag }}</view>
                  </view>
                </view>
            </view>
          </view>            
            
            
          <view class="bottom-box">
            <view class="consult-button" @tap="toConsultant(consultant)">{{ consultant.price ? '¥' + consultant.price.toFixed(2) : '咨询TA' }}</view>
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
          this.$emit('toConsultant', consultant.id)
        }
    }
}
</script>
<style lang="scss" scoped>
@import "@/style/common.scss";
.consultant-list-box {
  .consultant-item {
      position: relative;
      background: #ffffff;
      border-radius: 12upx;
      margin-bottom: 16upx;
      padding: 24upx;
      .header-icon {
        position: absolute;
        top: 16upx;
        left: 420upx;
        height: 20upx;
        line-height: 20upx;
        .header-icon-img {
          width: 21upx;
          height: 20upx;
        }
        .header-icon-text {
          margin-left: 10upx;
          font-size: 22upx;
          font-weight: 500;
          color: #CA7C00;
        }
      }
      .upper-box {
        display: flex;
        .img-box {
            position: relative;
            //width: 162upx;
            //height: 212upx;
            image {
                width: 162upx;
                height: 212upx;
                border-radius: 10%;
            }
          .img-tab {
            text-align: center;
            position: absolute;
            border-radius: 0 0 12upx 12upx;
            bottom: 0;
            color: #fff;
            width: 162upx;
            height: 48upx;
            line-height: 48upx;
            font-size: 24upx;
            background: rgba(0,0,0,0.3);
            backdrop-filter: blur(6px);
          }
        }
        .txt-box {
          width: 450upx;
          height: 202upx;
          display: flex;
          flex-direction: column;
          margin-left: 24upx;
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
          }  
          .introduce {
            font-size: 26upx;
            font-weight: 400;
            color: #777777;
            margin-bottom: 10upx;
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
      }
      
      .bottom-box {
        position: absolute;
        bottom: 16upx;
        right: 16upx;
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