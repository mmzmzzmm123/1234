<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="案例信息" prop="name">
        <el-input
          v-model.trim="queryParams.name"
          placeholder="请输入案例名称或关键字"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="客户姓名" prop="customerName">
        <el-input
          v-model.trim="queryParams.customerName"
          placeholder="请输入客户姓名"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['custom:customerCase:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:customerCase:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:customerCase:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:customerCase:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerCaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="案例名称" align="center" prop="name" >
        <template slot-scope="scope">
          <AutoHideMessage :data="scope.row.name" :maxLength="10"></AutoHideMessage>
        </template>
      </el-table-column>
      <el-table-column label="关键词" align="center" prop="keyword" >
        <template slot-scope="scope">
          <AutoHideMessage :data="scope.row.keyword" :maxLength="10"></AutoHideMessage>
          <!--<AutoHideInfo :data="scope.row.keyword.split(',')" :line="1"></AutoHideInfo>-->
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" >
        <template slot-scope="scope">
          <AutoHideMessage :data="scope.row.remark" :maxLength="10"></AutoHideMessage>
        </template>
      </el-table-column>
      <el-table-column label="所属客户" align="center" prop="customerName" />
      <el-table-column
        label="文件"
        align="center"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="getFileListByCaseId(scope.row)"
          >文件列表
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="160"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.createTime, "{y}-{m}-{d} {h}:{i}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:customerCase:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:customerCase:remove']"
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

    <!-- 添加或修改客户案例管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" @closed="cancel" width="520px" append-to-body>
      <div style="height: 600px; overflow: auto; padding-right: 20px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="案例名称" prop="name">
          <el-input v-model.trim="form.name" type="textarea" maxlength="50" rows = "1" show-word-limit  placeholder="请输入案例名称" />
        </el-form-item>
        <el-form-item label="关键词" prop="keywordArray">
          <el-select
            v-model="form.keywordArray"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请创建案例关键词，按回车创建，最多20个" style="width: 100%;">
          </el-select>
        </el-form-item>
        <el-form-item label="案例备注" prop="remark">
          <el-input
            type="textarea"
            placeholder="请输入案例备注"
            v-model.trim="form.remark"
            maxlength="200"
            rows = "4"
            show-word-limit
          ></el-input>
        </el-form-item>

        <el-form-item label="所属客户" prop="customerId">
          <el-input v-model="form.customerName" placeholder="" style="width: 60%" :readonly="true" />
          <span style="margin-left: 10px"> <el-button type="primary" @click="selectCustomer">选择所属客户</el-button></span>
        </el-form-item>
        <el-form-item label="案例文件" prop="file" >
          <DragUpload v-if="form.id == null || form.id <= 0" @callbackMethod="addOrEditCustomerCase" ref="uploadCaseFile"></DragUpload>
          <DragUploadEdit v-else @callbackMethod="addOrEditCustomerCase" :caseFileList="form.caseFileList" ref="editUploadCaseFile"></DragUploadEdit>
        </el-form-item>
      </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <MuchFileDown ref="muchFileDownRef"></MuchFileDown>
    <SelectCustomer @dealCustomerId="dealCustomerId" ref="selectCustomerRef"></SelectCustomer>
  </div>
</template>

<script>
  import { listCustomerCase, getCustomerCase, delCustomerCase, addCustomerCase, updateCustomerCase, exportCustomerCase,getFileListByCaseId,downCaseFile } from "@/api/custom/customerCase";
  import DragUpload from "@/components/FileUpload/DragUpload";
  import DragUploadEdit from "@/components/FileUpload/DragUploadEdit";
  import SelectCustomer from "@/components/Customer/SelectCustomer";
  import MuchFileDown from "@/components/FileDownload/MuchFileDown";
  import AutoHideMessage from "@/components/AutoHideMessage";
  import AutoHideInfo from "@/components/AutoHideInfo";

  export default {
    name: "CustomerCase",
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
        // 客户案例管理表格数据
        customerCaseList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          customerName: null
        },
        // 表单参数
        form: {

        },
        // 表单校验
        rules: {
          name: [
            { required: true, message: "案例名称不能为空", trigger: "blur" },
          ],
          keywordArray: [
            { required: true, message: "案例关键词不能为空", trigger: "blur" },
          ],
        },
        submitFlag: false
      };
    },
    components: {
      "DragUpload": DragUpload,
      "SelectCustomer":SelectCustomer,
      "MuchFileDown": MuchFileDown,
      "AutoHideMessage": AutoHideMessage,
      "AutoHideInfo":AutoHideInfo,
      "DragUploadEdit":DragUploadEdit
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询客户案例管理列表 */
      getList() {
        this.loading = true;
        listCustomerCase(this.queryParams).then(response => {
          this.customerCaseList = response.rows;
          this.total = response.total;
          this.loading = false;
      });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        //this.$refs["uploadCaseFile"].uploadReset();
        //this.$refs["editUploadCaseFile"].uploadReset();
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          name: null,
          keywordArray: [],
          remark: null,
          customerId: null,
          customerName: null,
          caseFileList:[],
          caseFileUrl: [],
          caseFileName: []
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
        this.title = "添加客户案例管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getCustomerCase(id).then(response => {
          this.form = {
            id: response.data.id,
            name: response.data.name,
            keywordArray: response.data.keyword.split(","),
            remark: response.data.remark,
            customerId: response.data.customerId,
            customerName: response.data.customerName,
            caseFileList: response.data.caseFileList,
            caseFileUrl: [],
            caseFileName: []
          };
          this.open = true;
          this.title = "修改客户案例管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
              if(this.submitFlag){
                  this.$message({
                      message: "正在上传提交中，请勿重复提交",
                      type: "warning",
                  });
                  return;
              }
              //判断案例关键词
              if(this.form.keywordArray.length > 20){
                this.$message({message: "案例关键词最多20个", type: "warning"});
                return;
              }
              this.submitFlag = true;
              this.form.keyword = this.form.keywordArray.join(",");
              if (this.form.id != null) {
                this.$refs["editUploadCaseFile"].uploadFile();
              } else {
                this.$refs["uploadCaseFile"].uploadFile();
              }
          }
        });
      },
      addOrEditCustomerCase(fileResult){
        this.form.caseFileName = fileResult.fileName;
        this.form.caseFileUrl = fileResult.fileUrl;
        if(this.form.caseFileUrl.length == 0){
          this.$message.error('请至少选择一个文件上传');
          this.submitFlag = false;
          return;
        }
        if(this.form.id != null){
          console.log(this.form.caseFileName.length);
          updateCustomerCase(this.form).then(response => {
            if (response.code === 200) {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }
            this.submitFlag = false;
          });
        }else{
          addCustomerCase(this.form).then(response => {
            if (response.code === 200) {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }
            this.submitFlag = false;
          });
        }
      },
      selectCustomer(){
        this.$refs['selectCustomerRef'].showDialog("选择案例所属客户");
      },
      dealCustomerId(customerId, customerName){
         this.form.customerId = customerId;
         this.form.customerName = customerName;
      },
      getFileListByCaseId(customerCase){
        getFileListByCaseId(customerCase.id).then(response => {
          if (response.code === 200) {
              this.$refs["muchFileDownRef"].showDialog(null, response.rows);
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除客户案例管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCustomerCase(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有客户案例管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCustomerCase(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      }
    }
  };
</script>
