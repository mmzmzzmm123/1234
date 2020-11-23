<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="家长姓名" prop="jzxm">
            <el-input
              v-model="queryParams.jzxm"
              placeholder="请输入家长姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="幼儿姓名" prop="yexm">
            <el-input
              v-model="queryParams.yexm"
              placeholder="请输入幼儿姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="联系方式" prop="lxfs">
            <el-input
              v-model="queryParams.lxfs"
              placeholder="请输入联系方式"
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
      <el-tooltip
        effect="dark"
        content="点我，可以复制内容发送给家长填报《入园体验申请》呦"
        placement="top-start"
      >
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="copy($event, inviteCode)"
          v-hasPermi="['benyi:experience:add']"
          >一键复制</el-button
        >
      </el-tooltip>
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:experience:edit']"
        >回复</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:experience:remove']"
        >删除</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="experienceList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column fixed label="幼儿姓名" align="center" prop="yexm" />
      <el-table-column label="家长姓名" align="center" prop="jzxm" />
      <el-table-column
        label="幼儿出生日期"
        align="center"
        prop="csrq"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" align="center" prop="lxfs" />
      <el-table-column
        label="申请体验时间"
        align="center"
        prop="sqtysj"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sqtysj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="上午或下午"
        align="center"
        prop="swxw"
        :formatter="swxwFormat"
      />
      <el-table-column
        label="是否回复"
        align="center"
        prop="sfhf"
        :formatter="ynFormat"
      />
      <el-table-column
        label="体验内容"
        align="center"
        prop="tynrid"
        :formatter="tynrFormat"
      />
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
            v-hasPermi="['benyi:experience:edit']"
            >回复</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:experience:remove']"
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

    <!-- 添加或修改入班体验申请对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="big-dialog"
      append-to-body
    >
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-col :span="12">
            <el-form-item label="家长姓名" prop="jzxm">
              <el-input
                v-model="form.jzxm"
                placeholder="请输入家长姓名"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="lxfs">
              <el-input
                v-model="form.lxfs"
                placeholder="请输入联系方式"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿姓名" prop="yexm">
              <el-input
                v-model="form.yexm"
                placeholder="请输入幼儿姓名"
                :disabled="true"
              />
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
                placeholder="选择幼儿出生日期"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="拟入园时间" prop="nrysj">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.nrysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择拟入园时间"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="申请体验时间" prop="sqtysj">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.sqtysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择申请体验时间"
                :disabled="true"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上午或下午" prop="swxw">
              <el-select
                v-model="form.swxw"
                placeholder="请选择"
                :disabled="true"
              >
                <el-option
                  v-for="dict in swxwOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
              <el-input v-model="form.schoolid" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否回复" prop="sfhf">
              <el-radio-group v-model="form.sfhf">
                <el-radio
                  v-for="dict in ynOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}</el-radio
                >
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="回复内容" prop="hfrn">
              <el-input
                v-model="form.hfrn"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="园长指示" prop="yzzs">
              <el-input
                v-model="form.yzzs"
                type="textarea"
                placeholder="请输入内容"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体验内容" prop="tynrid">
              <el-select v-model="form.tynrid" placeholder="请选择">
                <el-option
                  v-for="dict in tynrOptions"
                  :key="dict.id"
                  :label="dict.title"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体验结果" prop="tyjg">
              <el-select v-model="form.tyjg" placeholder="请选择">
                <el-option
                  v-for="dict in tyjgOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入园时间" prop="rysj">
              <el-date-picker
                clearable
                size="small"
                style="width: 200px"
                v-model="form.rysj"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择入园时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listExperience,
  getExperience,
  delExperience,
  addExperience,
  updateExperience,
  exportExperience,
} from "@/api/benyi/experience";

import { listHalfdayplan } from "@/api/benyi/halfdayplan";

import { getUserProfile } from "@/api/system/user";

import Clipboard from "clipboard";

export default {
  name: "Experience",
  data() {
    return {
      inviteCode: "",
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
      // 入班体验申请表格数据
      experienceList: [],
      //字典翻译
      swxwOptions: [],
      ynOptions: [],
      tyjgOptions: [],
      tynrOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jzxm: undefined,
        yexm: undefined,
        csrq: undefined,
        lxfs: undefined,
        nrysj: undefined,
        sqtysj: undefined,
        swxw: undefined,
        schoolid: undefined,
        sfhf: undefined,
        hfrn: undefined,
        hfuserid: undefined,
        fhsj: undefined,
        yzzs: undefined,
        tyjg: undefined,
        rysj: undefined,
        tynrid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jzxm: [
          { required: true, message: "家长姓名不能为空", trigger: "blur" },
        ],
        yexm: [
          { required: true, message: "幼儿姓名不能为空", trigger: "blur" },
        ],
        csrq: [
          { required: true, message: "幼儿出生日期不能为空", trigger: "blur" },
        ],
        lxfs: [
          {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        nrysj: [
          { required: true, message: "拟入园时间不能为空", trigger: "blur" },
        ],
        sqtysj: [
          { required: true, message: "申请体验时间不能为空", trigger: "blur" },
        ],
        swxw: [
          { required: true, message: "下午或下午不能为空", trigger: "blur" },
        ],
        hfrn: [
          { required: true, message: "回复内容不能为空", trigger: "blur" },
        ],
        yzzs: [
          { required: true, message: "园长指示不能为空", trigger: "blur" },
        ],
        tynrid: [
          { required: true, message: "体验内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getUser();
    this.getTynr();
    this.getDicts("sys_dm_swxw").then((response) => {
      this.swxwOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.ynOptions = response.data;
    });
    this.getDicts("sys_dm_tyjg").then((response) => {
      this.tyjgOptions = response.data;
    });
  },
  methods: {
    getTynr() {
      listHalfdayplan(null).then((response) => {
        //console.log(response.rows);
        this.tynrOptions = response.rows;
      });
    },
    getUser() {
      getUserProfile().then((response) => {
        var domain = window.location.host;
        //console.log(domain);
        //this.user = response.data;
        this.inviteCode =
          response.data.dept.deptName +
          "入班半日体验申请表 " +
          "http://" +
          domain +
          "/experience/apply/" +
          response.data.dept.deptId;
      });
    },
    // 字典翻译
    tynrFormat(row, column) {
      var actions = [];
      var datas = this.tynrOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == row.tynrid) {
          actions.push(datas[key].title);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译
    ynFormat(row, column) {
      return this.selectDictLabel(this.ynOptions, row.sfhf);
    },
    // 字典翻译
    swxwFormat(row, column) {
      return this.selectDictLabel(this.swxwOptions, row.swxw);
    },
    /** 查询入班体验申请列表 */
    getList() {
      this.loading = true;
      listExperience(this.queryParams).then((response) => {
        this.experienceList = response.rows;
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
        jzxm: undefined,
        yexm: undefined,
        csrq: undefined,
        lxfs: undefined,
        nrysj: undefined,
        sqtysj: undefined,
        swxw: undefined,
        schoolid: undefined,
        sfhf: undefined,
        hfrn: undefined,
        hfuserid: undefined,
        fhsj: undefined,
        yzzs: undefined,
        tyjg: undefined,
        rysj: undefined,
        tynrid: undefined,
        createTime: undefined,
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
      this.title = "添加入班体验申请";
    },
    copy(e, text) {
      const clipboard = new Clipboard(e.target, { text: () => text });
      clipboard.on("success", (e) => {
        this.msgSuccess("复制成功");
        // 释放内存
        clipboard.off("error");
        clipboard.off("success");
        clipboard.destroy();
      });
      clipboard.on("error", (e) => {
        // 不支持复制
        this.msgError("手机权限不支持复制功能");
        // 释放内存
        clipboard.off("error");
        clipboard.off("success");
        clipboard.destroy();
      });
      clipboard.onClick(e);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getExperience(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入班体验申请";
        this.form.sfhf = "Y";
        (this.form.hfrn =
          "家长你好，非常欢迎你带孩子来园入班进行半日体验。我已经按照你所申请入班体验的时间，在本园XX班做了安排。请你在" +
          response.data.sqtysj +
          "(" +
          this.swxwFormat(response.data) +
          ")" +
          "开始进班体验。小一班班长章一梦老师将联系你安排具体事宜，并给你一份半日体验计划表。祝你体验愉快！"),
          (this.form.yzzs = "请XX班按此预约时间做准备。");
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateExperience(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addExperience(this.form).then((response) => {
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
        '是否确认删除入班体验申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delExperience(ids);
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