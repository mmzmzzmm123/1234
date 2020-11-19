<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="90px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="通知书标题" prop="title">
            <el-input
              v-model="queryParams.title"
              placeholder="请输入标题"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="通知书类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择">
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="4">
          <el-form-item class="no-margin">
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:recruitstudentsnotice:add']"
        >新增</el-button
      >
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:recruitstudentsnotice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:recruitstudentsnotice:remove']"
        >删除</el-button>
      </el-col>-->
    </div>

    <el-table
      border
      v-loading="loading"
      :data="noticeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="标题" align="center" prop="title" />
      <el-table-column
        label="类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="180"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleCopy(scope.row)"
            v-hasPermi="['benyi:recruitstudentsnotice:edit']"
            v-show="!selectable(scope.row)"
            >复制</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:recruitstudentsnotice:edit']"
            v-show="selectable(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:recruitstudentsnotice:remove']"
            v-show="selectable(scope.row)"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row, scope.index)"
            v-hasPermi="['benyi:recruitstudentsnotice:query']"
            >详细</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改入园通知书对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="big-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <Editor v-model="form.content" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-show="isshow" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listNotice,
  getNotice,
  delNotice,
  addNotice,
  updateNotice,
  copyNotice,
} from "@/api/benyi/recruitstudentsnotice";

import Editor from "@/components/Editor";

export default {
  name: "Notice",
  components: {
    Editor,
  },
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
      // 入园通知书表格数据
      noticeList: [],
      //字典翻译
      typeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      isshow: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        content: undefined,
        type: undefined,
        schoolid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_noticetype").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    //能否选中
    selectable(row) {
      if (row.type == "1") {
        return false;
      } else {
        return true;
      }
    },
    // 字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    /** 查询入园通知书列表 */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then((response) => {
        this.noticeList = response.rows;
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
        id: undefined,
        title: undefined,
        content: undefined,
        type: undefined,
        schoolid: undefined,
        createTime: undefined,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.reset();
      const id = row.id || this.ids;
      getNotice(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "入园通知书详情";
        this.isshow = false;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入园通知书";
      this.isshow = true;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getNotice(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入园通知书";
        this.isshow = true;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateNotice(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNotice(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除入园通知书编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delNotice(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 复制按钮操作 */
    handleCopy(row) {
      const id = row.id || this.ids;
      this.$confirm('确认复制入园通知书编号为"' + id + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return copyNotice(id);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("复制成功");
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
.edit-btns {
  .el-button {
    display: block;
    margin: 0 auto;
  }
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>