<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="月计划" prop="mpid">
        <el-select v-model="queryParams.mpid" size="small">
          <el-option
            v-for="item in themeMonthPlanOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="周次" prop="zc">
        <el-input
          v-model="queryParams.zc"
          placeholder="请输入周次"
          clearable
          size="small"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:thememonthplan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:thememonthplan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:thememonthplan:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="weekplanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="所属月计划" align="center" prop="mpid" :formatter="themeMonthPlanFormat"/>
      <el-table-column label="周次" align="center" prop="zc" />
      <el-table-column label="开始时间" align="center" prop="starttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动" align="center" prop="activityid" />
      <el-table-column label="家长支持" align="center" prop="jzzc" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:thememonthplan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:thememonthplan:remove']"
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

    <!-- 添加或修改主题整合周计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="月计划" prop="mpid">
          <el-select v-model="form.mpid" size="small" :disabled="true">
            <el-option
              v-for="item in themeMonthPlanOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="周次" prop="zc">
          <el-input-number v-model="form.zc" placeholder="请输入周次" />
        </el-form-item>
        <el-form-item label="开始时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="选择活动" prop="activityid">
          <el-checkbox-group
            v-model="themeactivityList"
            :max="max"
            @change="getThemeActivityIdValue"
          >
            <el-checkbox
              v-for="(item,i) in themeactivityOptions"
              :label="item.id"
              :key="i"
            >{{item.name}}</el-checkbox>
          </el-checkbox-group>
          <el-input v-model="form.activityid" v-if="false" />
        </el-form-item>
        <el-form-item label="家长支持" prop="jzzc">
          <el-input v-model="form.jzzc" type="textarea" placeholder="请输入内容" />
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
  listWeekplan,
  getWeekplan,
  delWeekplan,
  addWeekplan,
  updateWeekplan,
} from "@/api/benyi/themeweekplan";
import { listMonthplan, getMonthplan } from "@/api/benyi/thememonthplan";
import { listActivityByThemeId } from "@/api/benyi/activity";

export default {
  name: "Weekplan",
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
      // 主题整合周计划表格数据
      weekplanList: [],
      //主题整合月计划列表
      themeMonthPlanOptions: [],
      defaultThemeMonthType: "",
      themeactivityList: [],
      themeactivityOptions: [],
      max: 5,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mpid: undefined,
        zc: undefined,
        starttime: undefined,
        endtime: undefined,
        activityid: undefined,
        jzzc: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        zc: [{ required: true, message: "周次不能为空", trigger: "blur" }],
        starttime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endtime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        activityid: [
          { required: true, message: "活动不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const thememonthplanid = this.$route.params && this.$route.params.id;
    this.getThemeMonthPlan(thememonthplanid);
    this.getThemeMonthPlanList();
  },
  methods: {
    //获取选中的checkbox
    getThemeActivityIdValue() {
      //console.log(this.themeList);
      var text = ";";
      this.themeactivityList.forEach(function (value, key, arr) {
        //console.log(value); // 结果依次为1，2，3
        text = text + value + ";";
        //console.log(text);
      });
      this.form.activityid = text;
    },
    //主题活动
    getThemeActivityList(themeid) {
      listActivityByThemeId(themeid).then((response) => {
        //console.log(response.rows);
        this.themeactivityOptions = response.rows;
      });
    },
     // 字典翻译
    themeMonthPlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.themeMonthPlanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.mpid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //计划详情
    getThemeMonthPlan(thememonthplanid) {
      getMonthplan(thememonthplanid).then((response) => {
        this.queryParams.mpid = response.data.id;
        this.defaultThemeMonthType = response.data.id;
        console.log(response.data.themes);
        var themeids = response.data.themes.split(";");
        var array = [];
        //console.log(arr);
        themeids.forEach(function (value, key, arr) {
          //console.log(value); // 结果依次为1，2，3
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        this.getThemeActivityList(array);

        this.getList();
      });
    },
    getThemeMonthPlanList() {
      listMonthplan().then((response) => {
        this.themeMonthPlanOptions = response.rows;
      });
    },
    /** 查询主题整合周计划列表 */
    getList() {
      this.loading = true;
      listWeekplan(this.queryParams).then((response) => {
        this.weekplanList = response.rows;
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
        mpid: undefined,
        zc: 0,
        starttime: undefined,
        endtime: undefined,
        activityid: undefined,
        jzzc: undefined,
        createuserid: undefined,
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
      this.queryParams.mpid = this.defaultThemeMonthType;
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
      this.title = "添加主题整合周计划";
      this.form.mpid = this.queryParams.mpid;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主题整合周计划";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateWeekplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWeekplan(this.form).then((response) => {
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
        '是否确认删除主题整合周计划编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delWeekplan(ids);
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
      this.$confirm("是否确认导出所有主题整合周计划数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportWeekplan(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>