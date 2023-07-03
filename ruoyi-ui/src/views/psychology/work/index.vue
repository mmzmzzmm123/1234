<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="咨询师" prop="consultName">
        <el-input
          v-model="queryParams.consultName"
          placeholder="请输入咨询师"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="咨询方式" prop="serveName">
        <el-input
          v-model="queryParams.serveName"
          placeholder="请输入咨询方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="咨询时段" prop="timeStart">
        <el-date-picker clearable
                        v-model="queryParams.timeStart"
                        type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择咨询时段">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="咨询时段" prop="timeEnd">
        <el-date-picker clearable
                        v-model="queryParams.timeEnd"
                        type="datetime"
                        format="yyyy-MM-dd HH:mm"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        placeholder="请选择咨询时段">
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
          v-hasPermi="['system:work:add']"
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
          v-hasPermi="['system:work:edit']"
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
          v-hasPermi="['system:work:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:work:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="咨询师" align="center" prop="consultName" />
      <el-table-column label="服务" align="center" prop="serveName" />
      <el-table-column label="班次" align="center" prop="type" />
      <el-table-column label="星期" align="center" prop="week" />
      <el-table-column label="咨询时段开始时间" align="center" prop="timeStart" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.timeStart, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="咨询时段结束时间" align="center" prop="timeEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.timeEnd, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="咨询时长(分钟)" align="center" prop="time" />
      <el-table-column label="可预约数量" align="center" prop="num" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          {{ scope.row.status === '0' ? '开启' : '关闭' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:work:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:work:remove']"
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

    <!-- 添加或修改咨询服务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="咨询师" prop="consultId">
          <el-select v-model="form.consultId" placeholder="请选择咨询师" @change="consultChange" :disabled="consultList.length === 1" clearable>
            <el-option
              v-for="item in consultList"
              :key="item.id"
              :label="item.userName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="咨询方式" prop="serveId">
          <el-select v-model="form.serveId" placeholder="请选择咨询方式" clearable>
            <el-option
              v-for="item in serveList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="可选班次" prop="type">
          <el-select v-model="form.type" placeholder="请选择班次" clearable>
            <el-option
              v-for="item in dict.type.consult_time"
              :key="item.label"
              :label="item.label"
              :value="item.label"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="咨询时间" prop="timeRange">
          <el-date-picker
            clearable
            v-model="form.timeRange"
            :picker-options="{
              start: '08:30',
              step: '00:15',
              end: '18:30'
            }"
            format="yyyy-MM-dd HH:mm"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="可预约数量" prop="num">
          <el-input-number v-model="form.num" disabled :min="1" :max="99999"></el-input-number>
        </el-form-item>
        <el-form-item label="启用" prop="status">
          <el-switch v-model="form.status" active-value="0" inactive-value="1"/>
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
import { mapState } from "vuex";
import { getServeAll } from "@/api/psychology/serve";
import { getConsultAll } from "@/api/psychology/consult";
import { listWork, getWork, delWork, addWork, updateWork } from "@/api/psychology/work";

export default {
  dicts: ['consult_time'],
  name: "ConsultWork",
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
      consultList: [],
      serveList: [],
      // 咨询服务表格数据
      workList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        consultId: null,
        consultName: null,
        serveId: null,
        serveName: null,
        type: null,
        timeStart: null,
        timeEnd: null,
        timeRange: [],
        time: null,
        num: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        consultId: [
          { required: true, message: "咨询师不能为空", trigger: "change" }
        ],
        serveId: [
          { required: true, message: "咨询方式不能为空", trigger: "change" }
        ],
        timeRange: [
          { required: true, message: "咨询时段不能为空", trigger: "change" }
        ],
        type: [
          { required: true, message: "可选班次不能为空", trigger: "change" }
        ],
        num: [
          { required: true, message: "可预约数量不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    console.log(this.userName)
    this.getConsultList()
    this.getList();
  },
  computed: {
  ...mapState({
      userName: (state) => state.user.name,
    }),
  },
  methods: {
    // isAdmin() {
    //   return this.userName === 'admin'
    // },
    /** 查询咨询师列表 */
    async getConsultList() {
      const res = await getConsultAll()
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.getConsultServeList(this.consultList[0].id)
      }
    },
    /** 查询咨询师服务列表 */
    getConsultServeList(consultId) {
      getServeAll({ consultId: consultId, status: 0 }).then(response => {
        this.serveList = response.data;
      });
    },
    consultChange(id) {
      console.log(id)
      this.getConsultServeList(id)
    },
    /** 查询咨询服务列表 */
    getList() {
      this.loading = true;
      listWork(this.queryParams).then(response => {
        this.workList = response.rows;
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
        id: null,
        consultId: null,
        consultName: null,
        serveId: null,
        serveName: null,
        type: null,
        timeStart: null,
        timeEnd: null,
        time: null,
        num: 1,
        delFlag: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加咨询服务";
      if (this.consultList.length === 1) {
        this.form.consultId = this.consultList[0].id
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWork(id).then(response => {
        this.form = response.data;
        this.form.timeRange = [this.form.timeStart, this.form.timeEnd]
        this.open = true;
        this.title = "修改咨询服务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.timeStart = this.form.timeRange[0]
          this.form.timeEnd = this.form.timeRange[1]
          this.form.consultName = this.consultList.find(i => this.form.consultId === i.id).userName
          this.form.serveName = this.serveList.find(i => this.form.serveId === i.id).name

          if (this.form.id != null) {
            updateWork(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除咨询服务编号为"' + ids + '"的数据项？').then(function() {
        return delWork(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/work/export', {
        ...this.queryParams
      }, `work_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
