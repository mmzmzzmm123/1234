<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="基地名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入基地名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主持人" prop="zcrid">
        <el-input
          v-model="queryParams.zcrid"
          placeholder="请输入主持人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地类型" prop="jdtype">
        <el-select
          v-model="queryParams.jdtype"
          placeholder="请选择基地类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jdtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['zcrpsgl:zcrjdcj:add']"
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
          v-hasPermi="['zcrpsgl:zcrjdcj:edit']"
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
          v-hasPermi="['zcrpsgl:zcrjdcj:remove']"
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
      :data="zcrjdcjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="基地名称" align="center" prop="name" />
      <el-table-column label="主持人" align="center" prop="zcrid" />
      <el-table-column
        label="基地类型"
        align="center"
        prop="jdtype"
        :formatter="jdtypeFormat"
      />
      <el-table-column label="招生名额" align="center" prop="zsme" />
      <el-table-column
        label="学段"
        align="center"
        prop="xd"
        :formatter="xdFormat"
      />
      <el-table-column
        label="学科"
        align="center"
        prop="xk"
        :formatter="xkFormat"
      />
      <el-table-column
        label="报名开始时间"
        align="center"
        prop="starttime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="报名结束时间"
        align="center"
        prop="endtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, "{y}-{m}-{d}") }}</span>
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
            v-hasPermi="['zcrpsgl:zcrjdcj:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zcrpsgl:zcrjdcj:remove']"
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

    <!-- 添加或修改主持人基地对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="基地名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入基地名称" />
        </el-form-item>
        <el-form-item label="主持人" prop="zcrid">
          <el-input v-model="form.zcrid" placeholder="请输入主持人id" />
        </el-form-item>
        <el-form-item label="基地类型" prop="jdtype">
          <el-select v-model="form.jdtype" placeholder="请选择基地类型">
            <el-option
              v-for="dict in jdtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学段" prop="xd">
          <el-select
            v-model="form.xd"
            placeholder="请选择学段"
          >
            <el-option
              v-for="dict in xdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学科" prop="xk">
          <el-select
            v-model="form.xk"
            placeholder="请选择学科"
          >
            <el-option
              v-for="dict in xkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="招生名额" prop="zsme">
          <el-input v-model="form.zsme" placeholder="请输入招生名额" />
        </el-form-item>
        <el-form-item label="报名开始时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择报名开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报名结束时间" prop="endtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择报名结束时间"
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
  listZcrjdcj,
  getZcrjdcj,
  delZcrjdcj,
  addZcrjdcj,
  updateZcrjdcj,
  exportZcrjdcj,
} from "@/api/zcrpsgl/zcrjdcj";

export default {
  name: "Zcrjdcj",
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
      // 主持人基地表格数据
      zcrjdcjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 基地类型字典
      jdtypeOptions: [],
      // 学段字典
      xdOptions: [],
      // 学科字典
      xkOptions: [],
      // 当前报名状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        zcrid: null,
        jdtype: null,
        status: null,
        zsme: null,
        starttime: null,
        endtime: null,
        createUserid: null,
        xk: null,
        xd: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_zcrjdtype").then((response) => {
      this.jdtypeOptions = response.data;
    });
    this.getDicts("sys_dm_xybmzt").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.xdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.xkOptions = response.data;
    });
  },
  methods: {
    /** 查询主持人基地列表 */
    getList() {
      this.loading = true;
      listZcrjdcj(this.queryParams).then((response) => {
        this.zcrjdcjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 基地类型字典翻译
    jdtypeFormat(row, column) {
      return this.selectDictLabel(this.jdtypeOptions, row.jdtype);
    },
    // 当前报名状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 学段字典翻译
    xdFormat(row, column) {
      return this.selectDictLabel(this.xdOptions, row.xd);
    },
    // 学科字典翻译
    xkFormat(row, column) {
      return this.selectDictLabel(this.xkOptions, row.xk);
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
        name: null,
        zcrid: null,
        jdtype: null,
        status: "0",
        zsme: null,
        starttime: null,
        endtime: null,
        createUserid: null,
        createTime: null,
        xk: null,
        xd: null,
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
      this.title = "添加主持人基地";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getZcrjdcj(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主持人基地";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateZcrjdcj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addZcrjdcj(this.form).then((response) => {
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
        '是否确认删除主持人基地编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delZcrjdcj(ids);
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
      this.$confirm("是否确认导出所有主持人基地数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportZcrjdcj(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
