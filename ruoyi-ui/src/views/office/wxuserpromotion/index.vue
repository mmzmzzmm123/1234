<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户" prop="wxId">
        <el-input v-model="queryParams.wxId" placeholder="请输入wx用户" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item label="优惠券名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>


      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
  <!--    <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['office:wxuserpromotion:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['office:wxuserpromotion:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['office:wxuserpromotion:remove']">删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:wxuserpromotion:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxuserpromotionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户" align="center" prop="wxId" />
      <!-- <el-table-column label="优惠券名称" align="center" prop="couponId" /> -->
      <el-table-column label="优惠券名称" align="center" prop="name" />
      <el-table-column label="优惠券类型" align="center" prop="couponType" :formatter="couponTypeFormatter" />
      <el-table-column label="适用商户" align="center" prop="merchantId" />
      <el-table-column label="适用门店" align="center" prop="storeId" :formatter="storeFormatter" />
      <el-table-column label="标准金额" align="center" prop="standardPrice" />
      <el-table-column label="优惠金额" align="center" prop="discountPrice" />
      <el-table-column label="抵扣时长" align="center" prop="maxMinute" />
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
      <el-table-column label="周内可用日期" align="center" prop="weekDays" />
      <!-- <el-table-column label="状态" align="center" prop="status" /> -->
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['office:wxuserpromotion:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['office:wxuserpromotion:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改用户优惠券 t_wx_user_promotion对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="wx用户" prop="wxId">
          <el-input v-model="form.wxId" placeholder="请输入wx用户" />
        </el-form-item>
        <el-form-item label="优惠券id" prop="couponId">
          <el-input v-model="form.couponId" placeholder="请输入优惠券id" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="适用商户 " prop="merchantId">
          <el-input v-model="form.merchantId" placeholder="请输入适用商户" />
        </el-form-item>
        <el-form-item label="适用门店 0 所有门店适用" prop="storeId">
          <el-input v-model="form.storeId" placeholder="请输入适用门店" />
        </el-form-item>
        <el-form-item label="标准金额" prop="standardPrice">
          <el-input v-model="form.standardPrice" placeholder="请输入标准金额" />
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountPrice">
          <el-input v-model="form.discountPrice" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="抵扣时长" prop="maxMinute">
          <el-input v-model="form.maxMinute" placeholder="请输入抵扣时长" />
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker clearable v-model="form.startDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择开始日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker clearable v-model="form.endDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择结束日期">
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
  import {
    listWxuserpromotion,
    getWxuserpromotion,
    delWxuserpromotion,
    addWxuserpromotion,
    updateWxuserpromotion
  } from "@/api/office/wxuserpromotion";

  import {
    listStore
  } from "@/api/office/store";

  export default {
    name: "Wxuserpromotion",
    dicts: ["promotion_type", "valid_type"],
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
        // 用户优惠券 t_wx_user_promotion表格数据
        wxuserpromotionList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          wxId: null,
          couponId: null,
          name: null,
          couponType: null,
          merchantId: null,
          storeId: null,
          standardPrice: null,
          discountPrice: null,
          maxMinute: null,
          startDate: null,
          endDate: null,
          weekDays: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          name: [{
            required: true,
            message: "名称不能为空",
            trigger: "blur"
          }],
          couponType: [{
            required: true,
            message: "优惠券类型(满减、免费体验券)不能为空",
            trigger: "change"
          }],
          storeId: [{
            required: true,
            message: "适用门店 0 所有门店适用不能为空",
            trigger: "blur"
          }],
          discountPrice: [{
            required: true,
            message: "优惠金额不能为空",
            trigger: "blur"
          }],
        },
        weekDays: [],
        storeOptions: [],
        weekOptions: [{
          label: '周一',
          value: '1'
        }, {
          label: '周二',
          value: '2'
        }, {
          label: '周三',
          value: '3'
        }, {
          label: '周四',
          value: '4'
        }, {
          label: '周五',
          value: '5'
        }, {
          label: '周六',
          value: '6'
        }, {
          label: '周日',
          value: '7'
        }],
      };
    },
    created() {
      this.getList();
      this.getStoreOptions();
    },
    methods: {
      /** 查询用户优惠券 t_wx_user_promotion列表 */
      getList() {
        this.loading = true;
        listWxuserpromotion(this.queryParams).then(response => {
          this.wxuserpromotionList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      getStoreOptions() {
        listStore().then(response => {
          this.storeOptions = response.rows;
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
          wxId: null,
          couponId: null,
          name: null,
          couponType: null,
          merchantId: null,
          storeId: null,
          standardPrice: null,
          discountPrice: null,
          maxMinute: null,
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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加用户优惠券 t_wx_user_promotion";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getWxuserpromotion(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改用户优惠券 t_wx_user_promotion";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateWxuserpromotion(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addWxuserpromotion(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除用户优惠券 t_wx_user_promotion编号为"' + ids + '"的数据项？').then(function() {
          return delWxuserpromotion(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/wxuserpromotion/export', {
          ...this.queryParams
        }, `wxuserpromotion_${new Date().getTime()}.xlsx`)
      },
      storeFormatter(row) {
        var res = "";
        for (var i = 0; i < this.storeOptions.length; i++) {
          if (this.storeOptions[i].id == row.storeId) {
            res = this.storeOptions[i].name;
            break;
          }
        }
        return res;
      },
      validTypeFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.valid_type, row.validType);
      },
      couponTypeFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.promotion_type, row.couponType);
      },
    }
  };
</script>
