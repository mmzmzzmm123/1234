<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="月计划" prop="mpid">
            <el-select v-model="queryParams.mpid" size="small">
              <el-option
                v-for="item in themeMonthPlanOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="周次" prop="zc">
            <el-input-number
              v-model="queryParams.zc"
              placeholder="周次"
              clearable
              size="small"
              :min="1"
              class="my-date-picker"
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
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:thememonthplan:add']"
        v-show="isShow"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:thememonthplan:edit']"
        v-show="isShow"
        >填充</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:thememonthplan:remove']"
        v-show="isShow"
        >删除</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="weekplanList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="活动" align="center" prop="activityid">
        <template slot-scope="scope" v-if="scope.row.activityid != undefined">
          <p
            v-for="(item, index) in scope.row.activityid.split(';')"
            :key="index"
          >
            {{ themeactivityFormat(item) }}
          </p>
        </template></el-table-column
      >
      <el-table-column
        label="所属月计划"
        align="center"
        prop="mpid"
        :formatter="themeMonthPlanFormat"
      />
      <el-table-column label="周次" align="center" prop="zc" />
      <el-table-column label="开始时间" align="center" prop="starttime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endtime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>

      <el-table-column label="家长支持" align="center" prop="jzzc" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:thememonthplan:edit']"
            v-show="isShow"
            >填充</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:thememonthplan:remove']"
            v-show="isShow"
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

    <!-- 添加或修改主题整合周计划对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="月计划" prop="mpid">
          <el-select v-model="form.mpid" :disabled="true">
            <el-option
              v-for="item in themeMonthPlanOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="周次" prop="zc">
          <el-input-number
            class="my-date-picker"
            v-model="form.zc"
            :min="1"
            placeholder="请输入周次"
          />
        </el-form-item>
        <el-form-item label="起止时间" prop="starttime">
          <!-- <el-date-picker
            clearable
            class="my-date-picker"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间"
          ></el-date-picker> -->
          <el-date-picker
            v-model="form.starttime"
            clearable
            class="my-date-picker"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="结束时间" prop="endtime">
          <el-date-picker
            clearable
            class="my-date-picker"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间"
          ></el-date-picker>
        </el-form-item> -->
        <el-form-item label="选择活动" prop="activityid">
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
        <el-form-item label="家长支持" prop="jzzc">
          <el-input
            v-model="form.jzzc"
            type="textarea"
            placeholder="请输入内容"
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
  listMonthplanitem,
  getMonthplanitem,
  delMonthplanitem,
  addMonthplanitem,
  updateMonthplanitem,
} from "@/api/benyi/thememonthplanitem";
import { listMonthplan, getMonthplan } from "@/api/benyi/thememonthplan";
import { listActivityByThemeId } from "@/api/benyi/activity";

export default {
  name: "Monthplanitem",
  data() {
    return {
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
      // 主题整合周计划表格数据
      weekplanList: [],
      //主题整合月计划列表
      themeMonthPlanOptions: [],
      //id默认值
      defaultThemeMonthType: "",
      //选中的chebox值
      themeactivityList: [],
      //获取的活动列表
      themeactivityOptions: [],
      max: 5,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mpid: undefined,
        zc: undefined,
        starttime: undefined,
        endtime: undefined,
        activityid: undefined,
        jzzc: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        zc: [{ required: true, message: "周次不能为空", trigger: "blur" }],
        starttime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" },
        ],
        endtime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" },
        ],
        activityid: [
          { required: true, message: "活动不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const thememonthplanid = this.$route.params && this.$route.params.id;
    this.getThemeMonthPlan(thememonthplanid);
    this.getThemeMonthPlanList();
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
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
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
      listActivityByThemeId(themeid).then((response) => {
        this.themeactivityOptions = response.rows;
      });
    },
    // 字典翻译
    themeMonthPlanFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.themeMonthPlanOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.mpid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //计划详情
    getThemeMonthPlan(thememonthplanid) {
      getMonthplan(thememonthplanid).then((response) => {
        this.queryParams.mpid = response.data.id;
        this.defaultThemeMonthType = response.data.id;
        // console.log(response.data.themes);
        var themeids = response.data.themes.split(";");
        var array = [];
        //console.log(arr);
        themeids.forEach(function (value, key, arr) {
          //console.log(value); // 结果依次为1，2，3
          if (value != "") {
            array.push(parseInt(value));
          }
        });
        this.getThemeActivityList(array);

        if (response.data.status == "0") {
          this.isShow = true;
        } else {
          this.isShow = false;
        }

        this.getList();
      });
    },
    getThemeMonthPlanList() {
      listMonthplan().then((response) => {
        this.themeMonthPlanOptions = response.rows;
      });
    },
    /** 查询主题整合周计划列表 */
    getList() {
      this.loading = true;
      listMonthplanitem(this.queryParams).then((response) => {
        this.weekplanList = response.rows;
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
        mpid: undefined,
        zc: 1,
        starttime: undefined,
        endtime: undefined,
        activityid: undefined,
        jzzc: undefined,
        createuserid: undefined,
        createTime: undefined,
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
      this.queryParams.mpid = this.defaultThemeMonthType;
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
      this.title = "添加主题整合周计划";
      this.form.mpid = this.queryParams.mpid;
    },
    /** 填充按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMonthplanitem(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "填充主题整合周计划明细";
        let arrTime = [];
        if (response.data.starttime == null) {
        } else {
          arrTime.push(response.data.starttime);
          arrTime.push(response.data.endtime);
        }
        this.form.starttime = arrTime;
        if (activityid == null) {
        } else {
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
        }
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          let arrTime = this.form.starttime;
          this.form.starttime = arrTime[0];
          this.form.endtime = arrTime[1];
          if (this.form.id != undefined) {
            updateMonthplanitem(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("填充成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addMonthplanitem(this.form).then((response) => {
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
      this.$confirm("是否确认删除主题整合周计划明细的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delMonthplanitem(ids);
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