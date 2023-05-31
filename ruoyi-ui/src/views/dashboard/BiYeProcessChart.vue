<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import month from "@/components/Crontab/month";

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
    materials:{
      type: Array,
    }
  },
  data() {
    return {
      chart: null,
      yHeads:[]
    }
  },
  watch:{
    materials(newData,oldData){
      this.yHeads = []
      newData.forEach((item)=>{
        this.yHeads.push(item.name)
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
    getStartDate(){
      let now = new Date()
      if(now.getMonth()>8) return new Date(now.getFullYear()+'-10')
      return new Date(now.getFullYear()-1+'-10')},
    getEndDate(){
      let now = new Date()
      if(now.getMonth()>8) return new Date(now.getFullYear()+1+'-5')
      return new Date(now.getFullYear()+'-5')},
    getData(){
      let now = new Date()
      let startYear;
      now.getMonth()>8 ? startYear = now.getFullYear():startYear = now.getFullYear()-1
      let endYear = startYear +1
      return [startYear+'-12',endYear+'-05',endYear+'-05',endYear+'-05',
        endYear+'-5',startYear+'-12']
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            // Use axis to trigger tooltip
            type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
          }
        },
        title: [
          {
            text: '进度安排表',
            left: 'center',
            textStyle:{
              color:'#350080',
              fontWeight: 'bold'
            }
          }
        ],
        legend: {
          data:this.yHeads
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: "time",
          min: this.getStartDate(),
          max: this.getEndDate(),
          axisLabel: {
            interale: 0,
            // rotate: -40, //设置日期显示样式（倾斜度）
            formatter: function (value) {//在这里写你需要的时间格式
              var t_date = new Date(value);
              return [t_date.getFullYear(), t_date.getMonth() + 1].join('-')
              // + " " + [t_date.getHours(), t_date.getMinutes()].join(':'); 时分
            }
          }
        },
        yAxis: {
          type: 'category',
          data: this.yHeads
        },
        series: [
          {
            name: '进度',
            type: 'bar',
            stack: 'x',
            label: {
              show: true,
              position:'top',
              textStyle: { //数值样式
                color: 'black',
                fontSize: 16
              },
              text:'123'
            },
            emphasis: {
              focus: 'series'
            },
            data: this.getData(),
            itemStyle: {
              normal: {
                //这里是重点
                color: function(params) {
                  //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                  var colorList = ['#c23531','#2f4554', '#61a0a8', '#d48265', '#666',
                    '#91c7ae','#749f83', '#ca8622','#c23531','#a182f92'];
                  return colorList[params.dataIndex]
                }
              }
            }
          },
        ]
      })
    }
  }
}
</script>
