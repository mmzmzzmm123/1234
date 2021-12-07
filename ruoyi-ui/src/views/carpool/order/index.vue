<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNum">
        <el-input v-model="queryParams.orderNum" placeholder="请输入订单号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出发地" prop="departure">
        <el-input v-model="queryParams.departure" placeholder="请输入出发地" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="目的地" prop="destination">
        <el-input v-model="queryParams.destination" placeholder="请输入目的地" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出发时间" prop="departureTime">
        <el-date-picker clearable size="small" v-model="queryParams.departureTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择出发时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择订单状态">
          <el-option v-for="dict in dict.type.carpool_order_state" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否接单" prop="isTake">
        <el-select v-model="queryParams.isTake" placeholder="请选择接单状态">
          <el-option v-for="dict in dict.type.carpool_is_take" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="司机姓名" prop="driverName">
        <el-input v-model="queryParams.driverName" placeholder="请输入司机姓名" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['carpool:order:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['carpool:order:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['carpool:order:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['carpool:order:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--      <el-table-column label="autoID" align="center" prop="id" />-->
      <el-table-column label="订单号" align="center" prop="orderNum" />
      <el-table-column label="出发地" align="center" prop="departure" />
      <el-table-column label="目的地" align="center" prop="destination" />
      <el-table-column label="出发时间" align="center" prop="departureTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.departureTime ,'{y}-{m}-{d} {h}:{i}:{s}')}}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前人数" align="center" prop="member" />
      <el-table-column label="是否接单" align="center" prop="isTake">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carpool_is_take" :value="scope.row.isTake" />
        </template>
      </el-table-column>
      <el-table-column label="接单司机" align="center" prop="driverName" />
      <el-table-column label="订单当前的状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.carpool_order_state" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['carpool:order:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['carpool:order:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="出发地" prop="departure">
          <el-input v-model="form.departure" placeholder="请输入出发地" />
        </el-form-item>
        <el-form-item label="目的地" prop="destination">
          <el-input v-model="form.destination" placeholder="请输入目的地" />
        </el-form-item>
        <el-form-item label="出发时间" prop="departureTime">
          <el-date-picker clearable size="small" v-model="form.departureTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择出发时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="当前人数" prop="member">
          <el-input v-model="form.member" placeholder="请输入当前人数" />
        </el-form-item>
        <el-form-item label="订单状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择订单状态">
            <el-option v-for="dict in dict.type.carpool_order_state" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
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
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
} from "@/api/carpool/order";

export default {
  name: "Order",
  dicts: ["sys_user_sex", "carpool_order_state", "carpool_is_take"],
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
      // 订单信息表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNum: null,
        departure: null,
        destination: null,
        departureTime: null,
        member: null,
        state: null,
        isTake: null,
        driverName: null,
        isdelete: null,
        createUser: null,
        updateUser: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then((response) => {
        this.orderList = response.rows;
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
        orderNum: null,
        departure: null,
        destination: null,
        departureTime: null,
        member: null,
        state: null,
        isdelete: null,
        createUser: null,
        createTime: null,
        updateUser: null,
        updateTime: null,
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
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getOrder(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then((response) => {
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
      this.$modal
        .confirm('是否确认删除订单信息编号为"' + ids + '"的数据项？')
        .then(function () {
          return delOrder(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "carpool/order/export",
        {
          ...this.queryParams,
        },
        `order_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
