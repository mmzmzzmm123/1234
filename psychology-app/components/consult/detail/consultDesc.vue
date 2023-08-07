<template>
  <view>
    <view class="info-first">

      <view class="qualification">
        <text class="qualification-title">执业资格</text>
        <view class="qualification-val" v-for="item in info.qualification">{{ item }}</view>
      </view>

      <view class="experience" v-if="info.experiences && info.experiences.length > 0">
        <text class="experience-title">受训经历</text>
        <view class="experience-content">
          <view class="experience-list">
            <view class="experience-item" v-for="item in info.experiences">
              <view class="experience-item-icon"></view>
              <view class="experience-item-tiem">{{ item.time[0] + '-' + item.time[1] }}</view>
              <view class="experience-item-info">{{ item.info }}</view>
            </view>
          </view>
        </view>
      </view>
    </view>

    <view class="tabs" v-if="info.tabList && info.tabList.length > 0">
      <text class="tabs-title">擅长领域</text>
      <view v-for="p in info.tabList">
        <view class="tabs-name">
          {{ p.name }}
        </view>
        <text v-for="i in p.child" class="tabs-item">{{ i }}</text>
      </view>
    </view>

    <view class="text-wrapper">
      <text class="text-wrapper-title">咨询风格</text>
      <text class="text-wrapper-info">{{ info.zxStyle || '用客观和真诚来共情来访，用积极和热情关注来访的闪光点，尊重来访的感受客观中立的帮助来访找到问题。' }}</text>
    </view>

    <view class="zxWord">
      <text class="zxWord-title">咨询寄语</text>
      <text class="zxWord-info">{{ info.zxWord }}</text>
    </view>

    <view class="text-wrapper">
      <text class="text-wrapper-title">咨询须知</text>
      <text class="text-wrapper-info">{{ info.notice || '咨询中请勿擅自录音或者是摄像，做到有效的保护好来访的利益。' }}</text>
    </view>
  </view>
</template>
<script>
export default {
  props: ['info'],
}
</script>
<style lang="scss" scoped>
.info-first {
  display: flex;
  flex-direction: column;
  margin-top: 16upx;
}
.qualification {
  background-color: rgba(255,255,255,1.000000);
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  padding: 32upx;
  .qualification-title {
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 30upx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    text-align: left;
    margin-bottom: 32upx;
  }
  .qualification-val {
    font-size: 26upx;
    font-weight: 400;
    color: #333333;
  }
}

.zxWord {
  background-color: rgba(255,255,255,1.000000);
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  padding: 32upx;
  margin-top: 16upx;
}
.zxWord-title {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  text-align: left;
}
.zxWord-info {
  width: 702upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  margin-top: 24upx;
}
.experience {
  background-color: rgba(255,255,255,1.000000);
  position: relative;
  //display: flex;
  //flex-direction: column;
  //justify-content: flex-center;
  margin-top: 16upx;
  padding: 32upx;
}
.experience-title {
  width: 120upx;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
}
.experience-content {
  flex-direction: row;
  display: flex;
  justify-content: space-between;
  margin-top: 32upx;
}
.experience-list {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.experience-item {
  position: relative;
  height: 78upx;
  width: 100%;
  padding-left: 64upx;
  padding-bottom: 16upx;
}
.experience-item-icon {
  background-color: rgba(255,255,255,1.000000);
  border-radius: 100%;
  position: absolute;
  left: 20upx;
  top: 0upx;
  width: 20upx;
  height: 20upx;
  border: 2px solid rgba(255,112,63,1);
  display: block;
  z-index: 9;
  text-align: center;
}
.experience-item:not(:last-child)::after {
  content: "";
  display: block;
  position: absolute;
  left: 34upx;
  top: 0;
  z-index: 8;
  height: 100%;
  width: 1upx;
  background: linear-gradient(to bottom, #AAAAAA 0%, #AAAAAA 50%,transparent 50%);
  background-size: 2upx 12upx;
  background-repeat: repeat-y;
}
.experience-item-tiem {
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 24upx;
  font-family: PingFangSC-Regular;
  text-align: left;
}
.experience-item-info {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  margin-top: 8upx;
}
.tabs {
  background-color: rgba(255,255,255,1.000000);
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  margin-top: 16upx;
  padding: 16upx;
}
.tabs-title {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  margin: 16upx 0 0 24upx;
}
.tabs-name {
  background-color: rgba(255,112,63,0.100000);
  border-radius: 4upx;
  height: 34upx;
  width: 150upx;
  line-height: 34upx;
  margin: 32upx 0 0 24upx;
  text-align: center;
  color: rgba(255,112,63,1);
  font-size: 22upx;
  font-family: PingFangSC-Regular;
  margin: 16upx;
}
.tabs-item {
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  margin-top: 16upx;
  margin-left: 16upx;
}
.text-wrapper {
  background-color: rgba(255,255,255,1.000000);
  margin-top: 16upx;
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
}
.text-wrapper-title {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 30upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  margin: 32upx 0 0 24upx;
}
.text-wrapper-info {
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  margin: 32upx 0 32upx 24upx;
}
</style>