<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="简称" prop="shortName">
        <el-input
          v-model="queryParams.shortName"
          placeholder="请输入简称"
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
          v-hasPermi="['service:serviceInfo:add']"
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
          v-hasPermi="['service:serviceInfo:edit']"
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
          v-hasPermi="['service:serviceInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['service:serviceInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column label="简称" align="center" prop="shortName" :show-overflow-tooltip="true"/>
      <el-table-column label="排序" align="center" prop="sortNum" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="更新人" align="center" prop="updateBy" :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" :show-overflow-tooltip="true">
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
            v-hasPermi="['service:serviceInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:serviceInfo:remove']"
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

    <!-- 添加或修改服务信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="简称" prop="shortName">
          <el-input v-model="form.shortName" placeholder="请输入简称" />
        </el-form-item>
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-divider content-position="center">服务子项目信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddServiceItem">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteServiceItem">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="serviceItemList" :row-class-name="rowServiceItemIndex" @selection-change="handleServiceItemSelectionChange" ref="serviceItem">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="名称" prop="name" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.name" placeholder="请输入名称" />
            </template>
          </el-table-column>
          <el-table-column label="买断项" prop="ifBuyoutItem" width="86">
            <template slot-scope="scope">
              <el-select v-model="scope.row.ifBuyoutItem" placeholder="请选择买断项">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="订单过期时间单位" prop="orderExpireTimeUnit" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.orderExpireTimeUnit" placeholder="请选择订单过期时间单位">
                <el-option
                  v-for="dict in dict.type.date_time_unit"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="订单服务时长" prop="orderServiceDuration" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.orderServiceDuration" placeholder="请输入订单服务时长" />
            </template>
          </el-table-column>
          <el-table-column label="排序" prop="sortNum" width="124">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sortNum" placeholder="请输入排序" />
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
import { listServiceInfo, getServiceInfo, delServiceInfo, addServiceInfo, updateServiceInfo } from "@/api/service/serviceInfo";

export default {
  name: "ServiceInfo",
  dicts: ['date_time_unit', 'sys_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedServiceItem: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 服务信息表格数据
      serviceInfoList: [],
      // 服务子项目表格数据
      serviceItemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        shortName: null,
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
    /** 查询服务信息列表 */
    getList() {
      this.loading = true;
      listServiceInfo(this.queryParams).then(response => {
        this.serviceInfoList = response.rows;
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
        name: null,
        shortName: null,
        sortNum: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.serviceItemList = [];
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
      this.title = "添加服务信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getServiceInfo(id).then(response => {
        this.form = response.data;
        this.serviceItemList = response.data.serviceItemList;
        this.open = true;
        this.title = "修改服务信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.serviceItemList = this.serviceItemList;
          if (this.form.id != null) {
            updateServiceInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServiceInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除服务信息编号为"' + ids + '"的数据项？').then(function() {
        return delServiceInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 服务子项目序号 */
    rowServiceItemIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** 服务子项目添加按钮操作 */
    handleAddServiceItem() {
      let obj = {};
      obj.name = "";
      obj.ifBuyoutItem = "";
      obj.orderExpireTimeUnit = "";
      obj.orderServiceDuration = "";
      obj.sortNum = "";
      obj.updateBy = "";
      obj.updateTiime = "";
      this.serviceItemList.push(obj);
    },
    /** 服务子项目删除按钮操作 */
    handleDeleteServiceItem() {
      if (this.checkedServiceItem.length == 0) {
        this.$modal.msgError("请先选择要删除的服务子项目数据");
      } else {
        const serviceItemList = this.serviceItemList;
        const checkedServiceItem = this.checkedServiceItem;
        this.serviceItemList = serviceItemList.filter(function(item) {
          return checkedServiceItem.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleServiceItemSelectionChange(selection) {
      this.checkedServiceItem = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('service/serviceInfo/export', {
        ...this.queryParams
      }, `serviceInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
