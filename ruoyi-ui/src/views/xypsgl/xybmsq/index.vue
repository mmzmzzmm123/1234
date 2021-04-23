<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="方案编号" prop="faid">
        <el-select v-model="queryParams.faid" placeholder="请选择方案编号" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="报名基地" prop="jdid">
        <el-select v-model="queryParams.jdid" placeholder="请选择报名基地id" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地类别" prop="jdtype">
        <el-select v-model="queryParams.jdtype" placeholder="请选择基地类别" clearable size="small">
          <el-option
            v-for="dict in jdtypeOptions"
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
          v-hasPermi="['xypsgl:xybmsq:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['xypsgl:xybmsq:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['xypsgl:xybmsq:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="xybmsqList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案编号" align="center" prop="faid" />
      <el-table-column label="报名基地" align="center" prop="jdid" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="进修编号" align="center" prop="jxbh" />
      <el-table-column label="学段" align="center" prop="xd" :formatter="xdFormat" />
      <el-table-column label="学科" align="center" prop="xk" :formatter="xkFormat" />
      <el-table-column label="基地类别" align="center" prop="jdtype" :formatter="jdtypeFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['xypsgl:xybmsq:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['xypsgl:xybmsq:remove']"
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

    <!-- 添加或修改学员报名申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="方案编号" prop="faid">
          <el-select v-model="form.faid" placeholder="请选择方案编号">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="报名基地" prop="jdid">
          <el-select v-model="form.jdid" placeholder="请选择报名基地id">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="进修编号" prop="jxbh">
          <el-input v-model="form.jxbh" placeholder="请输入进修编号" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-select v-model="form.xb" placeholder="请选择性别">
            <el-option
              v-for="dict in xbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学段" prop="xd">
          <el-select v-model="form.xd" placeholder="请选择学段">
            <el-option
              v-for="dict in xdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学科" prop="xk">
          <el-select v-model="form.xk" placeholder="请选择学科">
            <el-option
              v-for="dict in xkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="zc">
          <el-select v-model="form.zc" placeholder="请选择职称">
            <el-option
              v-for="dict in zcOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位名称" prop="dwmc">
          <el-input v-model="form.dwmc" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="单位地址" prop="dwdz">
          <el-input v-model="form.dwdz" placeholder="请输入单位地址" />
        </el-form-item>
        <el-form-item label="基地类别" prop="jdtype">
          <el-select v-model="form.jdtype" placeholder="请选择基地类别">
            <el-option
              v-for="dict in jdtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份" prop="sf">
          <el-select v-model="form.sf" placeholder="请选择身份">
            <el-option
              v-for="dict in sfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱" />
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
import { listXybmsq, getXybmsq, delXybmsq, addXybmsq, updateXybmsq, exportXybmsq } from "@/api/xypsgl/xybmsq";

export default {
  name: "Xybmsq",
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
      // 学员报名申请表格数据
      xybmsqList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 学段字典
      xdOptions: [],
      // 学科字典
      xkOptions: [],
      // 职称字典
      zcOptions: [],
      // 基地类别字典
      jdtypeOptions: [],
      // 身份字典
      sfOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jdid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        xd: null,
        xk: null,
        zc: null,
        dwmc: null,
        dwdz: null,
        jdtype: null,
        sf: null,
        phone: null,
        email: null,
        createUser: null
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
    this.getDicts("sys_user_sex").then(response => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then(response => {
      this.xdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then(response => {
      this.xkOptions = response.data;
    });
    this.getDicts("sys_dm_zcrzjzzc").then(response => {
      this.zcOptions = response.data;
    });
    this.getDicts("sys_dm_zcrjdtype").then(response => {
      this.jdtypeOptions = response.data;
    });
    this.getDicts("sys_dm_zcrsf").then(response => {
      this.sfOptions = response.data;
    });
  },
  methods: {
    /** 查询学员报名申请列表 */
    getList() {
      this.loading = true;
      listXybmsq(this.queryParams).then(response => {
        this.xybmsqList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 学段字典翻译
    xdFormat(row, column) {
      return this.selectDictLabel(this.xdOptions, row.xd);
    },
    // 学科字典翻译
    xkFormat(row, column) {
      return this.selectDictLabel(this.xkOptions, row.xk);
    },
    // 职称字典翻译
    zcFormat(row, column) {
      return this.selectDictLabel(this.zcOptions, row.zc);
    },
    // 基地类别字典翻译
    jdtypeFormat(row, column) {
      return this.selectDictLabel(this.jdtypeOptions, row.jdtype);
    },
    // 身份字典翻译
    sfFormat(row, column) {
      return this.selectDictLabel(this.sfOptions, row.sf);
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
        faid: null,
        jdid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        xd: null,
        xk: null,
        zc: null,
        dwmc: null,
        dwdz: null,
        jdtype: null,
        sf: null,
        phone: null,
        email: null,
        createTime: null,
        createUser: null
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
      this.title = "添加学员报名申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getXybmsq(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学员报名申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateXybmsq(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addXybmsq(this.form).then(response => {
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
      this.$confirm('是否确认删除学员报名申请编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delXybmsq(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有学员报名申请数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportXybmsq(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
