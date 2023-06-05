<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="couponName">
        <el-input v-model="queryParams.couponName" placeholder="请输入名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="适用门店" prop="storeId">
        <el-select v-model="queryParams.storeId" clearable filterable>
          <el-option v-for="option in storeOptions" :key="option.id" :label="option.name" :value="option.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="卡类型" prop="cardType">
        <el-select v-model="queryParams.cardType" clearable filterable>
          <el-option v-for="option in dict.type.coupon_type" :label="option.label" :key="option.value"
            :value="option.value"></el-option>
        </el-select>
      </el-form-item>

      <!-- <el-form-item label="开始日期" prop="startDate">
        <el-date-picker clearable v-model="queryParams.startDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择开始日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束日期" prop="endDate">
        <el-date-picker clearable v-model="queryParams.endDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择结束日期">
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="周内可用日期(1,3,4,7)" prop="weekDays">
        <el-input v-model="queryParams.weekDays" placeholder="请输入周内可用日期(1,3,4,7)" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['office:storecoupon:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['office:storecoupon:edit']"
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
          v-hasPermi="['office:storecoupon:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:storecoupon:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storecouponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="couponName" />
      <el-table-column label="适用门店" align="center" prop="storeId" :formatter="storeFormatter" />
      <el-table-column label="原价" align="center" prop="originalPrice" />
      <el-table-column label="当前价格" align="center" prop="price" />
      <el-table-column label="卡券类型" align="center" prop="cardType" :formatter="cardTypeFormatter" />
      <el-table-column label="可使用次数" align="center" prop="numCanUse" />
      <!-- <el-table-column label="单日只扣一次次数(针对次数卡)" align="center" prop="oneTimeOneDay" /> -->
      <el-table-column label="抵扣时长(小时)" align="center" prop="maxMinuteOnce" />
      <!-- <el-table-column label="购买限制(余额/积分)" align="center" prop="payType" /> -->
      <el-table-column label="可购买次数" align="center" prop="maxHold" />
       <el-table-column label="有效期类型" align="center" prop="validType" :formatter="validTypeFormatter"/>
        <el-table-column label="自购买日期起天数" align="center" prop="validDays" />
      <el-table-column label="开始日期" align="center" prop="startDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束日期" align="center" prop="endDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="周内可用日期" align="center" prop="weekDays" />
      <!--   <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['office:storecoupon:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['office:storecoupon:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改卡券对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span=12>
            <el-form-item label="名称" prop="couponName">
              <el-input v-model="form.couponName" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="可购买次数" prop="maxHold">
              <el-input-number v-model="form.maxHold" :step="1" step-strictly :min="1"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="适用门店(不选默认全适用)" prop="storeId">
              <el-select v-model="form.storeId" filterable>
                <el-option v-for="option in storeOptions" :key="option.id" :label="option.name" :value="option.id" />
              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span=12>
            <el-form-item label="原价" prop="originalPrice">
              <el-input v-model="form.originalPrice" placeholder="请输入原价" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="卡类型" prop="cardType">
              <el-select v-model="form.cardType">
                <el-option v-for="option in dict.type.coupon_type" :label="option.label" :key="option.value"
                  :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="当前价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入当前价格" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="可使用次数" prop="numCanUse">
              <el-input-number v-model="form.numCanUse" :step="1" step-strictly :min="1" :disabled='form.cardType==2'>
              </el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <!--   <el-form-item label="单日只扣一次次数" prop="oneTimeOneDay">
              <el-input v-model="form.oneTimeOneDay" placeholder="请输入单日只扣一次次数" :disabled='form.cardType==2' />
            </el-form-item> -->
            <el-form-item label="抵扣时长(小时)" prop="maxMinuteOnce">
              <el-input-number v-model="form.maxMinuteOnce" :step="1" step-strictly :min="1"
                :disabled='form.cardType==1' />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- <el-col :span=12>

          </el-col> -->
          <el-col :span=24>
            <el-form-item label="周内可用日期" prop="weekDays">
              <el-checkbox-group v-model="weekDays">
                <el-checkbox-button v-for="(week, index) in weekOptions" :label="week.value" :key="index"
                  :value="week.value">{{week.label}}
                </el-checkbox-button>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span=12>
            <el-form-item label="有效期类型" prop="validType">
              <el-select v-model="form.validType">
                  <el-option v-for="option in dict.type.valid_type" :label="option.label" :key="option.value"
                    :value="option.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="自购买日起天数" prop="validDays" >
              <el-input-number v-model="form.validDays" :step="1" step-strictly :min="1" :disabled='form.validType!=1' />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span=12>
            <el-form-item label="开始日期" prop="startDate">
              <el-date-picker clearable v-model="form.startDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择开始日期" :disabled='form.validType!=2'>
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span=12>
            <el-form-item label="结束日期" prop="endDate">
              <el-date-picker clearable v-model="form.endDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择结束日期" :disabled='form.validType!=2'>
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item> -->
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
    listStorecoupon,
    getStorecoupon,
    delStorecoupon,
    addStorecoupon,
    updateStorecoupon
  } from "@/api/office/storecoupon";

  import {
    listStore
  } from "@/api/office/store";

  export default {
    name: "Storecoupon",
    dicts: ["coupon_type", "valid_type"],
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
        // 卡券表格数据
        storecouponList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          couponName: null,
          storeId: null,
          originalPrice: null,
          price: null,
          cardType: null,
          numCanUse: null,
          oneTimeOneDay: null,
          maxMinuteOnce: null,
          payType: null,
          maxHold: null,
          startDate: null,
          endDate: null,
          weekDays: null,
          status: null,
        },
        // 表单参数
        form: {},
        weekDays: [],
        // 表单校验
        rules: {
          couponName: [{
            required: true,
            message: "名称不能为空",
            trigger: "blur"
          }],
          // storeId: [{
          //   required: true,
          //   message: "适用门店不能为空",
          //   trigger: "blur"
          // }],
          price: [{
            required: true,
            message: "当前价格不能为空",
            trigger: "blur"
          }],
          cardType: [{
            required: true,
            message: "卡券类型(时长卡、次数卡)不能为空",
            trigger: "change"
          }],
          maxHold: [{
            required: true,
            message: "可购买次数不能为空",
            trigger: "blur"
          }],
          // startDate: [{
          //   required: true,
          //   message: "开始日期不可为空",
          //   trigger: "blur"
          // }],
          // endDate: [{
          //   required: true,
          //   message: "结束日期不能为空",
          //   trigger: "blur"
          // }]
        },
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
      /** 查询卡券列表 */
      getList() {
        this.loading = true;
        listStorecoupon(this.queryParams).then(response => {
          this.storecouponList = response.rows;
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
          couponName: null,
          storeId: null,
          originalPrice: null,
          price: null,
          cardType: null,
          numCanUse: null,
          oneTimeOneDay: null,
          maxMinuteOnce: null,
          payType: null,
          maxHold: null,
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
        this.title = "添加卡券";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getStorecoupon(id).then(response => {
          this.form = response.data;
          // this.form.weekDays = this.weekDays.join(',');
          // debugger
          this.weekDays = response.data.weekDays.split(',')
          this.open = true;
          this.title = "修改卡券";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.form.weekDays = this.weekDays.join(',');
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateStorecoupon(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addStorecoupon(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除卡券编号为"' + ids + '"的数据项？').then(function() {
          return delStorecoupon(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/storecoupon/export', {
          ...this.queryParams
        }, `storecoupon_${new Date().getTime()}.xlsx`)
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
      cardTypeFormatter(row, index) {
        // debugger
        return this.selectDictLabel(this.dict.type.coupon_type, row.cardType);
      },
      validTypeFormatter(row, index) {
        return this.selectDictLabel(this.dict.type.valid_type, row.validType);
      },

    }
  };
</script>
