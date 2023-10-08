<template>
  <view class="main">
    <view class="header">
      <view class="header-bottom"></view>
      <view class="header-text">咨询师推荐</view>
    </view>
    <view class="list" v-if="consults.length > 0">
      <view class="item" v-for="item in consults">
        <view class="item-content" @tap="toConsult(item.id)">
          <image :src="item.img" class="img"/>
          <view class="info">
            <text class="name">{{ item.nickName }}</text>
            <view class="item-info">
              <text class="item-mode">{{ item.mode }}</text>
              <view class="item-work">
                <text>已有</text>
                <text class="text-red">{{ item.workNum }}人</text>
                <text>预约</text>
              </view>
            </view>
            <text class="item-desc txt-overflow">{{ item.info }}</text>
            <view class="tag-list">
              <view v-for="(tag, index) in item.tabs.split(',')"  v-if="index < 3" class="tag-item">
                {{ tag }}
              </view>
            </view>
          </view>

          <view class="item-bottom">
            <view class="gps">
              <image src="/static/consult/gps-black.png" class="gps-img"/>
              <text class="text-group_2">{{ item.province + '-' + item.city }}</text>
            </view>
            <view class="price">{{ item.newPrice ? '¥' + item.newPrice.toFixed(2) : item.price ? '¥' + item.price.toFixed(2) : '咨询TA' }}</view>
          </view>
        </view>

        <view class="item-zxWord">
          {{ item.zxWord }}
        </view>
      </view>
    </view>

    <view class="footer">
      *展示为部分咨询师，如需预约，请前往
      <text class="text-red" @click="goHome">心理咨询首页</text>
    </view>
  </view>
</template>

<script>
import indexServer from '@/server/consult/index'

export default {
  name: "Recommend",
  data() {
    return {
      queryData: {},
      consults: [],
    }
  },
  created() {
    this.getConsult()
  },
  methods: {
    async getConsult() {
      const res = await indexServer.getConsult(this.queryData, 1, 10)
      if (res.code === 200 && res.rows.length > 0) {
        this.consults = res.rows.splice(0, 2)
      }

      console.log(this.consults)
    },
    goHome() {
      uni.navigateTo({
        url: "/pages/consult/index",
      });
    },
    toConsult(id) {
      uni.navigateTo({
        url: "/pages/consult/consultant?id=" + id,
      });
    },
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
  text-align: center;
  .text-red {
    color: #ff703f;
  }
  .header {
    border-radius: 20upx;
    position: relative;
    width: 334upx;
    height: 90upx;
    border: 2px solid #ff703f;
    display: flex;
    flex-direction: column;
    margin-top: 40upx;
    justify-content: center;
    align-items: center;
    .header-bottom {
      position: absolute;
      width: 226upx;
      height: 17upx;
      top: 57upx;
      background: #FFE4DB;
    }
    .header-text {
      position: absolute;
      //left: 59upx;
      top: 16upx;
      //width: 216upx;
      height: 50upx;
      color: #ff703f;
      font-size: 36upx;
      font-family: PingFangSC-Semibold;
      font-weight: 600;
      text-align: center;
      line-height: 50upx;
    }
  }
  .list {
    margin-top: 46upx;
  }
  .item {
    width: 622upx;
    margin: 32upx;
    .item-content {
      height: 270upx;
      display: flex;
      flex-direction: column;
      flex-wrap: wrap;
      .img {
        width: 172upx;
        height: 264upx;
        border-radius: 12upx;
      }
      .info {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        margin-left: 32upx;
        .name {
          width: 90upx;
          height: 42upx;

          color: rgba(51,51,51,1);
          font-size: 30upx;
          font-family: PingFangSC-Semibold;
          font-weight: 600;
          text-align: left;
          white-space: nowrap;
          line-height: 42upx;
        }
        .item-info {
          width: 418upx;
          height: 30upx;
          margin-top: 12upx;
          flex-direction: row;
          display: flex;
          justify-content: space-between;
        }
        .item-mode {
          width: 198upx;
          height: 30upx;

          color: rgba(119,119,119,1);
          font-size: 22upx;
          font-family: PingFangSC-Regular;
          font-weight: normal;
          text-align: left;
          white-space: nowrap;
          line-height: 30upx;
        }
        .item-work {
          //width: 146upx;
          height: 30upx;
          font-family: PingFangSC-Regular;
          color: rgba(170,170,170,1.000000);
          font-size: 22upx;
          font-weight: normal;
          line-height: 30upx;
        }
        .item-desc {
          margin-top: 12upx;
          width: 418upx;
          color: rgba(119,119,119,1);
          font-size: 26upx;
          font-family: PingFangSC-Regular;
          text-align: left;
        }
        .tag-list {
          margin-top: 12upx;
          display: flex;
          .tag-item {
            background-color: rgba(255,112,63,0.100000);
            border-radius: 4upx;
            color: #ff703f;
            font-size: 22upx;
            font-family: PingFangSC-Regular;
            text-align: center;
            width: 120upx;
            height: 34upx;
            line-height: 34upx;
            margin-right: 16upx;
          }
        }
      }

      .item-bottom {
        width: 418upx;
        height: 50upx;
        margin-top: 35upx;
        flex-direction: row;
        display: flex;
        justify-content: space-between;
      }
      .gps {
        height: 30upx;
        line-height: 30upx;
        margin-top: 8upx;
        margin-left: 32upx;
        image {
          width: 16upx;
          height: 18upx;
        }
        text {
          margin-left: 6upx;
          font-size: 22upx;
          font-weight: 400;
          color: #AAAAAA;
        }
      }
      .price {
        border-radius: 25upx;
        height: 50upx;
        line-height: 50upx;
        border: 1px solid #ff703f;
        width: 120upx;
        color: #ff703f;
        font-size: 24upx;
        font-family: PingFangSC-Regular;
        text-align: center;
      }
    }
    .item-zxWord {
      border-radius: 12upx;
      margin-top: 24upx;
      padding: 16upx 25upx;
      text-align: left;
      font-size: 26upx;
      color: #777777;
      background-color: #fff0eb;
      font-family: PingFangSC-Regular;
    }
  }

  .footer {
    width: 622upx;
    height: 37upx;
    line-height: 37upx;
    color: #AAAAAA;
    font-size: 26upx;
    font-family: PingFangSC-Regular;
    font-weight: normal;
    text-align: left;
  }
}
</style>