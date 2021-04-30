<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="报名基地" prop="jdid">
        <el-select v-model="form.jdid" placeholder="请选择报名基地" >
            <el-option
              v-for="dict in zcrjdcjList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
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
        <el-select
          v-model="queryParams.jdtype"
          placeholder="请选择基地类别"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jdtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
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

    <el-tabs v-model="activeName" @tab-click="handleChangeTab">
      <!-- 基地报名tab -->
      <el-tab-pane label="基地列表" name="jdlb">
        <el-table v-loading="loading" :data="zcrjdcjList">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="基地名称" align="center" prop="name" />
          <el-table-column
            label="主持人"
            align="center"
            prop="zcrid"
            :formatter="ZcrFormat"
          />
          <el-table-column
            label="基地类型"
            align="center"
            prop="jdtype"
            :formatter="jdtypeFormat"
          />
          <el-table-column label="招生名额" align="center" prop="zsme" />
          <el-table-column
            label="报名开始时间"
            align="center"
            prop="starttime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="报名结束时间"
            align="center"
            prop="endtime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.endtime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
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
                @click="handleAdd(scope.row)"
                v-show="isShow_bm(scope.row)"
                v-hasPermi="['xypsgl:xybmsq:add']"
                >报名</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 我的报名tab -->
      <el-tab-pane label="我的报名" name="wdbm">
        <el-table
          v-loading="loading"
          :data="xybmsqList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column
            label="报名基地"
            align="center"
            prop="jdid"
            :formatter="JdFormat"
          />
          <el-table-column label="姓名" align="center" prop="name" />
          <el-table-column label="进修编号" align="center" prop="jxbh" />
          <el-table-column
            label="学段"
            align="center"
            prop="xd"
            :formatter="xdFormat"
          />
          <el-table-column
            label="学科"
            align="center"
            prop="xk"
            :formatter="xkFormat"
          />
          <el-table-column
            label="基地类别"
            align="center"
            prop="jdtype"
            :formatter="jdtypeFormat"
          />
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
                v-hasPermi="['xypsgl:xybmsq:edit']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['xypsgl:xybmsq:remove']"
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
      </el-tab-pane>
    </el-tabs>

    <!-- 添加或修改学员报名申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="报名基地" prop="jdid">
          <el-select v-model="form.jdid" placeholder="请选择报名基地id" :disabled="true">
            <el-option
              v-for="dict in zcrjdcjList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基地类别" prop="jdtype">
          <el-select v-model="form.jdtype" placeholder="请选择基地类别" :disabled="true">
            <el-option
              v-for="dict in jdtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" :disabled="true"/>
        </el-form-item>
        <el-form-item label="进修编号" prop="jxbh">
          <el-input v-model="form.jxbh" placeholder="请输入进修编号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-select v-model="form.xb" placeholder="请选择性别" :disabled="true">
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
            :disabled="true"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学段" prop="xd">
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
        <el-form-item label="职称" prop="zc">
          <el-select v-model="form.zc" placeholder="请选择职称" :disabled="true">
            <el-option
              v-for="dict in zcOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位名称" prop="dwmc">
          <el-input v-model="form.dwmc" placeholder="请输入单位名称" :disabled="true"/>
        </el-form-item>
        <el-form-item label="单位地址" prop="dwdz">
          <el-input v-model="form.dwdz" placeholder="请输入单位地址" :disabled="true"/>
        </el-form-item>

        <el-form-item label="身份" prop="sf">
          <el-select v-model="form.sf" placeholder="请选择身份" >
            <el-option
              v-for="dict in sfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" :disabled="true"/>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入电子邮箱" :disabled="true"/>
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
  listXybmsq,
  getXybmsq,
  delXybmsq,
  addXybmsq,
  updateXybmsq,
  exportXybmsq,
} from "@/api/xypsgl/xybmsq";
import {
  listJsjbxx,
  getJsjbxx,
  delJsjbxx,
  addJsjbxx,
  updateJsjbxx,
} from "@/api/qtjs/jsjbxx";
import {
  listZcrjdcj,
  getZcrjdcj,
  delZcrjdcj,
  addZcrjdcj,
  updateZcrjdcj,
  exportZcrjdcj,
} from "@/api/zcrpsgl/zcrjdcj";
import { listZcrbmsq, getZcrbmsq } from "@/api/zcrpsgl/zcrbmsq";
import { listXxjbxx } from "@/api/qtjs/xxjbxx";
import { getUserProfile } from "@/api/system/user";

export default {
  name: "Xybmsq",
  data() {
    return {
      activeName: "jdlb",
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
      // 主持人基地表格数据
      zcrjdcjList: [],
      // 主持人选项
      zcrOptions: [],
      
      // 当前报名人数
      dqybm: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jdid: null,
        jsid: null,
        jxbh: null,
        jdtype: null,
        sf: null,
        phone: null,
        email: null,
        createUser: null,
        xxshstatus: null,
        xxshjy: null,
        qjshstatus: null,
        qjshjy: null,
        xyxbstatus: null,
      },
      // 查询报名名额参数
      queryParams_bm: {
        jdid: null,
      },

      // 查询教师基本信息
      queryParams_jsjbxx: {
        dabh: null,
      },      
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getZcrList();
    this.getUser();
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.xdOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.xkOptions = response.data;
    });
    this.getDicts("sys_dm_zcrzjzzc").then((response) => {
      this.zcOptions = response.data;
    });
    this.getDicts("sys_dm_zcrjdtype").then((response) => {
      this.jdtypeOptions = response.data;
    });
    this.getDicts("sys_dm_zcrsf").then((response) => {
      this.sfOptions = response.data;
    });
  },
  methods: {
    /** 查询学员报名申请列表 */
    getList() {
      this.loading = true;
      listXybmsq(this.queryParams).then((response) => {
        this.xybmsqList = response.rows;
        this.total = response.total;
        this.getJdList();
        this.loading = false;
      });
    },
    /** 查询主持人基地列表 */
    getJdList() {
      this.loading = true;
      listZcrjdcj(null).then((response) => {
        this.zcrjdcjList = response.rows;
        this.loading = false;
      });
    },
    // /** 查询教师基本信息列表 */
    // getJdList() {
    //   this.loading = true;
    //   listZcrjdcj(null).then((response) => {
    //     this.zcrjdcjList = response.rows;
    //     this.loading = false;
    //   });
    // },
    /** 查询主持人报名申请列表 */
    getZcrList() {
      listZcrbmsq(null).then((response) => {
        this.zcrOptions = response.rows;
      });
    },
    // 主持人字典翻译
    ZcrFormat(row, column) {
      var actions = [];
      var datas = this.zcrOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].jsid == "" + row.zcrid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 基地字典翻译
    JdFormat(row, column) {
      var actions = [];
      var datas = this.zcrjdcjList;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.jdid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 报名是否显示
    isShow_bm(row) {
      this.queryParams_bm.jdid = row.id;
      listXybmsq(this.queryParams_bm).then((response) => {
        this.dqybm = response.rows.length;
      });
      if (this.dqybm < row.zsme) {
        return true;
      } else {
        return false;
      }
    },
    // 获取登录用户档案编号
    getUser() {
      getUserProfile().then((response) => {
        this.queryParams_jsjbxx.dabh = response.data.userName;
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
    // 切换tab
    handleChangeTab(tab, event) {},
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
        jsid: null,
        jxbh: null,
        jdtype: null,
        sf: null,
        createTime: null,
        createUser: null,
        xxshstatus: null,
        xxshjy: null,
        qjshstatus: null,
        qjshjy: null,
        xyxbstatus: null,
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
    handleAdd(row) {
      this.reset();
      listJsjbxx(this.queryParams_jsjbxx).then((response) => {
        this.form.name = response.rows[0].jsxm;
        this.form.jsid = response.rows[0].jsid;
        this.form.jxbh = response.rows[0].dabh;
        this.form.xb = response.rows[0].xb;
        this.form.csrq = response.rows[0].csrq;
        this.form.xd = response.rows[0].xd;
        this.form.xk = response.rows[0].xk;
        this.form.zc = response.rows[0].zc;
        this.form.xl = response.rows[0].xl;
        this.form.dwmc = response.rows[0].tsbzXxjbxx.xxmc;
        this.form.dwdz = response.rows[0].tsbzXxjbxx.xxdz;
        this.form.email = response.rows[0].email;
        this.form.phone = response.rows[0].phone;
      });
      const id = row.id || this.ids;
      this.form.jdid = id;
      getZcrjdcj(id).then((response) => {
        this.form.jdtype = response.data.jdtype;
        this.open = true;
        this.title = "基地报名";
      });  
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getXybmsq(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学员报名申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateXybmsq(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addXybmsq(this.form).then((response) => {
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
        '是否确认删除学员报名申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delXybmsq(ids);
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
