<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="学期计划" prop="tpid">
            <el-select v-model="queryParams.tpid" size="small">
              <el-option
                v-for="item in mathPlanOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="游戏数学" prop="mathconent">
            <el-input
              v-model="queryParams.mathconent"
              placeholder="请输入游戏数学内容"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
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
      <!-- 
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:mathtermplan:add']"
          v-show="isShow"
          >新增</el-button
        >
      -->
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:mathtermplan:edit']"
        v-show="isShow"
        >填充</el-button
      >
    </div>

    <el-table
    border
      v-loading="loading"
      :data="mathtermplanitemList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
      fixed
        label="游戏数学内容"
        align="center"
        prop="mathconent"
        :formatter="mathFaFormat"
      />
      <el-table-column
        label="所属计划"
        align="center"
        prop="tpid"
        :formatter="mathPlanFormat"
      />
      <el-table-column label="月份" align="center" prop="month" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:mathtermplan:edit']"
            v-show="isShow"
            >填充</el-button
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

    <!-- 添加或修改游戏数学学期计划明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="v-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属计划" prop="tpid">
          <el-select v-model="form.tpid" size="small" :disabled="true">
            <el-option
              v-for="item in mathPlanOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="月份" prop="month">
          <el-date-picker
            v-model="form.month"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择月份"
            class="my-date-picker"
            :disabled="true"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="选择方案" prop="mathconent">
          <el-checkbox-group
            v-model="mathFaList"
            :max="max"
            @change="getMathFaContentValue"
          >
            <el-checkbox
              v-for="(item, i) in mathFaOptions"
              :label="item.id"
              :key="i"
              :max="max"
              >{{ item.name }}</el-checkbox
            >
          </el-checkbox-group>
          <el-input v-model="form.mathconent" v-if="false" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注"
          />
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
  listMathtermplanitem,
  getMathtermplanitem,
  delMathtermplanitem,
  addMathtermplanitem,
  updateMathtermplanitem,
} from "@/api/benyi/mathtermplanitem";
// 获取游戏数学内容接口
import { listMath, getMath } from "@/api/benyi/math";
// 获取游戏数学方案
import {
  listPlan,
  getPlan,
  delPlan,
  addPlan,
  updatePlan,
} from "@/api/benyi/plan";

// 获取班级接口
import { listClass } from "@/api/system/class";
// 获取游戏数学计划接口
import { listMathtermplan, getMathtermplan } from "@/api/benyi/mathtermplan";

export default {
  name: "Mathtermplanitem",
  data() {
    return {
      //选中的chebox值
      mathFaList: [],
      // 最多选择方案个数
      max: 5,
      // 是否显示按钮
      isShow: true,
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
      // 游戏数学学期计划明细表格数据
      mathtermplanitemList: [],
      // 计划列表
      mathPlanOptions: [],
      // 游戏数学内容选项
      mathOptions: [],
      // 游戏数学方案选项
      mathFaOptions: [],
      // 默认游戏数学类型
      defaultMathType: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tpid: undefined,
        month: undefined,
        mathconent: undefined,
        createuserid: undefined,
        updateuserid: undefined,
      },
      queryParams_class: {
        bjbh: undefined,
      },
      queryParams_classtype: {
        classtypeId: undefined,
      },
      queryParams_mathfa: {
        mathid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        month: [{ required: true, message: "月份不能为空", trigger: "blur" }],
        mathconent: [
          { required: true, message: "内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const mathplanid = this.$route.params && this.$route.params.id;
    this.getMathPlan(mathplanid);
    this.getMathPlanList();
    this.getClassType();
  },
  methods: {
    /** 查询游戏数学学期计划明细列表 */
    getList() {
      this.loading = true;
      listMathtermplanitem(this.queryParams).then((response) => {
        this.mathtermplanitemList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //计划详情
    getMathPlan(mathplanid) {
      getMathtermplan(mathplanid).then((response) => {
        this.queryParams_class.bjbh = response.data.classid;
        this.queryParams.tpid = response.data.id;
        this.defaultMathType = response.data.id;
        if (response.data.status == "0") {
          this.isShow = true;
        } else {
          this.isShow = false;
        }

        this.getList();
      });
    },
    //获取选中的checkbox
    getMathFaContentValue() {
      var text = ";";
      this.mathFaList.forEach(function (value, key, arr) {
        text = text + value + ";";
      });
      this.form.mathconent = text;
    },
    // 获取班级类型
    getClassType() {
      listClass(this.queryParams_class).then((response) => {
        this.queryParams_classtype.classtypeId = response.rows[0].bjtype;
        this.getMathList();
      });
    },
    // 获取游戏数学内容列表
    getMathList() {
      listMath(this.queryParams_classtype).then((response) => {
        this.mathOptions = response.rows;
        this.queryParams_mathfa.mathid = response.rows[0].id;
        this.getMathFa();
      });
    },
    // 获取游戏数学方案列表
    getMathFa() {
      listPlan(this.queryParams_mathfa).then((response) => {
        this.mathFaOptions = response.rows;
      });
    },
    // 获取游戏数学学期计划列表
    getMathPlanList() {
      listMathtermplan().then((response) => {
        this.mathPlanOptions = response.rows;
      });
    },
    // 字典翻译
    mathPlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.mathPlanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.tpid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 游戏数学方案--字典状态字典翻译
    mathFaFormat(row, column) {
      if (row.mathconent != null) {
        var ilength = row.mathconent.split(";").length - 1;
        var names = "";
        for (var i = 1; i < ilength; i++) {
          names =
            names +
            this.selectMoeDictLabel(
              this.mathFaOptions,
              row.mathconent.split(";")[i]
            ) +
            "；";
        }
        //this.selectDictLabel(this.scopeOptions, row.xnxq);
        return names;
      }
      return "";
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
        tpid: undefined,
        month: undefined,
        mathconent: undefined,
        remark: undefined,
        createuserid: undefined,
        createTime: undefined,
        updateuserid: undefined,
        updateTime: undefined,
      };
      this.resetForm("form");
      this.mathFaList = [];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.tpid = this.defaultMathType;
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
      this.title = "添加游戏数学学期计划明细";
      this.form.tpid = this.queryParams.tpid;
    },
    /**填充按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMathtermplanitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "填充游戏数学学期计划明细";
        var mathconent = response.data.mathconent.split(";");
        var array = [];
        mathconent.forEach(function (value, key, arr) {
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        this.mathFaList = array;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateMathtermplanitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("填充成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMathtermplanitem(this.form).then((response) => {
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
    // /** 删除按钮操作 */
    // handleDelete(row) {
    //   const ids = row.id || this.ids;
    //   this.$confirm(
    //     '是否确认删除游戏数学学期计划明细编号为"' + ids + '"的数据项?',
    //     "警告",
    //     {
    //       confirmButtonText: "确定",
    //       cancelButtonText: "取消",
    //       type: "warning",
    //     }
    //   )
    //     .then(function () {
    //       return delMathtermplanitem(ids);
    //     })
    //     .then(() => {
    //       this.getList();
    //       this.msgSuccess("删除成功");
    //     })
    //     .catch(function () {});
    // },
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