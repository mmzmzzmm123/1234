<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="contractor">
        <el-input
          v-model="queryParams.contractor"
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
        >录入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-s-check"
          size="mini"
          :disabled="single"
          @click="handleAudit"
          v-hasPermi="['csa:contract:edit']"
        >审核
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="single"
          @click="handleInvalid"
          v-hasPermi="['csa:contract:remove']"
        >作废
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['csa:contract:edit']"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['csa:contract:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
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
      <el-table-column label="签约人" align="center" prop="contractor"/>
      <el-table-column label="合约状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.csa_contract_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="激活方式" align="center" prop="activateWay">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.csa_activation_way" :value="scope.row.activateWay"/>
        </template>
      </el-table-column>
      <el-table-column label="凭据编号" align="center" prop="joinCredentialNo"/>
      <el-table-column label="金币数量" align="right" prop="coins" width="120"/>
<!--      <el-table-column label="账户金额" align="center" prop="balance"/>-->
      <el-table-column label="菜品斤数" align="right" prop="weight" width="120"/>
      <el-table-column label="拱棚年限" align="center" prop="archedYears"/>
      <el-table-column label="生效日期" align="center" prop="joinDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.joinDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="天数" align="right" prop="days" width="80" />
      <el-table-column label="备注" align="center" prop="remark" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.status != '1'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['csa:contract:edit']"
          >修改
          </el-button>
          <el-button
            :disabled="scope.row.status != '1'"
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
    <el-dialog :title="title" :visible.sync="open" width="560px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="签约人" prop="contractor">
              <el-input v-model="form.contractor" placeholder="签约人"/>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="菜地编号" prop="farmerId">-->
<!--              <el-select v-model="form.farmerId" filterable placeholder="签约的菜地编号">-->
<!--                <el-option-->
<!--                  v-for="item in this.famers"-->
<!--                  :key="item.farmerId"-->
<!--                  :label="item.code"-->
<!--                />-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="激活方式" prop="activateWay">
              <el-select disabled v-model="form.activateWay" placeholder="请选择激活方式">
                <el-option
                  v-for="dict in dict.type.csa_activation_way"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="凭据编号" prop="joinCredentialNo">
              <el-input v-model="form.joinCredentialNo" placeholder="合同编号或收据编号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="生效日期" prop="joinDate">
              <el-date-picker clearable
                              v-model="form.joinDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择生效日期"
                              style="width: 170px;"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到期日期" prop="dueDate">
              <el-date-picker clearable
                              v-model="form.dueDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="请选择到期日期"
                              style="width: 170px;"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="金币数量" prop="coins">
              <el-input v-model.number="form.coins" placeholder="请输入金币">
                <template slot="suffix">个</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户金额" prop="balance">
              <el-input disabled v-model.number="form.balance" placeholder="请输入账户金额">
                <template slot="suffix">元</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="菜品重量" prop="weight">
              <el-input v-model.number="form.weight" placeholder="请输入菜品重量">
                <template slot="suffix">斤</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拱棚年限" prop="archedYears">
              <el-input v-model.number="form.archedYears" placeholder="请输入拱棚年限">
                <template slot="suffix">年</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" rows="3" placeholder="请输入备注"/>
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
  import { listContract, getContract, delContract, addContract, updateContract, auditContract } from '@/api/csa/contract'
  import Dict from '../../system/dict/index'

  export default {
    name: 'Contract',
    components: { Dict },
    dicts: ['csa_activation_way', 'csa_contract_status'],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 选中的当前行数据
        curRowData: null,
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
          contractor: null,
          joinDate: null,
          dueDate: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          contractor: [
            { required: true, message: '签约人不能为空', trigger: 'blur' }
          ],
          joinDate: [
            { required: true, message: '生效日期必选', trigger: 'blur' }
          ],
          dueDate: [
            { required: true, message: '到期日期必选', trigger: 'blur' }
          ],
          joinCredentialNo: [
            { required: true, message: '凭据编号不能为空', trigger: 'blur' }
          ],
          // gardenId: [
          //   { required: true, message: '菜地编号不能为空', trigger: 'blur' }
          // ],
          coins: [
            { required: true, message: '金币数量不能为空', trigger: 'blur' },
            { type: 'number', message: '金币数量必须为整数', trigger: 'change' }
          ],
          // balance: [
          //   { required: true, message: '账户金额不能为空', trigger: 'blur' },
          //   { type: 'number', message: '金币数量必须为整数', trigger: 'change' }
          // ],
          weight: [
            { required: true, message: '菜品重量不能为空', trigger: 'blur' },
            { type: 'number', message: '菜品重量必须为整数', trigger: 'change' }
          ],
          archedYears: [
            { required: true, message: '拱棚年限不能为空', trigger: 'blur' },
            { type: 'number', message: '拱棚年限必须为整数', trigger: 'change' }
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
          contractor: null,
          coins: 0,
          balance: 0,
          weight: 0,
          archedYears: 0,
          activateWay: "QY",
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
        this.curRowData = (selection.length == 1) ? selection[0] : null;
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
      /** 审核按钮操作 */
      handleAudit(row) {
        if (this.curRowData.status != '1') {
          this.$modal.alertWarning("只有待定状态的合约才可以审核！");
          return;
        }

        this.$modal.confirm('是否要确定审核此合约', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // this.curRowData.contractId = 2;
          auditContract(this.curRowData.contractId).then( response => {
            this.$modal.alertSuccess(
              "【" + this.curRowData.contractor +
              "】的凭据编号为【" + this.curRowData.joinCredentialNo + "】的合约审核成功!")
            this.getList();
          }).catch((response) => {
            // this.$modal.alertError(response);
            // console.log(response)
          })
        })
      },
      /** 作废按钮操作 */
      handleInvalid(row) {
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
        this.$modal.confirm('是否确认删除【' + row.contractor + '】的编号为【' + row.joinCredentialNo + '】的合约？')
          .then(function() {
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
