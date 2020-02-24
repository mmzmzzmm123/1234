<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="90px">
      <el-form-item label="考勤组名称" prop="attendanceName">
        <el-input
          v-model="queryParams.attendanceName"
          placeholder="请输入考勤组名称"
          clearable
          size="small"考勤组名称
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <el-form-item label="班次类型" prop="classType">
        <el-select v-model="queryParams.classType" placeholder="请选择班次类型" clearable size="small">
          <el-option
            v-for="dict in classTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['base:group:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:group:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:group:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:group:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border fit highlight-current-row :data="groupList" @selection-change="handleSelectionChange" @sort-change='tableSortChange' @row-dblclick="handleUpdate">
    <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="行号" width="80" align="center">
            <template slot-scope="scope">{{scope.$index + 1}}</template>
    </el-table-column>
      <el-table-column label="UUID" align="center" prop="id" v-if="false"/>
      <el-table-column label="考勤组名称" align="center" prop="attendanceName" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="拍照设置" align="center" prop="photoSet" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="班次类型" align="center" prop="classType" :formatter="classTypeFormat" />
      <el-table-column label="班次" align="center" prop="classes" :formatter="classesFormat" />
      <el-table-column label="备注" align="center" prop="remark" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:group:edit']"
          >修改</el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:group:remove']"
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

    <!-- 添加或修改考勤组管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="考勤组名称" prop="attendanceName">
          <el-input v-model="form.attendanceName" placeholder="请输入考勤组名称" />
        </el-form-item>
        <el-form-item label="成员" prop="attendancePeople">
          <el-select v-model="form.attendancePeople" placeholder="请选择考勤人员" @focus="getUsers"/>
        </el-form-item>
        <el-form-item label="日历" prop="calendarId">
          <el-input v-model="form.calendarId" placeholder="请输入日历" />
        </el-form-item>
        <el-form-item label="定位地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入定位地址" />
        </el-form-item>
        <el-form-item label="定位误差" prop="addError">
          <el-select v-model="form.addError" filterable>
            <el-option
              v-for="item in addErrorOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拍照设置" prop="photoSet">
          <el-select v-model="form.photoSet" filterable>
            <el-option
              v-for="item in PhotoSetOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班次类型">
          <el-select v-model="form.classType" placeholder="请选择班次类型">
            <el-option
              v-for="dict in classTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="userTitle" :visible.sync="userOpen" width="600px">
      <el-transfer
        v-model="form.attendanceName"
        :props="{
          key: 'value',
          label: 'desc'
         }"
        :data="Users">
      </el-transfer>
    </el-dialog>
  </div>
</template>

<script>
  import { listGroup, getGroup, delGroup, addGroup, updateGroup, exportGroup } from "@/api/base/group";
  import { userSelect} from "@/api/system/user";


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
      // 考勤组管理表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //弹出层标题
      userTitle: "",
      // 是否人员显示弹出层
      userOpen: false,
      // 班次类型字典
      classTypeOptions: [],
      // 班次字典
      classesOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: undefined,
        isAsc: undefined,
        attendanceName: undefined,
        classType: undefined,
        classes: undefined,
      },
      //选择框枚举
      addErrorOptions: [{
        value: '无限制',
        label: '无限制'
      }, {
        value: '500',
        label: '500米'
      }, {
        value: '1000',
        label: '1000米'
      }, {
        value: '1500',
        label: '1500米'
      }, {
        value: '2000',
        label: '2000米'
      }],
      PhotoSetOptions: [{
        value: '必须拍照',
        label: '必须拍照'
      }, {
        value: '可选拍照',
        label: '可选拍照'
      }, {
        value: '定位异常拍照',
        label: '定位异常拍照'
      }],
      //用户
      Users:[],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        attendanceName: [
          { required: true, message: "考勤组名称不能为空", trigger: "blur" }
        ],
        attendancePeople: [
          { required: true, message: "成员不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_class_type").then(response => {
      this.classTypeOptions = response.data;
    });
    this.getDicts("sys_classes").then(response => {
      this.classesOptions = response.data;
    });
  },
  methods: {
      /** 通用考勤组管理排序 */
      tableSortChange(column) {
          this.queryParams.pageNum = 1;
          if (column.order === 'descending') {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'desc';
          } else {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'asc';
          }
          this.getList();
      },
    /** 查询人员列表 */
    getUsers() {
      this.loading = true;
      userSelect().then(response => {
        console.log(response.data);
        this.userOpen = true;
        this.userTitle = "选择考勤人员";
        this.loading = false;
      });
    },
    /** 查询考勤组管理列表 */
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then(response => {
        this.groupList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 班次类型字典翻译
    classTypeFormat(row, column) {
      return this.selectDictLabel(this.classTypeOptions, row.classType);
    },
    // 班次字典翻译
    classesFormat(row, column) {
      return this.selectDictLabel(this.classesOptions, row.classes);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        attendanceName: undefined,
        attendancePeople: undefined,
        calendarId: undefined,
        address: undefined,
        addError: '无限制',
        photoSet: '必须拍照',
        classType: undefined,
        classes: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        remark: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加考勤组管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGroup(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改考勤组管理";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addGroup(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除考勤组管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGroup(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有考勤组管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGroup(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
