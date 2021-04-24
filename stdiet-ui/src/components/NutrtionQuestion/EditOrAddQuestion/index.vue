<template>
  <!-- 添加或修改营养知识小问答对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="关键词" prop="keyArray">
          <el-select
            v-model="form.keyArray"
            multiple
            filterable
            clearable
            allow-create
            default-first-option
            placeholder="请创建案例关键词，按回车创建，最多5个"
            style="width: 100%"
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
        <el-form-item label="内容" prop="content">
          <Editor :value="form.content" :isClear="isClear" @change="questionContentChange"></Editor>
        </el-form-item>

         <el-form-item label="展示状态" prop="wxShow">
              <el-switch
                v-model="form.wxShow"
                active-text="小程序展示"
                inactive-text="小程序不展示">
              </el-switch>
              <div>提示：请保证内容正确再展示到小程序</div>
          </el-form-item>    
       
       
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
</template>
<script>
import { addNutritionQuestion, updateNutritionQuestion } from "@/api/custom/nutritionQuestion";
import Editor from '@/components/Wangeditor';
import AutoHideMessage from "@/components/AutoHideMessage";

export default {
  name: "EditOrAddQuestion",
  components: {
      Editor,AutoHideMessage
  },
  data() {
    return {
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ]
      },
      keyOptions:[],
      isClear: false,
      callback: null,
      askQuestionId: undefined
    };
  },
  created(){
        
  },
  methods: {
    showDialog(data, callback, askQuestionId) {
      this.reset();
      if(askQuestionId == undefined || askQuestionId == null){
          if(data.id != undefined && data.id != null){
            this.form = {
                id: data.id,
                title: data.title,
                content: data.content,
                key: data.key,
                keyArray: (data.key != null && data.key.trim() != '' ) ? data.key.split(",") : [],
                showFlag: data.showFlag,
                wxShow: data.showFlag == 1 ? true : false
            }
            this.title = "修改营养小知识";
         }else{
            this.title = "新增营养小知识";
         }
      }else{
          this.form = {
            title: data.title,
            askQuestionId: askQuestionId
          }
          this.title = "问题解答并新增小知识";
      }
      this.callback = callback;
      this.open = true;
    },
    reset(){
        this.form = {
            id: null,
            title: null,
            content: null,
            key: null,
            keyArray: [],
            wxShow: false,
            showFlag: 0,
            askQuestionId: null
        };
        this.resetForm("form");
    },
    questionContentChange(content){
        console.log(content);
        this.form.content = content;
    },
    /** 提交按钮 */
    submitForm() {
       this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.keyArray = this.form.keyArray == null ? [] : this.form.keyArray;
          //判断案例关键词
          if (this.form.keyArray.length > 5) {
            this.$message({ message: "关键词最多5个", type: "warning" });
            return;
          }
          //判断内容
          if (this.form.content == null || this.form.content.trim().length == 0) {
            this.$message({ message: "内容不能为空", type: "warning" });
            return;
          }
          this.form.key = this.form.keyArray.join(",");
          this.form.showFlag = this.form.wxShow ? 1 : 0;
          if (this.form.id != null) {
            updateNutritionQuestion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.callback && this.callback();
              }
            });
          } else {
            addNutritionQuestion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.callback && this.callback();
              }
            });
          }
        }
      });
    },
    onClosed() {
      this.reset();
    },
    cancel(){
        this.open = false;
        this.reset();
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
