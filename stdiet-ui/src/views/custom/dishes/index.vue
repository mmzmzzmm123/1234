<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="菜品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入菜品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="菜品类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择菜品类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['custom:dishes:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:dishes:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dishesList" @selection-change="handleSelectionChange">
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="id" align="center" prop="id" />-->
      <el-table-column label="菜品名称" align="center" prop="name" />
      <el-table-column label="菜品类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="做法" align="center" prop="methods" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:dishes:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:dishes:remove']"
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

    <!-- 添加或修改菜品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="菜品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入菜品名称" />
        </el-form-item>
        <el-form-item label="菜品类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择菜品类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="食材" prop="ingIds">
          <el-transfer
            style="text-align: left; display: inline-block"
            v-model="form.ingIds"
            filterable
            :render-content="renderFunc"
            :titles="['备选', '已选']"
            :button-texts="['', '']"
            :format="{
          noChecked: '${total}',
          hasChecked: '${checked}/${total}',
        }"
            @change="handleChange"
            :data="data"
          >
            <el-select class="transfer-footer" slot="left-footer" size="small"
                       v-model="ingType"
                       v-for="dict in ingTypeOptions"
                       :key="dict.dictValue"
                       :label="dict.dictLabel"
                       :value="dict.dictValue"/>
            <div class="transfer-footer" slot="right-footer" size="small" />
          </el-transfer>
        </el-form-item>
        <el-form-item label="做法" prop="methods">
          <el-input v-model="form.methods" type="textarea" placeholder="请输入内容" />
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
  import { listDishes, getDishes, delDishes, addDishes, updateDishes, exportDishes } from "@/api/custom/dishes";

  export default {
    name: "Dishes",
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
        // 菜品表格数据
        dishesList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        ingType: 1,
        // 食材类别字典
        ingTypeOptions: [],
        // 菜品类别字典
        typeOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          type: null,
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
      this.getDicts("cus_dishes_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("cus_ing_type").then(response => {
        this.ingTypeOptions = response.data;
      });
    },
    methods: {
      /** 查询菜品列表 */
      getList() {
        this.loading = true;
        listDishes(this.queryParams).then(response => {
          this.dishesList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 菜品类型字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
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
          type: null,
          methods: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          ingIds: []
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
        this.title = "添加菜品";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getDishes(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改菜品";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateDishes(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addDishes(this.form).then(response => {
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
        this.$confirm('是否确认删除菜品编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDishes(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有菜品数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDishes(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      renderFunc(h, option) {
        if (this.form.ingIds.includes(option.key)) {
          return <span>
              {option.key} - {option.label}
            </span>
        }
        return <span>{option.label}</span>;
      },
    }
  };
</script>
