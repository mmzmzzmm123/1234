<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="是否划分学院" prop="divideCollege">
        <el-select v-model="queryParams.divideCollege" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="校区" prop="campus">
        <el-select v-model="queryParams.campus" placeholder="请选择校区" clearable>
          <el-option
            v-for="dict in dict.type.campus"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column label="学院名称" align="center" prop="deptName" />
      <el-table-column label="学生在册总人数" align="center" prop="stuAcc" />
      <el-table-column label="当日省内返校学生" align="center" prop="inProReturnAcc" />
      <el-table-column label="当日省外返校学生" align="center" prop="outProReturnAcc" />
      <el-table-column label="省内累计学生" align="center" prop="inProAcc" />
      <el-table-column label="省外累计学生" align="center" prop="outProAcc" />
      <el-table-column label="高风险返回学生数" align="center" prop="highLevReturnAcc" />
      <el-table-column align="center" label="实际在校学生人数">
      	<el-table-column label="教学区宿舍区" align="center" prop="dormitoryTeachingAreaAcc" />
      	<el-table-column label="教学区其他地方" align="center" prop="dormitoryOtherAreaAcc" />
      	<el-table-column label="家属区" align="center" prop="familyAreaAcc" />
      	<el-table-column label="走读生" align="center" prop="dayStudentAcc" />
      	<el-table-column label="小计" align="center" prop="inSchoolAcc" />
      </el-table-column>

      <el-table-column label="B类密接" align="center" prop="typeBAcc" />

      <el-table-column align="center" label="当日请假离校人数（不返校）">
      <el-table-column label="市内" align="center" prop="leaveInCityAcc" />
      <el-table-column label="省内市外" align="center" prop="leaveOutCityInProAcc" />
      <el-table-column label="省外" align="center" prop="leaveOutProAcc" />
      <el-table-column label="小计" align="center" prop="leaveTodayAcc" />
      </el-table-column>

      <el-table-column align="center" label="未返校学生人数">
      <el-table-column label="因疫暂缓返校人数" align="center" prop="epidemicSusAcc" />
      <el-table-column label="因就业实习暂缓返校人数" align="center" prop="practiceSusAcc" />
      <el-table-column label="因其他暂缓返校人数" align="center" prop="otherSusAcc" />
      <el-table-column label="小计" align="center" prop="susAcc" />
      </el-table-column>

      <el-table-column align="center" label="重点人员管控">
      <el-table-column label="校外实际集中隔离人数" align="center" prop="outSchCentIsolationAcc" />
      <el-table-column label="校内实际居家隔离人数" align="center" prop="outSchHomeIsolationAcc" />
      <el-table-column label="校内实际居家健康监测人数" align="center" prop="inSchHomeIsolationAcc" />
      </el-table-column>


    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
  import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/student/account";
  import { listDept } from "@/api/system/dept";

  export default {
    name: "Info",
    dicts: ['sys_yes_no', 'campus'],
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
        // 学生信息表格数据
        infoList: [],
        // 二级部门列表
        deptList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          studentId: null,
          studentName: null,
          deptId: null,
          deptName: null,
          trainingLevel: null,
          grade: null,
          address: null,
          nation: null,
          campus: null,
          studentTag: null,
          accommodation: null,
          accommodationPark: null,
          dormitoryNo: null,
          isOnSchool: null,
          controlLevel: null,
          notSchoolReason: null,
          placeToSchoolLevel: null,
          placeToSchool: null,
          divideCollege: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          studentId: [
            { required: true, message: "学号不能为空", trigger: "blur" }
          ],
          studentName: [
            { required: true, message: "姓名不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询学生信息列表 */
      getList() {
        this.loading = true;
        listInfo(this.queryParams).then(response => {
          this.infoList = response.rows;
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
          campus: null,
          divideCollege: null
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
        this.title = "添加学生信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getInfo(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改学生信息";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateInfo(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addInfo(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除学生信息编号为"' + ids + '"的数据项？').then(function() {
          return delInfo(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('student/account/export', {
          ...this.queryParams
        }, `info_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
