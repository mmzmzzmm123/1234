<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="基地类型" prop="jdtype">
        <el-select
          v-model="queryParams.jdtype"
          placeholder="请选择基地类型"
        >
          <el-option
            v-for="dict in jdtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="dwmc">
        <el-select
          v-model="queryParams.dwmc"
          placeholder="请选择部门"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in deptOptions"
            :key="dict.xxmc"
            :label="dict.xxmc"
            :value="dict.xxmc"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="身份" prop="sf">
        <el-select
          v-model="queryParams.sf"
          placeholder="请选择身份"
        >
          <el-option
            v-for="dict in sfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
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
          @click="handlePass"
          v-hasPermi="['zcrpsgl:zcrjgqr:passJgqrStatus']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleBack"
          v-hasPermi="['zcrpsgl:zcrjgqr:backJgqrStatus']"
        >退回</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zcrjgqrList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="教师姓名" align="center" prop="name" />
      <el-table-column label="身份" align="center" prop="sf"  :formatter="sfFormat"/>
      <el-table-column
        label="基地类型"
        align="center"
        prop="jdtype"
        :formatter="jdtypeFormat"
      />
      <el-table-column label="学段" align="center" prop="xd" :formatter="xdFormat"/>
      <el-table-column label="学科" align="center" prop="xk" :formatter="xkFormat"/>
      <el-table-column
        label="结果确认当前状态"
        align="center"
        prop="jgqrStatus"
        :formatter="jgqrStatusFormat"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleLook(scope.row)"
            v-hasPermi="['zcrpsgl:zcrjgqr:look']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.jgqrStatus=='1'?false:true"
            @click="handlePass(scope.row)"
            v-hasPermi="['zcrpsgl:zcrjgqr:passJgqrStatus']"
          >通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.jgqrStatus=='2'?false:true"
            @click="handleBack(scope.row)"
            v-hasPermi="['zcrpsgl:zcrjgqr:backJgqrStatus']"
          >退回</el-button>
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

    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="方案编号" prop="name">
          {{ form.faid }}
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          {{ form.name }}
        </el-form-item>
        <el-form-item label="学段">
          <el-select v-model="form.xd" placeholder="请选择学段" :disabled="true">
            <el-option
              v-for="dict in xdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"

            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学科" prop="xk">
          <el-select v-model="form.xk" placeholder="请选择学科" :disabled="true">
            <el-option
              v-for="dict in xkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学校审核状态" prop="xxshStatus">
          <el-select v-model="form.xxshStatus" placeholder="请选择当前状态" :disabled="true">
            <el-option
              v-for="dict in xxshStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核状态" prop="qjshStatus">
          <el-select v-model="form.qjshStatus" placeholder="请选择当前状态" :disabled="true">
            <el-option
              v-for="dict in qjshStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结果确认状态" prop="jgqrStatus">
          <el-select v-model="form.jgqrStatus" placeholder="请选择当前状态" :disabled="true">
            <el-option
              v-for="dict in jgqrStatusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { passJgqrStatus,  backJgqrStatus } from "@/api/zcrpsgl/zcrjgqr";
  import { listZcrbmsqJgqr,  getZcrbmsq } from "@/api/zcrpsgl/zcrbmsq";
  import { listXxjbxx } from "@/api/qtjs/xxjbxx";

  export default {
    name: "Zcrjgqr",
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
        // 区级审核表格数据
        zcrjgqrList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //基地类型--数据字典
        jdtypeOptions: [],
        //结果确认状态--数据字典
        jgqrStatusOptions: [],
        //区级审核状态--数据字典
        qjshStatusOptions: [],
        //学校审核状态--数据字典
        xxshStatusOptions: [],
        //学段--数据字典
        xdOptions: [],
        //学科--数据字典
        xkOptions: [],
        //身份--数据字典
        sfOptions: [],
        //学校
        deptOptions: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          jdtype: null,
          dwmc: null,
          sf: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        }
      };
    },
    created() {
      this.getxxList();
      this.getList();
      //基地类型
      this.getDicts("sys_dm_zcrjdtype").then((response) => {
        this.jdtypeOptions = response.data;
      });

      this.getDicts("sys_dm_zcrjgqrzt").then((response) => {
        this.jgqrStatusOptions = response.data;
      });

      this.getDicts("sys_dm_zcrshzt").then((response) => {
        this.qjshStatusOptions = response.data;
      });

      this.getDicts("sys_dm_zcrshzt").then((response) => {
        this.xxshStatusOptions = response.data;
      });
      this.getDicts("sys_dm_rjxk").then((response) => {
        this.xkOptions = response.data;
      });
      this.getDicts("sys_dm_rjxd").then((response) => {
        this.xdOptions = response.data;
      });
      this.getDicts("sys_dm_zcrsf").then((response) => {
        this.sfOptions = response.data;
      });

    },
    methods: {
      //获取基地校列表
      getxxList() {
        listXxjbxx(null).then((response) => {
          this.deptOptions = response.rows;
        });
      },
      /** 查询学校审核列表 */
      getList() {
        this.loading = true;
        listZcrbmsqJgqr(this.queryParams).then(response => {
          this.zcrjgqrList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 字典翻译--基地类型
      jdtypeFormat(row, column) {
        return this.selectDictLabel(this.jdtypeOptions, row.jdtype);
      },
      // 学科字典翻译
      xkFormat(row, column) {
        return this.selectDictLabel(this.xkOptions, row.xk);
      },
      // 学段字典翻译
      xdFormat(row, column) {
        return this.selectDictLabel(this.xdOptions, row.xd);
      },
      // 身份字典翻译
      sfFormat(row, column) {
        return this.selectDictLabel(this.sfOptions, row.sf);
      },

      jgqrStatusFormat(row, column) {
        return this.selectDictLabel(this.jgqrStatusOptions, row.jgqrStatus);
      },


      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          jdtype: null,
          sf: null
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

      /** 查看操作 */
      handleLook(row) {
        this.reset();
        const id = row.id || this.ids;
        getZcrbmsq(id).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "主持人报名明细详情";
        });
      },

      /** 审核通过操作 */
      handlePass(row) {
        const id = row.id|| this.ids;
        getZcrbmsq(id).then((response) => {
          passJgqrStatus(response.data).then(response => {
            if (response.code === 200) {
              this.msgSuccess("审核成功");
              this.open = false;
              this.getList();
            }
          });
        });
      },

      /** 退回操作 */
      handleBack(row) {
        const id = row.id|| this.ids;
        getZcrbmsq(id).then((response) => {
          backJgqrStatus(response.data).then(response => {
            if (response.code === 200) {
              this.msgSuccess("审核成功");
              this.open = false;
              this.getList();
            }
          });
        });
      }

    }
  };
</script>
