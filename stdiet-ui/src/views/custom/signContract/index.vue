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
      <el-form-item label="调理项目" style="margin: 0">
        <span>{{form.projectName}}</span>
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
      <el-form-item label="证件类型">
        <el-select v-model="idType">
          <el-option
            v-for="opt in idTypeOptions"
            :key="opt.value"
            :label="opt.label"
            :value="opt.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="证件号" prop="cusId">
        <el-input v-model="form.cusId" placeholder="请输入证件号"/>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" placeholder="请输入电话"/>
      </el-form-item>
    </el-form>
    <div>
      <el-checkbox v-model="read" @change="handleRadioChange" style="margin-bottom: 12px">已阅读合同相关
      </el-checkbox>
      <span class="detail" @click="dialogVisible = true" style="margin-left: 10px;">合同详情</span>
    </div>
    <el-button type="primary" style="width: 100%" @click="submitForm" :disabled="!read">提交签约</el-button>
    <el-dialog
      :title="titleArray[this.form.projectId]"
      :visible.sync="dialogVisible"
      width="90%"
      custom-class="contract_dialog"
      center>
      <div class="dialog-detail">
        <div  v-show="this.form.projectId == 0">
        <p>
          <div>甲方：胜唐体控（深圳市胜唐科技有限公司）</div>
          <div>公司地址：深圳市宝安区西乡街道宝民二路缤纷时代广场3楼318</div>
          <div>联系电话：0755-23213903</div>
        </p>
        <p>
          <div>乙方：<b>{{form.signName}}</b></div>
          <div>身份证号：<b>{{form.cusId}}</b></div>
          <div>联系电话：<b>{{form.phone}}</b></div>
          </p>
          <p>
          <div>甲乙双方本着诚信合作、互惠互利的原则，依据中华人民共和国有相关法律，经友好协商，现就胜唐体控减肥咨询指导服务合作相关事宣达成一致协议，以兹共同遵守。</div>
          <div>若乙方为未成年，由应由乙方法定监护人代签本合同，乙方享受同等法律权益。</div>
          </p>
          <p>
            第一条 甲方资质与服务保证
          <div class="line-rule">1、甲方系经由工商部门注册、合法经营的正规公司，具备提供相关咨询服务的法定资质。</div>
          <div class="line-rule">2、甲方在合约期内为乙方安排营养师团队提供营养减脂服务。</div>
          <div class="line-rule">3、甲方按与乙方约定事项及服务内容，通过定制营养减脂饮食方案为乙方提供减脂服务，服务中不使用任何仪器、针药、不使用医疗类药物等。</div>
          <div class="line-rule">4、甲方为乙方提供专业营养指导咨询服务。</div>
          <div class="line-rule">5、服务标准</div>
          <div style="margin-left: 24px">5.1、甲方为乙方每周提供一对一有效咨询时间6日，每日咨询服务时间为早10:00—晚19:00。每日服务9小时（法定节假日除外）</div>
          <div style="margin-left: 24px">5.2、乙方可享受营养学基础知识学习，肥胖与生活习惯知识学习，享受营养师亲自培训初级自我体重管理知识。</div>
          <div style="margin-left: 24px">5.3、甲方为乙方定制营养减脂饮食方案，7天为一期，每期结束前1—2天，更新发送下一期饮食减脂方案。</div>
          <div style="margin-left: 24px">5.4、甲方对乙方所做出的承诺不得超出力所能及的范围，不得夸大、吹嘘、作假服务效果，双方明确，甲方只提供咨询服务，不含任何医疗行为。</div>
          <div style="margin-left: 24px">5.5、甲方需保证提供服务的导师具备相关的服务资质证书。</div>
          <div style="margin-left: 24px">5.6、甲方为乙方提供的服务方式主要为网络线上进行指导服务，不涉及线下服务内容。</div>
          <div style="margin-left: 24px">5.7、乙方不能在家佐餐，需要进行外食指导，甲方有责任及义务为乙方提供对应的指导。</div>
          <div style="margin-left: 24px">5.8、乙方自订购服务日起，享有单一营养师的指导及咨询服务，如需中途更换指导老师，需在当期方案执行结束至少3日内告知，以便于更换指导。</div>
          <div style="margin-left: 24px">5.9、甲方每日会敦促乙方进行服务效果反馈，一日三餐需进行打卡。</div>
          <!--<div style="margin-left: 24px">5.10、甲方在接收到乙方发送咨询需求3.5小时内需及时为乙方解答（周日及法定节假日除外）。</div>-->
          </p>

          <p>第二条 合作内容及费用
          <div class="line-rule">经甲乙双方协商确定，乙方向甲方购买 <b>{{form.serveTimeStr}}</b> “胜唐体控瘦身指导服务”（以下简称服务）。
            经甲乙双方协商一致，确定乙方向甲方支付服务费用为人民币 <b>{{form.amount}}</b>元，大写：<b>{{form.amountUpper}}</b>。
          </div>
          <div><b>{{form.remark ? `（备注：${form.remark}）`:''}}</b></div>
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
          <div class="line-rule">9、甲方在乙方告知所有身体情况及药物服用情况，甲方仍未做出合理安排，对乙方的身体及心理造成影响时，乙方可申请扣除已进行服务期外相应的服务款项，退还未进行服务期对应款项。</div>
          </p>

          <p>第四条 其他
          <div class="line-rule">1、如因本合同发生争议，双方应协商解决，如协商不成，双方可向甲方所在地法院提起诉讼解决。</div>
          <div class="line-rule">2、本合同经双方线上签字盖章后生效。</div>
          </p>
        </div>

        <div v-show="this.form.projectId != 0">
          <p>
          <p>
          <div>服务公司（以下简称甲方）：{{form.companyName}}</div>
          <div>法定代表人：{{form.companyLegalPerson}}</div>
          </p>
          <p>
          <div>用户（以下简称乙方）：<b>{{form.signName}}</b></div>
          <div>身份证号：<b>{{form.cusId}}</b></div>
          <div>联系电话：<b>{{form.phone}}</b></div>
          </p>
          <p>
          <div>经甲乙方双方协商同意，签定服务合同，在合同有效期间，甲、乙双方必须遵守国 家法律、法规，以保护甲乙双方合法权益不受侵犯。</div>
          </p>
          <p>
            一、本合同服务期限自合同成立并生效之日开始，至 <b>{{form.expireTime}}</b> 日终止。
          </p>
          <p>
            二、服务费： <b>{{form.amount}}</b> 元人民币（大写： <b>{{form.amountUpper}}</b>）。
          </p>
          <p>
            三、支付方式
          <div class="line-rule">1.乙方一次性付清服务费用以人民币形式通过银行转账或其他支付向甲方支付共计 <b>{{form.serveTime/30}}</b> 个月服务费。</div>
          <div class="line-rule">2.甲方账户信息<br>
            <div style="padding-left: 10px;">甲方名称：{{form.companyName}}</div>
            <div style="padding-left: 10px;">开户行：{{form.bankName}}</div>
            <div style="padding-left: 10px;">银行账号：{{form.bankAccount}}</div>
          </div>
          <div class="line-rule">
            3. 墨迹商城胜唐体控收款
          </div>
          <div class="line-rule">
            4. 微信转账与支付宝转账
          </div>
          </p>
          <p>
            四、服务事项
          <div class="line-rule">1.乙方需提供以下筛查报告<br>
            <div style="padding-left: 10px;">1).{{reportcontentArray[this.form.projectId]}}</div>
            <div style="padding-left: 10px;">2）.过往疾病史病历、在使用或服用药物。</div>
            <div style="padding-left: 10px;">3）.过往是否使用过激素类药物或保健药品，附加详细说明。 </div>
          </div>
          <div class="line-rule">2、服务时间<br>
            <div style="padding-left: 10px;">1).在指导调理期内，专业营养师的指导时间为一天 <b>{{form.hourDay}} </b>小时，一周 <b>{{form.dayWeek}}</b> 天。 </div>
            <div style="padding-left: 10px;">2).营养师回复消息时间不超过 <b>{{form.replyIntervalTime}}</b> 小时，国家法定节假日期间暂停服务。</div>
            <div style="padding-left: 10px;">3).每日服务时间为：早9：30——12：00，晚13：30——19：00</div>
          </div>
          <div class="line-rule">3、服务指导方式<br>
            <div style="padding-left: 10px;">1).以线上饮食指导为主，线下指导可进行预约至甲方办公所在地进行面对面咨询 指导。 </div>
            <div style="padding-left: 10px;">2).线上指导方式为添加指定营养师{{form.tutor}}联系方式（微信），或至线下咨询。 </div>
            <div style="padding-left: 10px;">3).一对一指导定制乙方可以指定甲方营养师团队任意营养师，做咨询指导服务， 若需要多位营养师同时进行指导，相关服务费用按标准收取。 </div>
            <div style="padding-left: 10px;">4).在指定服务期内，每日的饮食指导食谱，按周发送，每周七天，每天包含三餐 的饮食指导。</div>
            <div style="padding-left: 10px;">5).指导服务周期自合同签订之日起，指导范围不包括药物指导，医用级配方，处 方性，涉及医疗及专业药学范围。 </div>
          </div>
          <div class="line-rule">4、服务效果及保障<br>
            <div style="padding-left: 10px;">1).可改善效果：<br>{{changeContentArray[this.form.projectId]}}</div>
            <div style="padding-left: 10px;">2).预期调理结果：
              <template v-for="(item,index) in anticipateResultArray[this.form.projectId]">
                <br>{{item}}
              </template>
            </div>
            <div style="padding-left: 10px;">3).调理意义：<br>{{significanceArray[this.form.projectId]}}</div>
            <div style="padding-left: 10px;">4).需调理周期<b> {{form.serveTime/30}}</b> 个月</div>
            <div style="padding-left: 10px;">5).服务暂停:<br>乙方因个人原因无法按照指导服务进行调理时，应当提前一周向甲 方申请暂停服务，暂停期间不计入有效服务周期内。</div>
          </div>
          </p>

          <p>
            五、争议及提前结束服务
          <div class="line-rule">1.服务时间阶段（服务指导期、服务跟踪期、免费咨询期以下称为服务时间阶段） 具体安排，由甲方根据乙方的实际情况与乙方协商确定。</div>
          <div class="line-rule">2.如不可抗拒的因素（如意外怀孕等）而暂停服务指导，其后续服务期限由甲乙 双方协商确定。</div>
          <div class="line-rule">3.若非甲方技术问题的情况下，乙方未执行营养师的指导，因此未达到预期效果， 甲方不进行延期服务。 </div>
          <div class="line-rule">4.乙方未按照甲方为其制定方案执行，所带来的的后果由乙方自行承担，甲方不 承担责任。 </div>
          <div class="line-rule">5.乙方在服务期内享有 2 次暂停服务的机会，暂停前应与甲方沟通。暂停结束后， 重新与甲方汇报当前身体状况。 </div>
          <div class="line-rule">6.乙方隐瞒病情或身体情况误导营养师，造成的后果由乙方自行承担且甲方不予 退款。</div>
          <div class="line-rule">7.乙方同意且理解甲方定制营养方案付出的时间和智力成本，乙方在第一周营养 方案定制交付之前申请退款可退款 90%。在第一周营养方案定制交付之后，因个 人原因不履行本合同的，则不予退款。 </div>
          <div class="line-rule">8.若乙方具有特殊病史或使用激素药物、中药及其他疗程性药物等影响甲方提供 服务的情况，需在购买调理服务前告知甲方，以便于合理安排调理计划，若乙方 未告知甲方相关的疾病史以及药物使用情况，因此造成的负面效果由乙方自行承 担。 </div>
          <div class="line-rule">9.甲方在乙方告知所有身体情况及药物服用情况，甲方仍未做出合理安排，对乙 方的身体及心理造成影响时，乙方可申请扣除已进行服务期外相应的服务款项， 退还未进行服务期对应款项。
          </div>
          </p>
          <p>
            六、 保密条款
          <div class="line-rule">1.甲方为乙方的资料保密，包括：姓名、性别、身份证号、联系电话、过往病 史、过敏史等。 </div>
          <div class="line-rule">2.甲方不可把资料泄露给第三方，只允许把信息透露给甲方相对应负责人。</div>
          <div class="line-rule">3.乙方为甲方的营养方案保密，包括：一对一定制方案、体检安排、文字或 语音解答、各类指导等。</div>
          <div class="line-rule">4.乙方不可把资料泄露给第三方，若泄露给他人，造成的损失由乙方承担。 </div>
          <div class="line-rule">5.乙方不得为以下行为： <br>
            <div style="padding-left: 10px;">1）.将甲方资料提供给他人阅读、复制、传递</div>
            <div style="padding-left: 10px;">2）.未经甲方授权，以甲方的名义或以甲方员工身份对外发布； </div>
            <div style="padding-left: 10px;">3）.将甲方资料以口头、书面或者其他形式直接或间接的传递给甲方的竞 争对手。 </div>
          </div>
          <div class="line-rule">6.若乙方违反保密义务，给甲方造成名誉损失或经济损失，需承担赔偿责任。
          </div>
          </p>
          <p>
            七、合同签订与解除
          <div class="line-rule">1.本合同经甲乙双方线上签订电子版之日起生效。</div>
          </p>
          <p>
            八、其他
          <div class="line-rule">1.甲乙双方任何一方违约，需向守约方赔偿2个月的服务费，如果违约金不足以弥 补对方损失的，违约方还需补足守约方的实际损失。</div>
          <div class="line-rule">2.因履行本合同发生的争议，经友好协商不能解决的，协商不能解决的，任何一方 均可向甲方（或乙方）住所地有管辖权的人民法院提起诉讼。</div>
          <div class="line-rule">3.本合同一式二份，甲方执一份乙方执一份，具有同等法律效力。本合同未经事宜， 双方可以签订补充协议，补充协议与原合同约定不一致的，以补充协议为准。</div>
          </p>
        </div>


      </div>
      <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleConfirm">确 定</el-button>
      </span>
    </el-dialog>

  </div>

</template>

<script>
import { getFile, signContract } from "@/api/custom/contract";
import { digitUppercase, validatorIDCard } from "../../../utils/ruoyi";
import dayjs from "dayjs";

export default {
  name: "sign",
  data() {
    const checkSignName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("姓名不能为空"));
      }
      callback();
    };

    const checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|6|7|8|9][0-9]{9}$/;
      const hkPhoneReg = /^(5|6|8|9)\d{7}$/;
      if (!value) {
        return callback(new Error("电话号码不能为空"));
      }
      setTimeout(() => {
        // Number.isInteger是es6验证数字是否为整数的方法,实际输入的数字总是识别成字符串
        // 所以在前面加了一个+实现隐式转换
        if (!Number.isInteger(+value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (phoneReg.test(value) || hkPhoneReg.test(value)) {
            callback();
          } else {
            callback(new Error("电话号码格式不正确"));
          }
        }
      }, 100);
    };

    const checkcusId = (rule, value, callback) => {
      if (!value) {
        callback();
        //return callback(new Error('证件号码不能为空'))
      }
      setTimeout(() => {
        const { code, msg } = validatorIDCard(value, this.idType);
        if (code === 1) {
          callback();
        } else {
          return callback(new Error(msg));
        }
      }, 100);
    };

    return {
      show: false,
      form: {},
      read: false,
      dialogVisible: false,
      idType: 1,
      idTypeOptions: [
        { label: "身份证", value: 1 },
        { label: "港澳身份证", value: 2 },
        { label: "台湾身份证", value: 3 },
        { label: "护照", value: 4 },
        { label: "军官证", value: 5 },
      ],
      serveTimeIdOption: [
        { label: "7天", value: 7 },
        { label: "1个月", value: 30 },
        { label: "2个月", value: 60 },
        { label: "3个月", value: 90 },
        { label: "4个月", value: 120 },
        { label: "5个月", value: 150 },
        { label: "6个月", value: 180 },
        { label: "7个月", value: 210 },
        { label: "8个月", value: 240 },
        { label: "9个月", value: 270 },
        { label: "10个月", value: 300 },
        { label: "11个月", value: 330 },
        { label: "12个月", value: 360 },
        { label: "13个月", value: 390 },
        { label: "14个月", value: 420 },
        { label: "15个月", value: 450 },
        { label: "16个月", value: 480 },
        { label: "17个月", value: 510 },
        { label: "18个月", value: 540 },
      ],
      rules: {
        signName: [
          { required: true, trigger: "blur", validator: checkSignName },
        ],
        phone: [{ required: true, trigger: "blur", validator: checkPhone }],
        cusId: [{ required: false, trigger: "blur", validator: checkcusId }],
      },
      titleArray: [
        "营养减脂服务合同",
        "月经不调调理指导服务合同",
        "多囊卵巢综合症调理指导服务合同",
        "备孕营养调理指导服务合同",
        "产后调理服务合同",
        "高血压调理服务合同",
        "高血糖调理服务合同",
        "高血脂调理服务合同",
        "高尿酸服务合同",
        "营养性贫血服务合同",
        "消化系统管理服务合同",
        "心脑血管系统服务合同",
      ],
      //需要提交的提交报告内容
      reportcontentArray: [
        "",
        "女性激素六项、促卵泡激素（FSH）、促黄体生成素（LH）、雌二醇(E2)、 睾酮（T）、孕酮(PRO)、垂体泌乳素(PRL)。 ",
        "女性激素六项、促卵泡激素（FSH）、促黄体生成素（LH）、雌二醇(E2)、 睾酮（T）、孕酮(PRO)、垂体泌乳素(PRL)、子宫附件彩超。 ",
        "男性激素六项、女性激素六项、衣原体抗原、优生五项、AMH（抗缪勒管激素）、 抗精子抗体、抗子宫内膜抗体、抗卵巢抗体、血常规（五分类）、贫血三项。 ",
        "（遵医嘱检查）。",
        "血脂四项、颈动脉彩超、血压（BP）、同型半胱氨酸（HCY）。 ",
        "血糖、糖化血红蛋白(GHB)、胰岛素测定(INS)、C肽、肾功能三项、眼底检 查、同型半胱氨酸（HCY）。 ",
        "血脂四项、血压（BP）、颈动脉彩超、同型半胱氨酸（HCY）。",
        "肾功能三项、双肾彩超、尿常规、尿微量白蛋白定量。 ",
        "贫血三项、血常规（五分类）、肝功能五项、双肾彩超。 ",
        "碳13呼气试验、胃功能三项、G-17（胃泌素17）。 ",
        "血脂四项、血压（BP）、同型半胱氨酸（HCY）、心脏彩超、心功能三项、 颈动脉彩超。 ",
      ],
      //可改善效果
      changeContentArray: [
        "",
        "平衡女性性激素分泌 改善痛经、月经紊乱等症状 改善气色、 延缓衰老。",
        "平衡体内激素、增强自身免疫力 减轻体重、改善月经紊乱。 ",
        "改善双方体质，改善妇科问题，增加受孕几率 预防妊娠糖尿病及孕期高血压。 ",
        "改善乳汁质量、合理控制体重、改善缺钙、脱发等症状 ，预 防骨质疏松。",
        "调节血压，保护心、脑、肝、肾器官 改善头晕，头痛，疲劳 等症状 预防中风、粥样动脉硬化、血栓性疾病等心脑血管疾病 ",
        "减少生活饮食习惯血糖升高的诱因 ，改 善血糖水平。预防营养失衡 ，提升机体抵抗力。保护肾脏、泌尿系统、视网 膜、心脑血管",
        "改善代谢紊乱、提升精力 降低血液粘稠 保护肝脏及心脑血管 ",
        "提高代谢功能，降低尿酸指标 保护肾脏、胰岛、心脑血管 预 防痛风、肾脏、心脑血管疾病。 ",
        "提升体能及免疫力，改善头痛、头晕、精神状差、易困倦、 消化不良等贫血症状，改善心慌、胸闷，提升睡眠质量",
        "改善消化能力，改善胃溃疡、胃肠炎等胃肠道疾病症状，降 低胃肠道肿瘤患病风险。 ",
        "1.预防高血压导致心肌梗死、脑梗死、微动脉肿瘤等疾病；2. 改善血液粘稠，预防因血黏度迅速升高，造成心脑供血不足引发的冠心病、高 血压、脑血栓等心脑血管疾病；3.改善血管组织新陈代谢；4.降低饮酒对心脑血管的伤害；5.降低其他慢病或因素对心脑血管的伤害，如糖尿病、肥胖、胰 岛素抵抗、年龄增长、性别（男性发病高于女性）、种族、遗传等 。",
      ],
      //预期效果
      anticipateResultArray: [
        [],
        [
          "1个月，气色明显改善，经期前后腰腹坠胀明显减轻!",
          "2个月，经期逐渐规律!",
          "3个月，掌握自身营养规律，经期恢复正常周期；持续营养方案，激素分泌稳 定，延缓衰老，更显年轻态!",
        ],
        [
          "第1周期，体重减轻，肤色逐步变浅，情绪趋于稳定!",
          "第2 周期，经期规律稳定!",
          "第3周期，体重持续降低，经期恢复正常。激素分泌稳定，延缓衰老，更显年 轻态!",
        ],
        [
          "1-3个月，双方体质得到明显改善，女性妇科炎症得到缓解，男性体力得到提升， 疲惫感降低。",
          "4-6个月，女性卵巢功能得到改善，卵子质量，精子活力得到提升，提高受孕几率。",
        ],
        [
          "1个月，体重明显降低，产后水肿消失，稳定产奶量，减缓 产后抽筋，睡眠障碍；",
          "2个月，脱发减少,体重持续降低 ",
        ],
        [
          "1个月，高血压引起的头晕，颈项僵直得到明显缓解；",
          "2个月，不易疲劳，体感更轻松； ",
          "3个月，稳定血压。持续健康饮食方案，能够有效帮助避免心脑肾并发症。 ",
        ],
        [
          "第1周期，血糖趋于稳定，缓解多饮，多食，多尿的症状；",
          "第2周期，血糖进一步下降，末梢循环增快，自身抵抗力得到明显提升； ",
          "第3周期，小便变清澈，泡沫减少，血糖进一步稳定。持续营养方案，能够减少多 种并发症的产生。",
        ],
        [
          "1个月，头晕减少，体重降低； ",
          "2个月，肢体麻木得到缓解；",
          "3个月，血液粘稠度降低。坚持营养方案，延缓血管硬化，病程发展。",
        ],
        [
          "1个月，减少关节肿胀酸痛；",
          "2个月，帮助避免痛风急性发作；",
          "3个月，尿酸得到控制，指数降低。",
        ],
        [
          "1个月，消化功能得到改善，食欲得到增加，贫血性水肿得 到缓解； ",
          "2个月，头晕，头痛得到缓解，精神状态得到明显改善，疲惫感减少；",
          "3个月，睡眠质量得到明显提升，不易惊醒。",
        ],
        [
          "1个月，消化能力得到改善，胃酸、胃胀得到缓解；",
          "2个月， 饭前，饭后疼痛感减轻，反酸、嗳气等症状得到明显缓解；",
          "3个月，消化功能得到明显改善，排便趋向正常。坚持营养方案，胃肠免疫功能到持续养护，幽 门螺旋杆菌感染率降低，避免胃肠道肿瘤的出现。",
        ],
        [
          "2个月，血压得到明显稳定，心慌、气短等症状得到缓解； ",
          "4个月，心动力得到提升，脑供血供氧得到改善，头脑轻松，昏沉感降低；",
          "6 个月，心脑血管功能得到改善，降低心脑血管疾病急性发作的几率。",
        ],
      ],
      //调理意义
      significanceArray: [
        "",
        "提示女性体内激素水平是否正常 ",
        "提示女性体内激素水平是否正常，更清晰的观察子宫、卵巢、 盆腔等生殖器是否有病变发生。",
        "提示男性体内激素水平是否正常、提示女性体内激素水平是否正常、 阳性结果结合临床可确定沙眼衣原体感染、阴性时不能完全排除、可用细胞培养 法确定,妊娠早期感染可引起流产、死胎、胎儿畸形等.显示女性卵巢内含有的原 始卵泡的数量和质量、是反映女性生育能力的一个重要指标、检测男女生育能力、 检测女性生育能力,通过检测血液细胞的计数及不同种类细胞、成分的分类来反映 身体状况，如：贫血、感染、血液系统疾病、物理化学因素损伤等、提示贫血类 型。 ",
        "",
        "用于评估受检者的脂肪代谢水平及血脂代谢紊乱评价、动脉粥 样硬化性疾病危险性预测和营养学评价。了解颈部大动脉有无异常(如斑块、 硬化、狭窄)。可以清楚自己的血压情况，对起居，饮食，用药，保健等等都 有重要的指导作用。用于冠心病、动脉硬化、心梗、脑梗等的早期预警。",
        "用于评估受检者的脂肪代谢水平及血脂代谢紊乱评价、动脉粥样硬化性疾病危险性预测和营养学评价。可以清楚自己的血 压情况，对起居，饮食，用药，保健等等都有重要的指导作用。了解颈部大动 脉有无异常(如斑块、硬化、狭窄)。用于冠心病、动脉硬化、心梗、脑梗等的 早期预警。",
        "用于评估受检者的脂肪代谢水平及血脂代谢紊乱评价、动脉粥 样硬化性疾病危险性预测和营养学评价。可以清楚自己的血压情况，对起居， 饮食，用药，保健等等都有重要的指导作用。解颈部大动脉有无异常(如斑块、 硬化、狭窄)。用于冠心病、动脉硬化、心梗、脑梗等的早期预警。 ",
        "主要用于了解肾功能异常，痛风，尿酸偏高等。更清晰的检查 肾癌、游走肾等是否异常病变发生。可提示有无泌尿系统疾患：如急、慢性肾 炎，肾盂肾炎，膀胱炎，尿道炎，肾病综合征，狼疮性肾炎，血红蛋白尿，肾 梗塞、肾小管重金属盐及药物导致急性肾小管坏死，肾或膀胱肿瘤以及有无尿 糖等。微量白蛋白尿是指在尿中出现微量白蛋白。白蛋白是一种血液中的正常 蛋白质，但在生理条件下尿液中仅出现极少量白蛋白。微量白蛋白尿反映肾脏 异常渗漏蛋白质。 ",
        "提示贫血类型。通过检测血液细胞的计数及不同种类细胞、成 分的分类来反映身体状况，如：贫血、感染、血液系统疾病、物理化学因素损 伤等。提示肝胆系统疾病。更清晰的检查肾癌、游走肾等是否异常病变发生 ",
        "查看胃部是否有病变，通过吹气了解胃内有无幽门螺旋杆菌感 染，此感染与胃炎、消化性溃疡、胃癌等发病有密切关系。 可用于胃癌、胃 溃疡、萎缩性胃炎的初筛。1、胃泌素17（ G-17）由胃窦G细胞分泌，具有促 进胃酸分泌、胃肠道粘膜生长及调节食管括约肌等作用，高G-17分泌在胃癌发 展过程中起重要促进作用。 2、G-17可以直接评估胃窦部粘膜的健康状况和间接反映胃体部粘膜的健康状 况，同时可反映胃酸水平的高低，从而对胃部疾病风险进行有效评估。",
        "用于评估受检者的脂肪代谢水平及血脂代谢紊乱评价、动脉粥 样硬化性疾病危险性预测和营养学评价。可以清楚自己的血压情况，对起居， 饮食，用药，保健等等都有重要的指导作用。用于冠心病、动脉硬化、 心梗、脑梗等的早期预警。心血管疾病的重要诊断方法。动态显示心腔内结构、 心脏的搏动和血液流动，对先天性心脏病、心肌病、冠心病、心肌梗塞并发症 及肺心病有较大的诊断价值。用于心肌梗塞、心肌炎、皮肌炎等的辅助诊断。 了解颈部大动脉有无异常(如斑块、硬化、狭窄)。 ",
      ],
    };
  },
  mounted() {
    document.title = this.$route.meta.title;
  },
  created() {
    this.getContract();
  },
  methods: {
    getContract() {
      const { pathname } = window.location;
      const id = pathname.substring(pathname.lastIndexOf("/") + 1);
      // console.log(id)
      getFile(id).then((result) => {
        if (result.url) {
          // this.$router.push(result.url);
          window.location.href = `${window.location.origin}${result.url}`;
        } else if (result.data) {
          this.form = result.data;
          this.form.amount = parseInt(result.data.amount);
          this.form.amountUpper = digitUppercase(this.form.amount);
          this.form.serveTimeStr = this.serveTimeIdOption.find(
            (obj) => obj.value === parseInt(result.data.serveTime)
          ).label;
          this.form.expireTime = dayjs()
            .add(this.form.serveTime / 30, "month")
            .format("YYYY-MM-DD");
          this.show = true;
        }
      });
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          signContract(this.form).then((result) => {
            if (result.code === 200) {
              window.location.href = window.location.origin + result.url;
            }
          });
        }
      });
    },
    handleRadioChange(val) {
      this.read = val;
    },
    handleConfirm() {
      this.dialogVisible = false;
      this.read = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.sign-contract-container {
  font-family: Helvetica Neue, Helvetica, Arial, Microsoft Yahei,
    Hiragino Sans GB, Heiti SC, WenQuanYi Micro Hei, sans-serif;
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
