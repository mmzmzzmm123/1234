<template>
  <!-- 选择用户对话框 -->
  <el-dialog :title="title" :visible.sync="open" @closed="cancel" width="480px" append-to-body>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="60px" style="margin-top: -20px">
      <el-form-item label="名字" prop="name">
        <el-input
          v-model.trim="queryParams.name"
          placeholder="请输入名字"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          size="mini"
          :disabled="single"
          @click="handleSelect"
        >选择当前勾选用户</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="customerCenterList"
      @selection-change="handleSelectionChange"
      ref="customerTable"
    >
     <!-- <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="166"
      />-->
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户姓名" align="center" prop="name" />
      <el-table-column label="手机号" align="center" prop="phone" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :layout="'total, sizes, prev, next'"
      :pageSizes="[5]"
      @pagination="getList"
    />

  </div>
    <div slot="footer" class="dialog-footer">
      <el-button
        type="success"
        :disabled="single"
        @click="handleSelect"
      >确定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {
  listCustomer,
} from "@/api/custom/customer";


export default {
  name: "selectCustomer",
  components: {

  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选择名字数组
      names:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 客户档案表格数据
      customerCenterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        name: null,
        phone: null
      }
    };
  },
  created() {
    this.getList();
  },
  computed: {
  },
  methods: {
    showDialog(title) {
      this.title = title ? title : "选择客户";
      this.open = true;
    },
    /** 查询客户档案列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then((response) => {
        this.customerCenterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.$refs.customerTable.clearSelection();
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
      this.ids = selection.map((item) => item.id);
      this.names = selection.map((item) => item.name);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 选择用户操作 */
    handleSelect() {
      const id = this.ids[0];
      const name = this.names[0];
      this.cancel();
      this.$emit('dealCustomerId', id, name);
    },

  },
};
</script>
