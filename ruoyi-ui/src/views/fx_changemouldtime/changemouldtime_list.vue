<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="ibox ">
        <div class="ibox-title">
          <h3>换膜明细 </h3>
        </div>
        <div class="ibox-content">
          <el-table
            :data="tableData"
            stripe
            border
            style="width: 100%">
            <el-table-column
              prop="StartTime"
              sortable
              label="换模开始时间">
            </el-table-column>
            <el-table-column
              prop="Mould"
              sortable
              label="模具">
            </el-table-column>
            <el-table-column
              prop="ChangeMouldTime"
              sortable
              label="换模时间">
            </el-table-column>
            <el-table-column
              prop="Line"
              sortable
              label="线号">
            </el-table-column>

          </el-table>
        </div>
      </div>
    </el-row>

  </div>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') // echarts theme
  import resize from '../dashboard/mixins/resize'
  import {getChangeMouldTimeList} from '@/api/dashboard/changemouldtime'

  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'density30day',
    data() {
      return {
        tableData: []
      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        getChangeMouldTimeList().then(response => {


          for (var i = 0; i < response.data.length; i++) {
            // lineChartData.xAxisData.push((response.data[i].time + '').replace(' 00:00:00.0', ''))
            // lineChartData.actualData.push(parseFloat(response.data[i].density).toFixed(4))


            this.tableData.push({
              Line: response.data[i].Line,
              ChangeMouldTime: (response.data[i].ChangeMouldTime)*1,
              Mould: response.data[i].Mould,
              StartTime: response.data[i].StartTime,

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

