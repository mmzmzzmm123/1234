<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词" prop="title">
        <el-input
          v-model.trim="queryParams.key"
          placeholder="请输入关键词"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="小程序展示状态" prop="showFlag" label-width="200">
        <el-select
          v-model="queryParams.showFlag"
          placeholder="请选择展示状态"
          clearable
          size="small"
        >
          <el-option key="0" label="不展示" value="0"/>
          <el-option key="1" label="展示" value="1"/>
        </el-select>
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
      <el-col :span="1.5">
        <el-button
          type="success"
          size="mini"
          @click="regenerateIndex"
          v-hasPermi="['custom:nutritionQuestion:regenerateIndex']"
        >重新生成索引</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-question"
          @click="handleAskQuestion"
          v-hasPermi="['custom:askQuestion:list']"
        >问题解答</el-button>
      </el-col>-->
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
      <el-table-column label="小程序展示状态" align="center" prop="wxShow" >
        <template slot-scope="scope">
        <el-switch
                v-model="scope.row.wxShow"
                active-text="展示"
                inactive-text="不展示"
                @change="handleWxShow($event, scope.row)"
                >
              </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d} {h}:{i}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="createByName">

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
    <!--<el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
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
    </el-dialog>-->

    <!-- 编辑添加营养知识问答 -->
    <EditOrAddQuestion ref="editOrAddQuestionRef"></EditOrAddQuestion>

    <el-dialog :title="previewQuestion.title" :visible.sync="previewOpen" width="800px" append-to-body>
        <div style="height: 600px; overflow: auto" v-html="previewQuestion.content">
        </div>
    </el-dialog>

    <el-dialog title="用户提问列表" :visible.sync="askQuestionOpen" width="1000px" append-to-body>
        <AskQuestion ref="askQuestionListRef" @refreshList="getList"></AskQuestion>
    </el-dialog>

    
  </div>
</template>

<script>
import { listNutritionQuestion, getNutritionQuestion, delNutritionQuestion, addNutritionQuestion, updateNutritionQuestion, exportNutritionQuestion,updateWxShow,regenerateQuestionIndex } from "@/api/custom/nutritionQuestion";
import Editor from '@/components/Wangeditor';
import AutoHideMessage from "@/components/AutoHideMessage";
import AskQuestion from "../askQuestion";
import EditOrAddQuestion from "@/components/NutrtionQuestion/EditOrAddQuestion";

export default {
  name: "NutritionQuestion",
  components: { Editor,AutoHideMessage,AskQuestion,EditOrAddQuestion },
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
        key: null,
        showFlag: null
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
      },
      askQuestionOpen: false
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
        response.data.data.forEach(element => {
          element.wxShow = element.showFlag == 1 ? true : false;
        });
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
        keyArray: [],
        wxShow: false,
        showFlag: 0
      };
      this.resetForm("form");
    },
    questionContentChange(content){
        //console.log(content);
        this.form.content = content;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        key: ""
      }
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
      //this.reset();
      //this.open = true;
      //this.title = "添加营养小知识";
      this.reset();
      this.$refs.editOrAddQuestionRef.showDialog(this.form, () => {
          this.getList();
      });
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
          keyArray: (response.data.key != null && response.data.key.trim() != '' ) ? response.data.key.split(",") : [],
          showFlag: response.data.showFlag,
          wxShow: response.data.showFlag == 1 ? true : false
        }
        //this.form.keyArray = response.data.key.split(","),
        //this.open = true;
        //this.title = "修改营养小知识";
        this.$refs.editOrAddQuestionRef.showDialog(this.form, () => {
          this.getList();
        });
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
          this.form.showFlag = this.form.wxShow ? 1 : 0;
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
    },
    handleWxShow(newWxshow, row){
      let param = {
        id: row.id,
        showFlag: newWxshow ? 1 : 0
      };
      updateWxShow(param);
    },
    handleAskQuestion(){
      this.askQuestionOpen = true;
      //this.$refs.askQuestionListRef.init();
    },
    onClosedAskQuestion(){
       console.log(this.$refs.askQuestionListRef.isUpdateFlag);
    },
    regenerateIndex(){
        regenerateQuestionIndex().then(response => {
            if(response.code == 200){
                 this.msgSuccess("生成成功");
                 this.getList();
            }
        });
    }
  }
};
</script>
