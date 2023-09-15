<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="服务信息" prop="serviceId">
        <el-select
          v-model="queryParams.serviceId"
          filterable
          remote
          clearable
          reserve-keyword
          placeholder="请输入服务关键词"
          :remote-method="getServiceInfo"
          :loading="queryServiceLoading">
          <el-option
            v-for="item in queryServiceInfoList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="买断项" prop="ifBuyoutItem">
        <el-select v-model="queryParams.ifBuyoutItem" placeholder="请选择买断项" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="时间单位" prop="orderExpireTimeUnit">
        <el-select v-model="queryParams.orderExpireTimeUnit" placeholder="请选择订单过期时间单位" clearable>
          <el-option
            v-for="dict in dict.type.date_time_unit"
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          plain-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['service:serviceItem:add']"-->
      <!--        >新增</el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['service:serviceItem:edit']"
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
          v-hasPermi="['service:serviceItem:remove']"
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
          v-hasPermi="['service:serviceItem:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="服务" align="center" prop="serviceId" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{ scope.row.serviceInfo.name }}
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column label="买断项" align="center" prop="ifBuyoutItem" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.ifBuyoutItem"/>
        </template>
      </el-table-column>
      <el-table-column label="订单过期时间单位" align="center" prop="orderExpireTimeUnit" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.date_time_unit" :value="scope.row.orderExpireTimeUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="订单服务时长" align="center" prop="orderServiceDuration" :show-overflow-tooltip="true"/>
      <el-table-column label="排序" align="center" prop="sortNum" :show-overflow-tooltip="true"/>
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
            v-hasPermi="['service:serviceItem:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['service:serviceItem:remove']"
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

    <!-- 添加或修改服务子项目对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="买断项" prop="ifBuyoutItem">
          <el-select v-model="form.ifBuyoutItem" placeholder="请选择买断项">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间单位" prop="orderExpireTimeUnit">
          <el-select v-model="form.orderExpireTimeUnit" placeholder="请选择订单过期时间单位">
            <el-option
              v-for="dict in dict.type.date_time_unit"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="服务时长" prop="orderServiceDuration">
          <el-input v-model="form.orderServiceDuration" placeholder="请输入订单服务时长"/>
        </el-form-item>
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序"/>
        </el-form-item>
        <el-divider content-position="center">服务子项目价格信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddServiceItemPrice">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteServiceItemPrice">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="serviceItemPriceList" :row-class-name="rowServiceItemPriceIndex"
                  @selection-change="handleServiceItemPriceSelectionChange" ref="serviceItemPrice">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="等级" prop="staffLevelConfigId" width="180">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.staffLevelConfigId"
                filterable
                remote
                reserve-keyword
                clearable
                placeholder="请输入等级关键字"
                :remote-method="getStaffLevelConfig"
                :loading="queryStaffLevelConfigLoading">
                <el-option
                  v-for="item in queryStaffLevelConfigList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="价格" prop="price" width="180">
            <template slot-scope="scope">
              <el-input type="number" v-model="scope.row.price" placeholder="请输入价格"/>
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
import {
  listServiceItem,
  getServiceItem,
  delServiceItem,
  addServiceItem,
  updateServiceItem
} from "@/api/service/serviceItem";
import {listServiceInfo} from "@/api/service/serviceInfo";
import {listStaffLevelConfig} from "@/api/staff/staffLevelConfig";

export default {
  name: "ServiceItem",
  dicts: ['date_time_unit', 'sys_yes_no'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedServiceItemPrice: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 服务子项目表格数据
      serviceItemList: [],
      // 服务子项目价格表格数据
      serviceItemPriceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serviceId: null,
        name: null,
        ifBuyoutItem: null,
        orderExpireTimeUnit: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      queryServiceInfoList: [],
      queryServiceLoading: false,
      queryStaffLevelConfigList: [],
      queryStaffLevelConfigLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /**
     * 查询员工等级配置信息
     * */
    getStaffLevelConfig(name) {
      if (name == null || name === "") {
        this.queryStaffLevelConfigList = [];
        return;
      }
      this.queryStaffLevelConfigLoading = true;
      listStaffLevelConfig({name: name}).then(res => {
        this.queryStaffLevelConfigList = res.rows;
      }).finally(e => {
        this.queryStaffLevelConfigLoading = false;
      })
    },
    /**
     * 查询服务信息
     * */
    getServiceInfo(name) {
      if (name == null || name === "") {
        this.queryServiceInfoList = [];
        return;
      }
      this.queryServiceLoading = true;
      listServiceInfo({name: name}).then(res => {
        this.queryServiceInfoList = res.rows;
      }).finally(e => {
        this.queryServiceLoading = false;
      })
    },
    /** 查询服务子项目列表 */
    getList() {
      this.loading = true;
      listServiceItem(this.queryParams).then(response => {
        this.serviceItemList = response.rows;
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
        serviceId: null,
        name: null,
        ifBuyoutItem: null,
        orderExpireTimeUnit: null,
        orderServiceDuration: null,
        sortNum: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.serviceItemPriceList = [];
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
      this.title = "添加服务子项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      listStaffLevelConfig({}).then(res => {
        this.queryStaffLevelConfigList = res.rows;
      })
      getServiceItem(id).then(response => {
        this.form = response.data;
        this.serviceItemPriceList = response.data.serviceItemPriceList;
        this.open = true;
        this.title = "修改服务子项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.serviceItemPriceList = this.serviceItemPriceList;
          if (this.form.id != null) {
            updateServiceItem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addServiceItem(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除服务子项目编号为"' + ids + '"的数据项？').then(function () {
        return delServiceItem(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 服务子项目价格序号 */
    rowServiceItemPriceIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 服务子项目价格添加按钮操作 */
    handleAddServiceItemPrice() {
      let obj = {};
      obj.staffLevelConfigId = "";
      obj.price = "";
      this.serviceItemPriceList.push(obj);
    },
    /** 服务子项目价格删除按钮操作 */
    handleDeleteServiceItemPrice() {
      if (this.checkedServiceItemPrice.length == 0) {
        this.$modal.msgError("请先选择要删除的服务子项目价格数据");
      } else {
        const serviceItemPriceList = this.serviceItemPriceList;
        const checkedServiceItemPrice = this.checkedServiceItemPrice;
        this.serviceItemPriceList = serviceItemPriceList.filter(function (item) {
          return checkedServiceItemPrice.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleServiceItemPriceSelectionChange(selection) {
      this.checkedServiceItemPrice = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('service/serviceItem/export', {
        ...this.queryParams
      }, `serviceItem_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
