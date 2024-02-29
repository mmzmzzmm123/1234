<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属车系id" prop="seriesId">
        <el-input
          v-model="queryParams.seriesId"
          placeholder="请输入所属车系id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车辆具体名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入车辆具体名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="外观颜色" prop="color">
        <el-input
          v-model="queryParams.color"
          placeholder="请输入外观颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内饰颜色" prop="innerColor">
        <el-input
          v-model="queryParams.innerColor"
          placeholder="请输入内饰颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前车辆所在地" prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入当前车辆所在地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车源地" prop="sourceArea">
        <el-input
          v-model="queryParams.sourceArea"
          placeholder="请输入车源地"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注信息" prop="notes">
        <el-input
          v-model="queryParams.notes"
          placeholder="请输入备注信息"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车龄" prop="age">
        <el-input
          v-model="queryParams.age"
          placeholder="请输入车龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卖家id" prop="ownerId">
        <el-input
          v-model="queryParams.ownerId"
          placeholder="请输入卖家id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="售卖价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入售卖价格"
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
          v-hasPermi="['system:source:add']"
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
          v-hasPermi="['system:source:edit']"
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
          v-hasPermi="['system:source:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:source:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sourceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="${comment}" align="center" prop="id" />
      <el-table-column label="所属车系id" align="center" prop="seriesId" />
      <el-table-column label="车辆具体名称" align="center" prop="name" />
      <el-table-column label="外观颜色" align="center" prop="color" />
      <el-table-column label="内饰颜色" align="center" prop="innerColor" />
      <el-table-column label="当前车辆所在地" align="center" prop="area" />
      <el-table-column label="车辆状态/" align="center" prop="status" />
      <el-table-column label="车源地" align="center" prop="sourceArea" />
      <el-table-column label="备注信息" align="center" prop="notes" />
      <el-table-column label="车龄" align="center" prop="age" />
      <el-table-column label="卖家id" align="center" prop="ownerId" />
      <el-table-column label="售卖价格" align="center" prop="price" />
      <el-table-column label="车辆类型：中规车&国产现车&进口车&合资车" align="center" prop="type" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:source:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:source:remove']"
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

    <!-- 添加或修改车源管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属车系id" prop="seriesId">
          <el-input v-model="form.seriesId" placeholder="请输入所属车系id" />
        </el-form-item>
        <el-form-item label="车辆具体名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入车辆具体名称" />
        </el-form-item>
        <el-form-item label="外观颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入外观颜色" />
        </el-form-item>
        <el-form-item label="内饰颜色" prop="innerColor">
          <el-input v-model="form.innerColor" placeholder="请输入内饰颜色" />
        </el-form-item>
        <el-form-item label="当前车辆所在地" prop="area">
          <el-input v-model="form.area" placeholder="请输入当前车辆所在地" />
        </el-form-item>
        <el-form-item label="车源地" prop="sourceArea">
          <el-input v-model="form.sourceArea" placeholder="请输入车源地" />
        </el-form-item>
        <el-form-item label="备注信息" prop="notes">
          <el-input v-model="form.notes" placeholder="请输入备注信息" />
        </el-form-item>
        <el-form-item label="车龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入车龄" />
        </el-form-item>
        <el-form-item label="卖家id" prop="ownerId">
          <el-input v-model="form.ownerId" placeholder="请输入卖家id" />
        </el-form-item>
        <el-form-item label="售卖价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入售卖价格" />
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
import { listSource, getSource, delSource, addSource, updateSource } from "@/api/system/source";

export default {
  name: "Source",
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
      // 车源管理表格数据
      sourceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        seriesId: null,
        name: null,
        color: null,
        innerColor: null,
        area: null,
        status: null,
        sourceArea: null,
        notes: null,
        age: null,
        ownerId: null,
        price: null,
        type: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        seriesId: [
          { required: true, message: "所属车系id不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "车辆具体名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询车源管理列表 */
    getList() {
      this.loading = true;
      listSource(this.queryParams).then(response => {
        this.sourceList = response.rows;
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
        seriesId: null,
        name: null,
        color: null,
        innerColor: null,
        area: null,
        status: null,
        sourceArea: null,
        notes: null,
        age: null,
        ownerId: null,
        price: null,
        type: null,
        createTime: null,
        updateTime: null
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
      this.title = "添加车源管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSource(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改车源管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSource(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSource(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除车源管理编号为"' + ids + '"的数据项？').then(function() {
        return delSource(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/source/export', {
        ...this.queryParams
      }, `source_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
