<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="queryParams.nickName" placeholder="请输入昵称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="微信id" prop="appId">
        <el-input v-model="queryParams.appId" placeholder="请输入微信app id" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!--  <el-form-item label="uinion id" prop="unionId">
        <el-input
          v-model="queryParams.unionId"
          placeholder="请输入uinion id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入手机号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="邀请人手机号" prop="inviterPhone">
        <el-input v-model="queryParams.inviterPhone" placeholder="请输入邀请人手机号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="分享码" prop="shareCode">
        <el-input
          v-model="queryParams.shareCode"
          placeholder="请输入分享码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信code" prop="wxCode">
        <el-input
          v-model="queryParams.wxCode"
          placeholder="请输入微信code"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="是否会员" prop="isMember">
        <el-input
          v-model="queryParams.isMember"
          placeholder="请输入是否会员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['office:wxuser:add']">新增</el-button>
      </el-col>
      <!--   <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['office:wxuser:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['office:wxuser:remove']">删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['office:wxuser:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxuserList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="昵称" align="center" prop="nickName" />
      <el-table-column label="微信id" align="center" prop="appId" />
      <!-- <el-table-column label="uinion id" align="center" prop="unionId" /> -->
      <el-table-column label="手机号" align="center" prop="phone" />
      <el-table-column label="分享码" align="center" prop="shareCode" />
      <!-- <el-table-column label="微信code" align="center" prop="wxCode" /> -->
      <el-table-column label="邀请人手机号" align="center" prop="inviterPhone" />
      <el-table-column label="是否会员" align="center" prop="isMember" />
      <el-table-column label="余额" align="center" prop="amount" />
      <el-table-column label="积分" align="center" prop="point" />
      <!-- <el-table-column label="性别" align="center" prop="sex" />
      <el-table-column label="登录ip" align="center" prop="loginIp" />
      <el-table-column label="登录时间" align="center" prop="loginDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['office:wxuser:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['office:wxuser:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改微信用户信息 t_wx_user对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="微信app id" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入微信app id" />
        </el-form-item>
        <el-form-item label="uinion id" prop="unionId">
          <el-input v-model="form.unionId" placeholder="请输入uinion id" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="分享码" prop="shareCode">
          <el-input v-model="form.shareCode" placeholder="请输入分享码" />
        </el-form-item>
        <el-form-item label="微信code" prop="wxCode">
          <el-input v-model="form.wxCode" placeholder="请输入微信code" />
        </el-form-item>
        <el-form-item label="邀请人手机号" prop="inviterPhone">
          <el-input v-model="form.inviterPhone" placeholder="请输入邀请人手机号" />
        </el-form-item>
        <el-form-item label="是否会员" prop="isMember">
          <el-input v-model="form.isMember" placeholder="请输入是否会员" />
        </el-form-item>
        <el-form-item label="登录ip" prop="loginIp">
          <el-input v-model="form.loginIp" placeholder="请输入登录ip" />
        </el-form-item>
        <el-form-item label="登录时间" prop="loginDate">
          <el-date-picker clearable v-model="form.loginDate" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择登录时间">
          </el-date-picker>
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
    listWxuser,
    getWxuser,
    delWxuser,
    addWxuser,
    updateWxuser,
    listStoreWxuser
  } from "@/api/office/wxuser";

  export default {
    name: "Wxuser",
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
        // 微信用户信息 t_wx_user表格数据
        wxuserList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          nickName: null,
          appId: null,
          unionId: null,
          phone: null,
          shareCode: null,
          wxCode: null,
          inviterPhone: null,
          isMember: null,
          sex: null,
          loginIp: null,
          loginDate: null,
          status: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询微信用户信息 t_wx_user列表 */
      getList() {
        this.loading = true;
        // listWxuser(this.queryParams).then(response => {
        //   this.wxuserList = response.rows;
        //   this.total = response.total;
        //   this.loading = false;
        // });
        listStoreWxuser(this.queryParams).then(response => {
          this.wxuserList = response.rows;
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
          nickName: null,
          appId: null,
          unionId: null,
          phone: null,
          shareCode: null,
          wxCode: null,
          inviterPhone: null,
          isMember: null,
          sex: null,
          loginIp: null,
          loginDate: null,
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
        this.title = "添加微信用户信息 t_wx_user";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getWxuser(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改微信用户信息 t_wx_user";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateWxuser(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addWxuser(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除微信用户信息 t_wx_user编号为"' + ids + '"的数据项？').then(function() {
          return delWxuser(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('office/wxuser/export', {
          ...this.queryParams
        }, `wxuser_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
