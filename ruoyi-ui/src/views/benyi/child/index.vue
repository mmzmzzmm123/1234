<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
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
        <el-select
          v-model="queryParams.xb"
          placeholder="请选择性别"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in sexOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['benyi:child:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:child:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:child:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          :disabled="multiple"
          @click="handleTiaoban"
          v-hasPermi="['benyi:child:edit']"
        >调班</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="childList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <!-- <el-table-column label="幼儿园名称" align="center" prop="schoolid" /> -->
      <el-table-column label="班级名称" align="center" prop="classid" :formatter="classFormat" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="家长手机号码" align="center" prop="phone" />
      <el-table-column label="性别" align="center" prop="xb" :formatter="xbFormat" />
      <el-table-column label="民族" align="center" prop="mz" :formatter="mzFormat" />
      <el-table-column label="详细地址" show-overflow-tooltip align="center" prop="addrDetail" />
      <el-table-column label="曾经就读幼儿园" align="center" prop="everSchool" />
      <el-table-column label="是否学习英语" align="center" prop="learnEnglish" :formatter="ynFormat" />
      <el-table-column label="入园渠道" align="center" prop="source" :formatter="sourceFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:child:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:child:remove']"
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

    <!-- 添加或修改幼儿信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="英文名" prop="enName">
              <el-input v-model="form.enName" placeholder="请输入英文名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="小名" prop="infantName">
              <el-input v-model="form.infantName" placeholder="请输入小名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入家长手机号码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="xb">
              <el-select v-model="form.xb" placeholder="请选择性别">
                <el-option
                  v-for="dict in sexOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="证件号码" prop="zjhm">
              <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
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
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="出生地" prop="birthProvincename">
              <v-distpicker
                v-model="form.birthProvincename"
                :placeholders="placeholders"
                :province="diglogForm.province"
                :city="diglogForm.city"
                :area="diglogForm.area"
                @selected="onSelected_brith"
              ></v-distpicker>
              <el-input v-model="form.birthProvince" v-if="false" />
              <el-input v-model="form.birthCity" v-if="false" />
              <el-input v-model="form.birthArea" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="户口地" prop="registeredProvincename">
              <v-distpicker
                v-model="form.registeredProvincename"
                :placeholders="placeholders"
                :province="diglogForm1.province"
                :city="diglogForm1.city"
                :area="diglogForm1.area"
                @selected="onSelected_registered"
              ></v-distpicker>
              <el-input v-model="form.registeredProvince" v-if="false" />
              <el-input v-model="form.registeredCity" v-if="false" />
              <el-input v-model="form.registeredArea" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="现住址" prop="addrProvincename">
              <v-distpicker
                v-model="form.addrProvincename"
                :placeholders="placeholders"
                :province="diglogForm2.province"
                :city="diglogForm2.city"
                :area="diglogForm2.area"
                @selected="onSelected_addr"
              ></v-distpicker>
              <el-input v-model="form.addrProvince" v-if="false" />
              <el-input v-model="form.addrCity" v-if="false" />
              <el-input v-model="form.addrArea" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="详细地址" prop="addrDetail">
              <el-input v-model="form.addrDetail" placeholder="请输入详细地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="曾就读园" prop="everSchool">
              <el-input v-model="form.everSchool" placeholder="请输入曾经就读幼儿园" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学习英语" prop="learnEnglish">
              <el-radio-group v-model="form.learnEnglish">
                <el-radio
                  v-for="dict in ynOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入园渠道" prop="source">
              <el-select v-model="form.source" placeholder="请选择入园渠道">
                <el-option
                  v-for="dict in sourceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第一语言" prop="firstLanguage">
              <el-input v-model="form.firstLanguage" placeholder="请输入第一语言" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="第二语言" prop="seconderLanguage">
              <el-input v-model="form.seconderLanguage" placeholder="请输入第二语言" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="其他语言" prop="otherLanguage">
              <el-input v-model="form.otherLanguage" placeholder="请输入其他语言" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="选择班级" prop="classid">
              <el-select v-model="form.classid" placeholder="请选择班级">
                <el-option
                  v-for="dict in classOptions"
                  :key="dict.bjbh"
                  :label="dict.bjmc"
                  :value="dict.bjbh"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 幼儿调班信息对话框 -->
    <el-dialog title="幼儿调班" :visible.sync="open_tb" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules_tb" label-width="80px">
        <el-form-item label="选择班级" prop="classid">
          <el-select v-model="form.classid" placeholder="请选择班级">
            <el-option
              v-for="dict in classOptions"
              :key="dict.bjbh"
              :label="dict.bjmc"
              :value="dict.bjbh"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm_tb">确 定</el-button>
        <el-button @click="cancel_tb">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listChild,
  getChild,
  delChild,
  addChild,
  updateChild,
  updateChild_tb,
} from "@/api/benyi/child";

import { listClass } from "@/api/system/class";

//导入省市区三级联动库
import VDistpicker from "v-distpicker";

export default {
  name: "Child",
  data() {
    return {
      placeholders: {
        province: "请选择省",
        city: "请选择市",
        area: "请选择区",
      },
      diglogForm: {
        province: null,
        city: null,
        area: null,
      },
      diglogForm1: {
        province: null,
        city: null,
        area: null,
      },
      diglogForm2: {
        province: null,
        city: null,
        area: null,
      },
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
      // 幼儿信息表格数据
      childList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open_tb: false,
      // 状态数据字典
      statusOptions: [],
      // 性别状态字典
      sexOptions: [],
      // 民族字典
      mzOptions: [],
      //是否字典
      ynOptions: [],
      //入园渠道
      sourceOptions: [],
      //班级
      classOptions: [],
      //是否可编辑
      isable: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        enName: undefined,
        infantName: undefined,
        phone: undefined,
        xb: undefined,
        mz: undefined,
        zjhm: undefined,
        csrq: undefined,
        birthProvince: undefined,
        birthProvincename: undefined,
        birthCity: undefined,
        birthCityname: undefined,
        birthArea: undefined,
        birthAreaname: undefined,
        registeredProvince: undefined,
        registeredProvincename: undefined,
        registeredCity: undefined,
        registeredCityname: undefined,
        registeredArea: undefined,
        registeredAreaname: undefined,
        addrProvince: undefined,
        addrProvincename: undefined,
        addrCity: undefined,
        addrCityname: undefined,
        addrArea: undefined,
        addrAreaname: undefined,
        addrDetail: undefined,
        everSchool: undefined,
        learnEnglish: undefined,
        source: undefined,
        status: undefined,
        enterDate: undefined,
        outDate: undefined,
        firstLanguage: undefined,
        seconderLanguage: undefined,
        otherLanguage: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "幼儿姓名不能为空", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "家长手机号码不能为空", trigger: "blur" },
        ],
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
      },
      rules_tb: {
        classid: [{ required: true, message: "班级不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getDicts("sys_user_sex").then((response) => {
      this.sexOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then((response) => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.ynOptions = response.data;
    });
    this.getDicts("sys_dm_ryqd").then((response) => {
      this.sourceOptions = response.data;
    });
  },
  components: {
    //省市区三级联动全局组件
    VDistpicker,
  },
  methods: {
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.xb);
    },
    // 字典翻译
    mzFormat(row, column) {
      return this.selectDictLabel(this.mzOptions, row.mz);
    },
    // 字典翻译
    ynFormat(row, column) {
      return this.selectDictLabel(this.ynOptions, row.learnEnglish);
    },
    // 字典翻译
    sourceFormat(row, column) {
      return this.selectDictLabel(this.sourceOptions, row.source);
    },
    // 字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 字典翻译
    classFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    /** 查询幼儿信息列表 */
    getList() {
      this.loading = true;
      listChild(this.queryParams).then((response) => {
        this.childList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel_tb() {
      this.open_tb = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        schoolid: undefined,
        classid: undefined,
        name: undefined,
        enName: undefined,
        infantName: undefined,
        phone: undefined,
        xb: undefined,
        mz: undefined,
        zjhm: undefined,
        csrq: undefined,
        birthProvince: undefined,
        birthProvincename: undefined,
        birthCity: undefined,
        birthCityname: undefined,
        birthArea: undefined,
        birthAreaname: undefined,
        registeredProvince: undefined,
        registeredProvincename: undefined,
        registeredCity: undefined,
        registeredCityname: undefined,
        registeredArea: undefined,
        registeredAreaname: undefined,
        addrProvince: undefined,
        addrProvincename: undefined,
        addrCity: undefined,
        addrCityname: undefined,
        addrArea: undefined,
        addrAreaname: undefined,
        addrDetail: undefined,
        everSchool: undefined,
        learnEnglish: "Y",
        source: undefined,
        status: "0",
        enterDate: undefined,
        outDate: undefined,
        firstLanguage: undefined,
        seconderLanguage: undefined,
        otherLanguage: undefined,
        createuserid: undefined,
        createTime: undefined,
      };

      this.diglogForm.province = "";
      this.diglogForm.city = "";
      this.diglogForm.area = "";

      this.diglogForm1.province = "";
      this.diglogForm1.city = "";
      this.diglogForm1.area = "";

      this.diglogForm2.province = "";
      this.diglogForm2.city = "";
      this.diglogForm2.area = "";

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
      this.title = "添加幼儿信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getChild(id).then((response) => {
        this.form = response.data;

        this.diglogForm.province = response.data.birthProvincename;
        this.diglogForm.city = response.data.birthCityname;
        this.diglogForm.area = response.data.birthAreaname;

        this.diglogForm1.province = response.data.registeredProvincename;
        this.diglogForm1.city = response.data.registeredCityname;
        this.diglogForm1.area = response.data.registeredAreaname;

        this.diglogForm2.province = response.data.addrProvincename;
        this.diglogForm2.city = response.data.addrCityname;
        this.diglogForm2.area = response.data.addrAreaname;

        this.open = true;
        this.title = "修改幼儿信息";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateChild(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addChild(this.form).then((response) => {
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
    /** 提交按钮 */
    submitForm_tb: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          console.log(this.ids + "---" + this.form.classid);
          updateChild_tb(this.form, this.ids).then((response) => {
            if (response.code === 200) {
              this.msgSuccess("调班成功");
              this.open_tb = false;
              this.getList();
            }
          });
        }
      });
    },
    /** 调班按钮操作 */
    handleTiaoban(row) {
      this.reset();
      const ids = row.id || this.ids;
      //console.log(ids);
      this.open_tb = true;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除幼儿信息编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delChild(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    //所在省市区触发联动方法
    onSelected_brith(data) {
      console.log("onSelected_brith=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.birthProvincename = undefined;
      } else {
        this.form.birthProvincename = data.province.value;
        this.form.birthProvince = data.province.code;
        this.form.birthCityname = data.city.value;
        this.form.birthCity = data.city.code;
        this.form.birthAreaname = data.area.value;
        this.form.birthArea = data.area.code;
      }
    },
    //户口所在地
    onSelected_registered(data) {
      console.log("onSelected_registered=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.registeredProvincename = undefined;
      } else {
        this.form.registeredProvincename = data.province.value;
        this.form.registeredProvince = data.province.code;
        this.form.registeredCityname = data.city.value;
        this.form.registeredCity = data.city.code;
        this.form.registeredAreaname = data.area.value;
        this.form.registeredArea = data.area.code;
      }
    },
    //住址所在地
    onSelected_addr(data) {
      console.log("onSelected_addr=" + data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.addrProvincename = undefined;
      } else {
        this.form.addrProvincename = data.province.value;
        this.form.addrProvince = data.province.code;
        this.form.addrCityname = data.city.value;
        this.form.addrCity = data.city.code;
        this.form.addrAreaname = data.area.value;
        this.form.addrArea = data.area.code;
      }
    },
  },
};
</script>