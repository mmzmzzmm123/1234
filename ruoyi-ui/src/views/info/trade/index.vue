<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交易编号" prop="tradeNo">
        <el-input
          v-model="queryParams.tradeNo"
          placeholder="请输入交易编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人" prop="operatorName">
        <el-input
          v-model="queryParams.operatorName"
          placeholder="请输入操作人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群编号" prop="groupNo">
        <el-input
          v-model="queryParams.groupNo"
          placeholder="请输入群编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="卡面值" prop="cardValue">
        <el-input
          v-model="queryParams.cardValue"
          placeholder="请输入卡面值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收卡汇率" prop="buyCardExchangeRate">
        <el-input
          v-model="queryParams.buyCardExchangeRate"
          placeholder="请输入收卡汇率"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收卡成本" prop="buyCost">
        <el-input
          v-model="queryParams.buyCost"
          placeholder="请输入收卡成本"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="奈拉值" prop="nailaValue">
        <el-input
          v-model="queryParams.nailaValue"
          placeholder="请输入奈拉值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入代码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="对接群" prop="commGroup">
        <el-input
          v-model="queryParams.commGroup"
          placeholder="请输入对接群"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="售出价格" prop="salePrice">
        <el-input
          v-model="queryParams.salePrice"
          placeholder="请输入售出价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="利润" prop="profit">
        <el-input
          v-model="queryParams.profit"
          placeholder="请输入利润"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行账号" prop="bankAccount">
        <el-input
          v-model="queryParams.bankAccount"
          placeholder="请输入银行账号"
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
          v-hasPermi="['info:trade:add']"
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
          v-hasPermi="['info:trade:edit']"
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
          v-hasPermi="['info:trade:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['info:trade:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="交易编号" align="center" prop="tradeNo" />
      <el-table-column label="操作人" align="center" prop="operatorName" />
      <el-table-column label="群编号" align="center" prop="groupNo" />
      <el-table-column label="卡类型" align="center" prop="cardType" />
      <el-table-column label="币种" align="center" prop="currencyType" />
      <el-table-column label="卡面值" align="center" prop="cardValue" />
      <el-table-column label="收卡汇率" align="center" prop="buyCardExchangeRate" />
      <el-table-column label="收卡成本" align="center" prop="buyCost" />
      <el-table-column label="奈拉值" align="center" prop="nailaValue" />
      <el-table-column label="代码" align="center" prop="code" />
      <el-table-column label="对接群" align="center" prop="commGroup" />
      <el-table-column label="售出价格" align="center" prop="salePrice" />
      <el-table-column label="利润" align="center" prop="profit" />
      <el-table-column label="银行账号" align="center" prop="bankAccount" />
      <el-table-column label="支付状态" align="center" prop="payStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['info:trade:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['info:trade:remove']"
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

    <!-- 添加或修改交易信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="交易编号" prop="tradeNo">
          <el-input v-model="form.tradeNo" placeholder="请输入交易编号" readonly/>
        </el-form-item>
        <el-form-item label="操作人" prop="operatorName">
          <el-input v-model="form.operatorName" placeholder="请输入操作人" readonly/>
        </el-form-item>
        <el-form-item label="群编号" prop="groupNo">
          <el-input v-model="form.groupNo" placeholder="请输入群编号" />
        </el-form-item>
        <el-form-item label="卡类型" prop="cardType">
          <el-radio-group v-model="form.cardType" size="medium">
            <el-radio v-for="(item, index) in cardTypeOptions" :key="index" :label="item.value"
              :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="币种" prop="currencyType">
          <el-radio-group v-model="form.currencyType" size="medium">
            <el-radio v-for="(item, index) in currencyTypeOptions" :key="index" :label="item.value"
              :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="卡面值" prop="cardValue">
          <el-input-number v-model="form.cardValue" placeholder="请输入卡面值" />
        </el-form-item>
        <el-form-item label="收卡汇率" prop="buyCardExchangeRate">
          <el-input-number v-model="form.buyCardExchangeRate" placeholder="请输入收卡汇率" />
        </el-form-item>
        <el-form-item label="收卡成本" prop="buyCost">
          <el-input-number v-model="form.buyCost" placeholder="请输入收卡成本" />
        </el-form-item>
        <el-form-item label="奈拉值" prop="nailaValue">
          <el-input-number v-model="form.nailaValue" placeholder="请输入奈拉值" />
        </el-form-item>
        <el-form-item label="代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入代码" />
        </el-form-item>
        <el-form-item label="对接群" prop="commGroup">
          <el-input v-model="form.commGroup" placeholder="请输入对接群" />
        </el-form-item>
        <el-form-item label="售出价格" prop="salePrice">
          <el-input-number v-model="form.salePrice" placeholder="请输入售出价格" />
        </el-form-item>
        <el-form-item label="利润" prop="profit">
          <el-input-number v-model="form.profit" placeholder="请输入利润" />
        </el-form-item>
        <el-form-item label="银行账号" prop="bankAccount">
          <el-input v-model="form.bankAccount" placeholder="请输入银行账号" />
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
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/info/trade";

export default {
  name: "Info",
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
      // 交易信息表格数据
      tradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tradeNo: null,
        operatorName: null,
        groupNo: null,
        cardType: null,
        currencyType: null,
        cardValue: null,
        buyCardExchangeRate: null,
        buyCost: null,
        nailaValue: null,
        code: null,
        commGroup: null,
        salePrice: null,
        profit: null,
        bankAccount: null,
        payStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        //tradeNo: [
          //{ required: true, message: "交易编号不能为空", trigger: "blur" }
        //],
      },
      cardTypeOptions: [
        {
          "label": "steam",
          "value": "steam"
        }
      ],
      currencyTypeOptions: [
        {
          "label": "USD",
          "value": "USD"
        }
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询交易信息列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.tradeList = response.rows;
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
        tradeNo: null,
        operatorName: null,
        groupNo: null,
        cardType: null,
        currencyType: null,
        cardValue: null,
        buyCardExchangeRate: null,
        buyCost: null,
        nailaValue: null,
        code: null,
        commGroup: null,
        salePrice: null,
        profit: null,
        bankAccount: null,
        payStatus: "0",
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
      this.title = "添加交易信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改交易信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除交易信息编号为"' + ids + '"的数据项？').then(function() {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('info/trade/export', {
        ...this.queryParams
      }, `trade_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
