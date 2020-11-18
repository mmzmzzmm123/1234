<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="周计划" prop="wid">
        <el-select v-model="queryParams.wid" size="small">
          <el-option
            v-for="item in planweekOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动类型" prop="activitytype">
        <el-select
          v-model="queryParams.activitytype"
          placeholder="请选择活动类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in activitytypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动时间" prop="activitytime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.activitytime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择活动时间"
        ></el-date-picker>
      </el-form-item>
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
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:planweek:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:planweek:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:planweek:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:planweek:export']"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="planweekitemList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="活动内容" align="center" prop="content">
        <template slot-scope="scope">
          <div v-html="scope.row.content"></div>
        </template>
      </el-table-column>
      <el-table-column
        label="所属计划"
        align="center"
        prop="wid"
        :formatter="themePlanFormat"
      />
      <el-table-column
        label="活动类型"
        align="center"
        prop="activitytype"
        :formatter="activitytypeFormat"
      />
      <el-table-column
        label="活动时间"
        align="center"
        prop="activitytime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.activitytime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="星期" align="center" prop="day" />
      <el-table-column
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
            v-hasPermi="['benyi:planweek:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:planweek:remove']"
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

    <!-- 添加或修改周计划(家长和教育部门细化)对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1024px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属计划" prop="wid">
          <el-select v-model="form.wid" size="small" :disabled="true">
            <el-option
              v-for="item in planweekOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="活动类型" prop="activitytype">
          <el-select v-model="form.activitytype" placeholder="请选择活动类型">
            <el-option
              v-for="dict in activitytypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动时间" prop="activitytime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.activitytime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动时间"
            >></el-date-picker
          >
        </el-form-item>
        <el-form-item label="活动内容" prop="content">
          <Editor v-model="form.content" placeholder="请输入活动内容" />
        </el-form-item>
        <!-- <el-form-item label="星期" prop="day">
          <el-input v-model="form.day" placeholder="请输入星期几" />
        </el-form-item> -->
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
  listPlanweekitem,
  getPlanweekitem,
  delPlanweekitem,
  addPlanweekitem,
  updatePlanweekitem,
  exportPlanweekitem,
} from "@/api/benyi/planweekitem";

import Editor from "@/components/Editor";

import { getPlanweek, listPlanweek } from "@/api/benyi/planweek";

const weekArr = [
  "星期日",
  "星期一",
  "星期二",
  "星期三",
  "星期四",
  "星期五",
  "星期六",
];
export default {
  name: "Planweekitem",
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
      // 周计划(家长和教育部门细化)表格数据
      planweekitemList: [],
      // 周计划
      planweekOptions: [],
      // 默认周计划
      defaultWeekType: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动类型字典
      activitytypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wid: undefined,
        activitytype: undefined,
        content: undefined,
        activitytime: undefined,
        createuserid: undefined,
        updateuserid: undefined,
        day: undefined,
      },
      // 日期控件 只显示今天和今天以后一周时间区间
      pickerOptions7: {
        disabledDate(time) {
          let curDate = new Date().getTime();
          let three = 7 * 24 * 3600 * 1000;
          let threeMonths = curDate + three;
          let datestart = Date.now() - 86400000;
          return time.getTime() < datestart || time.getTime() > threeMonths;
        },
      },

      // 表单参数
      form: {},
      // 表单校验
      rules: {
        activitytype: [
          { required: true, message: "活动类型不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "活动内容不能为空", trigger: "blur" },
        ],
        activitytime: [
          { required: true, message: "活动时间不能为空", trigger: "blur" },
        ],
        // day: [
        //   { required: true, message: "星期不能为空", trigger: "blur" }
        // ]
      },
    };
  },
  created() {
    const planweekid = this.$route.params && this.$route.params.id;
    this.getPlanweek2(planweekid);
    this.getPlanWeekList();
    this.getDicts("sys_dm_qyhdxs").then((response) => {
      this.activitytypeOptions = response.data;
    });
  },
  methods: {
    // 周计划
    getPlanweek2(planweekid) {
      getPlanweek(planweekid).then((response) => {
        this.queryParams.wid = response.data.id;
        this.defaultWeekType = response.data.id;
        this.getList();
      });
    },
    /** 查询周计划(家长和教育部门细化)列表 */
    getList() {
      this.loading = true;
      listPlanweekitem(this.queryParams).then((response) => {
        this.planweekitemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询周计划选项
    getPlanWeekList() {
      listPlanweek().then((response) => {
        this.planweekOptions = response.rows;
      });
    },
    // 周计划字典翻译
    themePlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.planweekOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.wid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 活动类型字典翻译
    activitytypeFormat(row, column) {
      return this.selectDictLabel(this.activitytypeOptions, row.activitytype);
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
        wid: undefined,
        activitytype: undefined,
        content: undefined,
        activitytime: undefined,
        createuserid: undefined,
        createTime: undefined,
        updateuserid: undefined,
        updateTime: undefined,
        day: undefined,
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
      this.queryParams.wid = this.defaultWeekType;
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
      this.title = "添加周计划(家长和教育部门细化)";
      this.form.wid = this.queryParams.wid;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPlanweekitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改周计划(家长和教育部门细化)";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePlanweekitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPlanweekitem(this.form).then((response) => {
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
        '是否确认删除周计划(家长和教育部门细化)数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delPlanweekitem(ids);
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
      this.$confirm(
        "是否确认导出所有周计划(家长和教育部门细化)数据项?",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return exportPlanweekitem(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>