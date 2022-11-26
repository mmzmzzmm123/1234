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
  sumList,
} from "@/api/student/pie";
import echarts from "echarts";
import axios from 'axios' 
export default {
  name: "Index",
  mounted() {
    this.echartsInit();
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
      mainTotle:0,
      inSchoolTotle:0,
      leaveStuTotle:0,
      notBackTotle:0,
      importmantTotle:0
    };
  },
  methods: {
    //初始化echarts
    echartsInit() {
      sumList().then(res=>{
        const code = res.code
        if(code !=200){
          alert("后台错误")
        }else{
          const data = res.data[0]
          // 总览
          this.listAcc = [
            {name:'在校\n人数',value:parseInt(data.inSchoolAcc)},
            {name:'非在校\n人数',value:parseInt(data.stuAcc)-parseInt(data.inSchoolAcc)}
          ]
          console.log(this.listAcc)
          this.mainTotle=parseInt(data.stuAcc)
          console.log(this.mainTotle)

          // 在校生
          this.listResidence = [
            {name:'教学区\n宿舍区',value:parseInt(data.dormitoryTeachingAreaAcc)},
            {name:'教学区\n其他地方',value:parseInt(data.dormitoryOtherAreaAcc)},
            {name:'家属区',value:parseInt(data.familyAreaAcc)},
            {name:'走读生',value:parseInt(data.dayStudentAcc)},
          ]
          this.inSchoolTotle = parseInt(data.dormitoryTeachingAreaAcc)+parseInt(data.dormitoryOtherAreaAcc)+
                                parseInt(data.familyAreaAcc)+parseInt(data.dayStudentAcc)
          // 请假离校（不返校）
          this.leaveStu=[
            {name:'市内',value:parseInt(data.leaveInCityAcc)},
            {name:'省内\n市外',value:parseInt(data.leaveOutCityInProAcc)},
            {name:'省外',value:parseInt(data.leaveOutProAcc)},
          ]
          console.log(this.leaveStu)
          this.leaveStuTotle = parseInt(data.leaveInCityAcc)+parseInt(data.leaveOutCityInProAcc)+parseInt(data.leaveOutProAcc)
          // 未返校
          this.listNobackschool=[
            {name:'因疫暂缓\n返校人数',value:parseInt(data.epidemicSusAcc)},
            {name:'因就业实习\n暂缓返校人数',value:parseInt(data.practiceSusAcc)},
            {name:'因其他暂缓\n返校人数',value:parseInt(data.otherSusAcc)},
          ]
          this.notBackTotle = parseInt(data.epidemicSusAcc)+parseInt(data.practiceSusAcc)+parseInt(data.otherSusAcc)
          // 重点监测人群
          this.listPlacetoschool=[
            {name:'校外实际集\n中隔离人数',value:parseInt(data.outSchCentIsolationAcc)},
            {name:'校内实际居\n家隔离人数',value:parseInt(data.outSchHomeIsolationAcc)},
            {name:'校内实际居\n家健康监测人数',value:parseInt(data.inSchHomeIsolationAcc)},
          ]
          this.importmantTotle = parseInt(data.outSchCentIsolationAcc)+parseInt(data.outSchHomeIsolationAcc)+parseInt(data.inSchHomeIsolationAcc)

          // 总体
          this.$echarts.init(document.getElementById("mainPie")).setOption({
            title:{
                  show:true,
                  text:'人员情况:'+this.mainTotle,
                  x:'center',      // 水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                  y: 'bottom',
            },
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
          // 在校学生
          this.$echarts.init(document.getElementById("inSchool")).setOption({
            title:{
                  show:true,
                  text:'在校生情况:'+this.inSchoolTotle,
                  x:'center',      // 水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                  y: 'bottom',
            },
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
          //请假（不返校）
          this.$echarts.init(document.getElementById("leaveStu")).setOption({
            title:{
                  show:true,
                  text:'请假学生（不返校）情况:'+this.leaveStuTotle,
                  x:'center',      // 水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                  y: 'bottom',
            },
            series: [
              {
                name: "请假离校",
                type: "pie", // 设置图表类型为饼图
                radius: "55%", // 饼图的半径，外半径为可视区尺寸（容器高宽中较小一项）的 55% 长度。
                data: this.leaveStu,
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
            title:{
                  show:true,
                  text:'未返校学生情况:'+this.notBackTotle,
                  x:'center',      // 水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                  y: 'bottom',
            },
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
          
          //重点
          this.$echarts.init(document.getElementById("specialStu")).setOption({
            title:{
                  show:true,
                  text:'重点监测人员情况:'+this.importmantTotle,
                  x:'center',      // 水平安放位置，默认为'left'，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
                  y: 'bottom',
            },
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
        }
        
      })
      
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

