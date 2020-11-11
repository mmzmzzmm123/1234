<template>
  <div v-if="show" class="sign-contract-container">
    <div class="contract-title">合同简要</div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="服务方" style="margin: 0">
        <span>深圳市胜唐科技有限公司</span>
      </el-form-item>
      <el-form-item label="导师" style="margin: 0">
        <span>{{form.tutor}}</span>
      </el-form-item>
      <el-form-item label="服务费" style="margin: 0">
        <span>￥{{form.amount}}</span>
      </el-form-item>
      <el-form-item label="服务时长" style="margin: 0">
        <span>{{form.serveTimeStr}}</span>
      </el-form-item>
      <el-form-item label="服务承诺" style="margin: 0">
        <span>健康吃瘦{{form.servePromise}}斤</span>
      </el-form-item>
      <el-form-item v-if="form.remark" label="备注" style="margin: 0">
        <span>{{form.remark}}</span>
      </el-form-item>
      <el-form-item label="姓名" prop="signName">
        <el-input v-model="form.signName" placeholder="请输入客户姓名"/>
      </el-form-item>
      <el-form-item label="证件号" prop="cusId">
        <el-input v-model="form.cusId" placeholder="请输入证件号"/>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入电话"/>
      </el-form-item>
    </el-form>
    <div>
      <el-checkbox v-model="read" @change="handleRadioChange" style="margin-bottom: 12px">已阅读合同相关
      </el-checkbox>
      <span class="detail" @click="dialogVisible = true">详情</span>
    </div>
    <el-button type="primary" style="width: 100%" @click="submitForm" :disabled="!read">提交签约</el-button>
    <el-dialog
      title="营养减脂服务合同"
      :visible.sync="dialogVisible"
      width="90%"
      custom-class="contract_dialog"
      center>
      <div class="dialog-detail">
        <p>甲方：胜唐体控（深圳市胜唐科技有限公司）</p>
        <p>乙方：<b>{{form.signName}}</b></p>
        <p>甲乙双方本着诚信合作、互惠互利的原则，依据中华人民共和国有关法律，经友好协商，现就胜唐体控减肥咨询指导服务合作相关事宣达成一致协议，以兹共同遵守。
          若乙方为未成年，应由乙方法定监护人代签本合同，乙方享受同等法律权益。</p>
        <p>
          第一条 甲方资质与服务保证
        <div class="line-rule">1、甲方经由工商部门注册、合法经营的正规公司。</div>
        <div class="line-rule">2、甲方在合约期内为乙方提供体重管理专家导师 <b>{{form.tutor}}</b> 提供营养减脂服务。</div>
        <div class="line-rule">3、甲方按与乙方约定事项及服务内容为乙方提供减脂服务，通过调节一日三餐、不使用任何仪器、针药、不使用医疗类药物等。</div>
        <div class="line-rule">4、提供专业营养指导咨询服务。</div>
        <div class="line-rule">5、服务标准</div>
        <div style="margin-left: 24px">5.1、甲方为乙方每周提供一对一有效咨询时间6日，每日咨询服务时间为早10:00——晚20:30。每日服务10.5小时（法定节假日除外）</div>
        <div style="margin-left: 24px">5.2、享受营养学基础知识学习，肥胖与生活习惯知识学习，享受营养师亲自培训初级自我体重管理知识。</div>
        <div style="margin-left: 24px">5.3、甲方为乙方定制营养减脂饮食方案，7天为一期，每期结束前1——2天，更新发送下一期饮食减脂方案。</div>
        <div style="margin-left: 24px">5.4、甲方对乙方所做出的承诺不得超出力所能及的范围，不得夸大、吹嘘、作假服务效果及疗程特性。</div>
        <div style="margin-left: 24px">5.5、甲方需保证提供服务的导师具备相关的服务资质证书。</div>
        <div style="margin-left: 24px">5.6、甲方为乙方提供的服务方式主要为网络线上进行指导服务，不涉及线下服务内容。</div>
        <div style="margin-left: 24px">5.7、乙方不能在家佐餐，需要进行外食指导，甲方有责任及义务为乙方提供对应的指导。</div>
        <div style="margin-left: 24px">5.8、乙方自订购服务日起，享有单一营养师的指导及咨询服务，如需中途更换指导老师，需在当期方案执行结束至少3日内告知，以便于更换指导。</div>
        <div style="margin-left: 24px">5.9、甲方每日会敦促乙方进行服务效果反馈，一日三餐需进行打卡。</div>
        <div style="margin-left: 24px">5.10、甲方在接收到乙方发送咨询需求3.5小时内需及时为乙方解答（周日及法定节假日除外）。</div>
        </p>

        <p>第二条 合作内容及费用
        <div class="line-rule">经甲乙双方协商确定，乙方向甲方购买 <b>{{form.serveTimeStr}}</b> “胜唐体控瘦身指导服务”（以下简称服务）。
          经甲乙双方协商一致，确定乙方向甲方支付服务费用为人民币 <b>{{form.amount}}</b>，大写：<b>{{form.amountUpper}}</b>。<b>{{form.remark ?
            `（备注：${form.remark}）`:''}}</b>
        </div>
        </p>

        <p>第三条 服务期约定
        <div class="line-rule">1、服务时间阶段（服务指导期、服务跟踪期、免费咨询期）具体安排，由甲方根据乙方的实际情况与乙方协商确定。</div>
        <div class="line-rule">2、因不可抗拒的因素（如意外怀孕等）而暂停服务指导，其后续服务期限由甲乙方协商确定。</div>
        <div class="line-rule">3、减肥需要双方的配合，在不是甲方技术问题的情况下，乙方不执行营养师的指导导致没有达到预期效果，责任由乙方承担。根据个人体质不同，在调理营养健康的前提下，健康吃瘦 <b>{{form.servePromise}}</b>
          斤
        </div>
        <div class="line-rule">
          4、乙方应按甲方制定的饮食方案来饮食，承诺在未与甲方沟通前，不得擅自吃方案之外的高热量、高油、高盐、高糖等不利于健康和减脂的饮食，服务期内擅自暴食导致体重上涨，次数超过3次视为乙方主动放弃营养减脂，且甲方无需免费延长服务时间。
        </div>
        <div class="line-rule">
          5、乙方在一个服务期内享有2次暂停服务的机会，在暂停前应与甲方沟通，确认暂停前已减重斤数，累积到总减重结果中。在暂停结束后，自新体重开始，甲方服务乙方继续完成承诺的减重斤数，即：包瘦斤数 - 暂停前已成功减重数。
        </div>
        <div class="line-rule">6、乙方隐瞒病情或身体情况误导营养师的，造成的后果由乙方承担并且甲方不予退款。</div>
        <div class="line-rule">
          7、乙方同意并且理解甲方定制营养方案付出的时间和智力成本，乙方在第一周营养方案定制交付之前申请退款的可退款90%，在第一周营养方案定制交付之后因个人原因，非不可抗力因素则不予退款。
        </div>
        <div class="line-rule">
          8、若乙方具有特殊病史、慢病疾病、食用减肥药物或产品、使用激素药物、中药及其他疗程性药物调理，需在购买减脂服务前告知甲方，以便于合理安排减脂计划，若乙方未告知甲方相关的疾病史以及药物使用情况，因此造成的负面效果由乙方自行承担。
        </div>
        <div class="line-rule">9、甲方在乙方告知所有身体情况及药物服用情况，甲方仍未做出合理安排，对乙方的身体及心理造成影响时，乙方可申请扣除已进行服务期外相应的服务款项，退还未进行服务期对应款项。
        </div>
        </p>

        <p>公司地址：深圳市宝安区西乡街道宝民二路缤纷时代广场3楼318</p>
        <p>联系电话：0755-23213903</p>
      </div>
      <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleConfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>

  import {getFile, signContract} from "@/api/custom/contract";
  import {digitUppercase, validatorIDCard} from "../../../utils/ruoyi";

  export default {
    name: 'sign',
    data() {

      const checkSignName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('姓名不能为空'))
        }
        callback();
      }

      const checkPhone = (rule, value, callback) => {
        const phoneReg = /^1[3|4|5|6|7|8|9][0-9]{9}$/
        if (!value) {
          return callback(new Error('电话号码不能为空'))
        }
        setTimeout(() => {
          // Number.isInteger是es6验证数字是否为整数的方法,实际输入的数字总是识别成字符串
          // 所以在前面加了一个+实现隐式转换
          if (!Number.isInteger(+value)) {
            callback(new Error('请输入数字值'))
          } else {
            if (phoneReg.test(value)) {
              callback()
            } else {
              callback(new Error('电话号码格式不正确'))
            }
          }
        }, 100)
      }

      const checkcusId = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('证件号码不能为空'))
        }
        setTimeout(() => {
          const {code, msg} = validatorIDCard(value);
          if (code === 1) {
            callback()
          } else {
            return callback(new Error(msg))
          }
        }, 100)
      }

      return {
        show: false,
        form: {},
        read: false,
        dialogVisible: false,
        serveTimeIdOption: [
          {label: '7天', value: 7},
          {label: '1个月', value: 30},
          {label: '2个月', value: 60},
          {label: '3个月', value: 90},
          {label: '4个月', value: 120},
          {label: '5个月', value: 150},
          {label: '6个月', value: 180},
          {label: '7个月', value: 210},
          {label: '8个月', value: 240},
          {label: '9个月', value: 270},
          {label: '10个月', value: 300},
          {label: '11个月', value: 330},
          {label: '12个月', value: 360},
        ],
        rules: {
          signName: [
            {required: true, trigger: "blur", validator: checkSignName}
          ],
          phone: [
            {required: true, trigger: "blur", validator: checkPhone}
          ],
          cusId: [
            {required: true, trigger: "blur", validator: checkcusId}
          ]
        }
      }
    },
    mounted() {
      document.title = this.$route.meta.title
    },
    created() {
      this.getContract();
    },
    methods: {
      getContract() {
        const {pathname} = window.location;
        const id = pathname.substring(pathname.lastIndexOf('/') + 1);
        // console.log(id)
        getFile(id).then(result => {
          if (result.url) {
            this.$router.push(result.url);
          } else if (result.data) {
            this.form = result.data;
            this.form.amount = parseInt(result.data.amount);
            this.form.amountUpper = digitUppercase(this.form.amount);
            this.form.serveTimeStr = this.serveTimeIdOption.find(obj => obj.value === parseInt(result.data.serveTime)).label
            this.show = true;
          }
        })
      },
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            signContract(this.form).then(result => {
              if (result.code === 200) {
                window.location.href = window.location.origin + result.url;
              }
            });
          }
        })
      },
      handleRadioChange(val) {
        this.read = val;
      },
      handleConfirm() {
        this.dialogVisible = false;
        this.read = true;
      },

    }
  }
</script>

<style lang="scss" scoped>
  .sign-contract-container {
    font-family: Helvetica Neue, Helvetica, Arial, Microsoft Yahei, Hiragino Sans GB, Heiti SC, WenQuanYi Micro Hei, sans-serif;
    padding: 32px;
    /*background-color: rgb(240, 242, 245);*/
    position: relative;

    .contract-title {
      text-align: center;
      font-size: 20px;
      margin-bottom: 32px;
    }


    .detail {
      color: #0066cc;
      cursor: pointer;
      font-size: 14px;
    }

    .contract_dialog {


      .dialog-detail {
        height: 60vh;
        margin-top: -12px;
        margin-bottom: -12px;
        overflow: auto;
      }
    }

    .line-rule {
      margin: 4px 0;
      line-height: 18px;
    }

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width: 1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>
