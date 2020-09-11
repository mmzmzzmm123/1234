<template>
  <div class="result-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="幼儿姓名" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入幼儿姓名" clearable size="small" />
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input v-model="queryParams.phone" placeholder="请输入联系方式" clearable size="small" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="handleQuery">查询</el-button>
        <el-button size="mini" @click="cancel">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="main" v-show="hide">
      <h2 class="title">幼儿信息查询与维护</h2>
      <div class="result-form">
        <p class="form-title">提交核对幼儿信息</p>
        <el-form class="form" ref="form" :model="form"  label-width="110px">
          <el-form-item label="父亲姓名" prop="father">
            <el-input v-model="form.father" placeholder="请输入父亲姓名"  @input="onInput()"/>
          </el-form-item>
          <el-form-item label="母亲姓名" prop="mother">
            <el-input v-model="form.mother" placeholder="请输入母亲姓名"  @input="onInput()"/>
          </el-form-item>
          <el-form-item label="家长联系方式" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入联系方式" :disabled="hide" />
          </el-form-item>
          <el-form-item label="幼儿姓名" prop="name">
            <el-input v-model="form.name" placeholder="请输入幼儿姓名" :disabled="hide" />
          </el-form-item>
          <el-form-item label="英文名" prop="enName">
            <el-input v-model="form.enName" placeholder="请输入联系方式" />
          </el-form-item>
          <el-form-item label="小名" prop="infantName">
            <el-input v-model="form.infantName" placeholder="请输入联系方式" />
          </el-form-item>
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
          <el-form-item label="证件号码" prop="zjhm">
            <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
          </el-form-item>
          <el-form-item label="幼儿出生日期" prop="csrq">
            <el-date-picker
              clearable
              size="small"
              v-model="form.csrq"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择幼儿出生日期"
            ></el-date-picker>
          </el-form-item>
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
          <el-form-item label="详细地址" prop="addrDetail">
            <el-input v-model="form.addrDetail" placeholder="请输入详细地址" />
          </el-form-item>
          <el-form-item label="曾就读园" prop="everSchool">
            <el-input v-model="form.everSchool" placeholder="请输入曾经就读幼儿园" />
          </el-form-item>
          <el-form-item label="学习英语" prop="learnEnglish">
            <el-radio-group v-model="form.learnEnglish">
              <el-radio
                v-for="dict in ynOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
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
          <el-form-item label="状态">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in statusOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
              >{{dict.dictLabel}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="第一语言" prop="firstLanguage">
            <el-input v-model="form.firstLanguage" placeholder="请输入第一语言" maxlength="2" />
          </el-form-item>
          <el-form-item label="第二语言" prop="seconderLanguage" >
            <el-input v-model="form.seconderLanguage" placeholder="请输入第二语言"  maxlength="2"/>
          </el-form-item>
          <el-form-item label="其他语言" prop="otherLanguage">
            <el-input v-model="form.otherLanguage" placeholder="请输入其他语言" />
          </el-form-item>
          <div></div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">提 交</el-button>
          <el-button @click="cancelSub">取 消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getChild_query, updateChild } from "@/api/benyi/child";
//导入省市区三级联动库
import VDistpicker from "v-distpicker";

// 更新紧急联系人
import {  updateContactpeople } from "@/api/benyi/contactpeople";

export default {
  name: "result",
  data() {
    return {
      placeholders: {
        province: "请选择省",
        city: "请选择市",
        area: "请选择区"
      },
      diglogForm: {
        province: null,
        city: null,
        area: null
      },
      diglogForm1: {
        province: null,
        city: null,
        area: null
      },
      diglogForm2: {
        province: null,
        city: null,
        area: null
      },
      hide: false,
      ishf: true,
      hfrn: "",
      yzzs: "",
      tynrcontent: "",
      href_tynr: "",
      father: "",
      mother: "",
      // 是否显示弹出层
      open: false,
      // 幼儿信息表格数据
      childList: [],
      // 性别状态字典
      sexOptions: [],
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
      // 查询参数
      queryParams: {
        name: undefined,
        phone: undefined
      },
      // 表单参数
      form: {
      }
    };
  },
  created() {
    // const sid = this.$route.params && this.$route.params.id;
    // this.queryParams.schoolid = sid;
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_mz").then(response => {
      this.mzOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.ynOptions = response.data;
    });
    this.getDicts("sys_dm_ryqd").then(response => {
      this.sourceOptions = response.data;
    });
  },
  components: {
    //省市区三级联动全局组件
    VDistpicker
  },
  methods: {
    // 强制输入刷新
    onInput() {
      this.$forceUpdate();
    },
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
      Object.keys(datas).map(key => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询入班体验申请列表 */
    getInfo() {
      getChild_query(this.queryParams).then(response => {
        if (response.code == "200") {
          this.hide = true;
          this.childList = response.rows;
          this.form = response.data;
          console.log(this.form);
          this.form.id = response.data.id;
          this.form.father = response.data.byChildContactpeople.fathername;
          this.form.mother = response.data.byChildContactpeople.mothername;
          this.diglogForm.province = response.data.birthProvincename;
          this.diglogForm.city = response.data.birthCityname;
          this.diglogForm.area = response.data.birthAreaname;

          this.diglogForm1.province = response.data.registeredProvincename;
          this.diglogForm1.city = response.data.registeredCityname;
          this.diglogForm1.area = response.data.registeredAreaname;

          this.diglogForm2.province = response.data.addrProvincename;
          this.diglogForm2.city = response.data.addrCityname;
          this.diglogForm2.area = response.data.addrAreaname;
        }
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
                //this.open = false;
                this.hide = false;
              }
            });
            this.form.childid = this.form.id;
            this.form.fathername = this.form.father;
            this.form.mothername = this.form.mother;
            updateContactpeople(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                //this.open = false;
                this.hide = false;
              }
            });
          }
        }
      });
    },
    // 取消按钮
    cancelSub() {
      this.hide = false;
    },

    /** 搜索按钮操作 */
    handleQuery() {
      if (
        this.queryParams.name == undefined ||
        this.queryParams.name.trim() == ""
      ) {
        this.msgError("请输入幼儿姓名");
        return false;
      }
      if (
        this.queryParams.phone == undefined ||
        this.queryParams.phone.trim() == ""
      ) {
        this.msgError("请输入联系方式");
        return false;
      }
      this.getInfo();
    },
    //所在省市区触发联动方法
    onSelected_brith(data) {
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
    // 返回按钮
    cancel() {
      this.queryParams = {
        name: undefined,
        phone: undefined
      };
    }
  }
};
</script>
<style lang="scss" scoped>
.result-container {
  padding: 60px 15px 0;
  .main {
    padding-top: 10px;
    border-top: 1px solid #eee;
    .title {
      text-align: center;
      font-size: 18px;
    }
    .result-form {
      .form-title {
        display: flex;
        align-items: center;
        text-align: left;
        text-align: left;
        font-size: 14px;
        font-weight: 700;
        &::before {
          content: "";
          width: 3px;
          height: 14px;
          background: #0a56ca;
          display: inline-block;
          margin: 1px 5px 0 0;
        }
      }
      .form {
        width: 500px;
        margin: 30px auto 0;
      }
    }
    .result-info {
      .title {
        display: flex;
        align-items: center;
        text-align: left;
        font-size: 14px;
        font-weight: 700;
        &::before {
          content: "";
          width: 3px;
          height: 14px;
          background: #0a56ca;
          display: inline-block;
          margin: 1px 5px 0 0;
        }
      }
      .info {
        padding: 0 20px;
      }
    }
  }
}
@media screen and (max-width: 768.89px) {
  .result-container {
    .main {
      .result-form {
        .form {
          width: 100%;
        }
      }
    }
  }
}
</style>