<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="卡号" prop="cardno">
        <el-input
          v-model="queryParams.cardno"
          placeholder="请输入卡号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="土地年限" prop="landYears">
        <el-input
          v-model="queryParams.landYears"
          placeholder="请输入土地年限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="拱棚年限" prop="archedYears">
        <el-input
          v-model="queryParams.archedYears"
          placeholder="请输入拱棚年限"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="套餐代码" prop="setMeal">
        <el-input
          v-model="queryParams.setMeal"
          placeholder="请输入套餐代码"
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
          v-hasPermi="['csa:card:add']"
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
          v-hasPermi="['csa:card:edit']"
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
          v-hasPermi="['csa:card:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['csa:card:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="卡id" align="center" prop="id" />
      <el-table-column label="卡号" align="center" prop="cardno" />
      <el-table-column label="激活有效期" align="center" prop="activationValidity" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.activationValidity, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="可用状态" align="center" prop="usabilityStatus" />
      <el-table-column label="激活状态" align="center" prop="activeStatus" />
      <el-table-column label="平方米" align="center" prop="m2" />
      <el-table-column label="土地年限" align="center" prop="landYears" />
      <el-table-column label="拱棚年限" align="center" prop="archedYears" />
      <el-table-column label="金币" align="center" prop="coins" />
      <el-table-column label="金额" align="center" prop="balance" />
      <el-table-column label="重量" align="center" prop="weight" />
      <el-table-column label="套餐代码" align="center" prop="setMeal" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:card:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:card:remove']"
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

    <!-- 添加或修改会员卡对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="卡号" prop="cardno">
          <el-input v-model="form.cardno" placeholder="请输入卡号" />
        </el-form-item>
        <el-form-item label="密码" prop="passwd">
          <el-input v-model="form.passwd" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="激活有效期" prop="activationValidity">
          <el-date-picker clearable
            v-model="form.activationValidity"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择激活有效期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="平方米" prop="m2">
          <el-input v-model="form.m2" placeholder="请输入平方米" />
        </el-form-item>
        <el-form-item label="土地年限" prop="landYears">
          <el-input v-model="form.landYears" placeholder="请输入土地年限" />
        </el-form-item>
        <el-form-item label="拱棚年限" prop="archedYears">
          <el-input v-model="form.archedYears" placeholder="请输入拱棚年限" />
        </el-form-item>
        <el-form-item label="金币" prop="coins">
          <el-input v-model="form.coins" placeholder="请输入金币" />
        </el-form-item>
        <el-form-item label="金额" prop="balance">
          <el-input v-model="form.balance" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量" />
        </el-form-item>
        <el-form-item label="套餐代码" prop="setMeal">
          <el-input v-model="form.setMeal" placeholder="请输入套餐代码" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listCard, getCard, delCard, addCard, updateCard } from "@/api/csa/card";

export default {
  name: "Card",
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
      // 会员卡表格数据
      cardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cardno: null,
        usabilityStatus: null,
        activeStatus: null,
        landYears: null,
        archedYears: null,
        setMeal: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cardno: [
          { required: true, message: "卡号不能为空", trigger: "blur" }
        ],
        passwd: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],
        m2: [
          { required: true, message: "平方米不能为空", trigger: "blur" }
        ],
        landYears: [
          { required: true, message: "土地年限不能为空", trigger: "blur" }
        ],
        archedYears: [
          { required: true, message: "拱棚年限不能为空", trigger: "blur" }
        ],
        coins: [
          { required: true, message: "金币不能为空", trigger: "blur" }
        ],
        balance: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
        weight: [
          { required: true, message: "重量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询会员卡列表 */
    getList() {
      this.loading = true;
      listCard(this.queryParams).then(response => {
        this.cardList = response.rows;
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
        cardno: null,
        passwd: null,
        activationValidity: null,
        usabilityStatus: "0",
        activeStatus: "0",
        m2: null,
        landYears: null,
        archedYears: null,
        coins: null,
        balance: null,
        weight: null,
        setMeal: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加会员卡";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCard(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会员卡";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCard(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCard(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除会员卡编号为"' + ids + '"的数据项？').then(function() {
        return delCard(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('csa/card/export', {
        ...this.queryParams
      }, `card_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
