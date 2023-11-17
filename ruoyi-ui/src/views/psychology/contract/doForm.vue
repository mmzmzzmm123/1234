<template>
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item prop="remark">
        <el-input
          type="textarea"
          :rows="2"
          :placeholder="placeholder"
          v-model="form.remark">
        </el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {updateContract} from "@/api/psychology/contract";

export default {
  name: "doForm",
  data() {
    return {
      open: false,
      title: '',
      placeholder: '',
      form: {
        id: null,
        status: null,
        remark: ''
      },
      // 表单校验
      rules: {}
    }
  },
  methods: {
    init(data) {
      this.form = {
        id: data.id,
        status: data.status,
        remark: '',
      }
      this.title = data.title
      this.placeholder = data.placeholder
      this.open = true
      this.rules.remark = [
        {required: true, message: this.placeholder, trigger: "blur"}
      ]
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(async valid => {
        if (valid) {
          console.log(this.form)
          if (this.form.status === '2') {
            this.form.memo2 = this.form.remark
          } else {
            this.form.memo1 = this.form.remark
          }

          updateContract(this.form).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.cancel()
            this.$emit('handleOk')
          });
        }
      });
    },
    cancel() {
      this.form = {
        id: null,
        status: null,
        remark: ''
      }
      this.open = false
    }
  }
}
</script>

<style scoped>

</style>
