<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入客户姓名"/>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" placeholder="请输入电话"/>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="submitForm">提交</el-button>
  </div>
</template>

<script>
  export default {
    name: 'signContract',
    data() {
      return {
        form: {},
        rules: {
          name: [
            {required: true, message: "姓名不能为空", trigger: "blur"}
          ],
          phone: [
            {required: true, message: "电话不能为空", trigger: "blur"}
          ]
        }
      }
    },
    methods: {
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.$emit('handleOnSubmit', this.form);
          }
        })
      }
    }
  }
</script>
