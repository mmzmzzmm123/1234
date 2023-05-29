<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
// import {getTopData} from '@/api/stu/info'
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

const animationDuration = 2000

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
    biyeInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      chart: null,
      heads: [],
      series:[],
      labelOption : {}
    }
  },
  watch:{
    biyeInfo(newData,oldData){
      let numArray = []
      newData.clsInfo.forEach((item)=>{
        this.heads.push(item.cls)
        numArray.push(item.stuNum)
      })
      this.series.push({
        name: '班级人数',
        type: 'bar',
        barGap: 0,
        label: this.labelOption,
        emphasis: {
          focus: 'series'
        },
        data: numArray
      })
      newData.stuMaterials.forEach((item,index)=>{
        let numsArray = []
        newData.clsInfo.forEach((cls)=>{
          let count = 0;
          newData.clsCountData.forEach((csd)=>{
            if(csd.stuCls == cls.cls) {
              count = csd.num
            }
          })
          numsArray.push(count)
        })
        this.series.push({
          name: item.name,
          type: 'bar',
          barGap: 0,
          label: this.labelOption,
          emphasis: {
            focus: 'series'
          },
          data: numsArray
        })
      })
      this.initChart()
    }
  },
  mounted() {
    app.config = {
      rotate: 90,
      align: 'left',
      verticalAlign: 'middle',
      position: 'insideBottom',
      distance: 15,
      onChange: function () {
        const labelOption = {
          rotate: app.config.rotate,
          align: app.config.align,
          verticalAlign: app.config.verticalAlign,
          position: app.config.position,
          distance: app.config.distance
        };
        myChart.setOption({
          series: [
            {
              label: labelOption
            },
            {
              label: labelOption
            },
            {
              label: labelOption
            },
            {
              label: labelOption
            },
            {
              label: labelOption
            },
            {
              label: labelOption
            }
          ]
        });
      }
    };
    this.labelOption = {
      show: true,
      position: app.config.position,
      distance: app.config.distance,
      align: app.config.align,
      verticalAlign: app.config.verticalAlign,
      rotate: app.config.rotate,
      formatter: '{c}  {name|{a}}',
      fontSize: 16,
      rich: {
        name: {}
      }
    }
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
      const posList = [
        'left',
        'right',
        'top',
        'bottom',
        'inside',
        'insideTop',
        'insideLeft',
        'insideRight',
        'insideBottom',
        'insideTopLeft',
        'insideTopRight',
        'insideBottomLeft',
        'insideBottomRight'
      ];
      app.configParameters = {
        rotate: {
          min: -90,
          max: 90
        },
        align: {
          options: {
            left: 'left',
            center: 'center',
            right: 'right'
          }
        },
        verticalAlign: {
          options: {
            top: 'top',
            middle: 'middle',
            bottom: 'bottom'
          }
        },
        position: {
          options: posList.reduce(function (map, pos) {
            map[pos] = pos;
            return map;
          }, {})
        },
        distance: {
          min: 0,
          max: 100
        }
      };

      this.chart.setOption({
        title: [
          {
            text: '各班级进度',
            left: 'center',
            textStyle:{
              color:'#350080',
              fontWeight: 'bold'
            }
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: this.heads
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: this.series
      })
    },
  }
}
</script>
