<template>
  <div class="dashboard-editor-container">


    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="ibox ">
        <div class="ibox-title">
          <h3>打样确认率 </h3>
        </div>
        <div class="ibox-content">
          <el-table
            :data="tableData"
            stripe
            border
            style="width: 100%">
            <el-table-column
              prop="NWX"
              sortable
              label="内外销">
            </el-table-column>
            <el-table-column
              prop="ConfirmationNum"
              sortable
              label="确认数">
            </el-table-column>
            <el-table-column
              prop="SampleNum"
              sortable
              label="打样数">
            </el-table-column>
            <el-table-column
              prop="Avg"
              sortable
              label="确认率">
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
  import {getConfirmation} from '@/api/dashboard/fx_technologist'

  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'fx_sample_confirmation',
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

        getConfirmation().then(response => {


          for (var i = 0; i < response.data.length; i++) {


            this.tableData.push({
              NWX: response.data[i].NWX,
              ConfirmationNum: (response.data[i].ConfirmationNum),
              SampleNum: (response.data[i].SampleNum),
              Avg: ((response.data[i].ConfirmationNum) / (response.data[i].SampleNum) * 100).toFixed(0) + '%'


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

