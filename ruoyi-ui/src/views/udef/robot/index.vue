<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="API账户" prop="apiId">
        <el-select v-model="queryParams.apiId" placeholder="请选择API账户" clearable>
          <el-option
            v-for="dict in dict.type.udef_dict_api_key"
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
          v-hasPermi="['udef:robot:add']"
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
          v-hasPermi="['udef:robot:edit']"
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
          v-hasPermi="['udef:robot:remove']"
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
          v-hasPermi="['udef:robot:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="robotList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="机器人" align="center" prop="robotName"/>
      <el-table-column label="合约名" align="center" prop="symbol"/>
<!--      <el-table-column label="固定开仓比" align="center" prop="quantityBase"/>-->
      <el-table-column label="固定开仓张数" align="center" prop="quantity"/>
      <el-table-column label="小周期" align="center" prop="litInterval"/>
      <el-table-column label="大周期" align="center" prop="bigInterval"/>
      <el-table-column label="持仓模式" align="center" prop="margin">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_dict_margin" :value="scope.row.margin"/>
        </template>
      </el-table-column>
      <el-table-column label="杠杆" align="center" prop="leverage"/>
      <el-table-column label="启停" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status=='init'"
                     size="mini"
                     type="text"
                     icon="el-icon-setting"
                     @click="handleAction(scope.row.id,scope.row.status)"
                     v-hasPermi="['udef:robot:edit']"
          >初始化
          </el-button>
          <el-button v-if="scope.row.status=='stopped'"
                     size="mini"
                     type="text"
                     icon="el-icon-video-play"
                     @click="handleAction(scope.row.id,scope.row.status)"
                     v-hasPermi="['udef:robot:edit']"
          >启动
          </el-button>
          <el-button v-if="scope.row.status=='running'"
                     size="mini"
                     type="text"
                     icon="el-icon-loading"
                     @click="handleAction(scope.row.id,scope.row.status)"
                     v-hasPermi="['udef:robot:edit']"
          >停止
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="API账户" align="center" prop="apiId">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.udef_dict_api_key" :value="scope.row.apiId"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['udef:robot:edit']"
          >修改
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

    <!-- 添加或修改机器人对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="机器人" prop="robotName">
          <el-input v-model="form.robotName" placeholder="请输入机器人"/>
        </el-form-item>
        <el-form-item label="合约名" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入合约名"/>
        </el-form-item>
<!--        <el-form-item label="固定开仓比" prop="quantityBase">-->
<!--          <el-input v-model="form.quantityBase" placeholder="请输入固定开仓比"/>-->
<!--        </el-form-item>-->
        <el-form-item label="固定开仓张数" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入固定开仓张数"/>
        </el-form-item>
        <el-form-item label="小周期" prop="litInterval">
          <el-input v-model="form.litInterval" placeholder="请输入小周期"/>
        </el-form-item>
        <el-form-item label="大周期" prop="bigInterval">
          <el-input v-model="form.bigInterval" placeholder="请输入大周期"/>
        </el-form-item>
        <el-form-item label="持仓模式" prop="margin">
          <el-select v-model="form.margin" placeholder="请选择持仓模式">
            <el-option
              v-for="dict in dict.type.udef_dict_margin"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="杠杆" prop="leverage">
          <el-input v-model="form.leverage" placeholder="请输入杠杆"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.udef_robot_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="API账户" prop="apiId">
          <el-select v-model="form.apiId" placeholder="请选择API账户">
            <el-option
              v-for="dict in dict.type.udef_dict_api_key"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">计划信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddDfEscapeSchedule">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteDfEscapeSchedule">删除
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="dfEscapeScheduleList" :row-class-name="rowDfEscapeScheduleIndex"
                  @selection-change="handleDfEscapeScheduleSelectionChange" ref="dfEscapeSchedule"
        >
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="计划类型" prop="escapeType" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.escapeType" placeholder="请选择计划类型">
                <el-option
                  v-for="dict in dict.type.udef_escape_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="平仓价格比" prop="priceRate" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.priceRate" placeholder="请输入平仓价格比"/>
            </template>
          </el-table-column>
          <el-table-column label="平仓数量比" prop="quantityRate" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantityRate" placeholder="请输入平仓价格比"/>
            </template>
          </el-table-column>
          <el-table-column label="优先级" prop="priority" width="150">
            <template slot-scope="scope">
              <el-select v-model="scope.row.priority" placeholder="请选择优先级">
                <el-option
                  v-for="dict in dict.type.udef_schedule_priority"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
<!--          <el-table-column label="平仓数量" prop="quantity" width="150">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.quantity" placeholder="请输入平仓数量"/>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRobot, getRobot, delRobot, addRobot, updateRobot, actionOnRobot } from '@/api/udef/robot'

export default {
  name: 'Robot',
  dicts: ['udef_escape_type', 'udef_schedule_priority', 'udef_dict_margin', 'udef_robot_status', 'udef_dict_api_key'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedDfEscapeSchedule: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 机器人表格数据
      robotList: [],
      // 计划表格数据
      dfEscapeScheduleList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        robotName: null,
        symbol: null,
        leverage: null,
        status: null,
        apiId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        robotName: [
          { required: true, message: '机器人不能为空', trigger: 'blur' }
        ],
        symbol: [
          { required: true, message: '合约名不能为空', trigger: 'blur' }
        ],
        litInterval: [
          { required: true, message: '小周期不能为空', trigger: 'blur' }
        ],
        bigInterval: [
          { required: true, message: '大周期不能为空', trigger: 'blur' }
        ],
        margin: [
          { required: true, message: '持仓模式不能为空', trigger: 'change' }
        ],
        leverage: [
          { required: true, message: '杠杆不能为空', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '状态不能为空', trigger: 'change' }
        ],
        apiId: [
          { required: true, message: 'API账户不能为空', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询机器人列表 */
    getList() {
      this.loading = true
      listRobot(this.queryParams).then(response => {
        this.robotList = response.rows
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
        robotName: null,
        symbol: null,
        quantityBase: null,
        quantity: null,
        litInterval: null,
        bigInterval: null,
        margin: null,
        leverage: null,
        status: null,
        apiId: null,
        createTime: null,
        updateTime: null
      }
      this.dfEscapeScheduleList = []
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
      this.title = '添加机器人'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getRobot(id).then(response => {
        this.form = response.data
        this.dfEscapeScheduleList = response.data.dfEscapeScheduleList
        this.open = true
        this.title = '修改机器人'
      })
    },

    handleAction(id, action) {
      actionOnRobot(id, action).then(response => {
        this.getList()
      })
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.form.dfEscapeScheduleList = this.dfEscapeScheduleList
          if (this.form.id != null) {
            updateRobot(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRobot(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除机器人编号为"' + ids + '"的数据项？').then(function() {
        return delRobot(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 计划序号 */
    rowDfEscapeScheduleIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 计划添加按钮操作 */
    handleAddDfEscapeSchedule() {
      let obj = {}
      obj.escapeType = ''
      obj.priceRate = ''
      obj.quantityRate = ''
      obj.quantity = ''
      obj.priority = ''
      this.dfEscapeScheduleList.push(obj)
    },
    /** 计划删除按钮操作 */
    handleDeleteDfEscapeSchedule() {
      if (this.checkedDfEscapeSchedule.length == 0) {
        this.$modal.msgError('请先选择要删除的计划数据')
      } else {
        const dfEscapeScheduleList = this.dfEscapeScheduleList
        const checkedDfEscapeSchedule = this.checkedDfEscapeSchedule
        this.dfEscapeScheduleList = dfEscapeScheduleList.filter(function(item) {
          return checkedDfEscapeSchedule.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleDfEscapeScheduleSelectionChange(selection) {
      this.checkedDfEscapeSchedule = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('udef/robot/export', {
        ...this.queryParams
      }, `robot_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
