<template>
  <div class="app-container">
    <el-form :model="queryParams"
             ref="queryForm"
             :inline="true"
             v-show="showSearch"
             label-width="68px">
      <el-form-item label="区域分类"
                    prop="areaCategory">
        <el-input v-model="queryParams.areaCategory"
                  placeholder="请输入区域分类"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="标准名称"
                    prop="standardName">
        <el-input v-model="queryParams.standardName"
                  placeholder="请输入标准名称"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="标准类型"
                    prop="standardCategory">
        <el-select v-model="queryParams.standardCategory"
                   placeholder="请选择标准类型"
                   clearable
                   size="small">
          <el-option v-for="dict in dict.type.pro_standard_category"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="实施日期"
                    prop="standardBeginDate">
        <el-date-picker clearable
                        size="small"
                        v-model="queryParams.standardBeginDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择实施日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="标准状态"
                    prop="standardStatus">
        <el-select v-model="queryParams.standardStatus"
                   placeholder="请选择标准状态"
                   clearable
                   size="small">
          <el-option v-for="dict in dict.type.pro_standard_status"
                     :key="dict.value"
                     :label="dict.label"
                     :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"
                   icon="el-icon-search"
                   size="mini"
                   @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh"
                   size="mini"
                   @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10"
            class="mb8">
      <el-col :span="1.5">
        <el-button type="primary"
                   plain
                   icon="el-icon-plus"
                   size="mini"
                   @click="handleAdd"
                   v-hasPermi="['productionManager:standard:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success"
                   plain
                   icon="el-icon-edit"
                   size="mini"
                   :disabled="single"
                   @click="handleUpdate"
                   v-hasPermi="['productionManager:standard:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger"
                   plain
                   icon="el-icon-delete"
                   size="mini"
                   :disabled="multiple"
                   @click="handleDelete"
                   v-hasPermi="['productionManager:standard:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning"
                   plain
                   icon="el-icon-download"
                   size="mini"
                   :loading="exportLoading"
                   @click="handleExport"
                   v-hasPermi="['productionManager:standard:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch"
                     @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading"
              :data="standardList"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection"
                       width="55"
                       align="center" />
      <el-table-column label="标准id"
                       align="center"
                       prop="standardId"
                       v-if="false" />
      <el-table-column label="区域分类"
                       align="center"
                       prop="areaCategory" />
      <el-table-column label="标准名称"
                       align="center"
                       prop="standardName" />
      <el-table-column label="标准类型"
                       align="center"
                       prop="standardCategory">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pro_standard_category"
                    :value="scope.row.standardCategory" />
        </template>
      </el-table-column>
      <el-table-column label="实施日期"
                       align="center"
                       prop="standardBeginDate"
                       width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.standardBeginDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标准状态"
                       align="center"
                       prop="standardStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pro_standard_status"
                    :value="scope.row.standardStatus" />
        </template>
      </el-table-column>
      <el-table-column label="备注"
                       align="center"
                       prop="remark" />
      <el-table-column label="操作"
                       align="center"
                       class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['productionManager:standard:edit']">修改</el-button>
          <el-button size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['productionManager:standard:remove']">删除</el-button>
          <el-button size="mini"
                     type="text"
                     icon="el-icon-download"
                     @click="downloadFile(scope.row)"
                     v-hasPermi="['productionManager:standard:remove']">下载</el-button>
          <el-dropdown size="mini"
                       @command="(command) => handleCommand(command, scope.row)"
                       v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <!-- <el-dropdown-item command="handleResetPwd"
                                icon="el-icon-key"
                                v-hasPermi="['system:user:resetPwd']">重置密码</el-dropdown-item>
              <el-dropdown-item command="handleAuthRole"
                                icon="el-icon-circle-check"
                                v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item> -->
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <!-- 添加或修改试验标准管理对话框 -->
    <el-dialog :title="title"
               :visible.sync="open"
               width="500px"
               append-to-body>
      <el-form ref="form"
               :model="form"
               :rules="rules"
               label-width="80px">
        <el-form-item label="区域分类"
                      prop="areaCategory">
          <el-input v-model="form.areaCategory"
                    placeholder="请输入区域分类" />
        </el-form-item>
        <el-form-item label="标准名称"
                      prop="standardName">
          <el-input v-model="form.standardName"
                    placeholder="请输入标准名称" />
        </el-form-item>
        <!-- <el-form-item label="PDF文件"
                      prop="pdfFiles">
        </el-form-item> -->

        <el-form-item label="上传"
                      prop="path">
          <el-input v-model="form.path"
                    placeholder="请选择上传文件"
                    :disabled="true" />
          <!-- accept=".jpg, .png" -->
          <el-upload ref="upload"
                     :limit="1"
                     :action="upload.url"
                     :headers="upload.headers"
                     :file-list="upload.fileList"
                     :on-progress="handleFileUploadProgress"
                     :on-success="handleFileSuccess"
                     :auto-upload="false">
            <el-button slot="trigger"
                       size="small"
                       type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;"
                       size="small"
                       type="success"
                       :loading="upload.isUploading"
                       @click="submitUpload">上传到服务器</el-button>
            <!-- <div slot="tip"
                 class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
          </el-upload>
        </el-form-item>
        <!-- <el-form-item size="large">
            <el-button type="primary"
                       @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item> -->

        <el-form-item label="标准类型">
          <el-radio-group v-model="form.standardCategory">
            <el-radio v-for="dict in dict.type.pro_standard_category"
                      :key="dict.value"
                      :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="实施日期"
                      prop="standardBeginDate">
          <el-date-picker clearable
                          size="small"
                          v-model="form.standardBeginDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择实施日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="标准状态">
          <el-radio-group v-model="form.standardStatus">
            <el-radio v-for="dict in dict.type.pro_standard_status"
                      :key="dict.value"
                      :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注"
                      prop="remark">
          <el-input v-model="form.remark"
                    type="textarea"
                    placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer"
           class="dialog-footer">
        <el-button type="primary"
                   @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStandard, getStandard, delStandard, addStandard, updateStandard, exportStandard } from "@/api/productionManager/standard";
import { getToken } from "@/utils/auth";

export default {
  // pdf文件导入参数

  //

  name: "Standard",
  dicts: ['pro_standard_category', 'pro_standard_status'],
  data () {
    var validatorPath = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请上传文件'));
      } else {
        // if (this.form.path !== '') {
        //   this.$refs.ruleForm.validateField('checkPass');
        // }
        callback();
      }
    };
    return {
      //导入相关参数 process.env.VUE_APP_BASE_API + "/productionManager/standard/upload"
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/productionManager/standard/upload",
        // 上传的文件列表
        fileList: [],
        filePath: ""

      },
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 试验标准管理表格数据
      standardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        areaCategory: null,
        standardName: null,
        standardCategory: null,
        standardBeginDate: null,
        standardStatus: null,

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        areaCategory: [
          { required: true, message: "区域分类不能为空", trigger: "blur" }
        ],
        standardName: [
          { required: true, message: "标准名称不能为空", trigger: "blur" }
        ],
        standardCategory: [
          { required: true, message: "标准类型不能为空", trigger: "blur" }
        ],
        standardStatus: [
          { required: true, message: "标准状态不能为空", trigger: "blur" }
        ],
        // path: [
        //   { required: true, message: "文件不能为空", trigger: ["blur"] }
        // ],
        // path: [
        //   { required: true, validator: validatorPath, trigger: ["blur", "change"] }
        // ],
      }
    };
  },
  created () {
    this.getList();
  },
  methods: {
    // pdf导入相关方法
    // submitForm () {
    //   this.$refs['elForm'].validate(valid => {
    //     if (!valid) return
    //     // TODO 提交表单
    //   })
    // },
    // resetForm () {
    //   this.$refs['elForm'].resetFields()
    // },


    pdffileBeforeUpload (file) {
      let isRightSize = file.size / 1024 / 1024 < 20
      if (!isRightSize) {
        this.$message.error('文件大小超过 20MB')
      }
      let isAccept = new RegExp('.pdf').test(file.type)
      if (!isAccept) {
        this.$message.error('应该选择.pdf类型的文件')
      }
      return isRightSize && isAccept
    },
    // 文件提交处理
    submitUpload () {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress (event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess (response, file, fileList) {
      this.upload.isUploading = false;
      this.upload.filePath = response.url;
      console.log("filepath>>>" + this.upload.filePath)
      this.form.path = response.url;
      this.$modal.msgSuccess(response.msg);
      // this.$nextTick(() => this.$refs.form.clearValidate())
    },


    /** 查询试验标准管理列表 */
    getList () {
      this.loading = true;
      listStandard(this.queryParams).then(response => {
        this.standardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        standardId: null,
        areaCategory: null,
        standardName: null,
        standardCategory: "0",
        standardBeginDate: null,
        standardStatus: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.standardId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset();
      this.open = true;
      this.upload.fileList = [];
      this.title = "添加试验标准管理";
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset();
      const standardId = row.standardId || this.ids
      getStandard(standardId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改试验标准管理";
      });
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.standardId != null) {
            updateStandard(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStandard(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const standardIds = row.standardId || this.ids;
      this.$modal.confirm('是否确认删除试验标准管理编号为"' + standardIds + '"的数据项？').then(function () {
        return delStandard(standardIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport () {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有试验标准管理数据项？').then(() => {
        this.exportLoading = true;
        return exportStandard(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}
</style>
