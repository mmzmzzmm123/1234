<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="学期计划" prop="tpid">
            <el-select v-model="queryParams.tpid" size="small">
              <el-option
                v-for="item in themePlanOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="主题内容" prop="themeconent">
            <el-select v-model="queryParams.themeconent" size="small">
              <el-option
                v-for="item in themeOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
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
        v-hasPermi="['benyi:themetermplan:edit']"
        v-show="isShow"
        >填充</el-button
      >
      <!-- <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:themetermplan:remove']"
        v-show="isShow"
        >删除</el-button
      > -->
    </div>

    <el-table
      border
      v-loading="loading"
      :data="termplanitemList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="主题内容" align="center" prop="themeconent"
        ><template slot-scope="scope" v-if="scope.row.themeconent != undefined">
          <p
            v-for="(item, index) in scope.row.themeconent.split(';')"
            :key="index"
          >
            {{ themeFormat(item) }}
          </p>
        </template></el-table-column
      >

      <el-table-column
        label="学期计划"
        align="center"
        prop="tpid"
        :formatter="themePlanFormat"
      />
      <el-table-column label="月份" align="center" prop="month" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="操作"
        fixed="right"
        align="center"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:themetermplan:edit']"
            v-show="isShow"
            >填充</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:themetermplan:remove']"
            v-show="isShow"
            >删除</el-button
          > -->
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

    <!-- 添加或修改主题整合学期计划明细对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属计划" prop="tpid">
          <el-select v-model="form.tpid" size="small" :disabled="true">
            <el-option
              v-for="item in themePlanOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="月份" prop="month">
          <el-date-picker
            class="my-date-picker"
            v-model="form.month"
            type="month"
            placeholder="请选择月"
            value-format="yyyy-MM"
            :disabled="true"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="选择主题" prop="themeconent">
          <el-checkbox-group
            v-model="themeList"
            :max="max"
            @change="getThemeconentValue"
          >
            <el-checkbox
              v-for="(item, i) in themeOptions"
              :label="item.id"
              :key="i"
              >{{ item.name }}</el-checkbox
            >
          </el-checkbox-group>
          <el-input v-model="form.themeconent" v-if="false" />
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
  listTermplanitem,
  getTermplanitem,
  delTermplanitem,
  addTermplanitem,
  updateTermplanitem,
  exportTermplanitem,
} from "@/api/benyi/themetermplanitem";

import { listClass } from "@/api/system/class";
import { listTermplan, getTermplan } from "@/api/benyi/themetermplan";
import { listTheme } from "@/api/benyi/theme";

export default {
  name: "Termplanitem",
  data() {
    return {
      isShow: true,
      //最大选中个数
      max: 2,
      //主题
      themeOptions: [],
      themeList: [],
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
      // 主题整合学期计划明细表格数据
      termplanitemList: [],
      //计划列表
      themePlanOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      defaultThemeType: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tpid: undefined,
        themeconent: undefined,
        createuserid: undefined,
        updateuserid: undefined,
      },
      queryParams_class: {
        bjbh: undefined,
      },
      queryParams_classtype: {
        classid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        month: [{ required: true, message: "月份不能为空", trigger: "blur" }],
        themeconent: [
          { required: true, message: "主题不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const themeplanid = this.$route.params && this.$route.params.id;
    //console.log(themeplanid);
    this.getThemePlan(themeplanid);
    this.getThemePlanList();
    this.getClassType();
  },
  methods: {
    // 主题--字典状态字典翻译
    // themeFormat(row, column) {
    //   if (row.themeconent != null) {
    //     var ilength = row.themeconent.split(";").length - 1;
    //     var names = "";
    //     for (var i = 1; i < ilength; i++) {
    //       names =
    //         names +
    //         this.selectMoeDictLabel(
    //           this.themeOptions,
    //           row.themeconent.split(";")[i]
    //         ) +
    //         " ";
    //     }
    //     //this.selectDictLabel(this.scopeOptions, row.xnxq);
    //     return names;
    //   }
    //   return "";
    // },
    themeFormat(themeid) {
      var name = "";
      if (themeid != null && themeid != "") {
        name = this.selectMoeDictLabel(this.themeOptions, themeid);
      }
      return name;
    },
    //获取选中的checkbox
    getThemeconentValue() {
      //console.log(this.themeList);
      var text = ";";
      this.themeList.forEach(function (value, key, arr) {
        //console.log(value); // 结果依次为1，2，3
        text = text + value + ";";
        //console.log(text);
      });
      this.form.themeconent = text;
    },

    // 字典翻译
    themePlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.themePlanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.tpid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //计划详情
    getThemePlan(themeplanid) {
      getTermplan(themeplanid).then((response) => {
        this.queryParams_class.bjbh = response.data.classid;
        this.queryParams.tpid = response.data.id;
        this.defaultThemeType = response.data.id;
        if (response.data.status == "0") {
          this.isShow = true;
        } else {
          this.isShow = false;
        }

        this.getList();
      });
    },
    // 获取班级类型
    getClassType() {
      listClass(this.queryParams_class).then((response) => {
        this.queryParams_classtype.classid = response.rows[0].bjtype;
        this.getThemeList();
      });
    },
    //主题
    getThemeList() {
      listTheme(this.queryParams_classtype).then((response) => {
        this.themeOptions = response.rows;
      });
    },
    getThemePlanList() {
      listTermplan().then((response) => {
        this.themePlanOptions = response.rows;
      });
    },
    /** 查询主题整合学期计划明细列表 */
    getList() {
      this.loading = true;
      listTermplanitem(this.queryParams).then((response) => {
        this.termplanitemList = response.rows;
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
        tpid: undefined,
        themeconent: undefined,
        remark: undefined,
        createuserid: undefined,
        month: undefined,
        updateuserid: undefined,
        updateTime: undefined,
      };
      this.resetForm("form");
      this.themeList = [];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.tpid = this.defaultThemeType;
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
      this.title = "添加主题整合学期计划明细";
      this.form.tpid = this.queryParams.tpid;
    },
    /**填充按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTermplanitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "填充主题整合学期计划明细";
        var themeconent = response.data.themeconent.split(";");
        var array = [];
        //console.log(arr);
        themeconent.forEach(function (value, key, arr) {
          //console.log(value); // 结果依次为1，2，3
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        this.themeList = array;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTermplanitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("填充成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTermplanitem(this.form).then((response) => {
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
      this.$confirm("是否确认删除主题整合学期计划明细的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delTermplanitem(ids);
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