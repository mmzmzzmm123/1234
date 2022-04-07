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
          type="success"
          icon="el-icon-lock"
          size="mini"
          :disabled="single"
          @click="handleConfirmClaim"
          v-hasPermi="['csa:contract:edit']"
        >确认生效
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="rowCanSelect" />
<!--      <el-table-column type="selection" width="55" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-checkbox :disabled="scope.row.status != '1'" />-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="签约人" align="center" prop="contractor"/>
      <el-table-column label="会员姓名" align="center" prop="farmer.name">
        <template slot-scope="scope">
          <el-tag
            :key="scope.row.contractId"
            :type="scope.row.status == '1' ? 'success' : 'warning'" effect="plain">
            {{ scope.row.farmer.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" align="center" prop="farmer.mobileNumber" width="120" />
      <el-table-column label="菜地编号" align="center" prop="garden.Name">
        <template slot-scope="scope">
          <el-tag
            :key="'c' + scope.row.contractId"
            :type="scope.row.status == '1' ? 'success' : 'warning'" effect="plain">
            {{ scope.row.garden ? scope.row.garden.code : '未认领' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="菜地名称" align="center" prop="garden.Name">
        <template slot-scope="scope">
          <el-tag
            :key="'n' + scope.row.contractId"
            :type="scope.row.status == '1' ? 'success' : 'warning'" effect="plain">
            {{ scope.row.garden ? (scope.row.garden.name != '' ? scope.row.garden.name : '未命名' ) : '未认领' }}
          </el-tag>
        </template>
      </el-table-column>
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
      <el-table-column label="金币数量" align="right" prop="coins" width="80"/>
<!--      <el-table-column label="账户金额" align="center" prop="balance"/>-->
      <el-table-column label="菜品斤数" align="right" prop="weight" width="80"/>
      <el-table-column label="拱棚年限" align="center" prop="archedYears" width="80"/>
      <el-table-column label="生效日期" align="center" prop="joinDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.joinDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="到期日期" align="center" prop="dueDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="天数" align="right" prop="days" width="60" />
      <el-table-column label="备注" align="center" prop="remark" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" fixed="right" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            :disabled="(scope.row.status != '2' && scope.row.status != '1')"
            size="mini "
            type="text"
            icon="el-icon-thumb"
            @click="handleUpdateFarmer(scope.row)"
            v-hasPermi="['csa:contract:edit']"
          >会员绑定
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
          <el-col :span="12">
            <el-form-item label="会员姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入会员姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="mobileNumber">
              <el-input v-model="form.mobileNumber" placeholder="请输入手机号码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="菜地编号" prop="garden.code">
              <el-select v-model="form.garden.code" value-key="code" @change="onGardenSelect" placeholder="请选择菜地编号"
                         :loading="isQuery" loading-text="正在查找可用菜地"
                         filterable remote :remote-method="remoteQueryGarden">
                <el-option
                  v-for="garden in this.canSellGardens"
                  :key="garden.gardenId"
                  :label="garden.code"
                  :value="garden"
                  :disabled="garden.farmerId != null && garden.farmerId != form.farmerId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜地名称" prop="garden.name">
              <el-input v-model="form.garden.name" placeholder="请输入菜地名称"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitClaimForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listEnterContract, getContract, auditContract, claimFarmer } from '@/api/csa/contract'
  import { getFarmerWithGarden  } from '@/api/csa/farmer'
  import { listCanSell } from '@/api/csa/garden'
  import Dict from '../../system/dict/index'

  export default {
    name: 'Enter',
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
        // 是否显示显示资料弹出层
        open: false,
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
        // 是否正在查询菜地列表
        isQuery: false,
        // 可售的菜地列表
        canSellGardens: [],
        // 表单参数
        form: {contractId: null, garden: {} },
        // 表单校验
        rules: {
          name: [
            { required: true, message: '会员姓名不能为空', trigger: 'blur' }
          ],
          mobileNumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ],
          garden: {
              code: [
                { required: true, message: '菜地编号不能为空', trigger: 'blur' }
              ],
              name: [
                { required: true, message: '菜地名称不能为空', trigger: 'blur' }
              ]
          }
        }
      }
    },
    created() {
      this.getList()
      this.remoteQueryGarden('')
    },
    methods: {
      /** 查询会员签约列表 */
      getList() {
        this.loading = true
        listEnterContract(this.queryParams).then(response => {
          this.contractList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      remoteQueryGarden(code) {
        this.isQuery = true;
        listCanSell(code).then(response => {
          this.canSellGardens = response.rows;
          this.isQuery = false;
        });
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
          garden: {},
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
      rowCanSelect(row) {
        return row.status == '1'
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.curRowData = (selection.length == 1) ? selection[0] : null;
        this.ids = selection.map(item => item.contractId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      onGardenSelect(val) {
        this.form.garden.gardenId = val.gardenId
        // console.log(val)
      },
      /** 认领菜地对话框弹出操作 */
      handleUpdateFarmer(row) {
        this.reset()
        const farmerId = row.farmerId;
        getFarmerWithGarden(farmerId).then(response => {
          this.form = response.data
          this.form.contractId = row.contractId
          this.open = true
          this.title = '会员绑定'
        })
      },
      /** 确认生效按钮操作 */
      handleConfirmClaim(row) {
        return;
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
      /** 认领菜地提交按钮 */
      submitClaimForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            let claim = {
              contractId: this.form.contractId,
              farmerName: this.form.name,
              mobileNumber: this.form.mobileNumber,
              gardenId: this.form.garden.gardenId,
              gardenName: this.form.garden.name
            }

            claimFarmer(claim).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList();
            })
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
