<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="小组名称" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入小组名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="咨询师" prop="consultId">
        <el-select v-model="queryParams.consultId" clearable>
          <el-option
            v-for="item in consultList"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="startTime">
        <el-date-picker
          v-model="timeVal"
          style="width: 280px;"
          size="small"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="onchangeTime"
        />
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['psychology:team:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="teamList" @selection-change="handleSelectionChange">
      <el-table-column label="小组封面" align="center" prop="img">
        <template slot-scope="scope">
          <image-preview :src="scope.row.img" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="小组名称" prop="title" />
      <el-table-column label="小组简介" prop="info" />
      <el-table-column label="咨询师" align="center" prop="consultName" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="状态" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="handleStatus(scope.row)" active-value="0" inactive-value="1" active-text="显示" inactive-text="隐藏"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['psychology:team:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            style="color: red"
            @click="handleDelete(scope.row)"
            v-hasPermi="['psychology:team:remove']"
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

    <!-- 添加或修改咨询小组对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="咨询师" prop="consultId">
          <el-select v-model="form.consultId" placeholder="请选择咨询师" clearable>
            <el-option
              v-for="item in consultList"
              :key="item.id"
              :label="item.nickName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="小组封面" prop="img">
          <my-cropper v-model="form.img" sizeTip="宽298px 高156px" :extraData="extraData" :width="298" :height="156"/>
        </el-form-item>
        <el-form-item label="小组名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入小组名称" maxlength="100" show-word-limit/>
        </el-form-item>
        <el-form-item label="小组简介" prop="info">
          <el-input type="textarea" :rows="3" size="mini" maxlength="255" show-word-limit v-model="form.info" placeholder="请输入小组简介" />
        </el-form-item>
        <el-form-item label="跳转url" prop="url">
          <el-input type="textarea" :rows="3" size="mini" maxlength="255" show-word-limit v-model="form.url" placeholder="请输入跳转url" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" step-strictly :max="9999"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" active-value="0" inactive-value="1" active-text="显示" inactive-text="隐藏"/>
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
import { getConsultAll } from "@/api/psychology/consult";
import { getList, getOne, del, add, update } from "@/api/psychology/team";

export default {
  name: "Column",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      timeVal: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 咨询小组表格数据
      teamList: [],
      consultList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      extraData: {
        module: this.$constants['picModules'][2],
        type: this.$constants['picTypes'][7]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startTime: null,
        endTime: null,
        consultId: null,
        title: null,
        cat: 1,
        img: null,
        status: null,
        orderByColumn: 'sort',
        isAsc: 'descending',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        consultId: [{ required: true, message: "请选择咨询师", trigger: "change" }],
        title: [{ required: true, message: "小组名称不能为空", trigger: "blur" }],
        info: [{ required: true, message: "小组简介不能为空", trigger: "blur" }],
        url: [{ required: true, message: "跳转链接不能为空", trigger: "blur" }],
        img: [{ required: true, message: "请上传小组封面", trigger: "change" }],
      }
    };
  },
  created() {
    this.getList();
    this.getConsults();
  },
  methods: {
    onchangeTime (e) {
      console.log(this.queryParams)
      this.timeVal = e;
      this.queryParams.startTime = this.timeVal[0] + ' 00:00:00'
      this.queryParams.endTime = this.timeVal[1] + ' 23:59:59'
      this.queryParams.pageNum = 1
      // this.getList();
    },
    async getConsults() {
      const res = await getConsultAll({ status: '0' })
      this.consultList = res.data
    },
    /** 查询咨询小组列表 */
    getList() {
      this.loading = true;
      getList(this.queryParams).then(response => {
        this.teamList = response.rows;
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
        id: null,
        consultId: null,
        consultName: null,
        title: null,
        info: null,
        cat: 1,
        sort: 99,
        img: null,
        url: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.queryParams.endTime = null
      this.timeVal = []
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加咨询小组";
    },
    handleStatus(row) {
      const that = this
      const content = row.status === '0' ? '确认要显示该咨询小组吗？' : '确认要隐藏该咨询小组吗？'
      const type = row.status === '0' ? 'success' : 'warning'

      this.$confirm(content, '提示', {
        type: type
      }).then(() => {
        update(row).then(response => {
          that.$modal.msgSuccess("修改成功");
          that.getList();
        });
      }).catch(() => {
        row.status = row.status === '0' ? '1' : '0'
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOne(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改咨询小组";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.consultName = this.consultList.find(item => item.id === this.form.consultId).nickName
          if (this.form.id != null) {
            update(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除咨询小组为"' + row.title + '"的数据项？').then(function() {
        return del(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
<style scoped>
/deep/ .el-switch .el-switch__core, .el-switch .el-switch__label {
  width: 60px !important;
  font-size: 12px !important;
}
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  width: 298px;
  height: 156px;
}
</style>
