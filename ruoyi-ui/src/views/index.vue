<template>
  <div class="mainbox-box">
    <header class="hover-header">
      <h1>可视化展板-ECharts</h1>
      <div ref="showTime" class="showTime">{{ showTime }}</div>
    </header>
    <section class="mainbox">
      <div class="column">
        <div class="panel bar">
          <upperleft />
        </div>
        <div class="panel line">
          <Leftcenter />
        </div>
        <div class="panel pie">
          <Lowerleft />
        </div>
      </div>
      <div class="column">
        <div class="no">
          <div class="no-hd">
            <ul>
              <li>125811</li>
              <li>104563</li>
            </ul>
          </div>
          <div class="no-bd">
            <ul>
              <li>需求人数</li>
              <li>供应人数</li>
            </ul>
          </div>
        </div>
        <div class="map">
          <middlemap />
        </div>
      </div>
      <div class="column">
        <div class="panel bar1">
          <upperright />
        </div>
        <div class="panel line1">
          <Rightcenter />
        </div>
        <div class="panel pie1">
          <Lowerright />
        </div>
      </div>
    </section>
  </div>
</template>
<script>
import "./../assets/css/index.css";
import "./../api/echart/flexible.js";
import "./../api/echart/china.js";
import upperleft from "./../Largescreen/upperleft.vue";
import Leftcenter from "./../Largescreen/Leftcenter.vue";
import Lowerleft from "./../Largescreen/Lowerleft.vue";
import upperright from "./../Largescreen/upperright.vue";
import Rightcenter from "./../Largescreen/Rightcenter.vue";
import Lowerright from "./../Largescreen/Lowerright.vue";
import middlemap from "./../Largescreen/middlemap.vue";
export default {
  name: "Index",
  components: {
    upperleft,
    Leftcenter,
    Lowerleft,
    upperright,
    Rightcenter,
    Lowerright,
    middlemap,
  },
  data() {
    return {
      // 版本号
      version: "3.8.4",
      t: null,
      showTime: "",
    };
  },
  mounted() {
    this.t = setTimeout(this.time(), 1000); // 開始运行
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    time() {
      clearTimeout(this.t); // 清除定时器
      var dt = new Date();
      var y = dt.getFullYear();
      var mt = dt.getMonth() + 1;
      var day = dt.getDate();
      var h = dt.getHours(); // 获取时
      var m = dt.getMinutes(); // 获取分
      var s = dt.getSeconds(); // 获取秒
      this.showTime =
        "当前时间：" +
        y +
        "年" +
        mt +
        "月" +
        day +
        "-" +
        h +
        "时" +
        m +
        "分" +
        s +
        "秒";
      var that = this;
      this.t = setTimeout(that.time, 1000); // 设定定时器，循环运行
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
.hover-header:hover {
}
</style>
