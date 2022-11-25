<template>
  <div class="app-container home">
    <div id="mainPie" class="mainClass"></div>
    <div class="mainClass">
      <div id="inSchool" class="littleClass"></div>
      <div id="outSchool" class="littleClass"></div>
      <div id="leaveStu" class="littleClass"></div>
      <div id="specialStu" class="littleClass"></div>
    </div>
  </div>
</template>

<script>
import {
  acc,
  residence,
  nobackschool,
  control,
  placetoschool,
} from "@/api/student/pie";
import echarts from "echarts";
export default {
  name: "Index",
  mounted() {
    // 在通过mounted调用即可
    setTimeout(() => {
      this.echartsInit();
    }, 800);
    this.getList();
  },
  data() {
    return {
      // 版本号
      version: "3.8.4",
      labelStyle: {
        show: true,
        // formatter: '{b} : {c} ({d}%)'
        formatter: "{b} : {c} ",
      },
      listAcc: [],
      listResidence: [],
      listNobackschool: [],
      listControl: [],
      listPlacetoschool: [],
    };
  },
  methods: {
    getList() {
      acc().then((response) => {
        for (var i = 0; i < response.rows.length; i++) {
          this.listAcc.push({
            name: response.rows[i].name,
            value: response.rows[i].valueNumber,
          });
        }
      });
      residence().then((response) => {
        for (var i = 0; i < response.rows.length; i++) {
          this.listResidence.push({
            name: response.rows[i].name,
            value: response.rows[i].valueNumber,
          });
        }
      });
      nobackschool().then((response) => {
        for (var i = 0; i < response.rows.length; i++) {
          this.listNobackschool.push({
            name: response.rows[i].name,
            value: response.rows[i].valueNumber,
          });
        }
      });
      control().then((response) => {
        for (var i = 0; i < response.rows.length; i++) {
          this.listControl.push({
            name: response.rows[i].name,
            value: response.rows[i].valueNumber,
          });
        }
      });
      placetoschool().then((response) => {
        for (var i = 0; i < response.rows.length; i++) {
          this.listPlacetoschool.push({
            name: response.rows[i].name,
            value: response.rows[i].valueNumber,
          });
        }
      });
    },
    //初始化echarts
    echartsInit() {
      this.$echarts.init(document.getElementById("mainPie")).setOption({
        series: [
          {
            name: "人员情况",
            type: "pie", // 设置图表类型为饼图
            radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: this.listAcc,
            itemStyle: {
              normal: {
                label: this.labelStyle,
                labelLine: { show: true },
              },
            },
          },
        ],
      });

      this.$echarts.init(document.getElementById("inSchool")).setOption({
        series: [
          {
            name: "实际在校",
            type: "pie", // 设置图表类型为饼图
            radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: this.listResidence,
            minAngle: 15, //最小角度
            startAngle: 270, //起始角度
            itemStyle: {
              normal: {
                label: this.labelStyle,
                labelLine: { show: true },
              },
            },
          },
        ],
      });
      //未返校

      this.$echarts.init(document.getElementById("outSchool")).setOption({
        series: [
          {
            name: "未返校",
            type: "pie", // 设置图表类型为饼图
            radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: this.listNobackschool,
            itemStyle: {
              normal: {
                label: this.labelStyle,
                labelLine: { show: true },
              },
            },
          },
        ],
      });
      //请假

      this.$echarts.init(document.getElementById("leaveStu")).setOption({
        series: [
          {
            name: "请假离校",
            type: "pie", // 设置图表类型为饼图
            radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: this.listControl,
            itemStyle: {
              normal: {
                label: this.labelStyle,
                labelLine: { show: true },
              },
            },
          },
        ],
      });
      //重点

      this.$echarts.init(document.getElementById("specialStu")).setOption({
        series: [
          {
            name: "重点监测",
            type: "pie", // 设置图表类型为饼图
            radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
            data: this.listPlacetoschool,
            itemStyle: {
              normal: {
                label: this.labelStyle,
                labelLine: { show: true },
              },
            },
          },
        ],
      });
    },
  },
};
</script>

<style scoped lang="scss">
.mainClass {
  display: flex;
  width: 50%;
  height: 500px;
  flex-wrap: wrap;
}
.littleClass {
  // display: flex;
  width: 50%;
  height: 50%;
}
.home {
  display: flex;
}
</style>

