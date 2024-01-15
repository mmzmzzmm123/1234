<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="公司编号" prop="companyNo">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.companyNo"-->
      <!--          placeholder="请输入公司编号"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="项目no" prop="projectNo">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.projectNo"-->
      <!--          placeholder="请输入项目no"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="章节名称 100-800章" prop="chapterName">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.chapterName"-->
      <!--          placeholder="请输入章节名称 100-800章"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="唯一编号" prop="uniqueNo">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.uniqueNo"-->
      <!--          placeholder="请输入唯一编号"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="子目号" prop="itemNum">
        <el-input
          v-model="queryParams.itemNum"
          placeholder="请输入子目号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="子目名称" prop="itemName">
        <el-input
          v-model="queryParams.itemName"
          placeholder="请输入子目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="是否混合材料" prop="mixture">
              <el-input
                v-model="queryParams.mixture"
                placeholder="请输入是否混合材料"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="单位名称" prop="unit">
              <el-input
                v-model="queryParams.unit"
                placeholder="请输入单位名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="原合同清单" prop="inventory">
              <el-input
                v-model="queryParams.inventory"
                placeholder="请输入原合同清单"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="单价" prop="unitPrice">
              <el-input
                v-model="queryParams.unitPrice"
                placeholder="请输入单价"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="合价" prop="combinedPrice">
              <el-input
                v-model="queryParams.combinedPrice"
                placeholder="请输入合价"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="0号台账数量" prop="zeroCount">
              <el-input
                v-model="queryParams.zeroCount"
                placeholder="请输入0号台账数量"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="0号台账合价" prop="zeroUnitPrice">
              <el-input
                v-model="queryParams.zeroUnitPrice"
                placeholder="请输入0号台账合价"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="0号台账合价" prop="zeroCombinedPrice">
              <el-input
                v-model="queryParams.zeroCombinedPrice"
                placeholder="请输入0号台账合价"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="创建人" prop="createUser">
              <el-input
                v-model="queryParams.createUser"
                placeholder="请输入创建人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="修改人" prop="updateUser">
              <el-input
                v-model="queryParams.updateUser"
                placeholder="请输入修改人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="是否删除" prop="deleted">
              <el-input
                v-model="queryParams.deleted"
                placeholder="请输入是否删除"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['base:bill:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['base:bill:add']"
        >新增
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['base:bill:edit']"
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
                v-hasPermi="['base:bill:remove']"
              >删除</el-button>
            </el-col>-->
      <!--      <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['base:bill:export']"
              >导出</el-button>
            </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="billList" @selection-change="handleSelectionChange">
      <el-table-column label="序号" align="center" prop="id"/>
      <!--      <el-table-column type="selection" width="55" align="center" />-->
      <!--      <el-table-column label="公司编号" align="center" prop="companyNo" />-->
      <!--      <el-table-column label="项目no" align="center" prop="projectNo" />-->
      <!--      <el-table-column label="章节名称 100-800章" align="center" prop="chapterName" />-->
      <!--      <el-table-column label="唯一编号" align="center" prop="uniqueNo" />-->
      <el-table-column label="子目号" align="center" prop="itemNum"/>
      <el-table-column label="子目名称" align="center" prop="itemName"/>
      <!--      <el-table-column label="是否混合材料" align="center" prop="mixture" />-->
      <el-table-column label="单位名称" align="center" prop="unit"/>
      <el-table-column label="原合同清单" align="center" prop="inventory"/>
      <el-table-column label="单价" align="center" prop="unitPrice"/>
      <el-table-column label="合价" align="center" prop="combinedPrice"/>
      <el-table-column label="0号台账数量" align="center" prop="zeroCount"/>
      <el-table-column label="0号台账合价" align="center" prop="zeroUnitPrice"/>
      <el-table-column label="0号台账合价" align="center" prop="zeroCombinedPrice"/>
      <!--      <el-table-column label="创建人" align="center" prop="createUser" />-->
      <!--      <el-table-column label="修改人" align="center" prop="updateUser" />-->
      <!--      <el-table-column label="是否删除" align="center" prop="deleted" />-->
      <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-edit"-->
      <!--            @click="handleUpdate(scope.row)"-->
      <!--            v-hasPermi="['base:bill:edit']"-->
      <!--          >修改</el-button>-->
      <!--          <el-button-->
      <!--            size="mini"-->
      <!--            type="text"-->
      <!--            icon="el-icon-delete"-->
      <!--            @click="handleDelete(scope.row)"-->
      <!--            v-hasPermi="['base:bill:remove']"-->
      <!--          >删除</el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <el-dialog :title="title" :visible.sync="importOpen" width="500px" append-to-body>
      <el-upload
        class="upload-file"
        drag
        action=""
        name="file"
        :file="file"
        :accept="'.xls,.xlsx'"
        :http-request="UploadFile"
        :limit="1"
        multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xls/xlsx文件，且不超过50Mb</div>
      </el-upload>
    </el-dialog>
    <!-- 添加或修改原数据---0号台账对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="公司编号" prop="companyNo">
          <el-input v-model="form.companyNo" placeholder="请输入公司编号"/>
        </el-form-item>
        <el-form-item label="项目no" prop="projectNo">
          <el-input v-model="form.projectNo" placeholder="请输入项目no"/>
        </el-form-item>
        <el-form-item label="章节名称 100-800章" prop="chapterName">
          <el-input v-model="form.chapterName" placeholder="请输入章节名称 100-800章"/>
        </el-form-item>
        <el-form-item label="唯一编号" prop="uniqueNo">
          <el-input v-model="form.uniqueNo" placeholder="请输入唯一编号"/>
        </el-form-item>
        <el-form-item label="子目号" prop="itemNum">
          <el-input v-model="form.itemNum" placeholder="请输入子目号"/>
        </el-form-item>
        <el-form-item label="子目名称" prop="itemName">
          <el-input v-model="form.itemName" placeholder="请输入子目名称"/>
        </el-form-item>
        <el-form-item label="是否混合材料" prop="mixture">
          <el-input v-model="form.mixture" placeholder="请输入是否混合材料"/>
        </el-form-item>
        <el-form-item label="单位名称" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位名称"/>
        </el-form-item>
        <el-form-item label="原合同清单" prop="inventory">
          <el-input v-model="form.inventory" placeholder="请输入原合同清单"/>
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="form.unitPrice" placeholder="请输入单价"/>
        </el-form-item>
        <el-form-item label="合价" prop="combinedPrice">
          <el-input v-model="form.combinedPrice" placeholder="请输入合价"/>
        </el-form-item>
        <el-form-item label="0号台账数量" prop="zeroCount">
          <el-input v-model="form.zeroCount" placeholder="请输入0号台账数量"/>
        </el-form-item>
        <el-form-item label="0号台账合价" prop="zeroUnitPrice">
          <el-input v-model="form.zeroUnitPrice" placeholder="请输入0号台账合价"/>
        </el-form-item>
        <el-form-item label="0号台账合价" prop="zeroCombinedPrice">
          <el-input v-model="form.zeroCombinedPrice" placeholder="请输入0号台账合价"/>
        </el-form-item>
        <el-form-item label="创建人" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建人"/>
        </el-form-item>
        <el-form-item label="修改人" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入修改人"/>
        </el-form-item>
        <el-form-item label="是否删除" prop="deleted">
          <el-input v-model="form.deleted" placeholder="请输入是否删除"/>
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
import {addBill, delBill, getBill, importTable, listBill, updateBill} from "@/api/base/bill";

export default {
  name: "Bill",
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
      // 原数据---0号台账表格数据
      billList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示导入弹出层
      importOpen: false,
      file: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        companyNo: null,
        projectNo: null,
        chapterName: null,
        uniqueNo: null,
        itemNum: null,
        itemName: null,
        mixture: null,
        unit: null,
        inventory: null,
        unitPrice: null,
        combinedPrice: null,
        zeroCount: null,
        zeroUnitPrice: null,
        zeroCombinedPrice: null,
        createUser: null,
        updateUser: null,
        deleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createUser: [
          {required: true, message: "创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateUser: [
          {required: true, message: "修改人不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        deleted: [
          {required: true, message: "是否删除不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 自定义上传
    UploadFile(option) {
      // 创建文件流
      const formData = new FormData();
      formData.append(option.filename, option.file);
      importTable(formData).then(response => {
        console.log(response)
      })
    },
    /** 查询原数据---0号台账列表 */
    getList() {
      this.loading = true;
      listBill(this.queryParams).then(response => {
        this.billList = response.rows;
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
        companyNo: null,
        projectNo: null,
        chapterName: null,
        uniqueNo: null,
        itemNum: null,
        itemName: null,
        mixture: null,
        unit: null,
        inventory: null,
        unitPrice: null,
        combinedPrice: null,
        zeroCount: null,
        zeroUnitPrice: null,
        zeroCombinedPrice: null,
        createUser: null,
        createTime: null,
        updateUser: null,
        updateTime: null,
        deleted: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加原数据---0号台账";
    },
    /** 导入按钮操作 */
    handleImport() {
      this.importOpen = true;
      this.title = "导入文档";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBill(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改原数据---0号台账";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBill(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBill(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除原数据---0号台账编号为"' + ids + '"的数据项？').then(function () {
        return delBill(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('base/bill/export', {
        ...this.queryParams
      }, `bill_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
