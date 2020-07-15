<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="7" :lg="7">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>婚纱框畅销款 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableData婚纱框"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="CNY"
                sortable
                label="销售金额">
              </el-table-column>
              <el-table-column
                prop="Area"
                sortable
                label="热销区域">
              </el-table-column>
              <el-table-column
                prop="CustomerNum"
                sortable
                label="客户数量">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="1" :lg="1">
        <h1></h1>
      </el-col>
      <el-col :xs="24" :sm="7" :lg="7">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>建材畅销款 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableData建材"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="CNY"
                sortable
                label="销售金额">
              </el-table-column>
              <el-table-column
                prop="Area"
                sortable
                label="热销区域">
              </el-table-column>
              <el-table-column
                prop="CustomerNum"
                sortable
                label="客户数量">
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="1" :lg="1">
        <h1></h1>
      </el-col>
      <el-col :xs="24" :sm="7" :lg="7">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>画框畅销款 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableData画框"
              stripe
              border
              style="width: 100%">
              <el-table-column
                prop="MouldingStyleCode"
                sortable
                label="型号">
              </el-table-column>
              <el-table-column
                prop="CNY"
                sortable
                label="销售金额">
              </el-table-column>
              <el-table-column
                prop="Area"
                sortable
                label="热销区域">
              </el-table-column>
              <el-table-column
                prop="CustomerNum"
                sortable
                label="客户数量">
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
  import {getBestJiancai} from '@/api/dashboard/fx_technologist'
  import {getBestHunsha} from '@/api/dashboard/fx_technologist'
  import {getBestHuakuang} from '@/api/dashboard/fx_technologist'



  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_best_selling_by_use',
    data() {
      return {
        tableData婚纱框: [],
        tableData画框: [],
        tableData建材: []

      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        getBestHunsha().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableData婚纱框.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2),
              Area: response.data[i].Area,
              CustomerNum: response.data[i].CustomerNum,


            })
          }
        })

        getBestJiancai().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableData建材.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2),
              Area: response.data[i].Area,
              CustomerNum: response.data[i].CustomerNum,

            })
          }
        })

        getBestHuakuang().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableData画框.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2),
              Area: response.data[i].Area,
              CustomerNum: response.data[i].CustomerNum,

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

