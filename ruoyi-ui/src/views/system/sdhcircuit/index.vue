<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="城市" prop="circity">
        <el-autocomplete
          v-model="queryParams.circity"
          :fetch-suggestions="querySearchGroup"
          placeholder="请选择"
          class="filter-item"
          @select="handleSelect"
          @focus="changeDataQuery"
          clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="运行商" prop="ciroperator">
        <!--        <el-input v-model="queryParams.ciroperator" placeholder="请输入运行商" clearable-->
        <!--                  @keyup.enter.native="handleQuery"/>-->

        <el-select v-model="queryParams.ciroperator" placeholder="请选择运行商" clearable
                   @keyup.enter.native="handleQuery">
          <el-option
            v-for="dict in dict.type.yys"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目名称" prop="cirproject">
        <el-input v-model="queryParams.cirproject" placeholder="请输入项目名称" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="电路名称" prop="cirname">
        <el-input v-model="queryParams.cirname" placeholder="请输入电路名称" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="电路编号" prop="circode">
        <el-input v-model="queryParams.circode" placeholder="请输入电路编号" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="带宽" prop="cirbandwidth">
        <el-input v-model="queryParams.cirbandwidth" placeholder="请输入带宽" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="A端地址" prop="aaddress">
        <el-input v-model="queryParams.aaddress" placeholder="请输入A端地址" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="Z端地址" prop="zaddress">
        <el-input v-model="queryParams.zaddress" placeholder="请输入Z端地址" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="项目经理" prop="projectname">
        <el-input v-model="queryParams.projectname" placeholder="请输入项目经理" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <!-- <el-form-item label="${comment}" prop="updatetime">
        <el-date-picker clearable v-model="queryParams.updatetime" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择${comment}">
        </el-date-picker>
      </el-form-item> -->
      <el-form-item label="创建人" prop="operator">
        <el-input v-model="queryParams.operator" placeholder="请输入创建人" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="更新人" prop="updateoperator">
        <el-input v-model="queryParams.updateoperator" placeholder="请输入更新人" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['system:sdhcircuit:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['system:sdhcircuit:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['system:sdhcircuit:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                   v-hasPermi="['system:sdhcircuit:export']">导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sdhcircuitList" @selection-change="handleSelectionChange"
              @row-dblclick="handleUpdate" v-hasPermi="['system:sdhcircuit:edit']">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id"/>
      <el-table-column label="城市" prop="circity"/>
      <el-table-column label="运行商" align="center" prop="ciroperator">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yys" :value="scope.row.ciroperator"/>
        </template>
      </el-table-column>
      <el-table-column label="项目名称" align="center" width="300" prop="cirproject"/>
      <el-table-column label="电路名称" align="center" width="300" prop="cirname"/>
      <el-table-column label="电路编号" align="center" width="300" prop="circode"/>
      <el-table-column label="电路类型" align="center" prop="cirtype">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cirtype" :value="scope.row.cirtype"/>
        </template>
      </el-table-column>
      <el-table-column label="带宽" align="center" prop="cirbandwidth"/>
      <el-table-column label="电路信息" align="center" width="300" prop="cirinfo"/>
      <el-table-column label="网管信息" align="center" width="300" prop="manageinfo"/>
      <el-table-column label="A端信息" align="center" width="300" prop="ainfo"/>
      <el-table-column label="A端地址" align="center" prop="aaddress"/>
      <el-table-column label="Z端信息" align="center" width="300" prop="zinfo"/>
      <el-table-column label="Z端地址" align="center" prop="zaddress"/>
      <el-table-column label="项目经理" align="center" prop="projectname"/>
      <el-table-column label="更新时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电路状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.dlzt" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="南向" align="center" prop="south">
      <template slot-scope="scope">
        <dict-tag :options="dict.type.zt" :value="scope.row.south"/>
      </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="operator"/>
      <el-table-column label="更新人" align="center" prop="updateoperator"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleCopyRow(scope.row)"
                     v-hasPermi="['system:sdhcircuit:add']">复制
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['system:sdhcircuit:edit']">修改
          </el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['system:sdhcircuit:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改电路信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" hight="50%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="城市" prop="circity">
          <el-autocomplete
            v-model="form.circity"
            :fetch-suggestions="querySearchGroup"
            placeholder="请选择"
            class="filter-item"
            @select="handleSelect"
            @focus="changeDataForm"
            clearable @keyup.enter.native="handleQuery"/>
        </el-form-item>
        <el-form-item label="运行商" prop="ciroperator">
          <el-select v-model="form.ciroperator" placeholder="请选额运行商">
            <el-option
              v-for="dict in dict.type.yys"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目名称" prop="cirproject">
          <el-input v-model="form.cirproject" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="电路名称" prop="cirname">
          <el-input v-model="form.cirname" placeholder="请输入电路名称"/>
        </el-form-item>
        <el-form-item label="电路编号" prop="circode">
          <el-input v-model="form.circode" placeholder="请输入电路编号"/>
        </el-form-item>
        <el-form-item label="电路类型" prop="cirtype">
          <!--          <el-input v-model="form.cirbandwidth" placeholder="请输入带宽"/>-->
          <el-select v-model="form.cirtype" placeholder="请选择电路类型">
            <el-option
              v-for="dict in dict.type.cirtype"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="带宽" prop="cirbandwidth">
          <el-input v-model="form.cirbandwidth" placeholder="请输入带宽"/>
        </el-form-item>
        <el-form-item label="电路信息" prop="cirinfo">
          <el-input v-model="form.cirinfo" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="网管信息" prop="manageinfo">
          <el-input v-model="form.manageinfo" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="A端信息" prop="ainfo">
          <el-input v-model="form.ainfo" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="A端地址" prop="aaddress">
          <el-input v-model="form.aaddress" placeholder="请输入A端地址"/>
        </el-form-item>
        <el-form-item label="Z端信息" prop="zinfo">
          <el-input v-model="form.zinfo" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="Z端地址" prop="zaddress">
          <el-input v-model="form.zaddress" placeholder="请输入Z端地址"/>
        </el-form-item>
        <el-form-item label="项目经理" prop="projectname">
          <el-input v-model="form.projectname" placeholder="请输入项目经理"/>
        </el-form-item>
        <el-form-item label="南向" prop="status">
          <!--          <el-input v-model="form.cirbandwidth" placeholder="请输入带宽"/>-->
          <el-select v-model="form.south" placeholder="请选择南向">
            <el-option
              v-for="dict in dict.type.zt"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电路状态" prop="status">
          <!--          <el-input v-model="form.cirbandwidth" placeholder="请输入带宽"/>-->
          <el-select v-model="form.status" placeholder="请选择电路状态">
            <el-option
              v-for="dict in dict.type.dlzt"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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
import {addSdhcircuit, delSdhcircuit, getSdhcircuit, listSdhcircuit, updateSdhcircuit} from "@/api/system/sdhcircuit";
import {listCity} from "@/api/system/city";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Sdhcircuit",
  dicts: ['yys', 'dlzt', 'cirtype', 'zt'],
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
      // 电路信息表格数据
      sdhcircuitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      groupArr: [],
      groupId: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        circity: null,
        ciroperator: null,
        cirproject: null,
        cirname: null,
        circode: null,
        cirtype: null,
        cirbandwidth: null,
        cirinfo: null,
        manageinfo: null,
        ainfo: null,
        aaddress: null,
        zinfo: null,
        zaddress: null,
        projectname: null,
        updatetime: null,
        status: null,
        operator: null,
        updateoperator: null
      },
      // 表单参数
      form: {},
      options: [],
      listCity: [],
      dlztOption: [],
      queryCity: {
        pageNum: 1,
        pageSize: 100,
        cityName: null,
        province: null
      },
      // 表单校验
      rules: {
        circity: [
          {required: true, message: "城市不能为空", trigger: "blur"}
        ],
        ciroperator: [
          {required: true, message: "运行商不能为空", trigger: "blur"}
        ],
        cirproject: [
          {required: true, message: "项目名称不能为空", trigger: "blur"}
        ],
        cirname: [
          {required: true, message: "电路名称不能为空", trigger: "blur"}
        ],
        circode: [
          {required: true, message: "电路编号不能为空", trigger: "blur"}
        ],
        cirtype: [
          {required: true, message: "电路类型不能为空", trigger: "change"}
        ],
        cirbandwidth: [
          {required: true, message: "带宽不能为空", trigger: "blur"}
        ],
        cirinfo: [
          {required: true, message: "电路信息不能为空", trigger: "blur"}
        ],
        manageinfo: [
          {required: true, message: "网管信息不能为空", trigger: "blur"}
        ],
        ainfo: [
          {required: true, message: "A端信息不能为空", trigger: "blur"}
        ],
        aaddress: [
          {required: true, message: "A端地址不能为空", trigger: "blur"}
        ],
        zinfo: [
          {required: true, message: "Z端信息不能为空", trigger: "blur"}
        ],
        zaddress: [
          {required: true, message: "Z端地址不能为空", trigger: "blur"}
        ],
        projectname: [
          {required: true, message: "项目经理不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "电路状态不能为空", trigger: "change"}
        ]
      }
    };
  },


  created() {
    this.getList();

    this.getListCity();
  },
  methods: {
    /** 查询电路信息列表 */
    getListCity() {
      this.loading = true;
      listCity(this.queryCity).then(response => {
        debugger;
        this.listCity = response.rows;
      });
    },

    querySearchGroup(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      console.log(results);
      if (!results) {
        results = [];
      }
      cb(results);
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.indexOf(queryString) === 0);
      };
    },
    //@select 点击选中建议项时触发
    handleSelect(item) {
      console.log(item)
      console.log("item.value" + item.value)
      console.log("item.name" + item.name)
      console.log(this.form.circity)
      console.log(this.queryParams.circity)
    },
    //@input 在 Input 值改变时触发
    changeDataQuery(value) {
      if (value != '') {
        this.handleChange()
      } else {
        this.form.circity = ""
      }
    },
    changeDataForm(value) {
      if (value != '') {
        this.handleChange()
      } else {
        this.queryParams.circity = ""
      }
    },
    convertArrayToObject(array) {
      const uniqueValues = Array.from(new Set(array)); // 使用 Set 去除重复值，并转换为数组
      return uniqueValues.map(item => {
        return {
          value: item.cityName,
          label: item.cityName,
          name: item.code
        };
      });
    },
    // 接口
    async handleChange() {
      await listCity(this.queryCity).then(response => {
        debugger;
        this.restaurants = this.convertArrayToObject(response.rows);
        console.log(this.restaurants)
      })
    },
    getList() {
      this.loading = true;
      listSdhcircuit(this.queryParams).then(response => {
        this.sdhcircuitList = response.rows;
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
        circity: null,
        ciroperator: null,
        cirproject: null,
        cirname: null,
        circode: null,
        cirtype: null,
        cirbandwidth: null,
        cirinfo: null,
        manageinfo: null,
        ainfo: null,
        aaddress: null,
        zinfo: null,
        zaddress: null,
        projectname: null,
        updatetime: null,
        status: null,
        operator: null,
        updateoperator: null,
        south: null
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
      this.title = "添加电路信息";
    },
    handleCopyRow(row) {
      this.reset();
      const id = row.id || this.ids
      getSdhcircuit(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.form.id = null;
        this.title = "新增电路信息";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSdhcircuit(id).then(response => {
        this.form = response.data;
        this.open = true;
        // this.dialogWidth = '70%'; // 可根据需求调整大小
        this.title = "修改电路信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSdhcircuit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            debugger;
            // alert(this.form);
            console.log(this.form);
            addSdhcircuit(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除电路信息编号为"' + ids + '"的数据项？').then(function () {
        return delSdhcircuit(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/sdhcircuit/export', {
        ...this.queryParams
      }, `sdhcircuit_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
