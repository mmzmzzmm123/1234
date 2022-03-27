<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜地" prop="gardenId">
        <el-input
          v-model="queryParams.gardenId"
          placeholder="请输入菜地"
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
          v-hasPermi="['csa:strain:add']"
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
          v-hasPermi="['csa:strain:edit']"
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
          v-hasPermi="['csa:strain:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:strain:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="strainList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="菜地品种id" align="center" prop="id" />
      <el-table-column label="种植任务" align="center" prop="taskId" />
      <el-table-column label="作物id" align="center" prop="cropId" />
      <el-table-column label="作物阶段" align="center" prop="cropPhaseId" />
      <el-table-column label="平方米" align="center" prop="m2" />
      <el-table-column label="产出重量" align="center" prop="outputWeight" />
      <el-table-column label="是否确定" align="center" prop="isDefinite" />
      <el-table-column label="是否种植" align="center" prop="isPlanted" />
      <el-table-column label="是否拉秧" align="center" prop="isEnded" />
      <el-table-column label="确定时间" align="center" prop="definiteDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.definiteDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="种植日期" align="center" prop="plantedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.plantedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="拉秧日期" align="center" prop="endedDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:strain:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:strain:remove']"
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

    <!-- 添加或修改菜地品种对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜地" prop="gardenId">
          <el-input v-model="form.gardenId" placeholder="请输入菜地" />
        </el-form-item>
        <el-form-item label="种植任务" prop="taskId">
          <el-input v-model="form.taskId" placeholder="请输入种植任务" />
        </el-form-item>
        <el-form-item label="作物id" prop="cropId">
          <el-input v-model="form.cropId" placeholder="请输入作物id" />
        </el-form-item>
        <el-form-item label="作物阶段" prop="cropPhaseId">
          <el-input v-model="form.cropPhaseId" placeholder="请输入作物阶段" />
        </el-form-item>
        <el-form-item label="平方米" prop="m2">
          <el-input v-model="form.m2" placeholder="请输入平方米" />
        </el-form-item>
        <el-form-item label="产出重量" prop="outputWeight">
          <el-input v-model="form.outputWeight" placeholder="请输入产出重量" />
        </el-form-item>
        <el-form-item label="是否确定" prop="isDefinite">
          <el-input v-model="form.isDefinite" placeholder="请输入是否确定" />
        </el-form-item>
        <el-form-item label="是否种植" prop="isPlanted">
          <el-input v-model="form.isPlanted" placeholder="请输入是否种植" />
        </el-form-item>
        <el-form-item label="是否拉秧" prop="isEnded">
          <el-input v-model="form.isEnded" placeholder="请输入是否拉秧" />
        </el-form-item>
        <el-form-item label="确定时间" prop="definiteDate">
          <el-date-picker clearable
            v-model="form.definiteDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择确定时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="种植日期" prop="plantedDate">
          <el-date-picker clearable
            v-model="form.plantedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择种植日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="拉秧日期" prop="endedDate">
          <el-date-picker clearable
            v-model="form.endedDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择拉秧日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listStrain, getStrain, delStrain, addStrain, updateStrain } from "@/api/csa/strain";

export default {
  name: "Strain",
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
      // 菜地品种表格数据
      strainList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gardenId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        gardenId: [
          { required: true, message: "菜地不能为空", trigger: "blur" }
        ],
        cropId: [
          { required: true, message: "作物id不能为空", trigger: "blur" }
        ],
        m2: [
          { required: true, message: "平方米不能为空", trigger: "blur" }
        ],
        outputWeight: [
          { required: true, message: "产出重量不能为空", trigger: "blur" }
        ],
        isDefinite: [
          { required: true, message: "是否确定不能为空", trigger: "blur" }
        ],
        isPlanted: [
          { required: true, message: "是否种植不能为空", trigger: "blur" }
        ],
        isEnded: [
          { required: true, message: "是否拉秧不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询菜地品种列表 */
    getList() {
      this.loading = true;
      listStrain(this.queryParams).then(response => {
        this.strainList = response.rows;
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
        gardenId: null,
        taskId: null,
        cropId: null,
        cropPhaseId: null,
        m2: null,
        outputWeight: null,
        isDefinite: null,
        isPlanted: null,
        isEnded: null,
        definiteDate: null,
        plantedDate: null,
        endedDate: null,
        status: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.title = "添加菜地品种";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStrain(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜地品种";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStrain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStrain(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除菜地品种编号为"' + ids + '"的数据项？').then(function() {
        return delStrain(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('csa/strain/export', {
        ...this.queryParams
      }, `strain_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
