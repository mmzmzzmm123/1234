<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="品牌名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入品牌名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌首字母" prop="letter">
        <el-input
          v-model="queryParams.letter"
          placeholder="请输入品牌首字母"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          @click="handleFirstAdd"
          v-hasPermi="['sysmanage:carbrand:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['sysmanage:carbrand:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carbrandList" @selection-change="handleSelectionChange">
      <el-table-column label="品牌首字母" align="center" prop="letter" />
      <el-table-column label="品牌名称" align="center" prop="name" />
      <el-table-column label="上级品牌" align="center" prop="parentName" />
      <el-table-column label="操作" align="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['sysmanage:carbrand:edit']"
            v-if="showAddSubBtn(scope.row)"
          >增加二级品牌</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sysmanage:carbrand:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sysmanage:carbrand:remove']"
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

    <!-- 添加或修改汽车一级品牌对话框 -->
    <el-dialog :title="title" :visible.sync="firstOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">        
        <el-form-item label="一级品牌名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入品牌名称" />
        </el-form-item>
        <el-form-item label="品牌首字母" prop="letter">
          <el-input v-model="form.letter" placeholder="请输入品牌首字母"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="firstSubmitForm">确 定</el-button>
        <el-button @click="cancelFirst">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改汽车二级品牌对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="一级品牌" prop="parentName" >
          <el-input v-model="form.parentName" placeholder="" disabled  />
        </el-form-item>
        
        <el-form-item label="二级品牌" prop="name">
          <el-input v-model="form.name" placeholder="请输入品牌名称" />
        </el-form-item>
        <el-form-item label="品牌首字母" prop="letter">
          <el-input v-model="form.letter" placeholder="请输入品牌首字母"  disabled />
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
import { listCarbrand, getCarbrand, delCarbrand, addCarbrand, updateCarbrand } from "@/api/sysmanage/carbrand";

export default {
  name: "Carbrand",
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
      // 汽车品牌表格数据
      carbrandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      firstOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        letter: null,
        parentId: null,
        status: null,
        indexId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  computed:{
    enableUpdate(){
      return this.form.id != null
    }
  },
  methods: {
    showAddSubBtn(row){
      return row.parentId == 0
    },
    /** 查询汽车品牌列表 */
    getList() {
      this.loading = true;
      listCarbrand(this.queryParams).then(response => {
        this.carbrandList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancelFirst() {
      this.firstOpen = false;
      this.reset();
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
        name: null,
        letter: null,
        parentId: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null,
        status: null,
        indexId: null
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
    handleAdd(row) {      
      let parentId = row.id;
      let parentName = row.name;
      let letter = row.letter;
      this.reset();
      this.form.parentId = parentId;
      this.form.parentName = parentName;
      this.form.letter = letter;      
      this.open = true;
      this.title = "添加汽车品牌";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarbrand(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改汽车品牌";
      });
    },
    /** 新增按钮操作 */
    handleFirstAdd() {
      this.reset();
      this.firstOpen = true;
      this.title = "添加汽车品牌";
    },
    /** 修改按钮操作 */
    handleFirstUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarbrand(id).then(response => {
        this.form = response.data;
        this.firstOpen = true;
        this.title = "修改汽车品牌";
      });
    },
      /** 一级品牌提交按钮 */
      firstSubmitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCarbrand(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.firstOpen = false;
              this.getList();
            });
          } else {
            addCarbrand(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.firstOpen = false;
              this.getList();
            });
          }
        }
      });
    },  
    /** 二级品牌提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCarbrand(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCarbrand(this.form).then(response => {
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
      const ids = row.id;
      // 如果删除的是一级品牌，需要判断是否存在二级品牌、系列、型号（）
      this.$modal.confirm('是否确认删除汽车品牌名称为"' + row.name + '"的数据项？').then(function() {
        return delCarbrand(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sysmanage/carbrand/export', {
        ...this.queryParams
      }, `汽车品牌_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
