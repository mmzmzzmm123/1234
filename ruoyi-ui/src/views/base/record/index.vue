<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="100px">
      <el-form-item label="考勤组名称" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入考勤组名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phonenumber">
        <el-input
          v-model="queryParams.phonenumber"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="班次" prop="classes">
        <el-select v-model="queryParams.classes" placeholder="请选择班次" clearable size="small">
          <el-option
            v-for="dict in classesOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工作日期" prop="workDate">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.workDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择工作日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="打卡类型" prop="punchType">
        <el-select v-model="queryParams.punchType" placeholder="请选择打卡类型" clearable size="small">
          <el-option
            v-for="dict in punchTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['base:record:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:record:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:record:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:record:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border fit highlight-current-row :data="recordList"
              @selection-change="handleSelectionChange" @sort-change='tableSortChange' @row-dblclick="handleUpdate">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="行号" width="80" align="center">
        <template slot-scope="scope">{{scope.$index + 1}}</template>
      </el-table-column>
      <el-table-column label="UUID" align="center" prop="id" v-if="false"/>
      <el-table-column label="考勤组名称" align="center" prop="deptName" sortable='custom'
                       :sort-orders="['ascending', 'descending']" width="110"/>
      <el-table-column label="姓名" align="center" prop="userName" sortable='custom'
                       :sort-orders="['ascending', 'descending']"/>
      <el-table-column label="手机号码" align="center" prop="phonenumber" sortable='custom'
                       :sort-orders="['ascending', 'descending']" width="110"/>
      <el-table-column label="班次" align="center" prop="classes" :formatter="classesFormat"/>
      <el-table-column label="工作日期" align="center" prop="workDate" width="120">
        <template slot-scope="scope">
          <!--          <span>{{ parseTime(scope.row.workDate) }}</span>-->
          <span>{{ scope.row.workDate | formatDateByDate('yyyy-MM-dd') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="打卡日期" align="center" prop="punchDate" width="120">
        <template slot-scope="scope">
          <!--          <span>{{ parseTime(scope.row.punchDate) }}</span>-->
          <span>{{ scope.row.punchDate | formatDateByDate('yyyy-MM-dd') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时间段" align="center" prop="timeQuantum" sortable='custom'
                       :sort-orders="['ascending', 'descending']"/>
      <el-table-column label="打卡类型" align="center" prop="punchType" :formatter="punchTypeFormat"/>
      <el-table-column label="打卡时间" align="center" prop="punchTime" width="110">
        <template slot-scope="scope">
          <!--          <span>{{ parseTime(scope.row.punchTime) }}</span>-->
          <span>{{ scope.row.punchTime | formatDateByDate('HH:mm:ss') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="拍照路径" align="center" prop="picUrl" sortable='custom'
                       :sort-orders="['ascending', 'descending']"/>
      <el-table-column label="定位地址" align="center" prop="address" sortable='custom'
                       :sort-orders="['ascending', 'descending']"/>
      <el-table-column label="备注" align="center" prop="remark" sortable='custom'
                       :sort-orders="['ascending', 'descending']"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:record:edit']"
          >修改
          </el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:record:remove']"
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

    <!-- 添加或修改考勤记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="考勤组名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入考勤组名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班次">
              <el-select v-model="form.classes" placeholder="请选择班次">
                <el-option
                  v-for="dict in classesOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="工作日期" prop="workDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.workDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择工作日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡日期" prop="punchDate">
              <el-date-picker clearable size="small" style="width: 200px"
                              v-model="form.punchDate"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择打卡日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="时间段" prop="timeQuantum">
              <el-input v-model="form.timeQuantum" placeholder="请输入时间段" disabled="false"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="打卡类型">
              <el-select v-model="form.punchType" placeholder="请选择打卡类型">
                <el-option
                  v-for="dict in punchTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="打卡时间" prop="punchTime">
              <el-time-picker  clearable size="small" style="width: 200px"
                              v-model="form.punchTime"
                              type="date"
                              value-format="HH:mm:ss"
                              placeholder="选择打卡时间">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="拍照路径" prop="picUrl">
              <el-input v-model="form.picUrl" placeholder="请输入拍照路径"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="定位地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入定位地址"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listRecord, getRecord, delRecord, addRecord, updateRecord, exportRecord } from '@/api/base/record'

  export default {
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
        // 总条数
        total: 0,
        // 考勤记录表格数据
        recordList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 班次字典
        classesOptions: [],
        // 打卡类型字典
        punchTypeOptions: [],
        // 状态字典
        statusOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderByColumn: undefined,
          isAsc: undefined,
          deptName: undefined,
          userName: undefined,
          phonenumber: undefined,
          classes: undefined,
          workDate: undefined,
          punchType: undefined,
          status: undefined
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          deptName: [
            { required: true, message: '考勤组名称不能为空', trigger: 'blur' }
          ], phonenumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.getList()
      this.getDicts('sys_classes').then(response => {
        this.classesOptions = response.data
      })
      this.getDicts('sys_sign').then(response => {
        this.punchTypeOptions = response.data
      })
      this.getDicts('sys_sign_status').then(response => {
        this.statusOptions = response.data
      })
    },
    methods: {
      /** 通用考勤记录排序 */
      tableSortChange(column) {
        this.queryParams.pageNum = 1
        if (column.order === 'descending') {
          this.queryParams.orderByColumn = column.prop
          this.queryParams.isAsc = 'desc'
        } else {
          this.queryParams.orderByColumn = column.prop
          this.queryParams.isAsc = 'asc'
        }
        this.getList()
      },
      /** 查询考勤记录列表 */
      getList() {
        this.loading = true
        listRecord(this.queryParams).then(response => {
          this.recordList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 班次字典翻译
      classesFormat(row, column) {
        return this.selectDictLabel(this.classesOptions, row.classes)
      },
      // 打卡类型字典翻译
      punchTypeFormat(row, column) {
        return this.selectDictLabel(this.punchTypeOptions, row.punchType)
      },
      // 状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status)
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          deptName: undefined,
          userName: undefined,
          phonenumber: undefined,
          classes: undefined,
          workDate: undefined,
          punchDate: undefined,
          timeQuantum: undefined,
          punchType: undefined,
          punchTime: undefined,
          status: undefined,
          picUrl: undefined,
          address: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          remark: undefined
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
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加考勤记录'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const id = row.id || this.ids
        getRecord(id).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改考勤记录'
        })
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateRecord(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg)
                }
              })
            } else {
              addRecord(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('新增成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg)
                }
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$confirm('是否确认删除考勤记录编号为"' + ids + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delRecord(ids)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有考勤记录数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return exportRecord(queryParams)
        }).then(response => {
          this.download(response.msg)
        }).catch(function() {
        })
      }
    }
  }
</script>
