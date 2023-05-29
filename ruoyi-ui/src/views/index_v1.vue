<template>
  <div class="dashboard-editor-container">

    <student-panel-group :biyeInfo="biyeInfo" :level="level" @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper">
          <BiYeProcessChart :materials="biyeInfo.stuMaterials"/>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="32">
<!--      <el-col :xs="24" :sm="24" :lg="8">-->
<!--        <div class="chart-wrapper">-->
<!--          <raddar-chart />-->
<!--        </div>-->
<!--      </el-col>-->
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <student-pie-chart :clsInfo="biyeInfo.clsInfo"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="16">
        <div class="chart-wrapper">
          <student-bar-chart :biyeInfo="biyeInfo" />
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import StudentPanelGroup from './dashboard/StudentPanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import StudentPieChart from './dashboard/StudentPieChart'
import StudentBarChart from './dashboard/StudentBarChart'
import BiYeProcessChart from './dashboard/BiYeProcessChart'
import {getTopData} from '@/api/stu/info'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    StudentPanelGroup,
    LineChart,
    RaddarChart,
    StudentPieChart,
    StudentBarChart,
    BiYeProcessChart
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      level:0,
      biyeInfo:{
        stuNum:0,
        finshedEnMaterial:0,
        totalEnMaterial:0,
        clsInfo:[],
        clsDataInfo:[]
      }
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    getLevel(){
      let now = new Date();
      now.getMonth()>10 ? this.level = now.getFullYear()+1 : this.level = now.getFullYear()
    }
  },
  mounted() {
    this.getLevel();
    getTopData(this.level).then(res=>{
      this.biyeInfo = res.data
      console.log(this.biyeInfo)
    })
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
