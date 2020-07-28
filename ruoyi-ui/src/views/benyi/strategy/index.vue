 <template>
  <div class="apply-input">
    <h2 class="title">本一体系常规招生策略</h2>
    <div class="apply-form">
      <el-collapse v-model="activeNames" accordion>
        <el-collapse-item name="1">
          <span slot="title" class="box-card-title">一、初次入园咨询接待</span>
          <div
            class="text item"
          >幼儿园招生联系人必须是园长或教学主管。无论是哪一种媒体，幼儿园对外公布的招生电话首选必须是园长，次选是教学主管。不要单设一般人员负责招生。以下建议是按时间顺序所安排的程序和流程，请本一加盟园参考使用。</div>
          <div class="text item">1.初次接触方式</div>
          <div
            class="text item"
          >1)电话或微信方式联系。园长在接到电话或收到信息时，首先逐一、耐心和详细回答家长提出的问题，让家长第一印象深刻。接下来，当家长表示积极意愿时，主动邀请家长来园参观。如果家长应允，即刻约定好具体参观时间。</div>
          <div
            class="text item"
          >2)无联系直接来园。如果家长事先未联系就直接来了幼儿园，如果其时间充裕，就直接接待和参观。如果当天没有时间参观，则预约改天的具体参观时间。</div>
          <div class="text item">2.接待</div>
          <div
            class="text item"
          >接待人必须是园长，园长不在则由教学主管接待。幼儿园应设置招生接待处。可以是专门房间，也可以是园长办公室。但需要温馨的布置和茶水伺候。将家长引领到接待出让座就坐，开始交谈。请参考以下几点。</div>
          <div class="text item">1)聆听家长问题，并耐心细致回答，伺机介绍本一理念、课程等（参考本一的“宣传方案”）。</div>
          <div class="text item">2)问询孩子情况。告知家长是否还有空余学位。</div>
          <div class="text item">3)如果家长不再有问题，主动请求家长参观幼儿园。</div>
          <div class="text item">3.参观</div>
          <div class="text item">参观程序可以先内后外，或者先外后内都可以，根据情况而定。</div>
          <div
            class="text item"
          >1)事先向家长承诺，在整个参观过程中，不会听到任何教师孩子的大声斥责、吼叫及不满的高声抱怨，等等。告诉家长，园里所有教师对孩子都好，这是我们幼儿园的最大特点。另一件需要事先告知家长的是，无论走到哪一间教室门前，家长都可以随时要求进去参观。证明我们有底气。</div>
          <div
            class="text item"
          >2)按楼内自然设置确定参观路线。自接待处开始，从一层到二层、三层顺序参观。走到哪里介绍到哪里。大厅理念墙介绍理念，走廊介绍孩子作品，楼梯墙介绍装饰或孩子作品，等等，都须要用本一理念加以说明和介绍。此外要特别参观一下的地方还包括保健室（包括隔离室）、厨房以及功能教室（如果有，包括图书室、美工室、科学室等）。</div>
          <div
            class="text item"
          >3)走到与家长的孩子同年龄段班级时，开门进去，请向家长介绍和解释当时班级正在进行的环节和活动。特别提示家长注意观察和感受教室内的师童互动氛围。同时，可以简单介绍一下签到板、上墙作品和规则及区角规则、教室区域化设置等（细节参观可以在“入班半日观摩”时进行）。教室内参观控制在8-10分钟。</div>
          <div class="text item">4)楼内参观完毕再接着看一下户外场地与设备，向家长做一下介绍。</div>
          <div class="text item">5)室内外参观完毕，再将家长让进接待室。主动询问家长是否有问题。如果有，逐一、耐心和详细作答。</div>
          <div
            class="text item"
          >6)观察或询问家长意愿。如果家长参观后就决定孩子入园，那么进入入园程序；如果还有犹豫，邀请家长来园参加“入班半日体验”。一旦家长决定来，预约具体时间，然后做出安排，落实到班级和人。</div>
        </el-collapse-item>
        <el-collapse-item name="2">
          <span slot="title" class="box-card-title">二、家长入班半日体验</span>
          <div class="text item">1. 预约进班时间，安排与咨询入园孩子相应年龄段班级准备按流程接待。</div>
          <div class="text item">2. 给家长发通知，将日期、时间、接待教师、班级教室名称和位置等要素列出。</div>
          <div class="text item">3. 半日体验当天，借给家长一本《一日流程》书，和给一份“咨询入园班级半日体验计划表”。</div>
          <div class="text item">4. 整个半日体验程序，完全按“咨询入园班级半日体验计划表”进行即可。</div>
          <div
            class="text item"
          >5. 半日体验完毕，由园长或教学主管再次接待，询问体验情况和家长决定。如果家长决定让孩子入园，则安排入园具体事项。如果家长还有顾虑或犹豫，邀请家长带孩子来园做几次“跟班半日”活动或两周试入园体验。</div>
        </el-collapse-item>
        <el-collapse-item name="3">
          <span slot="title" class="box-card-title">三、跟班半日活动</span>
          <div
            class="text item"
          >对任何孩子来说，有几次跟班活动半日的经验，再入园就会在很大程度上减少入园分离焦虑。因此，幼儿园可以征求咨询入园母亲的意见，给予母子几次半天跟班活动的机会。可参考以下建议。</div>
          <div class="text item">1. 邀请亲子（母亲或祖母亲）入班跟班半日，班上有专门安排。</div>
          <div class="text item">2. 第一天进班要在坐圈时给小朋友们介绍一下母子。</div>
          <div class="text item">3. 为有利于孩子适应，跟班要从周一开始，建议家长最好从周一到周五一直跟班。</div>
          <div class="text item">4. 跟班中间不要换家长，只须一位家长一直和孩子一起进班。</div>
          <div class="text item">5. 如果孩子适应好，半日跟班时家长可选择离开一定时间，不必全程陪伴。</div>
          <div class="text item">6. 嘱咐家长，跟班时母子最好能听从教师指示统一活动，尽量不要单独做事。</div>
          <div class="text item">7. 半日跟班时间限制一周。如果适应好，不须进行“试入园”，直接办理手续入园即可。</div>
        </el-collapse-item>
        <el-collapse-item name="4">
          <span slot="title" class="box-card-title">四、试入园</span>
          <div class="text item">1. 规定试入园最多时间为两周，即10个园日。试入园全免费。</div>
          <div class="text item">2. 须要为孩子做“入园体检”，给家长孩子入园体检信息，包括体检地点、名称等。此外，请列出一份试入园所需物品清单给家长。</div>
          <div
            class="text item"
          >3. 要求试入园时间必须是周一开始，即便试入园一周也是要求周一开始。可以是半天，也可以是全天；最初几天可以有家长短暂陪伴，这些都根据孩子和家庭情况而定。</div>
          <div class="text item">4. 将孩子每天在园情况，以微信或面对面方式与家长交流。</div>
          <div class="text item">5. 试入园第一周结束时，班长做一下评估，分析一下孩子适应得怎么样，包括是否需要继续尝试，并将结果报告给家长。</div>
          <div class="text item">6. 试入园结束后做出孩子是否适应和是否适合入园的评估报告。</div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Startegy",
  data() {
    return {
      activeNames: "1",
    };
  },
};
</script>

<style lang="scss">
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
  line-height: 20px;
  text-indent: 2em;
}
.box-card-title {
  display: flex;
  align-items: center;
  font-size: 16px;
  &::before {
    content: "";
    margin-right: 8px;
    width: 4px;
    height: 16px;
    background: #1890ff;
  }
  &.mr {
    margin: 10px 0;
  }
}
.apply-input {
  padding-top: 30px;
  p {
    margin: 0;
  }
  .title {
    text-align: center;
    font-size: 22px;
  }
  .apply-form {
    width: 96%;
    margin: 30px auto 0;
  }
}
@media screen and (max-width: 768.89px) {
  .apply-input {
    padding: 30px 10px 0;
    .apply-form {
      width: 100%;
    }
  }
}
</style>