<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="选择班级" prop="classid">
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
          <el-form-item label="选择教师" prop="jsid">
            <el-select
              v-model="queryParams.jsid"
              clearable
              size="small"
              placeholder="请选择教师"
            >
              <el-option
                v-for="dict in userOptions"
                :key="dict.userId"
                :label="dict.nickName"
                :value="dict.userId"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="考核月份" prop="month">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="queryParams.month"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择月份"
            ></el-date-picker>
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:teacherassessment:add']"
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
          v-hasPermi="['benyi:teacherassessment:edit']"
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
          v-hasPermi="['benyi:teacherassessment:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="teacherassessmentList"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="班级"
        align="center"
        prop="classid"
        :formatter="classFormat"
        fixed
      />
      <el-table-column
        label="教师"
        align="center"
        prop="sysUser.nickName"
        fixed
      />
      <el-table-column label="考核月份" align="center" prop="month" />
      <el-table-column label="一日流程" align="center" prop="yrlcbl" />
      <el-table-column label="教师出勤" align="center" prop="jskqbl" />
      <el-table-column label="幼儿出勤" align="center" prop="yekqbl" />
      <el-table-column label="事故" align="center" prop="sgbl" />
      <el-table-column label="卫生" align="center" prop="wsbl" />
      <el-table-column label="总分" align="center" prop="zfbl" />
      <el-table-column
        label="操作"
        fixed="right"
        align="center"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:teacherassessment:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:teacherassessment:remove']"
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

    <!-- 添加或修改教师月绩效考核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核月份" prop="month">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.month"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择考核月份"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="选择班级" prop="classid">
          <el-select
            v-model="form.classid"
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
        <el-form-item label="选择教师" prop="jsid">
          <!-- <el-input v-model="form.jsid" placeholder="请输入教师编号" /> -->
          <el-select
            v-model="form.jsid"
            clearable
            size="small"
            placeholder="请选择教师"
          >
            <el-option
              v-for="dict in userOptions"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一日流程" prop="yrlcbl">
          <el-input-number
            v-model="form.yrlcbl"
            placeholder="请输入一日流程得分"
            :disabled="true"
          />
        </el-form-item>
        <el-form-item label="教师出勤" prop="jskqbl">
          <el-input-number
            v-model="form.jskqbl"
            placeholder="请输入教师出勤得分"
          />
        </el-form-item>
        <el-form-item label="幼儿出勤" prop="yekqbl">
          <el-input-number
            v-model="form.yekqbl"
            placeholder="请输入幼儿出勤得分"
          />
        </el-form-item>
        <el-form-item label="事故" prop="sgbl">
          <el-input-number v-model="form.sgbl" placeholder="请输入事故得分" />
        </el-form-item>
        <el-form-item label="卫生" prop="wsbl">
          <el-input-number v-model="form.wsbl" placeholder="请输入卫生得分" />
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
  listTeacherassessment,
  getTeacherassessment,
  delTeacherassessment,
  addTeacherassessment,
  updateTeacherassessment,
  exportTeacherassessment,
} from "@/api/benyi/teacherassessment";
import { listDayflowassessmentbyJsid } from "@/api/benyi/dayflowassessment";

import { listClass, getUserList } from "@/api/system/class";
import { listUser } from "@/api/system/user";

export default {
  name: "Teacherassessment",
  data() {
    return {
      // 监听
      month: null,
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
      // 教师月绩效考核表格数据
      teacherassessmentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //班级
      classOptions: [],
      // 所有教师
      userOptions: [],
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jsid: undefined,
        classid: undefined,
        deptId: undefined,
        month: undefined,
        yrlcbl: undefined,
        jskqbl: undefined,
        yekqbl: undefined,
        sgbl: undefined,
        wsbl: undefined,
        zfbl: undefined,
      },
      // 查询参数
      queryParams_pg: {
        pgdx: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jsid: [{ required: true, message: "教师不能为空", trigger: "blur" }],
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
        month: [{ required: true, message: "月份不能为空", trigger: "blur" }],
        yrlcbl: [
          {
            required: true,
            message: "一日流程评估得分不能为空",
            trigger: "blur",
          },
        ],
        jskqbl: [
          { required: true, message: "教师出勤得分不能为空", trigger: "blur" },
        ],
        yekqbl: [
          { required: true, message: "幼儿出勤得分不能为空", trigger: "blur" },
        ],
        sgbl: [
          { required: true, message: "事故得分不能为空", trigger: "blur" },
        ],
        wsbl: [
          { required: true, message: "卫生得分不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  watch: {
    "queryParams.classid": function (val) {
      //console.log("1");
      if (val == "undefined" || val == "" || val == null) {
      } else {
        getUserList(val).then((response) => {
          //console.log(response);
          this.userOptions = response.rows;
        });
      }
    },
    "form.classid": function (val) {
      //console.log(val);
      if (val == "undefined" || val == "" || val == null) {
      } else {
        getUserList(val)
          .then((response) => {
            //console.log(response);
            this.userOptions = response.rows;
          })
          .catch((e) => {});
      }
    },
    "form.month": function (val) {
      //console.log(val);
      this.month = val;
    },
    "form.jsid": function (val) {
      //console.log(val);
      this.queryParams_pg.pgdx = val;
      this.dateRange[0] = this.month + "-01";
      this.dateRange[1] = this.month + "-31";
      //console.log(this.dateRange);
      listDayflowassessmentbyJsid(
        this.addDateRange(this.queryParams_pg, this.dateRange)
      ).then((response) => {
        //console.log(response);
        var total = 100;
        response.rows.forEach((item) => {
          total = total + item.zzdf;
        });

        this.form.yrlcbl = total;
      });
    },
  },
  methods: {
    /** 查询教师月绩效考核列表 */
    getList() {
      this.loading = true;
      listTeacherassessment(this.queryParams).then((response) => {
        this.teacherassessmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });

      this.getClassList();
      this.getUserList();
    },
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
        // console.log(response.rows[0].bjbh);
        // this.form.classid = response.rows[0].bjbh;
      });
    },
    /** 查询用户列表 */
    getUserList() {
      listUser(null).then((response) => {
        this.userOptions = response.rows;
      });
    },
    // 字典翻译
    classFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
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
    userFormat(row, column) {
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.jsid) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
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
        jsid: undefined,
        classid: undefined,
        deptId: undefined,
        month: undefined,
        yrlcbl: undefined,
        jskqbl: undefined,
        yekqbl: undefined,
        sgbl: undefined,
        wsbl: undefined,
        zfbl: undefined,
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
      this.title = "添加教师月绩效考核";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeacherassessment(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改教师月绩效考核";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTeacherassessment(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTeacherassessment(this.form).then((response) => {
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
      this.$confirm("是否确认删除教师月绩效考核的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delTeacherassessment(ids);
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