<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="用户ID" prop="userid">
        <el-input
          v-model="queryParams.userid"
          placeholder="请输入所属用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入书签标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="书签地址" prop="url">-->
<!--        <el-input-->
<!--          v-model="queryParams.url"-->
<!--          placeholder="请输入书签地址"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="书签地址" prop="urls">-->
<!--        <el-input-->
<!--          v-model="queryParams.urls"-->
<!--          placeholder="请输入书签地址"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="书签描述" prop="description">-->
<!--        <el-input-->
<!--          v-model="queryParams.description"-->
<!--          placeholder="请输入书签描述"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="书签描述" prop="image">-->
<!--        <el-input-->
<!--          v-model="queryParams.image"-->
<!--          placeholder="请输入书签描述"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="标签" prop="label">-->
<!--        <el-input-->
<!--          v-model="queryParams.label"-->
<!--          placeholder="请输入标签"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="分类ID" prop="menuId">-->
<!--        <el-input-->
<!--          v-model="queryParams.menuId"-->
<!--          placeholder="请输入分类ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="点赞数" prop="zcount">-->
<!--        <el-input-->
<!--          v-model="queryParams.zcount"-->
<!--          placeholder="请输入点赞数"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="0 未删除 1表示删除" prop="idelete">-->
<!--        <el-input-->
<!--          v-model="queryParams.idelete"-->
<!--          placeholder="请输入0 未删除 1表示删除"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="0公开显示 1隐藏显示 2好友显示 3稍后再看" prop="start">-->
<!--        <el-input-->
<!--          v-model="queryParams.start"-->
<!--          placeholder="请输入0公开显示 1隐藏显示 2好友显示 3稍后再看"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['bookmark:bookmark:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bookmark:bookmark:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bookmark:bookmark:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bookmark:bookmark:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="bookmarkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="bookmarkId" />
      <el-table-column label="用户ID" align="center" prop="userid" />
      <el-table-column label="书签标题" align="center" prop="title" />
      <el-table-column label="标签" align="center" prop="label" />
      <el-table-column label="点赞数" align="center" prop="zcount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bookmark:bookmark:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bookmark:bookmark:remove']"
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

    <!-- 添加或修改书签管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属用户ID" prop="userid">
          <el-input v-model="form.userid" placeholder="请输入所属用户ID" />
        </el-form-item>
        <el-form-item label="书签标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入书签标题" />
        </el-form-item>
        <el-form-item label="书签地址" prop="url">
          <el-input v-model="form.url" placeholder="请输入书签地址" />
        </el-form-item>
        <el-form-item label="书签地址" prop="urls">
          <el-input v-model="form.urls" placeholder="请输入书签地址" />
        </el-form-item>
        <el-form-item label="书签描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入书签描述" />
        </el-form-item>
        <el-form-item label="书签描述" prop="image">
          <el-input v-model="form.image" placeholder="请输入书签描述" />
        </el-form-item>
        <el-form-item label="标签" prop="label">
          <el-input v-model="form.label" placeholder="请输入标签" />
        </el-form-item>
        <el-form-item label="分类ID" prop="menuId">
          <el-input v-model="form.menuId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="点赞数" prop="zcount">
          <el-input v-model="form.zcount" placeholder="请输入点赞数" />
        </el-form-item>
        <el-form-item label="0 未删除 1表示删除" prop="idelete">
          <el-input v-model="form.idelete" placeholder="请输入0 未删除 1表示删除" />
        </el-form-item>
        <el-form-item label="0公开显示 1隐藏显示 2好友显示 3稍后再看" prop="start">
          <el-input v-model="form.start" placeholder="请输入0公开显示 1隐藏显示 2好友显示 3稍后再看" />
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
import { listBookmark, getBookmark, delBookmark, addBookmark, updateBookmark, exportBookmark } from "@/api/bookmark/bookmark";

export default {
  name: "Bookmark",
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
      // 总条数
      total: 0,
      // 书签管理表格数据
      bookmarkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userid: undefined,
        title: undefined,
        url: undefined,
        urls: undefined,
        description: undefined,
        image: undefined,
        label: undefined,
        menuId: undefined,
        zcount: undefined,
        idelete: undefined,
        start: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 查询书签管理列表 */
    getList() {
      this.loading = true;
      listBookmark(this.queryParams).then(response => {
        this.bookmarkList = response.rows;
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
        bookmarkId: undefined,
        userid: undefined,
        title: undefined,
        url: undefined,
        urls: undefined,
        description: undefined,
        image: undefined,
        label: undefined,
        menuId: undefined,
        zcount: undefined,
        idelete: undefined,
        start: undefined,
        createTime: undefined
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
      this.ids = selection.map(item => item.bookmarkId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加书签管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bookmarkId = row.bookmarkId || this.ids
      getBookmark(bookmarkId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改书签管理";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bookmarkId != undefined) {
            updateBookmark(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addBookmark(this.form).then(response => {
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
      const bookmarkIds = row.bookmarkId || this.ids;
      this.$confirm('是否确认删除书签管理编号为"' + bookmarkIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBookmark(bookmarkIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有书签管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportBookmark(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
