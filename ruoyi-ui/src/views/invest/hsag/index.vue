<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="时间" prop="sj">
        <el-date-picker clearable
          v-model="queryParams.sj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="收盘点位" prop="spdw">
        <el-input
          v-model="queryParams.spdw"
          placeholder="请输入收盘点位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pe数值" prop="peCv">
        <el-input
          v-model="queryParams.peCv"
          placeholder="请输入pe数值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pb数值" prop="pbCv">
        <el-input
          v-model="queryParams.pbCv"
          placeholder="请输入pb数值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ps数值" prop="psCv">
        <el-input
          v-model="queryParams.psCv"
          placeholder="请输入ps数值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pe10年百分位" prop="pePos10">
        <el-input
          v-model="queryParams.pePos10"
          placeholder="请输入pe10年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pb10年百分位" prop="pbPos10">
        <el-input
          v-model="queryParams.pbPos10"
          placeholder="请输入pb10年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ps10年百分位" prop="psPos10">
        <el-input
          v-model="queryParams.psPos10"
          placeholder="请输入ps10年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pe20年百分位" prop="pePos20">
        <el-input
          v-model="queryParams.pePos20"
          placeholder="请输入pe20年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="pb20年百分位" prop="pbPos20">
        <el-input
          v-model="queryParams.pbPos20"
          placeholder="请输入pb20年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="ps20年百分位" prop="psPos20">
        <el-input
          v-model="queryParams.psPos20"
          placeholder="请输入ps20年百分位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="信号" prop="zonghePos">
        <el-input
          v-model="queryParams.zonghePos"
          placeholder="请输入信号"
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
          v-hasPermi="['invest:hsag:add']"
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
          v-hasPermi="['invest:hsag:edit']"
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
          v-hasPermi="['invest:hsag:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['invest:hsag:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hsagList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="时间" align="center" prop="sj" />
      <el-table-column label="收盘点位" align="center" prop="spdw" />
      <el-table-column label="pe数值" align="center" prop="peCv" />
      <el-table-column label="pb数值" align="center" prop="pbCv" />
      <el-table-column label="ps数值" align="center" prop="psCv" />
      <el-table-column label="pe10年百分位" align="center" prop="pePos10" />
      <el-table-column label="pb10年百分位" align="center" prop="pbPos10" />
      <el-table-column label="ps10年百分位" align="center" prop="psPos10" />
      <el-table-column label="pe20年百分位" align="center" prop="pePos20" />
      <el-table-column label="pb20年百分位" align="center" prop="pbPos20" />
      <el-table-column label="ps20年百分位" align="center" prop="psPos20" />
      <el-table-column label="信号" align="center" prop="zonghePos" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['invest:hsag:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['invest:hsag:remove']"
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

    <!-- 添加或修改信号与系统对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="收盘点位" prop="spdw">
          <el-input v-model="form.spdw" placeholder="请输入收盘点位" />
        </el-form-item>
        <el-form-item label="pe数值" prop="peCv">
          <el-input v-model="form.peCv" placeholder="请输入pe数值" />
        </el-form-item>
        <el-form-item label="pb数值" prop="pbCv">
          <el-input v-model="form.pbCv" placeholder="请输入pb数值" />
        </el-form-item>
        <el-form-item label="ps数值" prop="psCv">
          <el-input v-model="form.psCv" placeholder="请输入ps数值" />
        </el-form-item>
        <el-form-item label="pe10年百分位" prop="pePos10">
          <el-input v-model="form.pePos10" placeholder="请输入pe10年百分位" />
        </el-form-item>
        <el-form-item label="pb10年百分位" prop="pbPos10">
          <el-input v-model="form.pbPos10" placeholder="请输入pb10年百分位" />
        </el-form-item>
        <el-form-item label="ps10年百分位" prop="psPos10">
          <el-input v-model="form.psPos10" placeholder="请输入ps10年百分位" />
        </el-form-item>
        <el-form-item label="pe20年百分位" prop="pePos20">
          <el-input v-model="form.pePos20" placeholder="请输入pe20年百分位" />
        </el-form-item>
        <el-form-item label="pb20年百分位" prop="pbPos20">
          <el-input v-model="form.pbPos20" placeholder="请输入pb20年百分位" />
        </el-form-item>
        <el-form-item label="ps20年百分位" prop="psPos20">
          <el-input v-model="form.psPos20" placeholder="请输入ps20年百分位" />
        </el-form-item>
        <el-form-item label="信号" prop="zonghePos">
          <el-input v-model="form.zonghePos" placeholder="请输入信号" />
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
import { listHsag, getHsag, delHsag, addHsag, updateHsag } from "@/api/invest/hsag";

export default {
  name: "Hsag",
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
      // 信号与系统表格数据
      hsagList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sj: null,
        spdw: null,
        peCv: null,
        pbCv: null,
        psCv: null,
        pePos10: null,
        pbPos10: null,
        psPos10: null,
        pePos20: null,
        pbPos20: null,
        psPos20: null,
        zonghePos: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询信号与系统列表 */
    getList() {
      this.loading = true;
      listHsag(this.queryParams).then(response => {
        this.hsagList = response.rows;
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
        sj: null,
        spdw: null,
        peCv: null,
        pbCv: null,
        psCv: null,
        pePos10: null,
        pbPos10: null,
        psPos10: null,
        pePos20: null,
        pbPos20: null,
        psPos20: null,
        zonghePos: null
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
      this.ids = selection.map(item => item.sj)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加信号与系统";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const sj = row.sj || this.ids
      getHsag(sj).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改信号与系统";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.sj != null) {
            updateHsag(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHsag(this.form).then(response => {
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
      const sjs = row.sj || this.ids;
      this.$modal.confirm('是否确认删除信号与系统编号为"' + sjs + '"的数据项？').then(function() {
        return delHsag(sjs);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('invest/hsag/export', {
        ...this.queryParams
      }, `hsag_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
