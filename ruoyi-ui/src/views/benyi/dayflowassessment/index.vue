<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="70px"
    >
      <el-form-item label="所属计划" prop="planid">
        <el-select v-model="queryParams.planid" placeholder="请选择评估计划">
          <el-option
            v-for="dict in dayflowassessmentplanOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级编号" prop="classid">
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
      <el-form-item label="学年学期" prop="xnxq">
        <el-select v-model="queryParams.xnxq" placeholder="请选择学年学期">
            <el-option
              v-for="dict in xnxqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
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

    <el-table
      v-loading="loading"
      :data="dayflowassessmentList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属计划" align="center" prop="planid" :formatter="planFormat"/>
      <el-table-column
        label="班级名称"
        align="center"
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column label="班级平均分" align="center" prop="classdf" />
      <el-table-column label="主班教师" align="center" prop="bzbh" :formatter="bzbhFormat" />
      <el-table-column label="配班教师" align="center" prop="pbbh" :formatter="pbbhFormat"/>
      <el-table-column label="助理教师" align="center" prop="zlbh" :formatter="zlbhFormat"/>
      <el-table-column label="学年学期" align="center" prop="xnxq" :formatter="xnxqFormat"/>
      <el-table-column label="评估标准" align="center" prop="bzid" :formatter="dayFlowFormat"/>
      <el-table-column label="标准满分" align="center" prop="bzmf" />
      <el-table-column label="扣分值" align="center" prop="kfz" />
      <!-- <el-table-column label="扣分次数" align="center" prop="kfcs" /> -->
      <el-table-column label="最终得分" align="center" prop="zzdf" />
      <el-table-column label="评估对象" align="center" prop="pgdx" :formatter="pgdxFormat"/>
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
            @click="handleAssessment(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:edit']"
            >评估</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowassessment:remove']"
            >删除</el-button
          > -->
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

    <!-- 添加或修改幼儿园一日流程评估对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属计划" prop="planid">
          <el-select
            v-model="form.planid"
            filterable
            placeholder="请选择评估计划"
          >
            <el-option
              v-for="dict in dayflowassessmentplanOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级编号" prop="classid">
          <el-select
            v-model="form.classid"
            filterable
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
        <el-form-item label="主班教师" prop="bzbh">
          <el-select
            v-model="form.bzbh"
            filterable
            placeholder="请选择主班教师"
          >
            <el-option
              v-for="item in zbjsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="配班教师" prop="pbbh">
          <el-select
            v-model="form.pbbh"
            filterable
            placeholder="请选择配班教师"
          >
            <el-option
              v-for="item in pbjsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="助理教师" prop="zlbh">
          <el-select
            v-model="form.zlbh"
            filterable
            placeholder="请选择助理教师"
          >
            <el-option
              v-for="item in zljsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评估学年学期" prop="xnxq">
          <el-select v-model="form.xnxq" placeholder="请选择学年学期">
            <el-option
              v-for="dict in xnxqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评估标准编号" prop="bzid">
          <el-input v-model="form.bzid" placeholder="请输入评估标准编号" />
        </el-form-item>
        <el-form-item label="扣分值" prop="kfz">
          <el-input v-model="form.kfz" placeholder="请输入扣分值" />
        </el-form-item>
        <!-- <el-form-item label="扣分次数" prop="kfcs">
          <el-input v-model="form.kfcs" placeholder="请输入扣分次数" />
        </el-form-item> -->
        <el-form-item label="评估对象" prop="pgdx">
          <el-select
            v-model="form.pgdx"
            filterable
            clearable
            placeholder="请选择当时教师"
          >
            <el-option
              v-for="item in userOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="执行人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入执行人" />
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
  listDayflowassessment,
  getDayflowassessment,
  delDayflowassessment,
  addDayflowassessment,
  updateDayflowassessment,
} from "@/api/benyi/dayflowassessment";
import { listDayflowassessmentplan, getDayflowassessmentplan, } from "@/api/benyi/dayflowassessmentplan";
import { listClass } from "@/api/system/class";
import { getUsersByRoleId } from "@/api/system/user";
import { listUser } from "@/api/system/user";
import { listDetail, getDetail } from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Dayflowassessment",
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
      // 幼儿园一日流程评估表格数据
      dayflowassessmentList: [],
      // 一日流程评估计划数据
      dayflowassessmentplanOptions: [],
      // 班级
      classOptions: [],
      //主班教师角色用户
      zbjsOptions: [],
      //配班教师角色用户
      pbjsOptions: [],
      //助理教师角色用户
      zljsOptions: [],
      // 学年学期
      xnxqOptions: [],
      // 所有教师
      userOptions: [],
      // 一日流程表格数据
      detailOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        kfz: undefined,
        bzmf: undefined,
        zzdf: undefined,
        kfcs: undefined,
        pgdx: undefined,
        classdf: undefined,
        createUserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getUserList();
    this.getDayFlowList();
    this.getDayflowassessmentplan();
    //获取主班教师角色用户列表
    getUsersByRoleId().then((response) => {
      this.zbjsOptions = response.zbjs;
      this.pbjsOptions = response.pbjs;
      this.zljsOptions = response.zljs;
    });
    // 获取学年学期
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    /** 查询幼儿园一日流程评估列表 */
    getList() {
      this.loading = true;
      listDayflowassessment(this.queryParams).then((response) => {
        this.dayflowassessmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询一日流程评估计划列表 */
    getDayflowassessmentplan() {
      listDayflowassessmentplan(null).then((response) => {
        this.dayflowassessmentplanOptions = response.rows;
      });
    },
    // 获取班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    // 班级字典翻译
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
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    /** 查询用户列表 */
    getUserList() {
      listUser(null).then(
        (response) => {
          this.userOptions = response.rows;
        }
      );
    },
    // 教师字典翻译
    bzbhFormat(row, column) {
      var actions = [];
      var datas = this.zbjsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.bzbh) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 教师字典翻译
    pbbhFormat(row, column) {
      var actions = [];
      var datas = this.pbjsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.pbbh) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 教师字典翻译
    zlbhFormat(row, column) {
      var actions = [];
      var datas = this.zljsOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.zlbh) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 教师字典翻译
    pgdxFormat(row, column) {
      var actions = [];
      var datas = this.userOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].userId == "" + row.pgdx) {
          actions.push(datas[key].nickName);
          return false;
        }
      });
      return actions.join("");
    },
    // 计划字典翻译
    planFormat(row, column) {
      var actions = [];
      var datas = this.dayflowassessmentplanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.planid) {
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
    // 一日流程内容字典翻译
    dayFlowFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.detailOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id + "" == "" + row.bzid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 学年学期类型--字典状态字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
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
        planid: undefined,
        deptId: undefined,
        classid: undefined,
        bzbh: undefined,
        bzxm: undefined,
        pbbh: undefined,
        pbxm: undefined,
        zlbh: undefined,
        zlxm: undefined,
        xnxq: undefined,
        bzid: undefined,
        kfz: undefined,
        bzmf: undefined,
        zzdf: undefined,
        kfcs: undefined,
        pgdx: undefined,
        classdf: undefined,
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
      this.title = "添加幼儿园一日流程评估";
      //获取主班教师角色用户列表
      getUsersByRoleId().then((response) => {
        this.zbjsOptions = response.zbjs;
        this.pbjsOptions = response.pbjs;
        this.zljsOptions = response.zljs;
      });
    },
    handleAssessment(row) {
      const id = row.id;
      this.$router.push({ path: "/benyi/dayflowassessment/teacher/" + id });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getDayflowassessment(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿园一日流程评估";
        //获取主班教师角色用户列表
        getUsersByRoleId().then((response) => {
          this.zbjsOptions = response.zbjs;
          this.pbjsOptions = response.pbjs;
          this.zljsOptions = response.zljs;
        });
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDayflowassessment(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addDayflowassessment(this.form).then((response) => {
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
        '是否确认删除幼儿园一日流程评估编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delDayflowassessment(ids);
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