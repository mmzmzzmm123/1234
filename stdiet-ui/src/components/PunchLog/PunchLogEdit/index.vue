<template>
  <!-- 修改打卡记录基础信息对话框 -->
    <el-dialog :title="title" :visible.sync="visible" width="600px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          
          <el-col :span="12">
            <el-form-item label="体重" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入体重" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="打卡日期" prop="logTime">
              <el-date-picker
                clearable
                size="small"
                style="width: 192px;"
                v-model="form.logTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择打卡时间"
                :picker-options="logTimePickerOptions"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="睡觉时间" prop="sleepTime">
              <el-time-select
                clearable
                size="small"
                style="width: 192px"
                v-model="form.sleepTime"
                :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:45',
                }"
                placeholder="选择睡觉时间"
              >
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起床时间" prop="wakeupTime">
              <el-time-select
                clearable
                size="small"
                style="width: 192px"
                v-model="form.wakeupTime"
                :picker-options="{
                  start: '00:00',
                  step: '00:15',
                  end: '23:45',
                }"
                placeholder="选择睡觉时间"
              >
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="运动锻炼" prop="sport">
              <el-select v-model="form.sport" placeholder="请选择运动情况">
                <el-option
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="按食谱" prop="diet">
              <el-select v-model="form.diet" placeholder="请选择饮食情况">
                <el-option
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="熬夜失眠" prop="insomnia">
              <el-select v-model="form.insomnia" placeholder="请选择熬夜失眠">
                <el-option
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="起床排便" prop="defecation">
              <el-select v-model="form.defecation" placeholder="请选择排便情况">
                <el-option
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="便秘" prop="constipation">
              <el-select v-model="form.constipation" placeholder="请选择便秘情况">
                <el-option
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="饮水量" prop="water">
              <el-input v-model="form.water" style="width: 192px" placeholder="请输入饮水量" />
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="情绪" prop="emotion">
              <el-input
                v-model="form.emotion"
                type="textarea"
                placeholder="请输入情绪"
              />
            </el-form-item>
          </el-col>
           
           <el-col>
            <el-form-item label="其他食物" prop="slyEatFood">
              <el-input
                v-model="form.slyEatFood"
                type="textarea"
                placeholder="请输入其他食物"
              />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>
<script>
import {
  getWxUserLog,updateWxUserLog
} from "@/api/custom/wxUserLog";
import dayjs from "dayjs";

export default {
  name: "PunchLogEdit",
  components: {
      
  },
  data() {
    return {
      visible: false,
      title: "",
      punchLog: null,
      form: {},
      rules:{
        weight: [
          { required: true, message: "体重不能为空", trigger: "blur" },
          {
            required: true,
            trigger: "blur",
            pattern: /^(([^0][0-9]+|0)\.([0-9]{1,2})$)|^([^0][0-9]+|0)$/,
            message: "体重格式不正确",
          },
        ],
        water: [
            { required: true, message: "饮水量不能为空", trigger: "blur" },
            {
                required: true,
                trigger: "blur",
                pattern: /^([^0][0-9]+|0)$/,
                message: "饮水量格式不正确",
            }
        ],
        logTime: [
            { required: true, message: "打卡时间不能为空", trigger: "blur" },
        ]
      },
      logTimePickerOptions: {
        disabledDate(time) {
          return time.getTime() > dayjs()
        },
      },
      yesNoOptions: [],
      callback: null
    };
  },
  created(){
        this.getDicts("sys_yes_no").then((response) => {
            this.yesNoOptions = response.data;
        });
  },
  methods: {
    showDialog(data, callback) {
      this.punchLog = data;
      this.callback = callback;
      this.title = `修改「${data.customerName}`+" "+`${data.logTime}」打卡记录`;
      this.getWxUserLog();
    },
    getWxUserLog() {
        this.reset();
        getWxUserLog(this.punchLog.id).then((response) => {
            if (response.code == 200) {
                this.form = response.data;
                this.visible = true;
            }
        });
    },
    reset(){
        this.form = {
            id: null,
            weight: null,
            sleepTime: null,
            wakeupTime: null,
            sport: null,
            diet: null,
            insomnia: null,
            defecation: null,
            water: null,
            logTime: null,
            constipation: null,
            emotion: null,
            slyEatFood: null

      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateWxUserLog(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.visible = false;
                this.callback && this.callback();
              }
            });
          }
        }
      });
    },
    onClosed() {
      this.form = null;
      this.punchLog = null
    },
    cancel(){
        this.visible = false
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
