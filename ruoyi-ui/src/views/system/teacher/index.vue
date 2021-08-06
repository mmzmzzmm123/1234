<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="用户名称" prop="userid">
            <el-select
              v-model="queryParams.userid"
              filterable
              placeholder="请选择用户"
            >
              <el-option
                v-for="item in teacherListAll"
                :key="item.userid"
                :label="item.user.nickName"
                :value="item.userid"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <!--<el-form-item label="证件号码" prop="zjhm">
        <el-input
          v-model="queryParams.zjhm"
          placeholder="请输入证件号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>-->
          <el-form-item label="教师学历" prop="xl">
            <el-select
              v-model="queryParams.xl"
              placeholder="请选择学历"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in xlOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="4">
          <el-form-item class="no-margin">
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
        </el-col>
      </el-row>
    </el-form>

    <div class="mb8 btn-list">
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['system:teacher:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['system:teacher:remove']"
        >清空</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['system:teacher:export']"
        >导出</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      border
      :data="teacherList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        fixed
        label="用户名称"
        align="center"
        prop="user.nickName"
      />
      <el-table-column
        label="出生日期"
        align="center"
        prop="csrq"
      ></el-table-column>
      <el-table-column label="毕业院校" align="center" prop="byyx" />
      <el-table-column
        label="学历"
        align="center"
        prop="xl"
        :formatter="xlFormat"
      />
      <el-table-column
        label="入职日期"
        align="center"
        prop="cjgzrq"
      ></el-table-column>
      <!-- <el-table-column
        label="创建时间"
        align="center"
        prop="createtime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column> -->
      <!--<el-table-column label="标识" align="center" prop="id" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="专业" align="center" prop="zy" />
      <el-table-column label="学位" align="center" prop="xw" :formatter="xwFormat" />
      <el-table-column label="资格证书" align="center" prop="zgzs" :formatter="zgzsFormat" />
      <el-table-column label="创建人" align="center" prop="createuserid" />-->
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:teacher:edit']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:teacher:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:teacher:remove']"
            >清空</el-button
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

    <!-- 添加或修改教师基本信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="v-dialog">
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="100px"
        :disabled="flag"
      >
        <el-form-item label="id" prop="id" v-show="false">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item label="用户名称" prop="teacherMingCheng">
          <el-input v-model="teacherMingCheng" :disabled="true" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input
            v-model="form.zjhm"
            placeholder="请输入证件号码"
            maxlength="18"
            @blur.prevent="changeZjhm()"
          />
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.csrq"
            type="date"
            :picker-options="pickerOptions0"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="毕业院校" prop="byyx">
          <el-input v-model="form.byyx" placeholder="请输入毕业院校" />
        </el-form-item>
        <el-form-item label="专业" prop="zy">
          <el-input v-model="form.zy" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="学历">
          <el-select v-model="form.xl" placeholder="请选择学历">
            <el-option
              v-for="dict in xlOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学位">
          <el-select v-model="form.xw" placeholder="请选择学位">
            <el-option
              v-for="dict in xwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期" prop="cjgzrq">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.cjgzrq"
            type="date"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions0"
            placeholder="选择入职日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid" v-show="false">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-show="!flag" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTeacher,
  listTeacher2,
  getTeacher,
  delTeacher,
  addTeacher,
  updateTeacher,
  exportTeacher,
} from "@/api/system/teacher";

export default {
  name: "Teacher",
  data() {
    const idCardValidity = (rule, code, callback) => {
      var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 ",
      };
      var tip = "";
      var pass = true;
      if (
        !code ||
        !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(
          code
        )
      ) {
        tip = "身份证号格式错误";
        pass = false;
      } else if (!city[code.substr(0, 2)]) {
        // tip = "地址编码错误"
        tip = "身份证输入错误";
        pass = false;
      } else {
        // 18位身份证需要验证最后一位校验位
        if (code.length === 18) {
          code = code.split("");
          // ∑(ai×Wi)(mod 11)
          // 加权因子
          var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
          // 校验位
          var parity = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2];
          var sum = 0;
          var ai = 0;
          var wi = 0;
          for (var i = 0; i < 17; i++) {
            ai = code[i];
            wi = factor[i];
            sum += ai * wi;
          }
          var last = parity[sum % 11];
          if (parity[sum % 11] != code[17]) {
            // tip = "校验位错误"
            tip = "身份证输入错误";
            pass = false;
          }
        }
      }
      if (!pass) {
        callback(new Error(tip));
      } else {
        callback();
      }
    };
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
      // 教师基本信息表格数据
      teacherList: [],
      //查询教师不分页
      teacherListAll: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学历字典
      xlOptions: [],
      // 学位字典
      xwOptions: [],
      // 资格证书字典
      zgzsOptions: [],
      //教师名称
      teacherMingCheng: "",
      //禁止添加今天以后的日期
      pickerOptions0: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e7;
        },
      },
      //修改和查看详情的标志  当查看详情时不允许编辑页面
      flag: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userid: undefined,
        zjhm: undefined,
        csrq: undefined,
        byyx: undefined,
        zy: undefined,
        xl: undefined,
        xw: undefined,
        cjgzrq: undefined,
        zgzs: undefined,
        createuserid: undefined,
        createtime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        zjhm: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          {
            pattern: /(^\d{8}(0\d|10|11|12)([0-2]\d|30|31)\d{3}$)|(^\d{6}(18|19|20)\d{2}(0\d|10|11|12)([0-2]\d|30|31)\d{3}(\d|X|x)$)/,
            message: "请输入正确的身份证号",
          },
          { validator: idCardValidity, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getList2();
    this.getDicts("sys_jsxl").then((response) => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_jsxw").then((response) => {
      this.xwOptions = response.data;
    });
    this.getDicts("sys_jszgzs").then((response) => {
      this.zgzsOptions = response.data;
    });
  },
  methods: {
    //根据证件号码生成出生日期
    changeZjhm() {
      //console.log(this.form.zjhm);
      let idCard = this.form.zjhm;
      let birthday = "";
      if (idCard != null && idCard !== "") {
        if (idCard.length === 15) {
          birthday = "19" + idCard.substr(6, 6);
        } else if (idCard.length === 18) {
          birthday = idCard.substr(6, 8);
        }
        birthday = birthday.replace(/(.{4})(.{2})/, "$1-$2-");
      }
      this.form.csrq = birthday;
    },
    /** 查询教师基本信息列表 */
    getList() {
      this.loading = true;
      listTeacher(this.queryParams).then((response) => {
        this.teacherList = response.rows;
        // console.log(response);
        this.total = response.total;
        this.loading = false;
      });
    },
    getList2() {
      listTeacher2(this.queryParams).then((response) => {
        this.teacherListAll = response.rows;
      });
    },

    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 学位字典翻译
    xwFormat(row, column) {
      return this.selectDictLabel(this.xwOptions, row.xw);
    },
    // 资格证书字典翻译
    zgzsFormat(row, column) {
      return this.selectDictLabel(this.zgzsOptions, row.zgzs);
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
        userid: undefined,
        zjhm: undefined,
        csrq: undefined,
        byyx: undefined,
        zy: undefined,
        xl: undefined,
        xw: undefined,
        cjgzrq: undefined,
        zgzs: undefined,
        createuserid: undefined,
        createtime: undefined,
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
      this.title = "添加教师基本信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeacher(id).then((response) => {
        this.form = response.data;
        this.teacherMingCheng = this.form.user.nickName;
        this.open = true;
        this.flag = false;
        this.title = "修改教师基本信息";
      });
    },
    /** 详情按钮 */
    handleDetail(row) {
      this.reset();
      const id = row.id;
      getTeacher(id).then((response) => {
        this.form = response.data;
        // console.log("csrq:" + response.data.csrq);
        if (response.data.zjhm == "" || response.data.zjhm == null) {
          this.form.zjhm = " ";
        }
        if (response.data.byyx == "" || response.data.byyx == null) {
          this.form.byyx = " ";
        }
        if (response.data.zy == "" || response.data.zy == null) {
          this.form.zy = " ";
        }
        if (response.data.xl == "" || response.data.xl == null) {
          this.form.xl = " ";
        }
        if (response.data.xw == "" || response.data.xw == null) {
          this.form.xw = " ";
        }

        this.teacherMingCheng = this.form.user.nickName;
        this.open = true;
        //详情页不允许编辑页面
        this.flag = true;
        this.title = "教师基本信息详情";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTeacher(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                // this.msgError(response.msg);
              }
            });
          } else {
            addTeacher(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                //this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm("是否确认清空教师基本信息数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delTeacher(ids);
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
      this.$confirm("是否确认导出所有教师基本信息数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportTeacher(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
.edit-btns {
  .el-button {
    display: block;
    margin: 0 auto;
  }
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>
