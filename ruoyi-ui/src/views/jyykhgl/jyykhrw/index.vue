<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="考核学年" prop="xn">
        <el-select v-model="queryParams.xn" placeholder="请选择学年">
          <el-option
            v-for="dict in xnOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考核学期" prop="xq">
        <el-select v-model="queryParams.xq" placeholder="请选择学期">
          <el-option
            v-for="dict in xqOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称" prop="rwmc">
        <el-input
          v-model="queryParams.rwmc"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jyykhgl:jyykhrw:add']"
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
          v-hasPermi="['jyykhgl:jyykhrw:edit']"
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
          v-hasPermi="['jyykhgl:jyykhrw:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jyykhrwList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="考核学年"
        align="center"
        prop="xn"
        :formatter="xnFormat"
      />
      <el-table-column
        label="考核学期"
        align="center"
        prop="xq"
        :formatter="xqFormat"
      />
      <el-table-column label="任务名称" align="center" prop="rwmc" />
      <el-table-column
        label="考核部门"
        align="center"
        prop="khbm"
        :formatter="khbmFormat"
      />
      <el-table-column label="任务说明" align="center" prop="rwsm" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="240"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAddChild(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:add']"
            >填充</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:remove']"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleCopy(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:edit']"
            >复制</el-button
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

    <!-- 添加或修改教研员考核任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核学年" prop="xn">
          <el-select v-model="form.xn" placeholder="请选择学年">
            <el-option
              v-for="dict in xnOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核学期" prop="xq">
          <el-select v-model="form.xq" placeholder="请选择学期">
            <el-option
              v-for="dict in xqOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务名称" prop="rwmc">
          <el-input v-model="form.rwmc" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="任务说明" prop="rwsm">
          <el-input
            v-model="form.rwsm"
            type="textarea"
            placeholder="请输入内容"
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
  listJyykhrw,
  getJyykhrw,
  delJyykhrw,
  addJyykhrw,
  updateJyykhrw,
  copyJyykhrw,
} from "@/api/jyykhgl/jyykhrw";

export default {
  name: "Jyykhrw",
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
      // 教研员考核任务表格数据
      jyykhrwList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //考核部门
      khbmOptions: [],
      //学年
      xnOptions: [],
      //学期
      xqOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        rwsm: null,
        khzqkssj: null,
        khzqjssj: null,
        parentId: null,
        xn: null,
        xq: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xn: [
          { required: true, message: "考核学年不能为空", trigger: "blur" },
        ],
        xq: [
          { required: true, message: "考核学期不能为空", trigger: "blur" },
        ],
        rwmc: [
          { required: true, message: "任务名称不能为空", trigger: "blur" },
        ],
        rwsm: [
          { required: true, message: "任务内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_gbxn").then((response) => {
      this.xnOptions = response.data;
    });
    this.getDicts("sys_dm_xq").then((response) => {
      this.xqOptions = response.data;
    });
    this.getDicts("sys_dm_jyykhbm").then((response) => {
      this.khbmOptions = response.data;
    });
  },
  methods: {
    // 考核部门字典翻译
    khbmFormat(row, column) {
      return this.selectDictLabel(this.khbmOptions, row.khbm);
    },
    // 学年字典翻译
    xnFormat(row, column) {
      return this.selectDictLabel(this.xnOptions, row.xn);
    },
    // 学期字典翻译
    xqFormat(row, column) {
      return this.selectDictLabel(this.xqOptions, row.xq);
    },
    /** 查询教研员考核任务列表 */
    getList() {
      this.loading = true;
      listJyykhrw(this.queryParams).then((response) => {
        this.jyykhrwList = response.rows;
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
        id: null,
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        createTime: null,
        rwsm: null,
        khzqkssj: null,
        khzqjssj: null,
        parentId: null,
        xn: null,
        xq: null,
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
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加教研员考核任务";
    },
        /** 填充按钮操作 */
    handleAddChild(row) {
      const id = row.id;
      this.$router.push({ path: "/jyykhgl/jyykhrw/child/" + id });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJyykhrw(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改教研员考核任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJyykhrw(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJyykhrw(this.form).then((response) => {
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
        '是否确认删除教研员考核任务的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJyykhrw(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
     /** 复制按钮操作 */
    handleCopy(row) {
      const ids = row.id;
      this.$confirm(
        '是否确认复制教研员考核任务的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return copyJyykhrw(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("复制成功");
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
</style>
