<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词" prop="title">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入关键词"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
     
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:nutritionQuestion:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:nutritionQuestion:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:nutritionQuestion:remove']"
        >删除</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:nutritionQuestion:export']"
        >导出</el-button>
      </el-col>-->
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nutritionQuestionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标题" align="center" prop="title" >
        <template slot-scope="scope">
        <AutoHideMessage :data="scope.row.title == null ? '' : (scope.row.title+'')" :maxLength="10"/>
        </template>
      </el-table-column>
      <el-table-column label="关键词" align="center" prop="key" >
        <template slot-scope="scope">
        <AutoHideMessage :data="scope.row.key == null ? '' : (scope.row.key+'')" :maxLength="10"/>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="content" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="clickPreviewQuestion(scope.row)"
          >预览</el-button>
        <!--<AutoHideMessage :data="scope.row.content == null ? '' : (scope.row.content+'')" :maxLength="10"/>-->
        </template>
      </el-table-column>
      
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          
          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:nutritionQuestion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:nutritionQuestion:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

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
        <!--<el-form-item label="内容" prop="content" >
            <el-input
              type="textarea"
              :rows="4"
              maxlength="1000"
              show-word-limit
              placeholder="请输入内容"
              v-model="form.content">
            </el-input>
          </el-form-item>-->

       <!-- <el-form-item label="关键词" prop="key">
          <el-input v-model="form.key" placeholder="请输入关键词" />
        </el-form-item>-->
       
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="previewQuestion.title" :visible.sync="previewOpen" width="800px" append-to-body>
        <div style="height: 600px; overflow: auto" v-html="previewQuestion.content">
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNutritionQuestion, getNutritionQuestion, delNutritionQuestion, addNutritionQuestion, updateNutritionQuestion, exportNutritionQuestion } from "@/api/custom/nutritionQuestion";
import Editor from '@/components/Wangeditor';
  import AutoHideMessage from "@/components/AutoHideMessage";
export default {
  name: "NutritionQuestion",
  components: { Editor,AutoHideMessage },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 营养知识小问答表格数据
      nutritionQuestionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        key: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "内容不能为空", trigger: "blur" },
        ]
      },
      keyOptions:[],
      isClear: false,
      previewOpen: false,
      previewQuestion: {
        title: "",
        content: ""
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询营养知识小问答列表 */
    getList() {
      this.loading = true;
      listNutritionQuestion(this.queryParams).then(response => {
        this.nutritionQuestionList = response.data.data;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        content: null,
        key: null,
        keyArray: []
      };
      this.resetForm("form");
    },
    questionContentChange(content){
        console.log(content);
        this.form.content = content;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    clickPreviewQuestion(row){
      this.previewQuestion.title = row.title;
      this.previewQuestion.content = row.content;
      this.previewOpen = true;
    },
    /** 新增按钮操作 */
    handleAdd() {
      //this.isClear = true;
      this.reset();
      this.open = true;
      this.title = "添加营养小知识";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNutritionQuestion(id).then(response => {
        this.form = {
          id: response.data.id,
          title: response.data.title,
          content: response.data.content,
          key: response.data.key,
          keyArray: response.data.key.split(",")
        }
        //this.form.keyArray = response.data.key.split(","),
        this.open = true;
        this.title = "修改营养小知识";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          //判断案例关键词
          if (this.form.keyArray.length > 5) {
            this.$message({ message: "关键词最多5个", type: "warning" });
            return;
          }
          this.form.key = this.form.keyArray.join(",");
          if (this.form.id != null) {
            updateNutritionQuestion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNutritionQuestion(this.form).then(response => {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除编号为"' + ids + '"的营养小知识?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNutritionQuestion(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有营养知识小问答数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportNutritionQuestion(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
