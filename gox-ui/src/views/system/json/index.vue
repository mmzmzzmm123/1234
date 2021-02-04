<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4">
        <dept-tree @selectedDept="selectedDept">
        </dept-tree>
      </el-col>
      <el-col :span="20">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="表单名字" prop="formName">
            <el-input
              v-model="queryParams.formName"
              placeholder="请输入表单名字"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
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
              icon="el-icon-edit"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:json:edit']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:json:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:json:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" icon="el-icon-download" size="mini" @click="handTableField">
              表格编辑
            </el-button>
            <!--        <el-button-->
            <!--          type="warning"-->
            <!--          icon="el-icon-download"-->
            <!--          size="mini"-->
            <!--          @click="handleExport"-->
            <!--          v-hasPermi="['system:json:export']"-->
            <!--        >导出</el-button>-->
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="jsonList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="id" align="center" prop="id" />
          <el-table-column label="表单名字" align="center" prop="formName" />
          <el-table-column label="创建时间" align="center" prop="createTime" />
          <el-table-column label="修改时间" align="center" prop="updateTime" />
          <!-- <el-table-column label="表单json" align="center" prop="formData" /> -->
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:json:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:json:remove']"
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
      </el-col>
    </el-row>

    <!-- 添加或修改单json存储对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <div>
        <el-transfer
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入字段名"
          v-model="value"
          :data="data">
        </el-transfer>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJson, getJson, delJson, addJson, updateJson, exportJson } from "@/api/system/json";
import deptTree from '@/views/components/deptTree'

export default {
  name: "Json",
  components: {
    deptTree
  },
  data() {
    return {
      //deptId
      deptId:0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 单json存储表格数据
      jsonList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        formName: null,
        formData: null,
        parentName:null,
        node:null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        // createTime: [
        //   { required: true, message: "创建时间不能为空", trigger: "blur" }
        // ],
        // updateTime: [
        //   { required: true, message: "修改时间不能为空", trigger: "blur" }
        // ],
        // createBy: [
        //   { required: true, message: "创建者不能为空", trigger: "blur" }
        // ],
        // updateBy: [
        //   { required: true, message: "修改者不能为空", trigger: "blur" }
        // ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ],
        formName: [
          { required: true, message: "表单名字不能为空", trigger: "blur" }
        ],
        formData: [
          { required: true, message: "表单json不能为空", trigger: "blur" }
        ],
        // delFlag: [
        //   { required: true, message: "删除标志不能为空", trigger: "blur" }
        // ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    selectedDept(data){
      this.deptId=data
      this.queryParams.node=data
      console.log(data)
    },
    /** 选择一个节点 */
    handTableField(){

    },
    filterMethod(query, item) {
      return item.name.indexOf(query) > -1;
    },
    /** 查询单json存储列表 */
    getList() {
      this.loading = true;
      listJson(this.queryParams).then(response => {
        this.jsonList = response.rows;
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
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null,
        remark: null,
        formName: null,
        formData: null,
        delFlag: null
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
      this.title = "添加表单json";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      const id = row.id
      this.$router.push('/tool/build/'+id)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJson(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJson(this.form).then(response => {
              this.msgSuccess("新增成功");
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
      this.$confirm('是否确认删除表单单json编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJson(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有表单json数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJson(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
