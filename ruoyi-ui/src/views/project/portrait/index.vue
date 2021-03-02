<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      v-show="showSearch"
      :inline="true"
    >
      <el-form-item label="项目名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入项目名称"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入负责人电话"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:project:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:project:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:project:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:project:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="projectList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目编号" prop="id" width="120" />
      <el-table-column
        label="项目类型"
        prop="type"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="项目名称"
        prop="name"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="所属院系"
        prop="deptName"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column label="负责人" prop="userName" width="100" />
      <el-table-column label="负责人电话" prop="phone" width="100" />
      <el-table-column
        label="项目金额"
        prop="total"
        align="center"
        width="100"
      />
      <el-table-column
        label="到账金额"
        prop="receive"
        align="center"
        width="100"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:project:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:project:remove']"
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

    <!-- 添加或修改项目配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="项目类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio
              v-for="dict in typeOptions"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="负责人" prop="userId">
          <el-select
            v-model="form.userId"
            placeholder="项目负责人"
            clearable
            size="small"
            style="width: 100%"
          >
            <el-option
              v-for="dict in userOptions"
              :key="dict.deptId + ':' + dict.userId"
              :label="
                dict.dept.deptName +
                ' - ' +
                dict.userName +
                '(' +
                dict.nickName +
                ')'
              "
              :value="dict.dept.deptId + ':' + dict.userId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="联系电话" prop="userId">
          <el-input
            type="text"
            v-model="form.phone"
            placeholder="项目负责人联系电话"
          />
        </el-form-item>
        <el-form-item label="项目金额" prop="total">
          <el-input-number
            v-model="form.total"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="到账金额" prop="receive">
          <el-input-number
            v-model="form.receive"
            controls-position="right"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="项目描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入内容"
          ></el-input>
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
  listProject,
  getProject,
  delProject,
  addProject,
  updateProject,
  exportProject,
  dataScope,
} from "@/api/system/project";
import { listUser } from "@/api/system/user";
import {
  treeselect as deptTreeselect,
  projectDeptTreeselect,
} from "@/api/system/dept";

export default {
  name: "Project",
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 项目表格数据
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      typeOptions: [
        { value: "HEND", label: "横向项目" },
        { value: "ZONG", label: "纵向项目" },
        { value: "XIAO", label: "校级项目" },
      ],
      // 菜单列表
      statusOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: undefined,
        projectKey: undefined,
        status: undefined,
      },
      userOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 表单校验
      rules: {
        type: [{ required: true, message: "项目类型不为空", trigger: "blur" }],
        userId: [
          { required: true, message: "项目负责人不能为空吗", trigger: "blur" },
        ],
        name: [
          { required: true, message: "项目名称不能为空", trigger: "blur" },
        ],
        projectKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" },
        ],
        projectSort: [
          { required: true, message: "项目顺序不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询项目列表 */
    getList() {
      this.loading = true;
      listProject(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.projectList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询菜单树结构 */
    getUserList() {
      listUser().then((response) => {
        this.userOptions = response.rows;
        console.log("userOptions ---> ", this.userOptions);
      });
    },
    /** 查询部门树结构 */
    getDeptTreeselect() {
      deptTreeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 所有部门节点数据
    getDeptAllCheckedKeys() {
      // 目前被选中的部门节点
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      // 半选中的部门节点
      let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 根据项目ID查询菜单树结构 */
    /** 根据项目ID查询部门树结构 */
    getProjectDeptTreeselect(projectId) {
      return projectDeptTreeselect(projectId).then((response) => {
        this.deptOptions = response.depts;
        return response;
      });
    },
    // 项目状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.projectName + '"项目吗?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return changeProjectStatus(row.projectId, row.status);
        })
        .then(() => {
          this.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      (this.deptExpand = true),
        (this.deptNodeAll = false),
        (this.form = {
          projectId: undefined,
          projectName: undefined,
          projectKey: undefined,
          projectSort: 0,
          status: "0",
          menuIds: [],
          deptIds: [],
          menuCheckStrictly: true,
          deptCheckStrictly: true,
          remark: undefined,
        });
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
      this.ids = selection.map((item) => item.projectId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getUserList();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getUserList();
      const projectId = row.id || this.ids;
      getProject(projectId).then((response) => {
        this.form = response.data;
        this.form.userId = this.form.deptId + ":" + this.form.userId;
        delete this.form.deptId;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 选择项目权限范围触发 */
    dataScopeSelectChange(value) {
      if (value !== "2") {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.reset();
      const projectDeptTreeselect = this.getProjectDeptTreeselect(
        row.projectId
      );
      getProject(row.projectId).then((response) => {
        this.form = response.data;
        this.openDataScope = true;
        this.$nextTick(() => {
          projectDeptTreeselect.then((res) => {
            this.$refs.dept.setCheckedKeys(res.checkedKeys);
          });
        });
        this.title = "分配数据权限";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          const ids = this.form.userId.split(":");
          this.form.deptId = ids[0];
          this.form.userId = ids[1];
          if (this.form.id != undefined) {
            updateProject(this.form).then((response) => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then((response) => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function () {
      if (this.form.projectId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then((response) => {
          this.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const projectIds = row.id || this.ids;
      this.$confirm(
        '是否确认删除项目编号为"' + projectIds + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delProject(projectIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有项目数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportProject(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        });
    },
  },
};
</script>
