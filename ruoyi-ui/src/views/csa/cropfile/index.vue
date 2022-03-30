<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作物名称" prop="cropName">
        <el-input
          v-model="queryParams.cropName"
          placeholder="请输入作物名称"
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
          v-hasPermi="['csa:cropfile:add']"
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
          v-hasPermi="['csa:cropfile:edit']"
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
          v-hasPermi="['csa:cropfile:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:cropfile:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cropfileList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="作物档案id" align="center" prop="cropId" />
      <el-table-column label="作物名称" align="center" prop="cropName" />
      <el-table-column label="代表图片" align="center" prop="picture" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.picture" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="作物描述" align="center" prop="description" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:cropfile:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:cropfile:remove']"
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

    <!-- 添加或修改作物档案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作物名称" prop="cropName">
          <el-input v-model="form.cropName" placeholder="请输入作物名称" />
        </el-form-item>
<!--        <el-form-item label="种植指南" prop="plantGuide">-->
<!--          <el-input v-model="form.plantGuide" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
        <el-form-item label="代表图片">
          <image-upload v-model="form.picture"/>
        </el-form-item>
        <el-form-item label="作物描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">作物生长阶段信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddCropPhase">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteCropPhase">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="cropPhaseList" :row-class-name="rowCropPhaseIndex" @selection-change="handleCropPhaseSelectionChange" ref="cropPhase">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="阶段名称" prop="phaseName" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.phaseName" placeholder="请输入阶段名称" />
            </template>
          </el-table-column>
          <el-table-column label="产出" prop="production" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.production" placeholder="请输入产出" />
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.status" placeholder="请选择状态">
                <el-option label="请选择字典生成" value="" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" placeholder="请输入备注" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCropfile, getCropfile, delCropfile, addCropfile, updateCropfile } from "@/api/csa/cropfile";

export default {
  name: "Cropfile",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedCropPhase: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 作物档案表格数据
      cropfileList: [],
      // 作物生长阶段表格数据
      cropPhaseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cropName: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cropName: [
          { required: true, message: "作物名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询作物档案列表 */
    getList() {
      this.loading = true;
      listCropfile(this.queryParams).then(response => {
        this.cropfileList = response.rows;
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
        cropId: null,
        cropName: null,
        plantGuide: null,
        picture: null,
        description: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      };
      this.cropPhaseList = [];
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
      this.ids = selection.map(item => item.cropId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加作物档案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const cropId = row.cropId || this.ids
      getCropfile(cropId).then(response => {
        this.form = response.data;
        this.cropPhaseList = response.data.cropPhaseList;
        this.open = true;
        this.title = "修改作物档案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.cropPhaseList = this.cropPhaseList;
          if (this.form.cropId != null) {
            updateCropfile(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCropfile(this.form).then(response => {
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
      const cropIds = row.cropId || this.ids;
      this.$modal.confirm('是否确认删除作物档案编号为"' + cropIds + '"的数据项？').then(function() {
        return delCropfile(cropIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 作物生长阶段序号 */
    rowCropPhaseIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 作物生长阶段添加按钮操作 */
    handleAddCropPhase() {
      let obj = {};
      obj.phaseName = "";
      obj.production = "";
      obj.picture = "";
      obj.status = "";
      obj.remark = "";
      this.cropPhaseList.push(obj);
    },
    /** 作物生长阶段删除按钮操作 */
    handleDeleteCropPhase() {
      if (this.checkedCropPhase.length == 0) {
        this.$modal.msgError("请先选择要删除的作物生长阶段数据");
      } else {
        const cropPhaseList = this.cropPhaseList;
        const checkedCropPhase = this.checkedCropPhase;
        this.cropPhaseList = cropPhaseList.filter(function(item) {
          return checkedCropPhase.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleCropPhaseSelectionChange(selection) {
      this.checkedCropPhase = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('csa/cropfile/export', {
        ...this.queryParams
      }, `cropfile_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
