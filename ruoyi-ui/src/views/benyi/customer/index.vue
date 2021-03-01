<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="90px"
    >
      <el-form-item label="客户姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="lxdh">
        <el-input
          v-model="queryParams.lxdh"
          placeholder="请输入联系电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="幼儿园名称" prop="schoolname">
        <el-input
          v-model="queryParams.schoolname"
          placeholder="请输入幼儿园名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在省" prop="sheng">
        <el-input
          v-model="queryParams.sheng"
          placeholder="请输入所在省"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户来源" prop="khly">
        <el-select v-model="queryParams.khly" placeholder="请选择客户来源">
          <el-option
            v-for="dict in lyOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:customer:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:customer:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:customer:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="customerList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="客户姓名" align="center" prop="name" />
      <el-table-column label="幼儿园名称" align="center" prop="schoolname" />
      <el-table-column label="幼儿园人数" align="center" prop="rs" />
      <el-table-column label="身份" align="center" prop="sflx" :formatter="gxFormat"/>
      <el-table-column label="联系电话" align="center" prop="lxdh" />
      <el-table-column label="微信" align="center" prop="wx" />
      <el-table-column label="抖音" align="center" prop="dy" />
      <el-table-column label="其他" align="center" prop="qt" />
      <el-table-column label="所在省" align="center" prop="sheng" />
      <el-table-column label="所在市" align="center" prop="shi" />
      <el-table-column label="客户来源" align="center" prop="khly" :formatter="lyFormat"/>
      <el-table-column label="消费项目" align="center" prop="xfxm" />
      <el-table-column label="消费价值" align="center" prop="xfjz" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:customer:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:customer:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改本一-客户关系管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿园名称" prop="schoolname">
              <el-input
                v-model="form.schoolname"
                placeholder="请输入幼儿园名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿园人数" prop="rs">
              <el-input-number
                v-model="form.rs"
                placeholder="请输入幼儿园人数"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份" prop="sflx">
              <el-select v-model="form.sflx" placeholder="请选择身份">
                <el-option
                  v-for="dict in gxOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="lxdh">
              <el-input v-model="form.lxdh" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="微信" prop="wx">
              <el-input v-model="form.wx" placeholder="请输入微信" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="抖音" prop="dy">
              <el-input v-model="form.dy" placeholder="请输入抖音" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他联系方式" prop="qt">
              <el-input v-model="form.qt" placeholder="请输入其他联系方式" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在省" prop="sheng">
              <el-input v-model="form.sheng" placeholder="请输入所在省" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在市" prop="shi">
              <el-input v-model="form.shi" placeholder="请输入所在市" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户来源" prop="khly">
              <el-select v-model="form.khly" placeholder="请选择客户来源">
                <el-option
                  v-for="dict in lyOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="消费项目" prop="xfxm">
              <el-input v-model="form.xfxm" placeholder="请输入消费项目" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="消费价值" prop="xfjz">
              <el-input v-model="form.xfjz" placeholder="请输入消费价值" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备注" prop="bz">
              <el-input v-model="form.bz" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="转换跟进" prop="zhgj">
              <el-input
                v-model="form.zhgj"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCustomer,
  getCustomer,
  delCustomer,
  addCustomer,
  updateCustomer,
  exportCustomer,
} from "@/api/benyi/customer";

export default {
  name: "Customer",
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
      // 本一-客户关系管理表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      gxOptions: [],
      lyOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        sflx: undefined,
        lxdh: undefined,
        wx: undefined,
        dy: undefined,
        qt: undefined,
        schoolname: undefined,
        rs: undefined,
        sheng: undefined,
        shengid: undefined,
        shi: undefined,
        shiid: undefined,
        khly: undefined,
        createUserid: undefined,
        bz: undefined,
        zhgj: undefined,
        state: undefined,
        xfxm: undefined,
        xfjz: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "客户姓名不能为空", trigger: "blur" },
        ],
        schoolname: [
          { required: true, message: "幼儿园名称不能为空", trigger: "blur" },
        ],
        lxdh: [
          { required: true, message: "幼儿园名称不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_khgxsflx").then((response) => {
      this.gxOptions = response.data;
    });
    this.getDicts("sys_dm_khgxly").then((response) => {
      this.lyOptions = response.data;
    });
  },
  methods: {
          // 字典翻译
    gxFormat(row, column) {
      return this.selectDictLabel(this.gxOptions, row.sflx);
    },
        // 字典翻译
    lyFormat(row, column) {
      return this.selectDictLabel(this.lyOptions, row.khly);
    },
    /** 查询本一-客户关系管理列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then((response) => {
        this.customerList = response.rows;
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
        id: undefined,
        name: undefined,
        sflx: undefined,
        lxdh: undefined,
        wx: undefined,
        dy: undefined,
        qt: undefined,
        schoolname: undefined,
        rs: 0,
        sheng: undefined,
        shengid: undefined,
        shi: undefined,
        shiid: undefined,
        khly: undefined,
        createUserid: undefined,
        bz: undefined,
        zhgj: undefined,
        state: undefined,
        xfxm: undefined,
        xfjz: undefined,
        createTime: undefined,
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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加本一-客户关系管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCustomer(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改本一-客户关系管理";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateCustomer(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addCustomer(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除本一-客户关系管理编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delCustomer(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有本一-客户关系管理数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportCustomer(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>