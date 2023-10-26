<template>
  <view class="page" v-if="show">
    <view class="header">
      <text class="header-title">{{ report.order.gaugeTitle }}</text>
      <text class="header-report">报告编号：{{ report.order.orderId }}</text>
      <view class="header-wrapper">
        <text class="header-name">测试者：{{ report.order.userName }}</text>
        <text class="header-time">测试时间：{{ report.order.createTime }}</text>
      </view>
    </view>
    <view class="result">
      <view class="circular">
        <circular-progress :fontShow="false" :percentage="percentage" :diam="190" :lineWidth="20" :fontSize="20"
                           progressColor="#FF703F" fontColor="#FF703F" defaultColor="#ffe4db">
          <view class="progress-text">
            <view class="text-score">{{ score }}</view>
            <view class="text-score-btn">综合得分</view>
          </view>
        </circular-progress>
      </view>
      <text class="memo1">{{ report.setting.memo1 }}</text>
      <text class="memo2">{{ report.setting.memo2 }}</text>
      <view class="result-header">
        <view class="result-left"></view>
        <view class="result-text">您的测评结果</view>
        <view class="result-right"></view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="得分解读"/>
      <view class="block-radar">
        <qiun-data-charts type="radar" :opts="opts" :chartData="radarData"/>
      </view>
      <view class="img-box" v-html="report.setting.memo3"/>
    </view>
    <view class="block-1" v-for="item in lats.filter(a => a.y !== -1)">
      <block-header :title="item.name"/>
      <range :lineBg="item.line" :proBg="item.prd" :percentage="item.score"/>
      <view class="desc">
        <view>
          {{ item.info }}
        </view>
        <view class="desc-title">{{ item.title }}</view>
      </view>
    </view>

    <view class="block-1">
      <block-header title="您的得分建议"/>
      <view class="img-box" style="margin-top: 80upx" v-html="report.setting.result"/>
    </view>

  </view>
</template>
<script>
import latResult from '@/utils/latResult'
import range from '@/components/common/range'
import blockHeader from '@/components/common/blockHeader'
import circularProgress from '@/components/circular-progress/circular-progress'

export default {
  components: {
    range,
    blockHeader,
    circularProgress
  },
  data() {
    return {
      show: false,
      opts: {
        update: true,
        width: '300px',
        height: '300px',
        padding: [0,0,0,0],
        fontSize: 11,
        legend: {show: false, padding: 0,margin: 0},
        extra: {radar: {border: true, max: 100}}
      },
      report: {},
      lats: [],
      radarData: {
        disableLegend: true,
        categories: [],
        series: [{
          name: "焦虑",
          data: []
        }]
      },
      score: 0,
      percentage: 0,
    }
  },
  methods: {
    initData(data, list) {
      const lats = list.filter(l => l.questionIds)
      let num = 0
      switch (data.order.gaugeType) {
        case 5:
          num = 25
          break
        case 6:
          num = 20
          break
        case 7:
          num = 33.34
          break
      }

      this.score = parseInt(data.order.score)
      this.percentage = Math.min(Math.round(this.score / data.order.gaugeNum * num), 100)

      if (lats.length > 0) {
        this.radarData.series[0].name = this.getGaugeType(data.order.gaugeType)
        lats.forEach((a, index) => {
          // a.score = 0
          if (a.questionIds) {
            const arr = a.questionIds.split(",")
            const res = data.results.filter(b => arr.includes(b.questionsId + '')) || []
            const score = res.reduce((a, c) => a + c.score, 0)
            this.radarData.categories[index] = a.name
            a.score = Math.round(score / res.length * num)
            this.radarData.series[0].data[index] = a.score
            a.title = this.getResultTitle(data.order.gaugeType, a.name, a.score)
          }
        })
        this.lats = lats.sort((a,b) => parseInt(a.y) - parseInt(b.y))
      }

      this.report = data
      this.show = true
    },
    getGaugeType(type) {
      let name = '测评'
      switch (type) {
        case 1:
          name = '普通计算'
          break;
        case 2:
          name = '多维计算'
          break;
        case 3:
          name = 'MBTI'
          break;
        case 4:
          name = 'SDS'
          break;
        case 5:
          name = 'SAS'
          break;
        case 6:
          name = 'SCL-90'
          break;
      }
      return name
    },
    getResultTitle(type, lat, percentage) {
      let title = ''

      if (type === 5) {
        title = latResult.getResultByLat5(lat, percentage)
      } else if(type === 6) {
        title = latResult.getResultByLat6(lat, percentage)
      } else if(type === 7) {
        title = latResult.getResultByLat7(lat, percentage)
      }

      return title
    }
  }
}
</script>
<style lang="scss">
.page {
  background-color: #fff9f7;
  position: relative;
  width: 750upx;
}

.header {
  width: 750upx;
  height: 665upx;
  background: url('/static/evaluation/report.png') 0upx 0upx no-repeat;
  background-size: 750upx 666upx;
  display: flex;
  flex-direction: column;
}

.header-title {
  //width: 280upx;
  height: 56upx;

  color: rgba(255, 255, 255, 1);
  font-size: 40upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  text-align: left;
  line-height: 56upx;
  margin: 41upx 0 0 32upx;
}

.header-report {
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  text-align: left;
  line-height: 37upx;
  margin: 24upx 0 0 32upx;
}

.header-wrapper {
  height: 37upx;
  margin: 20upx 0 450upx 32upx;
}

.header-name {
  width: 200upx;
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  line-height: 37upx;
}

.header-time {
  height: 37upx;
  color: rgba(255, 255, 255, 1);
  font-size: 26upx;
  font-family: PingFangSC-Regular;
  line-height: 37upx;
  margin-left: 60upx;
}

.result {
  box-shadow: 0px 4px 16px 0px rgba(255, 112, 63, 0.160000);
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 20upx;
  width: 686upx;
  margin-top: -380upx;
  margin-left: 32upx;
  padding-bottom: 32upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.circular {
  margin-top: 80upx;
  position: relative;

  .progress-text {
    width: 100%;
    height: 130px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding-top: 40upx;
    position: absolute;
    left: 0;
    top: 0;
  }

  .text-score {
    //width: 57upx;
    height: 78upx;
    color: rgba(255, 112, 63, 1);
    font-size: 56upx;
    font-family: PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    line-height: 78upx;
  }

  .text-score-btn {
    width: 180upx;
    height: 50upx;
    line-height: 50upx;
    background: #FFE4DB;
    border-radius: 25upx;
    color: rgba(255, 112, 63, 1);
    font-size: 30upx;
    text-align: center;
    margin-top: 4upx;
  }
}

.result-header {
  background-color: rgba(255, 168, 82, 1.000000);
  border-radius: 47upx;
  position: absolute;
  left: 175upx;
  top: -45upx;
  width: 366upx;
  height: 90upx;
  border: 2px solid #fff9f7;
  display: flex;
  justify-content: center;
  align-items: center;
}

.result-left {
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  display: flex;
  flex-direction: column;
}

.result-text {
  //width: 216upx;
  height: 50upx;
  color: rgba(255, 255, 255, 1);
  font-size: 36upx;
  font-family: PingFangSC-Semibold;
  font-weight: 600;
  margin-left: 10upx;
}

.result-right {
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 50%;
  width: 12upx;
  height: 12upx;
  margin-left: 10upx;
}

.block-1 {
  box-shadow: 0px 4px 16px 0px rgba(255, 112, 63, 0.160000);
  background-color: rgba(255, 255, 255, 1.000000);
  border-radius: 20upx;
  position: relative;
  width: 686upx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 32upx;
  padding-bottom: 32upx;
}

.block-radar {
  width: 100%;
  height: 498upx;
}

.memo1 {
  color: rgba(51, 51, 51, 1);
  font-size: 36upx;
  font-family: PingFangSC-Medium;
  font-weight: 500;
  margin-top: 80upx;
  padding: 0 10upx;
}

.memo2 {
  width: 622upx;
  color: rgba(119, 119, 119, 1);
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  margin-top: 32upx;
}

.desc {
  width: 622upx;
  color: #333333;
  font-size: 32upx;
  font-family: PingFangSC-Regular;
  text-align: justify;
  line-height: 45upx;
  margin-top: 80upx;

  .desc-title {
    margin-top: 24upx;
    font-weight: bold;
  }
}

.img-box {
  width: calc(100% - 48upx);
  padding-left: 24upx;
  padding-right: 24upx;
  padding-bottom: 24upx;
  overflow: scroll;
  //height: 800upx;
  //margin-top: 80upx;
  font-size: 32upx;

  ::v-deep img {
    width: 100%;
  }
}
</style>