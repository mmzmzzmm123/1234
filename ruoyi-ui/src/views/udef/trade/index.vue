<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="买卖方向" prop="side">
        <el-select v-model="queryParams.side" placeholder="请选择买卖方向" clearable>
          <el-option
            v-for="dict in dict.type.udef_dict_side"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="持仓方向" prop="positionSide">
        <el-select v-model="queryParams.positionSide" placeholder="请选择持仓方向" clearable>
          <el-option
            v-for="dict in dict.type.udef_open_side"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="交易对" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          placeholder="请输入交易对"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="机器人" prop="robotId">
        <el-select v-model="queryParams.robotId" placeholder="请选择机器人" clearable>
          <el-option
            v-for="dict in dict.type.udef_dict_robot_key"
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
          v-hasPermi="['udef:trade:add']"
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
          v-hasPermi="['udef:trade:edit']"
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
          v-hasPermi="['udef:trade:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['udef:trade:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tradeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="orderId" align="center" prop="orderId" />
      <el-table-column label="成交价" align="center" prop="price" />
      <el-table-column label="成交量" align="center" prop="qty" />
      <el-table-column label="买卖方向" align="center" prop="side">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_dict_side" :value="scope.row.side"/>
        </template>
      </el-table-column>
      <el-table-column label="持仓方向" align="center" prop="positionSide">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_open_side" :value="scope.row.positionSide"/>
        </template>
      </el-table-column>
      <el-table-column label="交易对" align="center" prop="symbol" />
      <el-table-column label="时间" align="center" prop="time" />
      <el-table-column label="机器人" align="center" prop="robotId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_dict_robot_key" :value="scope.row.robotId"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['udef:trade:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['udef:trade:remove']"
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

    <!-- 添加或修改成交订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="orderId" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入orderId" />
        </el-form-item>
        <el-form-item label="成交价" prop="price">
          <el-input v-model="form.price" placeholder="请输入成交价" />
        </el-form-item>
        <el-form-item label="成交量" prop="qty">
          <el-input v-model="form.qty" placeholder="请输入成交量" />
        </el-form-item>
        <el-form-item label="成交额" prop="quoteQty">
          <el-input v-model="form.quoteQty" placeholder="请输入成交额" />
        </el-form-item>
        <el-form-item label="实现盈亏" prop="realizedPnl">
          <el-input v-model="form.realizedPnl" placeholder="请输入实现盈亏" />
        </el-form-item>
        <el-form-item label="买卖方向" prop="side">
          <el-select v-model="form.side" placeholder="请选择买卖方向">
            <el-option
              v-for="dict in dict.type.udef_dict_side"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="持仓方向" prop="positionSide">
          <el-select v-model="form.positionSide" placeholder="请选择持仓方向">
            <el-option
              v-for="dict in dict.type.udef_open_side"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入交易对" />
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-input v-model="form.time" placeholder="请输入时间" />
        </el-form-item>
        <el-form-item label="机器人" prop="robotId">
          <el-select v-model="form.robotId" placeholder="请选择机器人">
            <el-option
              v-for="dict in dict.type.udef_dict_robot_key"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
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
import { listTrade, getTrade, delTrade, addTrade, updateTrade } from "@/api/udef/trade";

export default {
  name: "Trade",
  dicts: ['udef_dict_robot_key', 'udef_dict_side', 'udef_open_side'],
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
      // 成交订单表格数据
      tradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        price: null,
        qty: null,
        quoteQty: null,
        realizedPnl: null,
        side: null,
        positionSide: null,
        symbol: null,
        time: null,
        robotId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderId: [
          { required: true, message: "orderId不能为空", trigger: "blur" }
        ],
        price: [
          { required: true, message: "成交价不能为空", trigger: "blur" }
        ],
        qty: [
          { required: true, message: "成交量不能为空", trigger: "blur" }
        ],
        quoteQty: [
          { required: true, message: "成交额不能为空", trigger: "blur" }
        ],
        realizedPnl: [
          { required: true, message: "实现盈亏不能为空", trigger: "blur" }
        ],
        side: [
          { required: true, message: "买卖方向不能为空", trigger: "change" }
        ],
        positionSide: [
          { required: true, message: "持仓方向不能为空", trigger: "change" }
        ],
        symbol: [
          { required: true, message: "交易对不能为空", trigger: "blur" }
        ],
        time: [
          { required: true, message: "时间不能为空", trigger: "blur" }
        ],
        robotId: [
          { required: true, message: "机器人不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询成交订单列表 */
    getList() {
      this.loading = true;
      listTrade(this.queryParams).then(response => {
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
        orderId: null,
        price: null,
        qty: null,
        quoteQty: null,
        realizedPnl: null,
        side: null,
        positionSide: null,
        symbol: null,
        time: null,
        robotId: null
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
      this.title = "添加成交订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTrade(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改成交订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTrade(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTrade(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除成交订单编号为"' + ids + '"的数据项？').then(function() {
        return delTrade(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('udef/trade/export', {
        ...this.queryParams
      }, `trade_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
