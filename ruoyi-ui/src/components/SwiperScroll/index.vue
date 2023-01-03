<template>
  <!-- 通过ref可以获取到dom对象 -->
  <swiper class="SwiperScroll" :options="swiperOption" ref="swiper">
    <SwiperSlide ref="SwiperSlide">
      <div
        @mousewheel="loadMore"
        :class="{
          scrollbarContent: scrollbar,
        }"
      >
        <me-loading ref="pullDownLoading" type="down" />
        <div class="SwiperSlideContent">
          <!-- 所有内容放在插槽里 -->
          <slot></slot>
          <div v-if="!$slots.default" class="empty">
            - 空内容 -
          </div>
        </div>
        <me-loading ref="pullUpLoading" type="up" />
      </div>
    </SwiperSlide>
    <div class="swiper-scrollbar" slot="scrollbar" v-if="scrollbar">
      <div class="swiper-scrollbar-drag"></div>
    </div>
  </swiper>
</template>

<script>
import { Swiper, SwiperSlide } from "vue-awesome-swiper";
import "swiper/swiper.min.css";
import "./css/SwiperScroll.scss";
import MeLoading from "./loading";

export default {
  name: "SwiperScrollbar",
  components: {
    Swiper,
    SwiperSlide,
    MeLoading,
  },
  data() {
    return {
      pulling: false, //是否正在下拉中
      noMore: false,
      maxLoadingHeight: 38,
    };
  },
  props: {
    scrollbar: {
      type: Boolean,
      default: false,
    },
    up: {
      type: Boolean,
      default: false,
    },
    down: {
      type: Boolean,
      default: false,
    },
    autoMore: {
      type: Boolean,
      default: true,
    },
  },
  computed: {
    swiperOption() {
      let swiperOption = {
        direction: "vertical",
        slidesPerView: "auto",
        freeMode: true,
        mousewheel: true, //能够使用鼠标滑轮滑动
        autoHeight: false, //高度随内容变化
        setWrapperSize: false,
        updateOnWindowResize: true, //窗口自动适应
        on: {
          //下拉刷新时触发的事件
          sliderMove: this.sliderMove, //一开始使用sliderMove，有bug
          touchEnd: this.touchEnd,
        },
      };
      if (this.scrollbar) {
        swiperOption.scrollbar = {
          el: ".swiper-scrollbar",
        };
      }
      return swiperOption;
    },
  },
  watch: {},
  methods: {
    loadMore() {
      const swiper = this.$refs.swiper.$swiper;
      if (
        swiper.isEnd &&
        !this.noMore &&
        (this.autoMore || Math.abs(swiper.translate) > this.maxLoadingHeight)
      ) {
        if (this.up) {
          //开始上拉
          this.pulling = true;
          // swiper.allowTouchMove = false; //禁止触摸
          swiper.setTransition(swiper.params.speed); //设置初始速度
          swiper.setTranslate(swiper.translate - 100);
          //超过拉动距离时回弹
          //swiper.params.virtualTranslate = true; //定住不给回弹
          this.$emit("pullUp", this.pullUpEnd); //触发消息，传递结束下拉的函数
        }
      }
    },
    changUpdate() {
      this.$refs.swiper && this.$refs.swiper.$swiper.update(); //更新滚动条长度
    },
    getPullUpTrnslate() {
      // 获得上拉距离
      const swiper = this.$refs.swiper.$swiper;
      const SwiperSlide = this.$refs.SwiperSlide;
      return (
        SwiperSlide.$el.offsetHeight -
        Math.abs(swiper.translate) -
        swiper.height
      );
    },
    sliderMove() {
      if (this.pulling) return; //正在下拉中，则不重复下拉
      const swiper = this.$refs.swiper.$swiper;
      if (swiper.translate > 0) {
        if (this.down) {
          this.$refs.pullDownLoading.changeTranslate(swiper.translate);
        }
      } else if (swiper.isEnd) {
        if (this.up) {
          //上拉
          this.$refs.pullUpLoading.changeTranslate(this.maxLoadingHeight);
        }
      }
    },
    touchEnd() {
      const swiper = this.$refs.swiper.$swiper;
      // 非正常结束
      if (swiper.translate > 0) {
        if (swiper.translate < this.maxLoadingHeight) {
          this.$refs.pullDownLoading.changeTranslate(0);
        }
      }
      if (this.pulling) {
        return; //正在下拉中，则不重复下拉
      }
      if (swiper.translate > this.maxLoadingHeight) {
        if (this.down) {
          this.pulling = true; //正在下拉中

          // swiper.allowTouchMove = false; //禁止触摸
          swiper.setTransition(swiper.params.speed); //设置初始速度
          swiper.setTranslate(0); //移动到设定的位置（拖动过度时回到设置的位置）
          //swiper.params.virtualTranslate = true; //定住不给回弹
          this.$emit("pullDown", this.pullDownEnd); //触发消息，传递结束下拉的函数
        }
      } else {
        this.loadMore();
      }
      this.changUpdate();
    },
    pullDownEnd() {
      const swiper = this.$refs.swiper.$swiper;

      this.pulling = false; //下拉结束

      this.$refs.pullDownLoading.changeTranslate(0);
      swiper.allowTouchMove = true; //可以触摸
      swiper.setTransition(swiper.params.speed); //设置初始速度
      swiper.params.virtualTranslate = false; //可以回弹
      this.changUpdate();
      this.setNoMore(false);
    },
    pullUpEnd(noMore) {
      const swiper = this.$refs.swiper.$swiper;
      this.setNoMore(noMore);
      this.$refs.pullUpLoading.changeTranslate(0);
      this.pulling = false;
      swiper.allowTouchMove = true; //可以触摸
      swiper.params.virtualTranslate = false; //可以回弹
      this.changUpdate();
    },
    setNoMore(v) {
      this.noMore = v;
      this.$refs.pullUpLoading.setNoMore(v);
    },
  },
};
</script>

<style lang="scss" scoped>
.scrollbarContent {
  padding-right: 6px;
}
</style>
