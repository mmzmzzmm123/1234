<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="日历简称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入日历简称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <el-form-item label="状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in enabledOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['base:calendar:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:calendar:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:calendar:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:calendar:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border fit highlight-current-row :data="calendarList" @selection-change="handleSelectionChange" @sort-change='tableSortChange' @row-dblclick="handleUpdate">
    <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="行号" width="80" align="center">
            <template slot-scope="scope">{{scope.$index + 1}}</template>
    </el-table-column>
      <el-table-column label="UUID" align="center" prop="id" v-if = "false" />
      <el-table-column label="日历代码" align="center" prop="codeid" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="日历简称" align="center" prop="name" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="状态" align="center" prop="enabled" :formatter="enabledFormat" />
      <el-table-column label="备注" align="center" prop="remark" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="small"
            @click="handleAddCalendar(scope.row)"
            v-hasPermi="['base:calendar:edit']"
          >考勤</el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:calendar:edit']"
          >修改</el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:calendar:remove']"
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

    <!-- 添加或修改考勤日历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日历代码" prop="codeid">
          <el-input v-model="form.codeid" placeholder="请输入日历代码" />
        </el-form-item>
        <el-form-item label="日历简称" prop="name">
          <el-input v-model="form.name" placeholder="请输入日历简称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.enabled" placeholder="请选择状态">
            <el-option
              v-for="dict in enabledOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改日历对话框 -->
    <el-dialog :title="calendar" :visible.sync="calendarOpen" width="800px" >
      <el-calendar >
        <template
          slot="dateCell"
          slot-scope="{date, data}">
          <p :class="data.isSelected ? 'is-selected' : ''">
            {{ data.day.split('-').slice(2).join('-') }} {{ data.isSelected ? '休息' : '上班'}}
          </p>
        </template>
      </el-calendar>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCalendarForm">确 定</el-button>
        <el-button @click="cancelCalendar">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCalendar, getCalendar, delCalendar, addCalendar, updateCalendar, exportCalendar } from "@/api/base/calendar";
import { listCalendarItem, getCalendarItem, addCalendarItem, updateCalendarItem } from "@/api/base/calendarItem";

export default {
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
      // 考勤日历表格数据
      calendarList: [],
      // 弹出层标题
      title: "",
      // 弹出日历框标题
      calendar: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层
      calendarOpen: false,
      // 状态字典
      enabledOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: undefined,
        isAsc: undefined,
        name: undefined,
        enabled: undefined,
      },
      // 日历表单参数
      calendarForm: {
        id: undefined,
        parentId: undefined,
        work: undefined,
        workDay: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        codeid: [
          { required: true, message: "日历代码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "日历简称不能为空", trigger: "blur" }
        ],
        enabled: [
          { required: true, message: "状态（0正常 1停用）不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_enable").then(response => {
      this.enabledOptions = response.data;
    });
  },
  methods: {
      /** 通用考勤日历排序 */
      tableSortChange(column) {
          this.queryParams.pageNum = 1;
          if (column.order === 'descending') {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'desc';
          } else {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'asc';
          }
          this.getList();
      },
    /** 查询考勤日历列表 */
    getList() {
      this.loading = true;
      listCalendar(this.queryParams).then(response => {
        this.calendarList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    enabledFormat(row, column) {
      return this.selectDictLabel(this.enabledOptions, row.enabled);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 日历取消按钮
    cancelCalendar() {
      this.calendarOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        codeid: undefined,
        name: undefined,
        enabled: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    handleSelected(day) {
      console.log(day);
    },
    /** 新增日历操作 */
    handleAddCalendar(row) {
      this.reset();
      const id = row.id || this.ids;
      getCalendarItem(id).then(response => {
        this.calendarForm = response.data;
        this.calendarOpen = true;
        this.calendar = "考勤日历";
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考勤日历";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCalendar(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考勤日历";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCalendar(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCalendar(this.form).then(response => {
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
    /** 提交日历明细按钮 */
    submitCalendarForm: function() {
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除考勤日历编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCalendar(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有考勤日历数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCalendar(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
