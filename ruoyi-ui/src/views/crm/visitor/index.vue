<template>
  <div class="app-container" ref="app_container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="访客姓名" prop="visitorName">
        <el-input
          v-model="queryParams.visitorName"
          placeholder="请输入访客姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访客电话" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入访客电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访客事由" prop="matter">
        <el-input
          v-model="queryParams.matter"
          placeholder="请输入访客事由"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="待办事项" prop="backlog">
        <el-input
          v-model="queryParams.backlog"
          placeholder="请输入待办事项"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访问时间">
        <el-date-picker
          v-model="daterangeVisitTime"
          size="small"
          style="width: 240px"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_show_hide"
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
          v-hasPermi="['pr:visitor:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pr:visitor:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pr:visitor:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pr:visitor:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="table" border v-loading="loading" :data="visitorList">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="访客Id" align="center" prop="visitorId"/>
      <el-table-column label="访客姓名" align="center" prop="visitorName"/>
      <el-table-column label="访客电话" align="center" prop="phone"/>
      <el-table-column label="访客事由" align="center" prop="matter"/>
      <el-table-column label="待办事项" align="center" prop="backlog"/>
      <el-table-column label="访问时间" align="center" prop="visitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_show_hide" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pr:visitor:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pr:visitor:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改访客信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="访客姓名" prop="visitorName">
          <el-input v-model="form.visitorName" placeholder="请输入访客姓名"/>
        </el-form-item>
        <el-form-item label="访客电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入访客电话"/>
        </el-form-item>
        <el-form-item label="访客事由" prop="matter">
          <el-input v-model="form.matter" placeholder="请输入访客事由"/>
        </el-form-item>
        <el-form-item label="待办事项" prop="backlog">
          <el-input v-model="form.backlog" placeholder="请输入待办事项"/>
        </el-form-item>
        <el-form-item label="访问时间" prop="visitTime">
          <el-date-picker clearable size="small"
                          v-model="form.visitTime"
                          type="datetime"
                          format="yyyy-MM-dd HH:mm"
                          value-format="yyyy-MM-dd HH:mm"
                          placeholder="选择访问时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_show_hide"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}
            </el-radio>
          </el-radio-group>
        </el-form-item>
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
  import { listVisitor, getVisitor, delVisitor, addVisitor, updateVisitor } from '@/api/farming/crm/visitor'

  export default {
    name: 'Visitor-Context-Js',
    components: {

    },
    dicts: ['sys_show_hide'],
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
        // 访客信息表格数据
        visitorList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 备注时间范围
        daterangeVisitTime: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          visitorName: null,
          phone: null,
          matter: null,
          backlog: null,
          visitTime: null,
          status: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          visitorName: [
            { required: true, message: '访客姓名不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '访客电话不能为空', trigger: 'blur' }
          ],
          matter: [
            { required: true, message: '访客事由不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ]
        },
      }
    },
    computed: {
      isShowEditDiv: function() {
        return this.cellPosition.colIndex > -1 && this.cellPosition.rowIndex > -1;
      }
    },
    created() {
      this.getList()
    },
    mounted() {
    },
    destroyed() {
    },
    watch: {
    },
    methods: {
      /** 查询访客信息列表 */
      getList() {
        this.loading = true
        this.queryParams.params = {}
        if (null != this.daterangeVisitTime && '' != this.daterangeVisitTime) {
          this.queryParams.params['beginVisitTime'] = this.daterangeVisitTime[0]
          this.queryParams.params['endVisitTime'] = this.daterangeVisitTime[1]
        }
        listVisitor(this.queryParams).then(response => {
          this.visitorList = response.rows
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
          visitorId: null,
          visitorName: null,
          phone: null,
          matter: null,
          backlog: null,
          visitTime: null,
          status: '0',
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null
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
        this.daterangeVisitTime = []
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.visitorId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加访客信息'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const visitorId = row.visitorId || this.ids
        getVisitor(visitorId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改访客信息'
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.visitorId != null) {
              updateVisitor(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addVisitor(this.form).then(response => {
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
        const visitorIds = row.visitorId || this.ids
        this.$modal.confirm('是否确认删除访客信息编号为"' + visitorIds + '"的数据项？').then(function() {
          return delVisitor(visitorIds)
        }).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('pr/visitor/export', {
          ...this.queryParams
        }, `visitor_${new Date().getTime()}.xlsx`)
      }
    }
  }
</script>

<style scoped>
</style>

