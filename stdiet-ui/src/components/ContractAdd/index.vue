<template>
  <!-- 添加或修改合同对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="调理项目" prop="projectId">
        <el-select
          v-model="form.projectId"
          placeholder="请选择调理项目"
          filterable
          clearable
          size="small"
        >
          <el-option
            v-for="dict in conditioningProjectIdOption"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <!--<el-form-item label="客户姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名"/>
      </el-form-item>-->
      <el-form-item label="金额" prop="amount" style="width: 300px">
        <el-input v-model="form.amount" placeholder="请输入金额" />
      </el-form-item>
      <el-form-item
        label="服务承诺"
        prop="servePromise"
        v-show="form.projectId == 0 || form.projectId == 14"
      >
        <el-input
          style="width: 200px"
          v-model="form.servePromise"
          placeholder="请输入服务承诺"
        /><span style="margin-left: 5px">斤</span>
      </el-form-item>
      <el-form-item label="营养师" prop="nutritionistId">
        <el-select
          v-model="form.nutritionistId"
          placeholder="请选择营养师"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务时间" prop="serveTime">
        <el-select v-model="form.serveTime" placeholder="请选择服务时间">
          <el-option
            v-for="dict in serveTimeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="form.remark"
          type="textarea"
          placeholder="请输入内容"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { addContract } from "@/api/custom/contract";
import { mapState } from "vuex";

export default {
  name: "ContractAdd",
  components: {},
  props: {},
  data() {
    const checkServePromise = (rule, value, callback) => {
      if (this.form.projectId == 0 && !value) {
        return callback(new Error("请输入服务承诺"));
      }
      callback();
    };
    return {
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      callback: undefined,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        projectId: [
          { required: true, message: "请选择调理项目", trigger: "blur" },
        ],
        /*name: [
          {required: true, message: "请输入客户姓名", trigger: "blur"}
        ],*/
        amount: [
          { required: true, message: "请输入签订金额", trigger: "blur" },
          {
            required: true,
            trigger: "blur",
            pattern: /^[1-9]\d*$/,
            message: "签订金额格式不正确",
          },
        ],
        servePromise: [
          { required: true, trigger: "blur", validator: checkServePromise },
        ],
        serveTime: [
          { required: true, message: "请选择服务时间", trigger: "blur" },
        ],
        nutritionistId: [
          { required: true, message: "请选择营养师", trigger: "blur" },
        ],
      },
      conditioningProjectIdOption: [],
      serveTimeOptions: [],
      // nutritionistIdOptions: [],
    };
  },
  computed: {
    ...mapState({
      nutritionistIdOptions: (state) =>
        state.global.nutritionistIdOptions.slice(1),
    }),
  },
  created() {
    this.getDicts("cus_serve_time").then((response) => {
      this.serveTimeOptions = response.data;
    });
    this.getDicts("conditioning_project").then((response) => {
      this.conditioningProjectIdOption = response.data;
    });
  },
  methods: {
    showDialog(data, callback) {
      this.callback = callback;
      this.reset(data);
      this.title = "新增" + `「${data.customer}」客户合同`;
      this.open = true;
    },
    // 表单重置
    reset(obj) {
      const defaultNutritionist = this.nutritionistIdOptions.find(
        (opt) => opt.dictValue === obj.nutritionistId
      );
      const defaultProjectIdOption = this.conditioningProjectIdOption.find(
        (opt) => opt.remark === "default"
      );

      this.form = {
        id: null,
        customerId: obj.customerId,
        projectId: defaultProjectIdOption
          ? parseInt(defaultProjectIdOption.dictValue)
          : null,
        name: obj.customer,
        phone: null,
        serveTime: null,
        amount: null,
        path: null,
        createBy: null,
        nutritionistId: defaultNutritionist
          ? parseInt(defaultNutritionist.dictValue)
          : null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.tutor = this.selectDictLabel(
            this.nutritionistIdOptions,
            this.form.nutritionistId
          );
          if (this.form.projectId != 0 && this.form.projectId != 14) {
            this.form.servePromise = null;
          }
          addContract(this.form).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("新增成功");
              this.open = false;
              this.callback && this.callback();
            }
          });
        }
      });
    },
  },
};
</script>
