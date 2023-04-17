<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入用户昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="头像地址" prop="avatar">
        <el-input
          v-model="queryParams.avatar"
          placeholder="请输入头像地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信openID" prop="openId">
        <el-input
          v-model="queryParams.openId"
          placeholder="请输入微信openID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="企业名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入企业名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="统一社会信用代码" prop="tyshxydm">
        <el-input
          v-model="queryParams.tyshxydm"
          placeholder="请输入统一社会信用代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人姓名" prop="legalPersonName">
        <el-input
          v-model="queryParams.legalPersonName"
          placeholder="请输入法人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人手机号码" prop="legalPersonPhone">
        <el-input
          v-model="queryParams.legalPersonPhone"
          placeholder="请输入法人手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经办人姓名" prop="jbrName">
        <el-input
          v-model="queryParams.jbrName"
          placeholder="请输入经办人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="法人身份证号码" prop="legalPersonIdcard">
        <el-input
          v-model="queryParams.legalPersonIdcard"
          placeholder="请输入法人身份证号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经办人身份证号码" prop="jbrIdcard">
        <el-input
          v-model="queryParams.jbrIdcard"
          placeholder="请输入经办人身份证号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经办人手机号码" prop="jbrPhone">
        <el-input
          v-model="queryParams.jbrPhone"
          placeholder="请输入经办人手机号码"
          clearable
          size="small"
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
          v-hasPermi="['system:wechatUser:add']"
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
          v-hasPermi="['system:wechatUser:edit']"
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
          v-hasPermi="['system:wechatUser:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:wechatUser:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wechatUserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="手机号码" align="center" prop="phone" />
      <el-table-column label="头像地址" align="center" prop="avatar" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="微信openID" align="center" prop="openId" />
      <el-table-column label="企业名称" align="center" prop="companyName" />
      <el-table-column label="统一社会信用代码" align="center" prop="tyshxydm" />
      <el-table-column label="法人姓名" align="center" prop="legalPersonName" />
      <el-table-column label="法人手机号码" align="center" prop="legalPersonPhone" />
      <el-table-column label="经办人姓名" align="center" prop="jbrName" />
      <el-table-column label="法人身份证号码" align="center" prop="legalPersonIdcard" />
      <el-table-column label="经办人身份证号码" align="center" prop="jbrIdcard" />
      <el-table-column label="经办人手机号码" align="center" prop="jbrPhone" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:wechatUser:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:wechatUser:remove']"
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

    <!-- 添加或修改微信用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="头像地址" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入头像地址" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="微信openID" prop="openId">
          <el-input v-model="form.openId" placeholder="请输入微信openID" />
        </el-form-item>
        <el-form-item label="企业名称" prop="companyName">
          <el-input v-model="form.companyName" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="tyshxydm">
          <el-input v-model="form.tyshxydm" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="法人姓名" prop="legalPersonName">
          <el-input v-model="form.legalPersonName" placeholder="请输入法人姓名" />
        </el-form-item>
        <el-form-item label="法人手机号码" prop="legalPersonPhone">
          <el-input v-model="form.legalPersonPhone" placeholder="请输入法人手机号码" />
        </el-form-item>
        <el-form-item label="经办人姓名" prop="jbrName">
          <el-input v-model="form.jbrName" placeholder="请输入经办人姓名" />
        </el-form-item>
        <el-form-item label="法人身份证号码" prop="legalPersonIdcard">
          <el-input v-model="form.legalPersonIdcard" placeholder="请输入法人身份证号码" />
        </el-form-item>
        <el-form-item label="经办人身份证号码" prop="jbrIdcard">
          <el-input v-model="form.jbrIdcard" placeholder="请输入经办人身份证号码" />
        </el-form-item>
        <el-form-item label="经办人手机号码" prop="jbrPhone">
          <el-input v-model="form.jbrPhone" placeholder="请输入经办人手机号码" />
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
import { listWechatUser, getWechatUser, delWechatUser, addWechatUser, updateWechatUser, exportWechatUser } from "@/api/system/wechatUser";

export default {
  name: "WechatUser",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 微信用户信息表格数据
      wechatUserList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nickName: null,
        phone: null,
        avatar: null,
        openId: null,
        companyName: null,
        tyshxydm: null,
        legalPersonName: null,
        legalPersonPhone: null,
        jbrName: null,
        legalPersonIdcard: null,
        jbrIdcard: null,
        jbrPhone: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        openId: [
          { required: true, message: "微信openID不能为空", trigger: "blur" }
        ],
        companyName: [
          { required: true, message: "企业名称不能为空", trigger: "blur" }
        ],
        tyshxydm: [
          { required: true, message: "统一社会信用代码不能为空", trigger: "blur" }
        ],
        legalPersonName: [
          { required: true, message: "法人姓名不能为空", trigger: "blur" }
        ],
        legalPersonPhone: [
          { required: true, message: "法人手机号码不能为空", trigger: "blur" }
        ],
        jbrName: [
          { required: true, message: "经办人姓名不能为空", trigger: "blur" }
        ],
        legalPersonIdcard: [
          { required: true, message: "法人身份证号码不能为空", trigger: "blur" }
        ],
        jbrIdcard: [
          { required: true, message: "经办人身份证号码不能为空", trigger: "blur" }
        ],
        jbrPhone: [
          { required: true, message: "经办人手机号码不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询微信用户信息列表 */
    getList() {
      this.loading = true;
      listWechatUser(this.queryParams).then(response => {
        this.wechatUserList = response.rows;
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
        userId: null,
        nickName: null,
        phone: null,
        avatar: null,
        remark: null,
        openId: null,
        companyName: null,
        tyshxydm: null,
        legalPersonName: null,
        legalPersonPhone: null,
        jbrName: null,
        legalPersonIdcard: null,
        jbrIdcard: null,
        jbrPhone: null
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加微信用户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getWechatUser(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改微信用户信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateWechatUser(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWechatUser(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$confirm('是否确认删除微信用户信息编号为"' + userIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWechatUser(userIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有微信用户信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportWechatUser(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
