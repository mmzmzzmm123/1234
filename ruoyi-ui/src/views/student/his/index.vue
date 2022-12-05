<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学院名称" prop="deptId">
        <el-select v-model="queryParams.deptId" placeholder="请选择学院名称" clearable>
          <el-option
            v-for="dept in deptList"
            :key="dept.deptId"
            :label="dept.deptName"
            :value="dept.deptId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="历史状态" prop="hisState">
        <el-select v-model="queryParams.hisState" placeholder="请选择历史状态" clearable>
          <el-option
            v-for="dict in dict.type.student_tag"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="历史状态日期" prop="hisStateDate">
        <el-date-picker clearable
          v-model="queryParams.hisStateDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择历史状态日期">
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
          v-hasPermi="['student:his:add']"
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
          v-hasPermi="['student:his:edit']"
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
          v-hasPermi="['student:his:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['student:his:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hisList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学号" align="center" prop="studentId" />
      <el-table-column label="姓名" align="center" prop="studentName" />
      <el-table-column label="历史状态" align="center" prop="hisState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.student_tag" :value="scope.row.hisState"/>
        </template>
      </el-table-column>
      <el-table-column label="历史状态日期" align="center" prop="hisStateDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hisStateDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="studentSex" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.studentSex"/>
        </template>
      </el-table-column>
      <el-table-column label="学院名称" align="center" prop="deptName" />
      <el-table-column label="联系方式" align="center" prop="studentPhone" />
      <el-table-column label="身份证号" align="center" prop="idNum" />
      <el-table-column label="培养层次" align="center" prop="trainingLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.training_level" :value="scope.row.trainingLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="年级" align="center" prop="grade"/>
      <el-table-column label="住址" align="center" prop="address" />
      <el-table-column label="民族" align="center" prop="nation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.nation" :value="scope.row.nation"/>
        </template>
      </el-table-column>
      <el-table-column label="校区" align="center" prop="campus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.campus" :value="scope.row.campus"/>
        </template>
      </el-table-column>
      <el-table-column label="在校状态" align="center" prop="studentTag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.student_tag" :value="scope.row.studentTag"/>
        </template>
      </el-table-column>
      <el-table-column label="住宿地点" align="center" prop="accommodation">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.accommodation" :value="scope.row.accommodation"/>
        </template>
      </el-table-column>
      <el-table-column label="住宿园区" align="center" prop="accommodationPark">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.accommodation_park" :value="scope.row.accommodationPark"/>
        </template>
      </el-table-column>
      <el-table-column label="宿舍号" align="center" prop="dormitoryNo" />
      <el-table-column label="是否校内住宿" align="center" prop="isOnSchool">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isOnSchool"/>
        </template>
      </el-table-column>
      <el-table-column label="管控等级" align="center" prop="controlLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.control_level" :value="scope.row.controlLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="未返校原因" align="center" prop="notSchoolReason">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.not_school_reason" :value="scope.row.notSchoolReason"/>
        </template>
      </el-table-column>
      <el-table-column label="返校出发地风险等级" align="center" prop="placeToSchoolLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.risk_level" :value="scope.row.placeToSchoolLevel"/>
        </template>
      </el-table-column>
      <el-table-column label="返校出发地" align="center" prop="placeToSchool">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.place_to_school" :value="scope.row.placeToSchool"/>
        </template>
      </el-table-column>
      <el-table-column label="离校目的地" align="center" prop="placeToLeave">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.place_to_leave" :value="scope.row.placeToLeave"/>
        </template>
      </el-table-column>
      <el-table-column label="密接类型" align="center" prop="jointType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.joint_type" :value="scope.row.jointType"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['student:his:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['student:his:remove']"
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

    <!-- 添加或修改学生历史状态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="历史状态" prop="hisState">
          <el-select v-model="form.hisState" placeholder="请选择历史状态">
            <el-option
              v-for="dict in dict.type.student_tag"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="历史状态日期" prop="hisStateDate">
          <el-date-picker clearable
            v-model="form.hisStateDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择历史状态日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listHis, getHis, delHis, addHis, updateHis } from "@/api/student/his";
import { listDept } from "@/api/system/dept";

export default {
  name: "His",
  dicts: ['training_level', 'campus', 'control_level', 'place_to_school', 'sys_yes_no', 'risk_level', 'nation', 'accommodation_park', 'accommodation', 'not_school_reason', 'student_tag', 'joint_type', 'place_to_leave','sys_user_sex'],
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
      // 学生历史状态表格数据
      hisList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: null,
        deptId: null,
        deptName: null,
        hisState: null,
        hisStateDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      // 二级部门列表
      deptList: []
    };
  },
  created() {
    this.getList();
    this.getDeptList();
  },
  methods: {
    /** 查询学生历史状态列表 */
    getList() {
      this.loading = true;
      listHis(this.queryParams).then(response => {
        this.hisList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getDeptList() {
      listDept({"parentId": 100}).then(response => {
        this.deptList = response.data;
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
        studentId: null,
        hisState: null,
        hisStateDate: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.title = "添加学生历史状态";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHis(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生历史状态";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHis(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHis(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除学生历史状态编号为"' + ids + '"的数据项？').then(function() {
        return delHis(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('student/his/export', {
        ...this.queryParams
      }, `his_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
