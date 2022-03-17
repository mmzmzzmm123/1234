<template>
  <div class="app-container">

    <el-descriptions title="个人中心" :v-model="employee">
      <el-descriptions-item label="部门"></el-descriptions-item>
      <el-descriptions-item v-model="form.phone" label="手机号"></el-descriptions-item>
      <el-descriptions-item label="居住地">苏州市</el-descriptions-item>
      <el-descriptions-item label="备注">
        <el-tag size="small">学校</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="联系地址">江苏省苏州市吴中区吴中大道 1188 号</el-descriptions-item>
    </el-descriptions>

    <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>

    <!-- 添加或修改员工信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门id" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门id" />
        </el-form-item>
        <el-form-item label="职位id" prop="postId">
          <el-input v-model="form.postId" placeholder="请输入职位id" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="身份证" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证" />
        </el-form-item>
        <el-form-item label="银行卡" prop="bankCard">
          <el-input v-model="form.bankCard" placeholder="请输入银行卡" />
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
import { getEmployee, updateEmployee } from "@/api/oa/employee";

export default {
  name: "Employee",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 员工信息表格数据
      employee: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getEmployee();
  },
  methods: {
    /** 查询员工信息 */
    getEmployee() {
      this.loading = true;
      getEmployee('100001').then(response => {
        this.employee = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getEmployee(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工信息";
      });
    },
  }
};
</script>
