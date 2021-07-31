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
      <el-form-item label="关键词" prop="keyWordArray" label-width="100px">
          <el-select
            v-model="form.keyWordArray"
            multiple
            filterable
            clearable
            allow-create
            default-first-option
            placeholder="请创建模板关键词，用于模板检索，按回车创建"
            style="width:100%"
          >
            <el-option
              v-for="dict in keyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            >
            </el-option>
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
        keyWordArray: null,
        keyWord: null
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
      keyOptions:[
        {
          dictLabel: "1-7",
          dictValue: "1-7"
        },
        {
          dictLabel: "高血压",
          dictValue: "高血压"
        }
      ]
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
        data.keyWordArray = (data.keyWord != null && data.keyWord != "") ? data.keyWord.split(",") : null;
        this.form = data;
      } else {
        this.title = "创建模板";
      }
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid && this.checkFormat(this.form.keyWordArray)) {
          this.form.keyWord = (this.form.keyWordArray != null && this.form.keyWordArray.length > 0) ? this.form.keyWordArray.join(",") : null;
          this.form.keyWord = this.form.keyWord.replace("，",",");
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
        keyWordArray: null,
        keyWord: null
      };
      this.resetForm("form");
    },
    cancel() {
      this.visible = false;
    },
    checkFormat(keyWordArray){
      let flag = true;
      let msg = "";
      if(keyWordArray != null && keyWordArray.length > 0){
          keyWordArray.forEach((item,index) => {
             if(item.indexOf(",") != -1 || item.indexOf("，") != -1 || item.indexOf(" ") != -1){
                 msg = "关键词中不能含有逗号和空格";
                 flag = false;
             }
          });
      }
      if(!flag){
          this.msgError(msg);
      }
      return flag;
    }
  },
};
</script>
