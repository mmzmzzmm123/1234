<template>
  <uni-popup ref="selectTime" type="bottom" :is-mask-click="false">
    <view class="time-page">
      <view class="time-list">
        <view class="time-header">
          <text class="time-header-title">可约咨询时间</text>
          <uni-icons type="closeempty" size="24" class="time-close" @tap="close"/>
        </view>
        <view class="time-content-main">
          <view class="time-content" v-for="a in workList">
            <view class="time-content-day" :class="{ selected: a.flag }">
              <text class="time-content-week">{{ a.week }}</text>
              <text class="time-content-date">{{ a.day }}</text>
            </view>
            <view v-if="a.child.length === 0" class="time-content-list">
              <view>
                <view class="time-item-full-tag">
                  <text class="time-item-full-tag-text">已满</text>
                </view>
              </view>
            </view>
            <view v-if="a.child.length > 0" class="time-content-list">
              <view v-for="i in a.child">
                <view class="time-item-tag" :class="{ selected: i.id === workId }" @tap="selectDay(i.id, a.day)">
                  <text class="time-item-tag-text">{{ i.s }}</text>
                  <view class="time-item-tag-block"></view>
                  <text class="time-item-tag-text2">{{ i.e }}</text>
                </view>
              </view>
            </view>
          </view>
        </view>
      </view>
      <view class="tab-bar-wrap">
        <view class="box_btn">
          <button class="button_1" @tap="cancel">
            <text class="text_70">取消</text>
          </button>
          <button class="button_2" @tap="ok">
            <text class="text_71">确定</text>
          </button>
        </view>
      </view>
    </view>
  </uni-popup>
</template>
<script>

export default {
  props: ["workList","workId"],
  data() {
    return {
      userInfo: {},
    };
  },
  methods: {
    selectDay(id, day) {
      this.$emit("selectDay", id, day);
    },
    open() {
      this.$refs.selectTime.open('bottom')
    },
    close() {
      this.$refs.selectTime.close()
    },
    ok() {
      this.$emit('doOk')
      this.close()
    },
    cancel() {
      this.$emit('cancel')
      this.close()
    }
  }
};
</script>
<style lang="scss" scoped>
.time-page {
  overflow-y: auto;
  position: relative;
  background-color: #FFFFFF;
  padding-bottom: 138upx;
  max-height: 1000upx;

  .time-list {
    position: relative;
    display: flex;
    flex-direction: column;
  }
  .time-header {
    height: 72upx;
    width: 100%;
    line-height: 72upx;
    display: flex;
    justify-content: center;
    position: fixed;
    background: #FFFFFF;
  }
  .time-header-title {
    color: rgba(51,51,51,1);
    font-size: 36upx;
    font-weight: 500;
  }
  .time-close {
    width: 40upx;
    height: 40upx;
    position: absolute;
    right: 20upx;
  }
  .time-content-main {
    margin-top: 80upx;
    .time-content {
      display: flex;
      margin-left: 24upx;
    }
    .time-content-day {
      width: 44upx;
      height: 78upx;
      margin: 10upx;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      margin-right: 58upx;
      &.selected {
        .time-content-week,
        .time-content-date {
          color: #FF703F;
        }
      }
      .time-content-week {
        width: 60upx;
        color: rgba(51,51,51,1);
        font-size: 30upx;
        font-weight: 600;
        margin-left: 4upx;
      }
      .time-content-date {
        width: 76upx;
        color: rgba(119,119,119,1);
        font-size: 24upx;
        margin-top: 2upx;
      }
    }
    .time-content-list {
      display: flex;
      flex-wrap: wrap;
    }

    .time-item-full-tag {
      margin: 10rpx;
      background-color: rgba(248,248,248,1.000000);
      border-radius: 8rpx;
      height: 96rpx;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 96rpx;

      .time-item-full-tag-text {
        color: rgba(119,119,119,1);
        font-size: 26rpx;
      }
    }

    .time-item-tag {
      border-radius: 8rpx;
      width: 96rpx;
      height: 96rpx;
      border: 1px solid rgba(119,119,119,1);
      margin: 10rpx;
      display: flex;
      flex-direction: column;

      .time-item-tag-text {
        color: rgba(51,51,51,1);
        font-size: 26rpx;
        margin: 8rpx 0 0 16rpx;
      }
      .time-item-tag-block {
        background-color: rgba(51,51,51,1.000000);
        width: 12rpx;
        height: 2rpx;
        margin: 1rpx 0 0 42rpx;
      }
      .time-item-tag-text2 {
        color: rgba(51,51,51,1);
        font-size: 26rpx;
        margin: 1rpx 0 8rpx 16rpx;
      }
      &.selected {
        background-color: rgba(255,112,63,1.000000);
        color: #FFFFFF;
        border: none;
        .time-item-tag-text,
        .time-item-tag-text2 {
          color: #FFFFFF;
        }
        .time-item-tag-block {
          background-color: #FFF;
        }
      }
    }
  }
  .tab-bar-wrap {
    .box_btn {
      position: fixed;
      bottom: 0;
      background-color: #FFFFFF;
      width: 100%;
      height: 128upx;
      flex-direction: row;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .button_1 {
      border-radius: 40upx;
      height: 80upx;
      line-height: 80upx;
      border: 1rpx solid #777777;
      background: #FFFFFF;
      color: rgba(51,51,51,1);
      font-size: 28upx;
      text-align: center;
      width: 339upx;
    }
    .button_2 {
      background-color: rgba(255,112,63,1.000000);
      border-radius: 40upx;
      height: 80upx;
      line-height: 80upx;
      width: 339upx;
      color: rgba(255,255,255,1);
      font-size: 28upx;
    }
  }
}
</style>
