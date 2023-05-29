<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    clsInfo: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      chart: null,
      heads: [],
      mNums: []
    }
  },
  watch:{
    clsInfo(newData,oldData){
      newData.forEach((item)=>{
        this.heads.push(item.cls)
        this.mNums.push(Math.round(item.mNum/item.stuNum*100/6))
      })
      this.initChart()
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {

      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        title: [
          {
            text: '总体进度',
          }
        ],
        polar: {
          radius: [30, '80%']
        },
        angleAxis: {
          max: 100,
          startAngle: -90
        },
        radiusAxis: {
          type: 'category',
          data: this.heads
        },
        tooltip: {},
        series: {
          type: 'bar',
          data: this.mNums,
          color:'#5470c6',
          coordinateSystem: 'polar',
          label: {
            show: true,
            position: 'middle',
            formatter: '{b}: {c}'
          }
        }
      })
    }
  }
}
</script>
