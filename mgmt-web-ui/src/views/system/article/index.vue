<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="appid" prop="appid">
        <el-select v-model="queryParams.appid" placeholder="请选择appid" clearable>
          <el-option
            v-for="dict in dict.type.sys_type_list"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="articleType">
        <el-select v-model="queryParams.articleType" placeholder="请选择类型" clearable>
          <el-option
            v-for="dict in dict.type.sys_article_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="简短标题" prop="smallTitle">
        <el-input
          v-model="queryParams.smallTitle"
          placeholder="请输入简短标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完整标题" prop="bigTitle">
        <el-input
          v-model="queryParams.bigTitle"
          placeholder="请输入完整标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源" prop="articleSource">
        <el-select v-model="queryParams.articleSource" placeholder="请选择来源" clearable>
          <el-option
            v-for="dict in dict.type.sys_article_source"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序号" prop="sortNo">
        <el-input
          v-model="queryParams.sortNo"
          placeholder="请输入排序号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="星标数" prop="starNum">
        <el-input
          v-model="queryParams.starNum"
          placeholder="请输入星标数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:article:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:article:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:article:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:article:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="articleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="appid" align="center" prop="appid">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_type_list" :value="scope.row.appid"/>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="articleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_article_type" :value="scope.row.articleType"/>
        </template>
      </el-table-column>
      <el-table-column label="简短标题" align="center" prop="smallTitle" />
      <el-table-column label="完整标题" align="center" prop="bigTitle" />
      <el-table-column label="封面图" align="center" prop="faceUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.faceUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="缩略图" align="center" prop="faceThum" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.faceThum" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="articleContent" />
      <el-table-column label="来源" align="center" prop="articleSource">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_article_source" :value="scope.row.articleSource"/>
        </template>
      </el-table-column>
      <el-table-column label="排序号" align="center" prop="sortNo" />
      <el-table-column label="星标数" align="center" prop="starNum" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:article:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:article:remove']"
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

    <!-- 添加或修改app内容管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="appid" prop="appid">
          <el-select v-model="form.appid" placeholder="请选择appid">
            <el-option
              v-for="dict in dict.type.sys_type_list"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="articleType">
          <el-select v-model="form.articleType" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.sys_article_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="简短标题" prop="smallTitle">
          <el-input v-model="form.smallTitle" placeholder="请输入简短标题" />
        </el-form-item>
        <el-form-item label="完整标题" prop="bigTitle">
          <el-input v-model="form.bigTitle" placeholder="请输入完整标题" />
        </el-form-item>
        <el-form-item label="封面图" prop="faceUrl">
          <image-upload v-model="form.faceUrl"/>
        </el-form-item>
        <el-form-item label="缩略图" prop="faceThum">
          <image-upload v-model="form.faceThum"/>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.articleContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="来源" prop="articleSource">
          <el-select v-model="form.articleSource" placeholder="请选择来源">
            <el-option
              v-for="dict in dict.type.sys_article_source"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序号" prop="sortNo">
          <el-input v-model="form.sortNo" placeholder="请输入排序号" />
        </el-form-item>
        <el-form-item label="星标数" prop="starNum">
          <el-input v-model="form.starNum" placeholder="请输入星标数" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listArticle, getArticle, delArticle, addArticle, updateArticle } from "@/api/system/article";

export default {
  name: "Article",
  dicts: ['sys_article_type', 'sys_article_source', 'sys_type_list'],
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
      // app内容管理表格数据
      articleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        appid: null,
        articleType: null,
        smallTitle: null,
        bigTitle: null,
        faceUrl: null,
        faceThum: null,
        articleContent: null,
        articleSource: null,
        sortNo: null,
        starNum: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        appid: [
          { required: true, message: "appid不能为空", trigger: "change" }
        ],
        articleType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        smallTitle: [
          { required: true, message: "简短标题不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询app内容管理列表 */
    getList() {
      this.loading = true;
      listArticle(this.queryParams).then(response => {
        this.articleList = response.rows;
        this.total = response.total;
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
        appid: null,
        articleType: null,
        smallTitle: null,
        bigTitle: null,
        faceUrl: null,
        faceThum: null,
        articleContent: null,
        articleSource: null,
        sortNo: null,
        starNum: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null,
        remark: null
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
      this.title = "添加app内容管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getArticle(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改app内容管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateArticle(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArticle(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除app内容管理编号为"' + ids + '"的数据项？').then(function() {
        return delArticle(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/article/export', {
        ...this.queryParams
      }, `article_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
