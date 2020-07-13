<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :xs="24" :sm="11" :lg="11">

        <div class="ibox ">
          <div class="ibox-title">
            <h3>ODM爆款 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataODM"
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
                label="金额">
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
            <h3>OEM爆款 </h3>
          </div>
          <div class="ibox-content">
            <el-table
              :data="tableDataOEM"
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
                label="金额">
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
  import {getBestOEM} from '@/api/dashboard/fx_technologist'
  import {getBestODM} from '@/api/dashboard/fx_technologist'


  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_best_selling_by_odm_oem',
    data() {
      return {
        tableDataODM: [],
        tableDataOEM: []

      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        getBestOEM().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataOEM.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2)

            })
          }
        })

        getBestODM().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableDataODM.push({
              MouldingStyleCode: response.data[i].MouldingStyleCode,
              CNY: ((response.data[i].CNY)*1).toFixed(2)
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

