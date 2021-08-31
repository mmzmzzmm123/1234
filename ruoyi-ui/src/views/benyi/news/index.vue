<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      label-width="70px"
    >
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="新闻类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="新闻标题" prop="title">
            <el-input
              v-model="queryParams.title"
              type="text"
              placeholder="请输入新闻标题"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item>
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
        v-hasPermi="['benyi:news:add']"
        >新增</el-button
      >

      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:news:remove']"
        >删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      border
      :data="newsList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="新闻标题" align="center" prop="title" />
      <el-table-column
        label="新闻类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column
        label="所属学校"
        align="center"
        prop="deptId"
        :formatter="deptFormat"
      />
      <el-table-column
        label="是否审核"
        align="center"
        prop="ischeck"
        :formatter="ischeckFormat"
      />
      <el-table-column
        label="审核时间"
        align="center"
        prop="checkTime"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:news:edit']"
            v-show="isShow(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:news:remove']"
            >删除</el-button
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

    <!-- 添加或修改新闻中心对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1024px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="新闻标题" prop="title">
          <el-input
            v-model="form.title"
            type="text"
            placeholder="请输入新闻标题"
          />
        </el-form-item>
        <!-- <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="内容" class="ql-snow" prop="content">
          <Editor v-model="form.content" placeholder="请输入内容" />
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
import {
  listNews,
  getNews,
  delNews,
  addNews,
  updateNews,
  exportNews,
} from "@/api/benyi/news";

import Editor from "@/components/Editor";
import { listDept, getDept } from "@/api/system/dept";
import { listUser, getUser } from "@/api/system/user";

export default {
  name: "News",
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
      // 新闻中心表格数据
      newsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型字典
      typeOptions: [],
      // 是否删除字典
      isdelOptions: [],
      // 是否置顶字典
      istopOptions: [],
      // 是否审核字典
      ischeckOptions: [],
      // 创建人字典
      userOptions: [],
      // 学校
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        imgurl: undefined,
        abstractcontent: undefined,
        type: undefined,
        content: undefined,
        isdel: undefined,
        createuserid: undefined,
        deptId: undefined,
        istop: undefined,
        ischeck: undefined,
        checkuserid: undefined,
        checkTime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        content: [{ required: true, message: "内容不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDeptList();
    this.getUserList();
    this.getDicts("sys_dm_newstype").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.isdelOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.istopOptions = response.data;
    });
    this.getDicts("sys_dm_ischeck").then((response) => {
      this.ischeckOptions = response.data;
    });
  },
  methods: {
    isShow(row) {
      if (row.ischeck == "1") {
        return false;
      } else {
        return true;
      }
    },
    /** 查询新闻中心列表 */
    getList() {
      this.loading = true;
      listNews(this.queryParams).then((response) => {
        this.newsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 部门列表
    getDeptList() {
      listDept(null).then((response) => {
        this.deptOptions = response.data;
        //console.log(response.data);
      });
    },
    // 用户列表
    getUserList() {
      listUser(null).then((response) => {
        this.userOptions = response.rows;
      });
    },
    // 字典翻译
    deptFormat(row, column) {
      var actions = [];
      var datas = this.deptOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].deptId == "" + row.deptId) {
          actions.push(datas[key].deptName);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译
    userFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.createuserid) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 是否删除字典翻译
    isdelFormat(row, column) {
      return this.selectDictLabel(this.isdelOptions, row.isdel);
    },
    // 是否置顶字典翻译
    istopFormat(row, column) {
      return this.selectDictLabel(this.istopOptions, row.istop);
    },
    // 是否审核字典翻译
    ischeckFormat(row, column) {
      return this.selectDictLabel(this.ischeckOptions, row.ischeck);
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
        imgurl: undefined,
        abstractcontent: undefined,
        type: undefined,
        content: undefined,
        isdel: undefined,
        createuserid: undefined,
        createTime: undefined,
        deptId: undefined,
        istop: undefined,
        ischeck: undefined,
        checkuserid: undefined,
        checkTime: undefined,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加新闻中心";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getNews(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改新闻中心";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateNews(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNews(this.form).then((response) => {
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
      this.$confirm("是否确认删除选中的新闻中心数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delNews(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    // /** 导出按钮操作 */
    // handleExport() {
    //   const queryParams = this.queryParams;
    //   this.$confirm("是否确认导出所有新闻中心数据项?", "警告", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(function () {
    //       return exportNews(queryParams);
    //     })
    //     .then((response) => {
    //       this.download(response.msg);
    //     })
    //     .catch(function () {});
    // },
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