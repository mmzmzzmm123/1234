<template>
  <div >
    <div class="ibox-title">
      <el-row :gutter="24">

        <el-col :xs="24" :sm="24" :lg="24">

          <b>选择时间范围</b>

          <el-date-picker
            v-model="daterange_time1"
            align="right"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions">
          </el-date-picker>

          <b>--</b>
          <el-date-picker
            v-model="daterange_time2"
            align="right"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择日期"
            :picker-options="pickerOptions">
          </el-date-picker>

          <el-button @click="getData" type="primary">查询</el-button>

        </el-col>
      </el-row>
    </div>
    <el-container >


      <div id="chart_density_avg" style="width: 90%;height: 600px;top:20px"></div>
    </el-container>
  </div>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') // echarts theme
  import resize from '../dashboard/mixins/resize'
  import { getDensityDataRange } from '@/api/dashboard/density'

  let lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default  {
    mixins: [resize],
    name: 'density30day',
    data() {
      return {
        tableData: [],
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }, {
            text: '30天前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', date);
            }
          }]
        },
        daterange_time1: '',
        daterange_time2: '',
      }
    },

    created() {
      // this.getData()
    },
    mounted() {


    },
    methods: {
      getData() {

        lineChartData = {
          xAxisData: [],
          actualData: []
        }

        getDensityDataRange(this.daterange_time1,this.daterange_time2).then(response => {
          for (var i = 0; i < response.data.length; i++) {
            lineChartData.xAxisData.push((response.data[i].time + '').replace(' 00:00:00.0', ''))
            lineChartData.actualData.push(parseFloat(response.data[i].density).toFixed(3))
          }

          const chart_energy_avg = echarts.init(document.getElementById('chart_density_avg'))

          option_energy_avg.xAxis[0].data = lineChartData.xAxisData
          option_energy_avg.series[0].data = lineChartData.actualData
          option_energy_avg.title.text = '平均密度'
          option_energy_avg.yAxis[0].min = (Math.min.apply(null, lineChartData.actualData).toFixed(2)*1-0.01)
          option_energy_avg.yAxis[0].max = (Math.max.apply(null, lineChartData.actualData).toFixed(2)*1+0.01)

          chart_energy_avg.setOption(option_energy_avg, true)

        })
      }
    }

  }

  const option_energy_avg = {
    title: {
      text: '',
      textStyle: {
        color: '#000',
        fontSize: 18
      },
      itemGap: 5,

      x: 'center'
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: [{
      type: 'category',
      data: ['1#', '2#', '3#', '30#'],
      axisLine: {
        lineStyle: {
          color: '#999'
        }
      }
    }],
    yAxis: [{
      type: 'value',
      min: 0.36,
      max: 0.42,

      // splitNumber: 12,
      splitLine: {
        lineStyle: {
          type: 'dashed',
          color: '#DDD'
        }
      },
      axisLine: {
        show: false,
        lineStyle: {
          color: '#333'
        }
      },
      nameTextStyle: {
        color: '#999'
      },
      splitArea: {
        show: false
      }
    }],
    series: [{
      name: '密度',
      type: 'line',
      data: [80, 72],
      lineStyle: {
        normal: {
          width: 8,
          color: {
            type: 'linear',

            colorStops: [{
              offset: 0,
              color: '#A9F387' // 0% 处的颜色
            }, {
              offset: 1,
              color: '#48D8BF' // 100% 处的颜色
            }],
            globalCoord: false // 缺省为 false
          },
          shadowColor: 'rgba(72,216,191, 0.3)',
          shadowBlur: 10,
          shadowOffsetY: 20
        }
      },
      itemStyle: {
        normal: {
          color: '#a32323',
          borderWidth: 10,
          /*shadowColor: 'rgba(72,216,191, 0.3)',
          shadowBlur: 100,*/
          borderColor: '#A9F387',
          label : {show: true}
        }
      },
      smooth: false,
      markLine: {
        label: {
          formatter: '{b}: {c}'
        },
        precision: 4,
        lineStyle: {
          width: 3,
          color: 'red'
        },
        data: [{
          name: '目标',
          yAxis: '0.38'
        }, {
          type: 'average',
          name: '平均'
        }]
      }
    }]

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

