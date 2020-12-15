<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="食材名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入食材名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="食材类别" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择食材类别" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="地域" prop="area">
        <el-select v-model="queryParams.area" placeholder="请选择地域" clearable size="small">
          <el-option
            v-for="dict in areaOptions"
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
          v-hasPermi="['custom:ingredient:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:ingredient:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:ingredient:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:ingredient:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ingredientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="食材名称" align="center" prop="name" />
      <el-table-column label="食材类别" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="推荐分量" align="center" prop="recEstimation" />
      <el-table-column label="推荐分量单位" align="center" prop="recEstUnit" :formatter="recEstUnitFormat" />
      <el-table-column label="推荐分量" align="center" prop="recPortion" />
      <el-table-column label="蛋白质比例" align="center" prop="proteinRatio" />
      <el-table-column label="脂肪比例" align="center" prop="fatRatio" />
      <el-table-column label="碳水比例" align="center" prop="carbonRatio" />
      <el-table-column label="地域" align="center" prop="area" :formatter="areaFormat" />
      <el-table-column label="忌口人群" align="center" prop="notRec" :formatter="notRecFormat" />
      <el-table-column label="推荐人群" align="center" prop="recommend" :formatter="recommendFormat" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:ingredient:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:ingredient:remove']"
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

    <!-- 添加或修改食材对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="食材名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入食材名称" />
        </el-form-item>
        <el-form-item label="食材类别" prop="type">
          <el-select v-model="form.type" placeholder="请选择食材类别">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="推荐分量" prop="recEstimation">
          <el-input v-model="form.recEstimation" placeholder="请输入推荐分量" />
        </el-form-item>
        <el-form-item label="推荐分量单位" prop="recEstUnit">
          <el-select v-model="form.recEstUnit" placeholder="请选择推荐分量单位">
            <el-option
              v-for="dict in recEstUnitOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="推荐分量" prop="recPortion">
          <el-input v-model="form.recPortion" placeholder="请输入推荐分量" />
        </el-form-item>
        <el-form-item label="蛋白质比例" prop="proteinRatio">
          <el-input v-model="form.proteinRatio" placeholder="请输入蛋白质比例" />
        </el-form-item>
        <el-form-item label="脂肪比例" prop="fatRatio">
          <el-input v-model="form.fatRatio" placeholder="请输入脂肪比例" />
        </el-form-item>
        <el-form-item label="碳水比例" prop="carbonRatio">
          <el-input v-model="form.carbonRatio" placeholder="请输入碳水比例" />
        </el-form-item>
        <el-form-item label="地域" prop="area">
          <el-select v-model="form.area" placeholder="请选择地域">
            <el-option
              v-for="dict in areaOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="忌口人群">
          <el-checkbox-group v-model="form.notRec">
            <el-checkbox
              v-for="dict in notRecOptions"
              :key="dict.dictValue"
              :label="dict.dictValue">
              {{dict.dictLabel}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="推荐人群">
          <el-checkbox-group v-model="form.recommend">
            <el-checkbox
              v-for="dict in recommendOptions"
              :key="dict.dictValue"
              :label="dict.dictValue">
              {{dict.dictLabel}}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
  import { listIngredient, getIngredient, delIngredient, addIngredient, updateIngredient, exportIngredient } from "@/api/custom/ingredient";

  export default {
    name: "Ingredient",
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
        // 食材表格数据
        ingredientList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 食材类别字典
        typeOptions: [],
        // 推荐分量单位字典
        recEstUnitOptions: [],
        // 地域字典
        areaOptions: [],
        // 忌口人群字典
        notRecOptions: [],
        // 推荐人群字典
        recommendOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: null,
          type: null,
          area: null,
          notRec: null,
          recommend: null,
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
      this.getDicts("cus_ing_type").then(response => {
        this.typeOptions = response.data;
      });
      this.getDicts("cus_cus_unit").then(response => {
        this.recEstUnitOptions = response.data;
      });
      this.getDicts("cus_area").then(response => {
        this.areaOptions = response.data;
      });
      this.getDicts("cus_not_rec_group").then(response => {
        this.notRecOptions = response.data;
      });
      this.getDicts("cus_rec_group").then(response => {
        this.recommendOptions = response.data;
      });
    },
    methods: {
      /** 查询食材列表 */
      getList() {
        this.loading = true;
        listIngredient(this.queryParams).then(response => {
          this.ingredientList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 食材类别字典翻译
      typeFormat(row, column) {
        return this.selectDictLabel(this.typeOptions, row.type);
      },
      // 推荐分量单位字典翻译
      recEstUnitFormat(row, column) {
        return this.selectDictLabel(this.recEstUnitOptions, row.recEstUnit);
      },
      // 地域字典翻译
      areaFormat(row, column) {
        return this.selectDictLabel(this.areaOptions, row.area);
      },
      // 忌口人群字典翻译
      notRecFormat(row, column) {
        return this.selectDictLabels(this.notRecOptions, row.notRec);
      },
      // 推荐人群字典翻译
      recommendFormat(row, column) {
        return this.selectDictLabels(this.recommendOptions, row.recommend);
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
          recEstimation: null,
          recEstUnit: null,
          recPortion: null,
          proteinRatio: null,
          fatRatio: null,
          carbonRatio: null,
          area: null,
          notRec: [],
          recommend: [],
          remark: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null
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
        this.title = "添加食材";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getIngredient(id).then(response => {
          this.form = response.data;
          this.form.notRec = this.form.notRec.split(",");
          this.form.recommend = this.form.recommend.split(",");
          this.open = true;
          this.title = "修改食材";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.notRec = this.form.notRec.join(",");
            this.form.recommend = this.form.recommend.join(",");
            if (this.form.id != null) {
              updateIngredient(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addIngredient(this.form).then(response => {
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
        this.$confirm('是否确认删除食材编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delIngredient(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有食材数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportIngredient(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>
