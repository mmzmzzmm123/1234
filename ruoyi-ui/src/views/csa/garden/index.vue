<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜地编号" prop="code" label-width="100">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入菜地编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分区代码" prop="zoneCode">
        <el-input
          v-model="queryParams.zoneCode"
          placeholder="请输入分区代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜地名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜地名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竣工状态" prop="isCompleted">
        <el-input
          v-model="queryParams.isCompleted"
          placeholder="请输入竣工状态"
          clearable
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['csa:garden:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['csa:garden:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['csa:garden:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:garden:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gardenList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="菜地编号" align="center" prop="code" />
      <el-table-column label="分区代码" align="center" prop="zoneCode" />
      <el-table-column label="菜地名称" align="center" prop="name" />
      <el-table-column label="平方米" align="center" prop="m2" />
      <el-table-column label="竣工状态" align="center" prop="isCompleted" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:garden:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:garden:remove']"
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

    <!-- 添加或修改菜地划分对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜地编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入菜地编号" />
        </el-form-item>
        <el-form-item label="农场分区" prop="zoneCode">
          <el-input v-model="form.zoneCode" placeholder="请输入分区代码" />
        </el-form-item>
        <el-form-item label="菜地名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入菜地名称" />
        </el-form-item>
        <el-form-item label="平方米" prop="m2">
          <el-input v-model="form.m2" placeholder="请输入平方米" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listGarden, getGarden, delGarden, addGarden, updateGarden } from "@/api/csa/garden";

export default {
  name: "Garden",
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
      // 菜地划分表格数据
      gardenList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        zoneCode: null,
        name: null,
        memberId: null,
        managerId: null,
        workerId: null,
        contacterId: null,
        isFarming: null,
        isSelled: null,
        isCompleted: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: "菜地编号不能为空", trigger: "blur" }
        ],
        zoneCode: [
          { required: true, message: "分区代码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "菜地名称不能为空", trigger: "blur" }
        ],
        m2: [
          { required: true, message: "平方米不能为空", trigger: "blur" }
        ],
        coins: [
          { required: true, message: "金币不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询菜地划分列表 */
    getList() {
      this.loading = true;
      listGarden(this.queryParams).then(response => {
        this.gardenList = response.rows;
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
        code: null,
        zoneCode: null,
        name: null,
        m2: null,
        memberId: null,
        managerId: null,
        workerId: null,
        contacterId: null,
        coins: null,
        isFarming: null,
        isSelled: null,
        isCompleted: null,
        status: "0",
        delFlag: null,
        remark: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加菜地划分";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGarden(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜地划分";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGarden(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGarden(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除菜地Id为"' + ids + '"的数据项？').then(function() {
        return delGarden(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('csa/garden/export', {
        ...this.queryParams
      }, `garden_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
