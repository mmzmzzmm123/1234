<template>
  <view class="range" :style="{ marginTop: mTop }">
    <text>{{ down }}</text>
    <view class="range-pro" :style="{ background: lineBg, width: lineWidth, height: lintHeight }">
      <view class="range-progress" :style="{  background: proBg, width: Math.min(percentage, 100) + '%', height: lintHeight }"></view>
    </view>
    <text>{{ high }}</text>
  </view>
</template>
<script>
export default {
  props: {
    percentage: {
      type: Number,
      default: 0,
    },
    lineWidth: {
      type: String,
      default: '518upx',
    },
    lintHeight: {
      type: String,
      default: '24upx',
    },
    mTop: {
      type: String,
      default: '32upx',
    },
    high: {
      type: String,
      default: '高',
    },
    down: {
      type: String,
      default: '低',
    },
    lineBg: {
      type: String,
      default: '#FFE4DB',
    },
    proBg: {
      type: String,
      default: '#FF8960',
    }
  }
}
</script>
<style lang="scss">
.range {
  width: 750upx;
  height: 40upx;
  flex-direction: row;
  display: flex;
  align-items: center;
  justify-content: center;
  color: rgba(119, 119, 119, 1);
  font-size: 28upx;

  .range-pro {
    display: flex;
    flex-direction: column;
    margin: 0 24upx;
    background: #FFE4DB;
    border-radius: 16upx;
  }

  .range-progress {
    background-color: rgba(255, 112, 63, 1.000000);
    border-radius: 16upx;
    width: 66upx;
    display: flex;
    flex-direction: column;
  }

  .bg1 {
    background: #FFE4DB;
  }

  .prog1 {
    background: #FF8960;
  }

  .bg2 {
    background: #D4E3FF;
  }

  .prog2 {
    background: #7596DB;
  }

  .bg3 {
    background: #FFEDB2;
  }

  .prog3 {
    background: #F8CF67;
  }

  .bg4 {
    background: #C9E8C9;
  }

  .prog4 {
    background: #96D18D;
  }
}
</style>