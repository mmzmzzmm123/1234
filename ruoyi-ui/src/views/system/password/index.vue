<template>
  <div class="app-container">
    <el-card class="box-card loading-area">
      <div slot="header" class="clearfix">
        <span>安全设置</span>
      </div>
      <div>
        <el-form ref="form" :model="form" size="medium" label-width="210px">

          <el-collapse v-model="ActiveNames">
            <el-collapse-item title="常规设置" name="1">
              <el-form-item label="密码输错多少次后锁定用户" prop="maxFailedLoginAttempts">
                <el-input-number v-model="form.maxFailedLoginAttempts" :min="0" placeholder="请输入错误次数"
                                 controls-position=right>
                </el-input-number>

              </el-form-item>
              <el-form-item label="密码锁定时长(分钟)">
                <el-input-number v-model="form.userLockPeriod" :min="0" controls-position=right>
                </el-input-number>
              </el-form-item>
              <el-form-item label="密码多少天后过期">
                <el-input-number v-model="form.passwordExpirationPeriodDays" :min="0" controls-position=right>
                </el-input-number>
              </el-form-item>
              <el-form-item label="用户锁定后通知的邮箱" prop="userLockoutNotificationEmail">
                <el-input v-model="form.userLockoutNotificationEmail" placeholder="请输入用户锁定后通知的邮箱" clearable></el-input>
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="密码规则设置" name="2">
              <el-form-item label="密码最小长度">
                <el-input-number v-model="form.minimumLength" placeholder="密码最小长度" controls-position=right
                                 data-value="password.minimumLength">
                </el-input-number>
              </el-form-item>
              <el-form-item label="密码最大长度">
                <el-input-number v-model="form.maximumLength" placeholder="密码最大长度" controls-position=right
                                 data-value="password.minimumLength">
                </el-input-number>
              </el-form-item>
              <el-form-item label="密码需要包含的大写字母个数">
                <el-input-number v-model="form.minimumUppercaseLetters" :min="0" placeholder="密码需要包含的大写字母个数"
                                 controls-position=right></el-input-number>
              </el-form-item>
              <el-form-item label="密码需要包含的小写字母个数">
                <el-input-number v-model="form.minimumLowercaseLetters" :min="0"
                                 controls-position=right></el-input-number>
              </el-form-item>
              <el-form-item label="密码需要包含的数字个数">
                <el-input-number v-model="form.minimumDigits" :min="0" controls-position=right></el-input-number>
              </el-form-item>
              <el-form-item label="密码需要包含的特殊字符个数">
                <el-input-number v-model="form.minimumSpecialCharacters" :min="0" controls-position=right>
                </el-input-number>
              </el-form-item>

            </el-collapse-item>
          </el-collapse>
          <el-form-item size="medium" style="margin-top: 10px">
            <el-button type="primary" @click="submitForm">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import {listPassword, updatePassword} from "@/api/system/password";

export default {
  name: "Password",
  data() {
    return {
      activeName: ["1", "2"],
      // 遮罩层
      loading: true,
      // 密码策略表单参数
      form: {
        id: 1,
        createBy: "admin",
        createTime: "2022-05-15 12:00:02",
        maxFailedLoginAttempts: 5,
        maximumLength: 20,
        minimumDigits: 2,
        minimumLength: 8,
        minimumLowercaseLetters: 1,
        minimumSpecialCharacters: 1,
        minimumUppercaseLetters: 1,
        passwordExpirationPeriodDays: 90,
        userLockPeriod: 10,
        passwordPolicyDesc: "",
        updateBy: "",
        updateTime: null,
        userLockoutNotificationEmail: null
      },
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询密码策略列表 */
    getList() {
      this.loading = true;
      listPassword(this.queryParams).then(response => {
        this.form = response.data;
        console.log(this.form)
        this.loading = false;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        updatePassword(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.open = false;
          this.getList();
        });
      });
    },
  }
};
</script>
