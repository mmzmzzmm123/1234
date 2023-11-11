<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('0')">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="guide" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            订单总数
          </div>
          <count-to :start-val="0" :end-val="orderNum" :duration="2600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('1')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="dashboard" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            有效订单
          </div>
          <count-to :start-val="0" :end-val="validNum" :duration="3000" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('2')">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="lock" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            取消订单
          </div>
          <count-to :start-val="0" :end-val="cancelNum" :duration="3200" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('4')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="monitor" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            待支付订单
          </div>
          <count-to :start-val="0" :end-val="waitPayNum" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('3')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="money" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            订单金额
          </div>
          <count-to :start-val="0" :end-val="orderAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('5')">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="chart" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            有效订单金额
          </div>
          <count-to :start-val="0" :end-val="validOrderAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('6')">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="number" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            取消订单金额
          </div>
          <count-to :start-val="0" :end-val="cancelOrderAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel" @click="handleSetLineChartData('7')">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="time-range" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            待支付金额
          </div>
          <count-to :start-val="0" :end-val="waitPayOrderAmount" :duration="3600" class="card-panel-num" />
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>

import CountTo from 'vue-count-to'

export default {
  components: {
    CountTo
  },
  props: {
    chartData: {
      type: Array,
      required: true
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setCharData(val)
      }
    }
  },
  data(){
    return {
      orderNum: 0,
      validNum: 0,
      cancelNum: 0,
      waitPayNum: 0,
      orderAmount: 0,
      validOrderAmount: 0,
      cancelOrderAmount: 0,
      waitPayOrderAmount: 0
    }
  },
  methods: {
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    },
    /**
     * 设置数据
     * */
    setCharData(dataArr){
      // 订单总数与总额
      this.orderNum = dataArr.length;
      let totalAmount = 0;
      for (let index in dataArr){
        totalAmount += dataArr[index].payAmount;
      }
      this.orderAmount = totalAmount;

      // 有效订单数与总额
      let validOrderAmount = 0;
      let validOrderArr = dataArr.filter(item => (item.orderState === '0' || item.orderState === '2' || item.orderState === '3' || item.orderState === '4' || item.orderState === '5'));
      this.validNum = validOrderArr.length;
      for (let index in validOrderArr){
        validOrderAmount+= validOrderArr[index].payAmount;
      }
      this.validOrderAmount = validOrderAmount;

      // 取消订单
      let cancelOrderAmount = 0;
      let cancelOrderArr = dataArr.filter(item => item.orderState === '-1');
      this.cancelNum = cancelOrderArr.length;
      for (let index in cancelOrderArr){
        cancelOrderAmount+= cancelOrderArr[index].payAmount;
      }
      this.cancelOrderAmount = cancelOrderAmount;

      // 待支付单数
      let waitPayOrderAmount = 0;
      let waitPayOrderArr = dataArr.filter(item => item.orderState === '1');
      this.waitPayNum = waitPayOrderArr.length;
      for (let index in waitPayOrderArr){
        waitPayOrderAmount += waitPayOrderArr[index].payAmount;
      }
      this.waitPayOrderAmount = waitPayOrderAmount;
    }
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
