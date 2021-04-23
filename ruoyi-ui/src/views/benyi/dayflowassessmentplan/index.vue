<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
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
          <el-form-item label="评估月份" prop="month">
            <el-date-picker
              clearable
              size="small"
              v-model="queryParams.month"
              type="month"
              class="my-date-picker"
              value-format="yyyy-MM-dd"
              placeholder="选择评估月份"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="评估班级" prop="classid">
            <el-select
              v-model="queryParams.classid"
              clearable
              size="small"
              placeholder="请选择班级"
            >
              <el-option
                v-for="dict in classOptions"
                :key="dict.bjbh"
                :label="dict.bjmc"
                :value="dict.bjbh"
              ></el-option>
            </el-select>
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
        v-hasPermi="['benyi:dayflowassessmentplan:add']"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:dayflowassessmentplan:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:dayflowassessmentplan:remove']"
        >删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      border
      :data="dayflowassessmentplanList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="计划名称" fixed align="center" prop="name" />
      <el-table-column
        label="评估学年学期"
        align="center"
        prop="xnxq"
        :formatter="xnxqFormat"
      />
      <el-table-column
        label="评估班级"
        align="center"
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column
        label="评估内容"
        align="center"
        prop="connent"
        :formatter="dayFlowFormat"
      />
      <!-- <el-table-column label="内容分数" align="center" prop="score" />
      <el-table-column label="班级所得平均分" align="center" prop="classavg" /> -->
      <el-table-column label="评估时间" align="center" prop="starttime" />
      <!-- <el-table-column label="创建人" align="center" prop="createUserid" /> -->
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
            v-hasPermi="['benyi:dayflowassessmentplan:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowassessmentplan:remove']"
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

    <!-- 添加或修改幼儿园一日流程评估计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学年学期" prop="xnxq">
          <el-select v-model="form.xnxq" placeholder="请选择学年学期">
            <el-option
              v-for="dict in xnxqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评估班级" prop="classid">
          <el-select v-model="form.classid" placeholder="请选择班级">
            <el-option
              v-for="dict in classOptions"
              :key="dict.bjbh"
              :label="dict.bjmc"
              :value="dict.bjbh"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评估内容" prop="connent">
          <el-select
            v-model="form.connent"
            placeholder="请选择评估内容"
            @change="handleChange"
          >
            <el-option
              v-for="dict in detailOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评估时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.starttime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择评估时间"
          >
          </el-date-picker>
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
  listDayflowassessmentplan,
  getDayflowassessmentplan,
  delDayflowassessmentplan,
  addDayflowassessmentplan,
  updateDayflowassessmentplan,
} from "@/api/benyi/dayflowassessmentplan";
import { listClass } from "@/api/system/class";
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Dayflowassessmentplan",
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
      // 幼儿园一日流程评估计划表格数据
      dayflowassessmentplanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学年学期
      xnxqOptions: [],
      // 班级
      classOptions: [],
      // 一日流程表格数据
      detailOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        deptId: undefined,
        month: undefined,
        xnxq: undefined,
        classid: undefined,
        classavg: undefined,
        connent: undefined,
        score: undefined,
        starttime: undefined,
        createUserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xnxq: [
          { required: true, message: "学年学期不能为空", trigger: "blur" },
        ],
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
        connent: [
          { required: true, message: "评估内容不能为空", trigger: "blur" },
        ],
        starttime: [
          { required: true, message: "评估时间不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getDayFlowList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    dayFlowFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.detailOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id + "" == "" + row.connent) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询一日流程列表 */
    getDayFlowList() {
      listDetail(null).then((response) => {
        this.detailOptions = response.rows;
      });
    },
    // 字典翻译
    classFormat(row, column) {
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
        // console.log(response.rows[0].bjbh);
        // this.form.classid = response.rows[0].bjbh;
      });
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    /** 查询幼儿园一日流程评估计划列表 */
    getList() {
      this.loading = true;
      listDayflowassessmentplan(this.queryParams).then((response) => {
        this.dayflowassessmentplanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 选择评估内容时改变分数
    handleChange(value) {
      if (value === 1) {
        this.form.score = 11.0;
      } else if (value === 2) {
        this.form.score = 6.5;
      } else if (value === 3) {
        this.form.score = 7.5;
      } else if (value === 4) {
        this.form.score = 3.0;
      } else if (value === 5) {
        this.form.score = 3.0;
      } else if (value === 6) {
        this.form.score = 2.0;
      } else if (value === 7) {
        this.form.score = 5.0;
      } else if (value === 8) {
        this.form.score = 5.5;
      } else if (value === 9) {
        this.form.score = 3.5;
      } else if (value === 10) {
        this.form.score = 5.0;
      } else if (value === 11) {
        this.form.score = 12.0;
      } else if (value === 12) {
        this.form.score = 3.0;
      } else if (value === 13) {
        this.form.score = 28.5;
      } else if (value === 14) {
        this.form.score = 1.5;
      } else if (value === 15) {
        this.form.score = 3.0;
      }
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
        deptId: undefined,
        month: undefined,
        xnxq: undefined,
        classid: undefined,
        connent: undefined,
        score: undefined,
        classavg: undefined,
        starttime: undefined,
        createUserid: undefined,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加幼儿园一日流程评估计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDayflowassessmentplan(id).then((response) => {
        this.form = response.data;
        this.form.connent = parseInt(response.data.connent);
        this.open = true;
        this.title = "修改幼儿园一日流程评估计划";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDayflowassessmentplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDayflowassessmentplan(this.form).then((response) => {
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
        '是否确认删除幼儿园一日流程评估计划编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delDayflowassessmentplan(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
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