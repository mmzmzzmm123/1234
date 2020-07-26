 <template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="家长姓名" prop="jzxm">
        <el-input v-model="form.jzxm" placeholder="请输入家长姓名" />
      </el-form-item>
      <el-form-item label="幼儿姓名" prop="yexm">
        <el-input v-model="form.yexm" placeholder="请输入幼儿姓名" />
      </el-form-item>
      <el-form-item label="幼儿出生日期" prop="csrq">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="form.csrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择幼儿出生日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="联系方式" prop="lxfs">
        <el-input v-model="form.lxfs" placeholder="请输入联系方式" />
      </el-form-item>
      <el-form-item label="拟入园时间" prop="nrysj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="form.nrysj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择拟入园时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="申请体验时间" prop="sqtysj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="form.sqtysj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择申请体验时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="上午或下午" prop="swxw">
        <el-input v-model="form.swxw" placeholder="请输入上午或下午" />
      </el-form-item>
      <el-form-item label="体验学校" prop="schoolid">
        <el-input v-model="form.schoolid" placeholder="请输入体验学校" />
      </el-form-item>
      <el-form-item label="是否回复" prop="sfhf">
        <el-input v-model="form.sfhf" placeholder="请输入是否回复" />
      </el-form-item>
      <el-form-item label="回复内容" prop="hfrn">
        <el-input v-model="form.hfrn" type="textarea" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item label="回复人" prop="hfuserid">
        <el-input v-model="form.hfuserid" placeholder="请输入回复人" />
      </el-form-item>
      <el-form-item label="回复时间" prop="fhsj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="form.fhsj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择回复时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="园长指示" prop="yzzs">
        <el-input v-model="form.yzzs" type="textarea" placeholder="请输入内容" />
      </el-form-item>
      <el-form-item label="体验结果
            0不入园
            1入园" prop="tyjg">
        <el-input v-model="form.tyjg" placeholder="请输入体验结果
            0不入园
            1入园" />
      </el-form-item>
      <el-form-item label="入园时间" prop="rysj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="form.rysj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择入园时间"
        ></el-date-picker>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="submitForm">确 定</el-button>
  </div>
</template>

<script>
import { addExperience } from "@/api/benyi/experience";

export default {
  name: "Experience",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {},
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
        sfhf: undefined,
        hfrn: undefined,
        hfuserid: undefined,
        fhsj: undefined,
        yzzs: undefined,
        tyjg: undefined,
        rysj: undefined,
        createTime: undefined,
      };
      this.resetForm("form");
    },

    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateExperience(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addExperience(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
  },
};
</script>