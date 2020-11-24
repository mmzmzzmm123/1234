<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="计划名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入计划名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
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
          <el-form-item label="所属月份" prop="month">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="queryParams.month"
              type="month"
              value-format="yyyy-MM"
              placeholder="选择所属月份"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="4">
          <el-form-item label="所属周次" prop="zc">
            <el-input-number
              class="my-date-picker"
              v-model="queryParams.zc"
              placeholder="周次"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="请选择状态"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
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
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:themeweekplan:edit']"
        >审批</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="weekplanList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
        :selectable="isShow"
      />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        fixed
        label="计划名称"
        align="center"
        prop="name"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <router-link
            :to="'/benyi_course/themeweekplan/data/' + scope.row.id"
            class="link-type"
          >
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column
        label="班级名称"
        align="center"
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column label="所属月份" align="center" prop="month">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.month, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属周次" align="center" prop="zc" />
      <el-table-column
        label="学年学期"
        align="center"
        prop="xnxq"
        :formatter="xnxqFormat"
      />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="状态"
        align="center"
        prop="status"
        :formatter="statusFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:themeweekplan:edit']"
            v-show="isShow(scope.row)"
            >审批</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['benyi:themeweekplan:query']"
            >预览</el-button
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

    <!-- 添加或修改主题整合周计划（根据月计划明细）对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学年学期" prop="xnxq">
          <el-select
            v-model="form.xnxq"
            placeholder="请选择学年学期"
            :disabled="disable"
          >
            <el-option
              v-for="dict in xnxqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所属月份" prop="month">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.month"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择所属月份"
            :disabled="disable"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="所属周次" prop="zc">
          <el-input-number
            class="my-date-picker"
            v-model="form.zc"
            placeholder="周次"
            :disabled="disable"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remar">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
            :disabled="true"
          />
        </el-form-item>
        <el-form-item label="审批意见" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">退回</el-radio>
            <el-radio label="2">通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审批建议" prop="shyj">
          <el-input
            v-model="form.shyj"
            type="textarea"
            placeholder="请输入审核建议"
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
  listWeekplan,
  getWeekplan,
  updateWeekplan,
} from "@/api/benyi/themeweekplan";
import { listClass } from "@/api/system/class";

export default {
  name: "Weekplan",
  data() {
    return {
      disable: false,
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
      // 主题整合周计划（根据月计划明细）表格数据
      weekplanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //学年学期
      xnxqOptions: [],
      //班级
      classOptions: [],
      //当前状态
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        schoolid: undefined,
        classid: undefined,
        month: undefined,
        zc: undefined,
        status: "1",
        spr: undefined,
        sptime: undefined,
        shyj: undefined,
        createuserid: undefined,
        xnxq: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        status: [
          { required: true, message: "审批意见不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
    this.getDicts("sys_dm_planweekstatus").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    isShow(row) {
      if (row.status == "1") {
        return true;
      } else {
        return false;
      }
    },
    //班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    // 当前状态类型--字典状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
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
    /** 查询主题整合周计划（根据月计划明细）列表 */
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
        name: undefined,
        schoolid: undefined,
        classid: undefined,
        month: undefined,
        zc: undefined,
        remark: undefined,
        status: "2",
        spr: undefined,
        sptime: undefined,
        shyj: undefined,
        createuserid: undefined,
        createTime: undefined,
        xnxq: undefined,
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "审批主题整合周计划";
        this.disable = true;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateWeekplan(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("审批成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 预览按钮操作 */
    handleView(row) {
      const id = row.id;
      this.$router.push({
        path: "/benyi_course/themeweekplanprint/table/" + id,
      });
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
