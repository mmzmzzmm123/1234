<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import {formatDate_date, inDateBetween} from '@/utils'
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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    showType: {
      type: String,
      required: true
    },
    dateRange: {
      type: Array,
      required: true
    },
    chartData: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    showType: {
      deep: true,
      handler(val) {
        this.setOptions(val, this.dateRange, this.chartData)
      }
    },
    dateRange: {
      deep: true,
      handler(val) {
        this.setOptions(this.showType, val, this.chartData)
      }
    },
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(this.showType, this.dateRange, val)
      }
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
      this.setOptions(this.showType, this.dateRange, this.chartData)
    },
    setOptions(showType, dateRange, orderData) {
      let tips = showType==='0'?'订单总数':showType==='1'?'有效单数':showType==='2'?'取消单数':showType==='3'?'订单金额':showType==='4'?'待支付订单数':showType==='5'?'有效订单金额':showType==='6'?'取消订单金额':showType==='7'?'待支付金额':'其他';
      let dateArr = this.getAllDate(dateRange[0], dateRange[1]);
      let data = [];
      // 根据展示类型生成对应的数据
      if (showType === '0') {
        data = this.orderNumCount(dateArr, orderData);
      }
      if (showType === '1') {
        data = this.validOrderNumCount(dateArr, orderData);
      }
      if (showType === '2') {
        data = this.cancelOrderNumCount(dateArr, orderData);
      }
      if (showType === '4') {
        data = this.waitPayOrderNumCount(dateArr, orderData);
      }
      if (showType === '3') {
        data = this.orderTotalAmountCount(dateArr, orderData);
      }
      if (showType === '5') {
        data = this.validOrderTotalAmountCount(dateArr, orderData);
      }
      if (showType === '6') {
        data = this.cancelOrderTotalAmountCount(dateArr, orderData);
      }
      if (showType === '7') {
        data = this.waitPayOrderTotalAmountCount(dateArr, orderData);
      }
      // 开始设置参数
      this.chart.setOption({
        xAxis: {
          data: dateArr,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: [tips]
        },
        series: [
          {
            name: tips,
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#3888fa',
                lineStyle: {
                  color: '#3888fa',
                  width: 2
                },
                areaStyle: {
                  color: '#f3f8ff'
                }
              }
            },
            data: data,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }]
      })
    },
    /**
     * 根据日期订单总数统计
     * */
    orderNumCount(dateArr, orderData) {
      let data = [];
      for (let index in dateArr) {
        let num = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime)) {
            num += 1;
          }
        }
        data.push(num);
      }
      return data;
    },
    /**
     * 有效单数计算
     * */
    validOrderNumCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let num = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && (tempObj.orderState === '0' || tempObj.orderState === '2' || tempObj.orderState === '3' || tempObj.orderState === '4' || tempObj.orderState === '5')) {
            num += 1;
          }
        }
        data.push(num);
      }
      return data;
    },
    /**
     * 取消订单数计算
     * */
    cancelOrderNumCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let num = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && tempObj.orderState === '-1') {
            num += 1;
          }
        }
        data.push(num);
      }
      return data;
    },
    /**
     * 待支付单数计算
     * */
    waitPayOrderNumCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let num = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && tempObj.orderState === '1') {
            num += 1;
          }
        }
        data.push(num);
      }
      return data;
    },
    /**
     * 订单总金额计算
     * */
    orderTotalAmountCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let amount = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime)) {
            amount += tempObj.payAmount;
          }
        }
        data.push(amount);
      }
      return data;
    },
    /**
     * 有效订单总金额计算
     * */
    validOrderTotalAmountCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let amount = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && (tempObj.orderState === '0' || tempObj.orderState === '2' || tempObj.orderState === '3' || tempObj.orderState === '4' || tempObj.orderState === '5')) {
            amount += tempObj.payAmount;
          }
        }
        data.push(amount);
      }
      return data;
    },
    /**
     * 订单订单总金额计算
     * */
    cancelOrderTotalAmountCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let amount = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && tempObj.orderState === '-1') {
            amount += tempObj.payAmount;
          }
        }
        data.push(amount);
      }
      return data;
    },
    /**
     * 订单订单总金额计算
     * */
    waitPayOrderTotalAmountCount(dateArr, orderData){
      let data = [];
      for (let index in dateArr) {
        let amount = 0;
        let startDate = dateArr[index] + " 00:00:00";
        let endDate = dateArr[index] + " 23:59:59";
        for (let i = 0; i < orderData.length; i++) {
          let tempObj = orderData[i];
          if (inDateBetween(startDate, endDate, tempObj.createTime) && tempObj.orderState === '1') {
            amount += tempObj.payAmount;
          }
        }
        data.push(amount);
      }
      return data;
    },
    /**
     * 获取时间段内的所有日期
     *
     * @param start 开始日期
     * @param end 结束日期
     * @return []
     * */
    getAllDate(start, end) {
      let dateList = [];
      let startTime = Date.parse(start);
      let endTime = Date.parse(end);
      let days = Math.ceil((endTime - startTime) / (1000 * 60 * 60 * 24));
      for (let i = 0; i <= days; i++) {
        let tempDate = new Date(startTime + i * 24 * 60 * 60 * 1000);
        dateList.push(formatDate_date(tempDate));
      }
      return dateList;
    },
  }
}
</script>
