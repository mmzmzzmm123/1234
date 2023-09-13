<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="是否开启" prop="ifOpen">
        <el-select v-model="queryParams.ifOpen" placeholder="请选择是否开启" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['platform:platformNightFeeConfigure:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['platform:platformNightFeeConfigure:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['platform:platformNightFeeConfigure:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['platform:platformNightFeeConfigure:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="platformNightFeeConfigureList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="是否开启" align="center" prop="ifOpen" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.ifOpen"/>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" :show-overflow-tooltip="true"/>
      <el-table-column label="结束时间" align="center" prop="endTime" :show-overflow-tooltip="true"/>
      <el-table-column label="加收比例" align="center" prop="additionalChargeRatio" :show-overflow-tooltip="true"/>
      <el-table-column label="修改人" align="center" prop="updateBy" :show-overflow-tooltip="true"/>
      <el-table-column label="修改时间" align="center" prop="updateTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['platform:platformNightFeeConfigure:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['platform:platformNightFeeConfigure:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改夜间费用配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="是否开启" prop="ifOpen">
          <el-select v-model="form.ifOpen" placeholder="请选择是否开启">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="开始时间" prop="startTime">
          <el-time-select
            placeholder="开始时间"
            v-model="form.startTime"
            :picker-options="{start: '00:00',step: '00:30',end: '02:00' }">
          </el-time-select>
        </el-form-item>

        <el-form-item label="结束时间" prop="endTime">
          <el-time-select
            placeholder="结束时间"
            v-model="form.endTime"
            :picker-options="{start: '00:00',step: '00:30',end: '06:00',minTime: form.startTime }">
          </el-time-select>
        </el-form-item>

        <el-form-item label="加收比例" prop="additionalChargeRatio">
          <el-input v-model="form.additionalChargeRatio" placeholder="请输入加收比例"/>
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
  listPlatformNightFeeConfigure,
  getPlatformNightFeeConfigure,
  delPlatformNightFeeConfigure,
  addPlatformNightFeeConfigure,
  updatePlatformNightFeeConfigure
} from "@/api/platform/platformNightFeeConfigure";

export default {
  name: "PlatformNightFeeConfigure",
  dicts: ['sys_yes_no'],
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
      // 夜间费用配置表格数据
      platformNightFeeConfigureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ifOpen: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询夜间费用配置列表 */
    getList() {
      this.loading = true;
      listPlatformNightFeeConfigure(this.queryParams).then(response => {
        this.platformNightFeeConfigureList = response.rows;
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
        ifOpen: null,
        startTime: null,
        endTime: null,
        additionalChargeRatio: null,
        createBy: null,
        createTime: null,
        updateBy: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加夜间费用配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPlatformNightFeeConfigure(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改夜间费用配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePlatformNightFeeConfigure(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPlatformNightFeeConfigure(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除夜间费用配置编号为"' + ids + '"的数据项？').then(function () {
        return delPlatformNightFeeConfigure(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('platform/platformNightFeeConfigure/export', {
        ...this.queryParams
      }, `platformNightFeeConfigure_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
