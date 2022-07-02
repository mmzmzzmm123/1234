<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="交易对" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          placeholder="请输入交易对"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开仓方向" prop="openSide">
        <el-select v-model="queryParams.openSide" placeholder="请选择开仓方向" clearable>
          <el-option
            v-for="dict in dict.type.udef_open_side"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.udef_position_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="退出方式" prop="stopType">
        <el-select v-model="queryParams.stopType" placeholder="请选择退出方式" clearable>
          <el-option
            v-for="dict in dict.type.udef_robot_stop_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['udef:position:add']"
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
          v-hasPermi="['udef:position:edit']"
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
          v-hasPermi="['udef:position:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['udef:position:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="positionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :row-class-name="closedOrRunning" :cell-class-name="upOrDown"/>
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="前置余额" align="center" prop="predictBalance" />
      <el-table-column label="当前余额" align="center" prop="balance" />
      <el-table-column label="最大持仓" align="center" prop="maxPosition" />
      <el-table-column label="当前持仓" align="center" prop="position" />
      <el-table-column label="交易对" align="center" prop="symbol" />
      <el-table-column label="开仓方向" align="center" prop="openSide">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_open_side" :value="scope.row.openSide"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_position_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="持仓均价" align="center" prop="avgPrice" />
      <el-table-column label="退出方式" align="center" prop="stopType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_robot_stop_type" :value="scope.row.stopType"/>
        </template>
      </el-table-column>
      <el-table-column label="机器人" align="center" prop="robotId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_dict_robot_key" :value="scope.row.robotId"/>
        </template>
      </el-table-column>
      <el-table-column label="币种" align="center" prop="symbol"/>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['udef:position:edit']"
          >修改</el-button>
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

    <!-- 添加或修改机器人订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="前置余额" prop="predictBalance">
          <el-input v-model="form.predictBalance" placeholder="请输入前置余额" />
        </el-form-item>
        <el-form-item label="当前余额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入当前余额" />
        </el-form-item>
        <el-form-item label="最大持仓" prop="maxPosition">
          <el-input v-model="form.maxPosition" placeholder="请输入最大持仓" />
        </el-form-item>
        <el-form-item label="当前持仓" prop="position">
          <el-input v-model="form.position" placeholder="请输入当前持仓" />
        </el-form-item>
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入交易对" />
        </el-form-item>
        <el-form-item label="开仓方向" prop="openSide">
          <el-select v-model="form.openSide" placeholder="请选择开仓方向">
            <el-option
              v-for="dict in dict.type.udef_open_side"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.udef_position_status"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="执行策略" prop="strategy">
          <el-input v-model="form.strategy" placeholder="请输入执行策略" />
        </el-form-item>
        <el-form-item label="持仓均价" prop="avgPrice">
          <el-input v-model="form.avgPrice" placeholder="请输入持仓均价" />
        </el-form-item>
        <el-form-item label="退出方式" prop="stopType">
          <el-select v-model="form.stopType" placeholder="请选择退出方式">
            <el-option
              v-for="dict in dict.type.udef_robot_stop_type"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
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
<style>
.el-table .closed-row {
  background: #e9e9e9;
}

.el-table .running_row {
  background: #fbfad9;
}

.el-table .cell_up {
  color: green;
}

.el-table .cell_down {
  color: red;
}
</style>
<script>
import { listPosition, getPosition, delPosition, addPosition, updatePosition } from "@/api/udef/position";

export default {
  name: "Position",
  dicts: ['udef_dict_robot_key', 'udef_open_side', 'udef_position_status', 'udef_robot_stop_type'],
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
      // 机器人订单表格数据
      positionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        predictBalance: null,
        balance: null,
        maxPosition: null,
        position: null,
        symbol: null,
        openSide: null,
        status: null,
        strategy: null,
        avgPrice: null,
        stopType: null,
        robotId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        predictBalance: [
          { required: true, message: "前置余额不能为空", trigger: "blur" }
        ],
        balance: [
          { required: true, message: "当前余额不能为空", trigger: "blur" }
        ],
        maxPosition: [
          { required: true, message: "最大持仓不能为空", trigger: "blur" }
        ],
        position: [
          { required: true, message: "当前持仓不能为空", trigger: "blur" }
        ],
        symbol: [
          { required: true, message: "交易对不能为空", trigger: "blur" }
        ],
        openSide: [
          { required: true, message: "开仓方向不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        strategy: [
          { required: true, message: "执行策略不能为空", trigger: "blur" }
        ],
        robotId: [
          { required: true, message: "机器人不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    closedOrRunning({ row, rowIndex }) {
      let status
      if (row.status === 'closed') {
        status = 'closed-row'
      } else if (row.status === 'running') {
        status = 'running_row'
      } else {
        status = ''
      }
      return status
    },

    upOrDown({ row, column, rowIndex, columnIndex }) {
      let profit
      if (columnIndex === 3) {
        if (row.balance > row.predictBalance) {
          profit = 'cell_up'
        } else if (row.balance < row.predictBalance) {
          profit = 'cell_down'
        } else {
          profit = ''
        }
      }
      return profit
    },
    /** 查询机器人订单列表 */
    getList() {
      this.loading = true;
      listPosition(this.queryParams).then(response => {
        this.positionList = response.rows;
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
        predictBalance: null,
        balance: null,
        maxPosition: null,
        position: null,
        symbol: null,
        openSide: null,
        status: null,
        strategy: null,
        avgPrice: null,
        stopType: null,
        robotId: null,
        createTime: null,
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
      this.title = "添加机器人订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPosition(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改机器人订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePosition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPosition(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除机器人订单编号为"' + ids + '"的数据项？').then(function() {
        return delPosition(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('udef/position/export', {
        ...this.queryParams
      }, `position_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
