<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="选择班级" prop="classid">
            <el-select v-model="queryParams.classid" placeholder="请选择班级">
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
          <el-form-item label="当前状态" prop="status">
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
      <!-- <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:themetermplan:edit']"
        >审批</el-button
      > -->
    </div>

    <el-table
      border
      v-loading="loading"
      :data="termplanList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
      />
      <el-table-column
        fixed
        label="计划名称"
        align="center"
        prop="name"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <router-link
            :to="'/benyi_course/themetermplanprint/table/' + scope.row.id"
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
      <el-table-column label="开始月份" align="center" prop="startmonth">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startmonth, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束月份" align="center" prop="endmonth">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endmonth, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
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
        fixed="right"
        width="60"
        align="center"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:themetermplan:edit']"
            v-show="isShow(scope.row)"
            >审批</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['benyi:themetermplan:query']"
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

    <!-- 添加或修改主题整合学期计划对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="月份" prop="startmonth">
          <el-date-picker
            class="my-date-picker"
            v-model="form.startmonth"
            type="monthrange"
            range-separator="至"
            start-placeholder="开始月份"
            end-placeholder="结束月份"
            value-format="yyyy-MM"
            :disabled="true"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="学年学期" prop="xnxq">
          <el-select
            v-model="form.xnxq"
            placeholder="请选择学年学期"
            :disabled="true"
          >
            <el-option
              v-for="dict in xnxqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注"
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
  listTermplansp,
  getTermplan,
  updateTermplan,
} from "@/api/benyi/themetermplan";
import { listClass } from "@/api/system/class";

export default {
  name: "Termplan",
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
      // 主题整合学期计划表格数据
      termplanList: [],
      //班级
      classOptions: [],
      //学年学期
      xnxqOptions: [],
      //当前状态
      statusOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        startmonth: undefined,
        endmonth: undefined,
        xnxq: undefined,
        createuserid: undefined,
        status: "1",
        spr: undefined,
        sptime: undefined,
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
    this.getClassList();
    this.getList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
    this.getDicts("sys_dm_planweekstatus").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    // 未提交不显示审批按钮
    isShow(row) {
      if (row.status == "0") {
        return false;
      } else {
        return true;
      }
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
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    // 当前状态类型--字典状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    //班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    /** 查询主题整合学期计划列表 */
    getList() {
      this.loading = true;
      listTermplansp(this.queryParams).then((response) => {
        this.termplanList = response.rows;
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
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        startmonth: undefined,
        endmonth: undefined,
        xnxq: undefined,
        remark: undefined,
        createuserid: undefined,
        createTime: undefined,
        spr: undefined,
        sptime: undefined,
        status: "2",
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
      getTermplan(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "审批主题整合学期计划";
        const time = [];
        time.push(response.data.startmonth);
        time.push(response.data.endmonth);
        this.form.startmonth = time;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const time = this.form.startmonth;
          this.form.startmonth = time[0];
          this.form.endmonth = time[1];
          if (this.form.id != undefined) {
            updateTermplan(this.form).then((response) => {
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
        path: "/benyi_course/themetermplanprint/table/" + id,
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