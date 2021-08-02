<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <!--<el-form-item label="所属班级" prop="classid">
        <el-select v-model="queryParams.classid"  placeholder="请选择班级" >
          <el-option
            v-for="item in classListAll"
            :key="item.classid"
            :label="item.byClass.bjmc"
            :value="item.classid"
          />
        </el-select>
      </el-form-item>-->
          <el-form-item label="学年学期" prop="xnxq">
            <el-select
              v-model="queryParams.xnxq"
              placeholder="请选择学年学期"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xnxqOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="活动类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择活动类型"
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
          <!-- <el-form-item label="活动时间" prop="activitytime">
            <el-date-picker
              class="my-date-picker"
              clearable
              size="small"
              v-model="queryParams.activitytime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择活动时间"
            ></el-date-picker>
          </el-form-item> -->
          <el-form-item label="活动时间" prop="activitytime">
            <el-date-picker
              class="my-date-picker"
              v-model="dateRange"
              size="small"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
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
        v-hasPermi="['benyi:schoolcalendarclass:add']"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:schoolcalendarclass:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:schoolcalendarclass:remove']"
        >删除</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['benyi:schoolcalendarclass:export']"
        >导出</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="schoolcalendarclassList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
        :selectable="checkSelectable"
      />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="名称" align="center" prop="name" />
      <el-table-column
        label="活动类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column label="所属班级" align="center" prop="byClass.bjmc" />
      <el-table-column
        label="学年学期"
        align="center"
        prop="xnxq"
        :formatter="xnxqFormat"
      />
      <el-table-column
        label="活动开始时间"
        align="center"
        prop="activitytime"
      />
      <el-table-column
        label="活动截止时间"
        align="center"
        prop="activityendtime"
      />
      <el-table-column label="备注" align="center" show-overflow-tooltip prop="remark" />
      <!-- <el-table-column
        label="创建时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column> -->
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
            v-hasPermi="['benyi:schoolcalendarclass:edit']"
            :disabled="!checkSelectable(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:schoolcalendarclass:remove']"
            :disabled="!checkSelectable(scope.row)"
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

    <!-- 添加或修改园历管理(班级)对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="v-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事件名称" prop="name">
          <el-input v-model="form.name" size="small" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="活动类型" prop="type">
          <el-select
            v-model="form.type"
            size="small"
            placeholder="请选择活动类型"
          >
            <el-option
              v-for="dict in typeOptions"
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
            class="my-date-picker"
            v-model="form.activitytime"
            type="daterange"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions0"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
          <el-input v-model="form.activityendtime" v-if="false" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            type="textarea"
            v-model="form.remark"
            size="small"
            placeholder=""
          />
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
  listSchoolcalendarclass,
  listSchoolcalendarclassAll,
  getSchoolcalendarclass,
  delSchoolcalendarclass,
  addSchoolcalendarclass,
  updateSchoolcalendarclass,
  exportSchoolcalendarclass,
} from "@/api/benyi/schoolcalendarclass";

export default {
  name: "Schoolcalendarclass",
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
      // 园历管理(班级)表格数据
      schoolcalendarclassList: [],
      // 园历班级名称列表
      classListAll: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动类型字典
      typeOptions: [],
      //学年学期
      xnxqOptions: [],
      //声明方法
      selectable: Function,
      //禁止添加今天以前的日期
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        classid: undefined,
        xnxq: undefined,
        deptid: undefined,
        activitytime: undefined,
        createuserid: undefined,
        createtime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      // 表单校验
      rules: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        type: [
          { required: true, message: "活动类型不能为空", trigger: "blur" },
        ],
        activitytime: [
          { required: true, message: "活动时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    //this.getListAll();
    this.getDicts("sys_schoolcalendartype").then((response) => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    /** 查询园历管理(班级)列表 */
    getList() {
      this.loading = true;
      listSchoolcalendarclass(
        this.addDateRange(this.queryParams, this.dateRange)
      ).then((response) => {
        this.schoolcalendarclassList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询所有班级名称列表 */
    // getListAll() {
    //   listSchoolcalendarclassAll(this.queryParams).then((response) => {
    //     this.classListAll = response.rows;
    //   });
    // },
    // 活动类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },

    //控制按钮可用
    checkSelectable(row) {
      var date = new Date();
      //console.log(date.toLocaleDateString());
      return this.CompareDate(row.activitytime, date.toLocaleDateString());
    },
    //比较日期大小
    CompareDate(d1, d2) {
      return new Date(d1.replace(/-/g, "/")) > new Date(d2.replace(/-/g, "/"));
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
        classid: undefined,
        xnxq: undefined,
        deptid: undefined,
        activitytime: undefined,
        createuserid: undefined,
        createtime: undefined,
        remark: undefined,
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
      this.dateRange = [];
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
      this.title = "添加园历管理(班级)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      var myArray = new Array(2);
      getSchoolcalendarclass(id).then((response) => {
        this.form = response.data;
        myArray[0] = response.data.activitytime;
        myArray[1] = response.data.activityendtime;
        //console.log(myArray);
        this.form.activitytime = myArray;
        this.open = true;
        this.title = "修改园历管理(班级)";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          var v1 = this.form.activitytime[0];
          var v2 = this.form.activitytime[1];
          this.form.activitytime = v1;
          this.form.activityendtime = v2;

          if (this.form.id != undefined) {
            updateSchoolcalendarclass(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addSchoolcalendarclass(this.form).then((response) => {
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
        '是否确认删除园历管理(班级)编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delSchoolcalendarclass(ids);
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
      this.$confirm("是否确认导出所有园历管理(班级)数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportSchoolcalendarclass(queryParams);
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