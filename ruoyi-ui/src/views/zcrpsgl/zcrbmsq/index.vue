<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="方案编号" prop="faid">
        <el-select
          v-model="queryParams.faid"
          placeholder="请选择方案类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in zcrpsfaOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="基地类别" prop="jdtype">
        <el-select
          v-model="queryParams.jdtype"
          placeholder="请选择方案类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jdtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
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
      <!-- 方案tab -->
      <el-tab-pane label="方案列表" name="falb">
        <el-table
          v-loading="loading"
          :data="zcrpsfaList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="方案名称" align="center" prop="name" />
          <el-table-column
            label="基地类型"
            align="center"
            prop="jdtype"
            :formatter="jdtypeFormat"
          />
          <el-table-column
            label="当前状态"
            align="center"
            prop="status"
            :formatter="statusFormat"
          />
          <el-table-column
            label="报名时间"
            align="center"
            prop="starttime"
            width="180"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="结束时间"
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
                v-show="isShow(scope.row)"
                v-hasPermi="['zcrpsgl:zcrpsfa:edit']"
                >报名</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <!-- 我的报名tab -->
      <el-tab-pane label="我的报名" name="jdlb">
        <el-table
          v-loading="loading"
          :data="zcrbmsqList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column
            label="方案编号"
            align="center"
            prop="faid"
            :formatter="faFormat"
          />
          <el-table-column
            label="基地类型"
            align="center"
            prop="jdtype"
            :formatter="jdtypeFormat"
          />
          <el-table-column label="专家组" align="center" prop="zjz" />
          <el-table-column label="姓名" align="center" prop="name" />
          <el-table-column label="进修编号" align="center" prop="jxbh" />
          <el-table-column
            label="性别"
            align="center"
            prop="xb"
            :formatter="xbFormat"
          />
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
          <el-table-column label="单位名称" align="center" prop="dwmc" />
          <el-table-column label="单位地址" align="center" prop="dwdz" />

          <el-table-column
            label="上传方案名称"
            align="center"
            prop="scfaname"
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
                v-hasPermi="['zcrpsgl:zcrbmsq:edit']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['zcrpsgl:zcrbmsq:remove']"
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

    <!-- 添加或修改主持人报名申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="方案编号" prop="faid">
              <el-select
                v-model="form.faid"
                placeholder="请选择方案类型"
                clearable
                size="small"
                :disabled="true"
              >
                <el-option
                  v-for="dict in zcrpsfaOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="基地类别" prop="jdtype">
              <el-select
                v-model="form.jdtype"
                placeholder="请选择方案类型"
                clearable
                size="small"
                :disabled="true"
              >
                <el-option
                  v-for="dict in jdtypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进修编号" prop="jxbh">
              <el-input v-model="form.jxbh" placeholder="请输入进修编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="xb">
              <el-select
                v-model="form.xb"
                placeholder="请选择性别"
                clearable
                size="small"
                :disabled="true"
              >
                <el-option
                  v-for="dict in xbOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="学段" prop="xd" >
              <el-select
                v-model="form.xd"
                placeholder="请选择任教学段"
                clearable
                size="small"
                :disabled="true"
              >
                <el-option
                  v-for="dict in xdOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学科" prop="xk">
              <el-select
                v-model="form.xk"
                placeholder="请选择任教学科"
                clearable
                size="small"
                :disabled="true"
              >
                <el-option
                  v-for="dict in xkOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位名称" prop="dwmc">
              <el-input v-model="form.dwmc" placeholder="请输入单位名称" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位地址" prop="dwdz">
              <el-input v-model="form.dwdz" placeholder="请输入单位地址" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身份" prop="sf">
              <el-select
                v-model="form.sf"
                placeholder="请选择身份"
                clearable
                size="small"
              >
                <el-option
                  v-for="dict in sfOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专家组" prop="zjzid">
              <el-select
                v-model="form.zjzid"
                multiple
                placeholder="请选择专家"
                clearable
                size="small"
              >
                <el-option
                  v-for="dict in zjzOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="上传文件" prop="scfapath">
              <el-input v-model="form.scfaname" v-if="false" />
              <el-input v-model="form.scfapath" v-if="false" />
              <el-upload
                class="upload-demo"
                :action="uploadFileUrl"
                :headers="headers"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList"
                :on-success="handleAvatarSuccess"
              >
                <el-button size="small" type="primary">选择文件</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看评审方案(主持人评审管理-评审方案)对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open_check"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="方案名称" prop="name">
          <el-input
            v-model="form.name"
            placeholder="请输入方案名称"
            :disabled="true"
          />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-select
            v-model="form.status"
            placeholder="请选择当前状态"
            :disabled="true"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基地类型" prop="jdtype">
          <el-select
            v-model="form.jdtype"
            placeholder="请选择基地类型"
            :disabled="true"
          >
            <el-option
              v-for="dict in jdtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名时间" prop="starttime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            :disabled="true"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择报名时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            :disabled="true"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listZcrbmsq,
  getZcrbmsq,
  delZcrbmsq,
  addZcrbmsq,
  updateZcrbmsq,
  exportZcrbmsq,
} from "@/api/zcrpsgl/zcrbmsq";
import {
  listZcrpsfa,
  getZcrpsfa,
  delZcrpsfa,
  addZcrpsfa,
  updateZcrpsfa,
  exportZcrpsfa,
  openStatusZcrpsfa,
  stopStatusZcrpsfa,
  listJdx,
  selectJdtype,
} from "@/api/zcrpsgl/zcrpsfa";
import {
  listJsjbxx,
  getJsjbxx,
  delJsjbxx,
  addJsjbxx,
  updateJsjbxx,
} from "@/api/qtjs/jsjbxx";
import { listZcrzjzmd, getZcrzjzmd } from "@/api/zcrpsgl/zcrzjzmd";
import { getUserProfile } from "@/api/system/user";
import { listXxjbxx } from "@/api/qtjs/xxjbxx";
import { getToken } from "@/utils/auth";

export default {
  name: "Zcrbmsq",
  data() {
    return {
      activeName: "falb",
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
      // 主持人报名申请表格数据
      zcrbmsqList: [],
      //基地类型--数据字典
      jdtypeOptions: [],
      //当前状态--数据字典
      statusOptions: [],
      // 性别字典
      xbOptions: [],
      // 任教学科字典
      xkOptions: [],
      xdOptions: [],
      // 身份字典
      sfOptions: [],
      // 评审方案(主持人评审管理-评审方案)表格数据
      zcrpsfaList: [],
      // 专家组信息名单
      zjzOptions: [],
      // 主持人评审方案
      zcrpsfaOptions: [],
      // 上传文件list
      fileList: [],
      // 获取登录人信息
      user: [],
      // 方案数组
      faArray: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open_check: false,
      // 查询教师基本信息
      queryParams_jsjbxx: {
        dabh: null,
      }, 
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        zjzid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        xd: null,
        xk: null,
        dwmc: null,
        dwdz: null,
        jdtype: null,
        sf: null,
        phone: null,
        email: null,
        dwfzr: null,
        dwfzrphone: null,
        scfapath: null,
        scfaname: null,
        createUser: null,
        zjz: null,
      },
      // 查询参数
      queryParams_zjz: {
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    this.getPsfaList();
    this.getUser();
    this.getDicts("sys_dm_zcrfadqzt").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_zcrjdtype").then((response) => {
      this.jdtypeOptions = response.data;
    });
    listZcrpsfa().then((response) => {
      this.zcrpsfaOptions = response.rows;
    });
    this.getDicts("sys_user_sex").then((response) => {
      this.xbOptions = response.data;
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
    /** 查询主持人报名申请列表 */
    getList() {
      this.loading = true;
      listZcrbmsq(this.queryParams).then((response) => {
        this.zcrbmsqList = response.rows;
        this.total = response.total;
        this.popArray();
        this.loading = false;
      });
    },
    // 切换tab
    handleChangeTab(tab, event) {},
    /** 查询评审方案(主持人评审管理-评审方案)列表 */
    getPsfaList() {
      this.loading = true;
      listZcrpsfa(null).then((response) => {
        this.zcrpsfaList = response.rows;
        this.loading = false;
      });
    },
    // 获取登录用户档案编号
    getUser() {
      getUserProfile().then((response) => {
        this.queryParams_jsjbxx.dabh = response.data.userName;
      });
    },
    /** 查看按钮操作 */
    handleCheck(row) {
      this.reset();
      const id = row.id || this.ids;
      getZcrpsfa(id).then((response) => {
        this.form_check = response.data;
        this.open_check = true;
        this.title = "查看主持人评审方案";
      });
    },
    // 方案字典翻译
    faFormat(row, column) {
      var actions = [];
      var datas = this.zcrpsfaOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译--当前状态
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 字典翻译--基地类型
    jdtypeFormat(row, column) {
      return this.selectDictLabel(this.jdtypeOptions, row.jdtype);
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 学科字典翻译
    xkFormat(row, column) {
      return this.selectDictLabel(this.xkOptions, row.xk);
    },
    // 学段字典翻译
    xdFormat(row, column) {
      return this.selectDictLabel(this.xdOptions, row.xd);
    },
    // 数组传参
    popArray() {
      for (let index = 0; index < this.zcrbmsqList.length; index++) {
        this.faArray.push(this.zcrbmsqList[index].faid);
      }
      return this.faArray;
    },

    //设置是否可用
    isShow(row) {
      if (this.faArray.includes(row.id)) {
        return false;
      } else {
        return true;
      }
    },
    // 获取登录用户id
    getUser() {
      getUserProfile().then((response) => {
        this.queryParams_zjz.createUserid = response.data.userId;
        this.queryParams_jsjbxx.dabh = response.data.userName;
        this.getZjzmdList();
      });
    },

    // 文件上传
    handlePreview(file) {
      //console.log(file);
    },
    // 文件上传移除
    handleRemove(file, fileList) {
      // console.log(file);
      if (file.response.code == "200") {
        this.form.scfapath = "";
      }
    },
    // 文件上传 弹窗
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 文件上传  限制
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    // 文件上传 成功钩子
    handleAvatarSuccess(res, file) {
      // console.log(res, file);
      if (res.code == "200") {
        this.form.scfapath = res.fileName;
        this.form.scfaname = file.name;
      } else {
        this.msgError(res.msg);
      }
    },

    /** 查看按钮操作 */
    getZjzmdList() {
      listZcrzjzmd(this.queryParams_zjz).then((response) => {
        this.zjzOptions = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open_check = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        faid: null,
        zjzid: null,
        name: null,
        jxbh: null,
        xb: null,
        csrq: null,
        xd: null,
        xk: null,
        dwmc: null,
        dwdz: null,
        jdtype: null,
        sf: null,
        phone: null,
        email: null,
        dwfzr: null,
        dwfzrphone: null,
        scfapath: null,
        scfaname: null,
        createTime: null,
        createUser: null,
        zjz: null,
      };
      this.resetForm("form");
      this.fileList = [];
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
    /** 报名按钮操作 */
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
      getZcrpsfa(id).then((response) => {
        this.form.faid = response.data.id;
        this.form.jdtype = response.data.jdtype;
        this.open = true;
        this.title = "修改主持人报名申请";
      });
      this.open = true;
      this.title = "添加主持人报名申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getZcrbmsq(id).then((response) => {
        this.form = response.data;
        // 将字符串转换成数组，此时是字符串数组
        var arrIntZjz = [];
        if (null != row.zjzid && "" != row.zjzid) {
          var arrString = row.zjzid.split(",");
          // 将字符串数组的每一项转换成Number，生成一个新的数组
          for (var arrInt in arrString) {
            arrIntZjz.push(parseInt(arrString[arrInt]));
          }
        }
        // 将新的Number数组，绑定到select空间的v-model上
        this.form.zjzid = arrIntZjz;
        this.open = true;
        this.title = "修改主持人报名申请";
        this.fileList.push({
          name: response.data.scfaname,
          url: response.data.scfapath,
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            let zjzArr = [];
            for (let i = 0; i < this.form.zjzid.length; i++) {
              zjzArr.push(this.form.zjzid[i]);
            }
            this.form.zjzid = zjzArr.join();
            updateZcrbmsq(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            let zjzArr = [];
            for (let i = 0; i < this.form.zjzid.length; i++) {
              zjzArr.push(this.form.zjzid[i]);
            }
            this.form.zjzid = zjzArr.join();
            addZcrbmsq(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("报名成功");
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
        '是否确认删除主持人报名申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delZcrbmsq(ids);
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
      this.$confirm("是否确认导出所有主持人报名申请数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportZcrbmsq(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
