<template>
  <view class="page" v-if="report && report.order && report.setting">
    <mbti v-if="report.order && report.order.gaugeType === 3" :report="report"/>
    <sds v-if="report.order && report.order.gaugeType === 4" :report="report"/>
    <sas v-if="report.order && [5,6].includes(report.order.gaugeType)" :report="report" :lats="lats" :radarData="radarData"/>
    <normal v-if="report.order && ![3,4,5,6].includes(report.order.gaugeType)" :report="report"/>
    <recommend/>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
                        @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import serve from '@/server/evaluation/question'
import mbti from '@/components/evaluation/mbtiResult'
import sas from '@/components/evaluation/sasResult'
import sds from '@/components/evaluation/sdsResult'
import normal from '@/components/evaluation/normalResult'
import recommend from '@/components/consult/recommend'

export default {
  components: {
    mbti,
    sas,
    sds,
    normal,
    recommend
  },
  data() {
    return {
      orderId: null,
      userInfo: {},
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
    }
  },
  async created() {
    this.orderId = utils.getParam(location.href, "orderId")
    const data = await serve.getReport(this.orderId)
    if ([5,6].includes(data.order.gaugeType)) {
      let lats = await serve.getLats(data.order.gaugeId)
      lats = lats.filter(l => l.questionIds)
      const num = data.order.gaugeType ===  5 ? 25 : 20

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
    }

    this.report = data
    utils.share(data.order.gaugeTitle, '', data.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + data.order.gaugeId)
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
  },
  methods: {
    // 登录
    async confirmLogin() {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
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
      } else {
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
      }

      return title
    }
  }
}
</script>
<style lang="scss">

</style>