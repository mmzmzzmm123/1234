<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="记账日期" prop="accountdate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.accountdate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择记账日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="单号" prop="billnumber">
        <el-input
          v-model="queryParams.billnumber"
          placeholder="请输入单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司名称" prop="companyname">
        <el-input
          v-model="queryParams.companyname"
          placeholder="请输入公司名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工厂名称" prop="factoryname">
        <el-input
          v-model="queryParams.factoryname"
          placeholder="请输入工厂名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="warehousename">
        <el-input
          v-model="queryParams.warehousename"
          placeholder="请输入仓库名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库位名称" prop="locationname">
        <el-input
          v-model="queryParams.locationname"
          placeholder="请输入库位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="goodname">
        <el-input
          v-model="queryParams.goodname"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批次单号" prop="batchbillnumber">
        <el-input
          v-model="queryParams.batchbillnumber"
          placeholder="请输入批次单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专供号" prop="specialnumber">
        <el-input
          v-model="queryParams.specialnumber"
          placeholder="请输入专供号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
        <el-form-item label="类型名称" prop="typename">
        <el-select v-model="queryParams.typename" placeholder="请选择类型名称" clearable size="small">
          <el-option
            v-for="dict in typenameOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customername">
        <el-input
          v-model="queryParams.customername"
          placeholder="请输入客户名称"
          clearable
          size="small"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['data:currentinventory:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['data:currentinventory:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['data:currentinventory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['data:currentinventory:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" stripe border show-summary highlight-current-row :data="currentinventoryList"
              @selection-change="handleSelectionChange" @sort-change='tableSortChange' @row-dblclick="handleUpdate">
    <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="行号" width="80" align="center">
            <template slot-scope="scope">{{scope.$index + 1}}</template>
    </el-table-column>
      <el-table-column label="UUID" align="center" prop="id" v-if="false" />
      <el-table-column label="仓库名称" align="center" prop="warehousename" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="商品代码" align="center" prop="goodid" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="商品名称" align="center" prop="goodname" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="单位" align="center" prop="unitname" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="规格" align="center" prop="standard" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="当前库存" align="center" prop="amount" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="占用库存" align="center" prop="freezeamount" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="可用库存" align="center" prop="availableamount" sortable='custom' :sort-orders="['ascending', 'descending']"  />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['data:currentinventory:edit']"
          >修改</el-button>
          <el-button
            plain
            size="small"
            type="primary"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['data:currentinventory:remove']"
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

    <!-- 添加或修改当前库存存储对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="记账日期" prop="accountdate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.accountdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择记账日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="单号" prop="billnumber">
          <el-input v-model="form.billnumber" placeholder="请输入单号" />
        </el-form-item>
        <el-form-item label="公司UUID" prop="companyuuid">
          <el-input v-model="form.companyuuid" placeholder="请输入公司UUID" />
        </el-form-item>
        <el-form-item label="公司代码" prop="companyid">
          <el-input v-model="form.companyid" placeholder="请输入公司代码" />
        </el-form-item>
        <el-form-item label="公司名称" prop="companyname">
          <el-input v-model="form.companyname" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="工厂UUID" prop="factoryuuid">
          <el-input v-model="form.factoryuuid" placeholder="请输入工厂UUID" />
        </el-form-item>
        <el-form-item label="工厂代码" prop="factoryid">
          <el-input v-model="form.factoryid" placeholder="请输入工厂代码" />
        </el-form-item>
        <el-form-item label="工厂名称" prop="factoryname">
          <el-input v-model="form.factoryname" placeholder="请输入工厂名称" />
        </el-form-item>
        <el-form-item label="仓库UUID" prop="warehouseuuid">
          <el-input v-model="form.warehouseuuid" placeholder="请输入仓库UUID" />
        </el-form-item>
        <el-form-item label="仓库代码" prop="warehouseid">
          <el-input v-model="form.warehouseid" placeholder="请输入仓库代码" />
        </el-form-item>
        <el-form-item label="仓库名称" prop="warehousename">
          <el-input v-model="form.warehousename" placeholder="请输入仓库名称" />
        </el-form-item>
        <el-form-item label="库位UUID" prop="locationuuid">
          <el-input v-model="form.locationuuid" placeholder="请输入库位UUID" />
        </el-form-item>
        <el-form-item label="库位代码" prop="locationid">
          <el-input v-model="form.locationid" placeholder="请输入库位代码" />
        </el-form-item>
        <el-form-item label="库位名称" prop="locationname">
          <el-input v-model="form.locationname" placeholder="请输入库位名称" />
        </el-form-item>
        <el-form-item label="商品UUID" prop="gooduuid">
          <el-input v-model="form.gooduuid" placeholder="请输入商品UUID" />
        </el-form-item>
        <el-form-item label="商品代码" prop="goodid">
          <el-input v-model="form.goodid" placeholder="请输入商品代码" />
        </el-form-item>
        <el-form-item label="商品名称" prop="goodname">
          <el-input v-model="form.goodname" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="计量单位名称" prop="unitname">
          <el-input v-model="form.unitname" placeholder="请输入计量单位名称" />
        </el-form-item>
        <el-form-item label="规格" prop="standard">
          <el-input v-model="form.standard" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="入库数量" prop="inamount">
          <el-input v-model="form.inamount" placeholder="请输入入库数量" />
        </el-form-item>
        <el-form-item label="出库数量" prop="outamount">
          <el-input v-model="form.outamount" placeholder="请输入出库数量" />
        </el-form-item>
        <el-form-item label="数量" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="冻结数量" prop="freezeamount">
          <el-input v-model="form.freezeamount" placeholder="请输入冻结数量" />
        </el-form-item>
        <el-form-item label="批次管理" prop="isbatch">
          <el-input v-model="form.isbatch" placeholder="请输入批次管理" />
        </el-form-item>
        <el-form-item label="批次单号" prop="batchbillnumber">
          <el-input v-model="form.batchbillnumber" placeholder="请输入批次单号" />
        </el-form-item>
        <el-form-item label="专供号" prop="specialnumber">
          <el-input v-model="form.specialnumber" placeholder="请输入专供号" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="单据类型">
          <el-select v-model="form.billtype" placeholder="请选择单据类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建人代码" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人代码" />
        </el-form-item>
        <el-form-item label="创建人名称" prop="createusername">
          <el-input v-model="form.createusername" placeholder="请输入创建人名称" />
        </el-form-item>
        <el-form-item label="单据类型名称" prop="billtypename">
          <el-input v-model="form.billtypename" placeholder="请输入单据类型名称" />
        </el-form-item>
        <el-form-item label="类型名称">
          <el-select v-model="form.typename" placeholder="请选择类型名称">
            <el-option
              v-for="dict in typenameOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生产日期" prop="productdate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.productdate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生产日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单号" prop="salebillnumber">
          <el-input v-model="form.salebillnumber" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="产地" prop="productionarea">
          <el-input v-model="form.productionarea" placeholder="请输入产地" />
        </el-form-item>
        <el-form-item label="版本号" prop="versionnumber">
          <el-input v-model="form.versionnumber" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="客户UUID" prop="customeruuid">
          <el-input v-model="form.customeruuid" placeholder="请输入客户UUID" />
        </el-form-item>
        <el-form-item label="客户代码" prop="customerid">
          <el-input v-model="form.customerid" placeholder="请输入客户代码" />
        </el-form-item>
        <el-form-item label="客户名称" prop="customername">
          <el-input v-model="form.customername" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="明细UUID" prop="detailuuid">
          <el-input v-model="form.detailuuid" placeholder="请输入明细UUID" />
        </el-form-item>
        <el-form-item label="专供号代码" prop="specialcode">
          <el-input v-model="form.specialcode" placeholder="请输入专供号代码" />
        </el-form-item>
        <el-form-item label="单据类型">
          <el-radio-group v-model="form.billstatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="仓储日期" prop="storedate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.storedate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择仓储日期">
          </el-date-picker>
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
import { listCurrentinventory, getCurrentinventory, delCurrentinventory, addCurrentinventory, updateCurrentinventory, exportCurrentinventory } from "@/api/data/currentinventory";

export default {
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
      // 当前库存存储表格数据
      currentinventoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型名称字典
      typenameOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: undefined,
        isAsc: undefined,
        accountdate: undefined,
        billnumber: undefined,
        companyname: undefined,
        factoryname: undefined,
        warehousename: undefined,
        locationname: undefined,
        goodname: undefined,
        batchbillnumber: undefined,
        specialnumber: undefined,
        remark: undefined,
        typename: undefined,
        customername: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        accountdate: [
          { required: true, message: "记账日期不能为空", trigger: "blur" }
        ],        billnumber: [
          { required: true, message: "单号不能为空", trigger: "blur" }
        ],        companyuuid: [
          { required: true, message: "公司UUID不能为空", trigger: "blur" }
        ],        companyid: [
          { required: true, message: "公司代码不能为空", trigger: "blur" }
        ],        companyname: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],        factoryuuid: [
          { required: true, message: "工厂UUID不能为空", trigger: "blur" }
        ],        factoryid: [
          { required: true, message: "工厂代码不能为空", trigger: "blur" }
        ],        factoryname: [
          { required: true, message: "工厂名称不能为空", trigger: "blur" }
        ],        warehouseuuid: [
          { required: true, message: "仓库UUID不能为空", trigger: "blur" }
        ],        warehouseid: [
          { required: true, message: "仓库代码不能为空", trigger: "blur" }
        ],        warehousename: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],        gooduuid: [
          { required: true, message: "商品UUID不能为空", trigger: "blur" }
        ],        goodid: [
          { required: true, message: "商品代码不能为空", trigger: "blur" }
        ],        goodname: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],        unitname: [
          { required: true, message: "计量单位名称不能为空", trigger: "blur" }
        ],        inamount: [
          { required: true, message: "入库数量不能为空", trigger: "blur" }
        ],        outamount: [
          { required: true, message: "出库数量不能为空", trigger: "blur" }
        ],        amount: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],        freezeamount: [
          { required: true, message: "冻结数量不能为空", trigger: "blur" }
        ],        isbatch: [
          { required: true, message: "批次管理不能为空", trigger: "blur" }
        ],        type: [
          { required: true, message: "类型不能为空", trigger: "blur" }
        ],        billtype: [
          { required: true, message: "单据类型不能为空", trigger: "blur" }
        ],        createuserid: [
          { required: true, message: "创建人代码不能为空", trigger: "blur" }
        ],        createusername: [
          { required: true, message: "创建人名称不能为空", trigger: "blur" }
        ],        createtime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],        billtypename: [
          { required: true, message: "单据类型名称不能为空", trigger: "blur" }
        ],        typename: [
          { required: true, message: "类型名称不能为空", trigger: "blur" }
        ],        storedate: [
          { required: true, message: "仓储日期不能为空", trigger: "blur" }
        ]      }
    };
  },
  created() {
    this.getList();
    this.getDicts("data_current_type").then(response => {
      this.typenameOptions = response.data;
    });
  },
  methods: {
      /** 通用当前库存存储排序 */
      tableSortChange(column) {
          this.queryParams.pageNum = 1;
          if (column.order === 'descending') {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'desc';
          } else {
              this.queryParams.orderByColumn = column.prop;
              this.queryParams.isAsc = 'asc';
          }
          this.getList();
      },
    /** 查询当前库存存储列表 */
    getList() {
      this.loading = true;
      listCurrentinventory(this.queryParams).then(response => {
        this.currentinventoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类型名称字典翻译
    typenameFormat(row, column) {
      return this.selectDictLabel(this.typenameOptions, row.typename);
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
        accountdate: undefined,
        billnumber: undefined,
        companyuuid: undefined,
        companyid: undefined,
        companyname: undefined,
        factoryuuid: undefined,
        factoryid: undefined,
        factoryname: undefined,
        warehouseuuid: undefined,
        warehouseid: undefined,
        warehousename: undefined,
        locationuuid: undefined,
        locationid: undefined,
        locationname: undefined,
        gooduuid: undefined,
        goodid: undefined,
        goodname: undefined,
        unitname: undefined,
        standard: undefined,
        inamount: undefined,
        outamount: undefined,
        amount: undefined,
        freezeamount: undefined,
        isbatch: undefined,
        batchbillnumber: undefined,
        specialnumber: undefined,
        type: undefined,
        billtype: undefined,
        remark: undefined,
        createuserid: undefined,
        createusername: undefined,
        createtime: undefined,
        billtypename: undefined,
        typename: undefined,
        productdate: undefined,
        salebillnumber: undefined,
        productionarea: undefined,
        versionnumber: undefined,
        customeruuid: undefined,
        customerid: undefined,
        customername: undefined,
        detailuuid: undefined,
        specialcode: undefined,
        billstatus: "0",
        storedate: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加当前库存存储";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCurrentinventory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改当前库存存储";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCurrentinventory(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addCurrentinventory(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除当前库存存储编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delCurrentinventory(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有当前库存存储数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportCurrentinventory(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
