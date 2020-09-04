<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="xb">
        <el-select v-model="queryParams.xb" placeholder="请选择性别" clearable size="small">
          <el-option
            v-for="dict in xbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="政治面貌" prop="zzmm">
        <el-select v-model="queryParams.zzmm" placeholder="请选择政治面貌" clearable size="small">
          <el-option
            v-for="dict in zzmmOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="民族" prop="mz">
        <el-select v-model="queryParams.mz" placeholder="请选择民族" clearable size="small">
          <el-option
            v-for="dict in mzOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教学段" prop="rjxd">
        <el-select v-model="queryParams.rjxd" placeholder="请选择任教学段" clearable size="small">
          <el-option
            v-for="dict in rjxdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教学科" prop="rjxk">
        <el-select v-model="queryParams.rjxk" placeholder="请选择任教学科" clearable size="small">
          <el-option
            v-for="dict in rjxkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任教年级" prop="rjnj">
        <el-select v-model="queryParams.rjnj" placeholder="请选择任教年级" clearable size="small">
          <el-option
            v-for="dict in rjnjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学历" prop="xl">
        <el-select v-model="queryParams.xl" placeholder="请选择学历" clearable size="small">
          <el-option
            v-for="dict in xlOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学位" prop="xw">
        <el-select v-model="queryParams.xw" placeholder="请选择学位" clearable size="small">
          <el-option
            v-for="dict in xwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="师范生" prop="sfsfs">
        <el-select v-model="queryParams.sfsfs" placeholder="请选择是否师范生" clearable size="small">
          <el-option
            v-for="dict in sfsfsOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="录取年份" prop="lqnf">
        <el-select v-model="queryParams.lqnf" placeholder="请选择录取年份" clearable size="small">
          <el-option
            v-for="dict in lqnfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="聘任单位" prop="prdwid">
        <el-input
          v-model="queryParams.prdwid"
          placeholder="请输入聘任单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地校" prop="jdxid">
        <el-input
          v-model="queryParams.jdxid"
          placeholder="请输入基地校"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="毕业院校" prop="byyx">
        <el-input
          v-model="queryParams.byyx"
          placeholder="请输入毕业院校"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
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
          v-hasPermi="['jxjs:jxjsjbxx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jxjsjbxx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jxjsjbxx:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jxjsjbxxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="xb" :formatter="xbFormat" />
      <el-table-column label="政治面貌" align="center" prop="zzmm" :formatter="zzmmFormat" />
      <el-table-column label="民族" align="center" prop="mz" :formatter="mzFormat" />
      <el-table-column label="学历" align="center" prop="xl" :formatter="xlFormat" />
      <el-table-column label="学位" align="center" prop="xw" :formatter="xwFormat" />
      <el-table-column label="是否师范生" align="center" prop="sfsfs" :formatter="sfsfsFormat" />
      <el-table-column label="录取年份" align="center" prop="lqnf" :formatter="lqnfFormat" />
      <!-- <el-table-column label="出生日期" align="center" prop="csrq" width="180">
      <el-table-column label="聘任单位" align="center" prop="prdwid" />
      <el-table-column label="聘任单位名称" align="center" prop="prdwmc" />
      <el-table-column label="基地校" align="center" prop="jdxid" />
      <el-table-column label="任教学段" align="center" prop="rjxd" :formatter="rjxdFormat" />
      <el-table-column label="任教学科" align="center" prop="rjxk" :formatter="rjxkFormat" />
      <el-table-column label="任教年级" align="center" prop="rjnj" :formatter="rjnjFormat" />
      <el-table-column label="毕业院校" align="center" prop="byyx" /> 
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jxjsjbxx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxjsjbxx:remove']"
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

    <!-- 添加或修改见习教师基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
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
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="电子邮件" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮件" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
        </el-form-item>
        <el-form-item label="邮编" prop="yzbm">
          <el-input v-model="form.yzbm" placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="政治面貌" prop="zzmm">
          <el-select v-model="form.zzmm" placeholder="请选择政治面貌">
            <el-option
              v-for="dict in zzmmOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="民族" prop="mz">
          <el-select v-model="form.mz" placeholder="请选择民族">
            <el-option
              v-for="dict in mzOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="聘任单位" prop="prdwid">
          <el-input v-model="form.prdwid" placeholder="请输入聘任单位" />
        </el-form-item>
        <el-form-item label="聘任单位名称" prop="prdwmc">
          <el-input v-model="form.prdwmc" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="基地校" prop="jdxid">
          <el-input v-model="form.jdxid" placeholder="请输入基地校" />
        </el-form-item>
        <el-form-item label="任教学段" prop="rjxd">
          <el-select v-model="form.rjxd" placeholder="请选择任教学段">
            <el-option
              v-for="dict in rjxdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任教学科" prop="rjxk">
          <el-select v-model="form.rjxk" placeholder="请选择任教学科">
            <el-option
              v-for="dict in rjxkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任教年级" prop="rjnj">
          <el-select v-model="form.rjnj" placeholder="请选择任教年级">
            <el-option
              v-for="dict in rjnjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="毕业院校" prop="byyx">
          <el-input v-model="form.byyx" placeholder="请输入毕业院校" />
        </el-form-item>
        <el-form-item label="学历" prop="xl">
          <el-select v-model="form.xl" placeholder="请选择学历">
            <el-option
              v-for="dict in xlOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学位" prop="xw">
          <el-select v-model="form.xw" placeholder="请选择学位">
            <el-option
              v-for="dict in xwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否师范生" prop="sfsfs">
          <el-select v-model="form.sfsfs" placeholder="请选择是否师范生">
            <el-option
              v-for="dict in sfsfsOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="录取年份" prop="lqnf">
          <el-select v-model="form.lqnf" placeholder="请选择录取年份">
            <el-option
              v-for="dict in lqnfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
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
import {
  listJxjsjbxx,
  getJxjsjbxx,
  delJxjsjbxx,
  addJxjsjbxx,
  updateJxjsjbxx,
} from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jxjsjbxx",
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
      // 见习教师基本信息表格数据
      jxjsjbxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 政治面貌字典
      zzmmOptions: [],
      // 民族字典
      mzOptions: [],
      // 任教学段字典
      rjxdOptions: [],
      // 任教学科字典
      rjxkOptions: [],
      // 任教年级字典
      rjnjOptions: [],
      // 学历字典
      xlOptions: [],
      // 学位字典
      xwOptions: [],
      // 是否师范生字典
      sfsfsOptions: [],
      // 录取年份字典
      lqnfOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        otherid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        email: null,
        phone: null,
        yzbm: null,
        zzmm: null,
        mz: null,
        prdwid: null,
        prdwmc: null,
        jdxid: null,
        rjxd: null,
        rjxk: null,
        rjnj: null,
        byyx: null,
        xl: null,
        xw: null,
        sfsfs: null,
        lqnf: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "教师名称不能为空", trigger: "blur" },
        ],
        jxbh: [
          { required: true, message: "进修编号不能为空", trigger: "blur" },
        ],
        xb: [{ required: true, message: "教师性别不能为空", trigger: "blur" }],
        csrq: [
          { required: true, message: "出生日期不能为空", trigger: "blur" },
        ],
        phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_zzmm").then((response) => {
      this.zzmmOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.rjxdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.rjxkOptions = response.data;
    });
    this.getDicts("sys_dm_rjnj").then((response) => {
      this.rjnjOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_xw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_dm_sfsfs").then((response) => {
      this.sfsfsOptions = response.data;
    });
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.lqnfOptions = response.data;
    });
  },
  methods: {
    /** 查询见习教师基本信息列表 */
    getList() {
      this.loading = true;
      listJxjsjbxx(this.queryParams).then((response) => {
        this.jxjsjbxxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 政治面貌字典翻译
    zzmmFormat(row, column) {
      return this.selectDictLabel(this.zzmmOptions, row.zzmm);
    },
    // 民族字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
    },
    // 任教学段字典翻译
    rjxdFormat(row, column) {
      return this.selectDictLabel(this.rjxdOptions, row.rjxd);
    },
    // 任教学科字典翻译
    rjxkFormat(row, column) {
      return this.selectDictLabel(this.rjxkOptions, row.rjxk);
    },
    // 任教年级字典翻译
    rjnjFormat(row, column) {
      return this.selectDictLabel(this.rjnjOptions, row.rjnj);
    },
    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 学位字典翻译
    xwFormat(row, column) {
      return this.selectDictLabel(this.xwOptions, row.xw);
    },
    // 是否师范生字典翻译
    sfsfsFormat(row, column) {
      return this.selectDictLabel(this.sfsfsOptions, row.sfsfs);
    },
    // 录取年份字典翻译
    lqnfFormat(row, column) {
      return this.selectDictLabel(this.lqnfOptions, row.lqnf);
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
        otherid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        email: null,
        phone: null,
        yzbm: null,
        zzmm: null,
        mz: null,
        prdwid: null,
        prdwmc: null,
        jdxid: null,
        rjxd: null,
        rjxk: null,
        rjnj: null,
        byyx: null,
        xl: null,
        xw: null,
        sfsfs: null,
        lqnf: null,
        createTime: null,
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
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加见习教师基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJxjsjbxx(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改见习教师基本信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJxjsjbxx(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxjsjbxx(this.form).then((response) => {
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
        '是否确认删除见习教师基本信息数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJxjsjbxx(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
