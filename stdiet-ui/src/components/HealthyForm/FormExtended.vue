<template>
  <div>
    <p class="p_title_1">九、{{healthyData.projectName[form.conditioningProjectId+'']}}信息评估</p>
    <!--高血糖-->
    <div v-show="form.conditioningProjectId == 6 || form.conditioningProjectId == 5">
            <div v-show="form.conditioningProjectId == 6">
            <p class="p_title_2">1、餐前餐后血糖</p>
            <div style="margin-top:10px">
                <div class="margin-left"><span>餐前：</span><el-input placeholder="输入血糖数值" v-model="form.healthyExtend.bloodSugarMessage.beforeMealBloodSugar" maxlength="100" style="width:40%"></el-input><span class="margin-left">mmol/L</span></div>
                <div class="margin-left" style="margin-top:8px;"><span>餐后两小时：</span><el-input style="width:40%;" v-model="form.healthyExtend.bloodSugarMessage.afterMealBloodSugar" maxlength="100" placeholder="输入血糖数值"></el-input><span class="margin-left">mmol/L</span></div>
                <div class="margin-left" style="margin-top:8px;"><span>糖化血红蛋白：</span><el-input style="width:40%;" v-model="form.healthyExtend.bloodSugarMessage.sugarHemoglobin" maxlength="100" placeholder="输入糖化血红蛋白数值"></el-input><span class="margin-left">mmol/L</span></div>
            </div>
            <p class="p_title_2">2、血糖水平以及测量时间</p>
              <el-form-item :label="'(1) 是否有规律测血糖'"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.bloodSugarMessage.measureBloodSugarFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
              </el-radio-group>
              <div style="margin-top:8px;"><span>测量时间&nbsp;</span><el-input  v-model="form.healthyExtend.bloodSugarMessage.measureBloodSugarTime" placeholder="请描述血糖测量时间" /></div>
              <div><span>近期血糖水平</span>
                <el-input type="textarea"
                          v-model="form.healthyExtend.bloodSugarMessage.nearBloodSugar"
                          placeholder="请描述下近期血糖水平"
                          maxlength="200"
                          show-word-limit
                          rows="3"
                ></el-input>
              </div>
              </el-form-item>
              <el-form-item :label="'(2) 是否有低血糖反应'" class="margin-left">
                <el-radio-group v-model="form.healthyExtend.bloodSugarMessage.lowBloodSugarFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
              </el-radio-group>
              <div style="margin-top:8px;"><span>低血糖反应出现时间&nbsp;</span><el-input  v-model="form.healthyExtend.bloodSugarMessage.lowBloodSugarTime" maxlength="100" placeholder="请描述低血糖出现时间" /></div>
              </el-form-item>
              <el-form-item :label="'(3) 是否有出现并发症'"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.bloodSugarMessage.complicationFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
              </el-radio-group>
              <div style="margin-top:8px;"><span>并发症情况&nbsp;</span>
              <el-input type="textarea"
                          v-model="form.healthyExtend.bloodSugarMessage.complication"
                          placeholder="请描述下并发症情况"
                          maxlength="200"
                          show-word-limit
                          rows="3"
                ></el-input>
              </div>
              </el-form-item>
              <el-form-item :label="'(4) 目前是否存在下方症状'" class="margin-left">
                <el-radio-group v-model="form.healthyExtend.bloodSugarMessage.inferiorSymptomFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              <div style="margin-top:8px;"><span>若存在请选择对应症状</span>
              <el-checkbox-group v-model="form.healthyExtend.bloodSugarMessage.inferiorSymptom">
                  <el-checkbox v-for="(item,index) in healthyData['syndromeNameArray']" :label="item.value" :key="index">{{ item.name }}</el-checkbox>
              </el-checkbox-group>

              </div>
              </el-form-item>
              <p class="p_title_2">2、体重变化情况</p>
              <el-form-item :label="'(1) 近三个月体重是否有变化'"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.bloodSugarMessage.weightChangeFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span>若存在体重变化，请您描述一下变化情况</span>
                  <el-input type="textarea"
                          v-model="form.healthyExtend.bloodSugarMessage.weightChangeDescribe"
                          placeholder="近三个月体重变化情况"
                          maxlength="200"
                          show-word-limit
                          rows="3"
                ></el-input>
              </div>
              </el-form-item>
            </div>

            <div v-show="form.conditioningProjectId == 5">
              <p class="p_title_2">1、餐前餐后血压</p>
              <div style="margin-top:10px">
                  <div class="margin-left"><span>餐前：</span><el-input placeholder="输入血压数值" maxlength="100" v-model="form.healthyExtend.bloodPressureMessage.beforeMealBloodPressure" style="width:50%"></el-input><span class="margin-left">mmHg</span></div>
                  <div class="margin-left" style="margin-top:8px;"><span>餐后两小时：</span><el-input style="width:50%;" maxlength="100" v-model="form.healthyExtend.bloodPressureMessage.afterMealBloodPressure" placeholder="输入血压数值"></el-input><span class="margin-left">mmHg</span></div>
              </div>
              <p class="p_title_2">2、血压水平以及测量时间</p>
                <el-form-item :label="'(1) 是否有规律测血压'"  class="margin-left">
                  <el-radio-group v-model="form.healthyExtend.bloodPressureMessage.measureBloodPressureFlag">
                    <el-radio  :label="1" >是</el-radio>
                    <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span>测量时间&nbsp;</span><el-input maxlength="100" v-model="form.healthyExtend.bloodPressureMessage.measureBloodPressureTime" placeholder="请描述血压测量时间" /></div>
                <div><span>近期血压水平</span>
                  <el-input type="textarea"
                            v-model="form.healthyExtend.bloodPressureMessage.nearBloodPressure"
                            placeholder="请描述下近期血压水平"
                            maxlength="200"
                            show-word-limit
                            rows="3"
                  ></el-input>
                </div>
                </el-form-item>
                <el-form-item :label="'(2) 是否有低血压反应'" class="margin-left">
                  <el-radio-group v-model="form.healthyExtend.bloodPressureMessage.lowBloodPressureFlag">
                    <el-radio  :label="1" >是</el-radio>
                    <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span>低血压反应出现时间&nbsp;</span><el-input maxlength="100" v-model="form.healthyExtend.bloodPressureMessage.lowBloodPressureTime" placeholder="请描述低血压出现时间" /></div>
                </el-form-item>
                <el-form-item :label="'(3) 是否有出现并发症'"  class="margin-left">
                  <el-radio-group v-model="form.healthyExtend.bloodPressureMessage.complicationFlag">
                    <el-radio  :label="1" >是</el-radio>
                    <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span>并发症情况&nbsp;</span>
                <el-input type="textarea"
                            v-model="form.healthyExtend.bloodPressureMessage.complication"
                            placeholder="请描述下并发症情况"
                            maxlength="200"
                            show-word-limit
                            rows="3"
                  ></el-input>
                </div>
                </el-form-item>
                <el-form-item :label="'(4) 目前是否存在下方症状'" class="margin-left">
                  <el-radio-group v-model="form.healthyExtend.bloodPressureMessage.inferiorSymptomFlag">
                    <el-radio  :label="1" >是</el-radio>
                    <el-radio  :label="0" >否</el-radio>
                  </el-radio-group>
                <div style="margin-top:8px;"><span>若存在请选择对应症状</span>
                <el-checkbox-group v-model="form.healthyExtend.bloodPressureMessage.inferiorSymptom">
                    <el-checkbox v-for="(item,index) in healthyData['syndromeNameArray']" :label="item.value" :key="index">{{ item.name }}</el-checkbox>
                </el-checkbox-group>

                </div>
                </el-form-item>
                <p class="p_title_2">2、体重变化情况</p>
                <el-form-item :label="'(1) 近三个月体重是否有变化'"  class="margin-left">
                  <el-radio-group v-model="form.healthyExtend.bloodPressureMessage.weightChangeFlag">
                    <el-radio  :label="1" >是</el-radio>
                    <el-radio  :label="0" >否</el-radio>
                  </el-radio-group>
                </el-form-item>
            </div>

            <div>
                <p class="p_title_2">3、焦虑状态评估</p>
              <el-form-item label="(1) 你认为你是一个容易焦虑或紧张的人吗?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.anxietyStateMessage.easyAnxiousFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(2) 最近一段时间，你是否比平时更感到焦虑或忐忑不安?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.anxietyStateMessage.upsetRecently">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(3) 是否有一些特殊场合或情景更容易使得你紧张、焦虑?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.anxietyStateMessage.nervousOnSpecialOccasionsFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(4) 你曾经有过惊恐发作吗.即突然发生的强烈不适感或心慌、眩晕、感到憋气或呼吸困难等症状?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.anxietyStateMessage.terrifiedFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <p class="p_title_2">4、郁抑状态评估</p>
              <el-form-item label="(1) 过去几周(或几个月)是否感觉到无精打采、伤感、或对生活的乐趣减少?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.depressedStateMessage.listlessRecentlyFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(2) 除了不开心之外，是否比平时更加悲观或想哭?"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.depressedStateMessage.cryRecentlyFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(3) 经常有早醒吗?(事实上不需那么早醒来)"  class="margin-left">
                <el-radio-group v-model="form.healthyExtend.depressedStateMessage.wakeUpEarlyRecentlyFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="(4) 近来是否经常想到活着没有意思" class="margin-left">
                <el-radio-group v-model="form.healthyExtend.depressedStateMessage.noFunLiving">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
        </div>
        <!-- 月经不调、多囊卵巢综合症 -->
        <div v-if="form.conditioningProjectId == 1 || form.conditioningProjectId == 2">
            <p class="p_title_2">1、月经基础情况</p>
            <div style="margin-top:10px">
                <el-form-item label="(1) 月经周期、天数" class="margin-left">
                <div class="margin-left"><span class="text-span">月经周期：</span><el-input placeholder="输入月经周期" v-model="form.healthyExtend.menstruationMessage.menstrualCycle" maxlength="100" style="width:40%"></el-input><span class="margin-left">天</span></div>
                <div class="margin-left text-span" style="margin-top:8px;"><span>月经天数：</span><el-input style="width:40%;" v-model="form.healthyExtend.menstruationMessage.menstrualDays" maxlength="100" placeholder="输入月经天数"></el-input><span class="margin-left">天</span></div>
                </el-form-item>
            </div>
            <el-form-item label="(2) 月经大概预估量" class="margin-left">
                <el-input type="textarea"
                            v-model="form.healthyExtend.menstruationMessage.menstrualForecast"
                            placeholder="请描述下大概预估量，可以参考用的姨妈纸的量，比如日用280，换几次"
                            maxlength="100"
                            show-word-limit
                            rows="3"
                  ></el-input>
            </el-form-item>
            <el-form-item label="(3) 月经推迟时长" class="margin-left">
                <el-input type="textarea"
                            v-model="form.healthyExtend.menstruationMessage.menstrualDelayDays"
                            placeholder="请描述下推迟时长，可以填具体推迟天数，也可以填写具体的时间，如3个月2次之类的"
                            maxlength="100"
                            show-word-limit
                            rows="3"
                  ></el-input>
            </el-form-item>

            <el-form-item label="(4) 月经提前时长" class="margin-left">
                <el-input type="textarea"
                            v-model="form.healthyExtend.menstruationMessage.menstrualAdvanceDays"
                            placeholder="请描述下提前时长，可以填具体推迟天数，也可以填写具体的时间，如3个月2次之类的"
                            maxlength="100"
                            show-word-limit
                            rows="3"
                  ></el-input>
            </el-form-item>

            <el-form-item label="(4) 是否有闭经" class="margin-left">
                <el-radio-group  v-model="form.healthyExtend.menstruationMessage.amenorrhoeaFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span class="text-span">若存在闭经，请您描述一下闭经时长</span>
                  <el-input maxlength="100"
                          v-model="form.healthyExtend.menstruationMessage.amenorrhoeaDays"
                          placeholder="请输入闭经时长"
                ></el-input>
                </div>
            </el-form-item>


            <el-form-item label="(5) 月经颜色" class="margin-left">
                <el-radio-group v-model="form.healthyExtend.menstruationMessage.menstrualColor">
                  <el-radio  v-for="(item,index) in healthyData['menstrualColorArray']" :label="item.value" :key="index" >{{ item.name }}</el-radio>
                </el-radio-group>
                <div style="margin-top:8px">
                  <span class="text-span">其他颜色：</span>
                  <el-input maxlength="100"
                          v-model="form.healthyExtend.menstruationMessage.otherMenstrualColor"
                          placeholder="请输入其他颜色"
                          style="width:50%"
                ></el-input>
                </div>
            </el-form-item>
            <el-form-item label="(6) 月经形状" class="margin-left">
               <el-radio-group v-model="form.healthyExtend.menstruationMessage.menstrualCharacter">
                  <el-radio  v-for="(item,index) in healthyData['menstrualCharacterArray']" :label="item.value" :key="index">{{ item.name }}</el-radio>
                </el-radio-group>
                <div style="margin-top:8px">
                  <span class="text-span">其他形状：</span>
                  <el-input maxlength="100"
                          v-model="form.healthyExtend.menstruationMessage.otherMenstrualCharacter"
                          placeholder="请输入其他形状"
                          style="width:50%"
                ></el-input>
                </div>
            </el-form-item>

             <p class="p_title_2">2、痛经情况</p>
             <el-form-item label="(1)、是否痛经" class="margin-left">
                <el-radio-group  v-model="form.healthyExtend.menstruationMessage.menstrualPainFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
             </el-form-item>

            <el-form-item label="(2)、痛经性质" class="margin-left">

                <el-radio-group v-model="form.healthyExtend.menstruationMessage.menstrualNature">
                  <el-radio  v-for="(item,index) in healthyData['menstrualNatureArray']" :style="'width:100%;'+(index >0 ? 'margin-top:7px' : '')" :label="item.value" :key="index">{{ item.name }}</el-radio>
                </el-radio-group>

            </el-form-item>
            <el-form-item label="(3)、痛经类型" class="margin-left">

              <el-radio-group v-model="form.healthyExtend.menstruationMessage.menstrualType">
                  <el-radio  v-for="(item,index) in healthyData['menstrualTypeArray']" :style="'width:100%;'+(index >0 ? 'margin-top:7px' : '')" :label="item.value" :key="index">{{ item.name }}</el-radio>
                </el-radio-group>

            </el-form-item>
            <div class="margin-left text-span">
                  <div style="color:#1890ff">类型特点说明：</div>
                  <div style="margin-top:5px" v-for="(item,index) in healthyData['menstrualTypeIntroduceArray']" :key="index+'div'">{{item.name}}：<span class="text-span-min">{{item.value}}</span></div>
             </div>

            <p class="p_title_2">3、药物情况</p>
            <el-form-item label="(1)、是否用药" class="margin-left">
              <el-radio-group  v-model="form.healthyExtend.menstruationMessage.medicationFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>
                <div style="margin-top:8px;"><span class="text-span">若存在用药，请描述具体名称、剂量、服用时间等</span>
                  <el-input maxlength="100"
                          v-model="form.healthyExtend.menstruationMessage.medication"
                          placeholder="请输入用药详情"
                ></el-input>
                </div>
            </el-form-item>
            <el-form-item label="(2)、是否有生育计划" class="margin-left">

              <el-radio-group  v-model="form.healthyExtend.menstruationMessage.familyPlann">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>

            </el-form-item>

            <el-form-item label="(3)、是否存在同房出血情况" class="margin-left">

              <el-radio-group  v-model="form.healthyExtend.menstruationMessage.sameRoomBleed">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>

            </el-form-item>

            <el-form-item label="(4)、是否存在排卵期出血" class="margin-left">

              <el-radio-group  v-model="form.healthyExtend.menstruationMessage.ovulationBleed">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>

            </el-form-item>

             <el-form-item label="(5)、是否出现胰岛素抵抗（重点）" class="margin-left">

              <el-radio-group  v-model="form.healthyExtend.menstruationMessage.insulinResistanceFlag">
                  <el-radio  :label="1" >是</el-radio>
                  <el-radio  :label="0" >否</el-radio>
                </el-radio-group>

            </el-form-item>

            <el-form-item label="(6)、其他补充" class="margin-left">

                <el-input type="textarea"
                            v-model="form.healthyExtend.menstruationMessage.otherDescriptions"
                            placeholder="请输入其他补充"
                            maxlength="200"
                            show-word-limit
                            rows="3"
                  ></el-input>

            </el-form-item>

        </div>
  </div>
</template>
<script>
import * as healthyData from "@/utils/healthyData";
export default {
  name: "FormExtended",
  components: {
  },
  data() {
    return {
       healthyData:healthyData
    };
  },
  methods: {

  },
  props: {
    form: {
      type: Object,
      default: null
    }
  },
  created() {

  },
  computed:{

  }

};
</script>

<style scoped>

  .el-form-item {
    margin-bottom: 8px;
  }

  .p_title_1{
    font-size: 18px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_2{
    font-size: 16px;
    font-weight: bold;
    margin-top: 30px;
  }
  .p_title_3{
    font-size: 14px;
    font-weight: bold;
    margin-top: 30px;
  }
  .margin-left{
    margin-left: 14px;
  }
  .el-input__inner{
    width: 30%;
  }
  .margin-top-10{
    margin-top: 10px;
  }
  .width-50-left-8-right-5{
    width:50%;
    margin-left: 8px;
    margin-right: 5px;
  }
  .width-70-left-8-right-5{
    width:70%;
    margin-left: 8px;
    margin-right: 5px;
  }

  .text-span{
    color:#606266;
    font-weight: 700;
    font-size: 14px
  }

  .text-span-min{
    color:#606266;
    font-weight: 400;
    font-size: 13px
  }


</style>

