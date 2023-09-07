<template>
  <view class="countdown" :style="{fontSize: size+'upx', color: color, fontWeight: fontWeight}" v-if="seconds > 0">
    <text>{{ formattedTime }}</text>
  </view>
</template>

<script>
export default {
  props: {
    time: {
      type: Number,
      default: 0,
    },
    size: {
      type: Number,
      default: 24,
    },
    color: {
      type: String,
      default: '#FF703F',
    },
    fontWeight: {
      type: String,
      default: 'normal',
    }
  },
  data() {
    return {
      seconds: 0,
      intervalId: null,
      formattedTime: '',
    };
  },
  created() {
    this.seconds = this.time
    this.initCountdown();
    this.startCountdown();
  },
  methods: {
    initCountdown() {
      if (this.seconds > 0) {
        this.seconds--;
        this.formattedTime = this.formatTime(this.seconds);
      } else {
        clearInterval(this.intervalId);
      }
    },
    startCountdown() {
      const intervalId = setInterval(() => {
        this.initCountdown()
      }, 1000);
      this.intervalId = intervalId;
    },
    formatTime(time) {
      const hours = Math.floor(time / 3600);
      const minutes = hours === 0 ? Math.floor(time / 60) : Math.floor((time - hours * 3600) / 60);
      const seconds = Math.floor(time % 60);
      return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
    },
  }
};
</script>

<style lang="scss" scoped>
.countdown {
  font-family: PingFangSC-Medium;
}
</style>