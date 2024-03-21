<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品牌" prop="level2Brands">
        <el-cascader v-model="queryParams.level2Brands" :options="subBrandList" clearable filterable placeholder="请选择"
          @change="brandChange"></el-cascader>
      </el-form-item>
      <el-form-item label="车系" prop="serieId">
        <el-select v-model="queryParams.serieId" filterable reserve-keyword placeholder="请选择" clearable>
          <el-option v-for="item in carSeriesList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
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
          v-hasPermi="['sysmanage:carModel:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['sysmanage:carModel:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-upload" size="mini" @click="handleExport"
          v-hasPermi="['sysmanage:carModel:export']">导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['sysmanage:carModel:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="carModelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="车系" align="center" prop="serieName" />
      <el-table-column label="车型" align="center" prop="name" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sysmanage:carModel:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sysmanage:carModel:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改汽车型号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="品牌" prop="level2Brands">
          <el-cascader v-model="form.level2Brands" :options="subBrandList" clearable filterable placeholder="请选择"
            @change="updateBrandChange"></el-cascader>
        </el-form-item>
        <el-form-item label="车系" prop="serieId">
          <el-select v-model="form.serieId" filterable reserve-keyword placeholder="请选择" clearable>
            <el-option v-for="item in updateCarSeriesList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
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
import { listCarModel, getCarModel, delCarModel, addCarModel, updateCarModel } from "@/api/sysmanage/carModel";
import { listCarbrand } from "@/api/sysmanage/carbrand";
import { listCarSeries } from "@/api/sysmanage/carSeries";
export default {
  name: "CarModel",
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
      // 汽车型号表格数据
      carModelList: [],
      subBrandList: [],// 二级品牌
      carSeriesList: [],// 车系
      updateCarSeriesList:[],
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
        serieId: null,
        status: null
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
    brandChange(value) {
      this.carSeriesList = [];
      if (value.length == 2) {
        let level2BrandId = value[1];
        this.queryParams.level2BrandId = level2BrandId;
        this.getCarSeriesList(level2BrandId);
      } else {
        this.queryParams.level2BrandId = null;
      }
    },
    updateBrandChange(value) {
      this.updateCarSeriesList = [];
      if (value.length == 2) {
        let level2BrandId = value[1];
        this.getUpdateCarSeriesList(level2BrandId);
      }
    },
      /** 查询车系列表 */
      getUpdateCarSeriesList(level2BrandId) {
      let param = {}
      param.pageAble = false;
      param.level2BrandId = level2BrandId
      listCarSeries(param).then(response => {
        this.updateCarSeriesList = response.rows;
      });
    },
    /** 查询车系列表 */
    getCarSeriesList(level2BrandId) {
      let param = {}
      param.pageAble = false;
      param.level2BrandId = level2BrandId
      listCarSeries(param).then(response => {
        this.carSeriesList = response.rows;
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
              if (result[i].children == null) {
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
        this.subBrandList = this.transBrandList(response.rows, 2);
      });
    },
    /** 查询汽车型号列表 */
    getList() {
      this.loading = true;
      listCarModel(this.queryParams).then(response => {
        this.carModelList = response.rows;
        for (let i = 0; i < this.carModelList.length; i++) {
          this.carModelList[i].brand = this.carModelList[i].brandName + ' / ' + this.carModelList[i].level2BrandName
        }
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
        serieId: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null,
        status: null
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
      this.carSeriesList = [];
      this.resetForm("queryForm");
      this.queryParams.level2BrandId = null;
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
      this.title = "添加汽车型号";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCarModel(id).then(response => {
        this.form = response.data;
        this.form.level2Brands = [this.form.brandId,this.form.level2BrandId]
        this.updateBrandChange(this.form.level2Brands);
        this.open = true;
        this.title = "修改汽车型号";
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
            updateCarModel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCarModel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除选中的汽车型号？').then(function () {
        return delCarModel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sysmanage/carModel/export', {
        ...this.queryParams
      }, `车型_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
