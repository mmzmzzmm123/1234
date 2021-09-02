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
          <!-- <el-form-item label="班级名称" prop="classid">
        <el-select v-model="queryParams.classid" placeholder="请选择班级" clearable size="small">
          <el-option
            v-for="dict in classOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>-->
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="开始时间" prop="starttime">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="queryParams.starttime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择开始时间"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="结束时间" prop="endtime">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="queryParams.endtime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择结束时间"
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
        v-hasPermi="['benyi:planweek:add']"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:planweek:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:planweek:remove']"
        >删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      border
      :data="planweekList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        fixed
        label="计划名称"
        align="center"
        prop="name"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <router-link
            :to="'/benyi_course/planweek/data/' + scope.row.id"
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
      <el-table-column label="月份" align="center" prop="month">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="starttime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endtime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="周次"
        align="center"
        prop="weekly"
        :formatter="weeklyFormat"
      />
      <el-table-column label="本周主题" align="center" prop="themeofweek" />
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
          <el-button
            size="mini"
            type="text"
            icon="el-icon-setting"
            @click="handleActity(scope.row)"
            >活动</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['benyi:planweek:query']"
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

    <!-- 添加或修改周计划(家长和教育部门)对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      width="960px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="16">
          <el-col :xs="24" :ms="24" :md="12">
            <el-form-item label="计划名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入计划名称" />
            </el-form-item>
          </el-col>

          <el-col :xs="24" :ms="24" :md="12">
            <el-form-item label="起止时间" prop="starttime">
              <!-- <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.starttime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择开始时间"
              ></el-date-picker> -->
              <el-date-picker
                v-model="form.starttime"
                clearable
                class="my-date-picker"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                v-on:change="change()"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="结束时间" prop="endtime">
              <el-date-picker
                clearable
                size="small"
                class="my-date-picker"
                v-model="form.endtime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择结束时间"
              ></el-date-picker>
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :ms="24" :md="12">
            <el-form-item label="选择月份" prop="month">
              <el-date-picker
                clearable
                class="my-date-picker"
                v-model="form.month"
                type="month"
                value-format="yyyy-MM"
                placeholder="选择月份"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :ms="24" :md="12">
            <el-form-item label="选择周次" prop="weekly">
              <el-select v-model="form.weekly" placeholder="请选择周次">
                <el-option
                  v-for="dict in weeklyOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :ms="24" :md="12">
            <el-form-item label="本周主题" prop="themeofweek">
              <el-input
                v-model="form.themeofweek"
                placeholder="请输入本周主题"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <router-link
              to="/benyi_assessment/assessmentstudy"
              style="
                color: #545454;
                font-size: 10px;
                text-decoration: underline;
              "
              >去学习五大领域（健康、语言、社会、科学、艺术）</router-link
            >
          </el-col>

          <el-col :span="24">
            <el-form-item label="教学目标(健康)" prop="jxmbJk">
              <Editor
                v-model="form.jxmbJk"
                placeholder="请输入教学目标(健康)"
                :quillIndex="2"
                toref="Editora"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教学目标(语言)" prop="jxmbYy">
              <Editor
                v-model="form.jxmbYy"
                placeholder="请输入教学目标(语言)"
                :quillIndex="1"
                toref="Editora"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教学目标(社会)" prop="jxmbSh">
              <Editor
                v-model="form.jxmbSh"
                placeholder="请输入教学目标(社会)"
                :quillIndex="0"
                toref="Editora"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教学目标(科学)" prop="jxmbKx">
              <Editor
                v-model="form.jxmbKx"
                placeholder="请输入教学目标(科学)"
                :quillIndex="3"
                toref="Editora"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教学目标(艺术)" prop="jxmbYs">
              <Editor
                v-model="form.jxmbYs"
                placeholder="请输入教学目标(艺术)"
                :quillIndex="4"
                toref="Editora"
              />
            </el-form-item>
          </el-col>
        </el-row>
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
  listPlanweek,
  getPlanweek,
  delPlanweek,
  addPlanweek,
  updatePlanweek,
  exportPlanweek,
  checkPlanweek,
} from "@/api/benyi/planweek";

import Editor from "@/components/Editor";

import { listClass } from "@/api/system/class";

export default {
  name: "Planweek",
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
      // 周计划(家长和教育部门)表格数据
      planweekList: [],
      // 周计划当前状态
      statusOptions: [],
      //班级
      classOptions: [],
      // 周次
      weeklyOptions: [],
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
        starttime: undefined,
        endtime: undefined,
        themeofweek: undefined,
        jxmbSh: undefined,
        jxmbYy: undefined,
        jxmbJk: undefined,
        jxmbKx: undefined,
        jxmbYs: undefined,
        createuserid: undefined,
        status: undefined,
        shrid: undefined,
        shtime: undefined,
        weekly: undefined,
        month: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "计划名称不能为空", trigger: "blur" },
        ],
        starttime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        // endtime: [
        //   { required: true, message: "结束时间不能为空", trigger: "blur" },
        // ],
        themeofweek: [
          { required: true, message: "本周主题不能为空", trigger: "blur" },
        ],
        // jxmbSh: [
        //   {
        //     required: true,
        //     message: "教学目标(社会)不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // jxmbYy: [
        //   {
        //     required: true,
        //     message: "教学目标(语言)不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // jxmbJk: [
        //   {
        //     required: true,
        //     message: "教学目标(健康)不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // jxmbKx: [
        //   {
        //     required: true,
        //     message: "教学目标(科学)不能为空",
        //     trigger: "blur",
        //   },
        // ],
        // jxmbYs: [
        //   {
        //     required: true,
        //     message: "教学目标(艺术)不能为空",
        //     trigger: "blur",
        //   },
        // ],
        weekly: [{ required: true, message: "周次不能为空", trigger: "blur" }],
        month: [{ required: true, message: "月份不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    // 审核状态获取数据
    this.getDicts("sys_dm_planweekstatus").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_weekly").then((response) => {
      this.weeklyOptions = response.data;
    });
  },
  methods: {
    change: function () {
      //console.log(this.form.starttime);
      this.form.month = this.form.starttime[0].slice(0, 7);
    },
    /** 查询周计划(家长和教育部门)列表 */
    getList() {
      this.loading = true;
      listPlanweek(this.queryParams).then((response) => {
        this.planweekList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
        // console.log(this.classOptions);
      });
    },

    // 当前状态翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 性别字典翻译
    weeklyFormat(row, column) {
      return this.selectDictLabel(this.weeklyOptions, row.weekly);
    },
    // 班级字典翻译
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
        name: "教学计划",
        starttime: undefined,
        endtime: undefined,
        themeofweek: undefined,
        jxmbSh: undefined,
        jxmbYy: undefined,
        jxmbJk: undefined,
        jxmbKx: undefined,
        jxmbYs: undefined,
        createuserid: undefined,
        createTime: undefined,
        status: "0",
        shrid: undefined,
        shtime: undefined,
        weekly: undefined,
        month: undefined,
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
      this.title = "添加周计划(家长和教育部门)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      let timeArr = [];
      const id = row.id || this.ids;
      getPlanweek(id).then((response) => {
        this.form = response.data;
        timeArr.push(response.data.starttime);
        timeArr.push(response.data.endtime);
        this.form.starttime = timeArr;
        this.open = true;
        this.title = "修改周计划(家长和教育部门)";
      });
    },
    // /** 审核提交按钮操作 */
    // handleCheck(row) {
    //   const ids = row.id || this.ids;
    //   this.$confirm('确认提交周计划编号为"' + ids + '"的数据项?', "警告", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(function () {
    //       return checkPlanweek(ids);
    //     })
    //     .then(() => {
    //       this.getList();
    //       this.msgSuccess("提交成功");
    //     })
    //     .catch(function () {});
    // },
    isShow(row) {
      if (row.status == "1" || row.status == "2") {
        return false;
      } else {
        return true;
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          let timeArr = this.form.starttime;
          this.form.starttime = timeArr[0];
          this.form.endtime = timeArr[1];
          // console.log(this.form.starttime);
          // console.log(this.form.endtime);
          // console.log(this.form.month);
          if (this.form.id != undefined) {
            updatePlanweek(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPlanweek(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("保存成功，记得去<活动>按钮设置每天活动。");
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
      this.$confirm("是否确认删除周计划(家长和教育部门)数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delPlanweek(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 预览按钮操作 */
    handleView(row) {
      const id = row.id;
      this.$router.push({
        path: "/benyi_course/planweekprint/table/" + id,
      });
    },
    handleActity(row) {
      const id = row.id;
      this.$router.push({
        path: "/benyi_course/planweek/data/" + id,
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