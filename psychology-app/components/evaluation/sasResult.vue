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
      <view class="desc" style="margin-top: 0upx">
        {{ report.setting.memo3 }}
      </view>
    </view>
    <view class="block-1" v-for="item in lats.filter(a => a.y !== -1)">
      <block-header :title="item.name"/>
      <range :lineBg="item.line" :proBg="item.prd" :percentage="item.score"/>
      <view class="desc">
        <text class="desc-title">{{ item.title }}</text>
        <view>
          {{ item.info }}
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
        extra: {radar: {border: true, max: 64}}
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

      if (data.order.gaugeType === 7) {
        this.opts.extra.radar.max = 24
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
            this.radarData.series[0].data[index] = score
            a.score = Math.round(score / res.length * num)
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
        if (percentage < 25) {
          title = `从测评结果来看，您没有${lat}的情况或${lat}的情况较轻。`
        } else if (percentage >= 25 && percentage < 60) {
          title = `从测评结果来看，您的${lat}的情况较明显。`
        } else if (percentage >= 60) {
          title = `从测评结果来看，您的${lat}的情况比较严重。`
        }
      } else if(type === 6) {
        switch (lat) {
          case '躯体化':
            title = percentage <= 40 ? '你没有或者只有轻微的身体不适，包含心血管肠胃道、呼吸、身体疼痛以及焦虑等方面。' : '你在心血管、肠胃道、呼吸、身体疼痛以及焦虑等方面可能存在身体不适的情况。如果你在下方其他因子中也存在问题，那么你的躯体化症状可能是由心理问题引起的，你可以寻求心理咨询师帮助，以便进一步了解你的症状。'
            break
          case '焦虑':
            title = percentage <= 40 ? '你的焦虑程度较弱，你的心态不易焦虑，通常表现为安定的状态。' : '你的焦虑程度较强，你的心态可能比较容易焦虑，易表现出烦躁、不安静和神经过敏的症状，极端时可能导致惊恐发作。'
            break
          case '强迫症状':
            title = percentage <= 40 ? '你的强迫症状不明显，没有或很少出现强迫行为，你对无意义的思想、冲动和行为有明确的认知。' : '你的强迫症状较明显，在平时的生活中您谨小慎微、追求完美、注重细节，当事物没有按照既定程序发展时，会感到紧张焦虑不安。同时可能会有您无法控制的强迫性行为及思想出现，如反复洗手、反复检查等行为，当这些行为无法完成时会感到强烈的不安，从而影响到正常工作和生活。'
            break
          case '抑郁':
            title = percentage <= 40 ? '你的抑郁程度较弱，你的生活态度乐观积极，充满活力，心境愉快，希望你能继续保持你现有的心态。' : '你的抑郁程度较强，你可能有抑郁倾向，主要表现为较持久的情绪低落、闷闷不乐，对外界事物失去兴趣，感觉思维迟缓，言语行为反应速度变慢，偶尔会产生消极悲观的念头。可能会伴有失眠食欲减退等躯体不良症状。建议您尽快寻求咨询师的帮助。'
            break
          case '人际关系敏感':
            title = percentage <= 40 ? '你在人际交往中表现的问题较少，你在人际关系上能应付自如，人际交流自信，胸有成竹，并抱有积极的期待。' : '你在人际交往中可能问题较多，在人际交往中可能会表现出明显的不自在和自卑感，特别与他人比较时更加突出，在人际关系上表现出消极的期待。您很在意人际交往过程他人对您的看法，并且倾向于认为他人对您有消极的态度看法，并为此感到焦虑紧张。'
            break
          case '精神病性':
            title = percentage <= 40 ? '本次测评中本项你的得分较低，代表着你没有明显的精神病性症状。' : '本次测评中本项你的得分较高，代表着你的精神病性症状表现较为明显，你可能有限定不严的精神病性过程的症状表现，建议你咨询心理医生以便进一步了解你的症状。'
            break
          case '偏执':
            title = percentage <= 40 ? '你的偏执情况不明显，你的思维不易往极端方向走，对待事物不易产生敌对、猜疑、妄想、被动体验和夸大等思维。' : '你的偏执情况比较明显，你的思维可能比较偏执，对待事物容易产生敌对、猜疑、妄想、被动体验和夸大的思维。你在平时的生活中可能有固执、敏感多疑、好嫉妒，对批评、失败和挫折非常敏感，不喜欢受到他人的指责和质疑，对他人的行为言语总抱有怀疑的态度的情况。'
            break
          case '敌对':
            title = percentage <= 40 ? '你的脾气很温和，对待事物容易表现出友好的思想、情感和行为，待人友好，不喜欢争论，无破坏行为。' : '你的脾气可能难以控制，对待事物容易表现出敌对的思想、情感和行为，喜欢与人争论。'
            break
          case '恐怖':
            title = percentage <= 40 ? '你不容易产生恐怖心理，在自己出门旅行、空旷场地、人群和交通工具等场景下能正常的交往和活动。' : '你可能容易产生恐怖心理，在自己出门旅行、空旷场地、人群和交通工具等场景下可能会发生恐惧，并伴有明显的躯体症状，通常你会不自觉采取主动回避的方式减除这种不安。'
            break
          case '其他':
            title = percentage <= 40 ? '你的睡眠以及饮食情况表现较好，你没有因为心理问题影响到自己的饮食起居。' : '你的睡眠以及饮食情况表现较差，你可能因为心理问题影响到了自己的饮食起居。'
            break
        }
      } else if(type === 6) {
        switch (lat) {
          case '躯体化':
            title = percentage <= 40 ? '你没有或者只有轻微的身体不适，包含心血管肠胃道、呼吸、身体疼痛以及焦虑等方面。' : '你在心血管、肠胃道、呼吸、身体疼痛以及焦虑等方面可能存在身体不适的情况。如果你在下方其他因子中也存在问题，那么你的躯体化症状可能是由心理问题引起的，你可以寻求心理咨询师帮助，以便进一步了解你的症状。'
            break
          case '焦虑':
            title = percentage <= 40 ? '你的焦虑程度较弱，你的心态不易焦虑，通常表现为安定的状态。' : '你的焦虑程度较强，你的心态可能比较容易焦虑，易表现出烦躁、不安静和神经过敏的症状，极端时可能导致惊恐发作。'
            break
          case '强迫症状':
            title = percentage <= 40 ? '你的强迫症状不明显，没有或很少出现强迫行为，你对无意义的思想、冲动和行为有明确的认知。' : '你的强迫症状较明显，在平时的生活中您谨小慎微、追求完美、注重细节，当事物没有按照既定程序发展时，会感到紧张焦虑不安。同时可能会有您无法控制的强迫性行为及思想出现，如反复洗手、反复检查等行为，当这些行为无法完成时会感到强烈的不安，从而影响到正常工作和生活。'
            break
          case '抑郁':
            title = percentage <= 40 ? '你的抑郁程度较弱，你的生活态度乐观积极，充满活力，心境愉快，希望你能继续保持你现有的心态。' : '你的抑郁程度较强，你可能有抑郁倾向，主要表现为较持久的情绪低落、闷闷不乐，对外界事物失去兴趣，感觉思维迟缓，言语行为反应速度变慢，偶尔会产生消极悲观的念头。可能会伴有失眠食欲减退等躯体不良症状。建议您尽快寻求咨询师的帮助。'
            break
          case '人际关系敏感':
            title = percentage <= 40 ? '你在人际交往中表现的问题较少，你在人际关系上能应付自如，人际交流自信，胸有成竹，并抱有积极的期待。' : '你在人际交往中可能问题较多，在人际交往中可能会表现出明显的不自在和自卑感，特别与他人比较时更加突出，在人际关系上表现出消极的期待。您很在意人际交往过程他人对您的看法，并且倾向于认为他人对您有消极的态度看法，并为此感到焦虑紧张。'
            break
          case '精神病性':
            title = percentage <= 40 ? '本次测评中本项你的得分较低，代表着你没有明显的精神病性症状。' : '本次测评中本项你的得分较高，代表着你的精神病性症状表现较为明显，你可能有限定不严的精神病性过程的症状表现，建议你咨询心理医生以便进一步了解你的症状。'
            break
          case '偏执':
            title = percentage <= 40 ? '你的偏执情况不明显，你的思维不易往极端方向走，对待事物不易产生敌对、猜疑、妄想、被动体验和夸大等思维。' : '你的偏执情况比较明显，你的思维可能比较偏执，对待事物容易产生敌对、猜疑、妄想、被动体验和夸大的思维。你在平时的生活中可能有固执、敏感多疑、好嫉妒，对批评、失败和挫折非常敏感，不喜欢受到他人的指责和质疑，对他人的行为言语总抱有怀疑的态度的情况。'
            break
          case '敌对':
            title = percentage <= 40 ? '你的脾气很温和，对待事物容易表现出友好的思想、情感和行为，待人友好，不喜欢争论，无破坏行为。' : '你的脾气可能难以控制，对待事物容易表现出敌对的思想、情感和行为，喜欢与人争论。'
            break
          case '恐怖':
            title = percentage <= 40 ? '你不容易产生恐怖心理，在自己出门旅行、空旷场地、人群和交通工具等场景下能正常的交往和活动。' : '你可能容易产生恐怖心理，在自己出门旅行、空旷场地、人群和交通工具等场景下可能会发生恐惧，并伴有明显的躯体症状，通常你会不自觉采取主动回避的方式减除这种不安。'
            break
          case '其他':
            title = percentage <= 40 ? '你的睡眠以及饮食情况表现较好，你没有因为心理问题影响到自己的饮食起居。' : '你的睡眠以及饮食情况表现较差，你可能因为心理问题影响到了自己的饮食起居。'
            break
        }
      } else if(type === 7) {

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