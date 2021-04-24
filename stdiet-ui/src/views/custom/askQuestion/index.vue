<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!--<el-form-item label="问题内容" prop="question">
        <el-input
          v-model="queryParams.question"
          placeholder="请输入问题内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已回答，0未回答，1已回答" prop="replyFlag">
        <el-input
          v-model="queryParams.replyFlag"
          placeholder="请输入是否已回答，0未回答，1已回答"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:askQuestion:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="askQuestionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="问题内容" align="center" prop="question" />
      <el-table-column label="状态" align="center" prop="replyFlag" >
          <template slot-scope="scope">
                <el-tag :type="scope.row.replyFlag == 0 ? 'danger' : (scope.row.replyFlag == 1 ? 'success' : 'info')">
              {{scope.row.replyFlag == 0 ? '未解答' : (scope.row.replyFlag == 1 ? '已解答' : '已关闭')}}
              </el-tag>
          </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
            <el-button
            :disabled="scope.row.replyFlag != 0"
            size="normal"
            type="text"
            icon="el-icon-view"
            @click="replyQUestion(scope.row)"
            v-hasPermi="['custom:askQuestion:edit']"
          >解答</el-button>
            <el-button
            :disabled="scope.row.replyFlag != 0"
            size="normal"
            type="text"
            icon="el-icon-close"
            @click="handleCloseQuestion(scope.row)"
            v-hasPermi="['custom:askQuestion:edit']"
          >关闭</el-button>
          <el-button
            size="normal"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:askQuestion:remove']"
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

    <!-- 解答提问对话框 -->
    <EditOrAddQuestion ref="editOrAddQuestionRef"></EditOrAddQuestion>
  </div>
</template>

<script>
import { listAskQuestion, getAskQuestion, delAskQuestion, addAskQuestion, updateAskQuestion, exportAskQuestion } from "@/api/custom/askQuestion";
import EditOrAddQuestion from "@/components/NutrtionQuestion/EditOrAddQuestion";
export default {
  name: "AskQuestion",
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
      // 营养小知识提问表格数据
      askQuestionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        openid: null,
        question: null,
        replyFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  components: { EditOrAddQuestion },
  created() {
    this.getList();
  },
  methods: {
    /** 查询营养小知识提问列表 */
    getList() {
      this.isUpdateFlag = false;
      this.loading = true;
      listAskQuestion(this.queryParams).then(response => {
        this.askQuestionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    init() {
        this.isUpdateFlag = false;
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        openid: null,
        question: null,
        replyFlag: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        delFlag: null
      };
      this.resetForm("form");
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加营养小知识提问";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAskQuestion(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改营养小知识提问";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAskQuestion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addAskQuestion(this.form).then(response => {
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
      this.$confirm('是否确认删除营养小知识提问编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delAskQuestion(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有营养小知识提问数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportAskQuestion(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    //关闭问题
    handleCloseQuestion(row) {
        let param = {
            'id': row.id,
            'replyFlag': 2
        }
        updateAskQuestion(param).then(response => {
            if (response.code === 200) {
                this.msgSuccess("关闭成功");
                this.open = false;
                this.getList();
            }
        });
    },
    replyQUestion(row){
        this.$refs.editOrAddQuestionRef.showDialog({'id': null, 'title': row.question}, () => {
            this.getList();
            this.refreshQuestion();
        }, row.id);
    },
    refreshQuestion(){
        this.$emit('refreshList');
    }
  }
};
</script>