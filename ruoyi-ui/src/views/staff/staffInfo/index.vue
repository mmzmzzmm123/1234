<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.staff_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否在线" prop="ifOnline">
        <el-select v-model="queryParams.ifOnline" placeholder="请选择是否在线" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="微信" prop="weChatNum">
        <el-input
          v-model="queryParams.weChatNum"
          placeholder="请输入微信"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否置顶" prop="ifTop">
        <el-select v-model="queryParams.ifTop" placeholder="请选择是否置顶" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="推荐码" prop="referralCode">
        <el-input
          v-model="queryParams.referralCode"
          placeholder="请输入推荐码"
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
          v-hasPermi="['staff:staffInfo:add']"
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
          v-hasPermi="['staff:staffInfo:edit']"
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
          v-hasPermi="['staff:staffInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['staff:staffInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="staffInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="头像" align="center" prop="avatarUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="昵称" align="center" prop="nickName" :show-overflow-tooltip="true"/>
      <el-table-column label="状态" align="center" prop="state" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.staff_state" :value="scope.row.state"/>
        </template>
      </el-table-column>
      <el-table-column label="是否在线" align="center" prop="ifOnline" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.ifOnline"/>
        </template>
      </el-table-column>
      <el-table-column label="等级" align="center" prop="staffLevel" :show-overflow-tooltip="true"/>
      <el-table-column label="出生年月" align="center" prop="birthDate" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="微信" align="center" prop="weChatNum" :show-overflow-tooltip="true"/>
      <el-table-column label="手机号码" align="center" prop="phone" :show-overflow-tooltip="true"/>
      <el-table-column label="省份" align="center" prop="province" :show-overflow-tooltip="true"/>
      <el-table-column label="城市" align="center" prop="city" :show-overflow-tooltip="true"/>
      <el-table-column label="区" align="center" prop="region" :show-overflow-tooltip="true"/>
      <el-table-column label="个人介绍" align="center" prop="selfIntroduction" :show-overflow-tooltip="true"/>
      <el-table-column label="个人标签" align="center" prop="selfTags" :show-overflow-tooltip="true"/>
      <el-table-column label="录音文件" align="center" prop="voiceUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="录音时长" align="center" prop="voiceTime" :show-overflow-tooltip="true"/>
      <el-table-column label="是否置顶" align="center" prop="ifTop" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.ifTop"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortNum" :show-overflow-tooltip="true"/>
      <el-table-column label="推荐码" align="center" prop="referralCode" :show-overflow-tooltip="true"/>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['staff:staffInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['staff:staffInfo:remove']"
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

    <!-- 添加或修改员工信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组长标识" prop="referralUserId">
          <el-input v-model="form.referralUserId" placeholder="请输入组长标识" />
        </el-form-item>
        <el-form-item label="头像" prop="avatarUrl">
          <el-input v-model="form.avatarUrl" placeholder="请输入头像" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.staff_state"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否在线" prop="ifOnline">
          <el-select v-model="form.ifOnline" placeholder="请选择是否在线">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="等级" prop="staffLevel">
          <el-input v-model="form.staffLevel" placeholder="请输入等级" />
        </el-form-item>
        <el-form-item label="出生年月" prop="birthDate">
          <el-date-picker clearable
            v-model="form.birthDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择出生年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="微信" prop="weChatNum">
          <el-input v-model="form.weChatNum" placeholder="请输入微信" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="省份" prop="province">
          <el-input v-model="form.province" placeholder="请输入省份" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-input v-model="form.city" placeholder="请输入城市" />
        </el-form-item>
        <el-form-item label="区" prop="region">
          <el-input v-model="form.region" placeholder="请输入区" />
        </el-form-item>
        <el-form-item label="个人介绍" prop="selfIntroduction">
          <el-input v-model="form.selfIntroduction" placeholder="请输入个人介绍" />
        </el-form-item>
        <el-form-item label="个人标签" prop="selfTags">
          <el-input v-model="form.selfTags" placeholder="请输入个人标签" />
        </el-form-item>
        <el-form-item label="录音文件" prop="voiceUrl">
          <el-input v-model="form.voiceUrl" placeholder="请输入录音文件" />
        </el-form-item>
        <el-form-item label="录音时长" prop="voiceTime">
          <el-input v-model="form.voiceTime" placeholder="请输入录音时长" />
        </el-form-item>
        <el-form-item label="是否置顶" prop="ifTop">
          <el-select v-model="form.ifTop" placeholder="请选择是否置顶">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="推荐码" prop="referralCode">
          <el-input v-model="form.referralCode" placeholder="请输入推荐码" />
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
import { listStaffInfo, getStaffInfo, delStaffInfo, addStaffInfo, updateStaffInfo } from "@/api/staff/staffInfo";

export default {
  name: "StaffInfo",
  dicts: ['sys_yes_no', 'sys_user_sex', 'staff_state'],
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
      // 员工信息表格数据
      staffInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        referralUserId: null,
        nickName: null,
        state: null,
        ifOnline: null,
        sex: null,
        weChatNum: null,
        phone: null,
        ifTop: null,
        referralCode: null,
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
    /** 查询员工信息列表 */
    getList() {
      this.loading = true;
      listStaffInfo(this.queryParams).then(response => {
        this.staffInfoList = response.rows;
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
        referralUserId: null,
        avatarUrl: null,
        nickName: null,
        state: null,
        ifOnline: null,
        staffLevel: null,
        birthDate: null,
        sex: null,
        weChatNum: null,
        phone: null,
        province: null,
        city: null,
        region: null,
        selfIntroduction: null,
        selfTags: null,
        voiceUrl: null,
        voiceTime: null,
        ifTop: null,
        sortNum: null,
        referralCode: null,
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
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getStaffInfo(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateStaffInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStaffInfo(this.form).then(response => {
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
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除员工信息编号为"' + userIds + '"的数据项？').then(function() {
        return delStaffInfo(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('staff/staffInfo/export', {
        ...this.queryParams
      }, `staffInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
