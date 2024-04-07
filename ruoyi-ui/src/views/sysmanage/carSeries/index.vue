<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="一级品牌" prop="brands">
        <el-select v-model="queryParams.brands" multiple filterable remote reserve-keyword placeholder="请选择" clearable>
          <el-option v-for="item in carbrandList" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="二级品牌" prop="level2Brands">
        <el-cascader v-model="queryParams.level2Brands" :options="subBrandList" :props="props" clearable filterable
          placeholder="请选择"></el-cascader>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['sysmanage:carSeries:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['sysmanage:carSeries:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carSeriesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="一级品牌" align="center" prop="brandName" />
      <el-table-column label="二级品牌" align="center" prop="level2BrandName" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sysmanage:carSeries:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sysmanage:carSeries:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改车系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="selectBrands" label-width="80px">
        <el-form-item label="品牌" prop="level2Brands">
          <el-cascader v-model="form.level2Brands" :options="subBrandList" clearable filterable
            placeholder="请选择"></el-cascader>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
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
import { listCarSeries, getCarSeries, delCarSeries, addCarSeries, updateCarSeries } from "@/api/sysmanage/carSeries";
import { listCarbrand } from "@/api/sysmanage/carbrand";
export default {
  name: "CarSeries",
  data() {
    return {
      selectBrands: {
        level2Brands: [{
          type: 'array',
          required: true,
          message: '请选择汽车品牌！',
          triger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入车系名称！',
          triger: 'blur'
        }]
      },
      props: { multiple: true },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      carbrandList: [],// 一级品牌
      subBrandList: [],// 二级品牌
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车系表格数据
      carSeriesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        brandId: null,
        level2BrandId: null,
        brands: [],
        level2Brands: [],
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
    this.getBrandList();
    this.getList();
  },
  methods: {

    /** 查询汽车品牌列表 */
    getSubBrandsList(ele) {
      let param = {};
      param.pageable = false;
      param.parentId = ele.value;
      listCarbrand(param).then(response => {
        if (response.rows != null) {
          let children = []
          for (let i = 0; i < response.rows.length; i++) {
            let item = {}
            item.value = response.rows[i].id
            item.label = response.rows[i].name
          }
          ele.children = children;
        }
      });
    },
    /** 转换一级二级品牌 */
    transBrandList(list, level) {
      let result = [];
      let children = [];
      let parentItem = null;
      let i = 0;
      let item = {};
      for (i = 0; i < list.length; i++) {
        if (list[i].parentId != 0) {
          continue
        }
        item = {};
        item.value = list[i].id;
        item.label = list[i].name;
        result.push(item);
      }
      i = 0;
      if (level == 2) {
        for (i = 0; i < result.length; i++) {
          for (let j = 0; j < list.length; j++) {
            if (list[j].parentId == 0) {
              continue
            }
            if (list[j].parentId == result[i].value) {
              item = {};
              item.value = list[j].id;
              item.label = list[j].name;
              if(result[i].children == null){
                result[i].children = [];
              }
              result[i].children.push(item);
            }
          }
        }
      }
      return result;
    },
    /** 查询一级品牌*/
    getBrandList() {
      let param = {};
      param.pageAble = false;
      param.status = 0;
      listCarbrand(param).then(response => {
        this.carbrandList = this.transBrandList(response.rows, 1);
        this.subBrandList = this.transBrandList(response.rows, 2);
      });
    },
    /** 查询车系列表 */
    getList() {
      this.loading = true;
      listCarSeries(this.queryParams).then(response => {
        this.carSeriesList = response.rows;
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
        name: null,
        brandId: null,
        level2BrandId: null,
        level2Brands: [],
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加车系";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarSeries(id).then(response => {
        this.form = response.data;
        let subBrand = []
        subBrand[0] = this.form.brandId
        subBrand[1] = this.form.level2BrandId
        this.form.level2Brands = subBrand
        this.open = true;
        this.title = "修改车系";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.brandId = this.form.level2Brands[0]
          this.form.level2BrandId = this.form.level2Brands[1]
          this.form.level2Brands = null
          if (this.form.id != null) {
            updateCarSeries(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCarSeries(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除车系编号为"' + ids + '"的数据项？').then(function () {
        return delCarSeries(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sysmanage/carSeries/export', {
        ...this.queryParams
      }, `车系_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
