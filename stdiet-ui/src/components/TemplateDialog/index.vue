<template>
  <el-dialog
    :title="title"
    :visible.sync="visible"
    width="520px"
    append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="模板名称" prop="name" label-width="100px">
        <el-input v-model="form.name" placeholder="请输入模板名称" />
      </el-form-item>
      <el-form-item label="营养师" prop="nutritionistId" label-width="100px">
        <el-select v-model="form.nutritionistId" placeholder="请选择营养师">
          <el-option
            v-for="dict in nutritionistIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="营养师助理" prop="nutriAssisId" label-width="100px">
        <el-select v-model="form.nutriAssisId" placeholder="请选择营养师助理">
          <el-option
            v-for="dict in nutriAssisIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark" label-width="100px">
        <el-input
          v-model="form.remark"
          :rows="4"
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
import { mapState } from "vuex";
export default {
  name: "TemplateDialog",
  data() {
    return {
      title: "",
      form: {
        cusId: 0,
        id: null,
        name: null,
        nutriAssisId: null,
        nutritionistId: null,
        remark: null,
      },
      // 表单校验
      rules: {
        name: [{ required: true, message: "请输入模板名称", trigger: "blur" }],
        nutritionistId: [
          { required: true, message: "请选择营养师", trigger: "blur" },
        ],
        nutriAssisId: [
          { required: true, message: "请选择营养师助理", trigger: "blur" },
        ],
      },
      visible: false,
    };
  },
  computed: {
    ...mapState({
      //营养师
      nutritionistIdOptions: (state) =>
        state.global.nutritionistIdOptions.slice(1),
      //营养师助理
      nutriAssisIdOptions: (state) => state.global.nutriAssisIdOptions.slice(1),
    }),
  },
  methods: {
    showDialog(data) {
      this.visible = true;
      this.reset();
      if (data) {
        this.title = "修改模板";
        this.form = data;
      } else {
        this.title = "创建模板";
      }
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$emit("onConfirm", this.form);
          this.visible = false;
        }
      });
    },
    reset() {
      this.form = {
        cusId: 0,
        id: null,
        name: null,
        nutriAssisId: null,
        nutritionistId: null,
        remark: null,
      };
      this.resetForm("form");
    },
    cancel() {
      this.visible = false;
    },
  },
};
</script>
