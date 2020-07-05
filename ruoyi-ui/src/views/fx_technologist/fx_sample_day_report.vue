<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="11" :lg="11">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>新订单 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataDayFinishedSampleOrder"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="Technologist"
                sortable
                filter-method="filterHandler"
                filters="[{text: '唐力', value: '唐力'}, {text: '唐加名', value: '唐加名'}]"
                label="工艺员">
              </el-table-column>
              <el-table-column
                prop="SalesMan"
                sortable
                label="销售">
              </el-table-column>
              <el-table-column
                prop="CNY"
                sortable
                label="该款12个月销售金额">
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
            <h3>开模单 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataDayNewMould"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="UserName"
                sortable
                label="工艺员">
              </el-table-column>
              <el-table-column
                prop="new_or_fix"
                sortable
                label="分类">
              </el-table-column>
              <el-table-column
                prop="MouldCode"
                sortable
                label="模具">
              </el-table-column>


            </el-table>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="11" :lg="11">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>打样完成 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataDayFinishedSample"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="Technologist"
                sortable
                label="工艺员">
              </el-table-column>
              <el-table-column
                prop="FinishedDensity"
                sortable
                label="密度">
              </el-table-column>
              <el-table-column
                prop="FinishedSpeed"
                sortable
                label="产能">
              </el-table-column>
              <el-table-column
              prop="FinishedBy"
              sortable
              label="打样员">
            </el-table-column>
              <el-table-column
                prop="UseDay"
                sortable
                label="交期">
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
            <h3>BOM完成 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataDayFinishedBOM"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="Maker"
                sortable
                label="制作人">
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
  import {DayFinishedSampleOrder} from '@/api/dashboard/fx_technologist'
  import {DayNewMould} from '@/api/dashboard/fx_technologist'
  import {DayFinishedSample} from '@/api/dashboard/fx_technologist'
  import {DayFinishedBOM} from '@/api/dashboard/fx_technologist'


  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_confirmation',
    data() {
      return {
        tableDataDayFinishedSampleOrder: [],
        tableDataDayNewMould: [],
        tableDataDayFinishedSample: [],
        tableDataDayFinishedBOM: [],

      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        DayFinishedSampleOrder().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataDayFinishedSampleOrder.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2),
              SalesMan: response.data[i].SalesMan,
              Technologist: response.data[i].Technologist,

            })
          }
        })

        DayNewMould().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataDayNewMould.push({
              UserName: response.data[i].UserName,
              new_or_fix: response.data[i].new_or_fix,
              MouldCode: response.data[i].MouldCode,

            })
          }
        })

        DayFinishedSample().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataDayFinishedSample.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              Technologist: response.data[i].Technologist,
              FinishedDensity: response.data[i].FinishedDensity,
              FinishedSpeed: response.data[i].FinishedSpeed,
              FinishedBy: response.data[i].FinishedBy,
              UseDay: response.data[i].UseDay,
            })
          }
        })


        DayFinishedBOM().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataDayFinishedBOM.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              Maker: response.data[i].Maker,
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

