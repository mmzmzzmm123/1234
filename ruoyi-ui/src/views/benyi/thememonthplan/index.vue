<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="计划名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入计划名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属班级" prop="classid">
        <el-select v-model="queryParams.classid" placeholder="请选择班级">
          <el-option
            v-for="dict in classOptions"
            :key="dict.bjbh"
            :label="dict.bjmc"
            :value="dict.bjbh"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学年学期" prop="xnxq">
        <el-input
          v-model="queryParams.xnxq"
          placeholder="请输入学年学期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划月份" prop="month">
        <el-input
          v-model="queryParams.month"
          placeholder="请输入计划月份"
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

    <el-table v-loading="loading" :data="monthplanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="计划名称" align="center" prop="name" />
      <el-table-column label="所属班级" align="center" prop="classid" />
      <el-table-column label="学年学期" align="center" prop="xnxq" />
      <el-table-column label="计划月份" align="center" prop="month" />
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

    <!-- 添加或修改主题整合月计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="所属班级" prop="classid">
          <el-select
            v-model="form.classid"
            placeholder="请选择班级"
            @change="getClassName"
            :disabled="disable"
          >
            <el-option
              v-for="dict in classOptions"
              :key="dict.bjbh"
              :label="dict.bjmc"
              :value="dict.bjbh"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="计划月份" prop="month">
          <el-date-picker
            v-model="form.month"
            type="month"
            placeholder="选择计划月份"
            :disabled="disable"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="主题" prop="themes">
          <el-input v-model="form.themes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="自定义主题" prop="selfthemes">
          <el-input v-model="form.selfthemes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="家长支持" prop="support">
          <el-input v-model="form.support" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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
  listMonthplan,
  getMonthplan,
  delMonthplan,
  addMonthplan,
  updateMonthplan,
} from "@/api/benyi/thememonthplan";

import { listClass } from "@/api/system/class";

export default {
  name: "Monthplan",
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
      //是否
      disable: false,
      // 主题整合月计划表格数据
      monthplanList: [],
      //班级
      classOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        classid: undefined,
        xnxq: undefined,
        month: undefined,
        themes: undefined,
        selfthemes: undefined,
        support: undefined,
        remarks: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
        month: [
          { required: true, message: "计划月份不能为空", trigger: "blur" },
        ],
        themes: [{ required: true, message: "主题不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
  },
  methods: {
    getClassName(val) {
      //locations是v-for里面的也是datas里面的值
      let obj = {};
      obj = this.classOptions.find((item) => {
        return item.bjbh === val;
      });
      let getName = "";
      getName = obj.bjmc;
      this.form.name = getName;
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
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    /** 查询主题整合月计划列表 */
    getList() {
      this.loading = true;
      listMonthplan(this.queryParams).then((response) => {
        this.monthplanList = response.rows;
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
        classid: undefined,
        xnxq: undefined,
        month: undefined,
        themes: undefined,
        selfthemes: undefined,
        support: undefined,
        remarks: undefined,
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
      this.title = "添加<主题整合>月计划";
      this.disable = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMonthplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改<主题整合>月计划";
        this.disable = true;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMonthplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMonthplan(this.form).then((response) => {
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
        '是否确认删除主题整合月计划编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delMonthplan(ids);
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