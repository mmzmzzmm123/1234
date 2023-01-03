<template>
  <div class="SwiperScroll-warp SwiperScroll-warp-reset" :style="{ height }">
    <div class="warp-content">
      <p
        v-show="!noMore"
        :class="progressClassName"
        :style="{
          transform: progressTransform,
        }"
      ></p>
      <p class="warp-tip">{{ loadingText }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: "down", // up
    },
  },
  data() {
    return {
      loadingText: "",
      outHeight: 0,
      maxHeight: 38,
      noMore: false,
    };
  },
  methods: {
    changeTranslate(translate) {
      translate = Math.abs(translate);
      if (this.type == "up" && this.noMore) {
        return;
      }
      if (this.outHeight < this.maxHeight || translate == 0) {
        this.outHeight = translate;
        if (this.type == "down") {
          this.loadingText = "拉动刷新";
        } else {
          this.loadingText = "加载更多";
        }
      } else {
        this.loadingText = "加载中";
      }
    },
    setNoMore(noMore) {
      this.noMore = noMore ? true : false;
      if (this.type == "up") {
        if (this.noMore) {
          this.outHeight = this.maxHeight;
          this.loadingText = "没有更多了";
        } else {
          this.outHeight = 0;
        }
      }
    },
    setText(v) {
      this.loadingText = v;
    },
  },
  components: {},
  created() {},
  mounted() {},
  computed: {
    height() {
      return this.outHeight + "px";
    },
    progressClassName() {
      return this.outHeight < this.maxHeight || this.noMore
        ? "warp-progress"
        : "warp-progress SwiperScroll-rotate";
    },
    progressTransform() {
      return "rotate(" + (this.outHeight / this.maxHeight) * 360 + "deg)";
    },
  },
  watch: {},
};
</script>

<style scoped lang="scss"></style>
