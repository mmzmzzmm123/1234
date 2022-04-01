<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="加入日期" prop="joinDate">
        <el-date-picker clearable
                        v-model="queryParams.joinDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择加入日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="到期日期" prop="dueDate">
        <el-date-picker clearable
                        v-model="queryParams.dueDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择到期日期">
        </el-date-picker>
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
          v-hasPermi="['csa:contract:add']"
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
          v-hasPermi="['csa:contract:edit']"
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
          v-hasPermi="['csa:contract:remove']"
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
          v-hasPermi="['csa:contract:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="姓名" align="center" prop="memberName"/>
      <el-table-column label="金币" align="center" prop="coins"/>
      <el-table-column label="账户金额" align="center" prop="balance"/>
      <el-table-column label="菜品重量" align="center" prop="weight"/>
      <el-table-column label="拱棚年限" align="center" prop="archedYears"/>
      <el-table-column label="激活方式" align="center" prop="activateMode"/>
      <el-table-column label="凭据号码" align="center" prop="joinCredentialNo"/>
      <el-table-column label="签约日期" align="center" prop="joinDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.joinDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:contract:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['csa:contract:remove']"
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

    <!-- 添加或修改会员签约对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="660px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="memberName">
              <el-input v-model="form.memberName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="激活方式" prop="activateMode">
              <el-input v-model="form.activateMode" placeholder="请选择激活方式"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="凭据号码" prop="joinCredentialNo">
          <el-input v-model="form.joinCredentialNo" placeholder="请输入签约凭据号码"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="金币数量" prop="coins">
              <el-input v-model="form.coins" placeholder="请输入金币"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户金额" prop="balance">
              <el-input v-model="form.balance" placeholder="请输入账户金额"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="菜品重量" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入菜品重量"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拱棚年限" prop="archedYears">
              <el-input v-model="form.archedYears" placeholder="请输入拱棚年限"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始日期" prop="joinDate">
              <el-date-picker clearable
                              v-model="form.joinDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择开始日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日期" prop="dueDate">
              <el-date-picker clearable
                              v-model="form.dueDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择到期日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
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
  import { listContract, getContract, delContract, addContract, updateContract } from '@/api/csa/contract'

  export default {
    name: 'Contract',
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
        // 会员签约表格数据
        contractList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          memberName: null,
          joinDate: null,
          dueDate: null,
          status: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          memberName: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          coins: [
            { required: true, message: '金币数量不能为空', trigger: 'blur' }
          ],
          balance: [
            { required: true, message: '账户金额不能为空', trigger: 'blur' }
          ],
          weight: [
            { required: true, message: '菜品重量不能为空', trigger: 'blur' }
          ],
          archedYears: [
            { required: true, message: '拱棚年限不能为空', trigger: 'blur' }
          ],
          activateMode: [
            { required: true, message: '激活方式不能为空', trigger: 'blur' }
          ],
          joinCredentialNo: [
            { required: true, message: '凭据号码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      /** 查询会员签约列表 */
      getList() {
        this.loading = true
        listContract(this.queryParams).then(response => {
          this.contractList = response.rows
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
          contractId: null,
          farmerId: null,
          memberName: null,
          coins: null,
          balance: null,
          weight: null,
          archedYears: null,
          activateMode: null,
          joinCredentialNo: null,
          joinDate: null,
          dueDate: null,
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
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.contractId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加会员合约'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const contractId = row.contractId || this.ids
        getContract(contractId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改会员合约'
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.contractId != null) {
              updateContract(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              })
            } else {
              addContract(this.form).then(response => {
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
        const contractIds = row.contractId || this.ids
        this.$modal.confirm('是否确认删除会员合约编号为"' + contractIds + '"的数据项？').then(function() {
          return delContract(contractIds)
        }).then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).catch(() => {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('csa/contract/export', {
          ...this.queryParams
        }, `contract_${new Date().getTime()}.xlsx`)
      }
    }
  }
</script>
