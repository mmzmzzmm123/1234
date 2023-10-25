<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入商户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="appid" prop="appid">
        <el-input
          v-model="queryParams.appid"
          placeholder="请输入appid"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商户号" prop="mchId">
        <el-input
          v-model="queryParams.mchId"
          placeholder="请输入商户号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="密钥" prop="apiV3Key">
        <el-input
          v-model="queryParams.apiV3Key"
          placeholder="请输入密钥"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="序列号" prop="certSerialNo">
        <el-input
          v-model="queryParams.certSerialNo"
          placeholder="请输入序列号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="私钥路径" prop="privateKeyPath">
        <el-input
          v-model="queryParams.privateKeyPath"
          placeholder="请输入私钥路径"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="私钥证书" prop="privateCertPath">
        <el-input
          v-model="queryParams.privateCertPath"
          placeholder="请输入私钥证书"
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
          v-hasPermi="['office:wxpay:add']"
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
          v-hasPermi="['office:wxpay:edit']"
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
          v-hasPermi="['office:wxpay:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['office:wxpay:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxpayList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="商户id" align="center" prop="userId" />
      <el-table-column label="appid" align="center" prop="appid" />
      <el-table-column label="商户号" align="center" prop="mchId" />
      <el-table-column label="密钥" align="center" prop="apiV3Key" />
      <el-table-column label="序列号" align="center" prop="certSerialNo" />
      <el-table-column label="私钥路径" align="center" prop="privateKeyPath" />
      <el-table-column label="私钥证书" align="center" prop="privateCertPath" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['office:wxpay:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['office:wxpay:remove']"
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

    <!-- 添加或修改微信支付配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入商户id" />
        </el-form-item>
        <el-form-item label="appid" prop="appid">
          <el-input v-model="form.appid" placeholder="请输入appid" />
        </el-form-item>
        <el-form-item label="商户号" prop="mchId">
          <el-input v-model="form.mchId" placeholder="请输入商户号" />
        </el-form-item>
        <el-form-item label="密钥" prop="apiV3Key">
          <el-input v-model="form.apiV3Key" placeholder="请输入密钥" />
        </el-form-item>
        <el-form-item label="序列号" prop="certSerialNo">
          <el-input v-model="form.certSerialNo" placeholder="请输入序列号" />
        </el-form-item>
        <el-form-item label="私钥路径" prop="privateKeyPath">
          <el-input v-model="form.privateKeyPath" placeholder="请输入私钥路径" />
        </el-form-item>
        <el-form-item label="私钥证书" prop="privateCertPath">
          <el-input v-model="form.privateCertPath" placeholder="请输入私钥证书" />
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
import { listWxpay, getWxpay, delWxpay, addWxpay, updateWxpay } from "@/api/office/wxpay";

export default {
  name: "Wxpay",
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
      // 微信支付配置表格数据
      wxpayList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        appid: null,
        mchId: null,
        apiV3Key: null,
        certSerialNo: null,
        privateKeyPath: null,
        privateCertPath: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "商户id不能为空", trigger: "blur" }
        ],
        appid: [
          { required: true, message: "appid不能为空", trigger: "blur" }
        ],
        mchId: [
          { required: true, message: "商户号不能为空", trigger: "blur" }
        ],
        apiV3Key: [
          { required: true, message: "密钥不能为空", trigger: "blur" }
        ],
        certSerialNo: [
          { required: true, message: "序列号不能为空", trigger: "blur" }
        ],
        privateKeyPath: [
          { required: true, message: "私钥路径不能为空", trigger: "blur" }
        ],
        privateCertPath: [
          { required: true, message: "私钥证书不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信支付配置列表 */
    getList() {
      this.loading = true;
      listWxpay(this.queryParams).then(response => {
        this.wxpayList = response.rows;
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
        appid: null,
        mchId: null,
        apiV3Key: null,
        certSerialNo: null,
        privateKeyPath: null,
        privateCertPath: null,
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
      this.title = "添加微信支付配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWxpay(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信支付配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWxpay(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWxpay(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除微信支付配置编号为"' + ids + '"的数据项？').then(function() {
        return delWxpay(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('office/wxpay/export', {
        ...this.queryParams
      }, `wxpay_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
