<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="11" :lg="11">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>已完成打样 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataFinished"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="Technologist"
                sortable
                label="工艺员">
              </el-table-column>
              <el-table-column
                prop="num"
                sortable
                label="数量">
              </el-table-column>
              <el-table-column
                prop="avg_f"
                sortable
                label="平均分解(天)">
              </el-table-column>
              <el-table-column
                prop="avg_j"
                sortable
                label="平均交期(天)">
              </el-table-column>

            </el-table>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="1" :lg="1">
        <h1></h1>
      </el-col>
      <el-col :xs="24" :sm="11" :lg="11">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>未完成打样 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataUnFinished"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="Technologist"
                sortable
                label="工艺员">
              </el-table-column>
              <el-table-column
                prop="num"
                sortable
                label="数量">
              </el-table-column>
              <el-table-column
                prop="avg_use_date"
                sortable
                label="平均已过天数(天)">
              </el-table-column>
              <el-table-column
                prop="day15num"
                sortable
                label="超15天数量">
              </el-table-column>


            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') // echarts theme
  import resize from '../dashboard/mixins/resize'
  import {getUnFinishedSampleAvgDay} from '@/api/dashboard/fx_technologist'
  import {getFinishedSampleAvgDay} from '@/api/dashboard/fx_technologist'


  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_confirmation',
    data() {
      return {
        tableDataFinished: [],
        tableDataUnFinished: []

      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        getFinishedSampleAvgDay().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataFinished.push({
              Technologist: response.data[i].Technologist,
              avg_f: ((response.data[i].avg_f)*1).toFixed(2),
              num: response.data[i].num,
              avg_j: ((response.data[i].avg_j)*1).toFixed(2)

            })
          }
        })

        getUnFinishedSampleAvgDay().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataUnFinished.push({
              Technologist: response.data[i].Technologist,
              num: response.data[i].num,
              day15num: response.data[i].day15num,
              avg_use_date: ((response.data[i].avg_use_date)*1)

            })
          }
        })

      }
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

  @media (max-width: 1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>

