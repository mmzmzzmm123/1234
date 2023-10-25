<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="邮箱" prop="mailAccount">
        <el-input
          v-model="queryParams.mailAccount"
          placeholder="请输入邮箱地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="0 无问题   1 删除" prop="isDelete">
        <el-input
          v-model="queryParams.isDelete"
          placeholder="请输入0 无问题   1 删除"
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
          v-hasPermi="['mail:user:add']"
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['mail:user:edit']"
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['mail:user:remove']"
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['mail:user:export']"
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="email" align="center" prop="mailAccount" />
      <el-table-column label="1 发送晚安消息" align="center" prop="mailType" />
      <el-table-column label="name" align="center" prop="name" />
      <el-table-column label="0 无问题   1 删除" align="center" prop="isDelete" />
      <!--      <el-table-column label="学习计划人员" align="center" prop="type" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['mail:user:edit']"
            size="mini"
            type="text"
            icon="el-icon-s-promotion"
            @click="handleSend(scope.row)"
          >发送</el-button>
          <el-button
            v-hasPermi="['mail:user:edit']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            v-hasPermi="['mail:user:remove']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改邮件用户列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="邮箱账户" prop="mailAccount">
          <el-input v-model="form.mailAccount" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="0 无问题   1 删除" prop="isDelete">
          <el-input v-model="form.isDelete" placeholder="请输入0 无问题   1 删除" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 发送邮件的对话框 -->
    <el-dialog
      title="发送邮件"
      :visible.sync="dialogVisible"
      width="500px"
      @close="handleClose"
    >
      <el-form ref="sendForm" :model="sendForm" :rules="sendRules" inline label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sendForm.name" placeholder="请输入信息" size="mini" />
        </el-form-item>
        <el-form-item label="email" prop="mailAccount">
          <el-input v-model="sendForm.mailAccount" placeholder="请输入信息" size="mini" />
        </el-form-item>
        <el-form-item label="主题" prop="subject">
          <el-input value="晚安" placeholder="请输入信息" size="mini" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-select v-model="sendForm.content" size="mini" placeholder="请选择">
            <el-option
              v-for="item in contentList"
              :key="item.id"
              :label="item.content"
              :value="item.content"
              style="width: 300px;"
              class="options"
            />
          </el-select>
        </el-form-item>
        <el-row type="flex" justify="center">
          <el-button type="primary" size="mini" :disabled="loading1" @click="sendEmail">发送邮件</el-button>
        </el-row>

      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, sendEmail } from '@/api/mail/user'
import { listContent } from '@/api/mail/content'
export default {
  name: 'User',
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
      // 邮件用户列表表格数据
      userList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mailAccount: null,
        mailType: null,
        name: null,
        isDelete: null,
        type: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mailAccount: [
          { required: true, message: '必填', trigger: 'blur' }
        ]
      },
      // 发送弹框的显示隐藏
      dialogVisible: false,
      // 发送弹框的form
      sendForm: {},
      sendRules: {},
      // 内容列表
      contentList: [],
      // 内容请求参数
      // 查询参数
      query: {
        pageNum: 1,
        pageSize: 10,
        content: null,
        type: null,
        isDelete: null
      },
      // 发送请求loading防止再次触发
      loading1: false
    }
  },
  created() {
    this.getList()
    this.getContentList()
  },
  methods: {
    // 获取晚安语列表
    getContentList() {
      listContent(this.query).then((response) => {
        console.log(response.rows)

        this.contentList = response.rows
      })
    },
    /** 查询邮件用户列表列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.userList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        mailAccount: null,
        mailType: null,
        name: null,
        createTime: null,
        updateTime: null,
        isDelete: null,
        type: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加邮件用户列表'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改邮件用户列表'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除邮件用户列表编号为"' + ids + '"的数据项？').then(function() {
        return delUser(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mail/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    handleSend(row) {
      // console.log(row);
      this.sendForm = row
      this.dialogVisible = true
    },
    handleClose() {
      this.dialogVisible = false
      this.resetForm('sendForm')
    },
    // 发送邮件
    sendEmail() {
      // 防止重复提交
      if (this.loading) {
        return
      }
      const data = {
        attachments: '',
        receiveUserMail: this.sendForm.mailAccount,
        subject: '晚安',
        content: this.sendForm.content
      }
      this.loading1 = true
      sendEmail(data)
        .then(response => {
          this.$modal.msgSuccess('发送成功')
          this.dialogVisible = false
          this.getList()
        })
        .catch(() => {
        // 处理发送失败的情况
          this.$modal.msgError('发送失败')
        })
        .finally(() => {
          this.loading1 = false // 结束加载
        })
    }
  }
}
</script>
<style lang="scss" scoped>
  .options{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
