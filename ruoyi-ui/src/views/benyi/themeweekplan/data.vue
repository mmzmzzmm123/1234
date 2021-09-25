<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="周计划" prop="wpid">
            <el-select v-model="queryParams.wpid" size="small">
              <el-option
                v-for="item in themeWeekPlanOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="选择日期" prop="daytime">
            <el-date-picker
              clearable
              size="small"
              class="my-date-picker"
              v-model="queryParams.daytime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="星期" prop="zhou">
            <el-input-number
              v-model="queryParams.zhou"
              placeholder="星期"
              clearable
              size="small"
              :min="1"
              :max="7"
              class="my-date-picker"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="活动" prop="activityid">
            <el-input
              v-model="queryParams.activityid"
              placeholder="请输入活动"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col> -->
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="分组性质" prop="fzxz">
            <el-select v-model="queryParams.fzxz" size="small">
              <el-option
                v-for="item in fzxzOptions"
                :key="item.dictValue"
                :label="item.dictLabel"
                :value="item.dictValue"
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
      <!-- 
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:themeweekplan:add']"
          v-show="isShow"
        >新增</el-button>
        -->
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:themeweekplan:edit']"
        v-show="isShow"
        >填充</el-button
      >
      <!-- <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:themeweekplan:remove']"
        v-show="isShow"
        >删除</el-button
      > -->
    </div>

    <el-table
      border
      v-loading="loading"
      :data="weekplanitemList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="活动" align="center" prop="activityid"
        ><template slot-scope="scope" v-if="scope.row.activityid != undefined">
          <p
            v-for="(item, index) in scope.row.activityid.split(';')"
            :key="index"
          >
            {{ themeactivityFormat(item) }}
          </p>
        </template></el-table-column
      >
      <el-table-column
        label="所属周计划"
        align="center"
        prop="wpid"
        :formatter="themeweekPlanFormat"
      />
      <el-table-column label="日期" align="center" prop="daytime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.daytime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="星期" align="center" prop="zhou" />
      <el-table-column
        label="分组性质"
        align="center"
        prop="fzxz"
        :formatter="fzxzFormat"
      />
      <el-table-column label="家长支持" align="center" prop="jzzc" />
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
            v-hasPermi="['benyi:themeweekplan:edit']"
            v-show="isShow"
            >填充</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:themeweekplan:remove']"
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

    <!-- 添加或修改主题整合周计划明细对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="周计划" prop="wpid">
          <el-select v-model="form.wpid" size="small" :disabled="true">
            <el-option
              v-for="item in themeWeekPlanOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="日期" prop="daytime">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.daytime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            :disabled="disabled"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="星期" prop="zhou">
          <el-input-number
            class="my-date-picker"
            v-model="form.zhou"
            placeholder="请输入星期"
            :disabled="disabled"
          />
        </el-form-item>
        <el-form-item label="活动" prop="activityid">
          <el-checkbox-group
            v-model="themeactivityList"
            :max="max"
            @change="getThemeActivityIdValue"
          >
            <el-checkbox
              v-for="(item, i) in themeactivityOptions"
              :label="item.id"
              :key="i"
              >{{ item.name }}</el-checkbox
            >
          </el-checkbox-group>
          <el-input v-model="form.activityid" v-if="false" />
        </el-form-item>
        <el-form-item label="分组性质" prop="fzxz">
          <el-select v-model="form.fzxz" size="small">
            <el-option
              v-for="item in fzxzOptions"
              :key="item.dictValue"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="家长支持" prop="jzzc">
          <el-input
            v-model="form.jzzc"
            type="textarea"
            placeholder="请输入家长支持"
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
  listWeekplanitem,
  getWeekplanitem,
  delWeekplanitem,
  addWeekplanitem,
  updateWeekplanitem,
  exportWeekplanitem,
} from "@/api/benyi/themeweekplanitem";

import { listWeekplan, getWeekplan } from "@/api/benyi/themeweekplan";
import { listActivityById } from "@/api/benyi/activity";
import { listMonthplan } from "@/api/benyi/thememonthplan";
import { listMonthplanitem } from "@/api/benyi/thememonthplanitem";

export default {
  name: "Weekplanitem",
  data() {
    return {
      isShow: true,
      max: 2,
      disabled: false,
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
      // 主题整合周计划明细表格数据
      weekplanitemList: [],
      //主题整合周计划列表
      themeWeekPlanOptions: [],
      //id默认值
      defaultThemeWeekType: "",
      //分组性质
      fzxzOptions: [],
      //选中的chebox值
      themeactivityList: [],
      //主题活动
      themeactivityOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        daytime: undefined,
        zhou: undefined,
        activityid: undefined,
        fzxz: undefined,
        jzzc: undefined,
        createuserid: undefined,
        updateuserid: undefined,
        wpid: undefined,
      },
      // 查询参数
      queryParams_MonPlan: {
        xnxq: undefined,
        month: undefined,
        status: "2",
      },
      queryParams_MonPlanItem: {
        mpid: undefined,
        zc: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        daytime: [{ required: true, message: "日期不能为空", trigger: "blur" }],
        zhou: [{ required: true, message: "星期不能为空", trigger: "blur" }],
        fzxz: [
          { required: true, message: "分组性质不能为空", trigger: "blur" },
        ],
        activityid: [
          { required: true, message: "活动不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const themeweekplanid = this.$route.params && this.$route.params.id;
    this.getThemeWeekPlan(themeweekplanid);
    this.getThemeWeekPlanList();
    this.getDicts("sys_dm_fzxz").then((response) => {
      this.fzxzOptions = response.data;
    });
  },
  methods: {
    // 主题--字典状态字典翻译
    // themeactivityFormat(row, column) {
    //   if (row.activityid != null) {
    //     var ilength = row.activityid.split(";").length - 1;
    //     var names = "";
    //     for (var i = 1; i < ilength; i++) {
    //       names =
    //         names +
    //         this.selectMoeDictLabel(
    //           this.themeactivityOptions,
    //           row.activityid.split(";")[i]
    //         ) +
    //         "；";
    //     }
    //     //this.selectDictLabel(this.scopeOptions, row.xnxq);
    //     return names;
    //   }
    //   return "";
    // },
    themeactivityFormat(activityid) {
      var name = "";
      if (activityid != null && activityid != "") {
        name = this.selectMoeDictLabel(this.themeactivityOptions, activityid);
      }
      return name;
    },
    //获取选中的checkbox
    getThemeActivityIdValue() {
      //console.log(this.themeList);
      var text = ";";
      this.themeactivityList.forEach(function (value, key, arr) {
        //console.log(value); // 结果依次为1，2，3
        text = text + value + ";";
        //console.log(text);
      });
      this.form.activityid = text;
    },
    //主题活动
    getThemeActivityList(themeid) {
      listActivityById(themeid).then((response) => {
        //console.log(response.rows);
        this.themeactivityOptions = response.rows;
      });
    },
    // 分组性质类型--字典状态字典翻译
    fzxzFormat(row, column) {
      return this.selectDictLabel(this.fzxzOptions, row.fzxz);
    },
    // 字典翻译
    themeweekPlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.themeWeekPlanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.wpid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //计划详情
    getThemeWeekPlan(themeweekplanid) {
      getWeekplan(themeweekplanid).then((response) => {
        this.queryParams.wpid = response.data.id;
        this.defaultThemeweekType = response.data.id;

        // console.log(
        //   "月份：" + response.data.month + "   " + "周次:" + response.data.zc
        // );

        //查找活动id
        this.queryParams_MonPlan.month = response.data.month;
        this.queryParams_MonPlan.xnxq = response.data.xnxq;
        listMonthplan(this.queryParams_MonPlan).then((resMonPlan) => {
          //console.log(resMonPlan.rows);
          this.queryParams_MonPlanItem.mpid = resMonPlan.rows[0].id;
          this.queryParams_MonPlanItem.zc = response.data.zc;
          listMonthplanitem(this.queryParams_MonPlanItem).then(
            (resMonPlanItem) => {
              //console.log(resMonPlanItem.rows);
              var activityids = resMonPlanItem.rows[0].activityid.split(";");
              var array = [];
              //console.log(arr);
              activityids.forEach(function (value, key, arr) {
                //console.log(value); // 结果依次为1，2，3
                if (value != "") {
                  array.push(parseInt(value));
                }
              });
              this.getThemeActivityList(array);
            }
          );
        });

        if (response.data.status == "0") {
          this.isShow = true;
        } else {
          this.isShow = false;
        }

        this.getList();
      });
    },
    getThemeWeekPlanList() {
      listWeekplan().then((response) => {
        this.themeWeekPlanOptions = response.rows;
      });
    },
    /** 查询主题整合周计划明细列表 */
    getList() {
      this.loading = true;
      listWeekplanitem(this.queryParams).then((response) => {
        this.weekplanitemList = response.rows;
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
        daytime: undefined,
        zhou: undefined,
        activityid: undefined,
        fzxz: undefined,
        jzzc: undefined,
        createuserid: undefined,
        createTime: undefined,
        updateuserid: undefined,
        updateTime: undefined,
        wpid: undefined,
      };
      this.resetForm("form");
      this.themeactivityList = [];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.wpid = this.defaultThemeweekType;
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
      this.title = "添加主题整合周计划明细";
      this.form.wpid = this.queryParams.wpid;
      this.disabled = false;
    },
    /** 填充按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWeekplanitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "填充主题整合周计划明细";
        this.disabled = true;
        var activityid = response.data.activityid.split(";");
        var array = [];
        //console.log(arr);
        activityid.forEach(function (value, key, arr) {
          //console.log(value); // 结果依次为1，2，3
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        this.themeactivityList = array;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateWeekplanitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("填充成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addWeekplanitem(this.form).then((response) => {
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
      this.$confirm("是否确认删除主题整合周计划明细数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delWeekplanitem(ids);
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