<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="活动类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择活动类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in calendartypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="活动时间" prop="activitytime">
            <el-date-picker
              class="my-date-picker"
              clearable
              size="small"
              v-model="queryParams.activitytime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择活动时间"
            ></el-date-picker>
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
        v-hasPermi="['benyi:calendar:add']"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:calendar:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:calendar:remove']"
        >删除</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['benyi:calendar:export']"
        >导出</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="calendarList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="标识" align="center" prop="id" /> -->
      <el-table-column fixed label="名称" align="center" prop="name" />
      <el-table-column
        label="活动类型"
        align="center"
        :formatter="calendartypeFormat"
        prop="type"
      />
      <el-table-column
        label="活动开始时间"
        align="center"
        prop="activitytime"
        width="180"
      ></el-table-column>
      <el-table-column
        label="活动结束时间"
        align="center"
        prop="activityendtime"
        width="180"
      ></el-table-column>
      <el-table-column label="活动样式颜色" align="center" prop="stylecolor" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:calendar:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:calendar:remove']"
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

    <!-- 添加或修改园历管理(本一)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input
            v-model="form.name"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="活动类型">
          <el-select
            v-model="form.type"
            placeholder="请选择活动类型"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in calendartypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="起始时间" prop="activitytime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.activitytime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动起始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="activityendtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.activityendtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动结束时间"
          ></el-date-picker>
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
  listCalendar,
  getCalendar,
  delCalendar,
  addCalendar,
  updateCalendar,
  exportCalendar,
} from "@/api/benyi/calendar";

export default {
  name: "Calendar",
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
      // 园历管理(本一)表格数据
      calendarList: [],
      //活动类型
      calendartypeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        activitytime: undefined,
        activityendtime: undefined,
        stylecolor: undefined,
        createuserid: undefined,
        createtime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_schoolcalendartype").then((response) => {
      this.calendartypeOptions = response.data;
    });
  },
  methods: {
    // 学校园历类型--字典状态字典翻译
    calendartypeFormat(row, column) {
      return this.selectDictLabel(this.calendartypeOptions, row.type);
    },
    /** 查询园历管理(本一)列表 */
    getList() {
      this.loading = true;
      listCalendar(this.queryParams).then((response) => {
        this.calendarList = response.rows;
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
        name: undefined,
        type: undefined,
        activitytime: undefined,
        activityendtime: undefined,
        stylecolor: undefined,
        createuserid: undefined,
        createtime: undefined,
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
      this.title = "添加园历管理(本一)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCalendar(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改园历管理(本一)";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCalendar(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCalendar(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
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
        '是否确认删除园历管理(本一)数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delCalendar(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有园历管理(本一)数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportCalendar(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
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