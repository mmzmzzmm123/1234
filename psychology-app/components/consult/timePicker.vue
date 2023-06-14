<!-- 咨询预约时间选择组件 -->
<template>
  <view class="timePicker"> 
    <view class="header">
      <view class="title">可约咨询时间</view>
      <view class="close"></view>
    </view>
    <view class="content">
      <view class="item">
        <view id="left">
          <view class="date"></view>
          <view class="day"></view>
        </view>
        <view id="right">
          <view class="full" v-if="false">已满</view>
          <view class="time-list">
            <view class="time-item"></view>
          </view>
        </view>
      </view>
      
    </view>
    <view class="footer">
      <view class="cancel">返回</view>
      <view class="confirm">确定</view>
    </view>
  </view>
</template>

<script>
export default {
  name: 'TimePicker',
  props: ['consultantId'],
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

<style lang="scss" scoped>
.timePicker {
  
}
</style>