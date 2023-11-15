<template>
  <div v-if="open">
    <el-dialog title="账号开通成功！" :visible.sync="open" width="505px" height="268px" :close-on-click-modal="false" :modal-append-to-body="false" append-to-body>
      <div class="user" v-if="copyValue">
        <el-link type="primary" :underline="false" v-clipboard:copy="copyValue" v-clipboard:success="clipboardSuccess" class="copy">复制</el-link>
        <div v-for="item in copyValue.split(' ')">{{ item }}</div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="ok">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "user",
  data() {
    return {
      open: false,
      copyValue: '',
      // copyValue: '登录地址：http://admin.ssgpsy.com/ \n登录账号：张三 \n初始密码：YJY88888888',
    }
  },
  methods: {
    /** 复制代码成功 */
    clipboardSuccess() {
      this.$modal.msgSuccess("复制成功");
    },
    init(val) {
      this.open = true
      this.copyValue = val
    },
    ok() {
      this.open = false
    },
  }
}
</script>

<style scoped>
.user {
  padding-left: 20px;
  margin: 10px auto;
  background: #F9F9F9;
  width: 362px;
  height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
}
.copy {
  position: absolute;
  right: 20px;
  top: 20px;
}
</style>
