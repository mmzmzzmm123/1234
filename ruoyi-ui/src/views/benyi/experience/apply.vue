 <template>
  <div class="apply-input">
    <h2 class="title">入班半日体验申请表</h2>
    <p class="info">为了更好地了解贵园，申请来贵园进行入班半日体验。</p>
    <div class="apply-form">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="家长姓名" prop="jzxm">
          <el-input v-model="form.jzxm" placeholder="请输入家长姓名" />
          <el-input v-model="form.schoolid" v-if="false" />
        </el-form-item>
        <el-form-item label="联系方式" prop="lxfs">
          <el-input v-model="form.lxfs" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="幼儿姓名" prop="yexm">
          <el-input v-model="form.yexm" placeholder="请输入幼儿姓名" />
        </el-form-item>
        <el-form-item label="幼儿出生日期" prop="csrq">
          <el-date-picker
            clearable
            size="small"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择幼儿出生日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="拟入园时间" prop="nrysj">
          <el-date-picker
            clearable
            size="small"
            v-model="form.nrysj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择拟入园时间"
          ></el-date-picker>
        </el-form-item>
        <div>
          <div>
            <el-form-item label="预约体验时间" prop="sqtysj">
              <el-date-picker
                clearable
                size="small"
                v-model="form.sqtysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="申请体验时间"
              ></el-date-picker>
            </el-form-item>
            <el-form-item prop="swxw">
              <el-radio-group v-model="form.swxw">
                <el-radio label="1">上午</el-radio>
                <el-radio label="2">下午</el-radio>
              </el-radio-group>
            </el-form-item>
            <p class="come-txt">来园入班体验。请园长为我安排与我孩子年龄段相适应的班级。届时，我将与我的孩子准时入班进行半日体验。</p>
          </div>
          <p class="thanks-top">请园长批准我的申请为盼。</p>
          <p class="thanks">谢谢！</p>
        </div>
      </el-form>
      <div class="btns">
        <el-button class="btn" type="primary" @click="submitForm">确 定</el-button>
        <el-button class="btn" @click="reset">重 置</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { addExperience } from "@/api/benyi/experience";

export default {
  name: "Apply",
  data() {
    return {
      activeNames: ["1"],
      yysj: "",
      sworxw: "",
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jzxm: [
          { required: true, message: "家长姓名不能为空", trigger: "blur" },
        ],
        yexm: [
          { required: true, message: "幼儿姓名不能为空", trigger: "blur" },
        ],
        csrq: [
          { required: true, message: "幼儿出生日期不能为空", trigger: "blur" },
        ],
        lxfs: [
          {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        nrysj: [
          { required: true, message: "拟入园时间不能为空", trigger: "blur" },
        ],
        sqtysj: [
          { required: true, message: "申请体验时间不能为空", trigger: "blur" },
        ],
        swxw: [
          { required: true, message: "下午或下午不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const sid = this.$route.params && this.$route.params.id;
    this.form.schoolid = sid;
  },
  methods: {
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        jzxm: undefined,
        yexm: undefined,
        csrq: undefined,
        lxfs: undefined,
        nrysj: undefined,
        sqtysj: undefined,
        swxw: undefined,
        schoolid: undefined,
        createTime: undefined,
      };
      this.resetForm("form");
    },
    // 返回按钮
    cancel() {
      this.$router.go(-1);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addExperience(this.form).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("您的申请提交成功，等待园长回复");
              this.reset();
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss">
.apply-input {
  padding-top: 60px;
  p {
    margin: 0;
  }
  .title {
    text-align: center;
    font-size: 18px;
  }
  .info {
    text-align: center;
    font-size: 14px;
  }
  .apply-form {
    width: 500px;
    margin: 30px auto 0;
  }
  .btns {
    display: flex;
    margin-top: 30px;
    .btn {
      flex: 1;
      margin: 0 10px;
    }
  }
  .come-txt {
    line-height: 22px;
    text-indent: 2em;
    font-size: 14px;
  }
  .thanks-top {
    padding: 8px 0 8px 0;
    text-align: right;
    font-size: 14px;
  }
  .thanks {
    text-align: right;
    font-size: 14px;
  }
}
@media screen and (max-width: 768.89px) {
  .apply-input {
    padding: 60px 10px 30px;
    .apply-form {
      width: 100%;
    }
  }
}
</style>