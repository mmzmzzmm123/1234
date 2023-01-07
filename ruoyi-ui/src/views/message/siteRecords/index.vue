<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
    >
      <el-form-item label="发送者用户ID" prop="sendUserId">
        <el-input
          v-model="queryParams.sendUserId"
          placeholder="请输入发送者用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发送者用户名称" prop="sendUserName">
        <el-input
          v-model="queryParams.sendUserName"
          placeholder="请输入发送者用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收者用户ID" prop="toUserId">
        <el-input
          v-model="queryParams.toUserId"
          placeholder="请输入接收者用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接受者用户名称" prop="toUserName">
        <el-input
          v-model="queryParams.toUserName"
          placeholder="请输入接受者用户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="站内信标题" prop="msgSiteTitle">
        <el-input
          v-model="queryParams.msgSiteTitle"
          placeholder="请输入站内信标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否已读" prop="hasRead">
        <el-select
          v-model="queryParams.hasRead"
          placeholder="请输入是否已读"
          @change="handleQuery"
        >
          <el-option label="不限" :value="null"></el-option>
          <el-option label="已读" :value="'1'"></el-option>
          <el-option label="未读" :value="'0'"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAddAll"
          v-hasPermi="['message:siteRecords:sendAll']"
          >新增全体消息
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['message:siteRecords:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:siteRecords:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['message:siteRecords:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="siteRecordsList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="站内信流水id" align="center" prop="msgSiteId" />
      <el-table-column label="发送者用户ID" align="center" prop="sendUserId" />
      <el-table-column
        label="发送者用户名称"
        align="center"
        prop="sendUserName"
      />
      <el-table-column label="接收者用户ID" align="center" prop="toUserId" />
      <el-table-column
        label="接受者用户名称"
        align="center"
        prop="toUserName"
      />
      <el-table-column label="站内信标题" align="center" prop="msgSiteTitle" />
      <el-table-column label="是否已读" align="center" prop="hasRead">
        <template slot-scope="scope">
          {{ scope.row.hasRead == "1" ? "√" : "" }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            >查看</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['message:siteRecords:remove']"
            >删除</el-button
          >
          <el-button
            v-if="scope.row.sendUserId > 0"
            size="mini"
            type="text"
            icon="el-icon-refresh-right"
            @click="handleRedo(scope.row)"
            v-hasPermi="['message:siteRecords:add']"
            >重发
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改站内信消息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="接收者用户" prop="toUserName">
          <div v-if="formShowType == 1">
            <div v-if="form.msgSiteId">{{ form.toUserName }}</div>
            <el-input
              v-else
              v-model="form.toUserName"
              placeholder="请选择接收者用户"
              :readonly="true"
            >
              <el-button
                slot="append"
                @click="toChooseUser"
                v-hasPermi="['system:user:list']"
                >选择</el-button
              >
            </el-input>
          </div>
          <div v-else>【全体成员】</div>
        </el-form-item>
        <el-form-item label="站内信标题" prop="msgSiteTitle">
          <div v-if="form.msgSiteId">{{ form.msgSiteTitle }}</div>
          <el-input
            v-else
            v-model="form.msgSiteTitle"
            placeholder="请输入站内信标题"
            :maxlength="100"
            show-word-limit
            clearable
          />
        </el-form-item>
        <el-form-item label="站内信内容">
          <div v-if="form.msgSiteId" v-html="form.msgSiteContent"></div>
          <editor v-else v-model="form.msgSiteContent" :min-height="192" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="!form.msgSiteId" type="primary" @click="submitForm">
          确 定
        </el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <ChooseUserDialog
        ref="ChooseUserDialog"
        @chooseOver="chooseOver"
      ></ChooseUserDialog>
    </el-dialog>
  </div>
</template>

<script>
import {
  listSiteRecords,
  getSiteRecords,
  delSiteRecords,
  addSiteRecords,
  sendAllSiteRecords,
} from "@/api/message/siteRecords";
import { deepClone } from "@/utils/index";
import ChooseUserDialog from "@/views/system/user/components/ChooseUserDialog";
export default {
  name: "SiteRecords",
  components: {
    ChooseUserDialog,
  },
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
      // 站内信消息表格数据
      siteRecordsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sendUserId: null,
        sendUserName: null,
        toUserId: null,
        toUserName: null,
        msgSiteTitle: null,
        msgSiteContent: null,
        hasRead: null,
      },
      // 显示类型,1 指定用户,2 全体
      formShowType: 1,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        toUserName: [
          { required: true, message: "接收者用户不能为空", trigger: "blur" },
        ],
        msgSiteTitle: [
          { required: true, message: "站内信标题不能为空", trigger: "blur" },
        ],
        msgSiteContent: [
          { required: true, message: "站内信内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询站内信消息列表 */
    getList() {
      this.loading = true;
      listSiteRecords(this.queryParams).then((response) => {
        this.siteRecordsList = response.rows;
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
        msgSiteId: null,
        sendUserId: null,
        sendUserName: null,
        toUserId: null,
        toUserName: null,
        msgSiteTitle: null,
        msgSiteContent: null,
        hasRead: null,
        createTime: null,
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
      this.ids = selection.map((item) => item.msgSiteId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.formShowType = 1;
      this.title = "添加站内信消息";
    },
    /** 新增按钮操作(全体) */
    handleAddAll() {
      this.reset();
      this.open = true;
      this.formShowType = 2;
      this.form.toUserId = 0;
      this.form.toUserName = "【全体成员】";
      this.title = "发送全体消息";
    },
    /** 重发 */
    handleRedo(row) {
      if (row.toUserId == 0) {
        return;
      }
      let r = deepClone(row);
      r.msgSiteId = null;
      r.hasRead = "0";
      r.createTime = null;
      this.$modal
        .confirm("是否重复发送一遍？")
        .then(function () {
          return addSiteRecords(r);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("新增成功");
        })
        .catch(() => {});
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.reset();
      const msgSiteId = row.msgSiteId || this.ids;
      getSiteRecords(msgSiteId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "查看站内信消息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.msgSiteId == null) {
            if (this.formShowType == 1) {
              addSiteRecords(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            } else {
              sendAllSiteRecords(this.form).then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const msgSiteIds = row.msgSiteId || this.ids;
      this.$modal
        .confirm('是否确认删除站内信消息编号为"' + msgSiteIds + '"的数据项？')
        .then(function () {
          return delSiteRecords(msgSiteIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "message/siteRecords/export",
        {
          ...this.queryParams,
        },
        `siteRecords_${new Date().getTime()}.xlsx`
      );
    },
    toChooseUser() {
      this.$refs.ChooseUserDialog.open();
    },
    chooseOver(users) {
      if (!users || users.length == 0) {
        this.form.toUserId = null;
        this.form.toUserName = "";
      } else {
        this.form.toUserId = users[0].userId;
        this.form.toUserName = users[0].userName;
      }
    },
  },
};
</script>
