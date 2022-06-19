<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['invest:getzichanxifen:add']"
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
          v-hasPermi="['invest:getzichanxifen:edit']"
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
          v-hasPermi="['invest:getzichanxifen:remove']"
        >删除</el-button>
      </el-col>
      
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['invest:getzichanxifen:export']"
        >导出</el-button>
      </el-col>
      
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="getzichanxifenList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="货币" align="center" prop="huobi" />
      <el-table-column label="债券" align="center" prop="zhaiquan" />
      <el-table-column label="已投入" align="center" prop="yitouru" />
      <el-table-column label="国内资产" align="center" prop="guoneiZichan" />
      <el-table-column label="轮动资产" align="center" prop="lundongZichan" />
      <el-table-column label="海外资产" align="center" prop="haiwaiZichan" />
      <el-table-column label="总计" align="center" prop="zongji" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['invest:getzichanxifen:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['invest:getzichanxifen:remove']"
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="货币" prop="huobi">
          <el-input v-model="form.huobi" placeholder="请输入货币" />
        </el-form-item>
        <el-form-item label="债券" prop="zhaiquan">
          <el-input v-model="form.zhaiquan" placeholder="请输入债券" />
        </el-form-item>
        <el-form-item label="已投入" prop="yitouru">
          <el-input v-model="form.yitouru" placeholder="请输入已投入" />
        </el-form-item>
        <el-form-item label="国内资产" prop="guoneiZichan">
          <el-input v-model="form.guoneiZichan" placeholder="请输入国内资产" />
        </el-form-item>
        <el-form-item label="轮动资产" prop="lundongZichan">
          <el-input v-model="form.lundongZichan" placeholder="请输入轮动资产" />
        </el-form-item>
        <el-form-item label="海外资产" prop="haiwaiZichan">
          <el-input v-model="form.haiwaiZichan" placeholder="请输入海外资产" />
        </el-form-item>
        <el-form-item label="总计" prop="zongji">
          <el-input v-model="form.zongji" placeholder="请输入总计" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button typetype="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGetzichanxifen, getGetzichanxifen, delGetzichanxifen, addGetzichanxifen, updateGetzichanxifen } from "@/api/invest/getzichanxifen";

export default {
  name: "Getzichanxifen",
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
      // 资产细分表格数据
      getzichanxifenList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        huobi: null,
        zhaiquan: null,
        yitouru: null,
        guoneiZichan: null,
        lundongZichan: null,
        haiwaiZichan: null,
        zongji: null
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
    /** 查询资产细分列表 */
    getList() {
      this.loading = true;
      listGetzichanxifen(this.queryParams).then(response => {
        console.log(this.getzichanxifenList,'response')
        this.getzichanxifenList = response.rows;
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
        name: null,
        huobi: null,
        zhaiquan: null,
        yitouru: null,
        guoneiZichan: null,
        lundongZichan: null,
        haiwaiZichan: null,
        zongji: null
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
      this.ids = selection.map(item => item.name)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加资产细分";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const name = row.name || this.ids
      getGetzichanxifen(name).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改资产细分";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.name != null) {
            updateGetzichanxifen(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGetzichanxifen(this.form).then(response => {
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
      const names = row.name || this.ids;
      this.$modal.confirm('是否确认删除资产细分编号为"' + names + '"的数据项？').then(function() {
        return delGetzichanxifen(names);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('invest/getzichanxifen/export', {
        ...this.queryParams
      }, `getzichanxifen_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
