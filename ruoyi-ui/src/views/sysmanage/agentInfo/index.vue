<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="经销商名称" prop="agentName">
        <el-input v-model="queryParams.agentName" placeholder="请输入经销商名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="品牌名称" prop="brands">
        <el-select v-model="queryParams.brands" multiple filterable remote reserve-keyword placeholder="请输入品牌名称"
          :remote-method="remoteMethod" :loading="loading" clearable>
          <el-option v-for="item in carbrandList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="集团名称" prop="groupName">
        <el-input v-model="queryParams.groupName" placeholder="请输入集团名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="回访联系人" prop="contacts">
        <el-input v-model="queryParams.contacts" placeholder="请输入回访联系人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="回访联系人电话" prop="contactsPhone">
        <el-input v-model="queryParams.contactsPhone" placeholder="请输入回访联系人电话" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['sysmanage:agentInfo:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['sysmanage:agentInfo:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['sysmanage:agentInfo:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['sysmanage:agentInfo:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="agentInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="经销商名称" align="center" prop="agentName" />
      <el-table-column label="品牌名称" align="center" prop="brandName" />
      <el-table-column label="提交时间" align="center" prop="submitTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.submitTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="集团名称" align="center" prop="groupName" />
      <el-table-column label="回访联系人" align="center" prop="contacts" />
      <el-table-column label="回访联系人职务" align="center" prop="contactsPosition" />
      <el-table-column label="回访联系人电话" align="center" prop="contactsPhone" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['sysmanage:agentInfo:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['sysmanage:agentInfo:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改经销商档案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="经销商名称" prop="agentName">
          <el-input v-model="form.agentName" placeholder="请输入经销商名称" />
        </el-form-item>
        <el-form-item label="品牌名称" prop="brand">
          <el-select v-model="form.brand" filterable remote reserve-keyword placeholder="请输入品牌名称"
            :remote-method="remoteMethod" :loading="loading" clearable>
            <el-option v-for="item in carbrandList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提交时间" prop="submitTime">
          <el-date-picker clearable v-model="form.submitTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="请选择提交时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="集团名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入集团名称" />
        </el-form-item>
        <el-form-item label="回访联系人" prop="contacts">
          <el-input v-model="form.contacts" placeholder="请输入回访联系人" />
        </el-form-item>
        <el-form-item label="回访联系人职务" prop="contactsPosition">
          <el-input v-model="form.contactsPosition" placeholder="请输入回访联系人职务" />
        </el-form-item>
        <el-form-item label="回访联系人电话" prop="contactsPhone">
          <el-input v-model="form.contactsPhone" placeholder="请输入回访联系人电话" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" type="textarea" />
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
import { listAgentInfo, getAgentInfo, delAgentInfo, addAgentInfo, updateAgentInfo } from "@/api/sysmanage/agentInfo";
import { listCarbrand, getCarbrand } from "@/api/sysmanage/carbrand";
export default {
  name: "AgentInfo",
  data() {
    return {
      //品牌列表
      carbrandList: [],
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
      // 经销商档案表格数据
      agentInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        agentName: null,
        brand: null,
        submitTime: null,
        groupName: null,
        contacts: null,
        contactsPosition: null,
        contactsPhone: null,
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
  methods: {
    /** 查询汽车品牌列表 */
    getBrandList(brandName) {
      this.loading = true;
      let param = {};
      param.name = brandName;
      listCarbrand(param).then(response => {
        this.carbrandList = response.rows;
        this.loading = false;
      });
    },
    remoteMethod(brandName) {
      if (brandName !== '') {
        this.loading = true;
        this.getBrandList(brandName);
      } else {
        this.options = [];
      }
    },
    /** 查询经销商档案列表 */
    getList() {
      this.loading = true;
      listAgentInfo(this.queryParams).then(response => {
        this.agentInfoList = response.rows;
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
        agentName: null,
        brand: null,
        brands: null,//品牌查询条件
        carbrandList: [],// 品牌选择列表
        submitTime: null,
        groupName: null,
        contacts: null,
        contactsPosition: null,
        contactsPhone: null,
        remark: null,
        createTime: null,
        updateTime: null,
        createBy: null,
        updateBy: null
      };
      this.resetForm("form");
      this.carbrandList = [];// 品牌选择列表
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.carbrandList = [];// 品牌选择列表
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
      this.form.submitTime = this.getDateTime();
      this.open = true;
      this.title = "添加经销商档案";
    },

    getDateTime() {
      let year = new Date().getFullYear();
      let month = new Date().getMonth() + 1 < 10 ? "0" + (new Date().getMonth() + 1) : new Date().getMonth() + 1;
      let date = new Date().getDate() < 10 ? "0" + new Date().getDate() : new Date().getDate();
      let hh = new Date().getHours() < 10 ? "0" + new Date().getHours() : new Date().getHours();
      let mm = new Date().getMinutes() < 10 ? "0" + new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds() < 10 ? "0" + new Date().getSeconds() : new Date().getSeconds();
      return year +"-" + month +"-"+date +" " + hh +":"+ mm +":" + ss;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      // 根据id获取代理商
      getAgentInfo(id).then(response => {
        this.form = response.data;
        this.form.brand = this.form.brand - 0;
        //根据品牌id,获取品牌名称
        getCarbrand(this.form.brand).then(response => {
          //初始化品牌
          this.carbrandList = [];
          this.carbrandList[0] = response.data;
          this.open = true;
        });
        this.title = "修改经销商档案";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAgentInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAgentInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除经销商档案编号为"' + ids + '"的数据项？').then(function () {
        return delAgentInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('sysmanage/agentInfo/export', {
        ...this.queryParams
      }, `agentInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
