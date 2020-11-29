<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="微信应用" prop="appid">
        <el-select v-model="queryParams.appid" placeholder="请选择微信应用" clearable size="small">
          <el-option
            v-for="dict in appidOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择用户性别" clearable size="small">
          <el-option
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['custom:wxUserInfo:add']"-->
<!--        >新增-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['custom:wxUserInfo:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['custom:wxUserInfo:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:wxUserInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wxUserInfoList" @selection-change="handleSelectionChange">
      <el-table-column label="用户头像" align="center" prop="avatarUrl">
        <template slot-scope="scope">
          <el-image :src="scope.row.avatarUrl" style="width: 32px; height: 32px; border-radius: 50%"/>
        </template>
      </el-table-column>
      <!--      <el-table-column type="selection" width="55" align="center"/>-->
      <!--      <el-table-column label="微信openid" align="center" prop="openid"/>-->
      <el-table-column label="昵称" align="center" prop="nickName"/>
      <el-table-column label="微信应用" align="center" prop="appid" :formatter="appidFormat"/>
      <el-table-column label="电话" align="center" prop="phone"/>
      <el-table-column label="用户性别" align="center" prop="sex" :formatter="sexFormat"/>
      <el-table-column label="城市" align="center" prop="city"/>
      <el-table-column label="省份" align="center" prop="province"/>
      <el-table-column label="国家" align="center" prop="country"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:wxUserInfo:edit']"
          >修改
          </el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['custom:wxUserInfo:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
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

    <!-- 添加或修改微信用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="昵称" prop="nickName">-->
<!--          <el-input v-model="form.nickName" placeholder="请输入昵称"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="微信appid" prop="appid">-->
<!--          <el-select v-model="form.appid" placeholder="请选择微信appid">-->
<!--            <el-option-->
<!--              v-for="dict in appidOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话"/>
        </el-form-item>
<!--        <el-form-item label="用户头像" prop="avatarUrl">-->
<!--          <el-input v-model="form.avatarUrl" placeholder="请输入用户头像"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户性别" prop="sex">-->
<!--          <el-select v-model="form.sex" placeholder="请选择用户性别">-->
<!--            <el-option-->
<!--              v-for="dict in sexOptions"-->
<!--              :key="dict.dictValue"-->
<!--              :label="dict.dictLabel"-->
<!--              :value="dict.dictValue"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="城市" prop="city">-->
<!--          <el-input v-model="form.city" placeholder="请输入城市"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="省份" prop="province">-->
<!--          <el-input v-model="form.province" placeholder="请输入省份"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="国家" prop="country">-->
<!--          <el-input v-model="form.country" placeholder="请输入国家"/>-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
    listWxUserInfo,
    getWxUserInfo,
    delWxUserInfo,
    addWxUserInfo,
    updateWxUserInfo,
    exportWxUserInfo
  } from "@/api/custom/wxUserInfo";

  export default {
    name: "WxUserInfo",
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
        showSearch: false,
        // 总条数
        total: 0,
        // 微信用户表格数据
        wxUserInfoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 微信appid字典
        appidOptions: [],
        // 用户性别字典
        sexOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          nickName: null,
          appid: null,
          phone: null,
          sex: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
      this.getDicts("cus_wx_app").then(response => {
        this.appidOptions = response.data;
      });
      this.getDicts("sys_user_sex").then(response => {
        this.sexOptions = response.data;
      });
    },
    methods: {
      /** 查询微信用户列表 */
      getList() {
        this.loading = true;
        listWxUserInfo(this.queryParams).then(response => {
          this.wxUserInfoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 微信appid字典翻译
      appidFormat(row, column) {
        return this.selectDictLabel(this.appidOptions, row.appid);
      },
      // 用户性别字典翻译
      sexFormat(row, column) {
        return this.selectDictLabel(this.sexOptions, row.sex);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          openid: null,
          nickName: null,
          appid: null,
          phone: null,
          avatarUrl: null,
          sex: null,
          city: null,
          province: null,
          country: null,
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
        this.ids = selection.map(item => item.openid)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加微信用户";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const openid = row.openid || this.ids
        getWxUserInfo(openid).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改微信用户";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.openid != null) {
              updateWxUserInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addWxUserInfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const openids = row.openid || this.ids;
        this.$confirm('是否确认删除微信用户编号为"' + openids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delWxUserInfo(openids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有微信用户数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportWxUserInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      }
    }
  };
</script>

<style lang="scss" scoped>
  .avatar {
    width: 64px;
    height: 64px;
    border-radius: 50%;
  }
</style>
