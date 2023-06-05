<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
 <!--     <el-form-item label="用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="适用门店" prop="storeId">
        <el-input
          v-model="queryParams.storeId"
          placeholder="请输入适用门店"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="卡券名称" prop="couponName">
        <el-input
          v-model="queryParams.couponName"
          placeholder="请输入名称"
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
          v-hasPermi="['office:wxusercoupon:add']"
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
          v-hasPermi="['office:wxusercoupon:edit']"
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
          v-hasPermi="['office:wxusercoupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['office:wxusercoupon:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxusercouponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="适用门店" align="center" prop="storeId" />
      <el-table-column label="卡券id" align="center" prop="couponId" />
      <el-table-column label="名称" align="center" prop="couponName" />
      <el-table-column label="原价" align="center" prop="originalPrice" />
      <el-table-column label="当前价格" align="center" prop="price" />
      <el-table-column label="卡券类型(时长卡、次数卡)" align="center" prop="cardType" />
      <el-table-column label="可使用次数(针对次数卡)" align="center" prop="numCanUse" />
      <el-table-column label="单日只扣一次次数(针对次数卡)" align="center" prop="oneTimeOneDay" />
      <el-table-column label="每次最多抵扣时长(针对时长卡)" align="center" prop="maxMinuteOnce" />
      <el-table-column label="剩余时长" align="center" prop="minuteLeft" />
      <el-table-column label="开始日期" align="center" prop="startDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="周内可用日期(1,3,4,7)" align="center" prop="weekDays" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
     <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['office:wxusercoupon:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['office:wxusercoupon:remove']"
          >删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户优惠券对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="适用门店" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入适用门店" />
        </el-form-item>
        <el-form-item label="卡券id" prop="couponId">
          <el-input v-model="form.couponId" placeholder="请输入卡券id" />
        </el-form-item>
        <el-form-item label="名称" prop="couponName">
          <el-input v-model="form.couponName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="原价" prop="originalPrice">
          <el-input v-model="form.originalPrice" placeholder="请输入原价" />
        </el-form-item>
        <el-form-item label="当前价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入当前价格" />
        </el-form-item>
        <el-form-item label="可使用次数(针对次数卡)" prop="numCanUse">
          <el-input v-model="form.numCanUse" placeholder="请输入可使用次数(针对次数卡)" />
        </el-form-item>
        <el-form-item label="单日只扣一次次数(针对次数卡)" prop="oneTimeOneDay">
          <el-input v-model="form.oneTimeOneDay" placeholder="请输入单日只扣一次次数(针对次数卡)" />
        </el-form-item>
        <el-form-item label="每次最多抵扣时长(针对时长卡)" prop="maxMinuteOnce">
          <el-input v-model="form.maxMinuteOnce" placeholder="请输入每次最多抵扣时长(针对时长卡)" />
        </el-form-item>
        <el-form-item label="剩余时长" prop="minuteLeft">
          <el-input v-model="form.minuteLeft" placeholder="请输入剩余时长" />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable
            v-model="form.startDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable
            v-model="form.endDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周内可用日期(1,3,4,7)" prop="weekDays">
          <el-input v-model="form.weekDays" placeholder="请输入周内可用日期(1,3,4,7)" />
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
import { listWxusercoupon, getWxusercoupon, delWxusercoupon, addWxusercoupon, updateWxusercoupon } from "@/api/office/wxusercoupon";

export default {
  name: "Wxusercoupon",
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
      // 用户优惠券表格数据
      wxusercouponList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        storeId: null,
        couponId: null,
        couponName: null,
        originalPrice: null,
        price: null,
        cardType: null,
        numCanUse: null,
        oneTimeOneDay: null,
        maxMinuteOnce: null,
        minuteLeft: null,
        startDate: null,
        endDate: null,
        weekDays: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        cardType: [
          { required: true, message: "卡券类型(时长卡、次数卡)不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户优惠券列表 */
    getList() {
      this.loading = true;
      listWxusercoupon(this.queryParams).then(response => {
        this.wxusercouponList = response.rows;
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
        userId: null,
        storeId: null,
        couponId: null,
        couponName: null,
        originalPrice: null,
        price: null,
        cardType: null,
        numCanUse: null,
        oneTimeOneDay: null,
        maxMinuteOnce: null,
        minuteLeft: null,
        startDate: null,
        endDate: null,
        weekDays: null,
        status: null,
        remark: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户优惠券";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWxusercoupon(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户优惠券";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWxusercoupon(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWxusercoupon(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户优惠券编号为"' + ids + '"的数据项？').then(function() {
        return delWxusercoupon(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('office/wxusercoupon/export', {
        ...this.queryParams
      }, `wxusercoupon_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
