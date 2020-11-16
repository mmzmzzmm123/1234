<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
      clearable
      size="small"
    >
      <el-form-item label="班级信息" prop="classInfo">
        <el-select
          v-model="queryParams.classInfo"
          placeholder="请选择班级信息"
          @change="classInfoChange"
        >
          <el-option
            v-for="dict in classInfoOptions"
            :key="dict.bjbh"
            :label="dict.bjmc"
            :value="dict.bjbh"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="幼儿姓名" prop="childId">
        <el-select v-model="queryParams.childId" placeholder="请选择幼儿">
          <el-option
            v-for="dict in childInfoOptions.filter(
              c => c.classid == this.queryParams.classInfo
            )"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="检查日期" prop="checkTime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.checkTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择检查日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="保健师" prop="doctorName">
        <el-input
          v-model="queryParams.doctorName"
          placeholder="请输入保健师"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总评价" prop="totalAssessment">
        <el-select
          v-model="queryParams.totalAssessment"
          placeholder="请选择总评价"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in totalAssessmentOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="创建时间" prop="createtime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.createtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间"
        ></el-date-picker>
      </el-form-item>-->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:healthcheck:add']"
        >新增</el-button
      >
      <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:healthcheck:edit']"
          >修改</el-button
        >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:healthcheck:remove']"
        >删除</el-button
      >
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:healthcheck:export']"
          >导出</el-button
        >
      </el-col> -->
    </div>

    <el-table
      v-loading="loading"
      :data="healthcheckList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="体检表编号" align="center" prop="id" /> -->
      <el-table-column label="班级信息" align="center" prop="byClass.bjmc" />
      <el-table-column label="幼儿姓名" align="center" prop="byChild.name" />
      <el-table-column
        label="检查日期"
        align="center"
        prop="checkTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="保健师" align="center" prop="doctorName" />
      <el-table-column
        label="视力评价"
        align="center"
        prop="visionAssessment"
        :formatter="visionAssessmentFormat"
      />
      <el-table-column
        label="体重评价"
        align="center"
        prop="weightAssessment"
        :formatter="weightAssessmentFormat"
      />
      <el-table-column
        label="身高评价"
        align="center"
        prop="heightAssessment"
        :formatter="heightAssessmentFormat"
      />
      <el-table-column
        label="总评价"
        align="center"
        prop="totalAssessment"
        :formatter="totalAssessmentFormat"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.createtime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="100"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:healthcheck:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:healthcheck:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改儿童常规体检记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="big-dialog" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-col :span="12">
            <el-form-item label="班级信息" prop="classInfo">
              <el-select
                v-model="form.classInfo"
                placeholder="请选择班级信息"
                @change="classInfoChange"
              >
                <el-option
                  v-for="dict in classInfoOptions"
                  :key="dict.bjbh"
                  :label="dict.bjmc"
                  :value="dict.bjbh"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿姓名" prop="childId">
              <el-select v-model="form.childId" placeholder="请选择幼儿">
                <el-option
                  v-for="dict in childInfoOptions.filter(
                    c => c.classid == this.form.classInfo
                  )"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否龋齿">
              <el-select
                v-model="form.decayedTooth"
                placeholder="请选择是否龋齿"
              >
                <el-option
                  v-for="dict in decayedToothOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="视力评价">
              <el-select
                v-model="form.visionAssessment"
                placeholder="请选择视力评价"
              >
                <el-option
                  v-for="dict in visionAssessmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="左眼视力" prop="eyesVisionLeft">
              <el-input
                v-model="form.eyesVisionLeft"
                placeholder="请输入左眼视力"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="右眼视力" prop="eyesVisionRight">
              <el-input
                v-model="form.eyesVisionRight"
                placeholder="请输入右眼视力"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体重(kg)" prop="weight">
              <el-input
                v-model="form.weight"
                placeholder="请输入体重"
                maxlength="6"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体重评价">
              <el-select
                v-model="form.weightAssessment"
                placeholder="请选择体重评价"
              >
                <el-option
                  v-for="dict in weightAssessmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="身高(cm)" prop="height">
              <el-input
                v-model="form.height"
                placeholder="请输入身高"
                maxlength="3"
                oninput="value=value.replace(/[^\d]/g,'')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身高评价">
              <el-select
                v-model="form.heightAssessment"
                placeholder="请选择身高评价"
              >
                <el-option
                  v-for="dict in heightAssessmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总评价">
              <el-select
                v-model="form.totalAssessment"
                placeholder="请选择总评价"
              >
                <el-option
                  v-for="dict in totalAssessmentOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="保健师" prop="doctorName">
              <el-input v-model="form.doctorName" placeholder="请输入保健师" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="检查日期" prop="checkTime">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.checkTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择检查日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listHealthcheck,
  getHealthcheck,
  delHealthcheck,
  addHealthcheck,
  updateHealthcheck,
  exportHealthcheck
} from "@/api/benyi/healthcheck";
import { listClass } from "@/api/system/class";
import { listChild } from "@/api/benyi/child";

export default {
  name: "Healthcheck",
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
      // 儿童常规体检记录表格数据
      healthcheckList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //班级类型 字典
      classInfoOptions: [],
      // 班级下幼儿列表
      childInfoOptions: [],
      // 视力评价字典
      visionAssessmentOptions: [],
      // 是否龋齿字典
      decayedToothOptions: [],
      // 体重评价字典
      weightAssessmentOptions: [],
      // 身高评价字典
      heightAssessmentOptions: [],
      // 总评价字典
      totalAssessmentOptions: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        childId: undefined,
        classInfo: undefined,
        schoolName: undefined,
        checkTime: undefined,
        doctorName: undefined,
        eyesVisionLeft: undefined,
        eyesVisionRight: undefined,
        visionAssessment: undefined,
        decayedTooth: undefined,
        weight: undefined,
        weightAssessment: undefined,
        height: undefined,
        heightAssessment: undefined,
        totalAssessment: undefined,
        createtime: undefined,
        createuser: undefined
      },
      // 表单参数
      form: {},

      // 表单校验
      rules: {
        classInfo: [
          { required: true, message: "班级信息不能为空", trigger: "blur" }
        ],
        childId: [
          { required: true, message: "幼儿姓名不能为空", trigger: "blur" }
        ],
        checkTime: [
          { required: true, message: "检查时间不能为空", trigger: "blur" }
        ],
        doctorName: [
          { required: true, message: "保健医不能为空", trigger: "blur" }
        ],
        totalAssessment: [
          { required: true, message: "总评价不能为空", trigger: "blur" }
        ],
        weight: [
          {
            required: true,
            message: "输入内容必须是数字,且不能超过六位数",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getclassinfo();

    this.getDicts("sys_vision_assessment").then(response => {
      this.visionAssessmentOptions = response.data;
    });
    this.getDicts("sys_decayed_tooth").then(response => {
      this.decayedToothOptions = response.data;
    });
    this.getDicts("sys_weight_assessment").then(response => {
      this.weightAssessmentOptions = response.data;
    });
    this.getDicts("sys_height_assessment").then(response => {
      this.heightAssessmentOptions = response.data;
    });
    this.getDicts("sys_dm_tyjg").then(response => {
      this.totalAssessmentOptions = response.data;
    });
  },
  methods: {
    // 班级选择框变化后触发
    classInfoChange() {
      this.queryParams.childId = null;
      listChild(null).then(response => {
        this.childInfoOptions = response.rows;
      });
    },
    /** 查询儿童常规体检记录列表 */
    getList() {
      this.loading = true;
      listHealthcheck(this.queryParams).then(response => {
        this.healthcheckList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询所有班级信息
    getclassinfo() {
      listClass(null).then(response => {
        this.classInfoOptions = response.rows;
      });
    },
    // 视力评价字典翻译
    visionAssessmentFormat(row, column) {
      return this.selectDictLabel(
        this.visionAssessmentOptions,
        row.visionAssessment
      );
    },
    // 是否龋齿字典翻译
    decayedToothFormat(row, column) {
      return this.selectDictLabel(this.decayedToothOptions, row.decayedTooth);
    },
    // 体重评价字典翻译
    weightAssessmentFormat(row, column) {
      return this.selectDictLabel(
        this.weightAssessmentOptions,
        row.weightAssessment
      );
    },
    // 身高评价字典翻译
    heightAssessmentFormat(row, column) {
      return this.selectDictLabel(
        this.heightAssessmentOptions,
        row.heightAssessment
      );
    },
    // 总评价字典翻译
    totalAssessmentFormat(row, column) {
      return this.selectDictLabel(
        this.totalAssessmentOptions,
        row.totalAssessment
      );
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
        childId: undefined,
        classInfo: undefined,
        schoolName: undefined,
        checkTime: undefined,
        doctorName: undefined,
        eyesVisionLeft: undefined,
        eyesVisionRight: undefined,
        visionAssessment: undefined,
        decayedTooth: undefined,
        weight: undefined,
        weightAssessment: undefined,
        height: undefined,
        heightAssessment: undefined,
        totalAssessment: undefined,
        createtime: undefined,
        createuser: undefined
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加儿童常规体检记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getHealthcheck(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改儿童常规体检记录";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateHealthcheck(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addHealthcheck(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm("是否确认删除儿童常规体检记录数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delHealthcheck(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    }
    // /** 导出按钮操作 */
    // handleExport() {
    //   const queryParams = this.queryParams;
    //   this.$confirm("是否确认导出所有儿童常规体检记录数据项?", "警告", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(function () {
    //       return exportHealthcheck(queryParams);
    //     })
    //     .then((response) => {
    //       this.download(response.msg);
    //     })
    //     .catch(function () {});
    // },
  }
};
</script>
