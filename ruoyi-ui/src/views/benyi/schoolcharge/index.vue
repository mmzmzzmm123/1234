<template>
  <div class="app-container">
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:schoolcharge:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:schoolcharge:remove']"
          >删除</el-button
        >
      </el-col>
    </el-row> -->

    <el-table
      v-loading="loading"
      border
      :data="schoolchargeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="园所名称"
        fixed=""
        align="center"
        prop="dept.deptName"
      />
      <el-table-column label="保育费（大班）/月" align="center" prop="byf" />
      <el-table-column label="伙食费（大班）/天" align="center" prop="hsf" />
      <el-table-column label="保育费（中班）/月" align="center" prop="byfZ" />
      <el-table-column label="伙食费（中班）/天" align="center" prop="hsfZ" />
      <el-table-column label="保育费（小班）/月" align="center" prop="byfX" />
      <el-table-column label="伙食费（小班）/天" align="center" prop="hsfX" />
      <el-table-column label="保育费（托班）/月" align="center" prop="byfT" />
      <el-table-column label="伙食费（托班）/天" align="center" prop="hsfT" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width  edit-btns"
        width="60"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:schoolcharge:edit']"
            >设置</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:schoolcharge:remove']"
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

    <!-- 添加或修改园所收费标准对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="v-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="保育费大班(月)" prop="byf">
          <el-input
            v-model="form.byf"
            :precision="2"
            placeholder="请输入保育费(1-10:100;11-20:200;21-31:300)"
          />
        </el-form-item>
        <el-form-item label="伙食费大班(天)" prop="hsf">
          <el-input-number
            v-model="form.hsf"
            :precision="2"
            placeholder="请输入伙食费"
          />
        </el-form-item>
        <el-form-item label="保育费中班(月)" prop="byfZ">
          <el-input
            v-model="form.byfZ"
            :precision="2"
            placeholder="请输入保育费(1-10:100;11-20:200;21-31:300)"
          />
        </el-form-item>
        <el-form-item label="伙食费中班(天)" prop="hsfZ">
          <el-input-number
            v-model="form.hsfZ"
            :precision="2"
            placeholder="请输入伙食费"
          />
        </el-form-item>
        <el-form-item label="保育费小班(月)" prop="byfX">
          <el-input
            v-model="form.byfX"
            :precision="2"
            placeholder="请输入保育费(1-10:100;11-20:200;21-31:300)"
          />
        </el-form-item>
        <el-form-item label="伙食费小班(天)" prop="hsfX">
          <el-input-number
            v-model="form.hsfX"
            :precision="2"
            placeholder="请输入伙食费"
          />
        </el-form-item>
        <el-form-item label="保育费托班(月)" prop="byfT">
          <el-input
            v-model="form.byfT"
            :precision="2"
            placeholder="请输入保育费(1-10:100;11-20:200;21-31:300)"
          />
        </el-form-item>
        <el-form-item label="伙食费托班(天)" prop="hsT">
          <el-input-number
            v-model="form.hsfT"
            :precision="2"
            placeholder="请输入伙食费"
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
  listSchoolcharge,
  getSchoolcharge,
  delSchoolcharge,
  addSchoolcharge,
  updateSchoolcharge,
} from "@/api/benyi/schoolcharge";

export default {
  name: "Schoolcharge",
  data() {
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
      // 园所收费标准表格数据
      schoolchargeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        byf: undefined,
        hsf: undefined,
        createUserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询园所收费标准列表 */
    getList() {
      this.loading = true;
      listSchoolcharge(this.queryParams).then((response) => {
        this.schoolchargeList = response.rows;
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
        deptId: undefined,
        byf: undefined,
        hsf: undefined,
        byfZ: undefined,
        hsfZ: undefined,
        byfX: undefined,
        hsfX: undefined,
        byfT: undefined,
        hsfT: undefined,
        createUserid: undefined,
        createTime: undefined,
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      if (row.id == null) {
        this.open = true;
        this.title = "添加园所收费标准";
      } else {
        const id = row.id || this.ids;
        getSchoolcharge(id).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "设置园所收费标准";
        });
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSchoolcharge(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("设置成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSchoolcharge(this.form).then((response) => {
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
      this.$confirm("是否确认删除园所收费标准数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delSchoolcharge(ids);
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
.edit-btns {
  .el-button {
    display: block;
    margin: 0 auto;
  }
}
</style>
