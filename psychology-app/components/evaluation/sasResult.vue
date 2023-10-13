<template>
  <view class="page" v-if="report && report.order && report.setting">
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
      <view class="desc" style="margin-top: 0upx">
        {{ report.setting.memo3 }}
      </view>
    </view>
    <view class="block-1">
      <block-header title="躯体不适"/>
      <range :percentage="perList[0]"/>
      <view class="desc">
        <text class="desc-title">{{ getResultTitle('躯体不适', perList[0]) }}</text>
        <view>
          本因子考察您的焦虑导致的躯体不话的症状，得分越高说明您的症状越明显。在焦虑症早期，通常伴有多种躯体症状，如心慌心悸、胸闷、心前区不适或疼痛，心跳和呼吸次数加快，全身疲之感。导致生活和工作能力下降，简单的日常家务工作变得困难不堪，无法胜任，从而再次加重患者的担忧和焦虑。
        </view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="莫名恐惧"/>
      <range lineBg="#FFEDB2" proBg="#F8CF67" :percentage="perList[5]"/>
      <view class="desc">
        <text class="desc-title">{{ getResultTitle('莫名恐惧', perList[5]) }}</text>
        <view>
          本因子考察您的焦虑导致的莫名恐惧的症状，得分越高说明您的症状越明显。病理性焦虑情绪持续性或发作性时，患者有一种期待性的危险感,感到某种灾难降临，甚至有死亡的感受。患者担心自己会失去控制，可能突然昏倒或“发疯”，整天专注于自己的健康状态，担心疾病再度发作。70%的患者同时伴有忧郁症状，对目前、未来生活缺乏信心和乐趣。有的患者有时会出现情绪激动，失去平衡，经常无故地发怒，与家人争吵对什么事情都看不惯，不满意的情况。
        </view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="神经系统反应"/>
      <range lineBg="#D4E3FF" proBg="#7596DB" :percentage="perList[4]"/>
      <view class="desc">
        <text class="desc-title">{{ getResultTitle('神经系统反应', perList[4]) }}</text>
        <view>
          本因子考察您的焦虑产生的神经系统反应症状得分越高说明您的症状越明显。焦虑症患者的交感和附交感神经系统常常超负荷工作。患者出汗、晕眩、呼吸急促、心跳过快、身体发冷发热、手脚冰凉或发热、胃部难受、大小便过频喉头有阻塞感。
        </view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="运动性不安"/>
      <range lineBg="#C9E8C9" proBg="#96D18D" :percentage="perList[3]"/>
      <view class="desc">
        <text class="desc-title">{{ getResultTitle('运动性不安', perList[3]) }}</text>
        <view>
          本因子考察您的焦虑产生的运动性不安症状，得分越高说明您的症状越明显。患者在性格上比较内向、敏感，缺乏弹性，也就是比较顽固，多半对自己有很高的期望，但往往又因不能达到这个标准而觉得有罪恶感，容易起疑心，贬低自己的能力。
        </view>
      </view>
    </view>
    <view class="block-1">
      <block-header title="社会适应性差"/>
      <range lineBg="#D8E9FA" proBg="#85BCFF" :percentage="perList[2]"/>
      <view class="desc">
        <text class="desc-title">{{ getResultTitle('社会适应性差', perList[2]) }}</text>
        <view>
          本因子考察您的焦虑产生的社会适应性差的情况，得分越高说明您的情况越明显。经常维持高焦虑状态的人比较不受欢迎，其创造力和适应力也较差。他们容易被煽动，不够决断，相当谨慎和缺乏弹性。他们自我概念也很差，以致经常需要依赖大人以及不敢表达心中对人的不快感受所以发现高焦虑学生，较无冒险性，喜欢做白日梦，社会适应不良。
        </view>
      </view>
    </view>

    <view class="block-1">
      <block-header title="您的得分建议"/>
      <view class="img-box" v-html="report.setting.result"/>
    </view>

  </view>
</template>
<script>
import range from '@/components/common/range'
import blockHeader from '@/components/common/blockHeader'
import circularProgress from '@/components/circular-progress/circular-progress'

export default {
  components: {
    range,
    blockHeader,
    circularProgress
  },
  props: {
    report: {
      type: Object,
      default: {},
    }
  },
  data() {
    return {
      radarData: {
        disableLegend: true,
        categories: ["躯体不适", "综合", "社会适应性差", "运动性不安", "神经系统反应", "莫名恐惧"],
        series: [{
          name: "焦虑",
          data: [0, 0, 0, 0, 0, 0]
        }]
      },
      opts: {
        update: true,
        width: '300px',
        height: '300px',
        padding: [0,0,0,0],
        fontSize: 11,
        legend: {show: false, padding: 0,margin: 0},
        extra: {radar: {border: true, max: 64}}
      },
      score: 0,
      percentage: 0,
      perList: [],
    }
  },
  created() {
    this.score = parseInt(this.report.order.score)
    this.percentage = Math.round(this.report.order.score / this.report.order.gaugeNum * 25)

    const list = []
    this.report.results.forEach(item => {
      const index = item.questionsLat - 1
      if (list[index] && list[index].count !== 0) {
        list[index].score += item.score
        list[index].count += 1
      } else {
        list[index] = {
          score: item.score,
          count: 1,
        }
      }
    })

    list.forEach((a, i) => {
      // a.real = Math.round(a.score / this.report.order.gaugeNum * 25)
      // a.mix = Math.round(a.score / a.count * 25) + '%'
      this.perList[i] = Math.round(a.score / a.count * 25)
      this.radarData.series[0].data[i] = a.score
    })

    console.log(list)
  },
  methods: {
    getResultTitle(lat, percentage) {
      let title = ''
      if (percentage < 25) {
        title = `从测评结果来看，您没有${lat}的情况或${lat}的情况较轻。`
      } else if (percentage >= 25 && percentage < 60) {
        title = `从测评结果来看，您的${lat}的情况较明显。`
      } else if (percentage >= 60) {
        title = `从测评结果来看，您的${lat}的情况比较严重。`
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

.range {
  width: 622upx;
  height: 40upx;
  flex-direction: row;
  display: flex;
  align-items: center;
  margin-top: 32upx;
  color: rgba(119, 119, 119, 1);
  font-size: 28upx;

  .range-pro {
    display: flex;
    flex-direction: column;
    margin: 0 24upx;
    width: 348upx;
    height: 24upx;
    background: #FFE4DB;
    border-radius: 16upx;
  }

  .range-progress {
    background-color: rgba(255, 112, 63, 1.000000);
    border-radius: 16upx;
    width: 66upx;
    height: 24upx;
    display: flex;
    flex-direction: column;
  }

  .bg1 {
    background: #FFE4DB;
  }

  .prog1 {
    background: #FF8960;
  }

  .bg2 {
    background: #D4E3FF;
  }

  .prog2 {
    background: #7596DB;
  }

  .bg3 {
    background: #FFEDB2;
  }

  .prog3 {
    background: #F8CF67;
  }

  .bg4 {
    background: #C9E8C9;
  }

  .prog4 {
    background: #96D18D;
  }
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
  margin-top: 80upx;
  font-size: 32upx;

  ::v-deep img {
    width: 100%;
  }
}
</style>